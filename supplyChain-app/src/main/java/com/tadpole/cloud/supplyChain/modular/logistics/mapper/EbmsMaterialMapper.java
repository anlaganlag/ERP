package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.EbmsMaterial;
import java.util.List;

/**
 * <p>
 * EBMS物料信息 Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2022-12-22
 */
public interface EbmsMaterialMapper extends BaseMapper<EbmsMaterial> {

    /**
     * 获取EBMS物料信息
     * @return
     */
    List<EbmsMaterial> getEbmsMaterial();
}
