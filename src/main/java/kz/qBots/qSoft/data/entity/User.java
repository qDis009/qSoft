package kz.qBots.qSoft.data.entity;

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
    private Integer lastMessageId;
    private boolean deleted = false;
    private String fullName;
    private String phoneNumber;
    private String IEName;
    private String tgUserName;

    @Enumerated(EnumType.STRING)
    private Language language = Language.RUS;

    @Enumerated(EnumType.STRING)
    private ClientType clientType = ClientType.RETAIL;

    private String address;

    @OneToMany(mappedBy = "user")
    private Set<ItemFeedback> itemFeedbacks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany
    private Set<Item> items;

    @ManyToMany
    @JoinTable(
            name = "user_shop",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id"))
    private Set<Shop> shops;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<Cart> carts;

    @OneToMany(mappedBy = "user")
    private Set<ShopFeedback> shopFeedbacks;

    @OneToMany(mappedBy = "user")
    private Set<Complaint> complaints;

    public User(long chatId, String tgUserName) {
        this.chatId = chatId;
        this.tgUserName = tgUserName;
    }
}
