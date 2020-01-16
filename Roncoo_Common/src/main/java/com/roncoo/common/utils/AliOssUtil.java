package com.roncoo.common.utils;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 基于阿里云的OSS 资源的管理：上传、下载、删除、列表等操作
 * @author weining
 * @date 2019/12/12 19:41
 */
public class AliOssUtil {
    /**
     * 设置
     */
    private static String endPoint = "http://oss-cn-beijing.aliyuncs.com";

    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建
     * 并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com
     * 创建RAM账号。
     * 最后一个是模板名字
     */
    private static String accessKeyId = "LTAIhTvqTSmlmjeQ";
    private static String accessKeySecret = "X7X9w0Ck5GEIWgP9tl0Q6sgmFjQuMv";
    private static String bucketName = "qfjava1908";

    private static OSS client;
    static {
        client = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
    }

    /**
     * 上传文件到OSS
     * @param objName 文件名字
     * @param arr   文件内容
     * @return 返回访问路径
     */
    public static String upload(String objName, byte[] arr){
        //1.上传内容
        client.putObject(bucketName,objName,new ByteArrayInputStream(arr));
        //2.获取访问路径
        return client.generatePresignedUrl(bucketName,objName, DateUtil.addYear(3)).toString();
    }

    /**
     * 上传文件到OSS
     * @param objName 文件名字
     * @param arr   文件内容
     * @param date 上传时间
     * @return 返回访问路径
     */
    public static String upload(String objName, byte[] arr, Date date){
        //1.上传内容
        client.putObject(bucketName,objName,new ByteArrayInputStream(arr));
        //2.获取访问路径
        return client.generatePresignedUrl(bucketName,objName, date).toString();
    }

    public static String read(String objName){
        /**
         * 调用getObject的方法，返回一个OSSObject对象
         */
        OSSObject object = client.getObject(bucketName, objName);
        /**
         * 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
         */
        InputStream content = object.getObjectContent();
        if (content!=null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            StringBuffer buffer = new StringBuffer();
            String line;
            try{
                while ((line = reader.readLine())!=null){
                    buffer.append(line);
                }
                content.close();
            }catch (Exception  e){

            }
            /**
             * 数据读取完毕后需要关流
             */
            return buffer.toString();
        }
        return null;
    }

    /**
     * 获取文件的列表
     * @return 返回所有文件列表
     */
    public static List<String> list(){
        List<String> strings = new ArrayList<>();
        ObjectListing objectListing = client.listObjects(bucketName);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            strings.add(objectSummary.getKey());
        }
        return strings;
    }

    public static boolean del(String oriname){
        try {
            client.deleteObject(bucketName, oriname);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
