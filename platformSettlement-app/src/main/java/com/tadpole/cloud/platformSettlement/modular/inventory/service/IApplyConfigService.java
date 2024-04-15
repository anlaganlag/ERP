package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ApplyConfig;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ApplyConfigParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ApplyConfigResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyt
 * @since 2022-05-24
 */
public interface IApplyConfigService extends IService<ApplyConfig> {

    /**
     * 流程配置列表接口
     */
    PageResult<ApplyConfigResult> queryListPage(ApplyConfigParam param);

    /**
     * 流程配置新增接口
     */
    ResponseData add(ApplyConfigParam param);

    /**
     * 流程配置编辑接口
     */
    ResponseData edit(ApplyConfigParam param);

    /**
     * 流程配置删除接口
     */
    ResponseData delete(ApplyConfigParam param);
}
