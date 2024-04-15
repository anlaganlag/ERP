package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsTaxRate;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsTaxRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsTaxRateResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 清关税率 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgCustomsTaxRateService extends IService<TgCustomsTaxRate> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgCustomsTaxRateParam param);

    /**
     * 新增
     */
    ResponseData add(TgCustomsTaxRateParam param);

    /**
     * 删除
     */
    ResponseData delete(TgCustomsTaxRateParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgCustomsTaxRateParam param);

    /**
     * 导出
     */
    List<TgCustomsTaxRateResult> export(TgCustomsTaxRateParam param);

    /**
     * 导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

    /**
     * 目的国下拉
     */
    List<BaseSelectResult> targetCountrySelect();

    /**
     * 根据国家级联HSCode下拉
     */
    List<BaseSelectResult> hsCodeSelect(String countryCode);
}
