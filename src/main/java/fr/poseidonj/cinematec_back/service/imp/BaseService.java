package fr.poseidonj.cinematec_back.service.imp;

import fr.poseidonj.cinematec_back.exception.GenericException;
import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.PagedResponse;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SortDTO;
import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import fr.poseidonj.cinematec_back.repositories.IBaseRepository;
import fr.poseidonj.cinematec_back.service.IBaseService;
import fr.poseidonj.cinematec_back.utilities.annotation.Json;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

import static fr.poseidonj.cinematec_back.utilities.SearchUtility.*;
import static fr.poseidonj.cinematec_back.utilities.ServiceUtility.*;

public abstract class BaseService<E extends BaseEntity, D extends BaseDTO, R extends IBaseRepository<E>> implements IBaseService<D> {
    protected final R repository;
    protected final Class<E> entityClass;
    protected final Class<D> dtoClass;
    protected final IMapper mapper;

    protected BaseService(R repository, Class<E> entityClass, Class<D> dtoClass, IMapper mapper) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.mapper = mapper;
    }

    @Override
    public Map<String, String> getStructure() {
        Map<String, String> structure = new LinkedHashMap<>();
        Class<?> superClass = dtoClass.getSuperclass();

        BiConsumer<Field, Map<String, String>> consumer = ((field, map) -> {
            if (Objects.nonNull(field.getType().getSuperclass()) &&
                    Number.class.isAssignableFrom(field.getType())) {
                map.put(field.getName(), "number");
            } else if (field.getType().equals(LocalTime.class)) {
                map.put(field.getName(), "time");
            } else if (field.getType().equals(LocalDate.class)) {
                map.put(field.getName(), "date");
            } else if (field.isAnnotationPresent(Json.class)) {
                map.put(field.getName(), field.getAnnotation(Json.class).type());
            } else {
                map.put(field.getName(), field.getType().getSimpleName().toLowerCase());
            }
        });

        while (!superClass.equals(Object.class)) {
            structure.putAll(getMap(superClass, consumer));
            superClass = superClass.getSuperclass();
        }

        structure.putAll(getMap(dtoClass, consumer));
        return structure;
    }

    @Override
    public Map<String, String[]> getDisplay() {
        Map<String, String[]> display = getMap(dtoClass, (field, map) -> {
            if (!map.containsKey(field.getType().getSimpleName()) && field.isAnnotationPresent(Json.class)) {
                map.put(field.getName(), field.getAnnotation(Json.class).display());
            }
        });
        display.put("current", entityClass.getAnnotation(Json.class).display());
        return display;
    }

    @Override
    public Map<String, String> getType() {
        Map<String, String> types = new LinkedHashMap<>();
        Class<?> superClass = entityClass.getSuperclass();

        BiConsumer<Field, Map<String, String>> biConsumer = (field, map) -> {
            if (field.isAnnotationPresent(Json.class)) {
                map.put(field.getName(), field.getAnnotation(Json.class).type());
            }
        };

        while (!superClass.equals(Object.class)) {
            types.putAll(getMap(superClass, biConsumer));
            superClass = superClass.getSuperclass();
        }

        types.putAll(getMap(dtoClass, biConsumer));
        return types;
    }

    @Override
    public List<D> getAll() {
        return mapper.convertList(repository.findAll(), dtoClass);
    }

    @Override
    public PagedResponse<D> getAll(int page, int size) {
        return createPage(repository.findAll(getPageable(size, page)), null, mapper, dtoClass);
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
    public PagedResponse<D> search(SearchDTO<D> searchDTO, int page, int size) {
        return createPage(
                repository.findAll(
                        Example.of(
                                mapper.convert(searchDTO.getDto(), entityClass),
                                getMatcher(searchDTO, entityClass)
                        ), getPageable(size, page)
                ), searchDTO, mapper, dtoClass);
    }

    @Override
    public List<D> sort(SortDTO sortDTO) {
        return mapper.convertList(repository.findAll(
                Sort.by(sortDTO.getDirection(), sortDTO.getField())
        ), dtoClass);
    }

    @Override
    public PagedResponse<D> sort(SortDTO sortDTO, int page, int size) {
        E entity;
        try {
            entity = entityClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new GenericException(e);
        }
        return  createPage(repository.findAll(Example.of(entity), PageRequest.of(page, size, sortDTO.getDirection(),
                getPath(findField(entityClass, sortDTO.getField()).toArray(new String[]{})))), null, mapper, dtoClass);
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
