package org.jlobato.gpro.services.cup;

import java.util.List;
import java.util.Map;

import org.jlobato.gpro.dao.mybatis.facade.FachadaGPRCup;
import org.jlobato.gpro.dao.mybatis.model.Season;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GPROCupService {
	
	private static final transient Logger logger = LoggerFactory.getLogger(GPROCupService.class);
	
	@Autowired 
	private FachadaGPRCup repos;
	
	/**
	 * 
	 * @param season
	 * @return
	 */
	public List<Map<String, Object>> getStatistics(Season season) {
		List<Map<String, Object>> stats = repos.getStatistics(season);
		logger.debug("Service: getStatistics -> " + stats);
		return stats;
	}
	
	/**
	 * 
	 * @param season
	 * @return
	 */
	public List<Map<String, Object>> getSeeding(Season season) {
		List<Map<String, Object>> seed = repos.getSeeding(season);
		logger.debug("Service: getSeeding -> " + seed);
		return seed;
	}
	
	/**
	 * 
	 * @return
	 */
	public Short getSeasonForFirstEdition() {
		return repos.getFirstEditionSeason();
	}


}
