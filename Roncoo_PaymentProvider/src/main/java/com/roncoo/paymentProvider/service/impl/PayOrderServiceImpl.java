package com.roncoo.paymentProvider.service.impl;


import com.roncoo.common.dto.PayOrderDto;
import com.roncoo.common.vo.R;
import com.roncoo.entity.PayOrder;
import com.roncoo.paymentProvider.bean.AlipayBean;
import com.roncoo.paymentProvider.dao.PayOrderDao;
import com.roncoo.paymentProvider.service.PayOrderService;
import com.roncoo.paymentProvider.utils.AlipayUtil;
import com.roncoo.paymentProvider.utils.WeChatPayCommonUtil;
import com.roncoo.paymentProvider.utils.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

/**
 * @author weining
 * @date 2020/1/16 13:38
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {
    @Autowired
    private PayOrderDao payOrderDao;

    @Value("${pay.qrcodepre}")
    private String preUrl;

    @Override
    public R queryPay(String oid) {
        PayOrder payOrder = payOrderDao.selectByOid(oid);
        if (payOrder != null) {
            return queryPayType(oid, payOrder.getType());
        } else {
            return R.fail("订单不存在");
        }
    }

    @Override
    public R queryPayType(String oid, int type) {
        if (type == 1) {
            return queryAliPay(oid);
        } else if (type == 2) {
            return queryWxPay(oid);
        } else {
            return R.fail("支付类型错误");
        }
    }

    @Override
    public R queryAll() {
        return R.Ok(payOrderDao.selectAll());
    }

    @Override
    public R sendPay(PayOrderDto payOrderDto) {
        if (payOrderDto.getType() == 1) {
            return createAliPay(payOrderDto);
        } else if (payOrderDto.getType() == 2) {
            return createWxPay(payOrderDto);
        } else {
            return R.fail("支付类型错误");
        }
    }

    @Override
    public R cancelPay(String oid) {
        PayOrder payOrder = payOrderDao.selectByOid(oid);
        if (payOrder != null) {
            if (payOrder.getFlag() == 1) {
                if (payOrder.getType() == 1) {
                    return cancelAliPay(oid);
                } else {
                    return cancelWxPay(oid);
                }
            } else {
                return R.fail("该订单不可取消");
            }
        }
        return R.fail("您所查询的订单不存在");
    }

    @Override
    public R createAliPay(PayOrderDto dto) {
        //1.发起请求 获取预处理链接
        AlipayBean aliPayDto = new AlipayBean();
        aliPayDto.setOut_trade_no(dto.getOid());
        aliPayDto.setSubject(dto.getTitle());
        aliPayDto.setTotal_amount(dto.getMoney() / 100.0);
        String aliPay = AlipayUtil.createAliPay(aliPayDto);
        if (aliPay != null) {
            String url = preUrl + "/pay/qrcode/showqrcodeurl.png?msg=" + Base64.getUrlEncoder().encodeToString(aliPay.getBytes());
            System.out.println(url);
            PayOrder payOrder = new PayOrder(dto.getOid(), dto.getTitle(), url, dto.getMoney(), 1);
            payOrderDao.insert(payOrder);
            return R.Ok("支付宝支付成功", url);
        }
        return R.fail("支付宝支付失败");
    }

    @Override
    public R createWxPay(PayOrderDto dto) {
        try {
            String qrcode = WeChatPayCommonUtil.weixin_pay(dto.getMoney() + "", dto.getTitle(), dto.getOid());
            if (qrcode != null) {
                String url = preUrl + "/pay/qrcode/showqrcodeurl.png?msg=" + Base64.getUrlEncoder().encodeToString(qrcode.getBytes());
                PayOrder payOrder = new PayOrder(dto.getOid(), dto.getTitle(), url, dto.getMoney(), 2);
                payOrderDao.insert(payOrder);
                return R.Ok("微信支付成功", url);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return R.fail("微信支付失败");
    }

    @Override
    public R queryAliPay(String oid) {
        String s = AlipayUtil.queryAlipay(oid);
        if (s != null) {
            String r = "";
            int f = 1;
            switch (s) {
                case "WAIT_BUYER_PAY": {
                    r = "订单未支付";
                }
                break;
                case "TRADE_SUCCESS": {
                    r = "订单支付成功";
                    f = 2;
                }
                break;
                case "TRADE_FINISHED": {
                    r = "订单交易结束";
                    f = 4;
                }
                break;
                case "TRADE_CLOSED": {
                    r = "订单失败";
                    f = 3;
                }
                break;
                default:
                    break;
            }
            PayOrder payOrder = payOrderDao.selectByOid(oid);
            if (payOrder.getFlag() != f) {
                payOrderDao.update(oid, f);
            }
            return R.Ok(r);
        }
        return R.fail("支付宝账单查询失败");
    }

    @Override
    public R queryWxPay(String oid) {
        try {
            Map map = XmlUtil.doXMLParse(WeChatPayCommonUtil.weixin_query(oid));
            if (map.containsKey("trade_state")) {
                String state = (String) map.get("trade_state");
                String r = "";
                int f = 1;
                switch (state) {
                    case "SUCCESS": {
                        r = "订单支付成功";
                        f = 2;
                    }
                    break;
                    case "REFUND": {
                        r = "订单退款";
                        f = 5;
                    }
                    break;
                    case "NOTPAY": {
                        r = "订单未支付";
                        f = 1;
                    }
                    break;
                    case "CLOSED": {
                        r = "订单关闭";
                        f = 4;
                    }
                    break;
                    default: {
                        r = "支付失败";
                        f = 3;
                    }
                    break;
                }
                PayOrder payOrder = payOrderDao.selectByOid(oid);
                if (payOrder.getFlag() != f) {
                    payOrderDao.update(oid, f);
                }
                return R.Ok(r);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return R.fail("微信查询异常");
    }

    @Override
    public R cancelAliPay(String oid) {
        if (AlipayUtil.cancelPay(oid).equals(10000)) {
            payOrderDao.update(oid, 4);
            return R.Ok();
        } else {
            return R.fail();
        }
    }

    @Override
    public R cancelWxPay(String oid) {
        try {
            //1.发起微信的订单取消 并解析结果
            Map map = XmlUtil.doXMLParse(WeChatPayCommonUtil.weixin_cancel(oid));
            //2.检验时是否包含指定的key
            if(map.containsKey("result_code")){
                if(map.get("result_code").equals("SUCCESS")){
                    //4.操作数据库
                    payOrderDao.update(oid,4);
                    //5、返回结果
                    return R.Ok();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return R.fail();
    }
}
