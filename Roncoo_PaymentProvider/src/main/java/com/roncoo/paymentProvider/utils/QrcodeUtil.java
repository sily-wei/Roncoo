package com.roncoo.paymentProvider.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于谷歌的zxing二维码生成
 * @author weining
 * @date 2020/1/15 21:01
 */
public class QrcodeUtil {
    /**
     * 生成二维码，并将二维码写入到指定文件中
     * */
    public  static Boolean encode(String contents, String format, int width, String saveImgFilePath) {
        Boolean bool = false;
        BufferedImage image = createImage(contents,width,width);
        if (image != null) {
            bool = writeToFile(image, format, saveImgFilePath);
        }
        return bool;
    }

    /**
     * 创建BufferedImage
     * @param contents 二维码的内容
     * @param width 二维码图片的宽度
     * @param height 二维码图片的高度
     * @return BufferedImage
     * */
    public static BufferedImage createImage(String contents ,int width, int
            height) {
        //创建缓存图片对象
        BufferedImage bufImg=null;
        //存储图片相关信息
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        //设置图片相关信息 比如 分辨率 边距 编码格式等
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 3);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            //创建位图矩阵 参数说明：1、二维码 内容2、要生成的格式 3、宽 4、高 5、相关信息
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.QR_CODE, width, height, hints);
            //设置对应的颜色
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001,
                    0xFFFFFFFF);
            //生成图片
            bufImg = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImg;
    }
    /**
     * 创建BufferedImage
     * @param contents 二维码的内容
     * @param imgWidth 二维码图片的宽度 正方形
     * @return BufferedImage
     * */
    public static BufferedImage createImage(String contents , int imgWidth) {
        return createImage(contents, imgWidth,imgWidth);
    }
    /**
     * 将BufferedImage对象写入到文件中
     *  [png,jpg,bmp]
     */
    @SuppressWarnings("finally")
    public static Boolean writeToFile(BufferedImage bufImg, String format, String
            saveImgFilePath) {
        Boolean bool = false;
        try {
            bool = ImageIO.write(bufImg, format, new File(saveImgFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }

}
