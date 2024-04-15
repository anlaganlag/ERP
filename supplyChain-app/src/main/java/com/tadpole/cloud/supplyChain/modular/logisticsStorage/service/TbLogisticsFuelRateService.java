package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsFuelRate;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsFuelRateResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsFuelRateParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流燃料费率;(tb_logistics_fuel_rate)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsFuelRateService extends IService<TbLogisticsFuelRate> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lfrId 主键
     * @return 实例对象
     */
    TbLogisticsFuelRate queryById(BigDecimal lfrId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsFuelRate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsFuelRateResult> paginQuery(TbLogisticsFuelRateParam tbLogisticsFuelRate, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsFuelRate 实例对象
     * @return 实例对象
     */
    TbLogisticsFuelRate insert(TbLogisticsFuelRate tbLogisticsFuelRate);
    /** 
     * 更新数据
     *
     * @param tbLogisticsFuelRate 实例对象
     * @return 实例对象
     */
    TbLogisticsFuelRate update(TbLogisticsFuelRateParam tbLogisticsFuelRate);
    /** 
     * 通过主键删除数据
     *
     * @param lfrId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal lfrId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lfrIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> lfrIdList);
}