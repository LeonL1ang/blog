package wiki.leon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wiki.leon.pojo.User;

public interface UserDao extends JpaRepository<User, String> {

}
