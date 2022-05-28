package com.example.leaguecomp.user;

import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.ItemRepository;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.mail.MailService;
import com.example.leaguecomp.rune.RuneRepository;
import com.example.leaguecomp.rune.model.Rune;
import com.example.leaguecomp.summoner.SummonerRepository;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.dto.UserMinimalDTO;
import com.example.leaguecomp.user.mapper.UserMapper;
import com.example.leaguecomp.user.model.ERole;
import com.example.leaguecomp.user.model.Role;
import com.example.leaguecomp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final SummonerRepository summonerRepository;
    private final MailService mailService;
    private final ChampionRepository championRepository;
    private final ItemRepository itemRepository;
    private final RuneRepository runeRepository;

    public List<UserMinimalDTO> allUsersMinimal(){
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(Collectors.toList());
    }

    public List<UserListDTO> allUsersForList(){
        List<UserListDTO> list = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
            userMapper.populateRoles(user,userListDTO);
            if(userListDTO.getRoles().contains("EMPLOYEE")) {
                list.add(userListDTO);
            }
        }
        return list;
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("User not found: "+id));
    }
    public User findByName(String name){
        return userRepository.findByUsername(name)
                .orElseThrow(()->new EntityNotFoundException("User not found: "+name));
    }

    public UserListDTO edit(Long id, UserListDTO user){
        User actUser = userRepository.getById(id);
        Set<Role> rolesS = new HashSet<>();
        Set<String> roles = user.getRoles();
        roles.forEach(r->{
            Role ro = roleRepository.findByRole(ERole.valueOf(r))
                    .orElseThrow(()->new RuntimeException("no role"));
            rolesS.add(ro);
        });
        actUser.setRoles(rolesS);
        actUser.setUsername(user.getName());
        actUser.setEmail(user.getEmail());
        return userMapper.userListDtoFromUser(userRepository.save(actUser));
    }

    public void delete(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }

    public void followSummoner(String name,String summonerName){
        User user = findByName(name);
        Summoner summoner = summonerRepository.findByName(summonerName).orElseThrow(()->new RuntimeException("summoner not found"));
        user.getFollowedSummoners().add(summoner);
        userRepository.save(user);
    }

    public User create(User user){
        return userRepository.save(user);
    }


    public void sendMail(ChampionDTO champion) throws MessagingException, IOException {
        Champion championAct = championRepository.findById(champion.getId()).get();
        Set<String> runesAct = champion.getRunes();
        List<String> itemsAct = champion.getBuild();
        Set<Rune> runes = new HashSet<>();
        List<Item> items = new ArrayList<>();
        if(runesAct != null) {
            runesAct.forEach(r -> {
                Rune rune = runeRepository.findByName(r)
                        .orElseThrow(() -> new RuntimeException("no rune"));
                runes.add(rune);
            });
        }
        if(itemsAct != null) {
            itemsAct.forEach(item -> {
                Item item1 = itemRepository.findByName(item)
                        .orElseThrow(() -> new RuntimeException("no item"));
                items.add(item1);
            });
        }
        List<User> users = userRepository.findAll();
        for(User user: users){
            mailService.sendEmail(championAct,user);
        }
    }
}
