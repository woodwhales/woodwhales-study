package cn.woodwhales.httpclient.mapstruct.converter;

/**
 * @author woodwhales
 * @date 2020-12-24 21:09
 */
public interface DtoConverter<S extends Dto, T extends Vo, T2 extends Vo> {

    T convert(S s);

    T2 convertToSimpleVo(S s);
}
