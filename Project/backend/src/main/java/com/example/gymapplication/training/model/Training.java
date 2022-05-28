package com.example.gymapplication.training.model;

import com.example.gymapplication.user.model.User;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Training {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String title;

    @Column(nullable = false, length = 256)
    private String type;

    @Column(nullable = false)
    private String date;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User user;

    @ManyToMany(mappedBy = "regularTrainings", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<User> users =  new ArrayList<>();
}
