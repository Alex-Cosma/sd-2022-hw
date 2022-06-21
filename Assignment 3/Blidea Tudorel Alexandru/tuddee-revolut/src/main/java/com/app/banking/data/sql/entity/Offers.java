package com.app.banking.data.sql.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "offers")
public class Offers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 64)
    private String name;
    @Column(name = "description", length = 256)
    private String description;
    @Column(name = "available")
    private boolean available;

}
