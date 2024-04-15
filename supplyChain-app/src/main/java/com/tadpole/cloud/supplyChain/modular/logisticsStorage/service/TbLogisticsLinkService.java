package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsLink;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsLinkResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsLinkParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流信息查询;(tb_logistics_link)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsLinkService extends IService<TbLogisticsLink> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsLink queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsLink 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsLinkResult> paginQuery(TbLogisticsLinkParam tbLogisticsLink, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsLink 实例对象
     * @return 实例对象
     */
    TbLogisticsLink insert(TbLogisticsLink tbLogisticsLink);
    /** 
     * 更新数据
     *
     * @param tbLogisticsLink 实例对象
     * @return 实例对象
     */
    TbLogisticsLink update(TbLogisticsLinkParam tbLogisticsLink);
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
      * 物流商追踪链接
      * @param logTraMode1
      * @return
      */
     List<TbLogisticsLink> findByLogTraMode1(String logTraMode1);

     /**
      * 新增或者更新
      * @param linkParam
      * @return
      */
     ResponseData insertOrUpdate(TbLogisticsLinkParam linkParam);
 }