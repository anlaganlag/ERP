package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 物流供应商;(tb_logistics_provider)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsProviderMapper  extends BaseMapper<TbLogisticsProvider>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsProviderResult> selectByPage(IPage<TbLogisticsProviderResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsProvider> wrapper);

    @Select(" select distinct lp_code from tb_logistics_provider where  lp_code is not NULL ")
    List<String> lpCodeList();
    @Select(" select distinct lp_name from tb_logistics_provider where  lp_name is not NULL ")
    List<String> lpNameList();
    @Select(" select distinct lp_simple_name from tb_logistics_provider where  lp_simple_name is not NULL ")
    List<String> lpSimpleNameList();
}