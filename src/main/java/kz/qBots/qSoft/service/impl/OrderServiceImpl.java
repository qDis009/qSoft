package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.config.constants.DeliveryTypeConstants;
import kz.qBots.qSoft.config.constants.PaymentTypeConstants;
import kz.qBots.qSoft.config.constants.RoleConstants;
import kz.qBots.qSoft.data.component.CartComponent;
import kz.qBots.qSoft.data.component.OrderComponent;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.data.enums.DeliveryType;
import kz.qBots.qSoft.data.enums.OrderStatus;
import kz.qBots.qSoft.mapper.OrderMapper;
import kz.qBots.qSoft.rest.request.OrderRequest;
import kz.qBots.qSoft.service.OrderService;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderComponent orderComponent;
  private final OrderMapper orderMapper;
  private final CartComponent cartComponent;
  private final UserComponent userComponent;
  private final TelegramService telegramService;

  @Override
  public Page<OrderDto> findByUserId(int id, Pageable pageable) {
    return orderComponent.findByUserId(id, pageable).map(orderMapper::mapOrderToOrderDto);
  }

  @Override
  public OrderDto findById(int id) {
    return orderMapper.mapOrderToOrderDto(orderComponent.findById(id));
  }

  @Override
  @Transactional
  public OrderDto order(OrderRequest orderRequest) {
    Order order = orderComponent.create(orderMapper.mapOrderRequestToOrder(orderRequest));
    User user = userComponent.findById(orderRequest.getUserId());
    Set<Cart> orderCarts = order.getCarts();
    setOrderToCarts(orderCarts, order);
    order.setOrderStatus(OrderStatus.NEW);
    orderComponent.update(order);
    sendOrderNotificationToClient(user, order, orderCarts);
    sendNotificationToManager(order);
    return orderMapper.mapOrderToOrderDto(order);
  }

  private void sendNotificationToManager(Order order) {
    List<User> managers = userComponent.findByRoleName("MANAGER");
    StringBuilder message = new StringBuilder();
    message
        .append("Клиент оформил заказ!\n")
        .append("Заказ №")
        .append(order.getId())
        .append("\n")
        .append("Тип оплаты: ")
        .append(PaymentTypeConstants.PAYMENT_TYPE_STRING_MAP.get(order.getPaymentType()))
        .append("\n")
        .append("Доставка: ")
        .append(DeliveryTypeConstants.DELIVERY_TYPE_STRING_MAP.get(order.getDeliveryType()))
        .append("\n")
        .append("ФИО: ")
        .append(order.getFullName())
        .append("\n")
        .append("Номер телефона: ")
        .append(order.getPhoneNumber())
        .append("\n")
        .append("Адрес: ")
        .append(
            order.getDeliveryType() == DeliveryType.DELIVERY
                ? order.getAddress()
                : DeliveryTypeConstants.DELIVERY_TYPE_STRING_MAP.get(DeliveryType.PICKUP))
        .append("\n")
        .append("Название ИП: ")
        .append(order.getIEName())
        .append("\n")
        .append("Название магазина: ")
        .append(order.getShopName())
        .append("\n")
        .append("Комментарий к заказу: ")
        .append(order.getComment());
    for (User manager : managers) {
      SendMessage sendMessage =
          SendMessage.builder().text(message.toString()).chatId(manager.getChatId()).build();
      try {
        telegramService.sendMessage(sendMessage);
      } catch (TelegramApiException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void sendOrderNotificationToClient(User user, Order order, Set<Cart> orderCarts) {
    StringBuilder messageText = new StringBuilder();
    messageText.append("Ayan-market - Ваш заказ (№").append(order.getId()).append(")").append("\n");
    messageText.append(order.getCreated()).append(" Вы оформили заказ").append("\n");
    for (Cart cart : orderCarts) {
      messageText
          .append(cart.getItem().getNameRu())
          .append(" (")
          .append(cart.getItemCount())
          .append(" шт. KZT ")
          .append(cart.getTotalPrice() - cart.getTotalDiscount())
          .append(")")
          .append("\n");
    }
    SendMessage sendMessage =
        SendMessage.builder().text(messageText.toString()).chatId(user.getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  private void setOrderToCarts(Set<Cart> orderCarts, Order order) {
    for (Cart orderCart : orderCarts) {
      Cart cart = cartComponent.findById(orderCart.getId());
      cart.setOrder(order);
      cart.setEnabled(false);
      order.addDiscount(cart.getTotalDiscount());
      order.addTotal(cart.getTotalPrice());
      cartComponent.update(cart);
    }
  }

  @Override
  public void acceptOrderByManager(int id) {
    Order order = orderComponent.findById(id);
    order.setOrderStatus(OrderStatus.ACCEPTED_BY_MANAGER);
    sendNotificationToStorekeeper(order);
    sendManagerNotificationToClient(order);
    orderComponent.update(order);
  }

  private void sendManagerNotificationToClient(Order order) {
    String message =
        "Ваш заказ №"
            + order.getId()
            + " принят менеджером!"
            + "\n"
            + "Ожидайте когда с Вами свяжутся. Спасибо!";
    SendMessage sendMessage =
        SendMessage.builder().text(message).chatId(order.getUser().getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  private void sendNotificationToStorekeeper(Order order) {
    List<User> storekeepers = userComponent.findByRoleName("STOREKEEPER");
    StringBuilder message = new StringBuilder();
    message
        .append("Менеджер принял заказ!\n")
        .append("Заказ №")
        .append(order.getId())
        .append("\n")
        .append("Тип оплаты: ")
        .append(PaymentTypeConstants.PAYMENT_TYPE_STRING_MAP.get(order.getPaymentType()))
        .append("\n")
        .append("Доставка: ")
        .append(DeliveryTypeConstants.DELIVERY_TYPE_STRING_MAP.get(order.getDeliveryType()))
        .append("\n")
        .append("ФИО: ")
        .append(order.getFullName())
        .append("\n")
        .append("Номер телефона: ")
        .append(order.getPhoneNumber())
        .append("\n")
        .append("Адрес: ")
        .append(
            order.getDeliveryType() == DeliveryType.DELIVERY
                ? order.getAddress()
                : DeliveryTypeConstants.DELIVERY_TYPE_STRING_MAP.get(DeliveryType.PICKUP))
        .append("\n")
        .append("Название ИП: ")
        .append(order.getIEName())
        .append("\n")
        .append("Название магазина: ")
        .append(order.getShopName())
        .append("\n")
        .append("Комментарий к заказу: ")
        .append(order.getComment());
    for (User storekeeper : storekeepers) {
      SendMessage sendMessage =
          SendMessage.builder().text(message.toString()).chatId(storekeeper.getChatId()).build();
      try {
        telegramService.sendMessage(sendMessage);
      } catch (TelegramApiException e) {
        // TODO log
      }
    }
  }

  @Override
  public void setStatus(int id, String status) {
    orderComponent.setStatus(id, status);
  }

  @Override
  public List<OrderDto> getManagerNewOrders() {
    return orderComponent.findByStatus(OrderStatus.NEW).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public List<OrderDto> getManagerAcceptedOrders() {
    List<OrderStatus> excludedOrderStatus =
        List.of(OrderStatus.NEW, OrderStatus.GIVEN, OrderStatus.REJECTED);
    return orderComponent.findByExcludedOrderStatus(excludedOrderStatus).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public void rejectOrder(int id, String reason, String role) {
    Order order = orderComponent.findById(id);
    order.setOrderStatus(OrderStatus.REJECTED);
    order.setRejectReason(reason);
    sendRejectReasonToClient(reason, order, role);
    orderComponent.update(order);
  }

  private void sendRejectReasonToClient(String reason, Order order, String role) {
    String message =
        "К сожалению, "
            + RoleConstants.ROLE_MAP.get(role)
            + " Отклонил Ваш заказ №"
            + order.getId()
            + " Причина: "
            + reason;
    SendMessage sendMessage =
        SendMessage.builder().text(message).chatId(order.getUser().getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  @Override
  public List<OrderDto> getInWayOrders() {
    return orderComponent.findByStatus(OrderStatus.IN_THE_WAY).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public List<OrderDto> getManagerCompletedOrders() {
    List<OrderStatus> completedOrderStatuses = List.of(OrderStatus.GIVEN, OrderStatus.REJECTED);
    return orderComponent.findByOrderStatuses(completedOrderStatuses).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public List<OrderDto> getStorekeeperNewOrders() {
    return orderComponent.findByStatus(OrderStatus.ACCEPTED_BY_MANAGER).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public void acceptOrderByStorekeeper(int id) {
    Order order = orderComponent.findById(id);
    order.setOrderStatus(OrderStatus.ACCEPTED_BY_STOREKEEPER);
    sendStorekeeperNotificationToClient(order);
    orderComponent.update(order);
  }

  private void sendStorekeeperNotificationToClient(Order order) {
    String message = "Ваш заказ №" + order.getId() + " принят кладовщиком";
    SendMessage sendMessage =
        SendMessage.builder().text(message).chatId(order.getUser().getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO
    }
  }

  @Override
  public List<OrderDto> getStorekeeperAcceptedOrders() {
    return orderComponent.findByStatus(OrderStatus.ACCEPTED_BY_STOREKEEPER).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public void completeOrderByStorekeeper(int id) {
    Order order = orderComponent.findById(id);
    order.setOrderStatus(OrderStatus.COMPLETED);
    sendCompleteNotificationToClient(order);
    if (order.getDeliveryType().equals(DeliveryType.DELIVERY)) {
      sendCompleteNotificationToCourier(order);
    }
    orderComponent.update(order);
  }

  private void sendCompleteNotificationToCourier(Order order) {
    List<User> couriers = userComponent.findByRoleName("COURIER");
    StringBuilder message = new StringBuilder();
    message
        .append("Заказ собран!\n")
        .append("Заказ №")
        .append(order.getId())
        .append("\n")
        .append("Тип оплаты: ")
        .append(PaymentTypeConstants.PAYMENT_TYPE_STRING_MAP.get(order.getPaymentType()))
        .append("\n")
        .append("Доставка: ")
        .append(DeliveryTypeConstants.DELIVERY_TYPE_STRING_MAP.get(DeliveryType.DELIVERY))
        .append("\n")
        .append("ФИО: ")
        .append(order.getFullName())
        .append("\n")
        .append("Номер телефона: ")
        .append(order.getPhoneNumber())
        .append("\n")
        .append("Адрес: ")
        .append(order.getAddress())
        .append("\n")
        .append("Название ИП: ")
        .append(order.getIEName())
        .append("\n")
        .append("Название магазина: ")
        .append(order.getShopName())
        .append("\n")
        .append("Комментарий к заказу: ")
        .append(order.getComment());
    for (User courier : couriers) {
      SendMessage sendMessage =
          SendMessage.builder().text(message.toString()).chatId(courier.getChatId()).build();
      try {
        telegramService.sendMessage(sendMessage);
      } catch (TelegramApiException e) {
        // TODO log
      }
    }
  }

  private void sendCompleteNotificationToClient(Order order) {
    StringBuilder message = new StringBuilder();
    if (order.getDeliveryType().equals(DeliveryType.PICKUP)) {
      message
          .append("Ваш заказ №")
          .append(order.getId())
          .append(" собран!")
          .append("\n")
          .append("Вы можете забрать заказ по адресу: ");
    } else {
      message
          .append("Ваш заказ №")
          .append(order.getId())
          .append(" собран!")
          .append(" Назначаем курьера");
    }
    SendMessage sendMessage =
        SendMessage.builder().text(message.toString()).chatId(order.getUser().getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  @Override
  public List<OrderDto> getStorekeeperCompletedOrders() {
    List<OrderStatus> excludedOrderStatus =
        List.of(
            OrderStatus.NEW, OrderStatus.ACCEPTED_BY_MANAGER, OrderStatus.ACCEPTED_BY_STOREKEEPER);
    return orderComponent.findByExcludedOrderStatus(excludedOrderStatus).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public List<OrderDto> getCourierNewOrders() {
    return orderComponent
        .findByOrderStatusAndDeliveryType(OrderStatus.COMPLETED, DeliveryType.DELIVERY)
        .stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public void acceptOrderByCourier(int id, int courierId) {
    Order order = orderComponent.findById(id);
    User courier = userComponent.findById(courierId);
    order.setOrderStatus(OrderStatus.ACCEPTED_BY_COURIER);
    order.setCourier(courier);
    sendCourierAcceptedNotificationToClient(order, courierId);
    orderComponent.update(order);
  }

  private void sendCourierAcceptedNotificationToClient(Order order, int courierId) {
    User courier = userComponent.findById(courierId);
    String message =
        "Ваш заказ №"
            + order.getId()
            + " принят курьером!\n"
            + "Имя курьера: "
            + courier.getFullName()
            + "\n"
            + "Номер телефона: "
            + courier.getPhoneNumber();
    SendMessage sendMessage =
        SendMessage.builder().text(message).chatId(order.getUser().getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  @Override
  public List<OrderDto> getCourierAcceptedOrders(int courierId) {
    return orderComponent
        .findByOrderStatusAndCourierId(OrderStatus.ACCEPTED_BY_COURIER, courierId)
        .stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public List<OrderDto> getCourierInWayOrders(int courierId) {
    return orderComponent.findByOrderStatusAndCourierId(OrderStatus.IN_THE_WAY, courierId).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public void acceptInWayOrder(int id) {
    Order order = orderComponent.findById(id);
    order.setOrderStatus(OrderStatus.IN_THE_WAY);
    courierAcceptedInWayNotificationToClient(order);
    orderComponent.update(order);
  }

  private void courierAcceptedInWayNotificationToClient(Order order) {
    String message =
        "Курьер выехал к Вам! \n"
            + "Имя курьера: "
            + order.getCourier().getFullName()
            + "\n"
            + "Телефон курьера: "
            + order.getCourier().getPhoneNumber();
    SendMessage sendMessage =
        SendMessage.builder().text(message).chatId(order.getUser().getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  @Override
  public List<OrderDto> getCourierCompletedOrders(int courierId) {
    List<OrderStatus> orderStatuses = List.of(OrderStatus.GIVEN, OrderStatus.REJECTED);
    return orderComponent.findByOrderStatusesAndCourierId(orderStatuses, courierId).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public void sendCodeToClient(int id) {
    Order order = orderComponent.findById(id);
    int code = generateCode();
    order.setCode(code);
    sendMessageWithCodeToClient(order, code);
    orderComponent.update(order);
  }

  @Override
  public boolean enterCode(int id, int code) {
    Order order=orderComponent.findById(id);
    if(order.getCode()==code){
      order.setOrderStatus(OrderStatus.GIVEN);
    }
    orderComponent.update(order);
    return order.getCode()==code;
  }

  private void sendMessageWithCodeToClient(Order order, int code) {
    String message = "Ваш заказ №" +
            order.getId() +
            " доставлен!" +
            "\n" +
            "Пожалуйста продиктуйте данный код курьеру для подтверждения доставки:\n" +
            code;
    SendMessage sendMessage =
        SendMessage.builder().text(message).chatId(order.getUser().getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  private int generateCode() {
    Random random = new Random();
    return 1000 + random.nextInt(9000);
  }
}
