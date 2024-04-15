package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsProviderParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderResult;

import java.util.List;

/**
 * 物流供应商;(tb_logistics_provider)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsProviderService extends IService<TbLogisticsProvider> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpCode 主键
     * @return 实例对象
     */
    TbLogisticsProvider queryById(String lpCode);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsProvider 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsProviderResult> paginQuery(TbLogisticsProviderParam tbLogisticsProvider, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsProvider 实例对象
     * @return 实例对象
     */
    ResponseData insert(TbLogisticsProvider tbLogisticsProvider);
    /** 
     * 更新数据
     *
     * @param tbLogisticsProvider 实例对象
     * @return 实例对象
     */
    TbLogisticsProvider update(TbLogisticsProviderParam tbLogisticsProvider);
    /** 
     * 通过主键删除数据
     *
     * @param lpCode 主键
     * @return 是否成功
     */
    boolean deleteById(String lpCode);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lpCodeList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> lpCodeList);

    /**
     * 同步金蝶k3系统里面的物流上信息
     * @return
     */
    ResponseData synLogisticsBusinessFromERP();


    ResponseData lpCodeList();

    ResponseData lpNameList();

    ResponseData lpSimpleNameList();

}