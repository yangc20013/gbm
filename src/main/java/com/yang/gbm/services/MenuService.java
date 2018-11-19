package com.yang.gbm.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yang.gbm.models.Menu;


public interface MenuService {
	List<Map<String,Object>> getAllMenu();

	List<Map<String,Object>> getMenuByCurrentUser();

	Menu addMenu(Menu menu);

	Menu updateMenu(Menu menu);


}
