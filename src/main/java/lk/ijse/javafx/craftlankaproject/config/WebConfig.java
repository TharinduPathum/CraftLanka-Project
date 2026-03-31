package lk.ijse.javafx.craftlankaproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps the URL "http://localhost:8080/uploads/..."
        // to the actual folder on your computer
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/path/to/your/external/folder/");
    }
}
