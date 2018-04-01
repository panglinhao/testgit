package org.slsale.mapper;

import java.util.List;

import org.slsale.pojo.Role;

public interface RoleMapper {
	//获取角色列表
	public List<Role> getRoleList();
	
	//修改角色信息
	public int modifyRole(Role role);
	
	//增加角色
	public int addRole(Role role);
	
	//删除角色
	public int deleteRole(Role role);
	
	//查询角色名是否重复
	public List<Role> getRoleRename(Role role);
	
	//获取启用状态的roleList,给用户列表用
	public List<Role> getRoleListByIsStart();
} 
