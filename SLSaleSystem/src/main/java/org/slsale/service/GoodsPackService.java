package org.slsale.service;

import java.util.List;

import org.slsale.pojo.GoodsPack;
import org.slsale.pojo.GoodsPackAffiliated;

public interface GoodsPackService {
	//查询商品套餐列表
	public List<GoodsPack> getGoodsPackList(GoodsPack goodsPack);
	//查询总数量
	public int count(GoodsPack goodsPack);
	//添加
	public int addGoodsPack(GoodsPack goodsPack);
	//详细信息
	public GoodsPack getGoodsPackById(GoodsPack goodsPack);
	//修改
	public int modifyGoodsPack(GoodsPack goodsPack);
	//删除
	public int delGoodsPack(GoodsPack goodsPack);
	//级联添加
	public boolean addGoodsPackAndAffiliated(GoodsPack goodsPack,List<GoodsPackAffiliated> apaList);
	//级联修改
	public boolean modifyGoodsPackAndAffiliated(GoodsPack goodsPack,List<GoodsPackAffiliated> apaList);
	//查询编号
	public int goodsPackCodeIsexit(GoodsPack goodsPack);
}
