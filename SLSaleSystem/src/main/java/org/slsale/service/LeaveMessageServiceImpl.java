package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.LeaveMessageMapper;
import org.slsale.pojo.LeaveMessage;
import org.springframework.stereotype.Service;

@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {
	
	@Resource
	private LeaveMessageMapper leaveMessageMapper;

	@Override
	public List<LeaveMessage> getLeavaMessages(LeaveMessage leaveMessage) {
		// TODO Auto-generated method stub
		return leaveMessageMapper.getLeavaMessages(leaveMessage);
	}

	@Override
	public int count(LeaveMessage leaveMessage) {
		// TODO Auto-generated method stub
		return leaveMessageMapper.count(leaveMessage);
	}

	@Override
	public LeaveMessage getLeaveMessageById(LeaveMessage leaveMessage) {
		// TODO Auto-generated method stub
		return leaveMessageMapper.getLeaveMessageById(leaveMessage);
	}

	@Override
	public int modifyLeaveMessage(LeaveMessage leaveMessage) {
		// TODO Auto-generated method stub
		return leaveMessageMapper.modifyLeaveMessage(leaveMessage);
	}

	@Override
	public int delLeaveMessage(LeaveMessage leaveMessage) {
		// TODO Auto-generated method stub
		return leaveMessageMapper.delLeaveMessage(leaveMessage);
	}

	@Override
	public int addLeaveMessage(LeaveMessage leaveMessage) {
		// TODO Auto-generated method stub
		return leaveMessageMapper.addLeaveMessage(leaveMessage);
	}

}
