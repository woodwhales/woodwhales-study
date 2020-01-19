package cn.woodwhales.mapping.demo2;

import javax.persistence.EntityManager;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import cn.woodwhales.entity.demo2.ButtonEntity;
import cn.woodwhales.entity.demo2.MenuEntity;

public class JpaContext {

	private final EntityManager em;
	
	private MenuEntity menuEntity;
	
	public JpaContext(EntityManager em) {
        this.em = em;
    }
	
	@BeforeMapping
    public void setEntity(@MappingTarget MenuEntity menuEntity) {
       this.menuEntity = menuEntity;
       // you could do stuff with the EntityManager here
    }
	
	@AfterMapping
    public void establishRelation(@MappingTarget ButtonEntity buttonEntity) {
		buttonEntity.setMenu(menuEntity);
        // you could do stuff with the EntityManager here
    }
}
