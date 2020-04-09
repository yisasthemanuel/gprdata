package org.jlobato.gpro.services.webupdates;

import java.util.List;
import java.util.Optional;

import org.jlobato.gpro.dao.mybatis.facade.FachadaCategory;
import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.facade.FachadaTyres;
import org.jlobato.gpro.dao.mybatis.model.Category;
import org.jlobato.gpro.dao.mybatis.model.Manager;
import org.jlobato.gpro.utils.GPROUtils;
import org.jlobato.gpro.web.session.GPROWebSession;
import org.jlobato.gpro.web.session.GPROWebSessionFactory;
import org.jlobato.gpro.web.xbean.ManagerHistoryXBean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import net.sf.ehcache.CacheManager;

public class ManagerHistoryUpdater {
	
	//TODO Esto debería ir en la capa de negocio
	public static void updateManagerHistory(Manager manager, AbstractApplicationContext contexto, GPROWebSession session) {
		
		FachadaCategory categoryService = contexto.getBean(FachadaCategory.class);
		FachadaTyres tyresService = contexto.getBean(FachadaTyres.class);
		FachadaManager managerService = contexto.getBean(FachadaManager.class);
		
		List<ManagerHistoryXBean> history = session.getManagerHistory(manager.getIdGpro().toString());
		
		for (ManagerHistoryXBean season : history) {
			Optional<String> optBrandCode = Optional.ofNullable(GPROUtils.getTyreBrandCode(season.getTyres()));
			Short tyreBrand = optBrandCode.map((code) -> tyresService.getTyreBrand(code).getIdTyreBrand()).orElse(null);
			
			Short idSeason = GPROUtils.castIfNotNull(season.getSeason(), Short.class);
			Optional<Category> optCategory = Optional.ofNullable(categoryService.getCategoryByCode(GPROUtils.getCategoryCode(season.getGroup())));
			Short idCategory = optCategory.map((category) -> category.getIdCategory()).orElse(null); 
			Short idGroup = GPROUtils.castIfNotNull(GPROUtils.getGroupId(season.getGroup()), Short.class);
			Short position = GPROUtils.castIfNotNull(season.getPosition(), Short.class);
			Short wins = GPROUtils.castIfNotNull(season.getWins(), Short.class);
			String cup = season.getCup();
			Short fastestLaps = GPROUtils.castIfNotNull(season.getFastestLaps(), Short.class);
			Integer result = GPROUtils.getMoneyAsInt(season.getMoney());
			Long moneyBalance = result == null ? null : new Long(result);
			Short obr = GPROUtils.castIfNotNull(season.getObr(), Short.class);
			Short podiums = GPROUtils.castIfNotNull(season.getPodiums(), Short.class);
			Short points = GPROUtils.castIfNotNull(season.getPoints(), Short.class);
			Short poles = GPROUtils.castIfNotNull(season.getPoles(), Short.class);
			Short races = GPROUtils.castIfNotNull(season.getRaces(), Short.class);
			
			if (idSeason != null && idCategory != null) {
				//managerService.addManagerHistory(manager.getCodeManager(),
				managerService.updateManagerHistory(manager.getCodeManager(),
						idSeason,
						idCategory,
						idGroup,
						tyreBrand,
						position,
						wins,
						cup,
						fastestLaps,
						moneyBalance,
						obr,
						podiums,
						points,
						poles,
						races);
			}
		}
	}
	
	public static void main(String args[]) {
		System.setProperty("entorno", "I");
		//AbstractApplicationContext contexto = new FileSystemXmlApplicationContext("C:/Desarrollo/eclipse/ws/gpro/gprdata/src/main/webapp/WEB-INF/spring-applicationContext.xml");
		//AbstractApplicationContext contexto = new FileSystemXmlApplicationContext("C:/Desarrollo/gpro/gprdata/src/main/webapp/WEB-INF/spring-applicationContext.xml");
		AbstractApplicationContext contexto = new FileSystemXmlApplicationContext(args[0]);
		
		GPROWebSession session = GPROWebSessionFactory.getGPROWebSession();
		
		FachadaManager managerService = contexto.getBean(FachadaManager.class);
		
		List<Manager> managers = managerService.getManagersList();
		
		for (Manager manager : managers) {
			if (manager.getIdGpro() != null) {
				updateManagerHistory(
						manager,
						contexto,
						session
					);
			}
		}
		
		session.logout();
		
		CacheManager.getInstance().shutdown();
	}

}
