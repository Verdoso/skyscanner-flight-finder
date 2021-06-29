package org.greeneyed.skyscannerf.configuration;

import java.util.Arrays;

import javax.validation.constraints.NotBlank;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.greeneyed.summer.util.logging.LoggingClientHttpRequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Data
@Slf4j
@Profile("!test")
public class CommunicationsConfiguration {

    private static final String KEY_HEADER = "x-rapidapi-key";

    private static final String HOST_HEADER = "x-rapidapi-host";

    public static final String SKYSCANNER_API_REST_TEMPLATE = "SKYSCANNER_API_REST_TEMPLATE";

    @Data
    @Validated
    public static class ConfigurationData {
        @NotBlank
        private String apiAddress = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/";

        @NotBlank
        private String key;

        @NotBlank
        private String host;
    }

    @Bean
    LoggingClientHttpRequestInterceptor loggingClientHttpRequestInterceptor() {
        return new LoggingClientHttpRequestInterceptor();
    }

    @Bean
    @ConfigurationProperties(prefix = "skyscanner")
    public ConfigurationData configurationData() {
        return new ConfigurationData();
    }

    @Bean(name = SKYSCANNER_API_REST_TEMPLATE)
    public RestTemplate skyScannerFlightFinderRestTemplate(RestTemplateBuilder builder, ConfigurationData configurationData) {
        log.info("Configuring SkyScanner API to {}", configurationData.getApiAddress());
        ClientHttpRequestInterceptor authenticationHeadersInterceptor = (request, body, execution) -> {
            request.getHeaders().add(KEY_HEADER, configurationData.getKey());
            request.getHeaders().add(HOST_HEADER, configurationData.getHost());
            return execution.execute(request, body);
        };
        return builder.additionalInterceptors(Arrays.asList(authenticationHeadersInterceptor,loggingClientHttpRequestInterceptor()))
                .requestFactory(this::getClientHttpRequestFactory)
                .rootUri(configurationData.getApiAddress())
                .build();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        //@formatter:off
            int timeout = 5000;
            RequestConfig config = RequestConfig.custom()
              .setConnectTimeout(timeout)
              .setConnectionRequestTimeout(timeout)
              .setSocketTimeout(timeout)
              .build();
            CloseableHttpClient client = HttpClientBuilder
              .create()
              .setDefaultRequestConfig(config)
              .build();
            //@formatter:on
        return new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
    }
}
