package wiki.leon.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.leon.blog.common.entity.PageResult;
import wiki.leon.base.dao.ArticleDao;
import wiki.leon.base.pojo.Article;
import java.util.List;


@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public Article getArticle(Long aId) {
        // 从redis缓存中获得数据
        Article article = (Article) redisTemplate.opsForValue().get("article_id" + aId);
        // 不存在时到数据库查询
        if(article == null){
            article = articleDao.findById(aId).get();
            // 将数据放入缓存中
            redisTemplate.opsForValue().set("article_id" + aId, article);
        }
        return article;
    }

    public List<Article> getArticleAll() {
        return articleDao.findAll();
    }

    public PageResult<Article> getByPage(Integer page, Integer size) {

        // 获取PageAble对象
        Pageable pageable = PageRequest.of(page - 1, size);
        // 得到Page对象
        Page<Article> pageData = articleDao.findAll(pageable);
        // 返回分页结果
        return new PageResult<>(pageData.getTotalPages(), pageData.getContent());

    }

    public void save(Article article) {
        articleDao.save(article);
    }

    public void delete(Long aId){
        articleDao.deleteById(aId);
        redisTemplate.delete("article_id" + aId);
    }
    public void update(Article article) {
        articleDao.save(article);
        redisTemplate.delete("article_id" + article.getId());
    }
}
