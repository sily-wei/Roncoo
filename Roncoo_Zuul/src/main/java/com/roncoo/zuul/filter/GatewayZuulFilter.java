package com.roncoo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.roncoo.common.vo.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author weining
 * @date 2020/1/16 20:09
 */
public class GatewayZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的顺序 越小 表示越靠前
     * @return 返回int类型
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否启用该过滤器
     * @return  返回布尔
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤处理 实现对请求的管理
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean isempty = false;

        outer: //循环的标签可以指定结束的循环
        for (String k:parameterMap.keySet()){
            String[] arr= parameterMap.get(k);

            for (String a:arr){
                if (a!=null&&a.length()>0){
                    continue ;
                }else{
                    isempty = true;
                    break outer;
                }
            }
        }
        if (isempty){
            requestContext.setResponseStatusCode(400);
            requestContext.setSendZuulResponse(false);
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(R.Ok("请求参数为空，请重新传递"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
