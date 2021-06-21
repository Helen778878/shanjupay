package host.helen.shanjupay.merchant.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.common.domain.CommonErrorCode;
import host.helen.shanjupay.common.domain.RestResponse;
import host.helen.shanjupay.common.util.PhoneUtil;
import host.helen.shanjupay.merchant.api.MerchantService;
import host.helen.shanjupay.merchant.api.dto.MerchantDTO;
import host.helen.shanjupay.merchant.common.util.SecurityUtil;
import host.helen.shanjupay.merchant.convert.MerchantDetailConvert;
import host.helen.shanjupay.merchant.convert.MerchantRegisterConvert;
import host.helen.shanjupay.merchant.service.FileService;
import host.helen.shanjupay.merchant.service.SmsService;
import host.helen.shanjupay.merchant.vo.MerchantDetailVO;
import host.helen.shanjupay.merchant.vo.MerchantRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Classname MerchantController
 * @Description MerchantController
 * @Date 2021/6/14 5:21 PM
 * @Created by helen
 */
@RestController
@Api(value="商户平台应用接口",tags = "商户平台应用接口",description = "商户平台应用接口")
public class MerchantController {

    @Reference
    MerchantService merchantService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private FileService fileService;

    @ApiOperation(value="根据id查询商户信息")
    @GetMapping("/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id){
        return merchantService.queryMerchantById(id);
    }


    @ApiOperation(value="获取手机号验证码")
    @GetMapping("/sms")
    public String getSMS(@RequestParam("mobile")String mobile){
        return smsService.sendSmsCode(mobile);
    }

    @ApiOperation(value="商户注册")
    @ApiImplicitParam(value = "商户注册信息",name = "merchantRegisterVO",required = true,dataType = "MerchantRegisterVO",paramType = "body")
    @PostMapping("/merchants/register")
    public String m1(@RequestBody MerchantRegisterVO merchantRegisterVO){

        if (!StrUtil.isAllNotBlank( merchantRegisterVO.getMobile(),
                                    merchantRegisterVO.getPassword(),
                                    merchantRegisterVO.getUsername(),
                                    merchantRegisterVO.getVerifyCode(),
                                    merchantRegisterVO.getVerifyKey())) {
          throw new BusinessException(CommonErrorCode.E_110006);
        }

        if (!PhoneUtil.isMatches(merchantRegisterVO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100109);
        }


        smsService.checkSmsCode(merchantRegisterVO.getVerifyCode(),merchantRegisterVO.getVerifyKey());

        MerchantDTO merchantDTO = MerchantRegisterConvert.INSTANCE.vo2dto(merchantRegisterVO);
        MerchantDTO merchantDTO1 = merchantService.merchantRegistration(merchantDTO.getId(),merchantDTO);
        return JSON.toJSONString(merchantDTO1);
    }


    @ApiOperation("资质申请")
    @PostMapping("/my/merchants/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantInfo", value = "商户认证资料", required = true, dataType = "MerchantDetailVO", paramType = "body")
    })
    public void saveMerchant(@RequestBody MerchantDetailVO merchantDetailVO){

        Long merchantId = SecurityUtil.getMerchantId();

        MerchantDTO merchantDTO = MerchantDetailConvert.INSTANCE.vo2dto(merchantDetailVO);
        merchantDTO.setId(merchantId);
        merchantService.applyMerchant(merchantId,merchantDTO);
    }

    @ApiOperation(value="上传")
    @PostMapping("/merchants/upload")
    public RestResponse upload(MultipartFile multipartFile) throws IOException {
        String upload = fileService.upload(multipartFile.getBytes(), multipartFile.getOriginalFilename());
        return RestResponse.success(upload);
    }




}
