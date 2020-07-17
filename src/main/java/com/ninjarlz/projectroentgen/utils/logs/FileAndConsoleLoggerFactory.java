package com.ninjarlz.projectroentgen.utils.logs;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class used to prepare logger which sends the output to both console and file.
 */
public class FileAndConsoleLoggerFactory {

    /**
     * Contains information whether logger is already configured.
     */
    private static boolean loggerConfigured = false;

    /**
     * Gets the distance from the other Cartesian point.
     *
     * @param name the name of class where the instance of logger is used
     * @return the configured logger which sends the output to both console and file.
     */
    public static Logger getConfiguredLogger(String name) {
        if (!loggerConfigured) {
            InputStream stream = FileAndConsoleLoggerFactory.class.
                    getResourceAsStream("/logging.properties");
            try {
                LogManager.getLogManager().readConfiguration(stream);
                loggerConfigured = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Logger.getLogger(name);
    }
}
