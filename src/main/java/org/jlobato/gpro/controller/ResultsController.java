package org.jlobato.gpro.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.jlobato.gpro.dao.mybatis.facade.FachadaCategory;
import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.facade.FachadaSeason;
import org.jlobato.gpro.dao.mybatis.model.ManagerHistory;
import org.jlobato.gpro.dao.mybatis.model.ManagerResult;
import org.jlobato.gpro.dao.mybatis.model.Race;
import org.jlobato.gpro.dao.mybatis.model.Season;
import org.jlobato.gpro.data.DropdownModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The Class ResultsController.
 *
 * @author JLOBATO
 */
@Controller
@RequestMapping("/results")
public class ResultsController {
	
	/** Lo que en un futuro será un cliente a un microservicio. */
	@Autowired
	private FachadaSeason fachadaSeason;
	
	/** Lo que en un futuro será un cliente a un microservicio. */
	@Autowired
	private FachadaManager fachadaManager;
	
	
	/** Lo que en un futuro será un cliente a un microservicio. */
	@Autowired
	private FachadaCategory fachadaCategory;
	
	@Value("${url.prefix.categories.microservice}")
	private String hostCategoriesMicroservice;
	
	/** The host get managers. */
	@Value("${url.prefix.managers}")
	private String hostManagerMicroservice;
	
	/** The host GPRO. */
	@Value("${gpro.web.url}")
	private String hostGPRO;
	
	@Autowired
	private List<String> positionList;
	
	@Autowired
	private List<DropdownModel> racePositionList;
	
	@Autowired
	private List<DropdownModel> gridPositionList;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);

	/**
	 * Allraces.
	 *
	 * @param request the request
	 * @param session the session
	 * @param codSeason the cod season
	 * @return the string
	 */
	@GetMapping(value = "/allraces", produces = "application/json")
	public @ResponseBody String allraces(HttpServletRequest request, HttpSession session, @RequestParam("codSeason") String codSeason)	{
		logger.debug("ResultsController.getSeasonRaces - begin for season {}", request.getParameter("codSeason"));
		logger.debug("ResultsController.getSeasonRaces - begin for season {} ", codSeason);
		logger.debug("ResultsController.getSeasonRaces - end for season {}", request.getParameter("codSeason"));
		return "{codigo: apatrullando}";
	}
	
	/**
	 * Gets the results.
	 *
	 * @param currentSeason the current season
	 * @param currentRace the current race
	 * @return the results
	 */
	@GetMapping(value = "/results.html")
	public ModelAndView getResults(
			@RequestParam(value="currentSeason", required=false) String currentSeason,
			@RequestParam(value="currentRace", required=false) String currentRace) {
		
		// TODO: Meter equipo por defecto 
		
		//Modelo
		ModelAndView modelAndView = new ModelAndView();
		
		// Get the season object
        Season season = null;
        if (currentSeason != null) {
        	season = fachadaSeason.getSeason(Integer.valueOf(currentSeason));
        } else {
            season = fachadaSeason.getCurrentSeason();
        }
        
        // Get the race object
        Race race = null;
        if (currentRace != null) {
        	race = fachadaSeason.getRace(season.getIdSeason(), Integer.parseInt(currentRace));
        }
        else {
        	race = fachadaSeason.getCurrentRace();
        }
        
		// Temporada y carrera actual
        modelAndView.addObject("currentSeasonID", season.getIdSeason());
        modelAndView.addObject("currentRaceID", race.getIdRace());
        
		// Añadimos la llamada al microservicio
		RestTemplate restTemplate = new RestTemplate();
		@SuppressWarnings("unchecked")
		List<ManagerResult> results = restTemplate.getForObject(hostManagerMicroservice + "managers/results/" + season.getIdSeason() + "/" + race.getIdRace(), List.class);
		
		// Se incluye el salto para actualizar los resultados
		modelAndView.addObject("gproresultsUrlUpdate", hostManagerMicroservice + "managers/results");
		modelAndView.addObject("gproresultsPositionsUrlUpdate", hostManagerMicroservice + "managers/update-position");
		
		// Se añade la información sobre el grupo al que pertenece cada manager y se genera el correspondiente enlace
		List<String> history = new ArrayList<>(); 
		List<String> urls = new ArrayList<>();
		List<String> positions = new ArrayList<>();
		if (results != null) {
			final Short seasonId = season.getIdSeason();
			
			for (int i = 0; i < results.size(); i++) {
				String code;
				try {
					code = PropertyUtils.getProperty(results.get(i), "codeManager").toString();
					populateGroupName(
							fachadaManager.getManagerHistory(fachadaManager.getManagerByCode(code).getIdManager(), seasonId),
							history,
							urls,
							positions);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					logger.error("Error getting code manager from list");
				}
			}

			// Añadimos la lista de managers en función de la carrera actual
			modelAndView.addObject("managersList", results);
			modelAndView.addObject("history", history);
			modelAndView.addObject("urls", urls);
			modelAndView.addObject("currentPositions", positions);
			modelAndView.addObject("positionList", positionList);
			modelAndView.addObject("racePositionList", racePositionList);
			modelAndView.addObject("gridPositionList", gridPositionList);
		}
		
		
        // Combo de temporadas
        modelAndView.addObject("seasonList", fachadaSeason.getAvailableSeasons());
        // Combo de carreras
        modelAndView.addObject("racesList", fachadaSeason.getRaces(season));
		//Vista
		modelAndView.setViewName("/results/putresults");
		
		return modelAndView;
	}
	
	/**
	 * Gets the results.
	 *
	 * @param currentSeason the current season
	 * @param currentRace the current race
	 * @return the results
	 */
	@GetMapping(value = "/results-manager.html")
	public ModelAndView getResultsManager(
			@RequestParam(value="currentSeason", required=true) String currentSeason) {
		
		// TODO: Meter equipo por defecto 
		//Modelo
		ModelAndView modelAndView = new ModelAndView();
		
		// Get the season object
        Season season = fachadaSeason.getSeason(Integer.valueOf(currentSeason));
        
		// Temporada actual
        modelAndView.addObject("currentSeasonID", season.getIdSeason());
        
        // Combo de temporadas
        modelAndView.addObject("seasonList", fachadaSeason.getAvailableSeasons());
        
        // Combo posiciones
		modelAndView.addObject("racePositionList", racePositionList);
		modelAndView.addObject("gridPositionList", gridPositionList);
        
        // Carreras
        List<Race> races = fachadaSeason.getRaces(season);
        modelAndView.addObject("racesList", races);
        
        // Managers
        List<ManagerResult> managers = new ArrayList<>();
        
		// Añadimos la llamada al microservicio por cada carrera
        Iterator<Race> racesIterator = races.iterator();
		RestTemplate restTemplate = new RestTemplate();
        while (racesIterator.hasNext()) {
        	Race theRace = racesIterator.next();
    		@SuppressWarnings("unchecked")
    		List<ManagerResult> results = restTemplate.getForObject(hostManagerMicroservice + "managers/results/" + season.getIdSeason() + "/" + theRace.getIdRace(), List.class);
        	
    		// Se añade la información sobre el grupo al que pertenece cada manager y se genera el correspondiente enlace
    		List<String> history = new ArrayList<>(); 
    		List<String> positions = new ArrayList<>();
    		if (results != null) {
    			final Short seasonId = season.getIdSeason();
    			
    			for (int i = 0; i < results.size(); i++) {
    				String code;
    				try {
    					code = PropertyUtils.getProperty(results.get(i), "codeManager").toString();
    					populateGroupName(
    							fachadaManager.getManagerHistory(fachadaManager.getManagerByCode(code).getIdManager(), seasonId),
    							history,
    							null,
    							positions);
    				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
    					logger.error("Error getting code manager from list");
    				}
    			}

    			// Añadimos la lista de managers en función de la carrera actual
    			modelAndView.addObject("managersList", managers);
    			modelAndView.addObject("history", history);
    			modelAndView.addObject("currentPositions", positions);
    			modelAndView.addObject("positionList", positionList);
    		}
        }
        
		
		modelAndView.setViewName("/results/putresultsmanager");
		return modelAndView;
	}
	
	/**
	 * Populate group name.
	 *
	 * @param managerHistory the manager history
	 * @param history the history
	 * @param urls the urls
	 * @param positions 
	 */
	private void populateGroupName(List<ManagerHistory> managerHistory, List<String> history, List<String> urls, List<String> positions) {
		
		managerHistory.forEach(hist -> {
			if (urls != null) {
				String category = "";
				if (hist.getIdGroup() == null) {
					//Aquí se llamaría a getManagerHistory
					category = fachadaCategory.getCategory(hist.getIdCategory()).getDescriptionCategory();
				}
				else {
					//Aquí se llamaría a getManagerHistory
					category = fachadaCategory.getCategory(hist.getIdCategory()).getDescriptionCategory() + " - " + hist.getIdGroup().toString(); 
				}
				history.add(category);
				
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.hostGPRO).pathSegment("Standings.asp").queryParam("Group", category);
				urls.add(builder.toUriString());
			}
			
			positions.add(Short.toString(hist.getPosition()));
		});
	}

	/**
	 * 
	 * @param hist
	 */
	@SuppressWarnings("unused")
	private void getManagerHistoryInfo(ManagerHistory hist) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.hostCategoriesMicroservice).pathSegment("categories").pathSegment(Short.toString(hist.getIdCategory()));
		String endpoint = builder.toUriString();
		RestTemplate restTemplate = new RestTemplate();
		Object result = restTemplate.getForObject(endpoint, Object.class);
		try {
			String desc = PropertyUtils.getProperty(result, "descriptionCategory").toString();
			logger.info("Description category {}", desc);
		} catch (Exception e) {
			logger.error(e.getClass().getName() + " - " + e.getMessage(), e);
		}
	}
}
