package com.roncoo.common.dto;

import lombok.Data;

/**
 * @author weining
 * @date 2020/1/16 13:48
 */
@Data
public class PayOrderDto {
    private String oid;
    private String title;
    private String preurl;
    private int money;
    private int type;
}
