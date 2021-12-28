package org.jlobato.gpro.test.calendar;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.jlobato.gpro.dao.mybatis.facade.FachadaSeason;
import org.jlobato.gpro.dao.mybatis.facade.FachadaTrack;
import org.jlobato.gpro.dao.mybatis.model.Race;
import org.jlobato.gpro.dao.mybatis.model.Season;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.DtStart;

// TODO: Auto-generated Javadoc
/**
 * The Class GPROCalendarBrowserTest.
 *
 * @author JLOBATO
 */
public class GPROCalendarBrowserTest extends TestCase {
	
	/** The Constant APPLICATION_CONTEXT. */
	//private static final String APPLICATION_CONTEXT = "C:/Desarrollo/gpro-dev/gpro/gprdata/src/main/webapp/WEB-INF/spring-applicationContext.xml";
	private static final String APPLICATION_CONTEXT = "/spring-context/spring-applicationContext.xml";

	/** The Constant RACE_UID_DELIMITER. */
	private static final String RACE_UID_DELIMITER = "R";

	/** The Constant GPROSDS_SDS_PREFIX. */
	private static final String GPROSDS_SDS_PREFIX = "GPROSDS";
	
	/** The Constant GPROSDS_RAC_PREFIX. */
	private static final String GPROSDS_RAC_PREFIX = "GPROS";

	/** The Constant AT. */
	private static final String AT = "at";

	/** The Constant RACE. */
	private static final String RACE = "Race";

	/** The Constant logger. */
	private static final transient Logger logger = LoggerFactory.getLogger(GPROCalendarBrowserTest.class);
	
	/** The contexto. */
	private ApplicationContext contexto = null;
	
	/** The is. */
	private InputStream is = null;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	/**
	 * 
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		//Entorno desarrollo
		System.setProperty("entorno", "L");
		
		String calendarFileName = "GPROCalS85.ics";
		
		//Cargamos el contexto spring (el mismo contexto que la aplicación web)
		//contexto = new FileSystemXmlApplicationContext(APPLICATION_CONTEXT);
		contexto = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
		logger.info("Contexto cargado: " + contexto.getDisplayName());
		
		//Cargamos el calendario
		is = getClass().getResourceAsStream("/" + calendarFileName);
		
		logger.info(calendarFileName + " found");
		
		
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	/**
	 * 
	 */
	public void tearDown() throws Exception {
		if (is != null) {
			is.close();
			logger.info("GPRO Season InputStream closed");
		}
		logger.info("test tearDown");
	}
	
	/**
	 * Test formateo.
	 */
	@Test
	public void testFormateo() {
		String importe = "8476";
		
		double importeDouble = Double.valueOf(importe);
		double importeEuros = importeDouble / 100;
		
		
		double d = 1234567.89;    
		assertEquals(new DecimalFormat("#.##").format(d), "1234567,89");
		assertEquals(new DecimalFormat("0.00").format(d), "1234567,89");
		assertEquals(new DecimalFormat("#.##").format(importeEuros), "84,76");
		
		assertEquals(new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(d), "1234567.89");
		assertEquals(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(d), "1234567.89");
		assertEquals(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(importeEuros), "84.76");
		
		
		String format = "Documento pendiente de pago ({0}) - Error devuelto por el servicio de comprobación de estado del documento (SCH) ''{0}'': {1}-{2}";
		
		String resultado = MessageFormat.format(format, "046", "11", "Grafana");
		logger.info(resultado);
		assertEquals(resultado, "Documento pendiente de pago (046) - Error devuelto por el servicio de comprobación de estado del documento (SCH) '046': 11-Grafana");
	}

	/**
	 * Test import calendar.
	 */
	@Test
	/**
	 * 
	 */
	public void testImportCalendar() {
		
		CalendarBuilder builder = new CalendarBuilder();
		int seasonId = 0;
		
		int racesCount = 0;
		
		//Nueva temporada
		int seasonNumber = -1;
		
		try {
			Calendar calendar = builder.build(is);
			

			
			//Lista de carreras
			ArrayList<Race> races = new ArrayList<Race>();
			
			//Para obtener el id de los circuitos
			FachadaTrack fTrack = contexto.getBean(FachadaTrack.class);
			
			//Fechas de la temporada
			Date sos = null;
			Date eos = null;
			
			
			for (@SuppressWarnings("rawtypes")
			Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
				Component component = (Component) i.next();
				logger.info("Component [" + component.getName() + "]");

				//Si aún no hemos calculado el season number, lo calculamos
				if (seasonNumber == -1) {
					Property uid = component.getProperty(Property.UID);
					String prefixMatched = GPROSDS_SDS_PREFIX;
					int prefixIndex = uid.getValue().indexOf(GPROSDS_SDS_PREFIX);
					
					if (prefixIndex == -1) {
						prefixMatched = GPROSDS_RAC_PREFIX;
						prefixIndex = uid.getValue().indexOf(GPROSDS_RAC_PREFIX);
					}
					
					if (prefixIndex > -1) {
						prefixIndex += prefixMatched.length();
						int raceIndex = uid.getValue().indexOf(RACE_UID_DELIMITER, prefixIndex);
						String seasonNumberStr = uid.getValue().substring(prefixIndex, raceIndex);
						seasonNumber = new Integer(seasonNumberStr);
					}
				}
				
				
				//Sacamos el tipo de evento por la descripcion
				Property property = component.getProperty(Property.DESCRIPTION);
				
				int hyphenIndex = property.getValue().indexOf("-");
				
				if (hyphenIndex > 0) {
					//Habría que crear una carrera
					logger.info("Race event");
					Race race = new Race();
					
					//Obtenemos el id de carrera (1, 2, ... 17)
					int raceIndex = property.getValue().indexOf(RACE) + RACE.length();
					int atIndex = property.getValue().indexOf(AT);
					
					logger.info("raceIndex: " + raceIndex + ", atIndex: " + atIndex);
					
					String idRace = property.getValue().substring(raceIndex, atIndex).trim();
					logger.info("Race number should be: " + idRace);
					
					//Asociados el id de carrera
					race.withIdRace(new Short(idRace));
					
					//Asociamos el id de season
					race.withIdSeason(new Short((short)seasonNumber));
					
					//Asociamos el codigo de carrera
					race.withCodeRace("S" + seasonNumber + RACE_UID_DELIMITER + idRace);
					
					//Asociamos la descripción de carrera
					race.withDescriptionRace("Season " + seasonNumber + " - Race " + idRace);
					
					//Obtenemos la fecha de carrera
					DtStart dtstart = (DtStart)component.getProperty(Property.DTSTART);
					//Asociamos la fecha
					race.withRaceDate(dtstart.getDate());
					//Deducimos las fechas de la temporada
					//Inicio de la temporada
					if (sos == null || sos.after(dtstart.getDate())) {
						sos = dtstart.getDate();
					}
					//Fin de la temporada					
					if (eos == null || eos.before(dtstart.getDate())) {
						eos = dtstart.getDate();
					}
					
					//Obtenemos el circuito
					atIndex += AT.length();
					int bracketIndex = property.getValue().indexOf("(");
					logger.info("atIndex: " + atIndex + ", bracketIndex: " + bracketIndex);
					
					String trackDesc = property.getValue().substring(atIndex, bracketIndex).trim();
					logger.info("trackDesc: " + trackDesc);
					
					
					Short idTrack = fTrack.getTrackIdByName(trackDesc);
					logger.info("idTrack: " + idTrack);
					//Asociamos el circuito
					race.withIdTrack(idTrack);
					
					//Añadimos la carrera a la lista
					races.add(race);
					
				} else {
					logger.info("Not a race event. Skipping");
				}
			}
			
			//Creamos la temporada
			Season currentSeason = new Season();
			
			//Añadimos 6 días a la fecha de fin de temporada
			eos = DateUtils.addDays(eos, 6);
			
			currentSeason.withIdSeason(new Short((short)seasonNumber)).
				withSeasonNumber(seasonNumber).
				withCode("S" + seasonNumber).
				withNameSeason("Season " + seasonNumber).
				withStartDate(sos).
				withEndDate(eos);

			//Obtenemos la fachada para actualizar los datos
			FachadaSeason fSeason = contexto.getBean(FachadaSeason.class);
			
			//Actualizamos la temporada con lo que nos encontramos en el calendario importado
			fSeason.updateSeasonCalendar(currentSeason, races);
			
			racesCount = fSeason.getRaces(currentSeason).size();
			seasonId = fSeason.getSeason(seasonNumber).getIdSeason();
			
		} catch (Exception e) {
			logger.error(e.getClass().getName(), e);
		}
		
		assertEquals(seasonId, seasonNumber);
		assertEquals(17, racesCount);
	}
}
