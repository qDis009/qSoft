package kz.qBots.qSoft.config.constants;

import kz.qBots.qSoft.data.enums.PaymentType;

import java.util.Map;

public class PaymentTypeConstants {
  public static final Map<PaymentType, String> PAYMENT_TYPE_STRING_MAP =
      Map.of(
          PaymentType.CASH,
          "Наличными при получении",
          PaymentType.RENDER,
          "Перевод на Kaspi",
          PaymentType.INSTALMENT,
          "Рассрочка");
}