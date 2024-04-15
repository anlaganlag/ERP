package com.tadpole.cloud.externalSystem.modular.ebms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwTaskAutoCreate;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.MarkIdEndPointResult;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.PlatSiteMarkIdResult;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwTaskAutoCreateResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TbDwTaskAutoCreate;(Tb_DW_Task_Auto_Create)--数据库访问层
 * @author : LSY
 * @date : 2023-8-14
 */
@Mapper
public interface TbDwTaskAutoCreateMapper  extends BaseMapper<TbDwTaskAutoCreate>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbDwTaskAutoCreateResult> selectByPage(IPage<TbDwTaskAutoCreateResult> page , @Param(Constants.WRAPPER) Wrapper<TbDwTaskAutoCreate> wrapper);
     @Select("select m.country,m.marketplaceId,m.countryCode,m.region,m.url,e.endpoint,e.aws_region,e.remark " +
             "FROM  TbShopMarketplace m join TbShopEndpoints e on m.region =e.region where m.countryCode = #{countryCode}")
     MarkIdEndPointResult getMarkIdEndPointByCountryCode( String countryCode);



     List<PlatSiteMarkIdResult> getMarketplaceIdByPlatformSite(String platform, String site);
 }