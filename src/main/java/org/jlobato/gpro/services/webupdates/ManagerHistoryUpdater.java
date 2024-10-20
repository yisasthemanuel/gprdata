package org.jlobato.gpro.services.webupdates;

import java.util.List;
import java.util.Optional;

import org.jlobato.gpro.dao.mybatis.facade.FachadaCategory;
import org.jlobato.gpro.dao.mybatis.facade.FachadaManager;
import org.jlobato.gpro.dao.mybatis.facade.FachadaTyres;
import org.jlobato.gpro.dao.mybatis.model.Category;
import org.jlobato.gpro.dao.mybatis.model.Manager;
import org.jlobato.gpro.dao.mybatis.model.TyreBrand;
import org.jlobato.gpro.services.manager.ManagerServices;
import org.jlobato.gpro.services.tyres.TyreSuppliers;
import org.jlobato.gpro.utils.GPROUtils;
import org.jlobato.gpro.web.session.GPROWebSession;
import org.jlobato.gpro.web.session.GPROWebSessionFactory;
import org.jlobato.gpro.web.xbean.ManagerHistoryXBean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import net.sf.ehcache.CacheManager;

/**
 * The Class ManagerHistoryUpdater.
 */
public class ManagerHistoryUpdater {
	
	
	/**
	 * Instantiates a new manager history updater.
	 */
	private ManagerHistoryUpdater() {
		super();
	}

	/**
	 * Sets the manager history.
	 *
	 * @param contexto the contexto
	 * @param codManager the cod manager
	 * @param idSeason the id season
	 * @param categoryCode the category code
	 * @param group the group
	 * @param position the position
	 * @param tyre the tyre
	 */
	public static void setManagerHistory(AbstractApplicationContext contexto, String codManager, int idSeason, String categoryCode, Integer group, int position, String tyre) {
		FachadaCategory categoryService = contexto.getBean(FachadaCategory.class);
		FachadaTyres tyresService = contexto.getBean(FachadaTyres.class);
		FachadaManager managerService = contexto.getBean(FachadaManager.class);
		
		Manager manager = managerService.getManagerByCode(codManager);
		Category category = categoryService.getCategoryByAltCode(categoryCode);
		TyreBrand tyreBrand = tyresService.getTyreBrand(tyre);
		
		managerService.updateManagerHistory(
				manager.getCodeManager(),
				(short)idSeason,
				Short.valueOf(category.getIdCategory().toString()),
				group != null ? group.shortValue() : null,
				tyreBrand != null ? tyreBrand.getIdTyreBrand() : null,
				(short)position,
				null, //wins
				null, //cup
				null, //fastestLaps
				null, //moneyBalance
				null, //obr
				null, //podiums
				null, //points
				null, //poles
				null //races
			);
	}
	
	/**
	 * Update manager history.
	 *
	 * @param manager the manager
	 * @param contexto the contexto
	 * @param session the session
	 * @param fromSeasonId the from season id
	 */
	//TODO Esto debería ir en la capa de negocio
	public static void updateManagerHistory(Manager manager, AbstractApplicationContext contexto, GPROWebSession session, Short fromSeasonId) {
		
		FachadaCategory categoryService = contexto.getBean(FachadaCategory.class);
		FachadaTyres tyresService = contexto.getBean(FachadaTyres.class);
		FachadaManager managerService = contexto.getBean(FachadaManager.class);
		
		List<ManagerHistoryXBean> history = session.getManagerHistory(manager.getIdGpro().toString());
		
		for (ManagerHistoryXBean season : history) {
			Short idSeason = GPROUtils.castIfNotNull(season.getSeason(), Short.class);
			// Sí y sólo sí la temporada es la misma o posterior a la pasada como parámetro
			if (idSeason != null && (idSeason.shortValue() >= fromSeasonId.shortValue())) {
				Optional<String> optBrandCode = Optional.ofNullable(GPROUtils.getTyreBrandCode(season.getTyres()));
				Short tyreBrand = optBrandCode.map(code -> tyresService.getTyreBrand(code).getIdTyreBrand()).orElse(null);
				
				//TODO Cambiar a version 1.1.4 de mybatis
				Optional<Category> optCategory = Optional.ofNullable(categoryService.getCategoryByAltCode(GPROUtils.getCategoryCode(season.getGroup())));
				Integer idCategory = optCategory.map(category -> category.getIdCategory()).orElse(null); 
				Short idGroup = GPROUtils.castIfNotNull(GPROUtils.getGroupId(season.getGroup()), Short.class);
				Short position = GPROUtils.castIfNotNull(season.getPosition(), Short.class);
				Short wins = GPROUtils.castIfNotNull(season.getWins(), Short.class);
				String cup = season.getCup();
				Short fastestLaps = GPROUtils.castIfNotNull(season.getFastestLaps(), Short.class);
				Integer result = GPROUtils.getMoneyAsInt(season.getMoney());
				Long moneyBalance = result == null ? null : Long.valueOf(result);
				Short obr = GPROUtils.castIfNotNull(season.getObr(), Short.class);
				Short podiums = GPROUtils.castIfNotNull(season.getPodiums(), Short.class);
				Short points = GPROUtils.castIfNotNull(season.getPoints(), Short.class);
				Short poles = GPROUtils.castIfNotNull(season.getPoles(), Short.class);
				Short races = GPROUtils.castIfNotNull(season.getRaces(), Short.class);
				
				if (idCategory != null) { // El idSeason ya ha sido comprobado antes
					managerService.updateManagerHistory(manager.getCodeManager(),
							idSeason,
							Short.valueOf(idCategory.toString()),
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
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.setProperty("entorno", "L");
		AbstractApplicationContext contexto = new FileSystemXmlApplicationContext(args[0]);
		
		Short idSeason = Short.valueOf(args[1]); 
		
		GPROWebSession session = GPROWebSessionFactory.getGPROWebSession();
		
		FachadaManager managerService = contexto.getBean(FachadaManager.class);
		
		List<Manager> managers = managerService.getManagersList();
		
		for (Manager manager : managers) {
			if (manager.getIdGpro() != null) {
				updateManagerHistory(
						manager,
						contexto,
						session,
						idSeason
					);
			}
		}
		
		int nextSeason = idSeason.shortValue() + 1;
		
		//manager, season, categoría, grupo, posición, neumáticos
		//Elite
		String currentCategory = "E";
		setManagerHistory(contexto, ManagerServices.DANIEL_MANAGER_CODE, nextSeason, currentCategory, null, 7, TyreSuppliers.MICHIS.getName());
		
		//Master
		currentCategory = "M";
		setManagerHistory(contexto, ManagerServices.MAZ_MANAGER_CODE, nextSeason, currentCategory, 1, 6, TyreSuppliers.BADYEARS.getName());
		setManagerHistory(contexto, ManagerServices.ANIA_MANAGER_CODE, nextSeason, currentCategory, 2, 14, TyreSuppliers.DUNNOS.getName());
		setManagerHistory(contexto, ManagerServices.MARK_MANAGER_CODE, nextSeason, currentCategory, 2, 30, TyreSuppliers.CONTIS.getName());
		setManagerHistory(contexto, ManagerServices.JENNE_MANAGER_CODE, nextSeason, currentCategory, 2, 33, TyreSuppliers.DUNNOS.getName());
		setManagerHistory(contexto, ManagerServices.STEVEN_MANAGER_CODE, nextSeason, currentCategory, 4, 1, TyreSuppliers.BADYEARS.getName());
		
		//Pro
		currentCategory = "P";
		setManagerHistory(contexto, ManagerServices.JESUS_MANAGER_CODE, nextSeason, currentCategory, 7, 6, TyreSuppliers.DUNNOS.getName());
		setManagerHistory(contexto, ManagerServices.GEOFF_MANAGER_CODE, nextSeason, currentCategory, 14, 1, TyreSuppliers.BADYEARS.getName());
		
		//Amateur
		currentCategory = "A";
		setManagerHistory(contexto, ManagerServices.NEVZA_MANAGER_CODE, nextSeason, currentCategory, 14, 7, TyreSuppliers.PIPIS.getName());
		setManagerHistory(contexto, ManagerServices.MIKKO_MANAGER_CODE, nextSeason, currentCategory, 30, 9, TyreSuppliers.PIPIS.getName());
		
		//Rookie
		currentCategory = "R";
		
		session.logout();
		
		CacheManager.getInstance().shutdown();
	}

}
