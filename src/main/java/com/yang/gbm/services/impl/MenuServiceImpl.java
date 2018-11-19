package com.yang.gbm.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yang.gbm.models.Menu;
import com.yang.gbm.repositories.JdbcRepo;
import com.yang.gbm.repositories.MenuRepo;
import com.yang.gbm.services.MenuService;
import com.yang.gbm.utils.AccountUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuRepo menuRepo;
	
	@Autowired
	private JdbcRepo jdbcRepo;

	@Override
	public List<Map<String,Object>> getAllMenu() {
		List<Map<String,Object>> result = new ArrayList<>();
		
		List<Map<String,Object>> list = jdbcRepo.queryForList("select m.* from menu m ");
		for(Map<String,Object> m : list) {
			m.put("parentId", m.get("parent_id"));
			if(m.get("parent_id") == null || m.get("parent_id").toString().equals("0")) {
				result.add(m);
				setMenuChildren(m,list);
			}
		}
		
		return result;
	}

	@Override
	public List<Map<String,Object>> getMenuByCurrentUser() {
		List<Map<String,Object>> result = new ArrayList<>();
		long id = AccountUtils.getCurrentUser().getId();
		
		
		List<Map<String,Object>> list = jdbcRepo.queryForList("select m.* from menu m ,menu_role mr,user_role ur "
				+ "where m.id = mr.menu_id and mr.role_id= ur.role_id and m.usable = 1 and ur.user_id = ?", id);
		for(Map<String,Object> m : list) {
			if(m.get("parent_id") == null || m.get("parent_id").toString().equals("0")) {
				result.add(m);
				setMenuChildren(m,list);
			}
		}
		
		return result;
	}
	
	private void setMenuChildren(Map<String,Object> menu, List<Map<String,Object>> list) {
		menu.put("parentId", menu.get("parent_id"));
		List<Map<String,Object>> children = new ArrayList<>();
		for(Map<String,Object> m : list) {
			if(m.get("parent_id") != null && m.get("parent_id").toString().equals(menu.get("id").toString())) {
				children.add(m);
				setMenuChildren(m,list);
			}
		}
		menu.put("children", children);
	}

	@Override
	public Menu addMenu(Menu menu) {
		return menuRepo.save(menu);
	}

	@Override
	public Menu updateMenu(Menu menu) {
		return menuRepo.save(menu);
	}

}
