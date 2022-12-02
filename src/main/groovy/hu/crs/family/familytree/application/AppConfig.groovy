package hu.crs.family.familytree.application

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    ObjectMapper objectMapper() {
        new ObjectMapper()
    }
}
