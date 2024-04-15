package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPrice;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流价格--暂时不需要;(tb_logistics_price)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPriceService extends IService<TbLogisticsPrice> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param logpId 主键
     * @return 实例对象
     */
    TbLogisticsPrice queryById(BigDecimal logpId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPrice 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPriceResult> paginQuery(TbLogisticsPriceParam tbLogisticsPrice, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPrice 实例对象
     * @return 实例对象
     */
    TbLogisticsPrice insert(TbLogisticsPrice tbLogisticsPrice);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPrice 实例对象
     * @return 实例对象
     */
    TbLogisticsPrice update(TbLogisticsPriceParam tbLogisticsPrice);
    /** 
     * 通过主键删除数据
     *
     * @param logpId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal logpId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param logpIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> logpIdList);
}