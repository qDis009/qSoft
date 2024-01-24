package kz.qBots.qSoft.config.constants;

import kz.qBots.qSoft.data.enums.DeliveryType;

import java.util.Map;

public class DeliveryTypeConstants {
  public static final Map<DeliveryType, String> DELIVERY_TYPE_STRING_MAP =
      Map.of(DeliveryType.DELIVERY, "Курьером", DeliveryType.PICKUP, "Самовывоз");
}
