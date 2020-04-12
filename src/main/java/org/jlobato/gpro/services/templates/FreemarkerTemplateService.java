package org.jlobato.gpro.services.templates;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * The Class FreemarkerTemplateService.
 */
@Service("freemarkerTemplateService")
public class FreemarkerTemplateService implements TemplateService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateService.class);
	
    /** The cfg. */
    private Configuration cfg;
    
    /** The url plantillas. */
    private URL urlPlantillas;
    
    /** The dir base plantilla. */
    private File dirBasePlantilla;

	/**
	 * Process template.
	 *
	 * @param idPlantilla the id plantilla
	 * @param model the model
	 * @return the string
	 * @throws TemplateException the template exception
	 */
	@Override
	public String processTemplate(String idPlantilla, Map<String, Object> model) throws TemplateException {
        CharArrayWriter output = new CharArrayWriter();
        processTemplate(idPlantilla, model, output);
        return output.toString();
	}

	/**
	 * Process template.
	 *
	 * @param idPlantilla the id plantilla
	 * @param model the model
	 * @param out the out
	 * @throws TemplateException the template exception
	 */
	@Override
	public void processTemplate(String idPlantilla, Map<String, Object> model, Writer out) throws TemplateException {
        try {
            Template tpl = this.getTemplate(idPlantilla);
            model.put("ruta", dirBasePlantilla.toURI());
            tpl.process(model, out);
        } catch (Exception e) {
            throw new TemplateException(e.getMessage(), e);
        }
	}
	
	/**
	 * Gets the template.
	 *
	 * @param nameTemplate the name template
	 * @return the template
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public Template getTemplate(String nameTemplate) throws IOException {
        return cfg.getTemplate(nameTemplate);
    }

    /**
     * Gets the url plantillas.
     *
     * @return the url plantillas
     */
	public URL getUrlPlantillas() {
		return urlPlantillas;
	}

	/**
	 * Sets the url plantillas.
	 *
	 * @param urlPlantillas the new url plantillas
	 */
	public void setUrlPlantillas(URL urlPlantillas) {
		this.urlPlantillas = urlPlantillas;
	}

	/**
	 * Gets the dir base plantilla.
	 *
	 * @return the dir base plantilla
	 */
	public File getDirBasePlantilla() {
		return dirBasePlantilla;
	}

	/**
	 * Sets the dir base plantilla.
	 *
	 * @param dirBasePlantilla the new dir base plantilla
	 */
	public void setDirBasePlantilla(File dirBasePlantilla) {
		this.dirBasePlantilla = dirBasePlantilla;
	}

	/**
	 * Instantiates a new freemarker template service.
	 *
	 * @throws URISyntaxException the URI syntax exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public FreemarkerTemplateService() throws URISyntaxException, IOException {
        // Se determina donde estan las plantillas en el disco.
        urlPlantillas = Thread.currentThread().getContextClassLoader().getResource("templates");
        dirBasePlantilla = new File(urlPlantillas.toURI());

        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(dirBasePlantilla);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setOutputEncoding("UTF-8");
        cfg.setLocale(new Locale("es", "ES"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        
        logger.info("Sistema de plantillas inicializado");
	}
    
    


}
