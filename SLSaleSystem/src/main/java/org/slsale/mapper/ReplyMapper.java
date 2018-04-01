package org.slsale.mapper;

import java.util.List;

import org.slsale.pojo.Reply;

public interface ReplyMapper {

	//根据留言的id(messageId)查询回复
	public List<Reply> getReplys(Reply reply);
	//添加回复
	public int addReply(Reply reply);
	//删除回复
	public int delReply(Reply reply);
}
