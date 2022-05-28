package com.forum.thread.mapper;

import com.forum.thread.model.dto.TopicDTO;
import com.forum.thread.model.Topic;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    @Mappings({
            @Mapping(target = "categoryName", ignore = true),
            @Mapping(target = "originalPoster", ignore = true)
    })
    TopicDTO toDto(Topic topic);

    @AfterMapping
    default void setCategoryAndOP(Topic topic, @MappingTarget TopicDTO topicDTO) {
        topicDTO.setCategoryName(topic.getCategory().getName());
        topicDTO.setOriginalPoster(topic.getUser().getUsername());
    }

    @Mappings({
            @Mapping(target = "category", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    Topic fromDto(TopicDTO topicDTO);
}
