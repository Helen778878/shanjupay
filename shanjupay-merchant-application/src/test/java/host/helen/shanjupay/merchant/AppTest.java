package host.helen.shanjupay.merchant;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AppTest
 * @Description AppTest
 * @Date 2021/6/15 5:22 PM
 * @Created by helen
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {


    @Autowired
    private RestTemplate restTemplate;


    @Value("${shanjupay.sms.url}")
    String smsServerUrl;

    @Value("${shanjupay.sms.effectiveTime}")
    String effectiveTime;


    @Test
    public void m1(){
        Map<String, Object> uriVariables = new HashMap<>();

        uriVariables.put("name","sms");
        uriVariables.put("effectiveTime","300");
        String payload = "{\"mobile\":\"15611112222\"}";
        uriVariables.put("payload",payload);
        String s = restTemplate.postForObject("http://127.0.0.1:56085/sailing/generate", uriVariables, String.class);
        System.out.println(s);
    }

    @Test
    public void m2(){
        Map<String, Object> uriVariables = new HashMap<>();

        uriVariables.put("name","sms");
        uriVariables.put("verificationKey","300");
        uriVariables.put("verificationCode","300");

        String post = HttpUtil.post(smsServerUrl + "/verify",uriVariables);

        JSONObject jsonObject = JSON.parseObject(post);
        System.out.println(jsonObject.getBoolean("result"));
    }
}
