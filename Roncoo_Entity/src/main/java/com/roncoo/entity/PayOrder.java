package com.roncoo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author weining
 * @date 2020/1/16 11:22
 */
@Data
public class PayOrder {
    private Integer id;
    private String oid;
    private String title;
    private String preurl;
    private int money;
    private int type;
    private int flag;
    private Date stime;
    private Date modtime;

    public PayOrder() {
    }

    public PayOrder(String oid, String title, String preurl, int money, int type) {
        this.oid = oid;
        this.title = title;
        this.preurl = preurl;
        this.money = money;
        this.type = type;
    }
}
