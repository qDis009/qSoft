package kz.qBots.qSoft.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "market",name = "category")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nameKz;
  private String nameRu;

  @ManyToMany
  @JoinTable(
      name = "shop_category",
      joinColumns = @JoinColumn(name = "category_id"),
      inverseJoinColumns = @JoinColumn(name = "shop_id"))
  private Set<Shop> shops;

  @OneToMany(mappedBy = "category")
  private Set<SubCategory> subCategories;
}
