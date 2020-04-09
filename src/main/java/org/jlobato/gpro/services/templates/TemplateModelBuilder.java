package org.jlobato.gpro.services.templates;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jmplobato
 *
 */
public class TemplateModelBuilder {
	
	/**
	 * 
	 */
	private Map<String, Object> model;
	
	/**
	 * 
	 */
	private TemplateModelBuilder() {
		super();
		this.model = new HashMap<String, Object>();
	}
	
	/**
	 * 
	 * @return
	 */
	public static TemplateModelBuilder newInstance() {
		return new TemplateModelBuilder();
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Object> build() {
		return this.model;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public TemplateModelBuilder add(String key, Object value) {
		this.model.put(key, value);
		return this;
	}

}
