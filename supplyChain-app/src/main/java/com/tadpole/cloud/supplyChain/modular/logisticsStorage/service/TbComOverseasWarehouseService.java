package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbComOverseasWarehouse;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbComOverseasWarehouseResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbComOverseasWarehouseParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 海外仓信息;(Tb_Com_Overseas_Warehouse)表服务接口
 * @author : LSY
 * @date : 2024-1-19
 */
public interface TbComOverseasWarehouseService extends IService<TbComOverseasWarehouse> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param owName 主键
     * @return 实例对象
     */
    TbComOverseasWarehouse queryById(String owName);
    
    /**
     * 分页查询
     *
     * @param tbComOverseasWarehouse 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComOverseasWarehouseResult> paginQuery(TbComOverseasWarehouseParam tbComOverseasWarehouse, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComOverseasWarehouse 实例对象
     * @return 实例对象
     */
    TbComOverseasWarehouse insert(TbComOverseasWarehouse tbComOverseasWarehouse);
    /** 
     * 更新数据
     *
     * @param tbComOverseasWarehouse 实例对象
     * @return 实例对象
     */
    TbComOverseasWarehouse update(TbComOverseasWarehouseParam tbComOverseasWarehouse);
    /** 
     * 通过主键删除数据
     *
     * @param owName 主键
     * @return 是否成功
     */
    boolean deleteById(String owName);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param owNameList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> owNameList);
}