package fr.poseidonj.cinematec_back.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import fr.poseidonj.cinematec_back.repositories.IBaseRepository;

import java.util.List;

public abstract class BaseService<E extends BaseEntity,D extends BaseDTO> implements IBaseService<D> {
    private final IBaseRepository<E> repository;
    private final ObjectMapper mapper;

    protected BaseService(IBaseRepository<E> repository) {
        this.repository = repository;
        mapper = new ObjectMapper();
    }

    @Override
    public List<D> getAll() {
        return mapper.convertValue(repository.findAll(), new TypeReference<>() {});
    }
}
