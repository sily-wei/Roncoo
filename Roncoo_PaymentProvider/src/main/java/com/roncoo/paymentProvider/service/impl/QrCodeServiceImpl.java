package com.roncoo.paymentProvider.service.impl;


import com.roncoo.paymentProvider.service.QrCodeService;
import com.roncoo.paymentProvider.utils.QrcodeUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;

/**
 * @author weining
 * @date 2020/1/16 13:39
 */
@Service
public class QrCodeServiceImpl implements QrCodeService {
    private static int width = 400;

    @Override
    public void createQrCode(String msg, HttpServletResponse response) {
        BufferedImage image = QrcodeUtil.createImage(msg, width);
        if (image!=null){
            try {
                ImageIO.write(image,"jpeg",response.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void createQrCodeUrl(String msg, HttpServletResponse response) {
        //1.解码
        String s = new String(Base64.getDecoder().decode(msg));
        //2.生成缓存照片
        BufferedImage image = QrcodeUtil.createImage(s, width);
        if (image!=null){
            try {
                ImageIO.write(image,"jpeg",response.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
