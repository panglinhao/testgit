package org.slsale.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 商品信息
 * @author Administrator
 * 
 */
public class GoodsInfo extends Base{

	private String goodsSN;//商品编码
	private String goodsName;//商品名称
	private String goodsFormat;//商品规格
	private Double marketPrice;//市场价
	private Double realPrice;//优惠价格
	private Integer state;//状态（1、上架、2、下架）
	private String note;//商品说明
	private Integer num;//库存数量
	private String unit;//单位
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastUpdateTime;//最后更新时间
	private String createdBy;//创建人
	 
	public String getGoodsSN() {
		return goodsSN;
	}
	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsFormat() {
		return goodsFormat;
	}
	public void setGoodsFormat(String goodsFormat) {
		this.goodsFormat = goodsFormat;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
