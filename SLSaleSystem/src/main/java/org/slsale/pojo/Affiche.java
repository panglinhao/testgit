package org.slsale.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Affiche extends Base {

	private String code;//编码
	private String title;//标题
	private String content;//内容
	private String publisher;//发布人
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date publishTime;//发布时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;//生效时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;//失效时间
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
