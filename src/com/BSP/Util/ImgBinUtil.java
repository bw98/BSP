package com.BSP.Util;


import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ImgBinUtil {

    //将base64编制的二进制转化成图片并存储
    public static void base64StringToImage(String base64String, String bookId, String path) {
        try {
            byte[] bytes = Base64.decodeBase64(base64String);
            ByteArrayInputStream b = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(b);
            File f = new File(path);// 可以是jpg,png,gif格式
            ImageIO.write(bi, "jpg", f);// 不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
