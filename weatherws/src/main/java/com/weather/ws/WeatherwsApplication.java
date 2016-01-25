package com.weather.ws;

import java.util.Arrays;

import javax.faces.webapp.FacesServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
public class WeatherwsApplication extends SpringBootServletInitializer {
	
    public static void main(String[] args) {
    	SpringApplication.run(WeatherwsApplication.class, args);
      }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WeatherwsApplication.class);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.jsf");
        servletRegistrationBean.setName("Faces Servlet");
		return servletRegistrationBean;
    }
    
    @Bean
    public PropertyLoader propFileBean(){
    	return new PropertyLoader();
    }

}
