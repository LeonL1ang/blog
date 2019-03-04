package wiki.leon.wiki.leon.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wiki.leon.blog.common.entity.Result;
import wiki.leon.blog.common.entity.ResultStatusEnum;
import wiki.leon.utils.FastDFSClientUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
public class FileController {

    @RequestMapping(value = "file/uploadFast",method = RequestMethod.GET)
    public Result uploadFast(HttpServletRequest request)throws Exception {
        String path = "C:\\Users\\Leon\\Desktop\\default-head.png";
        File file = new File(path);
        System.out.println(file.getAbsoluteFile());
        String fileId = FastDFSClientUtils.upload(file, path);
        System.out.println("本地文件：" + path + "，上传成功！ 文件ID为：" + fileId);
        return Result.to(ResultStatusEnum.OK);
    }

}
