package org.jlobato.gpro.services.cup;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractRoundFromRaceStrategy.
 */
public abstract class AbstractRoundFromRaceStrategy implements RoundFromRaceStrategy {
	
	/** The Constant DEFAULT_STRATEGY. */
	public static final String DEFAULT_STRATEGY = "defaultRoundFromRaceStrategy";

	/**
	 * Gets the round from race.
	 *
	 * @param idRace the id race
	 * @param idSeason the id season
	 * @return the round from race
	 */
	@Override
	public String getRoundFromRace(Short idRace, Short idSeason) {
		return Integer.toString(idRace);
	}
	
	
	/**
	 * Gets the round no.
	 *
	 * @param idRace the id race
	 * @param idSeason the id season
	 * @return the round no
	 */
	int getRoundNo(Short idRace, Short idSeason) {
		return idRace - 2;
	}

}
