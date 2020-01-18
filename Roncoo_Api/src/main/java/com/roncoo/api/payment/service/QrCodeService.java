package com.roncoo.api.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author weining
 * @date 2020/1/18 18:40
 */
@FeignClient("roncoo-payment")
public interface QrCodeService {

    //生成二维码 png
    @GetMapping("/roncoo/payment/qrcode/showqrcode.png")
    public void createQrCode(String msg, HttpServletResponse response);

    //生成二维码 url
    @GetMapping("/roncoo/payment/qrcode/showqrcodeurl.png")
    public void createQrCodeUrl(String msg, HttpServletResponse response);
}
