package com.roncoo.common.result;

/**
 * @author weining
 * @date 2019/12/10 17:27
 */
public enum ResultCode {
    //
    OK(200), ERROR(400), NOTFOUNT(404);

    private int val;

    ResultCode(int v) {
        val = v;
    }
    public int getVal(){
        return val;
    }
}
