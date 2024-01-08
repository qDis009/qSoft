package kz.qBots.qSoft.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "market", name = "shop")
public class Shop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @OneToMany(mappedBy = "shop")
  private Set<Order> orders;

  @OneToMany(mappedBy = "shop")
  private Set<ShopFeedback> shopFeedbacks;

  @ManyToMany
  @JoinTable(
      name = "user_shop",
      joinColumns = @JoinColumn(name = "shop_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> users;

  @ManyToMany
  @JoinTable(
      name = "shop_category",
      joinColumns = @JoinColumn(name = "shop_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories;

  @ManyToMany
  @JoinTable(
      name = "shop_sub_category",
      joinColumns = @JoinColumn(name = "shop_id"),
      inverseJoinColumns = @JoinColumn(name = "sub_category_id"))
  private Set<SubCategory> subCategories;

  @OneToMany(mappedBy = "shop")
  private Set<Item> items;

  @OneToMany(mappedBy = "shop")
  private Set<Complaint> complaints;
}
