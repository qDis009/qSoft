package kz.qBots.qSoft.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.qBots.qSoft.data.enums.ClientType;
import kz.qBots.qSoft.data.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "market",name = "item")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nameRu;
  private String nameKz;
  private int soldCount;
  private int discountPercentage;
  private int retailPrice;
  private int wholesalePrice;
  private boolean enabled = true;
  private boolean deleted = false;
  private double grade;
  private String imageUrl;
  private String imageUrl3D;
  private String article;
  private String descriptionKz;
  private String descriptionRu;
  @Enumerated(EnumType.STRING)
  private ItemType itemType;

  @CreationTimestamp
  @Column(updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss.SSS 'Z'")
  private LocalDateTime created;

  @OneToMany(mappedBy = "item")
  private Set<ItemFeedback> itemFeedbacks;

  @OneToMany(mappedBy = "item")
  private Set<Cart> carts;

  @ManyToMany
  @JoinTable(
      name = "favourite",
      joinColumns = @JoinColumn(name = "item_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> users;

  @ManyToOne
  @JoinColumn(name = "shop_id", referencedColumnName = "id")
  private Shop shop;

  @JsonIgnore
  public double getDiscount() {
    return retailPrice * discountPercentage / 100d;
  }
}
