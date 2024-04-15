package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListDet2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbShipemtListClearancModel;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 出货清单明细2-装箱内容-金蝶+海外仓;(tb_logistics_packing_list_det2)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackingListDet2Service extends IService<TbLogisticsPackingListDet2> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetId 主键
     * @return 实例对象
     */
    TbLogisticsPackingListDet2 queryById(BigDecimal packDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackingListDet2 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackingListDet2Result> paginQuery(TbLogisticsPackingListDet2Param tbLogisticsPackingListDet2, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingListDet2 实例对象
     * @return 实例对象
     */
    TbLogisticsPackingListDet2 insert(TbLogisticsPackingListDet2 tbLogisticsPackingListDet2);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackingListDet2 实例对象
     * @return 实例对象
     */
    TbLogisticsPackingListDet2 update(TbLogisticsPackingListDet2Param tbLogisticsPackingListDet2);
    /** 
     * 通过主键删除数据
     *
     * @param packDetId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal packDetId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param packDetIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> packDetIdList);

     /**
      * 更具物流单号以及出货清单号 查询清关数据
      * @param lhrOddNumb
      * @param packCodeList
      * @return
      */
     List<TbShipemtListClearancModel> getClearanceData(String lhrOddNumb, List<String> packCodeList);

     /**
      * 更具批次号 查询
      * @param lhrCode
      * @return
      */
     List<TbLogisticsPackingListDet2> queryByLhrCode(String lhrCode);


     List<TbLogisticsPackingListDet2> queryByPackCode(List<String> packCodeList);

     int deleteByPackCode(String packCode);
 }