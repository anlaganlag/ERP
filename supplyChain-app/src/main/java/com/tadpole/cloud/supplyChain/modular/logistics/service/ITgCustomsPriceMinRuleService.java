package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsPriceMinRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsPriceMinRuleParam;

/**
 * <p>
 * 最低清关价格 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgCustomsPriceMinRuleService extends IService<TgCustomsPriceMinRule> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgCustomsPriceMinRuleParam param);

    /**
     * 新增
     */
    ResponseData add(TgCustomsPriceMinRuleParam param);

    /**
     * 删除
     */
    ResponseData delete(TgCustomsPriceMinRuleParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgCustomsPriceMinRuleParam param);
}
