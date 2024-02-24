package org.jlobato.gpro.services.cup;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * A factory for creating RoundFromRaceStrategy objects.
 */
@Component
public class RoundFromRaceStrategyFactory {
	
	/** The Constant DEFAULT_STRATEGY_KEY. */
	static final Integer DEFAULT_STRATEGY_KEY = Integer.valueOf(1);
	
	/** The Constant ROUND_POST_STRATEGY_KEY. */
	static final Integer ROUND_POST_STRATEGY_KEY = Integer.valueOf(2);
	
	/** The strategies. */
	private static Map<Integer, RoundFromRaceStrategy> strategies;	
	static {
		strategies = new HashMap<>();
		strategies.put(DEFAULT_STRATEGY_KEY, new DefaultRoundFromRaceStrategy());
		strategies.put(ROUND_POST_STRATEGY_KEY, new FormattedRoundFromRaceStrategy());
	}
		
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
	 * Creates the.
	 *
	 * @param impl the impl
	 * @return the round from race strategy
	 */
	public RoundFromRaceStrategy create(int impl) {
		return strategies.get(impl);
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
