package org.jlobato.gpro.services.cup;

import java.util.List;
import java.util.Map;

import org.jlobato.gpro.dao.mybatis.facade.FachadaGPRCup;
import org.jlobato.gpro.dao.mybatis.model.Season;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class GPROCupService.
 */
@Service
public class GPROCupService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GPROCupService.class);
	
	/** The repos. */
	@Autowired 
	private FachadaGPRCup repos;
	
	/** The round from frace strategy factory. */
	@Autowired
	private RoundFromRaceStrategyFactory roundFromFraceStrategyFactory;
	
	/**
	 * Gets the statistics.
	 *
	 * @param season the season
	 * @return the statistics
	 */
	public List<Map<String, Object>> getStatistics(Season season) {
		List<Map<String, Object>> stats = repos.getStatistics(season);
		logger.debug("Service: getStatistics -> {}", stats);
		return stats;
	}
	
	/**
	 * Gets the seeding.
	 *
	 * @param season the season
	 * @return the seeding
	 */
	public List<Map<String, Object>> getSeeding(Season season) {
		List<Map<String, Object>> seed = repos.getSeeding(season);
		logger.debug("Service: getSeeding -> {}", seed);
		return seed;
	}
	
	/**
	 * Gets the season for first edition.
	 *
	 * @return the season for first edition
	 */
	public Short getSeasonForFirstEdition() {
		return repos.getFirstEditionSeason();
	}
	
	/**
	 * Gets the round from race.
	 *
	 * @param idRace the id race
	 * @param idSeason the id season
	 * @return the round from race
	 */
	public String getRoundFromRace(Short idRace, Short idSeason) {
		return roundFromFraceStrategyFactory.create().getRoundFromRace(idRace, idSeason);
	}
}
