package org.slsale.service;


import org.slsale.pojo.Authority;


public interface AuthorityService {
	//授予角色权限
	public boolean addAuthority(String[] ids,String createdBy);
	//移除角色权限
	public boolean deleteAuthority(Authority authority, String checkFuncList);
	//修改权限
	public int modifyAuthority(Authority authority);
	//根据roleId和functionId查询对应角色是否具有此权限
	public Authority getAuthorityDefault(Authority authority);
}
