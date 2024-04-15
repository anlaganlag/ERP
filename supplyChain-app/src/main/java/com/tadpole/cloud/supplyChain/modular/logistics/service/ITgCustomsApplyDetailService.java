package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyDetailParam;

import java.util.List;

/**
 * <p>
 * 报关单明细 服务类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface ITgCustomsApplyDetailService extends IService<TgCustomsApplyDetail> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(TgCustomsApplyDetailParam param);

    /**
     * 查询列表
     * @param param
     * @return
     */
    ResponseData queryList(TgCustomsApplyDetailParam param);

    /**
     * 获取报关单信息缺失明细数据
     * @param param
     * @return
     */
    List<TgCustomsApplyDetail> getIncompleteDetail(TgCustomsApplyDetail param);

}
