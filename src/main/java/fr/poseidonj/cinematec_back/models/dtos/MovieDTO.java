package fr.poseidonj.cinematec_back.models.dtos;

import fr.poseidonj.cinematec_back.utilities.annotation.Json;
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
public class MovieDTO extends BaseDTO {
    private String name;
    @Json(type = "text")
    private String summary;
    private LocalTime runtime;
    private LocalDate releaseDate;
    @Json(type = "file")
    private String poster;
}
