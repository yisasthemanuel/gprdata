package org.jlobato.gpro.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jlobato.gpro.dao.mybatis.facade.FachadaGPRCup;
import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.facade.FachadaSeason;
import org.jlobato.gpro.dao.mybatis.model.CupStandingsSnapshot;
import org.jlobato.gpro.dao.mybatis.model.Manager;
import org.jlobato.gpro.dao.mybatis.model.Race;
import org.jlobato.gpro.dao.mybatis.model.Season;
import org.jlobato.gpro.dao.mybatis.model.Team;
import org.jlobato.gpro.services.cup.GPROCupService;
import org.jlobato.gpro.services.templates.TemplateException;
import org.jlobato.gpro.services.templates.TemplateModelBuilder;
import org.jlobato.gpro.services.templates.TemplateService;
import org.jlobato.gpro.utils.RomanNumeral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * The Class GprcupController.
 *
 * @author jlobato
 */
@Controller
@RequestMapping("/gprcup")
public class GprcupController {
	
	
	private static final String TEXT_PLAIN = "text/plain";

	private static final String ATTACHMENT_FILENAME = "attachment; filename=";

	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	/** The Constant CURRENT_CUP_STANDINGS. */
	private static final String CURRENT_CUP_STANDINGS = "currentCupStandings";

	/** The Constant CURRENT_SEASON. */
	private static final String CURRENT_SEASON = "currentSeason";

	/** The Constant CURRENT_RACE. */
	private static final String CURRENT_RACE = "currentRace";

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GprcupController.class);
	
	/** The template service. */
	@Autowired
	private TemplateService templateService;
	
	/** The fachada season. */
	@Autowired
	private FachadaSeason fachadaSeason;
	

	/** The fachada manager. */
	@Autowired
	private FachadaManager fachadaManager;
	
	/** The fachada cup. */
	@Autowired
	private FachadaGPRCup fachadaCup;
	
	/** The cup service. */
	@Autowired
	private GPROCupService cupService;
	
	@Autowired
	private SessionLocaleResolver locale;
	
	@Value("${gpro.gpr.cup.rules.url}")
	private String cupRulesURL;

	/**
	 * Cup.
	 *
	 * @param request the request
	 * @param session the session
	 * @return the model and view
	 */
	@GetMapping(value = "/gprcup.html")
	public ModelAndView cup(HttpServletRequest request, HttpSession session)	{
		logger.debug("GprcupController.cup - begin");
		
        ModelAndView modelAndView = new ModelAndView();
        
        //Hago el sendredirect para que pille el tema gprcup
		String view = "redirect:/gprcup/main.html";
        modelAndView.setViewName(view);
        
		logger.debug("GprcupController.cup - end");
		return modelAndView;
	}
	
	/**
	 * Main.
	 *
	 * @param request the request
	 * @param currentSeason the current season
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping(value = "/main.html", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView main(HttpServletRequest request,
			 @RequestParam(value=CURRENT_SEASON, required=false) String currentSeason,
			HttpSession session)	{
		logger.debug("GprcupController.main - begin");
		
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("/gprcup/main");
        
        //Pedimos al delegado de negocio que nos pase la lista con los managers disponibles
        List<Manager> managers = fachadaManager.getManagersList();
        
        Season current = null;
        if (currentSeason != null) {
        	current = fachadaSeason.getSeason(Integer.valueOf(currentSeason));
        } else {
            current = fachadaSeason.getCurrentSeason();
        }
        modelAndView.addObject("lastSeason", current.getIdSeason());
        
        Team team = fachadaCup.getDefaultTeam();
        
        CupStandingsSnapshot standings = fachadaCup.getStandings(current, team);
        
        if (standings != null) {
        	session.setAttribute(CURRENT_CUP_STANDINGS, standings);
        	modelAndView.addObject("lastRace", standings.getIdRace());
        	modelAndView.addObject("lastSeason", standings.getIdSeason());
            logger.info("GprcupController.main - Clasificación correspondiente a la carrera: {}", standings.getIdRace());
        }
        else {
        	// Si no hay datos de la copa, se elimina el atributo
        	session.removeAttribute(CURRENT_CUP_STANDINGS);
        }
        
        modelAndView.addObject("managersList", managers);
        modelAndView.addObject("racesList", fachadaSeason.getRaces(current));
        modelAndView.addObject("seasonList", fachadaSeason.getAvailableSeasons());
        modelAndView.addObject("GPROCupRulesURL", cupRulesURL);
        
        logger.info("GprcupController.main - Temporada actual: {}", current.getNameSeason());
        modelAndView.addObject(CURRENT_SEASON, current);
        
		logger.debug("GprcupController.main - end");
		return modelAndView;

	}
	
	/**
	 * Show standings.
	 *
	 * @param request the request
	 * @param session the session
	 * @return the model and view
	 * @throws ParseException 
	 */
	@PostMapping(value = "/showStandings.html")
	public ModelAndView showStandings(HttpServletRequest request, HttpSession session) throws ParseException {
		logger.debug("GprcupController.showStandings - begin");
		
        ModelAndView modelAndView = new ModelAndView();
        
        //Creamos el objeto CupStandings
        CupStandingsSnapshot cupStandings = getCupStandings(request);
              
        //Colocamos en sesión la clasificación actual
        session.setAttribute(CURRENT_CUP_STANDINGS, cupStandings);
        
        //Colocamos los objetos managers
        modelAndView.addObject("QF01", fachadaManager.getManager(cupStandings.getIdManagerQf1()));
        modelAndView.addObject("QF02", fachadaManager.getManager(cupStandings.getIdManagerQf2()));
        modelAndView.addObject("QF03", fachadaManager.getManager(cupStandings.getIdManagerQf3()));
        modelAndView.addObject("QF04", fachadaManager.getManager(cupStandings.getIdManagerQf4()));
        modelAndView.addObject("QF05", fachadaManager.getManager(cupStandings.getIdManagerQf5()));
        modelAndView.addObject("QF06", fachadaManager.getManager(cupStandings.getIdManagerQf6()));
        modelAndView.addObject("QF07", fachadaManager.getManager(cupStandings.getIdManagerQf7()));
        modelAndView.addObject("QF08", fachadaManager.getManager(cupStandings.getIdManagerQf8()));

        modelAndView.addObject("SF01", fachadaManager.getManager(cupStandings.getIdManagerSf1()));
        modelAndView.addObject("SF02", fachadaManager.getManager(cupStandings.getIdManagerSf2()));
        modelAndView.addObject("SF03", fachadaManager.getManager(cupStandings.getIdManagerSf3()));
        modelAndView.addObject("SF04", fachadaManager.getManager(cupStandings.getIdManagerSf4()));
        
        modelAndView.addObject("FI01", fachadaManager.getManager(cupStandings.getIdManagerFi1()));
        modelAndView.addObject("FI02", fachadaManager.getManager(cupStandings.getIdManagerFi2()));
        
        modelAndView.addObject("WI01", fachadaManager.getManager(cupStandings.getIdManagerWinner()));
        
        //Vista a preview
        modelAndView.setViewName("/gprcup/preview");
        
		logger.debug("GprcupController.showStandings - end");
		return modelAndView;

	}

	/**
	 * Gets the cup standings.
	 *
	 * @param request the request
	 * @return the cup standings
	 * @throws ParseException 
	 */
	private CupStandingsSnapshot getCupStandings(HttpServletRequest request) throws ParseException {
		CupStandingsSnapshot result = new CupStandingsSnapshot();
        
        DecimalFormat formatter = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(locale.resolveLocale(request)));
        
		//Managers
        String manager01 = request.getParameter("qfManager01");
        logger.debug("GprcupController.getCupStandings.qfManager01 - {}", manager01);
        
        String manager02 = request.getParameter("qfManager02");
        logger.debug("GprcupController.getCupStandings.qfManager02 - {}", manager02);
        
        String manager03 = request.getParameter("qfManager03");
        logger.debug("GprcupController.getCupStandings.qfManager03 - {}", manager03);
        
        String manager04 = request.getParameter("qfManager04");
        logger.debug("GprcupController.getCupStandings.qfManager04 - {}", manager04);
        
        String manager05 = request.getParameter("qfManager05");
        logger.debug("GprcupController.getCupStandings.qfManager05 - {}", manager05);
        
        String manager06 = request.getParameter("qfManager06");
        logger.debug("GprcupController.getCupStandings.qfManager06 - {}", manager06);
        
        String manager07 = request.getParameter("qfManager07");
        logger.debug("GprcupController.getCupStandings.qfManager07 - {}", manager07);
        
        String manager08 = request.getParameter("qfManager08");
        logger.debug("GprcupController.getCupStandings.qfManager08 - {}", manager08);
        
        String sfmanager01 = request.getParameter("sfManager01");
        logger.debug("GprcupController.getCupStandings.sfManager01 - {}", sfmanager01);
        
        String sfmanager02 = request.getParameter("sfManager02");
        logger.debug("GprcupController.getCupStandings.sfManager02 - {}", sfmanager02);
        
        String sfmanager03 = request.getParameter("sfManager03");
        logger.debug("GprcupController.getCupStandings.sfManager03 - {}", sfmanager03);
        
        String sfmanager04 = request.getParameter("sfManager04");
        logger.debug("GprcupController.getCupStandings.sfManager04 - {}", sfmanager04);
        
        String fmanager01 = request.getParameter("fManager01");
        logger.debug("GprcupController.getCupStandings.fManager01 - {}", fmanager01);
        
        String fmanager02 = request.getParameter("fManager02");
        logger.debug("GprcupController.getCupStandings.fManager02 - {}", fmanager02);
        
        String fmanagerWinner = request.getParameter("fManagerWinner");
        logger.debug("GprcupController.getCupStandings.fManagerWinner - {}", fmanagerWinner);
        

        //Scores
        String qfmanager01Score = request.getParameter("qfManager01Score");
        logger.debug("GprcupController.getCupStandings.qfManager01Score - {}", qfmanager01Score);
        
        String qfmanager02Score = request.getParameter("qfManager02Score");
        logger.debug("GprcupController.getCupStandings.qfManager02Score - {}", qfmanager02Score);
        
        String qfmanager03Score = request.getParameter("qfManager03Score");
        logger.debug("GprcupController.getCupStandings.qfManager03Score - {}", qfmanager03Score);
        
        String qfmanager04Score = request.getParameter("qfManager04Score");
        logger.debug("GprcupController.getCupStandings.qfManager04Score - {}", qfmanager04Score);
        
        String qfmanager05Score = request.getParameter("qfManager05Score");
        logger.debug("GprcupController.getCupStandings.qfManager05Score - {}", qfmanager05Score);
        
        String qfmanager06Score = request.getParameter("qfManager06Score");
        logger.debug("GprcupController.getCupStandings.qfManager06Score - {}", qfmanager06Score);
        
        String qfmanager07Score = request.getParameter("qfManager07Score");
        logger.debug("GprcupController.getCupStandings.qfManager07Score - {}", qfmanager07Score);
        
        String qfmanager08Score = request.getParameter("qfManager08Score");
        logger.debug("GprcupController.getCupStandings.qfManager08Score - {}", qfmanager08Score);
        
        String sfmanager01Score = request.getParameter("sfManager01Score");
        logger.debug("GprcupController.getCupStandings.sfManager01Score - {}", sfmanager01Score);
        
        String sfmanager02Score = request.getParameter("sfManager02Score");
        logger.debug("GprcupController.getCupStandings.sfManager02Score - {}", sfmanager02Score);
        
        String sfmanager03Score = request.getParameter("sfManager03Score");
        logger.debug("GprcupController.getCupStandings.sfManager03Score - {}", sfmanager03Score);
        
        String sfmanager04Score = request.getParameter("sfManager04Score");
        logger.debug("GprcupController.getCupStandings.sfManager04Score - {}", sfmanager04Score);
        
        String fmanager01Score = request.getParameter("fManager01Score");
        logger.debug("GprcupController.getCupStandings.fManager01Score - {}", fmanager01Score);
        
        String fmanager02Score = request.getParameter("fManager02Score");
        logger.debug("GprcupController.getCupStandings.fManager02Score - {}", fmanager02Score);
        
        setQF4Managers(result, manager01, manager02, manager03, manager04);
        setQF8Managers(result, manager05, manager06, manager07, manager08);
        
        setQF4Scores(result, qfmanager01Score, qfmanager02Score, qfmanager03Score, qfmanager04Score, formatter);
        setQF8Scores(result, qfmanager05Score, qfmanager06Score, qfmanager07Score, qfmanager08Score, formatter);

        setSFManagers(result, sfmanager01, sfmanager02, sfmanager03, sfmanager04);
        
        setSFScores(result, sfmanager01Score, sfmanager02Score, sfmanager03Score, sfmanager04Score, formatter);
        
        setFinalManagerScores(result, fmanager01, fmanager02, fmanagerWinner, fmanager01Score, fmanager02Score, formatter);
        
        //Recuperamos la carrera para la que se guarda la clasificación
        Race theRace = fachadaSeason.getRace(Short.valueOf(request.getParameter(CURRENT_RACE)));
        if (theRace != null) {
            result.setIdRace(theRace.getIdRace());
        }
        
        result.setIdSeason(Short.valueOf(request.getParameter(CURRENT_SEASON)));
        
        //Tomamos equipo por defecto. Esto en un futuro será el equipo asignado al usuario
        result.setIdTeam(fachadaCup.getDefaultTeam().getIdTeam());
        
        return result;
	}

	private void setQF8Scores(CupStandingsSnapshot result, String qfmanager05Score, String qfmanager06Score,
			String qfmanager07Score, String qfmanager08Score, DecimalFormat formatter) throws ParseException {
		result.setScoreManagerQf5((qfmanager05Score != null && !"".equals(qfmanager05Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager05Score).doubleValue()) : null);
        result.setScoreManagerQf6((qfmanager06Score != null && !"".equals(qfmanager06Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager06Score).doubleValue()) : null);
        result.setScoreManagerQf7((qfmanager07Score != null && !"".equals(qfmanager07Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager07Score).doubleValue()) : null);
        result.setScoreManagerQf8((qfmanager08Score != null && !"".equals(qfmanager08Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager08Score).doubleValue()) : null);
	}

	private void setQF4Scores(CupStandingsSnapshot result, String qfmanager01Score, String qfmanager02Score,
			String qfmanager03Score, String qfmanager04Score, DecimalFormat formatter) throws ParseException {
		result.setScoreManagerQf1((qfmanager01Score != null && !"".equals(qfmanager01Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager01Score).doubleValue()) : null);
        result.setScoreManagerQf2((qfmanager02Score != null && !"".equals(qfmanager02Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager02Score).doubleValue()) : null);
        result.setScoreManagerQf3((qfmanager03Score != null && !"".equals(qfmanager03Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager03Score).doubleValue()) : null);
        result.setScoreManagerQf4((qfmanager04Score != null && !"".equals(qfmanager04Score)) ? BigDecimal.valueOf(formatter.parse(qfmanager04Score).doubleValue()) : null);
	}

	private void setFinalManagerScores(CupStandingsSnapshot result, String fmanager01, String fmanager02,
			String fmanagerWinner, String fmanager01Score, String fmanager02Score, DecimalFormat formatter) throws ParseException {
		result.setIdManagerFi1(getManagerID(fmanager01));
        result.setIdManagerFi2(getManagerID(fmanager02));
                
        result.setScoreManagerFi1((fmanager01Score != null && !"".equals(fmanager01Score)) ? BigDecimal.valueOf(formatter.parse(fmanager01Score).doubleValue()) : null);
        result.setScoreManagerFi2((fmanager02Score != null && !"".equals(fmanager02Score)) ? BigDecimal.valueOf(formatter.parse(fmanager02Score).doubleValue()) : null);
        
        result.setIdManagerWinner(getManagerID(fmanagerWinner));
	}

	private void setSFScores(CupStandingsSnapshot result, String sfmanager01Score, String sfmanager02Score,
			String sfmanager03Score, String sfmanager04Score, DecimalFormat formatter) throws ParseException {
		result.setScoreManagerSf1((sfmanager01Score != null && !"".equals(sfmanager01Score)) ? BigDecimal.valueOf(formatter.parse(sfmanager01Score).doubleValue()) : null);
        result.setScoreManagerSf2((sfmanager02Score != null && !"".equals(sfmanager02Score)) ? BigDecimal.valueOf(formatter.parse(sfmanager02Score).doubleValue()) : null);
        result.setScoreManagerSf3((sfmanager03Score != null && !"".equals(sfmanager03Score)) ? BigDecimal.valueOf(formatter.parse(sfmanager03Score).doubleValue()) : null);
        result.setScoreManagerSf4((sfmanager04Score != null && !"".equals(sfmanager04Score)) ? BigDecimal.valueOf(formatter.parse(sfmanager04Score).doubleValue()) : null);
	}

	private void setSFManagers(CupStandingsSnapshot result, String sfmanager01, String sfmanager02, String sfmanager03,
			String sfmanager04) {
		result.setIdManagerSf1(getManagerID(sfmanager01));
        result.setIdManagerSf2(getManagerID(sfmanager02));
        result.setIdManagerSf3(getManagerID(sfmanager03));
        result.setIdManagerSf4(getManagerID(sfmanager04));
	}

	private void setQF8Managers(CupStandingsSnapshot result, String manager05, String manager06, String manager07,
			String manager08) {
		result.setIdManagerQf5(getManagerID(manager05));
        result.setIdManagerQf6(getManagerID(manager06));
        result.setIdManagerQf7(getManagerID(manager07));
        result.setIdManagerQf8(getManagerID(manager08));
	}

	private void setQF4Managers(CupStandingsSnapshot result, String manager01, String manager02, String manager03,
			String manager04) {
		result.setIdManagerQf1(getManagerID(manager01));
        result.setIdManagerQf2(getManagerID(manager02));
        result.setIdManagerQf3(getManagerID(manager03));
        result.setIdManagerQf4(getManagerID(manager04));
	}
	
	/**
	 * Gets the manager ID.
	 *
	 * @param id the id
	 * @return the manager ID
	 */
	private Short getManagerID(String id) {
		Short result = null;
		try {
			result = Short.valueOf(id);
		} catch(Exception e) {
			// Do nothing
		}
		return result;
	}

	/**
	 * Save standings.
	 *
	 * @param request the request
	 * @param session the session
	 * @return the model and view
	 * @throws ParseException 
	 */
	@PostMapping(value = "/saveStandings.html")
	public ModelAndView saveStandings(HttpServletRequest request, HttpSession session) throws ParseException {
		logger.debug("GprcupController.saveStandings - begin");
		
        //Creamos el objeto CupStandings
        CupStandingsSnapshot cupStandings = getCupStandings(request);
        
        //Le decimos que lo guarde
        fachadaCup.saveStandings(cupStandings);
		
		//Redirigimos a la página principal. Esto se puede mejorar
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CURRENT_SEASON, cupStandings.getIdSeason());
        modelAndView.addObject(CURRENT_RACE, cupStandings.getIdRace());
        String view = "redirect:/gprcup/main.html";
        modelAndView.setViewName(view);
        
		logger.debug("GprcupController.saveStandings - end");
		
		return modelAndView;
	}
	
	/**
	 * Export seeding.
	 *
	 * @param request the request
	 * @param response the response
	 * @param idSeason the id season
	 * @param idRace the id race
	 * @throws TemplateException the template exception
	 */
	@GetMapping(value = "/seeding.html")
	public void exportSeeding(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value=CURRENT_SEASON, required=false) String idSeason,
			@RequestParam(value=CURRENT_RACE, required=false) String idRace) throws TemplateException {
		logger.debug("GprcupController.exportSeeding - begin");
        
        Season season = null;
        if (idSeason != null) {
        	season = fachadaSeason.getSeason(Integer.valueOf(idSeason));
        } else {
        	season = fachadaSeason.getCurrentSeason();
        }
        
        TemplateModelBuilder builder = TemplateModelBuilder.newInstance();
        builder.add("stats", cupService.getSeeding(season))
        	.add("edition_no", RomanNumeral.toRoman((season.getIdSeason() - cupService.getSeasonForFirstEdition()) + 1))
        	.add("participants", 8);
        
        String result = templateService.processTemplate("plantilla.seed.ftl", builder.build());
		logger.debug("GprcupController.exportSeeding - RESULT - {}", result);
		
		
        //TODO Redirigir a una página genérica de download
        
        if (result != null) {
        	response.setContentType(TEXT_PLAIN);
        	response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + "seeding_post.txt");
        	try {
				response.getWriter().write(result);
			} catch (IOException e) {
				//TODO - Tratar esto con una página de error
				logger.error(e.getMessage(), e);
			}
        }
        
		logger.debug("GprcupController.exportSeeding - end");
	}
	
	/**
	 * Export statistics.
	 *
	 * @param request the request
	 * @param response the response
	 * @param idSeason the id season
	 * @param idRace the id race
	 * @throws TemplateException the template exception
	 */
	@GetMapping(value = "/statistics.html")
	public void exportStatistics(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value=CURRENT_SEASON, required=false) String idSeason,
			@RequestParam(value=CURRENT_RACE, required=false) String idRace
			) throws TemplateException {
		logger.debug("GprcupController.exportStatistics - begin");
        
        Season season = null;
        if (idSeason != null) {
        	season = fachadaSeason.getSeason(Integer.valueOf(idSeason));
        } else {
        	season = fachadaSeason.getCurrentSeason();
        }
        
        TemplateModelBuilder builder = TemplateModelBuilder.newInstance();
        builder.add("stats", cupService.getStatistics(season)).add("editions_no", (season.getIdSeason() - cupService.getSeasonForFirstEdition()) + 1);
		
        String result = templateService.processTemplate("statistics.report.ftl", builder.build());
		logger.debug("GprcupController.exportStatistics - RESULT - {}", result);
		
        
        //TODO Redirigir a una página genérica de download
        if (result != null) {
        	response.setContentType(TEXT_PLAIN);
        	response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + "statistics_post.txt");
        	try {
				response.getWriter().write(result);
			} catch (IOException e) {
				//TODO - Tratar esto con una página de error
				logger.error(e.getMessage(), e);
			}
        }
        
		logger.debug("GprcupController.exportStatistics - end");
	}
	
	/**
	 * Export current status.
	 *
	 * @param request the request
	 * @param session the session
	 * @param response the response
	 * @param screenshotURL the screenshot URL
	 * @throws TemplateException the template exception
	 * @throws ParseException 
	 */
	@PostMapping(value = "/round.html")
	public void exportCurrentStatus(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			String screenshotURL) throws TemplateException, ParseException {
		logger.debug("GprcupController.exportStatistics - begin");
		CupStandingsSnapshot cupStandings = getCupStandings(request);

		//Id de la carrera. La jornada es el número de carrera menos dos
		Short idRace = cupStandings.getIdRace();

		//Id de la temporada.
		Short idSeason = cupStandings.getIdSeason();
		

		TemplateModelBuilder builder = TemplateModelBuilder.newInstance()
				.add("round_no", cupService.getRoundFromRace(idRace, idSeason)) 
				.add("edition_no", RomanNumeral.toRoman((idSeason - cupService.getSeasonForFirstEdition()) + 1))
				.add("round_screenshot_url", (screenshotURL == null) ? "no url specified" : screenshotURL);

		String result = templateService.processTemplate("round.ftl", builder.build());
		logger.debug("GprcupController.exportCurrentStatus - RESULT - {}", result);

		//TODO Redirigir a una página genérica de download
		if (result != null) {
			response.setContentType(TEXT_PLAIN);
			response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + "round_post_" + cupService.getRoundForPostFromRace(idRace, idSeason) + ".txt");
			try {
				response.getWriter().write(result);
			} catch (IOException e) {
				//TODO - Tratar esto con una página de error
				logger.error(e.getMessage(), e);
			}
		}

		logger.debug("GprcupController.exportCurrentStatus - end");
	}
}
