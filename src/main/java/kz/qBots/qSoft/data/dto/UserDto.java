package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.*;
import kz.qBots.qSoft.data.enums.Language;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
  private Integer id;
  private long chatId;
  private boolean deleted;
  private String fullName;
  private String phoneNumber;
  private String IEName;
  private String tgUserName;
  private Language language;
  private String address;
  private Set<Shop> shops;
}
