package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByChatId(long chatId);
    @Modifying
    @Transactional
    @Query(value = "update market.user set language = :language where id = :id",nativeQuery = true)
    void setLanguage(int id,String language);
    List<User> findByRoles_Name(String roleName);
    @Query("select u from User u join fetch u.roles")
    List<User> findUsersWithRoles();
}
