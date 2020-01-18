package com.roncoo.api.payment.controller;

import com.roncoo.api.payment.service.QrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author weining
 * @date 2020/1/18 18:41
 */
@RestController
@Api(value = "二维码的生成",tags = "二维码的生成")
public class QrCodeController {
    @Autowired
    private QrCodeService qrCodeService;

    @ApiOperation(value = "生成二维码 png",notes = "生成二维码 png")
    @GetMapping("/roncoo/api/payment/qrcode/showqrcode.png")
    public void createQrCode(String msg, HttpServletResponse response) {
        qrCodeService.createQrCode(msg,response);
    }

    @ApiOperation(value = "生成二维码 url",notes = "生成二维码 url")
    @GetMapping("/roncoo/api/payment/qrcode/showqrcodeurl.png")
    public void createQrCodeUrl(String msg, HttpServletResponse response) {
        qrCodeService.createQrCodeUrl(msg,response);
    }
}
