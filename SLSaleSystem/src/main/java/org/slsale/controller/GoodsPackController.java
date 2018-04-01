package org.slsale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.PageSupport;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.GoodsPack;
import org.slsale.pojo.GoodsPackAffiliated;
import org.slsale.pojo.User;
import org.slsale.service.DataDictionaryService;
import org.slsale.service.GoodsInfoService;
import org.slsale.service.GoodsPackAffiliatedService;
import org.slsale.service.GoodsPackService;
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
import net.sf.json.JSONSerializer;

@Controller
public class GoodsPackController {

	private Logger logger = Logger.getLogger(GoodsPackController.class); 
	@Resource
	private GoodsPackService goodsPackService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private GoodsPackAffiliatedService gpaService;
	
	
	
	@RequestMapping("/backend/goodspacklist.html")
	public ModelAndView goodspacklist(HttpSession session, Model model,
			@RequestParam(value = "currentpage", required = false) Integer currentpage,
			@RequestParam(value = "s_goodsPackName", required = false) String s_goodsPackName,
			@RequestParam(value = "s_typeId", required = false) String s_typeId,
			@RequestParam(value = "s_state", required = false) String s_state){
		logger.debug("============goodspacklist============");
		//需要在数据字典中获取PACK_TYPE list
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("PACK_TYPE");
		List<DataDictionary> packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
		
		GoodsPack goodsPack = new GoodsPack();
		if (s_goodsPackName != null) {
			goodsPack.setGoodsPackName(s_goodsPackName);
		}
		if (!StringUtils.isNullOrEmpty(s_typeId)) {
			goodsPack.setTypeId(Integer.parseInt(s_typeId));
		} else {
			goodsPack.setTypeId(null);
		}
		if (!StringUtils.isNullOrEmpty(s_state)) {
			goodsPack.setState(Integer.parseInt(s_state));
		} else {
			goodsPack.setState(null);
		}
		// 分页的信息
		PageSupport pageSupport = new PageSupport();
		try {
			pageSupport.setTotalCount(goodsPackService.count(goodsPack));
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
			goodsPack.setPageSize(pageSupport.getPageSize());
			goodsPack.setStarNum((pageSupport.getPage() - 1) * pageSupport.getPageSize());

			List<GoodsPack> goodsPackList = null;
			try {
				goodsPackList = goodsPackService.getGoodsPackList(goodsPack);
			} catch (Exception e) {
				goodsPackList = null;
			}
			pageSupport.setItems(goodsPackList);
		} else {
			pageSupport.setItems(null);
		}
		model.addAttribute("packTypeList",packTypeList);
		model.addAttribute("page", pageSupport);
		model.addAttribute("s_goodsPackName", s_goodsPackName);
		model.addAttribute("s_typeId", s_typeId);
		model.addAttribute("s_state", s_state);
		return new ModelAndView("/backend/goodspacklist");
	}
	
	//跳转到增加页面
	@RequestMapping("/backend/addgoodspack.html")
	public ModelAndView addgoodspack(HttpSession session,Model model){
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("PACK_TYPE");
		List<DataDictionary> packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
		model.addAttribute("packTypeList", packTypeList);
		return new ModelAndView("/backend/addgoodspack");
	}
	
	//增加业务的处理
	@RequestMapping(value = "/backend/saveaddgoodspack.html", method = RequestMethod.POST)
	public ModelAndView saveaddgoodspack(HttpSession session,
										@ModelAttribute("addGoodsPack") GoodsPack addGoodsPack){
		try {
			User user = (User)session.getAttribute(Constants.SESSION_USER);
			List<GoodsPackAffiliated> GPAList = null;
			GPAList = getJavaCollection(new GoodsPackAffiliated(),addGoodsPack.getGoodsJson());
			addGoodsPack.setCreateTime(new Date());
			addGoodsPack.setCreatedBy(user.getLoginCode());
			addGoodsPack.setLastUpdateTime(new Date());
			goodsPackService.addGoodsPackAndAffiliated(addGoodsPack, GPAList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/backend/goodspacklist.html");
	}
	
	//   /backend/goodspackcodeisexit.html 
	@RequestMapping(value = "/backend/goodspackcodeisexit.html" , produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String goodspackcodeisexit(@RequestParam(value="goodsPackCode",required=false) String goodsPackCode,
									@RequestParam(value="id",required=false)String id){
		String result = "failed";
		try {
			GoodsPack goodsPack = new GoodsPack();
			goodsPack.setGoodsPackCode(goodsPackCode);
			if(!id.equals("-1"))
				goodsPack.setId(Integer.valueOf(id));
			
			if(goodsPackService.goodsPackCodeIsexit(goodsPack) != 0){
				result = "repeat";
			}else{
				result = "only";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//  /backend/modifygoodspackstate.html 点击状态直接修改上、下架状态
	@RequestMapping(value = "/backend/modifygoodspackstate.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String modifygoodspackstate(@RequestParam(value = "state",required = false) String state){
		String result = "failed";
		if(state == null || "".equals(state)){
			result = "nodata";
		}else{
			JSONObject jsonObject = JSONObject.fromObject(state);
			GoodsPack goodsPack = (GoodsPack) jsonObject.toBean(jsonObject, GoodsPack.class);
			try {
				goodsPackService.modifyGoodsPack(goodsPack);
			} catch (Exception e) {
				return result;
			}
			result = "success";
		}
		return result;
	}
	//  /backend/modifygoodspack.html 去修改页面
	@RequestMapping("/backend/modifygoodspack.html")
	public ModelAndView modifygoodspack(Model model,@RequestParam(value = "id") String id){
		if(null == id || id.equals("")){
			return new ModelAndView("redirect:/backend/goodspacklist.html");
		}
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("PACK_TYPE");
		List<DataDictionary> packTypeList = null;
		List<GoodsPackAffiliated> goodsList = null;
		GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
		goodsPackAffiliated.setGoodsPackId(Integer.valueOf(id));
		try {
			packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			goodsList = gpaService.getGoodsPackAffiliatedById(goodsPackAffiliated);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GoodsPack goodsPack = new GoodsPack();
		goodsPack.setId(Integer.valueOf(id));
		try {
			goodsPack = goodsPackService.getGoodsPackById(goodsPack);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("packTypeList", packTypeList);
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("goodsPack", goodsPack);
		return new ModelAndView("/backend/modifygoodspack");
	}
	
	//  /backend/savemodifygoodspack.html  保存修改页面的内容
	@RequestMapping(value = "/backend/savemodifygoodspack.html", method = RequestMethod.POST)
	public ModelAndView savemodifygoodspack(HttpSession session,
										@ModelAttribute("addGoodsPack") GoodsPack modifyGoodsPack){
		try {
			User user = (User)session.getAttribute(Constants.SESSION_USER);
			List<GoodsPackAffiliated> GPAList = null;
			GPAList = getJavaCollection(new GoodsPackAffiliated(),modifyGoodsPack.getGoodsJson());
			modifyGoodsPack.setCreateTime(new Date());
			modifyGoodsPack.setCreatedBy(user.getLoginCode());
			modifyGoodsPack.setLastUpdateTime(new Date());
			goodsPackService.modifyGoodsPackAndAffiliated(modifyGoodsPack, GPAList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/backend/goodspacklist.html");
	}
	
	// /backend/viewgoodspack.html 去详情信息页面
	@RequestMapping("/backend/viewgoodspack.html")
	public ModelAndView viewgoodspack(Model model,@RequestParam(value = "id") String id){
		if(null == id || id.equals("")){
			return new ModelAndView("redirect:/backend/goodspacklist.html");
		}
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("PACK_TYPE");
		List<DataDictionary> packTypeList = null;
		List<GoodsPackAffiliated> goodsList = null;
		GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
		goodsPackAffiliated.setGoodsPackId(Integer.valueOf(id));
		try {
			packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			goodsList = gpaService.getGoodsPackAffiliatedById(goodsPackAffiliated);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GoodsPack goodsPack = new GoodsPack();
		goodsPack.setId(Integer.valueOf(id));
		try {
			goodsPack = goodsPackService.getGoodsPackById(goodsPack);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("packTypeList", packTypeList);
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("goodsPack", goodsPack);
		return new ModelAndView("/backend/viewgoodspack");
	}
	
	// /backend/delgoodspack.html 删除套餐,关系表也要删除
	@RequestMapping(value = "/backend/delgoodspack.html" , produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delgoodspack(@RequestParam(value = "delId",required =false) String delId){
		if(delId != null && !"".equals(delId)){
			GoodsPack goodsPack = new GoodsPack();
			goodsPack.setId(Integer.valueOf(delId));
			if(goodsPackService.delGoodsPack(goodsPack) > 0){
				GoodsPackAffiliated affiliated = new GoodsPackAffiliated();
				affiliated.setGoodsPackId(Integer.valueOf(delId));
				gpaService.delGoodsPackAffiliated(affiliated);
				return "success";
			}
		}
		return "failed";
	}
	
	/**
     * 封装将json对象转换为java集合对象
     * 添加和修改操作，套餐中的商品和数量在js代码中封装为json对象
     */
    private <T> List<T> getJavaCollection(T clazz, String jsons) {
        List<T> objs=null;
        JSONArray jsonArray=(JSONArray)JSONSerializer.toJSON(jsons);
        if(jsonArray.size() > 1){
            objs=new ArrayList<T>();
            List list=(List)JSONSerializer.toJava(jsonArray);
            for(int i = 0; i < list.size()-1; i++){
            	JSONObject jsonObject=JSONObject.fromObject(list.get(i));
            	T obj=(T)JSONObject.toBean(jsonObject, clazz.getClass());
                objs.add(obj);
            }
        }
        return objs;
    }
}
