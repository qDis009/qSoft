package kz.qBots.qSoft.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.qBots.qSoft.telegram.handler.TelegramUpdateHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.facilities.TelegramHttpClientBuilder;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class ApplicationConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramUpdateHandler telegramUpdateHandler) throws TelegramApiException {
        TelegramBotsApi api=new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramUpdateHandler);
        return api;
    }
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
    @Bean
    public RequestConfig requestConfig() {
        int timeout = 75000;
        return RequestConfig.copy(RequestConfig.custom().build()).setSocketTimeout(timeout)
                .setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    public CloseableHttpClient closeableHttpClient(DefaultBotOptions defaultBotOptions) {
        return TelegramHttpClientBuilder.build(defaultBotOptions);
    }
}
