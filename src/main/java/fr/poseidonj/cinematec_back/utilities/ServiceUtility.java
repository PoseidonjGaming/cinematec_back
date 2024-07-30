package fr.poseidonj.cinematec_back.utilities;

import fr.poseidonj.cinematec_back.exception.GenericException;
import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.PagedResponse;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static fr.poseidonj.cinematec_back.utilities.SearchUtility.filtering;

@UtilityClass
public class ServiceUtility {

    public static <O> void browseField(Class<?> clazz, O object, BiConsumer<Field, O> consumer) {
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> consumer.accept(field, object));
    }

    public static void browseField(Class<?> clazz, Consumer<Field> consumer) {
        Arrays.stream(clazz.getDeclaredFields()).forEach(consumer);
    }


    public static <O> O get(Field field, Object object) {
        O returned;
        try {
            field.setAccessible(true);
            returned = (O) field.get(object);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            throw new GenericException(e);
        }
        return returned;

    }

    public static <O, T> void set(O source, T target, Field targetField) {
        try {
            if (targetField != null) {
                targetField.setAccessible(true);
                targetField.set(target, source);
                targetField.setAccessible(false);
            }

        } catch (IllegalAccessException e) {
            throw new GenericException(e);
        }
    }

    public static <O> Field getField(String name, Class<O> sourceClass) {
        if (sourceClass != null) {
            try {
                return sourceClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                return null;
            }
        }
        return null;


    }

    public static <T> Map<String, T> getMap(Class<?> clazz, BiConsumer<Field, Map<String, T>> consumer) {
        Map<String, T> map = new LinkedHashMap<>();
        browseField(clazz, field -> consumer.accept(field, map));
        return map;
    }

    public static Pageable getPageable(int size, int page) {
        return Pageable.ofSize(size).withPage(page);
    }

    public  <D extends BaseDTO, E extends BaseEntity> PagedResponse<D> createPage(Page<E> pageRequest, SearchDTO<D> searchDTO, IMapper mapper, Class<D> dtoClass) {
        List<D> list = mapper.convertList(pageRequest.getContent(), dtoClass);
        if (Objects.nonNull(searchDTO)) {
            list = list.stream().filter(d -> filtering(d, searchDTO)).toList();
        }

        return new PagedResponse<>(list, list.size(), pageRequest.getTotalElements());
    }

}
