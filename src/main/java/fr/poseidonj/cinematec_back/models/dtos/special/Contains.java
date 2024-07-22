package fr.poseidonj.cinematec_back.models.dtos.special;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contains {
    private String field;
    private List<?> values;
}
