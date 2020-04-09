package org.jlobato.gpro.services.authentication;

/**
 * Authentication Service Class. Always trust in the credentials provided.
 * 
 * @author JLOBATO
 *
 */
public class AuthenticationServiceTrust implements AuthenticationService {

	/**
	 * Always return true
	 */
	public boolean isUser(String username, String password) {
		return true;
	}


}
