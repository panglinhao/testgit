package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.GoodsPackAffiliatedMapper;
import org.slsale.pojo.GoodsPackAffiliated;
import org.springframework.stereotype.Service;

@Service
public class GoodsPackAffiliatedServiceImpl implements GoodsPackAffiliatedService {
	
	@Resource
	private GoodsPackAffiliatedMapper goodsPackAffiliatedMapper;

	public List<GoodsPackAffiliated> getGoodsPackAffiliatedById(GoodsPackAffiliated goodsPackAffiliated) {
		return goodsPackAffiliatedMapper.getGoodsPackAffiliatedById(goodsPackAffiliated);
	}

	public int getGoodsInfoExist(GoodsPackAffiliated goodsPackAffiliated) {
		return goodsPackAffiliatedMapper.getGoodsInfoExist(goodsPackAffiliated);
	}

	public int delGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated) {
		return goodsPackAffiliatedMapper.delGoodsPackAffiliated(goodsPackAffiliated);
	}

}
