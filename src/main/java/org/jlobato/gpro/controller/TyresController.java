package org.jlobato.gpro.controller;

import org.primeframework.transformer.domain.Document;
import org.primeframework.transformer.service.BBCodeParser;
import org.primeframework.transformer.service.BBCodeToHTMLTransformer;
import org.primeframework.transformer.service.Transformer.TransformFunction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tyres")
public class TyresController {
	
	@GetMapping(value = "/tyres")
	public ModelAndView mainTyres() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/tyres/tyres");
		return modelAndView;
	}
	
	@PostMapping(value = "/preview", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
    public String welcomeAsHTML(String text) {
		Document document = new BBCodeParser().buildDocument(text, null);
		return new BBCodeToHTMLTransformer().transform(document, node -> true, new TransformFunction.HTMLTransformFunction(), null);
    }

}
