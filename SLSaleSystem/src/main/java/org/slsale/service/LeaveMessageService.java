package org.slsale.service;

import java.util.List;

import org.slsale.pojo.LeaveMessage;

public interface LeaveMessageService {

	//查询列表（分页）
	public List<LeaveMessage> getLeavaMessages(LeaveMessage leaveMessage);
	
	//查询总数量
	public int count(LeaveMessage leaveMessage);
	
	//获取单个详细信息
	public LeaveMessage getLeaveMessageById(LeaveMessage leaveMessage);
	
	//更新
	public int modifyLeaveMessage(LeaveMessage leaveMessage);
	
	//删除
	public int delLeaveMessage(LeaveMessage leaveMessage);
	
	//添加
	public int addLeaveMessage(LeaveMessage leaveMessage);
}
