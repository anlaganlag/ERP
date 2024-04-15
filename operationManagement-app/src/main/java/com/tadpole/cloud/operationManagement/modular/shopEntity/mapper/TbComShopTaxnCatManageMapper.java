package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopTaxnCatManageResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopTaxnCatManage;

 /**
 * 资源-税号类别管理;(Tb_Com_Shop_Taxn_Cat_Manage)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComShopTaxnCatManageMapper  extends BaseMapper<TbComShopTaxnCatManage>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComShopTaxnCatManageResult> selectByPage(IPage<TbComShopTaxnCatManageResult> page , @Param(Constants.WRAPPER) Wrapper<TbComShopTaxnCatManage> wrapper);
}