package org.slsale.service;

import java.util.List;

import org.slsale.pojo.GoodsPackAffiliated;

public interface GoodsPackAffiliatedService {

	//
	public List<GoodsPackAffiliated> getGoodsPackAffiliatedById(GoodsPackAffiliated goodsPackAffiliated);
	//删除商品时，根据商品id查询是否有套餐上架该商品
	public int getGoodsInfoExist(GoodsPackAffiliated goodsPackAffiliated);
	
	public int delGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated);
}
