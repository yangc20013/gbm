package com.yang.gbm.contorllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yang.gbm.models.Menu;
import com.yang.gbm.models.Role;
import com.yang.gbm.models.User;
import com.yang.gbm.models.vo.SearchModel;
import com.yang.gbm.services.MenuService;
import com.yang.gbm.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/sys")
public class SystemController {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/menu/all", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String,Object>>> menuAll() {
		return new ResponseEntity<>(menuService.getAllMenu(),HttpStatus.OK);
	}
	@RequestMapping(value = "/menu/list", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String,Object>>> menuList() {
		return new ResponseEntity<>(menuService.getMenuByCurrentUser(),HttpStatus.OK);
	}
	@RequestMapping(value = "/menu/add", method = RequestMethod.POST)
	public ResponseEntity<Menu> menuAdd(Menu menu) {
		return new ResponseEntity<>(menuService.addMenu(menu),HttpStatus.OK);
	}
	@RequestMapping(value = "/menu/update", method = RequestMethod.POST)
	public ResponseEntity<Menu> menuUpdate(Menu menu) {
		return new ResponseEntity<>(menuService.updateMenu(menu),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public ResponseEntity<Page<User>> userPage(SearchModel model) {
		Pageable pageable = new PageRequest(model.getPageNo() - 1,model.getPageSize());
		return new ResponseEntity<>(userService.getUsersByPage(pageable),HttpStatus.OK);
	}
	@RequestMapping(value = "/user/get", method = RequestMethod.GET)
	public ResponseEntity<User> userGet(Long id) {
		System.out.println("ID: "+ id);
		return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
	}
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public ResponseEntity<User> userAdd(User user) {
		if(StringUtils.isEmpty(user.getPassword())) {// a new user, the default password is '123456'
			user.setPassword("$2a$04$i2QLaxYCmZXRLLcv7MNMU.ZpXQmso.lrEOQmldzKtTXuKI/OY4kA6");
		}
		return new ResponseEntity<>(userService.addUser(user),HttpStatus.OK);
	}
	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Void> userDelete(Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public ResponseEntity<User> userUpdate(User user) {
		return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/roleIds", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> getRoleByUserId(Long id) {
		return new ResponseEntity<>(userService.getRoleByUserId(id),HttpStatus.OK);
	}
	@RequestMapping(value = "/user/setRoles", method = RequestMethod.POST)
	public ResponseEntity<Void> setUserRoles(Long userId, Long[] roleIds) {
		userService.setUserRoles(userId, roleIds);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@RequestMapping(value = "/role/list", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> getRoleList() {
		return new ResponseEntity<>(userService.getAllRoles(),HttpStatus.OK);
	}
	@RequestMapping(value = "/role/delete", method = RequestMethod.GET)
	public ResponseEntity<Void> deleteRole(Long id) {
		checkAdminRole(id);
		userService.deleteRole(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/role/add", method = RequestMethod.POST)
	public ResponseEntity<Role> roleAdd(Role role) {
		Role result = null;
		if(StringUtils.isEmpty(role.getId())) {
			result = userService.addRole(role);
		}else {
			result = userService.updateRole(role);
		}
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	@RequestMapping(value = "/role/menuIds", method = RequestMethod.GET)
	public ResponseEntity<Long[]> getMenuIdsByRoleId(Long id) {
		return new ResponseEntity<>(userService.getMenuIdsByRoleId(id),HttpStatus.OK);
	}
	@RequestMapping(value = "/role/setMenus", method = RequestMethod.POST)
	public ResponseEntity<Void> setRoleMenus(Long roleId, Long[] menuIds) {
		checkAdminRole(roleId);
		userService.setRoleMenus(roleId,menuIds);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private void checkAdminRole(Long roleId) {
		if(roleId < 10) {
			new RuntimeException("不能操作Admin角色");
		}
	}
	
	

}
