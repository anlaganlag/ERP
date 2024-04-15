package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsAgainCoeffRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsAgainCoeffRuleParam;

/**
 * <p>
 * 清关二次折算 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgCustomsAgainCoeffRuleService extends IService<TgCustomsAgainCoeffRule> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgCustomsAgainCoeffRuleParam param);

    /**
     * 新增
     */
    ResponseData add(TgCustomsAgainCoeffRuleParam param);

    /**
     * 删除
     */
    ResponseData delete(TgCustomsAgainCoeffRuleParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgCustomsAgainCoeffRuleParam param);

    /**
     * 根据条件查询清关价格二次折算
     */
    TgCustomsAgainCoeffRule queryAgainCoeff(TgCustomsAgainCoeffRuleParam param);
}
