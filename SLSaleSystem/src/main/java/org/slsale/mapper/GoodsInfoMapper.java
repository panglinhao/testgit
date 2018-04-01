package org.slsale.mapper;

import java.util.List;

import org.slsale.pojo.GoodsInfo;

public interface GoodsInfoMapper {

	//根据Id查单个商品
	public GoodsInfo getGoodsInfoById(GoodsInfo goodsInfo);
	//查询所有商品（分页+模糊）
	public List<GoodsInfo> getGoodsInfoList(GoodsInfo goodsInfo);
	//增加商品信息
	public int addGoodsInfo(GoodsInfo goodsInfo);
	//删除商品
	public int deleteGoodsInfo(GoodsInfo goodsInfo);
	//修改商品信息
	public int modifyGoodsInfo(GoodsInfo goodsInfo);
	//查询商品编号是否存在
	public int GoodsInfoExist(GoodsInfo goodsInfo);
	//查询总数量
	public int count(GoodsInfo goodsInfo);
	//查询上架的商品
	public List<GoodsInfo> getGoodsList(GoodsInfo goodsInfo);
}
