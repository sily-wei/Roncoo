package com.roncoo.paymentProvider.service;


import com.roncoo.common.dto.PayOrderDto;
import com.roncoo.common.vo.R;

/**
 * @author weining
 * @date 2020/1/16 13:38
 */
public interface PayOrderService {
    R queryPay(String oid);

    R queryPayType(String oid, int type);

    R queryAll();


    R sendPay(PayOrderDto payOrderDto);

    R cancelPay(String oid);

    //支付支付
    R createAliPay(PayOrderDto dto);

    //微信支付
    R createWxPay(PayOrderDto dto);

    //查询
    R queryAliPay(String oid);

    R queryWxPay(String oid);

    //取消
    R cancelAliPay(String oid);

    R cancelWxPay(String oid);
}
