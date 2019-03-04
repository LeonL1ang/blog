package wiki.leon.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wiki.leon.base.pojo.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

}
