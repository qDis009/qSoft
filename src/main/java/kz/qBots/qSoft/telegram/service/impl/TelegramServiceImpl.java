package kz.qBots.qSoft.telegram.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.qBots.qSoft.config.property.TelegramProperty;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    private final ObjectMapper objectMapper;
    private final RequestConfig requestConfig;
    private final TelegramProperty telegramProperty;
    private final DefaultBotOptions defaultBotOptions;
    private final CloseableHttpClient closeableHttpClient;

    @Override
    public int sendMessage(SendMessage sendMessage) throws TelegramApiException {
        Assert.notNull(sendMessage,"send message object is null");
        return this.sendApiMethod(sendMessage).getMessageId();
    }

    @Override
    public void deleteMessage(Long chatId, Integer messageId) throws TelegramApiException {
        Assert.notNull(messageId,"message id is null");
        try{
            this.sendApiMethod(new DeleteMessage(String.valueOf(chatId),messageId));
        }catch (TelegramApiException e){
            log.warn("Cannot delete message with id: {} in chat: {}", messageId, chatId);
        }
    }
    private <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(
            Method method) throws TelegramApiException {
        try {
            String responseContent = this.sendMethodRequest(method);
            return method.deserializeResponse(responseContent);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
        }
    }


    private <T extends Serializable, Method extends BotApiMethod<T>> String sendMethodRequest(
            Method method)
            throws TelegramApiValidationException, IOException {
        method.validate();
        String url = this.getBaseUrl() + method.getMethod();
        HttpPost httppost = this.configuredHttpPost(url);
        httppost.addHeader("charset", StandardCharsets.UTF_8.name());
        httppost.setEntity(new StringEntity(objectMapper.writeValueAsString(method),
                ContentType.APPLICATION_JSON));
        return this.sendHttpPostRequest(httppost);
    }

    private String getBaseUrl() {
        return ApiConstants.BASE_URL + telegramProperty.getToken() + "/";
    }

    private HttpPost configuredHttpPost(String url) {
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        return httppost;
    }

    private String sendHttpPostRequest(HttpPost httppost) throws IOException {
        CloseableHttpResponse response = this.closeableHttpClient.execute(httppost,
                this.defaultBotOptions.getHttpContext());

        String responseEntity;
        try {
            responseEntity = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (Throwable throwable) {
            if (response != null) {
                try {
                    response.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
            }
            throw throwable;
        }
        response.close();
        return responseEntity;
    }
}
