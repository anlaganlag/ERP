package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbPlatformAccoSiteCode;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbPlatformAccoSiteCodeResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

 /**
 * 资源-平台-账号-站点-对应编码配置;(Tb_Platform_Acco_Site_Code)--数据库访问层
 * @author : LSY
 * @date : 2023-8-3
 */
@Mapper
public interface TbPlatformAccoSiteCodeMapper  extends BaseMapper<TbPlatformAccoSiteCode>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbPlatformAccoSiteCodeResult> selectByPage(IPage<TbPlatformAccoSiteCodeResult> page , @Param(Constants.WRAPPER) Wrapper<TbPlatformAccoSiteCode> wrapper);
}