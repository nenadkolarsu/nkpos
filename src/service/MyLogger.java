/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import java.io.File;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Pionir SU
 */
public class MyLogger {
    
        public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MyLogger.class);
    
        public  MyLogger() {
            String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
//        logger.debug("this is a debug log message");
//        logger.info("this is a information log message");
//        logger.warn("this is a warning log message");
        }
}
