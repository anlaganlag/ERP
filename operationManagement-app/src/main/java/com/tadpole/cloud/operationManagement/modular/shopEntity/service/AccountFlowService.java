package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountFlow;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountFlowResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountFlowParam;
import java.util.List;

 /**
 * 账户流水;(ACCOUNT_FLOW)表服务接口
 * @author : LSY
 * @date : 2023-11-10
 */
public interface AccountFlowService extends IService<AccountFlow> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountFlow queryById(String id);
    
    /**
     * 分页查询
     *
     * @param accountFlow 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<AccountFlowResult> paginQuery(AccountFlowParam accountFlow, long current, long size);
    /** 
     * 新增数据
     *
     * @param accountFlow 实例对象
     * @return 实例对象
     */
    AccountFlow insert(AccountFlow accountFlow);
    /** 
     * 更新数据
     *
     * @param accountFlow 实例对象
     * @return 实例对象
     */
    AccountFlow update(AccountFlowParam accountFlow);
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

     /**
      * 账户充值流水
      * @param accountFlow
      * @return
      */
     ResponseData addFlow(AccountFlowParam accountFlow);


 }