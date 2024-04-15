package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbEPlatStorageCenter;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbEPlatStorageCenterResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbEPlatStorageCenterParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 电商平台仓储中心;(Tb_E_Plat_Storage_Center)表服务接口
 * @author : LSY
 * @date : 2024-1-2
 */
public interface TbEPlatStorageCenterService extends IService<TbEPlatStorageCenter> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbEPlatStorageCenter queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbEPlatStorageCenter 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbEPlatStorageCenterResult> paginQuery(TbEPlatStorageCenterParam tbEPlatStorageCenter, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbEPlatStorageCenter 实例对象
     * @return 实例对象
     */
    TbEPlatStorageCenter insert(TbEPlatStorageCenter tbEPlatStorageCenter);
    /** 
     * 更新数据
     *
     * @param tbEPlatStorageCenter 实例对象
     * @return 实例对象
     */
    TbEPlatStorageCenter update(TbEPlatStorageCenterParam tbEPlatStorageCenter);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> idList);
}