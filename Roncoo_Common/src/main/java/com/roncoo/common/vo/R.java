package com.roncoo.common.vo;


import com.roncoo.common.result.ResultCode;
import lombok.Data;

/**
 * @author weining
 * @date 2019/12/10 17:33
 */
@Data
public class R {
    private int code;
    private String msg;
    private Object data;

    public static R setResult(boolean issucc,String data){
        if(issucc){
            return R.Ok(data);
        }else {
            return R.fail(data);
        }
    }

    public static R setR(int code,String msg,Object data){
        R r = new R();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static R Ok(Object data){
        return setR(ResultCode.OK.getVal(),"OK",data);
    }
    public static R Ok(){
        return setR(ResultCode.OK.getVal(),"OK",null);
    }

    public static R fail(Object data){
        return setR(ResultCode.ERROR.getVal(),"Error",data);
    }
    public static R fail(){
        return setR(ResultCode.ERROR.getVal(),"OK",null);
    }
}
