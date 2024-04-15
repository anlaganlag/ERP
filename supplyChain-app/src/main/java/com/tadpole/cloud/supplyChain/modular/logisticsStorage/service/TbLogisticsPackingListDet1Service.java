package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet1;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet1Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListDet1Param;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 出货清单明细1-箱子长宽高重-金蝶+海外仓;(tb_logistics_packing_list_det1)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackingListDet1Service extends IService<TbLogisticsPackingListDet1> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetId 主键
     * @return 实例对象
     */
    TbLogisticsPackingListDet1 queryById(BigDecimal packDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackingListDet1 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackingListDet1Result> paginQuery(TbLogisticsPackingListDet1Param tbLogisticsPackingListDet1, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingListDet1 实例对象
     * @return 实例对象
     */
    TbLogisticsPackingListDet1 insert(TbLogisticsPackingListDet1 tbLogisticsPackingListDet1);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackingListDet1 实例对象
     * @return 实例对象
     */
    TbLogisticsPackingListDet1 update(TbLogisticsPackingListDet1Param tbLogisticsPackingListDet1);
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
     * 通过出货清单号查询 明细1的数据
     * @param packCodeList
     * @return
     */
     List<TbLogisticsPackingListDet1> getByPackCode(List<String> packCodeList);

     /**
      * 获取通关所需箱件信息
      * @param lhrOddNumb
      * @param packCodeList
      * @return
      */
     List<TbLogisticsPackingListDet1Result> getClearanceBoxInfoData(String lhrOddNumb, List<String> packCodeList);

     /**
      * 根据批次号和快递单号 重置出货清单明细1数据
      * @param lhrCode
      * @param lhrOddNumb
      * @return
      */
     int resetData(String lhrCode, String lhrOddNumb);

     List<TbLogisticsPackingListDet1> queryByPackCode(String packCode);

     int deleteByPackCode(String packCode);
 }