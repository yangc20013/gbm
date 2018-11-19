package com.yang.gbm.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yang.gbm.models.Role;
import com.yang.gbm.models.User;
import com.yang.gbm.repositories.JdbcRepo;
import com.yang.gbm.repositories.RoleRepo;
import com.yang.gbm.repositories.UserRepo;
import com.yang.gbm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private JdbcRepo jdbcRepo;

	@Override
	public Page<User> getUsersByPage(Pageable pageable) {
		return userRepo.findAll(pageable);
	}

	@Override
	public User addUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User getUserById(long id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public User updateUser(User user) {
		User u = userRepo.findById(user.getId()).get();
		u.setUsername(user.getUsername());
		u.setNickName(user.getNickName());
		u.setPhone(user.getPhone());
		u.setTelephone(user.getTelephone());
		u.setEmail(user.getEmail());
		u.setEnabled(user.getEnabled());
		u.setUserType(user.getUserType());
		u.setRemarks(user.getRemarks());
		
		return userRepo.save(u);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepo.findById(id).get();
		user.setEnabled(false);
		userRepo.save(user);
	}

	@Override
	public List<Role> getRoleByUserId(Long id) {
		User user = userRepo.findById(id).get();
		return user.getRoles();
	}

	@Override
	public List<Role> getAllRoles() {
		return IteratorUtils.toList(roleRepo.findAll().iterator());
	}

	@Transactional
	@Override
	public void setUserRoles(Long userId, Long[] roleIds) {
		jdbcRepo.update("delete from user_role where user_id=?", userId);

		String sql = "insert into user_role(user_id,role_id)values(?,?)";
		for (int i = 0; i < roleIds.length; i++) {
			jdbcRepo.update(sql, userId, roleIds[i]);
		}

	}

	@Override
	public Role addRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public Role updateRole(Role role) {
		Role r = roleRepo.findById(role.getId()).get();
		r.setName(role.getName());
		r.setDescription(role.getDescription());
		return r;
	}
	@Transactional
	@Override
	public void deleteRole(Long id) {
		int count = jdbcRepo.queryForObject("select count(1) from user_role where role_id = ?", new Object[] {id}, Integer.class);
		if(count > 0) {
			throw new RuntimeException("删除失败，存在该权限的用户");
		}
		jdbcRepo.update("delete from user_role where role_id = ?",id);
		jdbcRepo.update("delete from menu_role where role_id = ?",id);
		jdbcRepo.update("delete from role where id = ?",id);
		
	}

	@Override
	public Long[] getMenuIdsByRoleId(Long id) {
		List<Map<String, Object>> list = jdbcRepo.queryForList("select menu_id from menu_role where role_id=? ", id);
		Long[] result = new Long[list.size()];
		for(int i=0;i<list.size();i++) {
			result[i] = Long.valueOf(list.get(i).get("menu_id").toString());
		}
		return result;
	}
	@Transactional
	@Override
	public void setRoleMenus(Long roleId, Long[] menuIds) {
		jdbcRepo.update("delete from menu_role where role_id=?", roleId);

		String sql = "insert into menu_role(menu_id,role_id)values(?,?)";
		for (int i = 0; i < menuIds.length; i++) {
			jdbcRepo.update(sql, menuIds[i], roleId);
		}
	}

}
