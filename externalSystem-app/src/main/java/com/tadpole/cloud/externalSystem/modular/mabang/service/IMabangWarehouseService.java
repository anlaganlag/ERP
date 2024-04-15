package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.WarehouseParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangWarehouseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 马帮仓库列表 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-09
 */
public interface IMabangWarehouseService extends IService<MabangWarehouse> {

    /**
     * 马帮仓库列表接口
     */
    PageResult<MabangWarehouseResult> findPageBySpec(MabangWarehouseParam param);

    /**
     * 保存查询仓库列表接口
     */
    ResponseData getWarehouseList(WarehouseParm warehouseParm) throws Exception;

    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    MabangWarehouse findByName(String warehouseName);

    @DataSource(name = "k3cloud")
    @Transactional(rollbackFor = Exception.class)
    Map<String, String> getK3MabangWarehouse();

    ResponseData queryNames();

    List<MabangWarehouseResult> exportExcel(MabangWarehouseParam param);
}
