package com.persistent.deskManagement.configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:swaggerInfo.properties")
public class AppConfig {
	
	@Value("${swagger.contact.name}")
	private String swaggerUsername;

	@Value("${swagger.contact.email}")
	private String swaggerEmail;
	
	@Value("${swagger.contact.url}")
	private String swaggerUrl;
	
	@Value("${swagger.apilicense}")
	private String apiLicense;
	
	@Value("${swagger.licenseurl}")
	private String licenseUrl;
	
	@Value("${swagger.version}")
	private String swaggerVersion;
	
	@Value("${swagger.title}")
	private String swaggerTitle;
	
	@Value("${swagger.description}")
	private String swaggerDescription;
	
	@Value("${swagger.termsOfService}")
	private String swaggerTermsOfService;
	
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage( "com.persistent.deskManagement.controller" ))
				.build()
				.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo(swaggerTitle,
				swaggerDescription,
				swaggerVersion,
				swaggerTermsOfService,
				new Contact(swaggerUsername, swaggerUrl, swaggerEmail),
				apiLicense,
				licenseUrl,
				Collections.emptyList());
	}	
}
