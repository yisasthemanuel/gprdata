package org.jlobato.gpro.services.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authentication Service Class
 * 
 * @author JLOBATO
 *
 */
public class AuthenticationServiceDummy implements AuthenticationService {

	/**
	 * 
	 */
	private static final transient Logger logger = LoggerFactory.getLogger(AuthenticationServiceDummy.class);

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean isUser(String username, String password) {
		logger.info("isUser -> yisasthemanuel/oxford");
		boolean result = "yisasthemanuel".equals(username) && "oxford".equals(password);
		return result;
	}
}
