package org.slsale.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slsale.common.Constants;
import org.slsale.common.HtmlEncode;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.pojo.LeaveMessage;
import org.slsale.pojo.MessageReply;
import org.slsale.pojo.Reply;
import org.slsale.pojo.User;
import org.slsale.service.LeaveMessageService;
import org.slsale.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class LeaveMessageController {

	@Resource
	private LeaveMessageService lmService;
	@Resource
	private ReplyService replyService;

	// /message/messagelist.html 留言管理页面
	@RequestMapping("/message/messagelist.html")
	public ModelAndView messagelist(HttpSession session, Model model,
			@RequestParam(value = "currentpage", required = false) Integer currentpage) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			LeaveMessage leaveMessage = new LeaveMessage();
			PageSupport page = new PageSupport();
			List<LeaveMessage> lmList = null;
			try {
				page.setTotalCount(lmService.count(leaveMessage));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (currentpage != null)
					page.setPage(currentpage);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());

				leaveMessage.setStarNum((page.getPage() - 1) * page.getPageSize());
				leaveMessage.setPageSize(page.getPageSize());

				try {
					lmList = lmService.getLeavaMessages(leaveMessage);
				} catch (Exception e) {
					lmList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(lmList);
			} else {
				page.setItems(null);
			}
			model.addAttribute("page", page);
			model.addAttribute("currentUser", ((User) session.getAttribute(Constants.SESSION_USER)).getLoginCode());
			return new ModelAndView("message/messagelist");
		}
	}

	// /message/getmessage.html 查看详细信息
	@RequestMapping(value = "/message/getmessage.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object getMessage(@RequestParam(value = "id", required = false) String id) {
		String cjson = "";
		if (id == null || "".equals(id)) {
			return "nodata";
		} else {
			LeaveMessage leaveMessage = new LeaveMessage();
			leaveMessage.setId(Integer.valueOf(id));
			try {
				leaveMessage = lmService.getLeaveMessageById(leaveMessage);
				// 查询回复列表
				Reply reply = new Reply();
				reply.setMessageId(Integer.valueOf(id));
				List<Reply> replyList = replyService.getReplys(reply);
				// 封装
				MessageReply messageReply = new MessageReply();
				messageReply.setLeaveMessage(leaveMessage);
				messageReply.setReplyList(replyList);
				// 转JSON格式
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(messageReply, jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				// TODO: handle exception
				return "failed";
			}
			return cjson;
		}
	}

	// /message/replymessage.html 回复留言
	@RequestMapping("/message/replymessage.html")
	public ModelAndView replymessage(HttpSession session, @ModelAttribute("addReply") Reply addReply) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			try {
				addReply.setCreateTime(new Date());
				addReply.setCreatedBy(((User) session.getAttribute(Constants.SESSION_USER)).getLoginCode());
				if (null != addReply.getReplyContent() && !addReply.getReplyContent().equals("")) {
					addReply.setReplyContent(HtmlEncode.htmlEncode(addReply.getReplyContent()));
				}
				if (replyService.addReply(addReply) > 0) {
					// 更新此条留言的状态（已回复）
					LeaveMessage leaveMessage = new LeaveMessage();
					leaveMessage.setId(addReply.getMessageId());
					leaveMessage.setState(1);
					lmService.modifyLeaveMessage(leaveMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/message/messagelist.html");// 返回留言列表
		}
	}

	// /backend/delmessage.html 删除留言（同时删除回复）
	@RequestMapping(value = "/backend/delmessage.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String delmessage(@RequestParam(value = "delId", required = false) String delId) {
		String result = "failed";
		if (delId == null || "".equals(delId)) {
			result = "nadata";
		} else {
			LeaveMessage leaveMessage = new LeaveMessage();
			leaveMessage.setId(Integer.valueOf(delId));
			if (lmService.delLeaveMessage(leaveMessage) > 0) {
				Reply reply = new Reply();
				reply.setMessageId(Integer.valueOf(delId));
				replyService.delReply(reply);
				result = "success";
			}
		}
		return result;
	}

	// /message/mymessage.html 我的留言
	@RequestMapping("/message/mymessage.html")
	public ModelAndView myMessage(HttpSession session, Model model,
			@RequestParam(value = "currentpage", required = false) Integer currentpage) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			User sessionUser = ((User) session.getAttribute(Constants.SESSION_USER));
			PageSupport page = new PageSupport();
			LeaveMessage leaveMessage = new LeaveMessage();
			List<LeaveMessage> lmList = null;
			
			leaveMessage.setCreatedBy(sessionUser.getLoginCode());
			try {
				page.setTotalCount(lmService.count(leaveMessage));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (currentpage != null)
					page.setPage(currentpage);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());

				leaveMessage.setStarNum((page.getPage() - 1) * page.getPageSize());
				leaveMessage.setPageSize(page.getPageSize());

				try {
					lmList = lmService.getLeavaMessages(leaveMessage);
				} catch (Exception e) {
					lmList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(lmList);
			} else {
				page.setItems(null);
			}
			model.addAttribute("page", page);
			model.addAttribute("currentUser", sessionUser.getLoginCode());
		}
		return new ModelAndView("message/mymessage");
	}

	// /message/addmessage.html 添加我的留言
	@RequestMapping("/message/addmessage.html")
	public ModelAndView addmessage(HttpSession session, @ModelAttribute("addmessage") LeaveMessage addmessage) {
		User sessionUser = (User) session.getAttribute(Constants.SESSION_USER);
		try {
			addmessage.setCreatedBy(sessionUser.getLoginCode());
			addmessage.setCreateTime(new Date());
			addmessage.setState(0);
			if (null != addmessage.getMessageContent() && !addmessage.getMessageContent().equals("")) {
				addmessage.setMessageContent(HtmlEncode.htmlEncode(addmessage.getMessageContent()));
			}
			lmService.addLeaveMessage(addmessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/message/mymessage.html");
	}
	
	
	//动态加载我的留言的回复内容
	@RequestMapping(value="/message/reply.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getReply( HttpServletRequest request,HttpSession session,@RequestParam Integer id){
		String result = "";
		if(null == id || "".equals(id)){
			result = "nodata";
		}else{
			try {
				Reply reply = new Reply();
				reply.setMessageId(id);
				List<Reply> rList = replyService.getReplys(reply);
				if(rList == null){
					result = "noreply";
				}else{
					result = JSONArray.fromObject(rList).toString();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				result = "failed";
			}
		}
		return result;
	}
}
