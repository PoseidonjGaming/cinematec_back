package fr.poseidonj.cinematec_back.models.dtos.special;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SortSearchDTO<D extends BaseDTO> {
    private SortDTO sortDTO;
    private SearchDTO<D> searchDTO;
}
