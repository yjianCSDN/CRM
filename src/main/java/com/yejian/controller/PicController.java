package com.yejian.controller;

import com.yejian.utils.ResultInfo;
import com.yejian.utils.picUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("picUpload")
public class PicController {

//    @Autowired
//    private picUtils picUtils;

    @PostMapping("upup")
    public ResultInfo uploadImg(@RequestParam("file") MultipartFile file){
        String s = "";
        try {
             s = picUtils.singleFileUpload(file);
        } catch (IOException e) {
            System.out.println("图片上传错误");
            e.printStackTrace();
        }finally {

        }
        return ResultInfo.ok(s,"上传成功");
    }


}
