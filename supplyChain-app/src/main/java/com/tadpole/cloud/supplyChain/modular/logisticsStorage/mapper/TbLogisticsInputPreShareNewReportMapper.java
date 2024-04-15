package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsInputPreShareNewReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsInputPreShareNewReportResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物流投入预估分担报告-新-暂时不需要;(tb_logistics_input_pre_share_new_report)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsInputPreShareNewReportMapper  extends BaseMapper<TbLogisticsInputPreShareNewReport>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsInputPreShareNewReportResult> selectByPage(IPage<TbLogisticsInputPreShareNewReportResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsInputPreShareNewReport> wrapper);
}