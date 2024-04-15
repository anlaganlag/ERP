package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsPriceCoeffRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsPriceCoeffRuleParam;

/**
 * <p>
 * 清关价格折算 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgCustomsPriceCoeffRuleService extends IService<TgCustomsPriceCoeffRule> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgCustomsPriceCoeffRuleParam param);

    /**
     * 新增
     */
    ResponseData add(TgCustomsPriceCoeffRuleParam param);

    /**
     * 删除
     */
    ResponseData delete(TgCustomsPriceCoeffRuleParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgCustomsPriceCoeffRuleParam param);

    /**
     * 根据条件查询清关价格折算
     */
    TgCustomsPriceCoeffRule queryPriceCoeff(TgCustomsPriceCoeffRuleParam param);
}
