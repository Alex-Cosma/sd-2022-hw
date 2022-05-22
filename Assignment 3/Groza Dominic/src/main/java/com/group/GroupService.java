package com.group;

import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.post.model.dto.PostDto;
import com.user.UserRepository;
import com.user.UserService;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserMapper userMapper;


    private Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found" + id));
    }

    public List<GroupDto> findAll() {
        return groupRepository.findAll().stream()
                .map(group->{
                    GroupDto groupDto = groupMapper.toDto(group);

                    Set<UserListDto> userListDtos = new HashSet<>();
                    group.getUsers().stream().map(userMapper::userListDtoFromUser).forEach(userListDtos::add);

                   groupDto.setUsers(userListDtos);
                    return groupDto;
                })
                .collect(Collectors.toList());
    }


    public GroupDto get(Long id) {
        return groupMapper.toDto(findById(id));
    }
}
