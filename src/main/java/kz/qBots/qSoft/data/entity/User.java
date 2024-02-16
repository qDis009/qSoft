package kz.qBots.qSoft.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.qBots.qSoft.data.enums.ClientType;
import kz.qBots.qSoft.data.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "market", name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private long chatId;
  @JsonIgnore private Integer lastMessageId;
  private String fullName;
  private String phoneNumber;
  private String IEName;
  private String shopName;
  @JsonIgnore private String tgUserName;

  @Enumerated(EnumType.STRING)
  private Language language = Language.RUS;

  @Enumerated(EnumType.STRING)
  private ClientType clientType = ClientType.RETAIL;

  private String address;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<ItemFeedback> itemFeedbacks;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @JsonIgnore @OneToMany private Set<Item> items;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "user_shop",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "shop_id"))
  private Set<Shop> shops;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<Order> orders;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<Cart> carts;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<ShopFeedback> shopFeedbacks;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<Complaint> complaints;

  @JsonIgnore
  @OneToMany(mappedBy = "courier")
  private Set<Order> acceptedOrders;

  @JsonIgnore @OneToMany private Set<Item> boughtItems;

  public User(long chatId, String tgUserName) {
    this.chatId = chatId;
    this.tgUserName = tgUserName;
  }
}
