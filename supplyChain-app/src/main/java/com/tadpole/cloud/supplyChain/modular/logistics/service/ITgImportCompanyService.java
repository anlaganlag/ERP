package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgImportCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgImportCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgEbmsCompanyResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgImportCompanyResult;

import java.util.List;

/**
 * <p>
 * 进口商 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgImportCompanyService extends IService<TgImportCompany> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgImportCompanyParam param);

    /**
     * 新增
     */
    ResponseData add(TgImportCompanyParam param);

    /**
     * 删除
     */
    ResponseData delete(TgImportCompanyParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgImportCompanyParam param);

    /**
     * 导出
     */
    List<TgImportCompanyResult> export(TgImportCompanyParam param);

    /**
     * 进口商名称下拉
     */
    List<BaseSelectResult> inCompanyNameSelect();

    /**
     * 实体公司下拉
     * @param comTaxType 税务类型（业务规划类型）
     * @return
     */
    List<TgEbmsCompanyResult> companySelect(String comTaxType);

    /**
     * 发货公司下拉
     */
    List<TgImportCompany> importCompanySelect();

    /**
     * 进口商公司运抵国下拉
     * @param param
     * @return
     */
    List<TgImportCompany> importCompanyCountrySelect(TgImportCompanyParam param);

}
