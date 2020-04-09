package org.jlobato.gpro.interceptors;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 
 * @author JLOBATO
 *
 */
public class LocaleChangeInterceptor extends HandlerInterceptorAdapter {
	private static final transient Logger logger = LoggerFactory.getLogger(LocaleChangeInterceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	logger.debug("LocaleChangeInterceptor - preHandle");
    	
    	logger.debug("LocaleChangeInterceptor.preHandle - current locale: " + RequestContextUtils.getLocale(request));
        
        String newLocale = request.getParameter("lang");
    	
        logger.debug("LocaleChangeInterceptor.preHandle - lang parameter: " + newLocale);
        
        
        if (newLocale != null) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            logger.debug("LocaleChangeInterceptor.preHandle - localeResolver: " + localeResolver);
            
            if (localeResolver == null) {
            	throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
        	Locale locale = StringUtils.parseLocaleString(newLocale);
            logger.debug("LocaleChangeInterceptor.preHandle - locale: " + locale.getDisplayName());
        	localeResolver.setLocale(request, response, locale);
        }
        
        logger.debug("LocaleChangeInterceptor.preHandle - end of the method delegating on parent class");
    	return super.preHandle(request, response, handler);
    }
	

}
