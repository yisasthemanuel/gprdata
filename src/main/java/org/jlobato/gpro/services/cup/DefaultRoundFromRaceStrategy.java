package org.jlobato.gpro.services.cup;

import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultRoundFromRaceStrategy.
 */
@Component(AbstractRoundFromRaceStrategy.DEFAULT_STRATEGY)
public class DefaultRoundFromRaceStrategy implements RoundFromRaceStrategy {

	/**
	 * Gets the round from race.
	 *
	 * @param idRace the id race
	 * @param idSeason the id season
	 * @return the round from race
	 */
	@Override
	public String getRoundFromRace(Short idRace, Short idSeason) {
		int result = idRace - 2;
		return (result == 13) ? "12 + 1" : Integer.toString(result);		
	}

}
