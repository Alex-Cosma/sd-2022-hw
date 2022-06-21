package com.app.bookingapp.data.sql.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Lob
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "pictures_id", nullable = false)
    private Long picturesId;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "rating", nullable = false)
    private Float rating;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "number_of_rooms", nullable = false)
    private Long numberOfRooms;

    @Column(name = "number_of_beds", nullable = false)
    private Long numberOfBeds;

    @Column(name = "number_of_bathrooms", nullable = false)
    private Long numberOfBathrooms;

    @Column(name = "kitchen", nullable = false)
    private Long kitchen;                           //TODO boolean

    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

}