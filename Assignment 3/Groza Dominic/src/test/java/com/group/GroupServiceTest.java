package com.group;

import com.TestCreationFactory;
import com.group.model.Group;
import com.group.model.dto.GroupDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository mockGroupRepository;
    @Mock
    private GroupMapper mockGroupMapper;
    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        groupService = new GroupService(mockGroupRepository, mockGroupMapper);
    }

    @Test
    void testFindAll() {
        List<GroupDto> groups = TestCreationFactory.listOf(GroupDto.class);
        when(mockGroupRepository.findAll().stream().map(
                mockGroupMapper::toDto)
                .collect(Collectors.toList())).thenReturn(groups);

        List <GroupDto> allGroups = groupService.findAll().stream().map(
                mockGroupMapper::fromDto).
                map(mockGroupMapper::toDto).
                collect(Collectors.toList());

        Assertions.assertEquals(groups.size(), allGroups.size());
    }

}
