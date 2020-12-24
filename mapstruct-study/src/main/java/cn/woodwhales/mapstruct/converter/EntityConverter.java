package cn.woodwhales.mapstruct.converter;

/**
 * @author woodwhales
 * @date 2020-12-24 21:05
 */
public interface EntityConverter<S extends Entity, T extends Dto> {

    T convert(S s);
}
