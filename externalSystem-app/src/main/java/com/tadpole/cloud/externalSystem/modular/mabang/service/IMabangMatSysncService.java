package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangMatSync;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangMatSyncParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangMatSyncResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangWarehouseResult;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 马帮物料同步记录表 服务类
 * </p>
 *
 * @author gal
 * @since 2022-10-24
 */
public interface IMabangMatSysncService extends IService<MabangMatSync> {


    public ResponseData addMaterielFromEbms(List<TbComMateriel> materielList, List<MabangWarehouseResult> warehouseList );
    public ResponseData updateFromEbms(List<TbComMateriel> materielList, List<MabangWarehouseResult> warehouseList );

    List<MabangMatSync> getSyncList(List<String> matCodeList,String WarehouseId);

    ResponseData stockChangeGrid();

    ResponseData addAllMatToNewWarehouse();
    List<MabangWarehouse>  getNewWarehouseList(Date creatStartDate);
    ResponseData addAllMatToNewWarehouse(List<MabangWarehouse> newWarehouseList, List<TbComMateriel> allMatList);

    PageResult<MabangMatSyncResult> listPage(MabangMatSyncParam param);

    List<MabangWarehouseResult> getNewWarehouseByName(String warehouse);

    /**
     * 查找已经同步成功的记录
     * @param matCodeList  物料编码List
     * @param warehouseId  仓库ID
     * @return 已经同步成功去重后的物流编码
     */
    List<MabangMatSync> getSyncSuccessMatCode(List<String> matCodeList,String warehouseId);
}
