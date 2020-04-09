package org.jlobato.gpro.services.authentication;

/**
 * Authentication Service Interface
 * 
 * @author JLOBATO
 *
 */
public interface AuthenticationService {
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean isUser(String username, String password);

}
