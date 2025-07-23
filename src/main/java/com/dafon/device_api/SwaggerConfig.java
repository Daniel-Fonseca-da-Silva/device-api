package com.dafon.device_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
    info = @Info(
        title = "Device API",
        version = "1.0",
        description = "API for device management"
    )
)
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
}
