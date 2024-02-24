package org.jlobato.gpro.services.tyres;

/**
 * The Enum TyreSuppliers.
 */
public enum TyreSuppliers {
	
	/** The dunnos. */
	DUNNOS("DU"), 
 /** The yokos. */
 YOKOS("YO"), 
 /** The michis. */
 MICHIS("MI"), 
 /** The avonns. */
 AVONNS("AV"), 
 /** The pipis. */
 PIPIS("PI"), 
 /** The hancks. */
 HANCKS("HA"), 
 /** The bridges. */
 BRIDGES("BR"), 
 /** The badyears. */
 BADYEARS("BY"), 
 /** The contis. */
 CONTIS("CO");
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new tyre suppliers.
	 *
	 * @param string the string
	 */
	TyreSuppliers(String string) {
		this.name = string;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
}
