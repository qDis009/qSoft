package kz.qBots.qSoft.telegram.dto;

import kz.qBots.qSoft.telegram.enums.Role;

public record StartCommandDto(Integer shopId, long chatId, Role role) {
  public String buildWebAppInfoUrl(String url) {
    return url + "?chat_id=" + this.chatId + "&role=" + this.role;
  }
}
