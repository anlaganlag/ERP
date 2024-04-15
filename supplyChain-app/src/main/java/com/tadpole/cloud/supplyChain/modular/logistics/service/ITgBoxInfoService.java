package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;

import java.util.List;

/**
 * <p>
 *  报关外箱服务类
 * </p>
 *
 * @author ty
 * @since 2023-07-07
 */
public interface ITgBoxInfoService extends IService<TgBoxInfo> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgBoxInfoParam param);

    /**
     * 新增
     */
    ResponseData add(TgBoxInfoParam param);

    /**
     * 删除
     */
    ResponseData delete(TgBoxInfoParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgBoxInfoParam param);

    /**
     * 箱型下拉
     */
    List<BaseSelectResult> boxTypeSelect();

    /**
     * 获取所有报关外箱
     */
    List<TgBoxInfo> listTgBoxInfo();

}
