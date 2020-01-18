package com.roncoo.paymentProvider.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author weining
 * @date 2020/1/16 13:38
 */
public interface QrCodeService {
    void createQrCode(String msg, HttpServletResponse response);

    void createQrCodeUrl(String msg, HttpServletResponse response);
}
