package wiki.leon.base.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.leon.base.pojo.Collection;
import wiki.leon.base.service.CollectionService;
import wiki.leon.blog.common.entity.Result;
import wiki.leon.blog.common.entity.ResultStatusEnum;

@CrossOrigin
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping
    public Result save(@RequestBody Collection collection){
        collectionService.save(collection);
        return Result.to(ResultStatusEnum.OK);
    }

//    @GetMapping("/list/{userId}/{page}/{size}")
//    public Result getListByUserId

}
