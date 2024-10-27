package org.jlobato.gpro.controller;

import org.primeframework.transformer.domain.Document;
import org.primeframework.transformer.service.BBCodeParser;
import org.primeframework.transformer.service.BBCodeToHTMLTransformer;
import org.primeframework.transformer.service.Transformer.TransformFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Class TyresController.
 */
@Controller
@RequestMapping("/tyres")
public class TyresController {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TyresController.class);
	
	/**
	 * Main tyres.
	 *
	 * @return the model and view
	 */
	@GetMapping(value = "/tyres.html")
	public ModelAndView mainTyres() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/tyres/tyres");
		return modelAndView;
	}
	
	/**
	 * Welcome as HTML.
	 *
	 * @param text the text
	 * @return the string
	 */
	@PostMapping(value = "/preview.html", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
    public String welcomeAsHTML(@RequestParam("text") String text) {
		String result = "";
		try {
			logger.debug("welcomeAsHTML - begin");
			logger.debug("welcomeAsHTML - procesing: '{}'", text);
			Document document = new BBCodeParser().buildDocument(text, null);
			result = new BBCodeToHTMLTransformer().transform(document, node -> true, new TransformFunction.HTMLTransformFunction(), null);
			logger.debug("welcomeAsHTML - end with response: '{}'", result);
		} catch(Exception e) {
			logger.error("Exception on welcomeAsHTML", e);
		}
		return result;
    }

}
