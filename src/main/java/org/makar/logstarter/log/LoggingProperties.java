package org.makar.logstarter.log;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "log.starter")
public class LoggingProperties {
    private Boolean enabled;
    private String level;

}