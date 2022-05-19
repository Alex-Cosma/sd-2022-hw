package com.example.demo.report;

import com.example.demo.book.BookMapper;
import com.example.demo.book.BookRepository;
import com.example.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDTO> findItemsByQuantityEquals(Integer quantity){
        return bookRepository.findItemsByQuantityEquals(quantity).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());    }
}
