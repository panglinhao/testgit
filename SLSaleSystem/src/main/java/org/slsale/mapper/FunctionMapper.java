package org.slsale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;

public interface FunctionMapper {

	//查询主菜单
	public List<Function> getMainFunctionList(Authority authority);
	//查询子菜单
	public List<Function> getSubFunctionList(Function function);
	//查询所有功能权限(分类，根据parentId分两次)
	public List<Function> getFunctionListById(Function function);
	//查询角色所需要的权限
	public List<Function> getFunctionListIn(@Param(value="funids")String funids);
	//用来获取角色开启的权限的所有url（两表联查authority和function）。拦截器权限校验用
	public List<Function> getFunctionListByRoleId(Authority authority);
} 
