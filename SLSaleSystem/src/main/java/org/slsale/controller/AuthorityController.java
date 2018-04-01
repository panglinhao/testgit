package org.slsale.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.Role;
import org.slsale.pojo.RoleFunctions;
import org.slsale.pojo.User;
import org.slsale.service.AuthorityService;
import org.slsale.service.FunctionService;
import org.slsale.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;

@Controller
public class AuthorityController {

	private Logger logger = Logger.getLogger(AuthorityController.class);
	@Resource
	private AuthorityService authorityService;
	@Resource
	private RoleService roleService;
	@Resource
	private FunctionService functionService;
	@Resource
	private RedisAPI redisAPI;
	@Resource
	private LoginController loginController;

	@RequestMapping("/backend/authoritymanage.html")
	public ModelAndView authoritymanage(HttpSession session, ModelAndView modelAndView) {
		// 获取roleList(启用状态的)
		List<Role> roleList = roleService.getRoleListByIsStart();
		modelAndView.addObject("roleList", roleList);
		modelAndView.setViewName("/backend/authoritymanage");
		return modelAndView;
	}

	@RequestMapping(value = "/backend/functions.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String functions() {
		String result = "nodata";
		Function function = new Function();
		function.setId(0);
		List<RoleFunctions> functionList = new ArrayList<RoleFunctions>();
		try {
			List<Function> fList = functionService.getFunctionListById(function);
			if (fList != null) {
				for (Function fun : fList) {
					List<Function> subList = functionService.getFunctionListById(fun);
					RoleFunctions rf = new RoleFunctions();
					rf.setMainFunction(fun);
					rf.setSubFunctions(subList);
					functionList.add(rf);
				}
				JSONArray jsonObject = JSONArray.fromObject(functionList);
				result = jsonObject.toString();
				logger.debug("result==========" + result);
			}
		} catch (Exception e) {
		}
		return result;
	}

	@RequestMapping("/backend/getAuthorityDefault.html")
	@ResponseBody
	public String getAuthorityDefault(@RequestParam(value = "rid") String rid, // roleId
			@RequestParam(value = "fid") String fid) {// functionId
		String result = "failed";
		// 查询角色对应的权限是否开启，开启状态让复选框√，去au_authority表获取
		if (rid != null && fid != null) {
			Authority authority = new Authority();
			authority.setRoleId(Integer.valueOf(rid));
			authority.setFunctionId(Integer.valueOf(fid));
			if (authorityService.getAuthorityDefault(authority) != null) {
				result = "success";
			}
		}
		return result;
	}

	// 修改权限(并把该角色的重新授权写入到redis中去,替换掉userlist加载时的值)---"/backend/modifyAuthority.html"
	@RequestMapping("/backend/modifyAuthority.html")
	@ResponseBody
	public String modifyAuthority(HttpSession session, @RequestParam(value = "ids") String ids) {
		logger.debug("ids========:" + ids);
		String result = "failed";
		// ids是一个roleId和所有选中的复选框funcid
		if (ids == null) {
			result = "nodata";
		} else {
			String[] idArray = StringUtils.split(ids, "-");
			if (idArray.length > 0) {
				// 修改权限
				User user = (User) session.getAttribute(Constants.SESSION_USER);
				authorityService.addAuthority(idArray, user.getLoginCode());
				// 更新redis中内容 menuList+roleId
				int roleId = Integer.valueOf(idArray[0]);
				List<Menu> mList = loginController.getMenuByCurrentUser(roleId);
				JSONArray jsonArray = JSONArray.fromObject(mList);
				redisAPI.set("menuList" + roleId, jsonArray.toString());

				//拦截器使用，相应角色没有权限的不能访问,替换掉登陆时redis中存放的所有url路径
				Authority authority = new Authority();
				authority.setRoleId(roleId);
				List<Function> functionList = functionService.getFunctionListByRoleId(authority);
				if (functionList != null && functionList.size() >= 0) {
					StringBuffer sBuffer = new StringBuffer();
					for (Function function : functionList) {
						sBuffer.append(function.getFuncUrl());
					}
					redisAPI.set("Role"+idArray[0]+"UrlList", sBuffer.toString());
				}
				result = "success";
			}
		}
		return result;
	}
}
