package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.EbmsMaterial;

import java.util.List;

/**
 * <p>
 * EBMS物料信息 服务类
 * </p>
 *
 * @author ty
 * @since 2022-12-22
 */
public interface IEbmsMaterialService extends IService<EbmsMaterial> {

    /**
     * 获取EBMS物料信息
     * @return
     */
    List<EbmsMaterial> getEbmsMaterial();

    /**
     * MC海外仓物料信息入库
     * @param ebmsMaterialList
     * @return
     */
    ResponseData syncEbmsMaterial(List<EbmsMaterial> ebmsMaterialList);
}
