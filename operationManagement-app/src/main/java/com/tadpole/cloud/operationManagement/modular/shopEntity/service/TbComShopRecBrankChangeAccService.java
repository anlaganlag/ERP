package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopRecBrankChangeAcc;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopRecBrankChangeAccParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopRecBrankChangeAccResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-店铺收款银行账号变更;(Tb_Com_Shop_Rec_Brank_Change_Acc)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopRecBrankChangeAccService extends IService<TbComShopRecBrankChangeAcc> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    TbComShopRecBrankChangeAcc queryById(BigDecimal sysId);
    
    /**
     * 分页查询
     *
     * @param tbComShopRecBrankChangeAcc 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopRecBrankChangeAccResult> paginQuery(TbComShopRecBrankChangeAccParam tbComShopRecBrankChangeAcc, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopRecBrankChangeAcc 实例对象
     * @return 实例对象
     */
    TbComShopRecBrankChangeAcc insert(TbComShopRecBrankChangeAcc tbComShopRecBrankChangeAcc);
    /** 
     * 更新数据
     *
     * @param tbComShopRecBrankChangeAcc 实例对象
     * @return 实例对象
     */
    TbComShopRecBrankChangeAcc update(TbComShopRecBrankChangeAcc tbComShopRecBrankChangeAcc);
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysIdList);

     /**
      * 根据收款账号变更申请记录ID 更新状态为已接受
      * @param sysId
      * @return
      */
     ResponseData updateReceive(BigDecimal sysId);

     /**
      * 根据收款账号变更申请记录ID 更新状态为完成
      * @param sysId
      * @return
      */
     ResponseData updateFinish(BigDecimal sysId);
 }