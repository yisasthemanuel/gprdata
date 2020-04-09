package org.jlobato.gpro.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author JLOBATO
 *
 */
public class AuthenticationDelegate {
	
	/**
	 * 
	 */
	@Autowired
	private AuthenticationLookup businessLookup;
	
	/**
	 * 
	 */
	private AuthenticationService businessService;
	
	/**
	 * 
	 */
	private String authenticationServiceType;
	

	/**
	 * 
	 * @param authenticationServiceType
	 */
	public void setAuthenticationServiceType(String authenticationServiceType) {
		this.authenticationServiceType = authenticationServiceType;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUser(String username, String password) {
		businessService = businessLookup.getAuthenticationService(this.authenticationServiceType);
		return businessService.isUser(username, password);
	}

}
