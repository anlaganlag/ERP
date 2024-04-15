package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgPostCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgPostCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgPostCompanyResult;

import java.util.List;

/**
 * <p>
 * 发货公司 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgPostCompanyService extends IService<TgPostCompany> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgPostCompanyParam param);

    /**
     * 新增
     */
    ResponseData add(TgPostCompanyParam param);

    /**
     * 删除
     */
    ResponseData delete(TgPostCompanyParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgPostCompanyParam param);

    /**
     * 导出
     */
    List<TgPostCompanyResult> export(TgPostCompanyParam param);

    /**
     * 发货公司名称下拉
     */
    List<BaseSelectResult> postCompanyNameSelect();

    /**
     * 发货公司名称下拉
     */
    List<TgPostCompany> postCompanySelect();
}
