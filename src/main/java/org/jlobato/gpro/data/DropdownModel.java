package org.jlobato.gpro.data;

import java.io.Serializable;

/**
 * The Interface DropdownModel.
 */
public interface DropdownModel extends Serializable {
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue();
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription();

}
