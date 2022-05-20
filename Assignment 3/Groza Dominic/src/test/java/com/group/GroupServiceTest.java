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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static com.TestCreationFactory.listOf;
import static com.TestCreationFactory.newGroupDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        MockitoAnnotations.openMocks(this);
        groupService = new GroupService(mockGroupRepository, mockGroupMapper);
    }

    @Test
    void findAll() {
        List<Group> groups = listOf(Group.class);
        when(mockGroupRepository.findAll()).thenReturn(groups);
        List<GroupDto> preparedGroupDtos = new ArrayList<>();
        for (Group group : groups) {
            GroupDto groupDto = newGroupDto();
            when(mockGroupMapper.toDto(group)).thenReturn(groupDto);
            preparedGroupDtos.add(groupDto);
        }

        List<GroupDto> obtainedGroupDtos = groupService.findAll();
        assertEquals(groups.size(), obtainedGroupDtos.size());
        for (int i = 0; i < obtainedGroupDtos.size(); i++) {
            GroupDto obtained = obtainedGroupDtos.get(i);
            GroupDto prepared = preparedGroupDtos.get(i);
            assertEquals(obtained, prepared);
        }
        assertEquals(groups.size(), obtainedGroupDtos.size());
        for (int i = 0; i < obtainedGroupDtos.size(); i++) {
            GroupDto obtained = obtainedGroupDtos.get(i);
            GroupDto prepared = preparedGroupDtos.get(i);
            assertEquals(obtained, prepared);
        }
    }

    @Test
    void get() {
        Group group = new Group();
        when(mockGroupRepository.findById(any(Long.class))).thenReturn(Optional.of(group));
        GroupDto groupDto = newGroupDto();
        when(mockGroupMapper.toDto(group)).thenReturn(groupDto);

        GroupDto obtainedGroupDto = groupService.get(1L);
        assertEquals(groupDto, obtainedGroupDto);
    }

}