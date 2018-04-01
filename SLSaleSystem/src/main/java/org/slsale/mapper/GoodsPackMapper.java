package org.slsale.mapper;

import java.util.List;

import org.slsale.pojo.GoodsPack;

public interface GoodsPackMapper {

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
	//获取最后一次添加的套餐id
	public int getLastAddGoodsPackId();
	//查询编号
	public int goodsPackCodeIsexit(GoodsPack goodsPack);
}
