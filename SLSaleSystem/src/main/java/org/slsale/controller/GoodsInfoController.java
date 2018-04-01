package org.slsale.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.GoodsInfo;
import org.slsale.pojo.GoodsPackAffiliated;
import org.slsale.pojo.User;
import org.slsale.service.GoodsInfoService;
import org.slsale.service.GoodsPackAffiliatedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class GoodsInfoController {

	private Logger logger = Logger.getLogger(GoodsInfoController.class);
	@Resource
	private GoodsInfoService goodsInfoService;
	@Resource
	private GoodsPackAffiliatedService gpaService;

	@RequestMapping("/backend/goodsinfolist.html")
	public ModelAndView goodsinfolist(HttpSession session, Model model,
			@RequestParam(value = "currentpage", required = false) Integer currentpage,
			@RequestParam(value = "s_goodsName", required = false) String s_goodsName,
			@RequestParam(value = "s_state", required = false) String s_state) {
		logger.debug("============goodsinfolist============");
		GoodsInfo goodsInfo = new GoodsInfo();
		if (s_goodsName != null) {
			goodsInfo.setGoodsName(s_goodsName);
		}
		if (!StringUtils.isNullOrEmpty(s_state)) {
			goodsInfo.setState(Integer.parseInt(s_state));
		} else {
			goodsInfo.setState(null);
		}
		// 分页的信息
		PageSupport pageSupport = new PageSupport();
		pageSupport.setPageSize(5);
		try {
			pageSupport.setTotalCount(goodsInfoService.count(goodsInfo));
		} catch (Exception e) {
			pageSupport.setTotalCount(0);
		}
		if (pageSupport.getTotalCount() > 0) {
			if (currentpage != null)
				pageSupport.setPage(currentpage);
			if (pageSupport.getPage() <= 0)
				pageSupport.setPage(1);
			if (pageSupport.getPage() > pageSupport.getPageCount())
				pageSupport.setPage(pageSupport.getPageCount());
			// 将分页查询的两个信息封装到user中
			goodsInfo.setPageSize(pageSupport.getPageSize());
			goodsInfo.setStarNum((pageSupport.getPage() - 1) * pageSupport.getPageSize());

			List<GoodsInfo> goodsInfoList = null;
			try {
				goodsInfoList = goodsInfoService.getGoodsInfoList(goodsInfo);
			} catch (Exception e) {
				goodsInfoList = null;
			}
			pageSupport.setItems(goodsInfoList);
		} else {
			pageSupport.setItems(null);
		}
		model.addAttribute("page", pageSupport);
		model.addAttribute("s_goodsName", s_goodsName);
		model.addAttribute("s_state", s_state);
		return new ModelAndView("/backend/goodsinfolist");
	}

	// backend/modifygoodsinfo.html
	@RequestMapping(value = "/backend/modifygoodsinfo.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String modifygoodsinfo(HttpSession session, @RequestParam String goodsInfo) {
		logger.debug("modifyGoodsInfo =======" + goodsInfo);
		String result = "failed";
		if (goodsInfo == null || "".equals(goodsInfo)) {
			result = "nodata";
		} else {
			try {
				JSONObject jsonObject = JSONObject.fromObject(goodsInfo);
				GoodsInfo goods = (GoodsInfo) jsonObject.toBean(jsonObject, GoodsInfo.class);
				if (goods.getGoodsSN() == null || "".equals(goods.getGoodsSN())) {
					// 只修改上/下架状态，在goodsInfolist页面直接操作
					goodsInfoService.modifyGoodsInfo(goods);
					return "success";
				} else {
					User sessionUser = (User) session.getAttribute(Constants.SESSION_USER);
					// 修改很多信息
					goods.setLastUpdateTime(new Date());
					goods.setCreatedBy(sessionUser.getLoginCode());
					goodsInfoService.modifyGoodsInfo(goods);
					result = "success";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/// backend/addgoodsinfo.html 添加商品
	@RequestMapping(value = "/backend/addgoodsinfo.html", method = RequestMethod.POST)
	public ModelAndView addgoodsinfo(HttpSession session, @ModelAttribute("addGoodsInfo") GoodsInfo addGoodsInfo) {
		try {
			User sessionUser = (User) session.getAttribute(Constants.SESSION_USER);
			addGoodsInfo.setCreatedBy(sessionUser.getLoginCode());
			addGoodsInfo.setLastUpdateTime(new Date());
			addGoodsInfo.setCreateTime(new Date());
			goodsInfoService.addGoodsInfo(addGoodsInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/backend/goodsinfolist.html");
	}

	// backend/getgoodsinfo.html 单个详细信息,点击查看的时候
	@RequestMapping(value = "/backend/getgoodsinfo.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object getgoodsinfo(@RequestParam(value = "id", required = false) String id) {
		String cjson = "";
		if (id == null || "".equals(id)) {
			return "nodata";
		} else {
			try {
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo.setId(Integer.valueOf(id));
				goodsInfo = goodsInfoService.getGoodsInfoById(goodsInfo);
				// user对象里有日期，所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(goodsInfo, jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return cjson;
		}
	}

	/// backend/goodsSNisexit.html
	@RequestMapping(value = "/backend/goodsSNisexit.html" , produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String goodsSNisexit(@RequestParam(value="id",required=false) String id,
							@RequestParam(value="goodsSN",required=false) String goodsSN){
		String result = "failed";
		GoodsInfo goodsInfo = new GoodsInfo();
		if(!id.equals(-1)){
			goodsInfo.setId(Integer.valueOf(id));
		}
		goodsInfo.setGoodsSN(goodsSN);
		try {
			if(goodsInfoService.GoodsInfoExist(goodsInfo) == 0){
				result = "only";
			}else{
				result = "repeat";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	// backend/delgoodsinfo.html
	@RequestMapping(value = "/backend/delgoodsinfo.html" , produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String delgoodsinfo(@RequestParam(value = "delId", required = false) String delId) {
		String result = "failed";
		GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
		goodsPackAffiliated.setGoodsInfoId(Integer.valueOf(delId));
		if(gpaService.getGoodsInfoExist(goodsPackAffiliated) == 0){
			GoodsInfo goodsInfo  = new GoodsInfo();
			goodsInfo.setId(Integer.parseInt(delId));
			goodsInfoService.deleteGoodsInfo(goodsInfo);
			result = "success";
		}else{
			result = "noallow";
		}
		return result;
	}
	
	@RequestMapping("/backend/goodslist.html")
	@ResponseBody
	public ModelAndView goodslist(HttpSession session,Model model,
			 @RequestParam(value="s_goodsName",required=false) String s_goodsName){
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setState(1);
		if(null != s_goodsName)
			goodsInfo.setGoodsName("%"+SQLTools.transfer(s_goodsName)+"%");
		goodsInfo.setStarNum(0);
		goodsInfo.setPageSize(10000);
		List<GoodsInfo> goodsInfoList = goodsInfoService.getGoodsInfoList(goodsInfo);
		model.addAttribute("goodsInfoList", goodsInfoList);
		model.addAttribute("s_goodsName", s_goodsName);
		return new ModelAndView("/backend/goodslist");
	}
}
