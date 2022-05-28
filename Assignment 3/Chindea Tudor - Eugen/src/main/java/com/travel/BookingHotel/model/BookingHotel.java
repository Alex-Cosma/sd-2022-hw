package com.travel.BookingHotel.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travel.city.model.City;
import com.travel.hotel.model.Hotel;
import com.travel.user.model.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class BookingHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
     @Column( nullable = false, length = 20)
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column( nullable = false, length = 20)
    private Date endDate;

    @Column(nullable = false)
    private Integer places;

    @Column( nullable = false)
    private Long price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="hotel_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Hotel hotel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bookingHotel_user",
            joinColumns = @JoinColumn(name = "bookingHotel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
