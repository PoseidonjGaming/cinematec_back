package fr.poseidonj.cinematec_back.utilities.mapper;

import java.util.List;

public interface IMapper {

    <S, T> T convert(S source, Class<T> targetClass);

    default <S, T> List<T> convertList(List<S> listSource, Class<T> targetClass) {
        return listSource.stream().map(source -> convert(source, targetClass)).toList();
    }


}
