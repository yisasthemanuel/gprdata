package org.jlobato.gpro.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.model.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author JLOBATO
 *
 */
@Controller
@RequestMapping("/image")
public class ImageController {
	
	/**
	 * 
	 */
	private static final transient Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	
	/**
	 * 
	 */
	@Autowired
	private FachadaManager fachadaManager;
	
	@RequestMapping(path = "/manager/{managerId}/show.html", method = RequestMethod.GET)
	public void showImage(@PathVariable String managerId, HttpServletRequest request, HttpServletResponse response) {
		try {
			//Vamos a recibir como parámetro el id del manager
			//Esto se podría generalizar luego para cualquier objeto de dominio
//			Manager theManager = (Manager) request.getSession().getAttribute("manager");
			Manager theManager = fachadaManager.getManager(new Short(managerId));
			response.setContentLength(0);
			if (theManager != null) {
				byte[] theManagerImg = theManager.getAvatarImg();
				if (theManagerImg != null) {
					response.setContentType("image/jpeg");
					OutputStream output = null;
					try {
						output = response.getOutputStream();
						response.setContentLength(theManagerImg.length);
						output.write(theManagerImg);
					} catch (Exception e) {
						logger.error("Error", e);
					} finally {
						try {
							output.close();
						} catch (IOException e) {
							logger.error("Error closing response", e);
						}
					}
				}
			}
		} catch (Throwable t) {
			logger.error("", t);
		}
	}

}
