package kz.qBots.qSoft.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(schema = "market", name = "cart")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private int itemCount;
  private int itemPrice;
  private double itemDiscount;
  private String itemName;
  private boolean enabled=true;

  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private Item item;
  @JsonIgnore
  public int getTotalPrice() {
    return itemPrice * itemCount;
  }
  @JsonIgnore
  public double getTotalDiscount() {
    return itemCount * itemDiscount;
  }
}
