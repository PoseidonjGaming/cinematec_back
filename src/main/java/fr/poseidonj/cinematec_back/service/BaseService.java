package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import fr.poseidonj.cinematec_back.repositories.IBaseRepository;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;

import java.util.List;

public abstract class BaseService<E extends BaseEntity, D extends BaseDTO> implements IBaseService<D> {
    private final IBaseRepository<E> repository;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;
    private final IMapper mapper;

    protected BaseService(IBaseRepository<E> repository, Class<E> entityClass, Class<D> dtoClass, IMapper mapper) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.mapper = mapper;
    }

    @Override
    public List<D> getAll() {
        return mapper.convertList(repository.findAll(), dtoClass);
    }

    @Override
    public List<D> getByIds(List<String> ids) {
        return mapper.convertList(repository.findByIdIn(ids), dtoClass);
    }

    @Override
    public D getById(String id) {
        return repository.findById(id).map(e -> mapper.convert(e, dtoClass)).orElse(null);
    }

    @Override
    public void save(D dto) {
        repository.save(mapper.convert(dto, entityClass));
    }
}
