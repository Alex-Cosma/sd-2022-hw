package com.example.airbnb.booking.model;

import com.example.airbnb.accommodation.model.Accommodation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    private Accommodation accommodation;

//    @Column(nullable = false, length = 20)
//    private Integer accommodation_id;

    @Column(nullable = false, length = 20)
    private Long guest_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, length = 20)
    private Date start_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, length = 20)
    private Date end_date;
}
