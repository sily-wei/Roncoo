package com.roncoo.paymentProvider.bean;

import lombok.Data;

/**
 * @author weining
 * @date 2020/1/15 19:52
 */
@Data
public class AlipayBean {
    public String out_trade_no; //商户订单号,
    public double total_amount;//订单总金额 单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    public String subject; // 订单标题

    public String body; //对订单的描述
    public String qr_code; //预下单请求生成的二维码码串


    public AlipayBean(String out_trade_no, double total_amount, String subject, String body, String qr_code) {
        this.out_trade_no = out_trade_no;
        this.total_amount = total_amount;
        this.subject = subject;
        this.body = body;
        this.qr_code = qr_code;
    }

    public AlipayBean(String out_trade_no, double total_amount, String subject) {
        this.out_trade_no = out_trade_no;
        this.total_amount = total_amount;
        this.subject = subject;
    }

    public AlipayBean() {
    }
}
