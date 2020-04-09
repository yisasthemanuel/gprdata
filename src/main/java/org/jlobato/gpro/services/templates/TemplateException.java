package org.jlobato.gpro.services.templates;

/**
 * 
 * @author jmplobato
 *
 */
public class TemplateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 941792065574669883L;

	public TemplateException(String message, Throwable origin) {
		super(message, origin);
	}

	public TemplateException(String message) {
		super(message);
	}
	
	

}
