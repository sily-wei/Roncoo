package com.roncoo.api.payment.service;

import com.roncoo.common.dto.PayOrderDto;
import com.roncoo.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author weining
 * @date 2020/1/16 21:15
 */
@FeignClient("roncoo-payment")
public interface PaymentService {
    //查询
    @GetMapping("/roncoo/payment/querypay.do")
    public R queryPay(String oid);

    //查询付款类型
    @GetMapping("/roncoo/payment/querypaytype.do")
    public R queryPayType(String oid,int type);

    //查询全部订单
    @GetMapping("/roncoo/payment/queryall.do")
    public R queryPayAll();

    //发起支付
    @PostMapping("/roncoo/payment/sendPay.do")
    public R sendPay(@RequestBody PayOrderDto payOrderDto);

    //取消订单
    @PostMapping("/roncoo/payment/cancelPay.do")
    public R cancelPay(String oid);
}
