package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.GoodsPackAffiliatedMapper;
import org.slsale.mapper.GoodsPackMapper;
import org.slsale.pojo.GoodsPack;
import org.slsale.pojo.GoodsPackAffiliated;
import org.springframework.stereotype.Service;
@Service
public class GoodsPackServiceImpl implements GoodsPackService {
	@Resource
	private GoodsPackMapper goodsPackMapper;
	@Resource
	private GoodsPackAffiliatedMapper gpaMapper;

	@Override
	public List<GoodsPack> getGoodsPackList(GoodsPack goodsPack) {
		// TODO Auto-generated method stub
		return goodsPackMapper.getGoodsPackList(goodsPack);
	}

	@Override
	public int count(GoodsPack goodsPack) {
		return goodsPackMapper.count(goodsPack);
	}

	@Override
	public int addGoodsPack(GoodsPack goodsPack) {
		return goodsPackMapper.addGoodsPack(goodsPack);
	}

	@Override
	public GoodsPack getGoodsPackById(GoodsPack goodsPack) {
		return goodsPackMapper.getGoodsPackById(goodsPack);
	}

	@Override
	public int modifyGoodsPack(GoodsPack goodsPack) {
		return goodsPackMapper.modifyGoodsPack(goodsPack);
	}

	@Override
	public int delGoodsPack(GoodsPack goodsPack) {
		// TODO Auto-generated method stub
		return goodsPackMapper.delGoodsPack(goodsPack);
	}

	@Override
	public boolean addGoodsPackAndAffiliated(GoodsPack goodsPack,List<GoodsPackAffiliated> apaList) {
		goodsPackMapper.addGoodsPack(goodsPack);
		//获取添加的套餐id
		int addGoodsPackId = goodsPackMapper.getLastAddGoodsPackId();
		if(apaList != null && apaList.size() > 0 && addGoodsPackId != 0){
			for (GoodsPackAffiliated goodsPackAffiliated : apaList) {
				goodsPackAffiliated.setGoodsPackId(addGoodsPackId);
				gpaMapper.addGoodsPackAffiliated(goodsPackAffiliated);
			}
			return true;
		}
		return false;
	}

	@Override
	public int goodsPackCodeIsexit(GoodsPack goodsPack) {
		return goodsPackMapper.goodsPackCodeIsexit(goodsPack);
	}

	@Override
	public boolean modifyGoodsPackAndAffiliated(GoodsPack goodsPack, List<GoodsPackAffiliated> apaList) {
		//先删掉关系表中相关的，再添加
		goodsPackMapper.modifyGoodsPack(goodsPack);
		GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
		goodsPackAffiliated.setGoodsPackId(goodsPack.getId());
		gpaMapper.delGoodsPackAffiliated(goodsPackAffiliated);
		if(apaList != null){
			for (GoodsPackAffiliated GPAffiliated : apaList) {
				GPAffiliated.setGoodsPackId(goodsPack.getId());
				gpaMapper.addGoodsPackAffiliated(GPAffiliated);
			}
			return true;
		}
		return false;
	}

}
