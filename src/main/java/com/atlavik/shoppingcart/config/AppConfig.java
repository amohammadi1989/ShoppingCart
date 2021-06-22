package com.atlavik.shoppingcart.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created By: Ali Mohammadi
 * Date: 13 Jun, 2021
 */
@Configuration
public class AppConfig {
        @Bean
        public ModelMapper modelMapper(){
                return new ModelMapper();
        }
        @Bean
        public Docket api() {
                return new Docket( DocumentationType.SWAGGER_2)
                        .select()
                        .apis( RequestHandlerSelectors.withClassAnnotation( RestController.class ))
                        .paths( PathSelectors.any())
                        .build();
        }
/*        @Bean
        @Qualifier("bCryptPasswordEncoder")
        public BCryptPasswordEncoder  getBCryptPasswordEncoder(){
                return  new BCryptPasswordEncoder(  );
        }*/
}
