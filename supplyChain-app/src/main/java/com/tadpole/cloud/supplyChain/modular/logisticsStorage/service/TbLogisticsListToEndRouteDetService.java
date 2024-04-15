package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRouteDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToEndRouteDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToEndRouteDetResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 物流单尾程信息-明细;(tb_logistics_list_to_end_route_det)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsListToEndRouteDetService extends IService<TbLogisticsListToEndRouteDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsListToEndRouteDet queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsListToEndRouteDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsListToEndRouteDetResult> paginQuery(TbLogisticsListToEndRouteDetParam tbLogisticsListToEndRouteDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsListToEndRouteDet 实例对象
     * @return 实例对象
     */
    TbLogisticsListToEndRouteDet insert(TbLogisticsListToEndRouteDet tbLogisticsListToEndRouteDet);
    /** 
     * 更新数据
     *
     * @param tbLogisticsListToEndRouteDet 实例对象
     * @return 实例对象
     */
    TbLogisticsListToEndRouteDet update(TbLogisticsListToEndRouteDetParam tbLogisticsListToEndRouteDet);
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
      * 根据批次号和快递单号 删除物流单尾程信息
      * @param lhrCode
      * @param lhrOddNumb
      * @return
      */
     int delByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb);

     /**
      * 根据批次号和快递单号 更新 物流单尾程信息
      * @param lhrCode
      * @param lhrOddNumb
      * @param state
      * @return
      */
     int upByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb, String state);
 }