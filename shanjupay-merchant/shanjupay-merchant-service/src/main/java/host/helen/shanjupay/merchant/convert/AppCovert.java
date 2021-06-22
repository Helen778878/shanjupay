package host.helen.shanjupay.merchant.convert;


import host.helen.shanjupay.merchant.api.dto.AppDTO;
import host.helen.shanjupay.merchant.entity.App;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Mapper
public interface AppCovert {

    AppCovert INSTANCE = Mappers.getMapper(AppCovert.class);

    AppDTO entity2dto(App entity);

    App dto2entity(AppDTO dto);

    List<AppDTO> listEntity2dto(List<App> app);

}