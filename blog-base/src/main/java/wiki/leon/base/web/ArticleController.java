package wiki.leon.base.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.leon.blog.common.entity.PageResult;
import wiki.leon.blog.common.entity.Result;
import wiki.leon.blog.common.entity.ResultStatusEnum;
import wiki.leon.base.pojo.Article;
import wiki.leon.base.service.ArticleService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result saveArticle(@RequestBody Article article){
        articleService.save(article);
        return Result.to(ResultStatusEnum.OK);
    }

    @PutMapping
    public Result updateArticle(@RequestBody Article article){
        articleService.update(article);
        return Result.to(ResultStatusEnum.OK);
    }

    @GetMapping("/{aId}")
    public Result getArticleById(@PathVariable Long aId){
        Article article = articleService.getArticle(aId);
        return Result.to(ResultStatusEnum.OK, article);
    }

    @GetMapping("/list")
    public Result getArticleAll(){
        List<Article> list = articleService.getArticleAll();
        return Result.to(ResultStatusEnum.OK, list);
    }

    @GetMapping("/list/{page}/{size}")
    public Result queryByPage(@PathVariable Integer page, @PathVariable Integer size) {
        PageResult<Article> pageData = articleService.getByPage(page, size);
        return Result.to(ResultStatusEnum.OK, pageData);
    }
}
