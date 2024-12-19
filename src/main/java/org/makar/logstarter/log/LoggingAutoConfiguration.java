package org.makar.logstarter.log;

import org.makar.logstarter.aspect.LogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingAutoConfiguration {
    private final LoggingProperties loggingProperties;

    public LoggingAutoConfiguration(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "log.starter.enabled", havingValue = "true", matchIfMissing = true)
    public LogAspect loggingAspect() {
        if (loggingProperties.getLevel() == null || loggingProperties.getLevel().isEmpty()) {
            loggingProperties.setLevel("INFO");
        }
        return new LogAspect(loggingProperties);
    }

}
