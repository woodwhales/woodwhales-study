package cn.woodwhales.dto.demo2;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {

	private String name;

	private List<ButtonDTO> buttons;
}
