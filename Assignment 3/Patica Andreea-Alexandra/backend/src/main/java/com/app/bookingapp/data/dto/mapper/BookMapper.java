package com.app.bookingapp.data.dto.mapper;

import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.data.sql.entity.Book;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookMapper {
    Book bookDtoToBook(BookDto bookDto);

    BookDto bookToBookDto(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromBookDto(BookDto bookDto, @MappingTarget Book book);
}
