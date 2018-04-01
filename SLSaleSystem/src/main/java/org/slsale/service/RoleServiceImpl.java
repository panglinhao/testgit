package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.RoleMapper;
import org.slsale.mapper.UserMapper;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;
	@Resource
	private UserMapper userMapper;
	
	@Override
	public List<Role> getRoleList() {
		return roleMapper.getRoleList();
	}

	@Override
	public int modifyRole(Role role) {
		//需要事务支持，修改完角色名称的时候，需要将对应用户的角色名称也修改
		return roleMapper.modifyRole(role);
	}

	@Override
	public int addRole(Role role) {
		return roleMapper.addRole(role);
	}

	@Override
	public int deleteRole(Role role) {
		return roleMapper.deleteRole(role);
	}

	@Override
	public List<Role> getRoleRename(Role role) {
		return roleMapper.getRoleRename(role);
	}

	@Override
	public List<Role> getRoleListByIsStart() {
		return roleMapper.getRoleListByIsStart();
	}

	
	@Override
	public boolean modifyRoleAndUser(Role role) {
		roleMapper.modifyRole(role);
		if(role.getRoleName() != null){
			User user = new User();
			user.setRoleId(role.getId());
			user.setRoleName(role.getRoleName());
			userMapper.modifyRoleName(user);
			return true;
		}
		return false;
	}

}
