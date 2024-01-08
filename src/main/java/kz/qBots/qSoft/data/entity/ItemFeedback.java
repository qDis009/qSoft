package kz.qBots.qSoft.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(schema = "market", name = "item_feedback")
public class ItemFeedback {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String comment;

  private double grade;

  @CreationTimestamp
  @Column(updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss.SSS 'Z'")
  private LocalDateTime created;

  @ManyToOne
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
