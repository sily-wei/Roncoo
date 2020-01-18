package com.roncoo.paymentProvider.utils;

import com.alipay.api.internal.util.file.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 *@Author feri
 *@Date Created in 2018/8/9 23:10
 */
public class HttpUtil {
   private static final int  CONNECT_TIMEOUT = 5000; // in milliseconds
    private final static String DEFAULT_ENCODING = "UTF-8";
    public static String postData(String urlStr, String data){
        return postData(urlStr, data, null);
    }
    public static String postData(String urlStr, String data, String contentType){
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            if(contentType != null){
                conn.setRequestProperty("content-type",contentType);
            }
            OutputStreamWriter writer = new
                    OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if(data == null) {
                data = "";
            }
            writer.write(data);
            writer.flush();
            writer.close();
            reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println("Error connecting to " + urlStr + ": " +
                    e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }
    /**
     * 网络请求
     * @param url 要请求的资源地址
     * @return  获取的结果 字符串*/
    public static String getJson(String url){
        String json=null;
        try {
            //1、创建资源对象
            URL urlObj = new URL(url);
            //2、创建连接对象
            HttpURLConnection huc = (HttpURLConnection) urlObj.openConnection();
            //3、设置请求信息
            huc.setRequestMethod("GET");
            //4、发起请求
            huc.connect();
            //5、验证响应状态码
            if (huc.getResponseCode() == 200) {
                //6、获取响应结果
                 json = new String(IOUtils.toByteArray(huc.getInputStream()));
            }
            //7、关闭
            huc.disconnect();
        }catch (Exception e){
        }
        return json;
    }

}
