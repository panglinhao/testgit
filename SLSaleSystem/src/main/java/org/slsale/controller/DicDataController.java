package org.slsale.controller;

import java.io.Reader;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.pojo.DataDictionary;
import org.slsale.service.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class DicDataController {

	private Logger logger = Logger.getLogger(DicDataController.class);

	@Resource
	private DataDictionaryService dataService;

	@RequestMapping("/backend/dicmanage.html")
	public ModelAndView dicmanage(HttpSession session, Model model) {
		// dataList
		List<DataDictionary> dataList = null;
		try {
			dataList = dataService.getDataDicByTypeCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("dataList", dataList);
		return new ModelAndView("/backend/dicmanage");
	}

	@RequestMapping(value = "/backend/getJsonDic.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object getJsonDic(@RequestParam(value = "typeCode") String typeCode) {
		if (typeCode == null || "".equals(typeCode)) {
			return "nodata";
		} else {
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode(typeCode);
			List<DataDictionary> dataList = null;
			try {
				dataList = dataService.getDataDictionaries(dataDictionary);
				if (dataList != null) {
					return JSONArray.fromObject(dataList).toString();
				}
				return "nodata";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}

	// /backend/typecodeisexit.html 查重
	@RequestMapping(value = "/backend/typecodeisexit.html" , produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String typecodeisexit(@RequestParam(value = "typeCode",required = false) String typeCode,
								@RequestParam(value = "id",required = false) String id){
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode(typeCode);
		if(id != null)
			dataDictionary.setId(Integer.valueOf(id));
		try {
			if(dataService.typeCodeIsExit(dataDictionary) > 0){
				return "repeat";
			}else{
				return "only";
			}
		} catch (Exception e) {
			return "failed";
		}
	}

	// 修改子类型
	@RequestMapping("/backend/modifyDic.html")
	@ResponseBody
	public String modifyDic(@RequestParam(value="dicJson" ,required = false) String dicJson){
		
		if(dicJson == null || "".equals(dicJson)){
			return "nodata";
		}else{
			JSONObject jsonObject = JSONObject.fromObject(dicJson);
			DataDictionary dataDictionary = (org.slsale.pojo.DataDictionary) JSONObject.toBean(jsonObject, DataDictionary.class);
			try {
				//查询valueId在该类型下是否重复
				if(dataService.typeCodeIsExit(dataDictionary) > 0){
					return "rename";
				}else{
					dataService.modifyDataDictionary(dataDictionary);
					return "success";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	///backend/delDic.html 删除子的
	@RequestMapping("/backend/delDic.html")
	@ResponseBody
	public String delDic(@RequestParam(value="id" ,required = false) String id){
		if(id == null || id.equals("")){
			return "nodata";
		}else{
			DataDictionary delDic = new DataDictionary();
			delDic.setId(Integer.valueOf(id));
			try {
				dataService.delDataDictionary(delDic);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	//  /backend/modifylDic.html 修改主的
	
	@RequestMapping("/backend/modifylDic.html")
	@ResponseBody
	public String modifyDic(@RequestParam String olddic, @RequestParam String newdic){
		if(olddic == null || "".equals(olddic) || newdic == null || "".equals(newdic)){
			return "nodata";
		}else{
			JSONObject newDicObject = JSONObject.fromObject(newdic);
			JSONObject oldDicObject = JSONObject.fromObject(olddic);
			DataDictionary newDataDictionary =  (DataDictionary)JSONObject.toBean(newDicObject, DataDictionary.class);
			DataDictionary oldDataDictionary =  (DataDictionary)JSONObject.toBean(oldDicObject, DataDictionary.class);
			
			try {
				List<DataDictionary> ddList = null;
				//第一个typeName是新的typeCode 第二个typeCode是旧的typeCode
				DataDictionary _ddDataDictionary  = new DataDictionary();
				_ddDataDictionary.setTypeName(newDataDictionary.getTypeCode());
				_ddDataDictionary.setTypeCode(oldDataDictionary.getTypeCode());
				ddList = dataService.getDataDictionariesNotIn(_ddDataDictionary);
				if(ddList !=null && ddList.size() > 0){//有重名
					return "rename";
				}else{
					newDataDictionary.setValueName(oldDataDictionary.getTypeCode());
					dataService.modifyDataDictionarys(newDataDictionary);
				}
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	//   /backend/delMainDic.html  删除主类型
	@RequestMapping("/backend/delMainDic.html")
	@ResponseBody
	public String delMainDic(@RequestParam String dic){
		if(dic == null && "".equals(dic)){
			return "nodata";
		}else{
			JSONObject jsonObject = JSONObject.fromObject(dic);
			DataDictionary delDataDictionary =  (DataDictionary)JSONObject.toBean(jsonObject, DataDictionary.class);
			try {
				dataService.delDataDictionary(delDataDictionary);
				return "success";
			} catch (Exception e) {
				return "failed";
			}
		}
	}
	
	///backend/addDic.html 添加主类型
	@RequestMapping("/backend/addDic.html")
	@ResponseBody
	public String addDic(@RequestParam String dic){
		if(dic == null || "".equals(dic)){
			return "nodata";
		}else{
			JSONObject jsonObject = JSONObject.fromObject(dic);
			DataDictionary addDic =  (DataDictionary)JSONObject.toBean(jsonObject, DataDictionary.class);
			try {
				DataDictionary _dataDictionarynew = new DataDictionary();
				_dataDictionarynew.setTypeCode(addDic.getTypeCode());
				
				List<DataDictionary> ddList = dataService.getDataDictionaries(_dataDictionarynew);
				//typeCode 不能重复
				if(null != ddList && ddList.size() > 0){
					return "rename";
				}else{
					_dataDictionarynew.setTypeCode(null);
					_dataDictionarynew.setTypeName(addDic.getTypeName());
					ddList = null;
					ddList = dataService.getDataDictionaries(_dataDictionarynew);
					if(null != ddList  && ddList.size() > 0){
						return "rename";
					}else
						dataService.addDataDictionary(addDic);
				}
				return "success";
			} catch (Exception e) {
				return "failed";
			}
		}
	}
	
	///backend/addDicSub.html  添加子类型，并重新加载子类型的div
	@RequestMapping(value = "/backend/addDicSub.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object addDicSub(@RequestParam String dic){
		if(dic == null && "".equals(dic)){
			return "nodata";
		}else{
			JSONObject jsonObject = JSONObject.fromObject(dic);
			DataDictionary addDic =  (DataDictionary)JSONObject.toBean(jsonObject, DataDictionary.class);
			try {
				    if(dataService.typeCodeIsExit(addDic) > 0){//valueName重名
				    	return "rename";
				    }else{
				    	int valueId = (dataService.getMaxValueId(addDic)==null?0:dataService.getMaxValueId(addDic))+1;
				    	addDic.setValueId(valueId);
				    	dataService.addDataDictionary(addDic);
				    }
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
	}
}
