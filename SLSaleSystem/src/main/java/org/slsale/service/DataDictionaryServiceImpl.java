package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.DataDictionaryMapper;
import org.slsale.pojo.DataDictionary;
import org.springframework.stereotype.Service;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource
	private DataDictionaryMapper dataDictionaryMapper;

	public List<DataDictionary> getDataDictionaries(DataDictionary dataDictionary) {
		return dataDictionaryMapper.getDataDictionaries(dataDictionary);
	}

	public List<DataDictionary> getDataDicByTypeCode() {
		return dataDictionaryMapper.getDataDicByTypeCode();
	}

	public int typeCodeIsExit(DataDictionary dataDictionary) {
		return dataDictionaryMapper.typeCodeIsExit(dataDictionary);
	}

	public int modifyDataDictionary(DataDictionary dataDictionary) {
		return dataDictionaryMapper.modifyDataDictionary(dataDictionary);
	}
	
	public int modifyDataDictionarys(DataDictionary dataDictionary) {
		return dataDictionaryMapper.modifyDataDictionarys(dataDictionary);
	}
	
	public int delDataDictionary(DataDictionary dataDictionary) {
		return dataDictionaryMapper.delDataDictionary(dataDictionary);
	}

	public List<DataDictionary> getDataDictionariesNotIn(DataDictionary dataDictionary) {
		return dataDictionaryMapper.getDataDictionariesNotIn(dataDictionary);
	}

	public int addDataDictionary(DataDictionary dataDictionary) {
		return dataDictionaryMapper.addDataDictionary(dataDictionary);
	}

	public Integer getMaxValueId(DataDictionary dataDictionary) {
		return dataDictionaryMapper.getMaxValueId(dataDictionary);
	}


	
}
