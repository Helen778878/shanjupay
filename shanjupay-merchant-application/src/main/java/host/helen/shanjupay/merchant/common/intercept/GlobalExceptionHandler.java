package host.helen.shanjupay.merchant.common.intercept;

import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.common.domain.CommonErrorCode;
import host.helen.shanjupay.common.domain.ErrorCode;
import host.helen.shanjupay.common.domain.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname GlobalExceptionHandler
 * @Description GlobalExceptionHandler
 * @Date 2021/6/17 2:46 PM
 * @Created by helen
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse processException(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Exception e){

        // 如果是业务异常
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            ErrorCode errorCode = businessException.getErrorCode();
            int code = errorCode.getCode();
            String desc = errorCode.getDesc();
            return new RestErrorResponse(String.valueOf(code),desc);
        }

        log.error(request.getRequestURI() + e.getMessage());
        return new RestErrorResponse(CommonErrorCode.UNKNOWN);
    }
}
