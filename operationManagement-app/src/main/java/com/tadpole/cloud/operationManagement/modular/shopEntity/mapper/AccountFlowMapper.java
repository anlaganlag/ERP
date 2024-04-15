package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountFlow;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountFlowResult;

 /**
 * 账户流水;(ACCOUNT_FLOW)表数据库访问层
 * @author : LSY
 * @date : 2023-11-10
 */
@Mapper
public interface AccountFlowMapper  extends BaseMapper<AccountFlow>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<AccountFlowResult> selectByPage(IPage<AccountFlowResult> page , @Param(Constants.WRAPPER) Wrapper<AccountFlow> wrapper);
}