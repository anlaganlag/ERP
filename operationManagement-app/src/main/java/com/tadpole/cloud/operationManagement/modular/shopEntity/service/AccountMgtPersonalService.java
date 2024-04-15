package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountMgtPersonalResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import java.util.List;

 /**
 * 个人账户管理;(ACCOUNT_MGT_PERSONAL)表服务接口
 * @author : LSY
 * @date : 2023-11-10
 */
public interface AccountMgtPersonalService extends IService<AccountMgtPersonal> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountMgtPersonal queryById(String id);
    
    /**
     * 分页查询
     *
     * @param accountMgtPersonal 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<AccountMgtPersonalResult> paginQuery(AccountMgtPersonalParam accountMgtPersonal, long current, long size);
    /** 
     * 新增数据
     *
     * @param accountMgtPersonal 实例对象
     * @return 实例对象
     */
    AccountMgtPersonal insert(AccountMgtPersonal accountMgtPersonal);
    /** 
     * 更新数据
     *
     * @param accountMgtPersonal 实例对象
     * @return 实例对象
     */
    AccountMgtPersonal update(AccountMgtPersonalParam accountMgtPersonal);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> idList);
}