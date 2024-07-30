package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.PagedResponse;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SortDTO;

public interface IPageService<D extends BaseDTO> {
    PagedResponse<D> getAll(int page, int size);

    PagedResponse<D> search(SearchDTO<D> searchDTO, int page, int size);

    PagedResponse<D> sort(SortDTO sortDTO, int page, int size);
}
