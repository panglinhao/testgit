package org.slsale.service;

import java.util.List;

import org.slsale.pojo.DataDictionary;

public interface DataDictionaryService {
	//获取数据字典列表(根据不同类型)
	public List<DataDictionary> getDataDictionaries(DataDictionary dataDictionary);
	//获取所有数据类型（typeCode）
	public List<DataDictionary> getDataDicByTypeCode();
	//查重
	public int typeCodeIsExit(DataDictionary dataDictionary);
	//修改
	public int modifyDataDictionary(DataDictionary dataDictionary);
	//修改主类型，不是根据id，根据typeCode
	public int modifyDataDictionarys(DataDictionary dataDictionary);
	//删除
	public int delDataDictionary(DataDictionary dataDictionary);
	//对主类型进行查重
	public List<DataDictionary> getDataDictionariesNotIn(DataDictionary dataDictionary);
	//添加
	public int addDataDictionary(DataDictionary dataDictionary);
	//获取某一个类型中最大的valueId
	public Integer getMaxValueId(DataDictionary dataDictionary);
}
