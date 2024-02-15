package kz.qBots.qSoft.telegram.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.qBots.qSoft.config.property.TelegramProperty;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
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
  private static final ContentType TEXT_PLAIN_CONTENT_TYPE =
      ContentType.create("text/plain", StandardCharsets.UTF_8);

  @Override
  public int sendMessage(SendMessage sendMessage) throws TelegramApiException {
    Assert.notNull(sendMessage, "send message object is null");
    return this.sendApiMethod(sendMessage).getMessageId();
  }

  @Override
  public void deleteMessage(Long chatId, Integer messageId) throws TelegramApiException {
    Assert.notNull(messageId, "message id is null");
    try {
      this.sendApiMethod(new DeleteMessage(String.valueOf(chatId), messageId));
    } catch (TelegramApiException e) {
      log.warn("Cannot delete message with id: {} in chat: {}", messageId, chatId);
    }
  }

  private <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method)
      throws TelegramApiException {
    try {
      String responseContent = this.sendMethodRequest(method);
      return method.deserializeResponse(responseContent);
    } catch (IOException e) {
      throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
    }
  }

  private <T extends Serializable, Method extends BotApiMethod<T>> String sendMethodRequest(
      Method method) throws TelegramApiValidationException, IOException {
    method.validate();
    String url = this.getBaseUrl() + method.getMethod();
    HttpPost httppost = this.configuredHttpPost(url);
    httppost.addHeader("charset", StandardCharsets.UTF_8.name());
    httppost.setEntity(
        new StringEntity(objectMapper.writeValueAsString(method), ContentType.APPLICATION_JSON));
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
    CloseableHttpResponse response =
        this.closeableHttpClient.execute(httppost, this.defaultBotOptions.getHttpContext());

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

  @Override
  public int sendDocument(SendDocument sendDocument) throws TelegramApiException {
    assertParamNotNull(sendDocument, "sendDocument");
    sendDocument.validate();
    try {
      String url = this.getBaseUrl() + SendDocument.PATH;
      HttpPost httppost = configuredHttpPost(url);

      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setLaxMode();
      builder.setCharset(StandardCharsets.UTF_8);
      builder.addTextBody(
          SendDocument.CHATID_FIELD, sendDocument.getChatId(), TEXT_PLAIN_CONTENT_TYPE);

      addInputFile(builder, sendDocument.getDocument(), SendDocument.DOCUMENT_FIELD, true);
      if (sendDocument.getReplyMarkup() != null) {
        builder.addTextBody(SendDocument.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendDocument.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendDocument.getReplyToMessageId() != null) {
        builder.addTextBody(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendDocument.getMessageThreadId() != null) {
        builder.addTextBody(SendDocument.MESSAGETHREADID_FIELD, sendDocument.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendDocument.getCaption() != null) {
        builder.addTextBody(SendDocument.CAPTION_FIELD, sendDocument.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
        if (sendDocument.getParseMode() != null) {
          builder.addTextBody(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
        }
      }
      if (sendDocument.getDisableNotification() != null) {
        builder.addTextBody(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendDocument.getProtectContent() != null) {
        builder.addTextBody(SendDocument.PROTECTCONTENT_FIELD, sendDocument.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }

      if (sendDocument.getAllowSendingWithoutReply() != null) {
        builder.addTextBody(SendDocument.ALLOWSENDINGWITHOUTREPLY_FIELD, sendDocument.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendDocument.getDisableContentTypeDetection() != null) {
        builder.addTextBody(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendDocument.getCaptionEntities() != null) {
        builder.addTextBody(SendDocument.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendDocument.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendDocument.getThumbnail() != null) {
        addInputFile(builder, sendDocument.getThumbnail(), SendDocument.THUMBNAIL_FIELD, false);
        builder.addTextBody(SendDocument.THUMBNAIL_FIELD, sendDocument.getThumbnail().getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
      }
      HttpEntity multipart = builder.build();
      httppost.setEntity(multipart);
      return sendDocument.deserializeResponse(sendHttpPostRequest(httppost)).getMessageId();
    } catch (IOException e) {
      throw new TelegramApiException("Unable to send document", e);
    }
  }

  private void addInputFile(
      MultipartEntityBuilder builder, InputFile file, String fileField, boolean addField) {
    if (file.isNew()) {
      if (file.getNewMediaFile() != null) {
        builder.addBinaryBody(
            file.getMediaName(),
            file.getNewMediaFile(),
            ContentType.APPLICATION_OCTET_STREAM,
            file.getMediaName());
      } else if (file.getNewMediaStream() != null) {
        builder.addBinaryBody(
            file.getMediaName(),
            file.getNewMediaStream(),
            ContentType.APPLICATION_OCTET_STREAM,
            file.getMediaName());
      }
    }

    if (addField) {
      builder.addTextBody(fileField, file.getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
    }
  }

  private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
    if (param == null) {
      throw new TelegramApiException("Parameter " + paramName + " can not be null");
    }
  }
  @Override
  public int sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
    assertParamNotNull(sendPhoto, "sendPhoto");

    sendPhoto.validate();
    try {
      String url = getBaseUrl() + SendPhoto.PATH;
      HttpPost httppost = configuredHttpPost(url);

      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setLaxMode();
      builder.setCharset(StandardCharsets.UTF_8);
      builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
      addInputFile(builder, sendPhoto.getPhoto(), SendPhoto.PHOTO_FIELD, true);

      if (sendPhoto.getReplyMarkup() != null) {
        builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendPhoto.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendPhoto.getReplyToMessageId() != null) {
        builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendPhoto.getMessageThreadId() != null) {
        builder.addTextBody(SendPhoto.MESSAGETHREADID_FIELD, sendPhoto.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendPhoto.getCaption() != null) {
        builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
        if (sendPhoto.getParseMode() != null) {
          builder.addTextBody(SendPhoto.PARSEMODE_FIELD, sendPhoto.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
        }
      }
      if (sendPhoto.getDisableNotification() != null) {
        builder.addTextBody(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendPhoto.getAllowSendingWithoutReply() != null) {
        builder.addTextBody(SendPhoto.ALLOWSENDINGWITHOUTREPLY_FIELD, sendPhoto.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendPhoto.getProtectContent() != null) {
        builder.addTextBody(SendPhoto.PROTECTCONTENT_FIELD, sendPhoto.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendPhoto.getCaptionEntities() != null) {
        builder.addTextBody(SendPhoto.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendPhoto.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (sendPhoto.getHasSpoiler() != null) {
        builder.addTextBody(SendPhoto.HASSPOILER_FIELD, objectMapper.writeValueAsString(sendPhoto.getHasSpoiler()), TEXT_PLAIN_CONTENT_TYPE);
      }
      HttpEntity multipart = builder.build();
      httppost.setEntity(multipart);

      return sendPhoto.deserializeResponse(sendHttpPostRequest(httppost)).getMessageId();
    } catch (IOException e) {
      throw new TelegramApiException("Unable to send photo", e);
    }
  }
}
