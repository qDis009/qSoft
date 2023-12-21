package kz.qBots.qSoft.telegram.dto;

public record StartCommandDto(Integer shopId, long chatId) {
  public String buildWebAppInfoUrl(String url) {
    return url + "?chat_id=" + this.chatId;
  }
}
