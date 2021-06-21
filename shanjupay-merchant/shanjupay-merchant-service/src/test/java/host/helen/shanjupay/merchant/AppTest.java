package host.helen.shanjupay.merchant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import host.helen.shanjupay.common.util.EncryptUtil;
import host.helen.shanjupay.merchant.api.MerchantService;
import host.helen.shanjupay.merchant.api.dto.MerchantDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname AppTest
 * @Description AppTest
 * @Date 2021/6/19 2:29 PM
 * @Created by helen
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    MerchantService merchantService;

    //生成token，指定商户id
    @Test
    public void createTestToken() {
        Long merchantId = 1405063520548032513L;//填写用于测试的商户id
        MerchantDTO merchantDTO = merchantService.queryMerchantById(merchantId);
        JSONObject token = new JSONObject();
        token.put("mobile", merchantDTO.getMobile());
        token.put("user_name", merchantDTO.getUsername());
        token.put("merchantId", merchantId);

        String jwt_token = "Bearer " + EncryptUtil.encodeBase64(JSON.toJSONString(token).getBytes());
        System.out.println(jwt_token);
        // Bearer eyJtZXJjaGFudElkIjoxNDA1MDYzNTIwNTQ4MDMyNTEzLCJtb2JpbGUiOiIxNTYyMjMxNDQ1OSJ9
    }
}
