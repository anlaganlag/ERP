package com.tadpole.cloud.product.modular.product.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.modular.product.entity.RdPlManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;

import java.util.List;

/**
 * <p>
 * 产品线管理 服务类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
public interface IRdPlManageService extends IService<RdPlManage> {

    List<RdPlManageResult> list(RdPlManageParam param);

    PageResult<RdPlManageResult> listPage(RdPlManageParam param);


    ResponseData add(RdPlManageParam param);

    ResponseData edit(RdPlManageParam param) throws IllegalAccessException;

    void enable(RdPlManageParam param);

    void disable(RdPlManageParam param);

    void enableBatch(List<RdPlManageParam> params);

    void disableBatch(List<RdPlManageParam> params);

    void editPersonBatch(List<RdPlManageParam> params) throws IllegalAccessException;
}
