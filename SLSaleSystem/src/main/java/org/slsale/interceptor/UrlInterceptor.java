package org.slsale.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slsale.common.Constants;
import org.slsale.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class UrlInterceptor implements HandlerInterceptor{
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String url = request.getRequestURI();
		if(url.indexOf("login") != -1){
			return true;
		}
		
		if(url.indexOf("statics") != -1){
			return true;
		}
		
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute(Constants.SESSION_USER);
		if(currentUser!=null){
			return true;
		}
		
		response.sendRedirect("/SLSaleSystem");
		return false;
	}

}
