package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsEssentialFactor;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsEssentialFactorParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsEssentialFactorResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 报关要素 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgCustomsEssentialFactorService extends IService<TgCustomsEssentialFactor> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgCustomsEssentialFactorParam param);

    /**
     * 新增
     */
    ResponseData add(TgCustomsEssentialFactorParam param);

    /**
     * 删除
     */
    ResponseData delete(TgCustomsEssentialFactorParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgCustomsEssentialFactorParam param);

    /**
     * 导出
     */
    List<TgCustomsEssentialFactorResult> export(TgCustomsEssentialFactorParam param);

    /**
     * 导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

    /**
     * 报关要素下拉
     */
    List<BaseSelectResult> essentialFactorSelect();
}
