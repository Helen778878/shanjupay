package host.helen.shanjupay.merchant.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import host.helen.shanjupay.common.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SecurityUtil {

	//测试使用
	public static Long getMerchantId() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
				.getRequest();
		String jsonToken = request.getHeader("authorization");
		if (StringUtils.isEmpty(jsonToken) || !jsonToken.startsWith("Bearer ")) {
			throw new RuntimeException("token is not as expected");
		}
		jsonToken = jsonToken.substring(7);
		jsonToken = EncryptUtil.decodeUTF8StringBase64(jsonToken);
		JSONObject jsonObject = JSON.parseObject(jsonToken);
		return jsonObject.getLong("merchantId");
	}

	public static LoginUser getUser() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		if (servletRequestAttributes != null) {
			HttpServletRequest request = servletRequestAttributes.getRequest();

			Object jwt = request.getAttribute("jsonToken");
			if (jwt instanceof LoginUser) {
				return (LoginUser) jwt;
			}
		}
		return new LoginUser();
	}

//	public static Long getMerchantId(){
//		MerchantService merchantService = ApplicationContextHelper.getBean(MerchantService.class);
//		MerchantDTO merchant = merchantService.queryMerchantByTenantId(getUser().getTenantId());
//		Long merchantId = null;
//		if(merchant!=null){
//			merchantId = merchant.getId();
//		}
//		return merchantId;
//	}

	/**
	 * 转换明文jsonToken为用户对象
	 * @param token
	 * @return
	 */
	public static LoginUser convertTokenToLoginUser(String token) {
		token = EncryptUtil.decodeUTF8StringBase64(token);
		LoginUser user = new LoginUser();
		JSONObject jsonObject = JSON.parseObject(token);
		String payload = jsonObject.getString("payload");
		Map<String, Object> payloadMap = JSON.parseObject(payload, Map.class);
		user.setPayload(payloadMap);
		user.setClientId(jsonObject.getString("client_id"));
		user.setMobile(jsonObject.getString("mobile"));
		user.setUsername(jsonObject.getString("user_name"));
		return user;
	}

}
