package com.tadpole.cloud.operationManagement.modular.brand.mapper;

import com.tadpole.cloud.operationManagement.api.brand.entity.TbTrademarkCertificate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
* 商标证书管理表 Mapper接口
* </p>
*
* @author S20190161
* @since 2023-10-27
*/
public interface TbTrademarkCertificateMapper extends BaseMapper<TbTrademarkCertificate> {
    List<TbTrademarkCertificate> queryByBrand(@Param("brandName") String brandName);
}
