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
 * 
 * @author jmplobato
 *
 */
@Service
public class FreemarkerTemplateService implements TemplateService {
	
	private static final transient Logger logger = LoggerFactory.getLogger(FreemarkerTemplateService.class);
	
    private Configuration cfg;
    private URL urlPlantillas;
    private File dirBasePlantilla;

	@Override
	public String processTemplate(String idPlantilla, Map<String, Object> model) throws TemplateException {
        CharArrayWriter output = new CharArrayWriter();
        processTemplate(idPlantilla, model, output);
        return output.toString();
	}

	@Override
	public void processTemplate(String idPlantilla, Map<String, Object> model, Writer out) throws TemplateException {
        try {
            Template tpl = this.getTemplate(idPlantilla);
            model.put("ruta", dirBasePlantilla.toURI());
            tpl.process(model, out);
        } catch (Exception e) {
            logger.error("Error generando plantilla con id: '" + idPlantilla + "'", e);
            throw new TemplateException(e.getMessage(),e);
        }
	}
	
	/**
	 * 
	 * @param nameTemplate
	 * @return
	 * @throws IOException
	 */
    public Template getTemplate(String nameTemplate) throws IOException {
        return cfg.getTemplate(nameTemplate);
    }

    /**
     * 
     * @return
     */
	public URL getUrlPlantillas() {
		return urlPlantillas;
	}

	/**
	 * 
	 * @param urlPlantillas
	 */
	public void setUrlPlantillas(URL urlPlantillas) {
		this.urlPlantillas = urlPlantillas;
	}

	/**
	 * 
	 * @return
	 */
	public File getDirBasePlantilla() {
		return dirBasePlantilla;
	}

	/**
	 * 
	 * @param dirBasePlantilla
	 */
	public void setDirBasePlantilla(File dirBasePlantilla) {
		this.dirBasePlantilla = dirBasePlantilla;
	}

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
