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
 * 
 * @author JLOBATO
 *
 */
@Component
public class ManagerServices {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ManagerServices.class);
	
	@Autowired
	FachadaManager managerDAO;
	
	/**
	 * 
	 * @param args Manager code and local path of new avatar
	 */
	public static void main(String args[]) {
		ApplicationContext contexto = null;
		try {
			//Entorno desarrollo
			System.setProperty("entorno", "I");
			
			//Cargamos el contexto spring (el mismo contexto que la aplicación web)
			contexto = new FileSystemXmlApplicationContext("C:/Desarrollo/oxygenws/gprdata/gprdata/src/main/webapp/WEB-INF/spring-applicationContext.xml");
			
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
