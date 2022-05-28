package com.lab4.demo.user;

import com.lab4.demo.playlist.PlaylistMapper;
import com.lab4.demo.playlist.PlaylistService;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.TrackMapper;
import com.lab4.demo.track.TrackService;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.dto.TrackDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TrackService trackService;
    private final TrackMapper trackMapper;
    private final PlaylistMapper playlistMapper;
    private final PlaylistService playlistService;

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map( user ->{
                            UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
                            userMapper.populateRoles(user,userListDTO);
                            userMapper.populateTracks(user,userListDTO);
                            return userListDTO;
                        }
                ).collect(toList());
    }

    public UserListDTO createUser(UserListDTO userListDTO) {
        User user = User.builder()
                .username(userListDTO.getName())
                .password(passwordEncoder.encode(userListDTO.getPassword()))
                .email(userListDTO.getEmail())
                .build();

        Set<String> rolesStr = userListDTO.getRoles();
        Set<Role> roles = new HashSet<>();
        if (rolesStr==null) {
            Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }
        user.setRoles(roles);
        return  userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public UserListDTO updateUser(Long id, UserListDTO user) throws UserNotFoundException {
        User user1 = findById(id);
        user1.setUsername(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        userMapper.populateRoles(user1,user);
        return userMapper.userListDtoFromUser(userRepository.save(user1));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserListDTO buyTrack(Long id, TrackDTO trackDTO) throws UserNotFoundException {
        User user = findById(id);
        Optional<Track> track = trackService.findByTitle(trackDTO.getTitle());
        if(track.isEmpty()) {
            Random tid = new Random();
            trackDTO.setId((long) tid.nextInt());
            TrackDTO trackDTO1 = trackService.create(trackDTO);
            user.getPurchasedTracks().add(trackMapper.fromDto(trackDTO1));
        }
        else{
            user.getPurchasedTracks().add(track.get());
        }
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public UserListDTO createPlaylist(Long id, PlaylistDTO playlistDTO) throws UserNotFoundException {
        User user = findById(id);
        playlistDTO.setTracks(new ArrayList<>());
        PlaylistDTO playlist = playlistService.create(playlistDTO);
        List<Playlist> playlists = user.getPlaylistList();
        playlists.add(playlistMapper.fromDTO(playlist));
        user.setPlaylistList(playlists);
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }
}
