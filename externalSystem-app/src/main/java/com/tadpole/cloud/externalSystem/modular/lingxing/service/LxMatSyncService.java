package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatConfig;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatSync;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.LxMatSyncParm;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.BrandResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.CategoryResp;

import java.util.List;

/**
 * 领星物料同步;(LX_MAT_SYNC)表服务接口
 * @author : LSY
 * @date : 2022-12-5
 */
public interface LxMatSyncService{

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LxMatSync queryById(String id);

    /**
     * 分页查询
     *
     * @param lxMatSync 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    PageResult<LxMatSync> paginQuery(LxMatSyncParm lxMatSync, long current, long size);
    /**
     * 新增数据
     *
     * @param lxMatSync 实例对象
     * @return 实例对象
     */
    LxMatSync insert(LxMatSync lxMatSync);
    /**
     * 更新数据
     *
     * @param lxMatSync 实例对象
     * @return 实例对象
     */
    LxMatSync update(LxMatSyncParm lxMatSync);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 获取物料同步记录
     * @param matCodeList
     * @return
     */
     List<LxMatSync> getSyncList(List<String> matCodeList);

    /**
     * 同步物料到领星ERP
     * @param filterResultData
     * @return
     */
    ResponseData initLxErpData(List<TbComMateriel> filterResultData);

    /**
     * 生成同步物料到领星erp的初始记录
     * @param tbComMateriel
     * @param lxMatConfig
     * @param brand
     * @param categoryMap
     * @return
     */
    LxMatSync generateSyncInfo(TbComMateriel tbComMateriel, LxMatConfig lxMatConfig, BrandResp brand,List<CategoryResp> allCategory);

    ResponseData syncToLxErp(LxMatSync lxMatSync, List<CategoryResp> allCategory);
}
