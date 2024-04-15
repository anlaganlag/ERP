package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsAccount;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsAccountResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsAccountParam;

import java.util.List;

/**
 * 物流账号;(tb_logistics_account)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsAccountService extends IService<TbLogisticsAccount> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lcCode 主键
     * @return 实例对象
     */
    TbLogisticsAccount queryById(String lcCode);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsAccount 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsAccountResult> paginQuery(TbLogisticsAccountParam tbLogisticsAccount, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsAccount 实例对象
     * @return 实例对象
     */
    TbLogisticsAccount insert(TbLogisticsAccount tbLogisticsAccount);
    /** 
     * 更新数据
     *
     * @param tbLogisticsAccount 实例对象
     * @return 实例对象
     */
    TbLogisticsAccount update(TbLogisticsAccountParam tbLogisticsAccount);
    /** 
     * 通过主键删除数据
     *
     * @param lcCode 主键
     * @return 是否成功
     */
    boolean deleteById(String lcCode);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lcCodeList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> lcCodeList);

    List<String> lcCodeList(String lcState);
}