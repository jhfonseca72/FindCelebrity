package com.globant.test.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class AppProperties {
    private String approach;

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public String getApproach() {
        return approach;
    }
}
