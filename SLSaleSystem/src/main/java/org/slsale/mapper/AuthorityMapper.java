package org.slsale.mapper;


import org.slsale.pojo.Authority;

public interface AuthorityMapper {
	//授权
	public int addAuthority(Authority authority);
	//移除权限
	public int deleteAuthority(Authority authority);
	//修改
	public int modifyAuthority(Authority authority);
	//根据roleId和functionId查询对应角色是否具有此权限
	public Authority getAuthorityDefault(Authority authority);
}
