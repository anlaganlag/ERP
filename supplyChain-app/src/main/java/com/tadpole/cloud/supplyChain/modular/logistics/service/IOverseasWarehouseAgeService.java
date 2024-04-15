package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseAge;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseAgeParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseAgeResult;

import java.util.List;

/**
 * <p>
 * 海外仓库龄报表服务类
 * </p>
 *
 * @author cyt
 * @since 2023-02-03
 */
public interface IOverseasWarehouseAgeService extends IService<OverseasWarehouseAge> {

    /**
     * 海外仓库龄报表分页查询列表
     */
    ResponseData queryPage(OverseasWarehouseAgeParam param);

    /**
     * 海外仓库龄报表导出
     * @param param
     * @return
     */
    List<OverseasWarehouseAgeResult> export(OverseasWarehouseAgeParam param);

    /**
     * 签收入库海外仓库龄报表
     */
    ResponseData batchAgeIn(List<OverseasWarehouseAge> param);

    /**
     * 海外仓库龄报表出库
     */
    ResponseData batchAgeOut(List<OverseasWarehouseAge> param);
}
