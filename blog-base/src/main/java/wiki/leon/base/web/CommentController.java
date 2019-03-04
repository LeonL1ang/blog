package wiki.leon.base.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.leon.blog.common.entity.Result;
import wiki.leon.blog.common.entity.ResultStatusEnum;
import wiki.leon.base.pojo.Comment;
import wiki.leon.base.service.CommentService;

/***
 *
 *  评论 comment
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list/{page}/{size}/{aId}")
    public Result getCommentByAId(@PathVariable String aId, @PathVariable Integer page, @PathVariable Integer size){
        return Result.to(ResultStatusEnum.OK, commentService.findListPageByAId(aId, page, size));
    }

    @PostMapping
    public Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return Result.to(ResultStatusEnum.OK, comment);
    }
}

