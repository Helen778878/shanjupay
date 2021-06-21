package host.helen.shanjupay.merchant.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.common.domain.CommonErrorCode;
import host.helen.shanjupay.merchant.api.MerchantService;
import host.helen.shanjupay.merchant.api.dto.MerchantDTO;
import host.helen.shanjupay.merchant.convert.MerchantConvert;
import host.helen.shanjupay.merchant.entity.Merchant;
import host.helen.shanjupay.merchant.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Created by Administrator.
 */
@org.apache.dubbo.config.annotation.Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantMapper merchantMapper;

    @Override
    public MerchantDTO queryMerchantById(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_100104);
        }
        return MerchantConvert.INSTANCE.entity2dto(merchant);
    }

    @Override
    public MerchantDTO merchantRegistration(Long merchantId,MerchantDTO merchantDTO) {

        Integer integer = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDTO.getMobile()));

        if (integer > 0) {
            // 手机号已经存在。
            throw new BusinessException(CommonErrorCode.E_100113);
        }

        Merchant bo = new Merchant();
        bo.setMobile(merchantDTO.getMobile());
        // 未进行资自申请
        bo.setAuditStatus("0");
        merchantMapper.insert(bo);
        merchantDTO.setId(bo.getId());

        return merchantDTO;
    }

    @Override
    public void applyMerchant(Long merchantId, MerchantDTO merchant) throws BusinessException {
        Merchant entity = MerchantConvert.INSTANCE.dto2entity(merchant);
        //将必要的参数设置到entity
        entity.setId(merchant.getId());
        entity.setMobile(merchant.getMobile());//因为资质申请的时候手机号不让改，还使用数据库中原来的手机号
        entity.setAuditStatus("1");//审核状态1-已申请待审核
        entity.setTenantId(merchant.getTenantId());

        merchantMapper.updateById(entity);

        System.out.println(entity);

    }
}
