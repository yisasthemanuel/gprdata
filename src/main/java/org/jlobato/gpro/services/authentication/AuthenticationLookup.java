package org.jlobato.gpro.services.authentication;

/**
 * Authentication Lookup Class
 * 
 * @author JLOBATO
 *
 */
public class AuthenticationLookup {
	
	/**
	 * 
	 * @param authenticationServiceType
	 * @return
	 */
	public AuthenticationService getAuthenticationService(String authenticationServiceType) {
		AuthenticationService result = null;
		
		if (authenticationServiceType != null) {
			if (authenticationServiceType.equals("DUMMY")) {
				result = new AuthenticationServiceDummy();
			}
			else {
				result = new AuthenticationServiceTrust();
			}
		}
		
		return result;
	}
}
