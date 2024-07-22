package fr.poseidonj.cinematec_back.models.dtos.special;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO<D extends BaseDTO> {

    private D dto;
    private boolean ignoredClass;
    private ExampleMatcher.MatchMode mode;
    private ExampleMatcher.StringMatcher type;

    private List<DateDTO> dates;
    private List<Contains> contains;
}
