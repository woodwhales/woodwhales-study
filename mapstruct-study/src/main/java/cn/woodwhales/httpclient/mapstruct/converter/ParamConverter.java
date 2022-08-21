package cn.woodwhales.httpclient.mapstruct.converter;

/**
 * @author woodwhales
 * @date 2020-12-24 21:46
 */
public interface ParamConverter<S extends Param, T extends Entity> {

    T convert(S s);
}
