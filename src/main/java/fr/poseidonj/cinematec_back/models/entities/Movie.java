package fr.poseidonj.cinematec_back.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends BaseEntity {
    private String name;
    private String description;
    private LocalTime duration;
    private LocalDate releaseDate;
    private String poster;
}
