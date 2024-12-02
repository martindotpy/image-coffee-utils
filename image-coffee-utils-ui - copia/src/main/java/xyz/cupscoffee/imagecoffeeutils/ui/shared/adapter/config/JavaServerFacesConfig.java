package xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.faces.webapp.FacesServlet;

/**
 * Java Server Faces configuration.
 */
@Configuration
public class JavaServerFacesConfig {
    /**
     * Register the FacesServlet.
     *
     * @return the ServletRegistrationBean
     */
    @Bean
    ServletRegistrationBean<FacesServlet> servletRegistrationBean() {
        return new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
    }

    /**
     * Set the init parameter to force the configuration to load.
     *
     * @return the ServletContextInitializer
     */
    @Bean
    ServletContextInitializer initializer() {
        return servletContext -> {
            servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        };
    }
}
