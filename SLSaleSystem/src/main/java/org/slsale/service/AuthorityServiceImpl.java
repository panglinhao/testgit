package org.slsale.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.AuthorityMapper;
import org.slsale.mapper.FunctionMapper;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Resource
	private AuthorityMapper authorityMapper;
	@Resource
	private FunctionMapper functionMapper;

	@Override
	public boolean addAuthority(String[] ids,String createdBy) {
		//权限修改时，先删除角色的所有权限，然后再添加权限
		Authority authority = new Authority();
		authority.setRoleId(Integer.valueOf(ids[0]));
		authorityMapper.deleteAuthority(authority);
		String funids = "";
		for(int i = 1; i < ids.length; i++){
			funids += Integer.parseInt(ids[i])+",";
		}
		if(funids.contains(",")){
			funids = funids.substring(0, funids.lastIndexOf(","));
			//查询出要添加的角色功能
			List<Function> fList = functionMapper.getFunctionListIn(funids);
			if(fList != null && fList.size()>0){
				for (Function function : fList) {
					authority.setCreatedBy(createdBy);
					authority.setCreationTime(new Date());
					authority.setFunctionId(function.getId());
					authority.setUserTypeId(0);
					authorityMapper.addAuthority(authority);
				}
			}
		}
		return true;
	}

	@Override
	public boolean deleteAuthority(Authority authority, String checkFuncList) {
		
		
		authorityMapper.deleteAuthority(authority);
		return true;
				
	}

	@Override
	public int modifyAuthority(Authority authority) {
		return authorityMapper.modifyAuthority(authority);
	}

	@Override
	public Authority getAuthorityDefault(Authority authority) {
		return authorityMapper.getAuthorityDefault(authority);
	}

}
