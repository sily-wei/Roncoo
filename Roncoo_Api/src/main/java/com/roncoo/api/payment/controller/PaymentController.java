package com.roncoo.api.payment.controller;

import com.roncoo.api.payment.service.PaymentService;
import com.roncoo.common.dto.PayOrderDto;
import com.roncoo.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weining
 * @date 2020/1/16 20:15
 */
@RestController
@Api(value = "支付服务的管理",tags = "支付服务的管理")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    //查询
    @ApiOperation(value = "订单的查询",notes = "订单的查询")
    @GetMapping("/roncoo/api/payment/querypay.do")
    public R queryPay(String oid){
        return paymentService.queryPay(oid);
    }

    //查询付款类型
    @ApiOperation(value = "根据付款类型查询订单",notes = "根据付款类型查询订单")
    @GetMapping("/roncoo/api/payment/querypaytype.do")
    public R queryPayType(String oid,int type){
        return paymentService.queryPayType(oid,type);
    }
    //查询全部订单
    @ApiOperation(value = "查询全部订单",notes = "查询全部订单")
    @GetMapping("/roncoo/api/payment/queryall.do")
    public R queryPayAll(){
        return paymentService.queryPayAll();
    }

    //发起支付
    @ApiOperation(value = "发起支付",notes = "发起支付")
    @PostMapping("/roncoo/api/payment/sendPay.do")
    public R sendPay(@RequestBody PayOrderDto payOrderDto){
        return paymentService.sendPay(payOrderDto);
    }

    //取消订单
    @ApiOperation(value = "取消订单",notes = "取消订单")
    @PostMapping("/roncoo/api/payment/cancelPay.do")
    public R cancelPay(String oid){
        return paymentService.cancelPay(oid);
    }
}
