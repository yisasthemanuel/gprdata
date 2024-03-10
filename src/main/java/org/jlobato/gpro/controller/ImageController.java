package org.jlobato.gpro.controller;

import java.io.OutputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.model.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class ImageController.
 *
 * @author JLOBATO
 */
@Controller
@RequestMapping("/image")
public class ImageController {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	
	/** The fachada manager. */
	@Autowired
	private FachadaManager fachadaManager;
	
	/**
	 * Show image.
	 *
	 * @param managerId the manager id
	 * @param request the request
	 * @param response the response
	 */
	@GetMapping(value = "/manager/{managerId}/show.html")
	public void showImage(@PathVariable String managerId, HttpServletRequest request, HttpServletResponse response) {
		try {
			//Vamos a recibir como parámetro el id del manager
			//Esto se podría generalizar luego para cualquier objeto de dominio
			Manager theManager = fachadaManager.getManager(Short.valueOf(managerId));
			response.setContentLength(0);
			if (theManager != null) {
				byte[] theManagerImg = theManager.getAvatarImg();
				if (theManagerImg != null) {
					writeImageToResponse(response, theManagerImg); 
				}
			}
		} catch (Exception ex) {
			logger.error("Error ", ex);
		}
	}

	/**
	 * Write image to response.
	 *
	 * @param response the response
	 * @param theManagerImg the the manager img
	 */
	private void writeImageToResponse(HttpServletResponse response, byte[] theManagerImg) {
		response.setContentType("image/jpeg");
		try (OutputStream output = response.getOutputStream()) {
			response.setContentLength(theManagerImg.length);
			output.write(theManagerImg);
		} catch (Exception e) {
			logger.error("Error escribiendo en el response", e);
		}
	}

}
