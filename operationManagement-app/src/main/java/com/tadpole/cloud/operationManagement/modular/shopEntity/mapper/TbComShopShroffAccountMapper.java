package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopShroffAccountResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopShroffAccount;

 /**
 * 资源-信用卡账号管理;(Tb_Com_Shop_Shroff_Account)--数据库访问层
 * @author : LSY
 * @date : 2023-8-3
 */
@Mapper
public interface TbComShopShroffAccountMapper  extends BaseMapper<TbComShopShroffAccount>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComShopShroffAccountResult> selectByPage(IPage<TbComShopShroffAccountResult> page , @Param(Constants.WRAPPER) Wrapper<TbComShopShroffAccount> wrapper);
}