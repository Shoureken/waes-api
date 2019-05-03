package com.dematte.waestest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket api() {
		return new Docket(SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.dematte.waestest.controller"))
				.build()
				.apiInfo(metaData())
				.useDefaultResponseMessages(false);

	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Assignment Scalable Web")
				.description("REST API for diff JSON Base64 encoded data")
				.contact(new Contact("Rodolfo Chiuff Dematte", "https://www.linkedin.com/in/rodolfodematte/",
				                     "rodolfodematte@gmail.com"))
				.build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		        .addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
		        .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}