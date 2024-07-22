package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SortDTO;
import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import fr.poseidonj.cinematec_back.repositories.IBaseRepository;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;

import static fr.poseidonj.cinematec_back.utilities.SearchUtility.filtering;
import static fr.poseidonj.cinematec_back.utilities.SearchUtility.getMatcher;

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
    public List<D> search(SearchDTO<D> searchDTO) {
        return mapper.convertList(repository.findAll(
                Example.of(mapper.convert(searchDTO.getDto(), entityClass),
                        getMatcher(searchDTO, entityClass))
        ), dtoClass).stream().filter(dto -> filtering(dto, searchDTO)).toList();
    }

    @Override
    public List<D> sort(SortDTO sortDTO) {
        return mapper.convertList(repository.findAll(
                Sort.by(sortDTO.getDirection(), sortDTO.getField())
        ), dtoClass);
    }

    @Override
    public D getById(String id) {
        return repository.findById(id).map(e -> mapper.convert(e, dtoClass)).orElse(null);
    }

    @Override
    public void save(D dto) {
        repository.save(mapper.convert(dto, entityClass));
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
