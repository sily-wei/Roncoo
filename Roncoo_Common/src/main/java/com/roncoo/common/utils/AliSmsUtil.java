package com.roncoo.common.utils;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.roncoo.common.dto.PhoneCodeDto;

/**
 * @author weining
 * @date 2019/12/10 17:27
 */
public class AliSmsUtil {

    /**
     * 发送短信验证码 有效期10分钟
     * @param phoneCodeDto 手机号 验证码 6位*/
    public static boolean sendSmsCode(PhoneCodeDto phoneCodeDto){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou","LTAIhTvqTSmlmjeQ", "X7X9w0Ck5GEIWgP9tl0Q6sgmFjQuMv");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneCodeDto.getPhone());
        request.putQueryParameter("SignName", "来自邢朋辉的短信");
        request.putQueryParameter("TemplateCode", "SMS_115250125");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+phoneCodeDto.getCode()+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendCode(PhoneCodeDto phoneCodeDto) {
    }
}
