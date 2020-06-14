package org.jlobato.gpro.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jlobato.gpro.dao.mybatis.facade.FachadaSeason;
import org.jlobato.gpro.dao.mybatis.model.ManagerResult;
import org.jlobato.gpro.dao.mybatis.model.Race;
import org.jlobato.gpro.dao.mybatis.model.Season;
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

/**
 * 
 * @author JLOBATO
 *
 */
@Controller
@RequestMapping("/results")
public class ResultsController {
	
	/**
	 * 
	 */
	@Autowired
	private FachadaSeason fachadaSeason;
	
	@Value("${url.prefix.managers}")
	private String hostGetManagers;
	
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);
	

	@GetMapping(value = "/allraces", produces = "application/json")
	public @ResponseBody String allraces(HttpServletRequest request, HttpSession session, String codSeason)	{
		logger.debug("ResultsController.getSeasonRaces - begin for season {}", request.getParameter("codSeason"));
		logger.debug("ResultsController.getSeasonRaces - begin for season {} ", codSeason);
		logger.debug("ResultsController.getSeasonRaces - end for season {}", request.getParameter("codSeason"));
		return "{codigo: apatrullando}";
	}
	
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@GetMapping(value = "/results")
	public ModelAndView getResults(
			@RequestParam(value="currentSeason", required=false) String currentSeason,
			@RequestParam(value="currentRace", required=false) String currentRace)	{
		
		// TODO: Meter equipo por defecto 
		
		//Modelo
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("Texto", "Eso es asín");
		
		// Get the season object
        Season season = null;
        if (currentSeason != null) {
        	season = fachadaSeason.getSeason(new Integer(currentSeason));
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
		List<ManagerResult> results = restTemplate.getForObject(hostGetManagers + "managers/results/" + season.getIdSeason() + "/" + race.getIdRace(), List.class);
		
		modelAndView.addObject("gproresultsUrlUpdate", hostGetManagers + "managers/results");
		
        // Añadimos la lista de managers en función de la carrera actual
		modelAndView.addObject("managersList", results);
		
		
        // Combo de temporadas
        modelAndView.addObject("seasonList", fachadaSeason.getAvailableSeasons());
        // Combo de carreras
        modelAndView.addObject("racesList", fachadaSeason.getRaces(season));
        
        
		//Vista
		modelAndView.setViewName("/results/putresults");
		
		return modelAndView;
	}

}
