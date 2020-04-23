package org.linky.application.config;

import org.linky.internal.generators.IGenerator;
import org.linky.internal.generators.implementations.RandomNumberGenerator;
import org.linky.internal.url.IUrlHandler;
import org.linky.internal.url.implementations.UrlStandardHandlerImpl;
import org.linky.internal.user.IUserHandler;
import org.linky.internal.user.implementations.UserStandardHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalConfiguration {

    @Bean
    public IUrlHandler getUrlHandler() {
        return new UrlStandardHandlerImpl();
    }

    @Bean
    public IUserHandler getUserHandler() {
        return new UserStandardHandlerImpl();
    }

    @Bean
    public IGenerator getDefaultGenerator() {
        return new RandomNumberGenerator();
    }

}
