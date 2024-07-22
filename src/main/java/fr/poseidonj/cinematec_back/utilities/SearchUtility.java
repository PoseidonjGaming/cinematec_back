package fr.poseidonj.cinematec_back.utilities;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import fr.poseidonj.cinematec_back.utilities.annotation.Entity;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.ExampleMatcher;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static fr.poseidonj.cinematec_back.utilities.ServiceUtility.*;

@UtilityClass
public class SearchUtility {

    public static <E extends BaseEntity, D extends BaseDTO>
    ExampleMatcher getMatcher(SearchDTO<D> searchDTO, Class<E> entityClass) {
        boolean isNull = Arrays.stream(searchDTO.getDto().getClass().getDeclaredFields()).
                filter(field -> !field.getType().equals(List.class))
                .allMatch(field -> Objects.isNull(get(field, searchDTO.getDto())));
        ExampleMatcher matcher;
        if (isNull) {
            matcher = ExampleMatcher
                    .matchingAll()
            ;
        } else {
            matcher = ExampleMatcher
                    .matchingAny();
        }
        matcher = matcher.withIgnorePaths("id");
        if (searchDTO.isIgnoredClass()) {
            matcher.getIgnoredPaths().add("_class");
        }


        return getSpecifiers(matcher.withIgnoreNullValues(), entityClass, searchDTO.getType());
    }

    public static ExampleMatcher getUserMatcher() {
        return ExampleMatcher.matchingAll()
                .withIgnorePaths("password", "id", "review", "roles", "seriesWatchlist", "moviesWatchlist")
                .withIgnoreNullValues().withMatcher("username", matcher -> matcher.exact().caseSensitive());
    }


    public static ExampleMatcher getSpecifiers(ExampleMatcher initMatcher,
                                               Class<?> clazz, ExampleMatcher.StringMatcher stringMatcher) {
        List<ExampleMatcher> matchers = new ArrayList<>();
        browseField(clazz, initMatcher, (field, matcher) -> {
            if (field.getType().equals(List.class) || field.getType().equals(LocalDate.class)) {
                matcher.getIgnoredPaths().add(field.getName());
            }

            if (field.getType().equals(String.class)) {

                matchers.add(matcher.withMatcher(field.getName(), match ->
                        match.stringMatcher(stringMatcher).ignoreCase()));
            }

        });

        return matchers.stream().reduce((exampleMatcher, exampleMatcher2) -> {
            exampleMatcher2.getPropertySpecifiers().getSpecifiers().forEach(value ->
                    exampleMatcher.getPropertySpecifiers().add(value));
            return exampleMatcher;
        }).orElse(initMatcher);
    }

    public static String getPath(String... parts) {
        if (parts[0].isEmpty()) {
            return Arrays.stream(parts).skip(1).reduce((s, s2) -> s + "." + s2).orElse("");
        }
        return Arrays.stream(parts).reduce((s, s2) -> s + "." + s2).orElse("");
    }

    public static <O> List<String> findField(Class<O> clazz, String searchedField) {
        List<String> pathToField = new ArrayList<>();

        Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getName().equals(searchedField)).findFirst()
                .ifPresent(field -> pathToField.add(field.getName()));

        if (pathToField.isEmpty()) {
            Class<?> superClass = clazz.getSuperclass();
            while (!superClass.equals(Object.class)) {
                pathToField.addAll(findField(superClass, searchedField));
                superClass = superClass.getSuperclass();
            }
        }

        if (pathToField.isEmpty()) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.getType().isAnnotationPresent(Entity.class))
                    .forEach(field -> {
                        List<String> path = findField(field.getType(), searchedField);
                        if (!path.isEmpty() && pathToField.isEmpty()) {
                            pathToField.add(field.getName());
                            pathToField.addAll(path);
                        }
                    });
        }

        return pathToField;
    }

    /**
     * Filter the current dto if it checks all/any conditions
     *
     * @param dto       the current dto to filter
     * @param searchDto the dto to compare to
     * @param <D>       the dto type
     * @return true if the dto check all/any conditions
     */
    public static <D extends BaseDTO> boolean filtering(D dto, SearchDTO<D> searchDto) {
        Predicate<Field> predicate = field -> {
            if (field.getType().equals(List.class)) {
                return contains(get(field, dto), get(field, searchDto.getDto()));
            }

            return true;
        };

        if (searchDto.getMode().equals(ExampleMatcher.MatchMode.ALL)) {
            boolean dates = !Objects.nonNull(searchDto.getDates()) || searchDto.getDates().stream().allMatch(dateDTO ->
                    isBetween(get(getField(dateDTO.getField(), dto.getClass()), dto),
                            dateDTO.getStartDate(), dateDTO.getEndDate()));
            boolean contains = !Objects.nonNull(searchDto.getContains()) || searchDto.getContains()
                    .stream().allMatch(contain -> {
                        Field field = getField(contain.getField(), dto.getClass());
                        return contain.getValues().contains(get(field, dto));
                    });
            return Arrays.stream(dto.getClass().getDeclaredFields())
                    .allMatch(predicate) && dates && contains;
        } else {
            boolean dates = !Objects.nonNull(searchDto.getDates()) || searchDto.getDates().stream().anyMatch(dateDTO ->
                    isBetween(get(getField(dateDTO.getField(), dto.getClass()), dto),
                            dateDTO.getStartDate(), dateDTO.getEndDate()));
            boolean contains = !Objects.nonNull(searchDto.getContains()) || searchDto.getContains()
                    .stream().anyMatch(contain -> {
                        Field field = getField(contain.getField(), dto.getClass());
                        return contain.getValues().contains(get(field, dto));
                    });
            return Arrays.stream(dto.getClass().getDeclaredFields())
                    .anyMatch(predicate) || dates || contains;
        }

    }

    /**
     * Returns {@code true} if the entityList contains all items in compareTo otherwise {@code false}
     *
     * @param entityList the current list
     * @param compareTo  the list to compare
     * @param <O>        the type of objects in the lists
     * @return {@code true} if the entityList contains all items in compareTo otherwise {@code false}
     */
    public static <O> boolean contains(List<O> entityList, List<O> compareTo) {
        if (Objects.nonNull(entityList) && Objects.nonNull(compareTo)) {
            return new HashSet<>(entityList).containsAll(compareTo);
        } else {
            return Objects.isNull(compareTo) || compareTo.isEmpty();
        }
    }

    public static boolean isBetween(LocalDate releaseDate, LocalDate startDate, LocalDate endDate) {
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            return (releaseDate.isEqual(startDate) || releaseDate.isEqual(endDate))
                    || (releaseDate.isAfter(startDate) && releaseDate.isBefore(endDate));
        }
        return Objects.nonNull(releaseDate);

    }
}
