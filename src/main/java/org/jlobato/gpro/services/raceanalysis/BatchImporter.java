package org.jlobato.gpro.services.raceanalysis;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

//TODO Mejorar la gestión de las excepciones.
//1. Ver qué pasa cuando el parser HTML se encuentra con una hoja excel y gestionar bien la excepcion
//2. Contemplar al menos dos tipos de excepcion: Una para leer lo que exporta la aplicación para cuando el error se produce al meter los datos en la base de datos
//3. Cambiar los clientes (importers) para contemplar estas excepciones y diferenciar el tratamiento de los casos
//4. Lanzar una excepción para el caso en que la carrera exista ya

//TODO Incluir el patrón observer para que los importers puedan hacer un resumen de la ejecución de la importación
/**
 * args[0] -> Directorio donde está el xml de configuración de spring
 * args[1] -> Directorio base donde están las sheets de las carreras.
 * 				El directorio base tiene la siguiente estructura
 * 					CARLO
 * 					|_Ahvenisto_S72.xls
 * 					|_Yeongam_S72.xls
 * 					MIKKO
 * 					|_S70 - R1 - Baku City.xls
 * 					|_S70 - R3 - Suzuka.xls
 * 					 
 * Ejemplo: BatchImporter C:/Desarrollo/gpro/gprdata/src/main/webapp/WEB-INF/spring-applicationContext.xml C:/Desarrollo/gpro/racedata
 * 
 * @author Jesús Manuel Pérez
 *
 */
public class BatchImporter {

	public static void main(String[] args) {
		System.out.println("INICIO IMPORTACIÓN");
		ApplicationContext contexto = null;
		try {
			//Entorno desarrollo
			System.setProperty("entorno", "I");
			
			//Cargamos el contexto spring (el mismo contexto que la aplicación web)
			contexto = new FileSystemXmlApplicationContext(args[0]);
			System.out.println("Contexto cargado: " + contexto.getDisplayName());
			
			//Importador de carreras
			SingleRaceImporter importer = contexto.getBean(SingleRaceImporter.class);
			
			//Los directorios de primer nivel son los managers (códigos)
			File baseDir = new File(args[1]);
			File[] managerDirs = baseDir.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return (pathname.isDirectory() && !pathname.getName().equals("ignore"));
				}
			});
			
			//Por cada manager
			for (int i = 0; i < managerDirs.length;i++) {
				File managerDir = managerDirs[i];
				//Pillamos todas las hojas excel que haya (extensión xlsx o xls)
				List<File> raceSheets = getAllFiles(managerDir);
				
				//Importamos cada una de las hojas
				Iterator<File> raceSheetsIterator = raceSheets.iterator();
				while (raceSheetsIterator.hasNext()) {
					File raceSheet = raceSheetsIterator.next();
					FileInputStream is = null;
					String raceDesc = "Carrera correspondiente al archivo '" + raceSheet.getName() + "'";
					try {
						is = new FileInputStream(raceSheet);
						importer.importSingleRace(raceDesc,  managerDir.getName(), is);
					} catch(Exception e) {
						System.err.println("Error parseando '" + raceDesc + "'");
						e.printStackTrace();
					} finally {
						try {is.close();} catch(Exception c) {}
					}
				}
				
			}
			
			
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
			System.out.println("FIN IMPORTACIÓN");
			System.exit(0);
		}		

	}
	
	/**
	 * 
	 * @param dir
	 * @return
	 */
	private static List<File> getAllFiles(File dir) {
		List<File> result = new ArrayList<>();
		
		File fileList[] = dir.listFiles();
		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					result.addAll(getAllFiles(fileList[i]));
				}
				else if (fileList[i].getName().endsWith(".xlsx") || fileList[i].getName().endsWith(".xls")) {
					result.add(fileList[i]);
				}
			}
		}
		
		return result;
		
	}

}
