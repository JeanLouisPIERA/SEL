package com.microselbourse.configurations;

/**
 * Classe pour l'importation des paramètres externalisés
 */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("customized-configs")
@RefreshScope
public class ApplicationPropertiesConfiguration {

}
