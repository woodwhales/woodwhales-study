package cn.woodwhales.entity.demo2;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {

	private String name;

	private List<ButtonEntity> buttons;
	
}
