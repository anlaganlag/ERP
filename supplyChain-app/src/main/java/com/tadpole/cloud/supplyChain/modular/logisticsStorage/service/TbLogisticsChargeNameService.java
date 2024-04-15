package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsChargeName;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsChargeNameResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsChargeNameParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流费用名称;(tb_logistics_charge_name)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsChargeNameService extends IService<TbLogisticsChargeName> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsChargeName queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsChargeName 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsChargeNameResult> paginQuery(TbLogisticsChargeNameParam tbLogisticsChargeName, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsChargeName 实例对象
     * @return 实例对象
     */
    TbLogisticsChargeName insert(TbLogisticsChargeName tbLogisticsChargeName);
    /** 
     * 更新数据
     *
     * @param tbLogisticsChargeName 实例对象
     * @return 实例对象
     */
    TbLogisticsChargeName update(TbLogisticsChargeNameParam tbLogisticsChargeName);
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