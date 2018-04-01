package org.slsale.controller;

import java.io.File;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.DataDictionaryService;
import org.slsale.service.RoleService;
import org.slsale.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private DataDictionaryService dataDictionaryService;

	@RequestMapping("/backend/modifyPwd.html")
	@ResponseBody
	public String modifyPwd(HttpSession session, @RequestParam String userJson) {
		logger.debug("modifyPwd userJson============" + userJson);
		User sessionUser = (User) session.getAttribute(Constants.SESSION_USER);
		if (userJson == null || "".equals(userJson)) {
			return "nodata";
		} else {
			JSONObject jsonObject = JSONObject.fromObject(userJson);
			User user = (User) JSONObject.toBean(jsonObject, User.class);
			user.setId(sessionUser.getId());
			user.setLoginCode(sessionUser.getLoginCode());
			try {
				if (userService.getLoginUser(user) != null) {
					user.setPassword(user.getPassword2());
					user.setPassword2(null);
					userService.modifyUser(user);
				} else {
					return "oldpwdwrong";
				}
			} catch (Exception e) {
				return "failed";
			}
		}
		return "success";
	}

	/**
	 * 获取用户列表（分页查询,也可能模糊查询）
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/backend/userlist.html")
	public ModelAndView userList(HttpSession session, Model model,
			@RequestParam(value = "currentpage", required = false) Integer currentpage,
			@RequestParam(value = "s_referCode", required = false) String s_referCode,
			@RequestParam(value = "s_loginCode", required = false) String s_loginCode,
			@RequestParam(value = "s_roleId", required = false) String s_roleId,
			@RequestParam(value = "s_isStart", required = false) String s_isStart) {
		@SuppressWarnings("unchecked")
		String menuList = (String) session.getAttribute(Constants.SESSION_BASE_MODEL);
		if (menuList == null) {
			return new ModelAndView("redirect:/");
		} else {
			// 获取roleList and cardTypeList (数据字典)
			List<Role> roleList = roleService.getRoleListByIsStart();
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("CARD_TYPE");
			List<DataDictionary> cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			// 封装查询的条件user
			User user = new User();
			if (null != s_referCode) {
				user.setReferCode("%" + SQLTools.transfer(s_referCode) + "%");
			}
			if (null != s_loginCode) {
				user.setLoginCode("%" + SQLTools.transfer(s_loginCode) + "%");
			}
			if (!StringUtils.isNullOrEmpty(s_roleId)) {
				user.setRoleId(Integer.valueOf(s_roleId));
			} else {
				user.setRoleId(null);
			}
			if (!StringUtils.isNullOrEmpty(s_isStart)) {
				user.setIsStart(Integer.valueOf(s_isStart));
			} else {
				user.setIsStart(null);
			}
			// 分页查询的参数PageSupport
			PageSupport page = new PageSupport();
			try {
				page.setTotalCount(userService.count(user));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (currentpage != null)
					page.setPage(currentpage);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				// 将分页查询的两个信息封装到user中
				user.setPageSize(page.getPageSize());
				user.setStarNum((page.getPage() - 1) * page.getPageSize());

				List<User> userList = null;
				try {
					userList = userService.getUserList(user);
				} catch (Exception e) {
					userList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(userList);
			} else {
				page.setItems(null);
			}
			model.addAttribute("page", page);
			model.addAttribute("roleList", roleList);
			model.addAttribute("cardTypeList", cardTypeList);
			model.addAttribute("s_loginCode", s_loginCode);
			model.addAttribute("s_referCode", s_referCode);
			model.addAttribute("s_isStart", s_isStart);
			model.addAttribute("s_roleId", s_roleId);
			return new ModelAndView("/backend/userlist");
		}
	}

	@RequestMapping(value = "/backend/loadUserTypeList.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object loadUserTypeList(@RequestParam(value = "s_roleId", required = false) String s_roleId) {
		String cjson = "";
		try {
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("USER_TYPE");
			List<DataDictionary> userTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			JSONArray jo = JSONArray.fromObject(userTypeList);
			cjson = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cjson;
	}

	@RequestMapping(value = "/backend/logincodeisexit.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String logincodeisexit(@RequestParam(value = "loginCode", required = false) String loginCode,
			@RequestParam(value = "id", required = false) String id) {
		logger.debug("plh logincodeexit logincode========" + loginCode);
		logger.debug("plh logincodeexit id===============" + id);
		String result = "failed";
		User user = new User();
		user.setLoginCode(loginCode);
		if (!id.equals(-1)) // -1代表的是增加的操作
			user.setId(Integer.valueOf(id));

		try {
			if (userService.loginCodeIsExit(user) == 0) {
				result = "only";
			} else {
				result = "repeat";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/backend/upload.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object upload(@RequestParam(value = "a_fileInputID", required = false) MultipartFile cardFile,
			@RequestParam(value = "a_fileInputBank", required = false) MultipartFile bankFile,
			@RequestParam(value = "m_fileInputID", required = false) MultipartFile mCardFile,
			@RequestParam(value = "m_fileInputBank", required = false) MultipartFile mBankFile,
			@RequestParam(value = "loginCode", required = false) String loginCode, HttpServletRequest request,
			HttpSession session) {
		// 增加和修改公用的上传图片的方法
		logger.debug("----------------上传图片开始------------------");
		// 根据服务器的操作系统，自动获取上传文的物理路径，自动适应各个操作系统的路径
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
		logger.debug("SL  path=========" + path);
		// 在数据字典中查找上传文件的最大容量
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("PERSONALFILE_SIZE");
		List<DataDictionary> list = dataDictionaryService.getDataDictionaries(dataDictionary);

		int filesize = 50000;
		if (list != null) {
			if (list.size() == 1) {
				filesize = Integer.valueOf(list.get(0).getValueName());
			}
		}

		if (cardFile != null) {
			String oldFileName = cardFile.getOriginalFilename();// 获取文件名
			// String suffix =
			// oldFileName.substring(oldFileName.lastIndexOf("."));
			String suffix = FilenameUtils.getExtension(oldFileName);// 获取文件后缀名
			logger.debug("plh cardFile suffix===========" + suffix);
			if (cardFile.getSize() > filesize) {
				// 文件大小超过50000
				return "1";
			} else if (suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")) {
				// 文件格式正确,给文件生成新名字
				String newFileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_IDcard.jpg";
				logger.debug("plh new newFileName======== " + cardFile.getName());
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 上传保存
				try {
					cardFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath() + "/statics/uploadfiles/" + newFileName;
				return url;
			} else {
				return "2";
			}
		}

		if (bankFile != null) {
			String oldFileName = bankFile.getOriginalFilename();// 获取文件名
			// String suffix =
			// oldFileName.substring(oldFileName.lastIndexOf("."));
			String suffix = FilenameUtils.getExtension(oldFileName);// 获取文件后缀名
			logger.debug("plh cardFile suffix===========" + suffix);
			if (bankFile.getSize() > filesize) {
				// 文件大小超过50000
				return "1";
			} else if (suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")) {
				// 文件格式正确,给文件生成新名字
				String newFileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_bank.jpg";
				logger.debug("plh new newFileName======== " + bankFile.getName());
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 上传保存
				try {
					bankFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath() + "/statics/uploadfiles/" + newFileName;
				return url;
			} else {
				return "2";
			}
		}

		if (mCardFile != null) {
			String oldFileName = mCardFile.getOriginalFilename();// 获取文件名
			// String suffix =
			// oldFileName.substring(oldFileName.lastIndexOf("."));
			String suffix = FilenameUtils.getExtension(oldFileName);// 获取文件后缀名
			logger.debug("plh cardFile suffix===========" + suffix);
			if (mCardFile.getSize() > filesize) {
				// 文件大小超过50000
				return "1";
			} else if (suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")) {
				// 文件格式正确,给文件生成新名字
				String newFileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_IDcard.jpg";
				logger.debug("plh new newFileName======== " + mCardFile.getName());
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 上传保存
				try {
					mCardFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath() + "/statics/uploadfiles/" + newFileName;
				return url;
			} else {
				return "2";
			}
		}

		if (mBankFile != null) {
			String oldFileName = mBankFile.getOriginalFilename();// 获取文件名
			// String suffix =
			// oldFileName.substring(oldFileName.lastIndexOf("."));
			String suffix = FilenameUtils.getExtension(oldFileName);// 获取文件后缀名
			logger.debug("plh cardFile suffix===========" + suffix);
			if (mBankFile.getSize() > filesize) {
				// 文件大小超过50000
				return "1";
			} else if (suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")) {
				// 文件格式正确,给文件生成新名字
				String newFileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_bank.jpg";
				logger.debug("plh new newFileName======== " + mBankFile.getName());
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 上传保存
				try {
					mBankFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath() + "/statics/uploadfiles/" + newFileName;
				return url;
			} else {
				return "2";
			}
		}
		return null;
	}

	@RequestMapping(value = "/backend/delpic.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String delPic(@RequestParam(value = "picpath", required = false) String picpath,
			@RequestParam(value = "id", required = false) String id, HttpServletRequest request, HttpSession session) {
		String result = "failed";
		if (picpath == null || "".equals(picpath)) {
			return "success";
		} else {
			// picpath：传过来的网络路径，需要解析成物理路径
			String[] paths = picpath.split("/");
			String path = request.getSession().getServletContext()
					.getRealPath(paths[2] + File.separator + paths[3] + File.separator + paths[4]);
			File file = new File(path);
			if (file.exists()) {
				if (file.delete()) {
					if (id.equals("0")) {// 添加用户时，删除上传的图片
						result = "success";
					} else {// 修改用户时，删除上传的图片
						User _user = new User();
						_user.setId(Integer.valueOf(id));
						if (picpath.indexOf("_IDcard.jpg") != -1) {
							_user.setIdCardPicPath(picpath);
						}
						if (picpath.indexOf("_bank.jpg") != -1) {
							_user.setBankPicPath(picpath);
						}
						try {
							if (userService.delUserPic(_user) > 0) {
								logger.debug("plh modify ----------- delPicPath success===============");
								result = "success";
							}
						} catch (Exception e) {
							e.printStackTrace();
							return result;
						}
					}
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/backend/adduser.html", method = RequestMethod.POST)
	public ModelAndView addUser(HttpSession session, @ModelAttribute("addUser") User addUser) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			try {
				String idCard = addUser.getIdCard();
				String ps = idCard.substring(idCard.length() - 6);
				addUser.setPassword(ps);
				addUser.setPassword2(ps);
				addUser.setCreateTime(new Date());
				addUser.setReferId(((User) session.getAttribute(Constants.SESSION_USER)).getId());
				addUser.setReferCode(((User) session.getAttribute(Constants.SESSION_USER)).getLoginCode());
				addUser.setLastUpdateTime(new Date());

				userService.addUser(addUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/userlist.html");
		}
	}

	@RequestMapping(value = "/backend/getuser.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object getuser(@RequestParam(value = "id", required = false) String id) {
		String cjson = "";
		if(id == null || "".equals(id)){
			return "nodata";
		}else{
			try {
				User user = new User();
				user.setId(Integer.valueOf(id));
				user = userService.getUserById(user);
				//user对象里有日期，所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(user,jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return cjson;
		}
	}
	

	@RequestMapping(value = "/backend/modifyuser.html",method=RequestMethod.POST)
	public ModelAndView modifyUser(HttpSession session,@ModelAttribute("modifyUser") User modifyUser){
		logger.debug("modifyPwd ================ ");
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				modifyUser.setLastUpdateTime(new Date());
				userService.modifyUser(modifyUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/backend/userlist.html");
	}
	
	@RequestMapping("/backend/deluser.html")
	@ResponseBody
	public String deluser(@RequestParam(value="delId",required = false) String delId,
						@RequestParam(value="delIdCardPicPath",required = false) String delIdCardPicPath,
						@RequestParam(value="delBankPicPath",required = false) String delBankPicPath,
						@RequestParam(value="delUserType",required = false) String delUserType,
						HttpSession session,HttpServletRequest request){
		String result = "failed";
		User deluser = new User();
		deluser.setId(Integer.valueOf(delId));
		//若被删除的用户为：普通消费会员、VIP会员、加盟店  则不可被删除
		if(delUserType.equals("2") || delUserType.equals("3") || delUserType.equals("4")){
			result = "noallow";
		}else{
			//删除的时候要先删除文件夹中的图片信息,然后删除用户信息
			if(this.delPic(delIdCardPicPath, delId, request, session).equals("success") && 
					this.delPic(delBankPicPath, delId, request, session).equals("success")){
				if(userService.deleteUser(deluser) != 0){
					result= "success";
				}
			}
		}
		return result;
	}
	
	/**
	 * 角色管理
	 * 
	 */
	
	// /member/registmember.html 注册新会员页面
	
	@RequestMapping("/member/registmember.html")
	public ModelAndView registmember(Model model){
		//cardTypeList
		DataDictionary dataDictionary = new DataDictionary();
		List<DataDictionary> cardTypeList = null;
		dataDictionary.setTypeCode("CARD_TYPE");
		try {
			cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("cardTypeList", cardTypeList);
		return new ModelAndView("/member/registmember");
	}
	
	//  /member/registrule.html 加载商城规则页面
	@RequestMapping("/member/registrule.html")
	public ModelAndView registrule(HttpSession session){
		Object baseModel = session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			return new ModelAndView("/member/registrule");
		}
	}
	
	//  /member/saveregistmember.html 注册的新会员
	@RequestMapping(value = "/member/saveregistmember.html", method = RequestMethod.POST)
	public ModelAndView saveregistmember(HttpSession session,@ModelAttribute("registMember") User registMember){
		try {
			registMember.setRoleId(2);//会员
			registMember.setRoleName("会员");
			registMember.setUserType("1");
			registMember.setUserTypeName("注册会员");//第一次注册会员均为注册会员
			String idCard =registMember.getIdCard();
			String ps = idCard = idCard.substring(idCard.length()-6);//截取后六位
			registMember.setPassword(ps);//初始密码为证件号后六位
			registMember.setPassword2(ps);
			registMember.setCreateTime(new Date());
			User sessionUser = (User) session.getAttribute(Constants.SESSION_USER);
			registMember.setReferId(sessionUser.getId());
			registMember.setReferCode(sessionUser.getLoginCode());
			registMember.setLastUpdateTime(new Date());
			
			userService.addUser(registMember);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/member/registmember.html");//失败去注册会员页面
		}
		return new ModelAndView("redirect:/member/registok.html?loginCode="+registMember.getLoginCode());//成功去成功页面
	}
	
	//  /member/registok.html  注册成功页面
	@RequestMapping("/member/registok.html")
	public ModelAndView registok(HttpSession session, Model model,
								@RequestParam(value = "loginCode", required = false) String loginCode){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			User registUser = new User();
			registUser.setLoginCode(loginCode);
			try {
				List<User> list =userService.getUserByLoginCode(registUser);
				if(list != null){
					registUser = list.get(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
				registUser = null;
			}
			model.addAttribute("registUser",registUser);
			return new ModelAndView("/member/registok");
		}
	}
	
	//   /member/memberlist.html  新会员管理,分页，只要注册会员userType=1
	@RequestMapping("/member/memberlist.html")
	public ModelAndView memberlist(HttpSession session,Model model,
								@RequestParam(value = "currentpage", required = false) Integer currentpage,
			      				@RequestParam(value = "s_loginCode", required = false) String s_loginCode,
			      				@RequestParam(value = "s_userName", required = false) String s_userName){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			//获取cardTypeList和userTypeList
			List<DataDictionary> cardTypeList = null;
			List<DataDictionary> userTypeList = null;
			try {
				DataDictionary dataDictionary = new DataDictionary();
				dataDictionary.setTypeCode("CARD_TYPE");
				cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
				dataDictionary.setTypeCode("USER_TYPE");
				userTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e) {
				e.printStackTrace();
			}
			User user = new User();
			if(null != s_loginCode)
				user.setLoginCode("%"+SQLTools.transfer(s_loginCode)+"%");
			if(null != s_userName)
				user.setUserName("%"+SQLTools.transfer(s_userName)+"%");
			user.setReferId(((User)session.getAttribute(Constants.SESSION_USER)).getId());
			user.setUserType("1");
			PageSupport page = new PageSupport();
			try {
				page.setTotalCount(userService.count(user));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (currentpage != null)
					page.setPage(currentpage);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				// 将分页查询的两个信息封装到user中
				user.setPageSize(page.getPageSize());
				user.setStarNum((page.getPage() - 1) * page.getPageSize());

				List<User> userList = null;
				try {
					userList = userService.getUserList(user);
				} catch (Exception e) {
					userList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(userList);
			} else {
				page.setItems(null);
			}
			model.addAttribute("s_loginCode", s_loginCode);
			model.addAttribute("s_userName", s_userName);
			model.addAttribute("cardTypeList", cardTypeList);
			model.addAttribute("userTypeList", userTypeList);
			model.addAttribute("page", page);
			return new ModelAndView("/member/memberlist");
		}
	}
	
	// /member/modifymember.html  修改会员信息
	@RequestMapping(value = "/member/modifymember.html", method = RequestMethod.POST)
	public ModelAndView modifymember(@ModelAttribute("modifyUser") User modifyUser){
		modifyUser.setLastUpdateTime(new Date());
		try {
			userService.modifyUser(modifyUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/member/memberlist.html");//修改成功后反回列表页面
	}
	
	
	//   /member/modifypersonalinfo.html 跳转到修改本人资料页面
	@RequestMapping("/member/modifypersonalinfo.html")
	public ModelAndView modifypersonalinfo(HttpSession session,Model model){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			// cardTypeList
			DataDictionary dataDictionary = new DataDictionary();
			List<DataDictionary> cardTypeList = null;
			try {
				dataDictionary.setTypeCode("CARD_TYPE");
				cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e) {
				e.printStackTrace();
			}
			User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
			model.addAttribute("cardTypeList", cardTypeList);
			model.addAttribute("currentUser", currentUser);
			return new ModelAndView("/member/modifypersonalinfo");
		}
	}
	
	//   /member/savepersonalinfo.html  修改本人资料
	@RequestMapping(value = "/member/savepersonalinfo.html", method = RequestMethod.POST)
	public ModelAndView savepersonalinfo(HttpSession session,@ModelAttribute("modifyUser") User sessionUser){
		try {
			sessionUser.setLastLoginTime(new Date());
			userService.modifyUser(sessionUser);
			//本人信息修改完成之后，将session中的user信息重新设置
			session.setAttribute(Constants.SESSION_USER, userService.getUserById(sessionUser));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/member/modifypersonalinfo.html");//成功后返回原来页面
	}
	
	//   /member/modifypersonalpwd.html 跳转到修改密码页面/member/modifypersonalpwd.html
	@RequestMapping("/member/modifypersonalpwd.html")
	public ModelAndView modifypersonalpassword(HttpSession session){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			return new ModelAndView("/member/modifypersonalpwd");
		}
	}
	
	
	//   /member/savesecondpwd.html  修改本人2级密码
	@RequestMapping("/member/savesecondpwd.html")
	@ResponseBody
	public String savesecondpwd(HttpSession session,
								@RequestParam(value = "userJson", required = false)String userJson){
		if(userJson == null || "".equals(userJson)){
			return "nodata";
		}else{
			JSONObject jsonObject = JSONObject.fromObject(userJson);
			User user = (User) JSONObject.toBean(jsonObject,User.class);
			user.setLastUpdateTime(new Date());
			try {
				String password2 = ((User)session.getAttribute(Constants.SESSION_USER)).getPassword2();
				if(!user.getPassword().equals(password2)){
					return "oldpwdwrong";
				}
				user.setPassword(null);
				user.setId(((User)session.getAttribute(Constants.SESSION_USER)).getId());
				userService.modifyUser(user);
				return "success";
			} catch (Exception e) {
				return "failed";
			}
		}
	}
}
