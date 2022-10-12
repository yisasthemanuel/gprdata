package org.jlobato.gpro.services.manager;

import java.io.File;
import java.nio.file.Files;

import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.model.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The Class ManagerServices.
 *
 * @author JLOBATO
 */
@Component
public class ManagerServices {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ManagerServices.class);
	
	/** The manager DAO. */
	@Autowired
	FachadaManager managerDAO;

	/** The Constant MARK_MANAGER_CODE. */
	public static final String MARK_MANAGER_CODE = "MARK";
	
	/** The Constant CARLO_MANAGER_CODE. */
	public static final String CARLO_MANAGER_CODE = "CARLO";
	
	/** The Constant GEOFF_MANAGER_CODE. */
	public static final String GEOFF_MANAGER_CODE = "GEOFF";
	
	/** The Constant CHRIS_MANAGER_CODE. */
	public static final String CHRIS_MANAGER_CODE = "CHRIS";
	
	/** The Constant EDWIN_MANAGER_CODE. */
	public static final String EDWIN_MANAGER_CODE = "EDWIN";
	
	/** The Constant NEVZA_MANAGER_CODE. */
	public static final String NEVZA_MANAGER_CODE = "NEVZA";
	
	/** The Constant DIEGO_MANAGER_CODE. */
	public static final String DIEGO_MANAGER_CODE = "DIEGO";
	
	/** The Constant JESUS_MANAGER_CODE. */
	public static final String JESUS_MANAGER_CODE = "JESUS";
	
	/** The Constant ANIA_MANAGER_CODE. */
	public static final String ANIA_MANAGER_CODE = "ANIA";
	
	/** The Constant MIKKO_MANAGER_CODE. */
	public static final String MIKKO_MANAGER_CODE = "MIKKO";
	
	/** The Constant ERWIN_MANAGER_CODE. */
	public static final String ERWIN_MANAGER_CODE = "ERWIN";

	/** The Constant JENNE_MANAGER_CODE. */
	public static final String JENNE_MANAGER_CODE = "JENNE";
	
	/** The Constant DANIEL_MANAGER_CODE. */
	public static final String DANIEL_MANAGER_CODE = "DANIEL";
	
	/** The Constant STEVEN_MANAGER_CODE. */
	public static final String STEVEN_MANAGER_CODE = "STEVEN";
		
	/**
	 * The main method.
	 *
	 * @param args Manager code and local path of new avatar
	 */
	public static void main(String args[]) {
		ApplicationContext contexto = null;
		try {
			//Entorno desarrollo
			System.setProperty("entorno", "L");
			
			//Cargamos el contexto spring (el mismo contexto que la aplicación web)
			contexto = new FileSystemXmlApplicationContext("C:/Desarrollo/git/gprdata/src/test/resources/spring-context/spring-applicationContext.xml");
			
			//Creamos nuestro objeto 
			ManagerServices managerServices = contexto.getBean(ManagerServices.class);
			
			byte[] array = Files.readAllBytes(new File(args[1]).toPath());
			
			Manager manager = managerServices.managerDAO.setManagerAvatar(args[0], array);
			
			logger.info("Manager " + manager.getCodeManager() + " - " + manager.getFirstName() + " " + manager.getLastName() + " avatar updated!");
		} catch(Throwable t) {
			logger.error("Error while updating manager's avatar", t);
		} finally {
			System.exit(0);
		}
		
	}

}
