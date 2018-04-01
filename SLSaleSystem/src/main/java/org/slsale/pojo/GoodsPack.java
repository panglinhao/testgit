package org.slsale.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class GoodsPack extends Base{

	private String goodsPackName;//套餐名称
	private String goodsPackCode;//套餐编码
	private Integer typeId;//套餐类型ID
	private String typeName;//套餐类型NAME
	private Double totalPrice;//套餐总价（系统生成，保存时根据相关商品的优惠价*数量计算生成）
	private Integer state;//状态（1、上架2、下架）
	private String note;//备注说明
	private Integer num;//库存数量
	private String createdBy;//创建人
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastUpdateTime;//最后更新时间
	
	private String goodsJson;
	
	public String getGoodsPackName() {
		return goodsPackName;
	}
	public void setGoodsPackName(String goodsPackName) {
		this.goodsPackName = goodsPackName;
	}
	public String getGoodsPackCode() {
		return goodsPackCode;
	}
	public void setGoodsPackCode(String goodsPackCode) {
		this.goodsPackCode = goodsPackCode;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getGoodsJson() {
		return goodsJson;
	}
	public void setGoodsJson(String goodsJson) {
		this.goodsJson = goodsJson;
	}
}
