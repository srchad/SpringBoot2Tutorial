package com.example.etc;


import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
@Component
public class ProxyCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {
        HttpHost proxy = new HttpHost("proxy.example.com");
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                                                          .setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {

                                                     @Override
                                                     public HttpHost determineProxy(HttpHost target,
                                                                                    HttpRequest request, HttpContext context)
                                                             throws HttpException {
                                                         if (target.getHostName().equals("192.168.0.5")) {
                                                             return null;
                                                         }
                                                         return super.determineProxy(target, request, context);
                                                     }

                                                 }).build();
        restTemplate.setRequestFactory(
                new HttpComponentsClientHttpRequestFactory(httpClient));
    }

}
*/