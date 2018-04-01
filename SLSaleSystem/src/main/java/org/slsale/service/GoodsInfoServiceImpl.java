package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.GoodsInfoMapper;
import org.slsale.pojo.GoodsInfo;
import org.springframework.stereotype.Service;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

	@Resource
	private GoodsInfoMapper goodsInfoMapper;
	@Override
	public GoodsInfo getGoodsInfoById(GoodsInfo goodsInfo) {
		return goodsInfoMapper.getGoodsInfoById(goodsInfo);
	}

	@Override
	public List<GoodsInfo> getGoodsInfoList(GoodsInfo goodsInfo) {
		return goodsInfoMapper.getGoodsInfoList(goodsInfo);
	}

	@Override
	public int addGoodsInfo(GoodsInfo goodsInfo) {
		// TODO Auto-generated method stub
		return goodsInfoMapper.addGoodsInfo(goodsInfo);
	}

	@Override
	public int deleteGoodsInfo(GoodsInfo goodsInfo) {
		// TODO Auto-generated method stub
		return goodsInfoMapper.deleteGoodsInfo(goodsInfo);
	}

	@Override
	public int modifyGoodsInfo(GoodsInfo goodsInfo) {
		// TODO Auto-generated method stub
		return goodsInfoMapper.modifyGoodsInfo(goodsInfo);
	}

	@Override
	public int GoodsInfoExist(GoodsInfo goodsInfo) {
		// TODO Auto-generated method stub
		return goodsInfoMapper.GoodsInfoExist(goodsInfo);
	}

	@Override
	public int count(GoodsInfo goodsInfo) {
		// TODO Auto-generated method stub
		return goodsInfoMapper.count(goodsInfo);
	}

	public List<GoodsInfo> getGoodsList(GoodsInfo goodsInfo) {
		return goodsInfoMapper.getGoodsList(goodsInfo);
	}

}
