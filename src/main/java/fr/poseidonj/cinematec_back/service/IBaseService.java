package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SortDTO;

import java.util.List;

public interface IBaseService<D extends BaseDTO> {
    List<D> getAll();
    List<D> getByIds(List<String> ids);
    List<D> search(SearchDTO<D> searchDTO);
    List<D> sort(SortDTO sortDTO);
    D getById(String id);

    void save(D dto);
    void delete(String id);
}
