package com.lab4.demo.order.mapper;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.model.Order;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target="userId", ignore = true),
            @Mapping(target="bookIds", ignore = true)
    })
    OrderDTO toDto(Order order);

    @Mappings({
            @Mapping(target="user", ignore = true),
            @Mapping(target="books", ignore = true)
    })
    Order fromDto(OrderDTO orderDTO);

    @AfterMapping
    default void populateFields(Order order, @MappingTarget OrderDTO orderDTO) {
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setBookIds(order.getBooks().stream().map(Book::getId).collect(Collectors.toSet()));
    }
}
