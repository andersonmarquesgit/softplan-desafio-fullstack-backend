package com.softplan.restapi.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket detalheApi() {

		Docket docket = new Docket(DocumentationType.SWAGGER_2);

		docket.select().apis(RequestHandlerSelectors.basePackage("com.softplan.restapi")).paths(PathSelectors.any())
				.build().apiInfo(this.informacoesApi().build());

		return docket;
	}

	private ApiInfoBuilder informacoesApi() {

		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

		apiInfoBuilder.title("Softplan Rest Api");
		apiInfoBuilder.description("Api para realização do desafio da Softplan.");
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.termsOfServiceUrl("Termo de uso: Deve ser usada para avaliação do desafio.");
		apiInfoBuilder.license("Licença - Open Source");
		apiInfoBuilder.licenseUrl("https://goo.gl/Qm3SM9");
		apiInfoBuilder.contact(this.contact());

		return apiInfoBuilder;

	}

	private Contact contact() {
		return new Contact("Anderson Marques", 
				"https://goo.gl/Qm3SM9", 
				"andersonmarques.ci@gmail.com");
	}
}
