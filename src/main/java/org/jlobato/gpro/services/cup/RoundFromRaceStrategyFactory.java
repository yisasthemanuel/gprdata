package org.jlobato.gpro.services.cup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating RoundFromRaceStrategy objects.
 */
@Component
public class RoundFromRaceStrategyFactory {
		
	/** The default strategy. */
	@Autowired
	@Qualifier(AbstractRoundFromRaceStrategy.DEFAULT_STRATEGY)
	private RoundFromRaceStrategy defaultStrategy;
	
	/** The current strategy. */
	private RoundFromRaceStrategy currentStrategy;
	
	/**
	 * Creates the.
	 *
	 * @return the round from race strategy
	 */
	public RoundFromRaceStrategy create() {
		if (this.currentStrategy == null) {
			this.currentStrategy = this.defaultStrategy;
		}
		
		return this.currentStrategy;
	}

	/**
	 * Gets the default strategy.
	 *
	 * @return the default strategy
	 */
	public RoundFromRaceStrategy getDefaultStrategy() {
		return defaultStrategy;
	}

	/**
	 * Sets the default strategy.
	 *
	 * @param defaultStrategy the new default strategy
	 */
	public void setDefaultStrategy(RoundFromRaceStrategy defaultStrategy) {
		this.defaultStrategy = defaultStrategy;
	}

	/**
	 * Gets the current strategy.
	 *
	 * @return the current strategy
	 */
	public RoundFromRaceStrategy getCurrentStrategy() {
		return currentStrategy;
	}

	/**
	 * Sets the current strategy.
	 *
	 * @param currentStrategy the new current strategy
	 */
	public void setCurrentStrategy(RoundFromRaceStrategy currentStrategy) {
		this.currentStrategy = currentStrategy;
	}

}
