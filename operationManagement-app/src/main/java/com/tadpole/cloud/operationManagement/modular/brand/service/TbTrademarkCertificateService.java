package com.tadpole.cloud.operationManagement.modular.brand.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbTrademarkCertificate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商标证书管理表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-27
 */
public interface TbTrademarkCertificateService extends IService<TbTrademarkCertificate> {
  void save(TbTrademarkCertificateParam param);
   TbTrademarkCertificateResult queryById(Long id);
    List<TbTrademarkCertificate> queryByBrand(String salesBrand);
   Page<TbTrademarkCertificateResult> getPage(TbTrademarkCertificatePageParam param);
}

