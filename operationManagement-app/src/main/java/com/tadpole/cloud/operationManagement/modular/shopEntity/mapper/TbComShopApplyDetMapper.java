package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyDet;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyDetResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资源-店铺申请详情表;(Tb_Com_Shop_Apply_Det)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComShopApplyDetMapper  extends MPJBaseMapper<TbComShopApplyDet> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComShopApplyDetResult> selectByPage(IPage<TbComShopApplyDetResult> page , @Param(Constants.WRAPPER) Wrapper<TbComShopApplyDet> wrapper);

 }