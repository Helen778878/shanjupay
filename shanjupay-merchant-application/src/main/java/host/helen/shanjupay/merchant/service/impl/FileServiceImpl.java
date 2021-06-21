package host.helen.shanjupay.merchant.service.impl;

import cn.hutool.core.util.RandomUtil;
import host.helen.shanjupay.common.domain.BusinessException;
import host.helen.shanjupay.common.util.QiniuUtils;
import host.helen.shanjupay.merchant.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Classname FileServiceImpl
 * @Description FileServiceImpl
 * @Date 2021/6/17 7:29 PM
 * @Created by helen
 */
@Service
public class FileServiceImpl implements FileService {


    @Value("${shanjupay.oss.accessKey}")
    private String accessKey;

    @Value("${shanjupay.oss.secretKey}")
    private String secretKey;

    @Value("${shanjupay.oss.bucket}")
    private String bucket;

    @Override
    public String upload(byte[] fileByte, String fileName) throws BusinessException {

        String suffix = fileName.substring(fileName.lastIndexOf("."));

        String newFileName = RandomUtil.randomString(32) + suffix;
        return QiniuUtils.upload2qiniu(accessKey, secretKey, bucket, fileByte, newFileName);
    }
}
