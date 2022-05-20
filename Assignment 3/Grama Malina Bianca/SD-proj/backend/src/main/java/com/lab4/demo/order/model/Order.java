package com.lab4.demo.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.user.model.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Builder.Default
    private Set<Book> books = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column
    @Builder.Default
    private Date deliveryDate = new Date(System.currentTimeMillis());

    @Column
    @Builder.Default
    private Date returnDate = new Date(System.currentTimeMillis() + 2 * 7 * 24 * 60 * 60 * 1000);

    @Column(nullable = false)
    private String address;
}
