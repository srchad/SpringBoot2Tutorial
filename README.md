# SpringBoot2Tutorial


# 33. Calling REST Services with RestTemplate


* **RestTemplate**은 보통 customize를 많이 하므로 auto configure 미제공
* 하지만, **RestTemplateBuilder** 로 auto configure 지원 => 실습


## 33.1 RestTemplate Customization
* 3가지 방식 지원
1. customization 범위을 최소화 화려면 **RestTemplateBuilder**의 method를 호출하는 방식으로 customization
2. application 수준으로 하려면 **RestTemplateCustomizer** 사용 => 실습
3. **RestTemplateBuilder** 빈을 생성해서 자체적으로 사용 => auto-configuration off

# 34. Calling REST Services with WebClient
* Spring WebFlux를 사용한다면, **WebClient**를 사용해서 rest service call 가능 => 실습
* RestTemplate에 비해 fully reactive
* WebClient.create() 로 자체 client 생성 가능

## 34.1 RestTemplate Customization
* 3가지 방식 지원
1. customization 범위을 최소화 화려면 **WebClient.Builder **의 method를 호출하는 방식으로 customization
2. application 수준으로 하려면 **WebClientCustomizer** 사용
3. **WebClient.create()**로 빈생성해서 자체적으로 사용 => auto-configuration off