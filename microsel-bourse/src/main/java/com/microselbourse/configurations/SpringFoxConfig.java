package com.microselbourse.configurations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {               
	
	
	@Bean
    public Docket api() throws FileNotFoundException, IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return new Docket(DocumentationType.SWAGGER_2)
        		//.groupName("SEL")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
                
               
    }
	
	 private ApiInfo getApiInfo() {

         Contact contact = new Contact("Jean-Louis PIERA", "https://github.com/JeanLouisPIERA/SEL", "jeanlouispiera@yahoo.fr");

         return new ApiInfoBuilder()

                 .title("ADHERENTS DE LA BOURSE D'ECHANGES DU SEL")

                 .description("Microservice de gestion des utilisateurs de la bourse d'échanges d'un Système d'Echanges Local associatif de type SEL")

                 .version("0.0.1")

                 .license("Apache 2.0")

                 .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")

                 .contact(contact)

                 .build();

	 }
   
}