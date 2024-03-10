package org.jlobato.gpro.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author JLOBATO
 *
 */
@Controller
public class InicioController {
	
	/**
	 * 
	 */
	private static final transient Logger logger = LoggerFactory.getLogger(InicioController.class);
	
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	public ModelAndView autenticar(HttpServletRequest request, HttpSession session)	{
		logger.debug("InicioController.autenticar - begin");
		
        ModelAndView modelAndView = new ModelAndView();
        
		logger.debug("InicioController.autenticar - end");
		return modelAndView;
	}
	
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login.html")
	public ModelAndView login(HttpServletRequest request, HttpSession session)	{
		logger.debug("InicioController.login - begin");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		
		logger.debug("InicioController.login - end");
		return modelAndView;
	}
	
	@RequestMapping(value = "/")
	public ModelAndView index(HttpServletRequest request, HttpSession session)	{
		logger.debug("InicioController.index - begin");
		logger.debug("InicioController.index - end");
		return main(request, session);
	}
	

	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/main.html")
	public ModelAndView main(HttpServletRequest request, HttpSession session)	{
		logger.debug("InicioController.main - begin");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("main");
		
		logger.debug("InicioController.main - end");
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/calendar/mainUpload.html")
	public ModelAndView mainUpload(HttpServletRequest request, HttpSession session)	{
		logger.debug("InicioController.calendar.mainUpload - begin");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/calendar/mainUpload");
		
		logger.debug("InicioController.calendar.mainUpload - end");
		return modelAndView;
	}
	
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/calendar/uploadingFile.html")
	public ModelAndView uploadingFile(HttpServletRequest request, HttpSession session)	{
		logger.debug("InicioController.calendar.uploadingFile - begin");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/calendar/uploadingFile");
		
		logger.debug("InicioController.calendar.uploadingFile - end");
		return modelAndView;
	}	
}
