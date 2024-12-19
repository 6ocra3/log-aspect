package org.makar.logstarter.log;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

@Getter
public class LoggingAspectStarter {
    private final Logger logger;
    private final Level logLevel;

    public LoggingAspectStarter(Class<?> clazz, String level) {
        logger = LoggerFactory.getLogger(clazz);
        logLevel = createLogginLevel(level);
    }

    public void log(String message) {
        logger.atLevel(logLevel).log(message);
    }

    private Level createLogginLevel(String level) {
        return switch (level.toUpperCase()) {
            case "DEBUG" -> Level.DEBUG;
            case "INFO" -> Level.INFO;
            case "WARN" -> Level.WARN;
            case "ERROR" -> Level.ERROR;
            case "TRACE" -> Level.TRACE;
            default -> throw new RuntimeException("Unknown log level: " + level);
        };
    }

}
