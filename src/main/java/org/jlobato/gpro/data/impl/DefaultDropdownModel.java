package org.jlobato.gpro.data.impl;

import org.jlobato.gpro.data.DropdownModel;

/**
 * The Class DefaultDropdownModel.
 */
public class DefaultDropdownModel implements DropdownModel {
	
	/** The value. */
	private String value;
	
	/** The description. */
	private String description;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4075730718724445485L;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Builder.
	 *
	 * @return the default dropdown model builder
	 */
	public static DefaultDropdownModelBuilder builder() {
		return new DefaultDropdownModelBuilder();
	}
	
	/**
	 * The Class DefaultDropdownModelBuilder.
	 */
	public static class DefaultDropdownModelBuilder {
		
		/** The value. */
		private String value;
		
		/** The description. */
		private String description;
		
		/**
		 * Instantiates a new default dropdown model builder.
		 */
		public DefaultDropdownModelBuilder() {
			// Do nothing
		}
		
		/**
		 * With value.
		 *
		 * @param value the value
		 * @return the default dropdown model builder
		 */
		public DefaultDropdownModelBuilder withValue(String value) {
			this.value = value;
			return this;
		}
		
		/**
		 * With description.
		 *
		 * @param description the description
		 * @return the default dropdown model builder
		 */
		public DefaultDropdownModelBuilder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		/**
		 * Builds the.
		 *
		 * @return the default dropdown model
		 */
		public DefaultDropdownModel build() {
			DefaultDropdownModel result = new DefaultDropdownModel();
			result.setValue(this.value);
			result.setDescription(this.description);
			return result;
		}
	}

	@Override
	public String toString() {
		return "DefaultDropdownModel [value=" + value + ", description=" + description + "]";
	}
}
