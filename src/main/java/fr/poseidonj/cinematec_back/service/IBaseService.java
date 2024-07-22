package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;

import java.util.List;

public interface IBaseService<D extends BaseDTO> {
    List<D> getAll();
    List<D> getByIds(List<String> ids);
    D getById(String id);

    void save(D dto);
}
