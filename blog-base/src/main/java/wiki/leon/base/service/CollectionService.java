package wiki.leon.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.leon.base.dao.CollectionDao;
import wiki.leon.base.pojo.Collection;

@Service
@Transactional
public class CollectionService {

    @Autowired
    private CollectionDao collectionDao;

    public void save(Collection collection) {
        collectionDao.save(collection);
    }
}
