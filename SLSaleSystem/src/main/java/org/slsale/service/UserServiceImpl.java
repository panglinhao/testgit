package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.UserMapper;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User getLoginUser(User user) {
		return userMapper.getLoginUser(user);
	}

	@Override
	public Integer loginCodeIsExit(User user) {
		return userMapper.loginCodeIsExit(user);
	}

	@Override
	public int modifyUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.modifyUser(user);
	}

	@Override
	public int count(User user) {
		return userMapper.count(user);
	}

	@Override
	public List<User> getUserList(User user) {
		// TODO Auto-generated method stub
		return userMapper.getUserList(user);
	}

	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.addUser(user);
	}

	@Override
	public int delUserPic(User user) {
		// TODO Auto-generated method stub
		return userMapper.delUserPic(user);
	}

	@Override
	public User getUserById(User user) {
		// TODO Auto-generated method stub
		return userMapper.getUserById(user);
	}

	@Override
	public int deleteUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.deleteUser(user);
	}

	@Override
	public List<User> getUserByLoginCode(User user) {
		// TODO Auto-generated method stub
		return userMapper.getUserByLoginCode(user);
	}


}
