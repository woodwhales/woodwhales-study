package cn.woodwhales.mapstruct.converter;

/**
 * @author woodwhales
 * @date 2020-12-24 21:22
 */
public interface MyConverter<S, T> {

    T convert(S s);
}
