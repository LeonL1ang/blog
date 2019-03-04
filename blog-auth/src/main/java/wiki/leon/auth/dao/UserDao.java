package wiki.leon.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wiki.leon.auth.pojo.User;

public interface UserDao extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User getByEmail(String email);
}
