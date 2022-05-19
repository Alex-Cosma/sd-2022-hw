package com.group;

import com.group.model.dto.GroupDto;
import com.user.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.UrlMapping.*;

@RestController
@RequestMapping(GROUPS)
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @CrossOrigin
    @GetMapping
    public List<GroupDto> allGroups() {
        return groupService.findAll();
    }

    @CrossOrigin
    @PatchMapping(ENTITY)
    public GroupDto addUser(@PathVariable Long id ,@RequestBody GroupDto groupDto) {
        return groupService.addUser(id,groupDto);
    }

    @CrossOrigin
    @GetMapping(ENTITY)
    public GroupDto getGroup(@PathVariable Long id) {
        return groupService.get(id);
    }
}
