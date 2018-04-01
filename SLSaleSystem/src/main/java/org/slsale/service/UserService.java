package org.slsale.service;

import java.util.List;

import org.slsale.pojo.Role;
import org.slsale.pojo.User;

public interface UserService {

	//根据loginCode，password查询
	public User getLoginUser(User user);
	//查询登录名是否存在（加入id可以完成修改用户时的查重功能）
	public Integer loginCodeIsExit(User user);
	//修改
	public int modifyUser(User user);
	//查询总数量
	public int count(User user);
	//查询用户集合
	public List<User> getUserList(User user);
	//添加
	public int addUser(User user);
	//删除证件照片
	public int delUserPic(User user);
	//根据Id得到用户详细信息
	public User getUserById(User user);
	//删除用户
	public int deleteUser(User user);
	//根据loginCode查询出具体用户信息
	public List<User> getUserByLoginCode(User user);
}
