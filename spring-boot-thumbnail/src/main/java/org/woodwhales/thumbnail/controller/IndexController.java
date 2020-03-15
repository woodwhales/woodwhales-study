package org.woodwhales.thumbnail.controller;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.woodwhales.thumbnail.utils.ClassPathTools;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @projectName: spring-boot-image
 * @author: woodwhales
 * @date: 20.3.12 18:52
 * @description:
 */
@Slf4j
@Controller
public class IndexController {

    // 限制图片最大压缩像素宽度为100
    public static final int WIDTH = 100;
    // 限制图片最大压缩像素高度为100
    public static final int HEIGHT = 100;

    @GetMapping({"", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/thumbnail")
    public String thumbnail(@RequestParam("image") MultipartFile file, HttpServletRequest request, Model model) throws IOException {
        String uploadPath = null;
        try {
            File uploadFile = ClassPathTools.getClassPathChildFile("static/upload/", file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);

            String des = ClassPathTools.buildChildFileAbsolutePath("static/thumb/", "thumb_" + file.getOriginalFilename());
            //String des = desDir.getAbsolutePath() + File.separatorChar + "thumb_"+ file.getOriginalFilename();
            Thumbnails.of(new FileInputStream(uploadFile))
                    .size(WIDTH, HEIGHT)
                    //.keepAspectRatio(false)  // 不保持宽高比，直接压缩成 WIDTH x HEIGHT, 可能图片会“变形”
                    .toFile(des);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

        model.addAttribute("imageURL", basePath + "upload/" + file.getOriginalFilename());
        model.addAttribute("thumbnailImageURL", basePath + "thumb/thumb_" + file.getOriginalFilename());
        return "thumbnail";
    }
}
