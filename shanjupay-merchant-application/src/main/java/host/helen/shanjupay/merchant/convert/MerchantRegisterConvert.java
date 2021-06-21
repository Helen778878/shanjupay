package host.helen.shanjupay.merchant.convert;

import host.helen.shanjupay.merchant.api.dto.MerchantDTO;
import host.helen.shanjupay.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Classname MerchantRegisterConvert
 * @Description MerchantRegisterConvert
 * @Date 2021/6/16 5:26 PM
 * @Created by helen
 */
@Mapper
public interface MerchantRegisterConvert {

    MerchantRegisterConvert INSTANCE = Mappers.getMapper(MerchantRegisterConvert.class);

    //将dto转成vo
    MerchantRegisterVO dto2vo(MerchantDTO merchantDTO);

    //将vo转成dto
    MerchantDTO vo2dto(MerchantRegisterVO merchantRegisterVO);

}
