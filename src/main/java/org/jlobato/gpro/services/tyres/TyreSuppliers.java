package org.jlobato.gpro.services.tyres;

public enum TyreSuppliers {
	DUNNOS("DU"), YOKOS("YO"), MICHIS("MI"), AVONNS("AV"), PIPIS("PI"), HANCKS("HA"), BRIDGES("BR"), BADYEARS("BY"), CONTIS("CO");
	
	private String name;

	TyreSuppliers(String string) {
		this.name = string;
	}
	
	public String getName() {
		return this.name;
	}
}
