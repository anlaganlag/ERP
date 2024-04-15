package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.ProductGallery;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.ProductGalleryMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IProductGalleryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* sku信息表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
public class ProductGalleryServiceImpl extends ServiceImpl<ProductGalleryMapper, ProductGallery> implements IProductGalleryService {

    @Autowired
    private ProductGalleryMapper mapper;

}
