package com.roncoo.paymentProvider.controller;


import com.roncoo.common.dto.PayOrderDto;
import com.roncoo.common.vo.R;
import com.roncoo.paymentProvider.service.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weining
 * @date 2020/1/16 13:37
 */
@RestController
public class PayOrderController {
    @Autowired
    private PayOrderService payOrderService;

    //查询
    @GetMapping("/roncoo/payment/querypay.do")
    public R queryPay(String oid){
        return payOrderService.queryPay(oid);
    }

    //查询付款类型
    @GetMapping("/roncoo/payment/querypaytype.do")
    public R queryPayType(String oid,int type){
        return payOrderService.queryPayType(oid,type);
    }

    //查询全部订单
    @GetMapping("/roncoo/payment/queryall.do")
    public R queryPayAll(){
        return payOrderService.queryAll();
    }

    //发起支付
    @PostMapping("/roncoo/payment/sendPay.do")
    public R sendPay(@RequestBody PayOrderDto payOrderDto){
        return payOrderService.sendPay(payOrderDto);
    }

    //取消订单
    @PostMapping("/roncoo/payment/cancelPay.do")
    public R cancelPay(String oid){
        return payOrderService.cancelPay(oid);
    }
}
