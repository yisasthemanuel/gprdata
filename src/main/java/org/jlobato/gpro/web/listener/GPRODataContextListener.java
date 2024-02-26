package org.jlobato.gpro.web.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author JLOBATO
 *
 */
public class GPRODataContextListener implements ServletContextListener {
	
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(GPRODataContextListener.class);

	/**
	 * 
	 */
	public void contextDestroyed(ServletContextEvent evt) {
		logger.info("GPRO Data contextDestroyed");
	}

	/**
	 * 
	 */
	public void contextInitialized(ServletContextEvent evt) {
		logger.info("GPRO Data contextInitialized");
		logger.info("GPRO deploy dir: {}", evt.getServletContext().getRealPath("/"));
	}

}
