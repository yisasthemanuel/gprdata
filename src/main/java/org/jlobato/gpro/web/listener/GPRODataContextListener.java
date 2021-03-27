package org.jlobato.gpro.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author JLOBATO
 *
 */
@SuppressWarnings("deprecation")
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

//		DiscoveryManager.getInstance().initComponent(new MyDataCenterInstanceConfig(), new DefaultEurekaClientConfig());
//
//		String vipAddress = "YISAS-GATEWAY";
//
//		InstanceInfo nextServerInfo = null;
//		try {
//			nextServerInfo = DiscoveryManager.getInstance().getEurekaClient().getNextServerFromEureka(vipAddress, false);
//
//			logger.info("Found an instance of example service to talk to from eureka: {}:{}", nextServerInfo.getVIPAddress(), nextServerInfo.getPort());
//	
//			logger.info("healthCheckUrl: {}", nextServerInfo.getHealthCheckUrl());
//			logger.info("override: {}", nextServerInfo.getOverriddenStatus());
//	
//			logger.info("Server Host Name {} at port {}", nextServerInfo.getHostName(), nextServerInfo.getPort());
//		} catch (Exception e) {
//			logger.error("Cannot get an instance of example service to talk to from eureka", e);
//		}
	}

}
