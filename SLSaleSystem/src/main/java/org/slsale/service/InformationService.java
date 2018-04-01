package org.slsale.service;

import java.util.List;

import org.slsale.pojo.Information;

public interface InformationService {

	//查询资讯列表（分页）
	public List<Information> getInformations(Information information);
	//查询数量
	public int count(Information information);
	//详情
	public Information getInformationById(Information information);
	//修改
	public int modifyInformation(Information information);
	//增加
	public int addInformation(Information information);
	//删除
	public int delInformation(Information information);
	//删除文件后，将资讯中所有引用这个文件的file相关内容清空
	public int modifyInformationFileInfo(Information information);
}
