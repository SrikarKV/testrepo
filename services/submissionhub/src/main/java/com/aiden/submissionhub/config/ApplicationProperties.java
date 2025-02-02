package com.aiden.submissionhub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Submissionhub.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Liquibase liquibase = new Liquibase();

    // jhipster-needle-application-properties-property

    private final Config config = new Config();


    public Liquibase getLiquibase() {
        return liquibase;
    }

    public Config getConfig() { return config; }


    // jhipster-needle-application-properties-property-getter

    public static class Liquibase {

        private Boolean asyncStart;

        public Boolean getAsyncStart() {
            return asyncStart;
        }

        public void setAsyncStart(Boolean asyncStart) {
            this.asyncStart = asyncStart;
        }
    }

    public static class Config {
        private String processorUrl;

        public String getProcessorUrl() {
            return processorUrl;
        }

        public void setProcessorUrl(String processorUrl) {
            this.processorUrl = processorUrl;
        }
    }
    // jhipster-needle-application-properties-property-class
}
