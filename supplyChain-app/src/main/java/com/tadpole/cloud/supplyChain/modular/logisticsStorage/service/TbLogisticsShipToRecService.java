package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipToRec;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipToRecParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipToRecResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 平台货件接收地址;(tb_logistics_ship_to_rec)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsShipToRecService extends IService<TbLogisticsShipToRec> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lfrId 主键
     * @return 实例对象
     */
    TbLogisticsShipToRec queryById(BigDecimal lfrId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsShipToRec 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsShipToRecResult> paginQuery(TbLogisticsShipToRecParam tbLogisticsShipToRec, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipToRec 实例对象
     * @return 实例对象
     */
    TbLogisticsShipToRec insert(TbLogisticsShipToRec tbLogisticsShipToRec);
    /** 
     * 更新数据
     *
     * @param tbLogisticsShipToRec 实例对象
     * @return 实例对象
     */
    TbLogisticsShipToRec update(TbLogisticsShipToRecParam tbLogisticsShipToRec);
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

     List<String> logRecHouseNameList();

     ResponseData batchAdd(List<TbLogisticsShipToRec> tbLogisticsShipToRecList);
 }