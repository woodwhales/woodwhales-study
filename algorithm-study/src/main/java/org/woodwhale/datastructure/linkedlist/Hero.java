package org.woodwhale.datastructure.linkedlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hero {
	private Integer id;
	private String name;
	
	@Override
	public String toString() {
		return "Hero [id=" + id + ", name=" + name + "]";
	}
}
