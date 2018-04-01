package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.FunctionMapper;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.springframework.stereotype.Service;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Resource
	private FunctionMapper functionMapper;
	@Override
	public List<Function> getMainFunctionList(Authority authority) {
		// TODO Auto-generated method stub
		return functionMapper.getMainFunctionList(authority);
	}

	@Override
	public List<Function> getSubFunctionList(Function function) {
		// TODO Auto-generated method stub
		return functionMapper.getSubFunctionList(function);
	}

	@Override
	public List<Function> getFunctionListById(Function function) {
		return functionMapper.getFunctionListById(function);
	}

	@Override
	public List<Function> getFunctionListByRoleId(Authority authority) {
		return functionMapper.getFunctionListByRoleId(authority);
	}

}
