package host.helen.shanjupay.common.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 **/
@ApiModel(value = "RestErrorResponse", description = "错误响应参数包装")
@Data
public class RestErrorResponse {

    private String errCode;

    private String errMessage;

    public RestErrorResponse(String errCode,String errMessage){
        this.errCode = errCode;
        this.errMessage= errMessage;
    }

    public RestErrorResponse(CommonErrorCode c) {
        this.errCode = String.valueOf(c.getCode());
        this.errMessage = c.getDesc();
    }


}
