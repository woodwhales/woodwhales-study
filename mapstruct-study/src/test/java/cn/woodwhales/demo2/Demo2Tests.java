package cn.woodwhales.demo2;

import cn.woodwhales.dto.demo2.ButtonDTO;
import cn.woodwhales.dto.demo2.MenuDTO;
import cn.woodwhales.entity.demo2.MenuEntity;
import cn.woodwhales.mapping.demo2.JpaContext;
import cn.woodwhales.mapping.demo2.MenuButtonMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Demo2Tests {
	
	@Autowired
	private MenuButtonMapping menuButtonMapping;
	
	@Test
	void testMenuButtonMapping() {
		String testMenuName = "菜单1";
		String testButtonName1 = "按钮1";
		String testButtonName2 = "按钮2";
		
		MenuDTO menuDTO = new MenuDTO(testMenuName, Arrays.asList(new ButtonDTO(testButtonName1), new ButtonDTO(testButtonName2)));
	
		JpaContext jpaCtx = new JpaContext( null );
		
		MenuEntity menuEntity = menuButtonMapping.toEntity(menuDTO, jpaCtx);
		
		assertThat(menuEntity).isNotNull();
		assertEquals(testMenuName, menuEntity.getName());
		assertEquals(menuEntity.getButtons().size(), menuDTO.getButtons().size());
		assertEquals(menuEntity.getButtons().get(0).getName(), menuDTO.getButtons().get(0).getName());
		assertEquals(menuEntity.getButtons().get(1).getName(), menuDTO.getButtons().get(1).getName());

		assertEquals(menuEntity.getButtons().get(0).getMenu(), menuEntity);
		assertEquals(menuEntity.getButtons().get(1).getMenu(), menuEntity);
	}

}
