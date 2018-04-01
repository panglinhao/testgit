package org.slsale.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Information extends Base {

	private String title;//标题
	private String content;//内容
	private Integer state;//发布状态（0、未发布 1、发布）默认为发布
	private String publisher;//发布人
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date publishTime;//发布时间
	private Integer typeId;//附件类型ID
	private String typeName;//附件类型NAME
	private String fileName;//附件名称
	private String filePath;//附件存放路径
	private Double fileSize;//附件大小
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uploadTime;//上传时间
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Double getFileSize() {
		return fileSize;
	}
	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
}
