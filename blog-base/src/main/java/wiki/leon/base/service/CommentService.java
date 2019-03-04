package wiki.leon.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.leon.blog.common.entity.PageResult;
import wiki.leon.base.dao.CommentDao;
import wiki.leon.base.pojo.Comment;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    // article id 获取分页评论list
    public PageResult<Comment> findListPageByAId(String aId, Integer page, Integer size) {
        // 获取pageable
        Pageable pageable = PageRequest.of(page - 1,size);
        Comment comment = new Comment();
        comment.setArticleId(aId);
        // 得到pageData
        Page<Comment> pageData = commentDao.findAll(Example.of(comment), pageable);
        // 返回pageResult对象
        return new PageResult<>(pageData.getTotalPages(), pageData.getContent());
    }

    // 保存评论
    public void save(Comment comment) {
        commentDao.save(comment);
    }
}
