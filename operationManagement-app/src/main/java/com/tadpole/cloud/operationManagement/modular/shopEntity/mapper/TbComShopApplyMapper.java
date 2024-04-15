package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApply;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 资源-店铺申请;(Tb_Com_Shop_Apply)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComShopApplyMapper  extends MPJBaseMapper<TbComShopApply> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComShopApplyResult> selectByPage(IPage<TbComShopApplyResult> page , @Param(Constants.WRAPPER) Wrapper<TbComShopApply> wrapper);

     /**
      * 更新店铺申请表的状态
       */ 
     @Update(" UPDATE TB_COM_SHOP_APPLY a SET a.SYS_APPLY_STATE = 3 " +
             " WHERE a.SYS_APPLY_STATE = 2 AND a.SYS_APPLY_ID NOT IN ( " +
             " SELECT DISTINCT t1.SYS_APPLY_ID FROM Tb_Com_Shop_Apply_Det t1 " +
                        " LEFT JOIN Tb_Com_Shop_Apply_Bank_Collect_Task t2 ON (t2.sys_Apply_Det_Id = t1.sys_Apply_Det_Id) " +
                        " LEFT JOIN Tb_Com_Shop_Apply_Taxn_Task t3 ON (t3.sys_Apply_Det_Id = t1.sys_Apply_Det_Id) " +
                        " WHERE t2.SYS_APPLY_DET_BCT_STATE = '未完成' OR t3.TAXN_TASK_STATE = '未完成')")
    void updateShopApplyState();
     
 }