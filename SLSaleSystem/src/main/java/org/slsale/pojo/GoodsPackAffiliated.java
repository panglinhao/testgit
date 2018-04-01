package org.slsale.pojo;

public class GoodsPackAffiliated {

	private Integer id;//主键ID
	private Integer goodsPackId;//商品套餐主表ID
	private Integer goodsInfoId;//商品ID
	private Integer goodsNum;//商品数量
	private String goodsName;//商品名称
	private Double realPrice;//优惠价格
	private String unit;//单位
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGoodsPackId() {
		return goodsPackId;
	}
	public void setGoodsPackId(Integer goodsPackId) {
		this.goodsPackId = goodsPackId;
	}
	public Integer getGoodsInfoId() {
		return goodsInfoId;
	}
	public void setGoodsInfoId(Integer goodsInfoId) {
		this.goodsInfoId = goodsInfoId;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
