package host.helen.shanjupay.merchant.api;

import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.merchant.api.dto.MerchantDTO;

/**
 * @Classname MerchantService
 * @Description MerchantService
 * @Date 2021/6/14 3:27 PM
 * @Created by helen
 */
public interface MerchantService {

    //根据 id查询商户
    MerchantDTO queryMerchantById(Long id);

    MerchantDTO merchantRegistration(Long merchantId,MerchantDTO merchantDTO);

    /**
     * 资质申请接口
     * @param merchantId 商户id
     * @param merchantDTO 资质申请的信息
     * @throws BusinessException
     */
    void applyMerchant(Long merchantId,MerchantDTO merchantDTO) throws BusinessException;
}
