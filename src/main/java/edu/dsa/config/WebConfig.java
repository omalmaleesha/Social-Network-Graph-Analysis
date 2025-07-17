package edu.dsa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
    When someone visits any path (/**) that doesn’t match a controller,
    look for a static file in the classpath:/static/ folder.
       1)To serve HTML/CSS/JS/images directly.
       2)To support Single Page Applications (SPA) like React/Angular — which often need all frontend assets bundled and served from the same backend.
       3)You can control where static files live and what URL paths map to them.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    /*If someone visits the root path /, just forward them to /index.html — don’t try to find a controller.
        1)To load your main frontend file (index.html) when the app starts.
        2)Required for SPAs so frontend routes (like /dashboard) still load the app.
        3)Avoids having to write a controller just to return the main page.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    /*
    WebConfig uses minimal, explicit overrides on top of Spring Boot defaults to:
    Guarantee static assets are reachable under any path (/**).
    Ensure the root path (/) always serves the SPA entry point via a server-side forward.
    Keep every tweak lightweight by relying on WebMvcConfigurer rather than replacing Spring’s auto-config.
    */
}
