package host.helen.shanjupay.merchant.service;

import host.helen.shanjupay.common.domain.BusinessException;

/**
 * @Classname FileService
 * @Description FileService
 * @Date 2021/6/17 7:28 PM
 * @Created by helen
 */
public interface FileService {
    String upload(byte[] fileByte, String fileName) throws BusinessException;
}
