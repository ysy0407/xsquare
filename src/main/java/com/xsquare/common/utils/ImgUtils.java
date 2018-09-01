package com.xsquare.common.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName
 * @Description 输出图片的util
 * @Author YuSongYuan
 * @Create 2018-03-27 15:04
 * @Package_name com.xsquare.common.utils
 * @Project_name xsquare
 * @Since JDK 1.8
 **/
@RestController
public class ImgUtils {

    static final String IMAGE_PATH = "img/";

    //根据图片名称获取图片
    @RequestMapping("getImg")
    public void getImageByName(HttpServletResponse response, @RequestParam Map<String, Object> params){
        System.out.println("--------getImageByName:"+params.get("imgName").toString());
        response.setContentType("image/jpeg");
        BufferedImage image = null;
        try {
            Resource resource = new ClassPathResource(IMAGE_PATH+params.get("imgName").toString());
            //不使用sun公司的JPEGImageDecoder，这是其私有的，使用ImageIO.read
//            JPEGImageDecoder decoderFile = JPEGCodec.createJPEGDecoder(resource.getInputStream());
//            image = decoderFile.decodeAsBufferedImage();
            image = ImageIO.read(resource.getInputStream());
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            IOUtils.closeQuietly(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
