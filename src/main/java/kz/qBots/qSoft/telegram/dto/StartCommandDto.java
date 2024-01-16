package kz.qBots.qSoft.telegram.dto;

import kz.qBots.qSoft.telegram.enums.Roles;

public record StartCommandDto(Integer shopId, long chatId, Roles role) {
  public String buildWebAppInfoUrl(String url) {
    return url + "?chat_id=" + this.chatId + "&role=" + this.role;
  }
}
