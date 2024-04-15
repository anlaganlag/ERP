package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyBankCollectTaskResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyBankCollectTask;

 /**
 * 资源-店铺申请银行收款任务;(Tb_Com_Shop_Apply_Bank_Collect_Task)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComShopApplyBankCollectTaskMapper  extends BaseMapper<TbComShopApplyBankCollectTask>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComShopApplyBankCollectTaskResult> selectByPage(IPage<TbComShopApplyBankCollectTaskResult> page , @Param(Constants.WRAPPER) Wrapper<TbComShopApplyBankCollectTask> wrapper);
}