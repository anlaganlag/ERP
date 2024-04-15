package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBoxInfo;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBoxInfoResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBoxInfoParam;

import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流箱信息-长宽高重;(tb_logistics_box_info)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsBoxInfoService extends IService<TbLogisticsBoxInfo> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetBoxNum 主键
     * @return 实例对象
     */
    TbLogisticsBoxInfo queryById(BigDecimal packDetBoxNum);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsBoxInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsBoxInfoResult> paginQuery(TbLogisticsBoxInfoParam tbLogisticsBoxInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsBoxInfo 实例对象
     * @return 实例对象
     */
    TbLogisticsBoxInfo insert(TbLogisticsBoxInfo tbLogisticsBoxInfo);
    /** 
     * 更新数据
     *
     * @param tbLogisticsBoxInfo 实例对象
     * @return 实例对象
     */
    TbLogisticsBoxInfo update(TbLogisticsBoxInfoParam tbLogisticsBoxInfo);
    /** 
     * 通过主键删除数据
     *
     * @param packDetBoxNum 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal packDetBoxNum);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param packDetBoxNumList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> packDetBoxNumList);
}