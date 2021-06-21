package host.helen.shanjupay.merchant.service;

/**
 * @Classname SmsService
 * @Description 发送验证码
 * @Date 2021/6/15 7:48 PM
 * @Created by helen
 */
public interface SmsService {

    /**
     * 手机号
     * @param mobile
     * @return
     */
    String sendSmsCode(String mobile);

    void checkSmsCode(String verifyCode,String verifyKey);
}
