package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRec;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListBoxRecResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 出货清单和亚马逊货件关系映射表;(tb_logistics_pack_list_box_rec)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackListBoxRecService extends IService<TbLogisticsPackListBoxRec> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    TbLogisticsPackListBoxRec queryById(BigDecimal sysId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackListBoxRec 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackListBoxRecResult> paginQuery(TbLogisticsPackListBoxRecParam tbLogisticsPackListBoxRec, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListBoxRec 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListBoxRec insert(TbLogisticsPackListBoxRec tbLogisticsPackListBoxRec);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListBoxRec 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListBoxRec update(TbLogisticsPackListBoxRecParam tbLogisticsPackListBoxRec);
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param sysIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> sysIdList);

     /**
      * 根据PackCode出货清单号 删除 出货清单和亚马逊货件关系映射
      *
      * @param packCode 出货清单号
      * @return 是否成功
      */
     boolean deleteByPackCode(String packCode);


     TbLogisticsPackListBoxRec queryByPackListCodeAndPackCode(String packListCode, String packCode);

     TbLogisticsPackListBoxRec queryByPackListCode(String packListCode);

     int deleteByPackListCode(String packListCode);

     ResponseData amazRecStateList();

 }