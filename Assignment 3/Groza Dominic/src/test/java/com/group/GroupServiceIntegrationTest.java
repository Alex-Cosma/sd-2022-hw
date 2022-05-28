package com.group;

import com.TestCreationFactory;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupServiceIntegrationTest {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        groupRepository.deleteAll();
    }

    @Test
    void findAll() {

        List<Group> groups = TestCreationFactory.listOf(Group.class);
        groupRepository.saveAll(groups);

        List <GroupDto> allGroups= groupService.findAll();

        Assertions.assertEquals(groups.size(), allGroups.size());
    }

    @Test
    void get() {
        Group group = TestCreationFactory.newGroup();
        groupRepository.save(group);

        GroupDto groupDto = groupService.get(group.getId());

        Assertions.assertEquals(group.getName(), groupDto.getName());
    }
}