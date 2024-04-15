package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;

import java.util.List;

/**
 * <p>
 * 报关自定义外箱表 服务类
 * </p>
 *
 * @author ty
 * @since 2023-08-18
 */
public interface ITgCustomBoxInfoService extends IService<TgCustomBoxInfo> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgCustomBoxInfoParam param);

    /**
     * 新增
     */
    ResponseData add(TgCustomBoxInfoParam param);

    /**
     * 删除
     */
    ResponseData delete(TgCustomBoxInfoParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgCustomBoxInfoParam param);

    /**
     * 箱型下拉
     */
    List<BaseSelectResult> boxTypeSelect();

    /**
     * 获取所有自定义报关外箱
     */
    List<TgCustomBoxInfo> listTgBoxInfo();

}
