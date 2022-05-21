package com.raulp.user.mapper;

import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
    @Mappings({
            @Mapping(target = "name", source = "instructor.username")
    })
    InstructorMinimalDTO instructorMinimalFromInstructor(Instructor instructor);
}
