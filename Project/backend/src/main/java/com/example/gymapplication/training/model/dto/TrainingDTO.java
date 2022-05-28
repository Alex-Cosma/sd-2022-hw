package com.example.gymapplication.training.model.dto;

import com.example.gymapplication.training.model.Location;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
@Getter
@Setter
public class TrainingDTO {
    private Long id;

    @NotNull
    @Pattern(regexp="^[A-Za-z .]*$", message = "Title must contain only letters")
    private String title;

    @NotNull(message = "Type must be a string")
    private String type;

    @NotNull(message = "Date must be a string")
    private String date;

    private String location;

    private String user;

    private List<String> users;
}
