package kz.qBots.qSoft.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "market",name = "sub_category")
public class SubCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nameKz;
  private String nameRu;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private Category category;

  @ManyToMany
  @JoinTable(
      name = "shop_sub_category",
      joinColumns = @JoinColumn(name = "sub_category_id"),
      inverseJoinColumns = @JoinColumn(name = "shop_id"))
  private Set<Shop> shops;
}
