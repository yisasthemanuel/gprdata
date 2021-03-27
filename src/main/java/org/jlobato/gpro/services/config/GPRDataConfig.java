package org.jlobato.gpro.services.config;

import java.util.ArrayList;
import java.util.List;

import org.jlobato.gpro.data.DropdownModel;
import org.jlobato.gpro.data.impl.DefaultDropdownModel;
import org.jlobato.gpro.i18n.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class GPRDataConfig.
 */
@Configuration
public class GPRDataConfig {
	
	/** The Constant logger. */
	public static final Logger logger = LoggerFactory.getLogger(GPRDataConfig.class);
	
	/**
	 * Position list.
	 *
	 * @return the list
	 */
	@Bean
	public List<String> positionList() {
		List<String> result = new ArrayList<>();
		logger.debug("creating positionList");
		for (short i = 1; i <= 40; i++) {
			result.add(Short.toString(i));
		}
		return result;
	}
	
	/**
	 * Race position list.
	 *
	 * @return the list
	 */
	@Bean
	public List<DropdownModel> racePositionList() {
		List<DropdownModel> result = new ArrayList<>();
		logger.debug("creating racePositionList");
		// Adding did not start 
		result.add(DefaultDropdownModel.builder()
				.withValue("")
				.withDescription(Constantes.LABEL_RESULT_DNS_I18N_KEY)
				.build());
		for (short i = 1; i <= 40; i++) {
			String valueDesc = Short.toString(i);
			DropdownModel element = DefaultDropdownModel.builder()
					.withValue(valueDesc)
					.withDescription(Constantes.LABEL_RESULT_GROUP_RACE_I18N_KEY)
					.build();
			result.add(element);
		}
		return result;
	}
	
	/**
	 * Grid position list.
	 *
	 * @return the list
	 */
	@Bean
	public List<DropdownModel> gridPositionList() {
		
		List<DropdownModel> result = new ArrayList<>();
		logger.debug("creating gridPositionList");
		// Adding did not qualified 
		result.add(DefaultDropdownModel.builder()
				.withValue("")
				.withDescription(Constantes.LABEL_RESULT_DNQ_I18N_KEY)
				.build());
		for (short i = 1; i <= 40; i++) {
			DropdownModel element = DefaultDropdownModel.builder()
					.withValue(Short.toString(i))
					.withDescription((i == 1) ? Constantes.LABEL_RESULT_POLE_I18N_KEY: Constantes.LABEL_RESULT_GROUP_GRID_I18N_KEY)
					.build();
			result.add(element);
		}
		return result;
	}

}
