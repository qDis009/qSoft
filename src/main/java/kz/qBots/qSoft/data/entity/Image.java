package kz.qBots.qSoft.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(schema = "market", name = "image")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String path;

  @ManyToOne
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "shop_feedback_id", referencedColumnName = "id")
  private ShopFeedback shopFeedback;
}
