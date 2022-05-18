package com.group;

import com.group.model.Group;
import com.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
