package org.slsale.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.HtmlEncode;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.pojo.Affiche;
import org.slsale.pojo.Information;
import org.slsale.pojo.User;
import org.slsale.service.AfficheService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class AfficheController {
	
	private Logger logger = Logger.getLogger(AfficheController.class);
	@Resource
	private AfficheService afficheService;
	
	// /informanage/affiche.html 公告管理页面
	@RequestMapping("/informanage/affiche.html")
	public ModelAndView affiche(HttpSession session,Model model,
								@RequestParam(value = "p",required = false) Integer currentpage){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			Affiche affiche = new Affiche();
			PageSupport page = new PageSupport();
			List<Affiche> afficheList = null;
			page.setPageSize(5);
			try {
				page.setTotalCount(afficheService.count(affiche));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if(page.getTotalCount() > 0){
				if(currentpage != null)
					page.setPage(currentpage);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				// 将分页查询的两个信息封装到user中
				affiche.setPageSize(page.getPageSize());//默认值是2
				affiche.setStarNum((page.getPage() - 1) * page.getPageSize());
				
				try {
					afficheList = afficheService.getAffiches(affiche);
				} catch (Exception e) {
					afficheList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(afficheList);
			}else{
				page.setItems(null);
			}
			model.addAttribute("page", page);
			return new ModelAndView("/informanage/affiche");
		}
	}
	
	//   /informanage/addAffiche.html  添加公告
	@RequestMapping("/informanage/addAffiche.html")
	public ModelAndView addAffiche(HttpSession session,
								@ModelAttribute("addAffiche")Affiche addAffiche){
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		addAffiche.setPublisher(user.getLoginCode());
		addAffiche.setPublishTime(new Date());
		if(null != addAffiche.getTitle() && !addAffiche.getTitle().equals("")){
			addAffiche.setTitle(HtmlEncode.htmlEncode(addAffiche.getTitle()));
		}
		try {
			afficheService.addAffiche(addAffiche);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/informanage/affiche.html");
	}
	
	//  /informanage/viewAffiche.html 查看单个公告的详情信息
	@RequestMapping(value = "/informanage/viewAffiche.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String viewAffiche(@RequestParam(value = "id", required = false)String id){
		String cjson = "";
		if(id == null || "".equals(id)){
			return "nodata";
		}else{
			try {
				Affiche affiche = new Affiche();
				affiche.setId(Integer.valueOf(id));
				affiche = afficheService.getAfficheById(affiche);
				if(null != affiche && affiche.getCode() != null && affiche.getTitle() != null){
					affiche.setTitle(HtmlEncode.htmlDecode(affiche.getTitle()));
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					cjson =  JSONObject.fromObject(affiche,jsonConfig).toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		return cjson;
	}
	//  /informanage/modifyAffiche.html  保存修改公告信息内容
	@RequestMapping("/informanage/modifyAffiche.html")
	public ModelAndView modifyAffiche(HttpSession session,
									@ModelAttribute("modifyAffiche")Affiche modifyAffiche){
		try {
			User sessionUser =  ((User)session.getAttribute(Constants.SESSION_USER));
			modifyAffiche.setPublisher(sessionUser.getLoginCode());
			modifyAffiche.setPublishTime(new Date(System.currentTimeMillis()));
			if(null != modifyAffiche.getTitle() && !modifyAffiche.getTitle().equals("")){
				modifyAffiche.setTitle(HtmlEncode.htmlEncode(modifyAffiche.getTitle()));
			}
			afficheService.modifyAffiche(modifyAffiche);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/informanage/affiche.html");
	}
	
	//   /informanage/delAffiche.html  删除
	@RequestMapping("/informanage/delAffiche.html")
	@ResponseBody
	public String delAffiche(@RequestParam(value = "id", required = false)String id){
		if(id == null && "".equals(id)){
			return "nodata";
		}else{
			Affiche affiche = new Affiche();
			affiche.setId(Integer.valueOf(id));
			try {
				afficheService.delAffiche(affiche);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	// 在首页main.jsp中有一个公告栏，点击more时，加载出公告列表
	@RequestMapping("/informanage/portalafficheList.html")
	public ModelAndView afficheList(HttpSession session,Model model,@RequestParam(value="p",required=false)Integer p){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			affiche(session,model,p);
		}
		return new ModelAndView("informanage/portalaffichelist");
	}
	
	//在首页的公告列表中点击列表标题，到详细信息页面
	@RequestMapping(value="/informanage/portalAfficheDetail.html", produces = {"text/html;charset=UTF-8"})
	public ModelAndView viewAffiche(HttpSession session,@RequestParam Integer id,Model model){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			if(null == id || "".equals(id)){
				id = 0;
			}else{
				try {
					Affiche affiche = new Affiche();
					affiche.setId(id);
					affiche = afficheService.getAfficheById(affiche);
					if(null != affiche && affiche.getCode() != null){
						model.addAttribute("affiche", affiche);
					}
				} catch (Exception e) {
				}
			}
		}
		return new ModelAndView("informanage/portalaffichedetail");
	}
}
