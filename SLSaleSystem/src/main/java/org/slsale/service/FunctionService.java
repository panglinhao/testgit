package org.slsale.service;

import java.util.List;

import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;

public interface FunctionService {

	//查询主菜单
	public List<Function> getMainFunctionList(Authority authority);
	//查询子菜单
	public List<Function> getSubFunctionList(Function function);
	//查询所有功能权限(分类，根据parentId分两次)
	public List<Function> getFunctionListById(Function function);
	//用来获取角色开启的权限的所有url（两表联查authority和function）。拦截器权限校验用
	public List<Function> getFunctionListByRoleId(Authority authority);
}
