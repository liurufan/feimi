package org.basic.feimi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.basic.feimi.util.RandomString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    @Qualifier("randomIdGenerator")
    public RandomString randomIdGenerator() {
        return new RandomString(12, true);
    }


    @Bean
    @Qualifier("randomTokenGenerator")
    public RandomString randomTokenGenerator() {
        return new RandomString(32, false);
    }

    @Bean
    @Qualifier("randomOrderIdGenerator")
    public RandomString randomOrderIdGenerator() {
        return new RandomString(16, false);
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory();
        // set the read timeot, this value is in miliseconds
        clientRequestFactory.setConnectTimeout(3000);
        clientRequestFactory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(clientRequestFactory);
        return restTemplate;
    }
}
