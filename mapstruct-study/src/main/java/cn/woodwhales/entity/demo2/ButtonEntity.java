package cn.woodwhales.entity.demo2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ButtonEntity {
	
	private String name;
	
    private MenuEntity menu;
    
}
