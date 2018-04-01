package org.slsale.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.RoleService;
import org.slsale.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

@Controller
public class RoleController {

	private Logger logger = Logger.getLogger(RoleController.class);
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;

	// 角色信息，不分页不模糊查
	@RequestMapping("/backend/rolelist.html")
	public ModelAndView rolelist(HttpSession session, Model model) {
		String menuList = (String) session.getAttribute(Constants.SESSION_BASE_MODEL);
		if (menuList == null) {
			return new ModelAndView("redirect:/");
		} else {
			List<Role> roleList = roleService.getRoleList();
			model.addAttribute("roleList", roleList);
			return new ModelAndView("/backend/rolelist");
		}
	}

	@RequestMapping(value = "/backend/addRole.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String addRole(HttpSession session, @RequestParam String role) {
		logger.debug("addRole=====================");
		String result = "failed";
		if (role != null && !"".equals(role)) {
			JSONObject jsonObject = JSONObject.fromObject(role);
			Role addRole = (Role) jsonObject.toBean(jsonObject, Role.class);
			if (roleService.getRoleRename(addRole) != null && roleService.getRoleRename(addRole).size() > 0) {
				result = "rename";
			}else{
				addRole.setCreateDate(new Date());
				addRole.setIsStart(1);
				addRole.setCreatedBy(((User) session.getAttribute(Constants.SESSION_USER)).getLoginCode());
				roleService.addRole(addRole);
				result = "success";
			}
		} else {
			result = "nodata";
		}
		return result;
	}
	
	@RequestMapping(value = "/backend/modifyRole.html" , produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String modifyRole(HttpSession session,@RequestParam String role){
		logger.debug("modifyRole=====================");
		String result = "failed";
		if(role == null || "".equals(role)){
			result = "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(role);
			Role mRole =  (Role)JSONObject.toBean(roleObject, Role.class);
			if(mRole.getRoleName() == null || mRole.getRoleCode() == null){
				roleService.modifyRole(mRole);
				result = "success";
			}else{
				if(roleService.getRoleRename(mRole) != null && roleService.getRoleRename(mRole).size() >0){
					result = "rename";
				}else{
					mRole.setCreatedBy(((User) session.getAttribute(Constants.SESSION_USER)).getLoginCode());
					roleService.modifyRoleAndUser(mRole);
					result = "success";
				}
			}
		}
		return result;
	}
	
	@RequestMapping("/backend/delRole.html")
	@ResponseBody
	public String delRole(@RequestParam String role){
		logger.debug("delRole=====================");
		String result = "failed";
		if(role == null || "".equals(role)){
			result = "nadata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(role);
			Role dRole =  (Role)JSONObject.toBean(roleObject, Role.class);
			//删除之前，先查询用户表中是否有用户使用该角色
			User user = new User();
			user.setRoleId(dRole.getId());
			List<User> userList = userService.getUserList(user);
			if(userList == null || userList.size() == 0){
				roleService.deleteRole(dRole);
				result = "success";
			}else{
				String flag = "";
				for(int i = 0; i < userList.size(); i++){
					flag += userList.get(i).getLoginCode();
					flag += ","; 
				}
				result = flag;
			}
		}
		return result;
	}
}
