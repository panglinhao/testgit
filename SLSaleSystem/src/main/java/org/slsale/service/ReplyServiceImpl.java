package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.ReplyMapper;
import org.slsale.pojo.Reply;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Resource
	private ReplyMapper replyMapper;

	@Override
	public List<Reply> getReplys(Reply reply) {
		// TODO Auto-generated method stub
		return replyMapper.getReplys(reply);
	}

	@Override
	public int addReply(Reply reply) {
		// TODO Auto-generated method stub
		return replyMapper.addReply(reply);
	}

	@Override
	public int delReply(Reply reply) {
		// TODO Auto-generated method stub
		return replyMapper.delReply(reply);
	}

}
