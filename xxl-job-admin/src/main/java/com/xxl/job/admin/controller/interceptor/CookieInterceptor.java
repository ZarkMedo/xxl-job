package com.xxl.job.admin.controller.interceptor;

import com.xxl.job.admin.core.util.FtlUtil;
import com.xxl.job.admin.core.util.I18nUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * push cookies to model as cookieMap
 *
 * @author xuxueli 2015-12-12 18:09:04
 */
@Component
public class CookieInterceptor extends HandlerInterceptorAdapter {

	@Value("${xxl.job.accessToken}")
	private String accessToken;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	    if (request.getHeader("XXL-JOB-ACCESS-TOKEN")==null){
			// cookie
			if (modelAndView!=null && request.getCookies()!=null && request.getCookies().length>0) {
				HashMap<String, Cookie> cookieMap = new HashMap<String, Cookie>();
				for (Cookie ck : request.getCookies()) {
					cookieMap.put(ck.getName(), ck);
				}
				modelAndView.addObject("cookieMap", cookieMap);
			}

			// static method
			if (modelAndView != null) {
				modelAndView.addObject("I18nUtil", FtlUtil.generateStaticModel(I18nUtil.class.getName()));
			}

		}else {
	    	if (!request.getHeader("XXL-JOB-ACCESS-TOKEN").equals(accessToken)){
				if (modelAndView != null) {
					modelAndView.addObject("I18nUtil", FtlUtil.generateStaticModel(I18nUtil.class.getName()));
				}
			}
		}

		super.postHandle(request, response, handler, modelAndView);
	}
	
}
