package fr.poseidonj.cinematec_back.models.dtos.special;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagedResponse<D extends BaseDTO> {
    private List<D> content;
    private long size;
    private long totalElement;

}
