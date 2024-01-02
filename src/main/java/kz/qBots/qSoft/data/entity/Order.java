package kz.qBots.qSoft.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kz.qBots.qSoft.data.enums.DeliveryType;
import kz.qBots.qSoft.data.enums.OrderStatus;
import kz.qBots.qSoft.data.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "market", name = "order")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private int total;
  private double discount;

  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Enumerated(EnumType.STRING)
  private DeliveryType deliveryType;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @CreationTimestamp
  @Column(updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime created;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime delivered;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "shop_id", referencedColumnName = "id")
  private Shop shop;

  @OneToMany(mappedBy = "order")
  private Set<Cart> carts;

  public void addDiscount(double itemDiscount) {
    discount += itemDiscount;
  }

  public void addTotal(double price) {
    total += price;
  }
}
