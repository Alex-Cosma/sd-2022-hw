package com.example.airbnb.booking.model;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accommodation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation accommodation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guest_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User guest;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, length = 20)
    private Date start_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, length = 20)
    private Date end_date;
}
