package com.yang.gbm.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.yang.gbm.models.Menu;

public interface MenuRepo extends CrudRepository<Menu, Long>{
	
	List<Menu> findByUsable(boolean usable);

}
