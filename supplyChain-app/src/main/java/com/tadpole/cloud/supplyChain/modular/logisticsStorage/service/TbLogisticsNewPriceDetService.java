package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsNewPriceDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsNewPriceDetParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流商的价格信息-明细;(tb_logistics_new_price_det)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsNewPriceDetService extends IService<TbLogisticsNewPriceDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogpDetId 主键
     * @return 实例对象
     */
    TbLogisticsNewPriceDet queryById(BigDecimal pkLogpDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsNewPriceDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsNewPriceDetResult> paginQuery(TbLogisticsNewPriceDetParam tbLogisticsNewPriceDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsNewPriceDet 实例对象
     * @return 实例对象
     */
    TbLogisticsNewPriceDet insert(TbLogisticsNewPriceDet tbLogisticsNewPriceDet);
    /** 
     * 更新数据
     *
     * @param tbLogisticsNewPriceDet 实例对象
     * @return 实例对象
     */
    ResponseData update(TbLogisticsNewPriceDetParam tbLogisticsNewPriceDet);
    /** 
     * 通过主键删除数据
     *
     * @param pkLogpDetId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkLogpDetId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param pkLogpDetIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> pkLogpDetIdList);

     /**
      * 通过ID+状态  更新价格明细的使用状态
      * @param  pkLogpId
      * @param pkLogpDetId
      * @param busLogpDetUseStatus
      * @return
      */
     ResponseData updateDetUseStatus(BigDecimal pkLogpId,BigDecimal pkLogpDetId, String busLogpDetUseStatus);


     @DataSource(name = "logistics")
     List<TbLogisticsNewPriceDet> queryByPkLogpId(BigDecimal pkLogpId, Integer busLogpDetStatus, String busLogpDetUseStatus);
 }