package host.helen.shanjupay.merchant.convert;

import host.helen.shanjupay.merchant.api.dto.MerchantDTO;
import host.helen.shanjupay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Classname MerchantConvert
 * @Description 定义DTO转换为entity的转换规则
 * @Date 2021/6/16 4:59 PM
 * @Created by helen
 */
@Mapper
public interface MerchantConvert {

    MerchantConvert INSTANCE = Mappers.getMapper(MerchantConvert.class);

    Merchant dto2entity(MerchantDTO merchantDTO);

    MerchantDTO entity2dto(Merchant merchant);
}
