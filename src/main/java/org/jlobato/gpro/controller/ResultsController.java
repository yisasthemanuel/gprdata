package org.jlobato.gpro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.facade.FachadaSeason;
import org.jlobato.gpro.dao.mybatis.facade.FachadaTeam;
import org.jlobato.gpro.dao.mybatis.model.Race;
import org.jlobato.gpro.dao.mybatis.model.Season;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private FachadaTeam fachadaTeam;
	
	/**
	 * 
	 */
	@Autowired
	private FachadaSeason fachadaSeason;
	
	/**
	 * 
	 */
	@Autowired
	private FachadaManager fachadaManager;
	
	
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
	public ModelAndView getResults(@RequestParam(value="currentSeason", required=false) String currentSeason)	{
		//Modelo
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("Texto", "Eso es asín");
		
        Season current = null;
        if (currentSeason != null) {
        	current = fachadaSeason.getSeason(new Integer(currentSeason));
        } else {
            current = fachadaSeason.getCurrentSeason();
        }
        
        Race currentRace = fachadaSeason.getCurrentRace();
        
        // Añadimos la lista de managers
		modelAndView.addObject("managersList", fachadaManager.getManagers(fachadaTeam.getDefaultTeam(), currentRace));
        
        modelAndView.addObject("currentSeasonID", current.getIdSeason());
        modelAndView.addObject("currentRace", currentRace.getIdRace());
        
		//Vista
		modelAndView.setViewName("/results/putresults");
		
		return modelAndView;
	}

}
