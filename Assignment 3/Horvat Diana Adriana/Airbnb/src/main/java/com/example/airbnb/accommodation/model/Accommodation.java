package com.example.airbnb.accommodation.model;

import com.example.airbnb.address.model.Address;
import com.example.airbnb.amenities.model.Amenity;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.image.model.ImageURL;
import com.example.airbnb.review.model.Review;
import com.example.airbnb.user.model.Role;
import lombok.*;
import org.bson.types.Decimal128;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 256)
    private String _id;

    @Column(nullable = false, length = 1024)
    private String name;

    @Column(nullable = false, columnDefinition="TEXT")
    private String description;

    @Column(nullable = true, columnDefinition="TEXT")
    private String house_rules;

    @Column(nullable = true, length = 256)
    private String property_type;

    @Column(nullable = true, length = 256)
    private String room_type;

    @Column(nullable = true)
    private Integer accommodates;

    @Column(nullable = false)
    private Integer bathrooms;

    @Column(nullable = false)
    private Integer bedrooms;

    @Column(nullable = false)
    private Integer beds;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Amenity> amenities = new HashSet<>();

    @Column(nullable = false)
    private Double price;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "images_fk", referencedColumnName = "id")
    private ImageURL imageURL;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_fk", referencedColumnName = "id")
    private Address address;

    //i know this should be one to many but the migration created the table for
    //the many to many relationship
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accommodation_reviews",
            joinColumns = @JoinColumn(name = "parent_fk"),
            inverseJoinColumns = @JoinColumn(name = "object_fk"))
    private Set<Review> reviews = new HashSet<>();

//    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private Set<Booking> bookings = new HashSet<>();

}
