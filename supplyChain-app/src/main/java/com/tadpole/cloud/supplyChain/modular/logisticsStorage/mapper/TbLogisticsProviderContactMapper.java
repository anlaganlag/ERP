package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProviderContact;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderContactResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流供应商联系信息;(tb_logistics_provider_contact)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsProviderContactMapper  extends BaseMapper<TbLogisticsProviderContact>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsProviderContactResult> selectByPage(IPage<TbLogisticsProviderContactResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsProviderContact> wrapper);

    /**
     * 从金蝶k3获取供应商信息
     * @return
     */
    List<TbLogisticsProvider> getLogisticsProvider();

    /**
     * 从金蝶k3获取供应商联系人信息
     * @return
     */
    List<TbLogisticsProviderContact> getLogisticsProviderContact();
}