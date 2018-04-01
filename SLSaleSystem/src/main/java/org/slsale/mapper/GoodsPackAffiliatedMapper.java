package org.slsale.mapper;

import java.util.List;

import org.slsale.pojo.GoodsPackAffiliated;

/**
 * 商品和套餐的关系表
 * @author Administrator
 *
 */
public interface GoodsPackAffiliatedMapper {

	//根据商品id查询是否有套餐上架该商品
	public List<GoodsPackAffiliated> getGoodsPackAffiliatedById(GoodsPackAffiliated goodsPackAffiliated);
	//添加套餐的时候，需要添加关系
	public int addGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated);
	//修改套餐的时候，先删
	public int delGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated);
	//删除商品时，根据商品id查询是否有套餐上架该商品
	public int getGoodsInfoExist(GoodsPackAffiliated goodsPackAffiliated);
}
