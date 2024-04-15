package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbBscOverseasWay;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbBscOverseasWayResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbBscOverseasWayParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 发货汇总表;(Tb_Bsc_Overseas_Way)表服务接口
 * @author : LSY
 * @date : 2024-1-10
 */
public interface TbBscOverseasWayService extends IService<TbBscOverseasWay> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbBscOverseasWay queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbBscOverseasWay 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbBscOverseasWayResult> paginQuery(TbBscOverseasWayParam tbBscOverseasWay, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbBscOverseasWay 实例对象
     * @return 实例对象
     */
    TbBscOverseasWay insert(TbBscOverseasWay tbBscOverseasWay);
    /** 
     * 更新数据
     *
     * @param tbBscOverseasWay 实例对象
     * @return 实例对象
     */
    TbBscOverseasWay update(TbBscOverseasWayParam tbBscOverseasWay);
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

     /**
      * 根据PackCode 删除发货数据汇总
      *
      * @param packCode 出货清单号
      * @return 是否成功
      */
     boolean deleteByPackCode(String packCode);


     void updBscOverseasWayDeliverStatus(String packCode,String state);

     /**
      * 创建发货记录
      * @param packCode
      * @return
      */
     boolean createBscOverseasWay(String packCode);

     /**
      * 更新出货方式 + 更新物流发货状态
      * @param lhrCode 发货批次号
      * @param lhrOddNumb 头程物流单号
      * @param deliverType 发货方式
      * @param deliverStatus 物料发货状态
      * @return
      */
     boolean updBscOverseasWayDeliveryType(String lhrCode,String lhrOddNumb, String deliverType,String deliverStatus);


     /**
      * 更新出货方式
      * @param lhrCode 发货批次号
      * @param deliverType 发货方式
      * @return
      */
     int updBscOverseasWayDeliveryType(String lhrCode, String deliverType);

     /**
      *更新发货数量
      * @param detParam
      * @return
      */
     int updateDeliveryNum(TbLogisticsPackListBoxRecDetParam detParam);

 }