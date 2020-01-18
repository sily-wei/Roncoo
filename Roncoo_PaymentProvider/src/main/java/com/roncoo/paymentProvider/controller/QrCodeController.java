package com.roncoo.paymentProvider.controller;


import com.roncoo.paymentProvider.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author weining
 * @date 2020/1/16 13:37
 */
@RestController
public class QrCodeController {
    @Autowired
    private QrCodeService qrCodeService;

    //生成二维码 png
    @GetMapping("/roncoo/payment/qrcode/showqrcode.png")
    public void createQrCode(String msg, HttpServletResponse response){
        qrCodeService.createQrCode(msg,response);
    }

    //生成二维码 url
    @GetMapping("/roncoo/payment/qrcode/showqrcodeurl.png")
    public void createQrCodeUrl(String msg, HttpServletResponse response){
        qrCodeService.createQrCodeUrl(msg,response);
    }
}
