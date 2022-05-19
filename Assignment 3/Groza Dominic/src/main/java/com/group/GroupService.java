package com.group;

import com.group.model.Group;
import com.group.model.dto.GroupDto;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;



    private Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found" + id));
    }

    public List<GroupDto> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    public GroupDto create(GroupDto group) {
        return groupMapper.toDto(groupRepository.save(groupMapper.fromDto(group)));
    }

    public Set<User> findAllUsers(Long id) {
        return groupRepository.findById(id).get().getUsers();
    }

    public GroupDto get(Long id) {
        return groupMapper.toDto(findById(id));
    }
}
