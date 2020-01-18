package com.roncoo.paymentProvider.bean;

import lombok.Data;

/**
 * @author weining
 * @date 2020/1/15 20:42
 */
@Data
public class AlipayRefoundBean {
    private String out_trade_no; //订单号64

    private double refund_amount; //退款金额

    private String out_request_no; //标识一次退款进度 同一笔交易多次退款需要保证唯一

    public AlipayRefoundBean(String out_trade_no, double refund_amount) {
        this.out_trade_no = out_trade_no;
        this.refund_amount = refund_amount;
    }

    public AlipayRefoundBean() {
    }
}
