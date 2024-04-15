package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.EbmsMaterial;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EbmsMaterialMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IEbmsMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * EBMS物料信息 服务实现类
 * </p>
 *
 * @author ty
 * @since 2022-12-22
 */
@Service
public class EbmsMaterialServiceImpl extends ServiceImpl<EbmsMaterialMapper, EbmsMaterial> implements IEbmsMaterialService {

    @Autowired
    private IEbmsMaterialService ebmsMaterialService;

    @Override
    @DataSource(name = "EBMS")
    public List<EbmsMaterial> getEbmsMaterial() {
        return baseMapper.getEbmsMaterial();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData syncEbmsMaterial(List<EbmsMaterial> ebmsMaterialList) {
        ebmsMaterialService.remove(null);
        ebmsMaterialService.saveBatch(ebmsMaterialList);
        return ResponseData.success();
    }
}
