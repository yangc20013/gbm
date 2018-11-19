package com.yang.gbm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yang.gbm.models.Role;
import com.yang.gbm.models.User;


public interface UserService {
	Page<User> getUsersByPage(Pageable pageable);

	User addUser(User user);

	User getUserById(long id);

	User updateUser(User user);

	void deleteUser(Long id);

	List<Role> getRoleByUserId(Long id);

	List<Role> getAllRoles();

	void setUserRoles(Long userId, Long[] roleIds);
	
	Role addRole(Role role);

	Role updateRole(Role role);

	void deleteRole(Long id);

	Long[] getMenuIdsByRoleId(Long id);

	void setRoleMenus(Long roleId, Long[] menuIds);

}
