package host.helen.shanjupay.merchant.api;

import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.merchant.api.dto.AppDTO;

/**
 * @Classname AppService
 * @Description AppService
 * @Date 2021/6/22 9:44 AM
 * @Created by helen
 */
public interface AppService {

    /**
     * 创建应用
     * @param merchantId 商户id
     * @param appDTO 应用信息
     * @return 创建成功的应用信息
     * @throws BusinessException
     */
    AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException;
}
