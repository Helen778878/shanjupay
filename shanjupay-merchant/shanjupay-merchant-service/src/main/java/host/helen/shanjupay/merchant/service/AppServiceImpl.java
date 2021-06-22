package host.helen.shanjupay.merchant.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.common.domain.CommonErrorCode;
import host.helen.shanjupay.merchant.api.AppService;
import host.helen.shanjupay.merchant.api.dto.AppDTO;
import host.helen.shanjupay.merchant.convert.AppCovert;
import host.helen.shanjupay.merchant.entity.App;
import host.helen.shanjupay.merchant.entity.Merchant;
import host.helen.shanjupay.merchant.mapper.AppMapper;
import host.helen.shanjupay.merchant.mapper.MerchantMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @Classname AppServiceImpl
 * @Description AppServiceImpl
 * @Date 2021/6/22 9:50 AM
 * @Created by helen
 */
@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;

    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException {

        if (merchantId == null || appDTO == null || StrUtil.isBlank(appDTO.getAppName())) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }

        Merchant merchant = merchantMapper.selectById(merchantId);

        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }

        if (!"2".equals(merchant.getAuditStatus())) {
            throw new BusinessException(CommonErrorCode.E_200003);
        }


        if (isAppNameExist(appDTO.getAppName())) {
            throw new BusinessException(CommonErrorCode.E_200004);
        }

        App app = AppCovert.INSTANCE.dto2entity(appDTO);
        String appId = RandomUtil.randomString(32);

        app.setAppId(appId);
        app.setMerchantId(merchantId);

        appMapper.insert(app);

        return AppCovert.INSTANCE.entity2dto(app);
    }



    private boolean isAppNameExist(String appName) {
        Integer integer = appMapper.selectCount(new QueryWrapper<App>().lambda().eq(App::getAppName, appName));
        return integer > 0;
    }
}
