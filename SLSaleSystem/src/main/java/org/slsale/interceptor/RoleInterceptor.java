package org.slsale.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RoleInterceptor implements HandlerInterceptor{
	
	@Resource
	private RedisAPI redisAPI;
	private Logger logger = Logger.getLogger(RoleInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURI();//获取到的是/SLSaleSystem/backend/userlist.html,多了个项目名
		String path = request.getContextPath();
		url = url.substring(path.length());
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute(Constants.SESSION_USER);
		if(currentUser!=null){
			int roleId = currentUser.getRoleId();
			String urlString = redisAPI.get("Role"+roleId+"UrlList");
			logger.debug("urlString from redis========"+urlString);
			if(urlString != null && !"".equals(urlString) && urlString.indexOf(url) != -1){
				return true;
			}else{
				response.sendRedirect("/SLSaleSystem/401.html");
				return false;
			}
		}
		response.sendRedirect("/SLSaleSystem");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
