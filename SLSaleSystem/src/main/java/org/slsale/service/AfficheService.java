package org.slsale.service;

import java.util.List;

import org.slsale.pojo.Affiche;

public interface AfficheService {

	//查询列表（分页）
	public List<Affiche> getAffiches(Affiche affiche);
	//查询总数量
	public int count(Affiche affiche);
	//添加公告
	public int addAffiche(Affiche affiche);
	//查询单个信息
	public Affiche getAfficheById(Affiche affiche);
	//修改信息
	public int modifyAffiche(Affiche affiche);
	//删除公告
	public int delAffiche(Affiche affiche);
}
