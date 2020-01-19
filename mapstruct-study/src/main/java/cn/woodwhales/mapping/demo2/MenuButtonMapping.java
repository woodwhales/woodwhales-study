package cn.woodwhales.mapping.demo2;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cn.woodwhales.dto.demo2.ButtonDTO;
import cn.woodwhales.dto.demo2.MenuDTO;
import cn.woodwhales.entity.demo2.ButtonEntity;
import cn.woodwhales.entity.demo2.MenuEntity;

@Mapper(componentModel = "spring")
public interface MenuButtonMapping {

	/**
	 * 按钮数据传输对象转按钮实体数据对象
	 * @param menuDTO
	 * @param context
	 * @return
	 */
	MenuEntity toEntity(MenuDTO menuDTO, @Context JpaContext context);

	/**
	 * 菜单数据传输对象转菜单实体数据对象
	 * @param buttonDTO
	 * @param context
	 * @return
	 */
	@Mapping(target = "menu", ignore = true)
    ButtonEntity toEntity(ButtonDTO buttonDTO, @Context JpaContext context);
}
