package org.slsale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.User;
import org.slsale.service.FunctionService;
import org.slsale.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);
	@Resource
	private UserService userService;
	@Resource
	private FunctionService functionService;
	@Resource
	private RedisAPI redisAPI;

	@RequestMapping("/login.html")
	@ResponseBody
	public Object login(HttpSession session, @RequestParam String user) {
		logger.debug("login===================");
		if (user == null) {
			return "nodata";
		} else {
			JSONObject jsonObject = JSONObject.fromObject(user);
			User userParam = (User) jsonObject.toBean(jsonObject, User.class);
			try {
				if (userService.loginCodeIsExit(userParam) == 0) {
					// 不存在这个帐号
					return "nologincode";
				} else {
					User currentUser = userService.getLoginUser(userParam);
					if (currentUser != null) {
						// 登陆帐号和密码都正确，保存session,更新登陆时间
						session.setAttribute(Constants.SESSION_USER, currentUser);
						// 更新时只需要id和lastLoginTime，新创建一个user对象效率更高
						User updateLoginTimeUser = new User();
						updateLoginTimeUser.setId(currentUser.getId());
						updateLoginTimeUser.setLastLoginTime(new Date());
						userService.modifyUser(updateLoginTimeUser);
						updateLoginTimeUser = null;
						return "success";
					} else {
						// 密码错误
						return "pwderror";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}

	// 根据登录用户的权限（roleId）加载出对应的菜单列表
	protected List<Menu> getMenuByCurrentUser(int roleId) {
		List<Menu> menuList = new ArrayList<Menu>();
		Authority authority =new Authority();
		authority.setRoleId(roleId);
		List<Function> mainList = functionService.getMainFunctionList(authority);
		if (mainList != null) {
			for (Function function : mainList) {
				Menu menu = new Menu();
				menu.setMainMenu(function);
				function.setRoleId(authority.getRoleId());
				List<Function> subList = functionService.getSubFunctionList(function);
				if (subList != null) {
					menu.setSubMenus(subList);
				}
				menuList.add(menu);
			}
		}
		return menuList;
	}

	@RequestMapping("/main.html")
	@ResponseBody
	public ModelAndView main(HttpSession session, ModelAndView modelAndView) {

		User user = (User) session.getAttribute(Constants.SESSION_USER);
		modelAndView.addObject("user", user);
		List<Menu> mList = null;
		String menuList = null;
		/**
		 * redis中存放数据的格式 key:menuList+roleID---eg:"menuList2" value:mList
		 */
		// redis里有没有数据
		if (!redisAPI.exist("menuList" + user.getRoleId())) {
			// redis中不存在数据,去数据库加载
			// 根据当前用户获取菜单列表mList
			mList = getMenuByCurrentUser(user.getRoleId());
			if (mList != null) {
				// 转化为json字符串，并存放在redis中
				JSONArray jsonArray = JSONArray.fromObject(mList);
				String jsonString = jsonArray.toString();
				menuList=jsonString;
				logger.debug("jsonString===== " + jsonString);
				modelAndView.addObject("mList", jsonString);
				redisAPI.set("menuList" + user.getRoleId(), jsonString);
			}
		}else{
			//直接从redis缓存中拿到数据
			String redisJsonString = redisAPI.get("menuList" + user.getRoleId());
			menuList=redisJsonString;
			logger.debug("mList from redis:  " + redisJsonString);
			if(null != redisJsonString && !"".equals(redisJsonString)){
				modelAndView.addObject("mList", redisJsonString);
			}else{
				modelAndView.setViewName("redirect:/");
				return modelAndView;
			}
		}
		//权限校验所需要的数据
		if(!redisAPI.exist("Role"+user.getRoleId()+"UrlList")){
			Authority authority = new Authority();
			authority.setRoleId(user.getRoleId());
			List<Function> functionList = functionService.getFunctionListByRoleId(authority);
			if (functionList != null && functionList.size() >= 0) {
				StringBuffer sBuffer = new StringBuffer();
				for (Function function : functionList) {
					sBuffer.append(function.getFuncUrl());
				}
				redisAPI.set("Role"+user.getRoleId()+"UrlList", sBuffer.toString());
				logger.debug("url======"+sBuffer.toString());
			}
		}
		session.setAttribute(Constants.SESSION_BASE_MODEL, menuList);
		modelAndView.setViewName("main");
		return modelAndView;
	}
	
	//
	
	@RequestMapping("/logout.html")
	public String logout(HttpSession session) {
		session.removeAttribute(Constants.SESSION_USER);
		session.invalidate();
		return "index";
	}
	
	@RequestMapping("/401.html")
	public String noRole(){
		return "401";
	}
}
