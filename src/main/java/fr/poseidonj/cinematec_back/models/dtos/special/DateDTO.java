package fr.poseidonj.cinematec_back.models.dtos.special;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateDTO {
    private String field;
    private LocalDate startDate;
    private LocalDate endDate;
}
