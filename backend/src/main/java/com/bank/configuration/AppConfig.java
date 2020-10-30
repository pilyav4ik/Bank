package com.bank.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AppConfig {

        @Bean
        public ModelMapper modelMapper() {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT)
                    .setFieldMatchingEnabled(true)
                    .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);
            return mapper;
        }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("100000KB"));
        factory.setMaxRequestSize(DataSize.parse("100000KB"));
        return factory.createMultipartConfig();
    }
}
