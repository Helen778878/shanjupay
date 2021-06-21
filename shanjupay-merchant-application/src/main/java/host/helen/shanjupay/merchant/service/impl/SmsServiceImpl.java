package host.helen.shanjupay.merchant.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.common.domain.CommonErrorCode;
import host.helen.shanjupay.merchant.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname SmsServiceImpl
 * @Description SmsServiceImpl
 * @Date 2021/6/15 7:49 PM
 * @Created by helen
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${shanjupay.sms.url}")
    String smsServerUrl;

    @Value("${shanjupay.sms.effectiveTime}")
    String effectiveTime;

    @Override
    public String sendSmsCode(String mobile) {
        String payload = "{\"mobile\":\"" + mobile + "\"}";
        return HttpUtil.post(smsServerUrl + "/generate?name=sms&effectiveTime=" + effectiveTime, payload);
    }

    @Override
    public void checkSmsCode(String verifyCode, String verifyKey) {
        Map<String, Object> uriVariables = new HashMap<>();

        uriVariables.put("name","sms");
        uriVariables.put("verificationKey",verifyKey);
        uriVariables.put("verificationCode",verifyCode);

        String post = HttpUtil.post(smsServerUrl + "/verify",uriVariables);

        JSONObject jsonObject = JSON.parseObject(post);
        boolean result = false;
        try {
            result = jsonObject.getBoolean("result");
        } catch (Exception e) {
            throw e;
        }
        if (!result) {
            throw new BusinessException(CommonErrorCode.E_100102);
        }

    }


}
