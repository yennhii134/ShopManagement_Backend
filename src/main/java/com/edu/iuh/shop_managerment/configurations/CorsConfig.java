package com.edu.iuh.shop_managerment.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // tất cả các đường dẫn đều được phép gọi API
                .allowedOriginPatterns("*") // cho phép tất cả các domain khác gọi API của mình
                .allowedMethods("*") // cho phép tất cả các phương thức GET, POST, PUT, DELETE
                .allowedHeaders("*") // cho phép tất cả các header
                .allowCredentials(true) // cho phép gửi cookie
                .maxAge(3600); // thời gian tối đa cho phép một domain khác gọi API của mình
    }
}
