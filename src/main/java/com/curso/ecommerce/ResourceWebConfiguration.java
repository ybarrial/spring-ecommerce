package com.curso.ecommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

    // CLASE QUE PERMITE APUNTAR HACIA LOS RECURSOS DE LAS IMAGENES PARA OBTENERLOS DE MANERA RELATIVA SOLO PAUNTANDO A ESA URL(/images/**)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }
}
