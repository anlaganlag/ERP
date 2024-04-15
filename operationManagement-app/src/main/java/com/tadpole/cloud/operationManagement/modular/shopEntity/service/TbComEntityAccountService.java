package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityAccount;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityAccountParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityAccountResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-公司实体账户信息;(Tb_Com_Entity_Account)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityAccountService extends IService<TbComEntityAccount> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param comAccId 主键
     * @return 实例对象
     */
    TbComEntityAccount queryById(BigDecimal comAccId);
    
    /**
     * 分页查询
     *
     * @param tbComEntityAccount 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityAccountResult> paginQuery(TbComEntityAccountParam tbComEntityAccount, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityAccount 实例对象
     * @return 实例对象
     */
    TbComEntityAccount insert(TbComEntityAccount tbComEntityAccount);
    /** 
     * 更新数据
     *
     * @param tbComEntityAccount 实例对象
     * @return 实例对象
     */
    TbComEntityAccount update(TbComEntityAccount tbComEntityAccount);
    /** 
     * 通过主键删除数据
     *
     * @param comAccId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal comAccId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param comAccIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> comAccIdList);
}