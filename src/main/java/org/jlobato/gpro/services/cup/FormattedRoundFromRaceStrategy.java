package org.jlobato.gpro.services.cup;

import java.text.DecimalFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class FormattedRoundFromRaceStrategy.
 */
public class FormattedRoundFromRaceStrategy extends AbstractRoundFromRaceStrategy {
	
	/** The Constant df. */
	private static final DecimalFormat df = new DecimalFormat("#00");

	/**
	 * Gets the round from race.
	 *
	 * @param idRace the id race
	 * @param idSeason the id season
	 * @return the round from race
	 */
	@Override
	public String getRoundFromRace(Short idRace, Short idSeason) {
		int result = getRoundNo(idRace, idSeason);
		return (result == 13) ? "12_1" : df.format(result);
	}


}
