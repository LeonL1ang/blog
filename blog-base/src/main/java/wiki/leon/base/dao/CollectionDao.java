package wiki.leon.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wiki.leon.base.pojo.Collection;

public interface CollectionDao extends JpaRepository<Collection, Long> {
}
