package com.example.myproject.application;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;

@Configuration
public class MyConfiguration {
    @Bean
    public HttpMessageConverters customerConverters(){
        return  new HttpMessageConverters(  new StringHttpMessageConverter(), new ResourceHttpMessageConverter() );
    }
}
