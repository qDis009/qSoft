package kz.qBots.qSoft.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "market", name = "item")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nameRu;
  private String nameKz;
  private int soldCount;
  private Integer discountPercentage;
  private Integer count;
  private int retailPrice;
  private int wholesalePrice;
  private boolean enabled = true;
  private boolean deleted = false;
  private double grade;
  private double feedbackGrade;
  private int gradeCount;
  private int feedbackGradeCount;
  private String imageUrl;
  private String imageUrl3D;
  private String article;
  private String descriptionKz;
  private String descriptionRu;

  @CreationTimestamp
  @Column(updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss.SSS 'Z'")
  private LocalDateTime created;

  @OneToMany(mappedBy = "item")
  private Set<ItemFeedback> itemFeedbacks;

  @OneToMany(mappedBy = "item")
  private Set<Cart> carts;

  @ManyToOne
  @JoinColumn(name = "shop_id", referencedColumnName = "id")
  private Shop shop;

  @ManyToOne
  @JoinColumn(name = "sub_category_id", referencedColumnName = "id")
  private SubCategory subCategory;

  @JsonIgnore
  public double getDiscount() {
    return retailPrice * discountPercentage / 100d;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return Objects.equals(id, item.id);
  }
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
