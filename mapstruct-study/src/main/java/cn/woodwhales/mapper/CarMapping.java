package cn.woodwhales.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cn.woodwhales.dto.CarDTO;
import cn.woodwhales.entity.Car;

/**
 * componentModel属性用于指定自动生成的接口实现类的组件类型
 * 这个属性支持四个值：
 * default: 这是默认的情况，mapstruct不使用任何组件类型, 可以通过Mappers.getMapper(Class)方式获取自动生成的实例对象。
 * cdi: the generated mapper is an application-scoped CDI bean and can be retrieved via @Inject
 * spring: 生成的实现类上面会自动添加一个@Component注解，可以通过Spring的 @Autowired方式进行注入
 * jsr330: 生成的实现类上会添加@javax.inject.Named 和@Singleton注解，可以通过 @Inject注解获取。
 *
 */
@Mapper(componentModel = "spring")
public interface CarMapping {
	
//	 CarMapping CAR_MAPPING = Mappers.getMapper(CarMapping.class);

	/**
	 * 源类型 目标类型 成员变量相同类型 相同变量名 不用写{@link org.mapstruct.Mapping}来映射
	 * @param car
	 * @return
	 */
	@Mapping(target = "type", source = "carType.type")
	@Mapping(target = "seatCount", source = "numberOfSeats")
	CarDTO carToCarDTO(Car car);

}
