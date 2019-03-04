package wiki.leon.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wiki.leon.base.pojo.Article;

public interface ArticleDao extends JpaRepository<Article, Long> {
}
