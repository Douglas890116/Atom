package com.maven.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

public class PropertiesUtils {
  private static Logger logger= LogManager.getLogger(PropertiesUtils.class.getName());
  private static final String DEFAULT_ENCODING = "UTF-8";

  private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
  private static ResourceLoader resourceLoader = new DefaultResourceLoader();

  public static Properties loadProperties(String... resourcesPaths) throws IOException {
    Properties props = new Properties();

    for (String location : resourcesPaths) {
      logger.debug("Loading properties file from:" + location);
      InputStream is = null;
      try {
        Resource resource = resourceLoader.getResource(location);
        is = resource.getInputStream();
        propertiesPersister.load(props, new InputStreamReader(is, DEFAULT_ENCODING));
      } catch (IOException ex) {
        logger.error("Could not load properties from classpath:" + location + ": " + ex.getMessage());
      } finally {
        if (is != null) {
          is.close();
        }
      }
    }
    return props;
  }
  
  /**
   * 深层加载
   * @param filepath
   * @throws IOException
   */
  public static Properties deepLoad(String filepath) throws IOException {
	/*  Enumeration<URL> sps = Thread.currentThread().getContextClassLoader().getResources(".");
	  while(sps.hasMoreElements()) {
		  System.out.println(sps.nextElement().getPath());
	  }*/
      Enumeration<URL> ps = Thread.currentThread().getContextClassLoader().getResources(filepath);
      Properties p = new Properties();
      while(ps.hasMoreElements()) {
          InputStream in = null;
          try {
        	  URL url  = ps.nextElement();
        	  //System.out.println(url);
              in = url.openStream();
              p.load(in);
          } finally {
        	  if(in != null) {
                  try {
                	  in.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
      }
      return p;
  }
  
}
