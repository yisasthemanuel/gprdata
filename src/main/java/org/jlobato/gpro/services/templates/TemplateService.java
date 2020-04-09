package org.jlobato.gpro.services.templates;

import java.io.Writer;
import java.util.Map;

/**
 * 
 * @author jmplobato
 *
 */
public interface TemplateService {

	/**
	 * 
	 * @param idPlantilla
	 * @param model
	 * @return
	 * @throws TemplateException
	 */
    String processTemplate(String idPlantilla, Map<String, Object> model) throws TemplateException;
    
    /**
     * 
     * @param idPlantilla
     * @param model
     * @param out
     * @throws TemplateException
     */
    void processTemplate(String idPlantilla, Map<String, Object> model, Writer out) throws TemplateException;

}
