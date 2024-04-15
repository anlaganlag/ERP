package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbReceivedInvenrotyAnalysisV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbReceivedInvenrotyAnalysisV2Result;
import org.apache.ibatis.annotations.Update;

/**
 * 来货报告;(Tb_Received_Invenroty_Analysis_V2)表数据库访问层
 * @author : LSY
 * @date : 2024-3-18
 */
@Mapper
public interface TbReceivedInvenrotyAnalysisV2Mapper  extends BaseMapper<TbReceivedInvenrotyAnalysisV2>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbReceivedInvenrotyAnalysisV2Result> selectByPage(IPage<TbReceivedInvenrotyAnalysisV2Result> page , @Param(Constants.WRAPPER) Wrapper<TbReceivedInvenrotyAnalysisV2> wrapper);

     void syncTbReceivedInvenrotyAnalysisV2();

     @Update("truncate table Tb_Received_Invenroty_Analysis_V2")
     void truncateTableTbReceivedInvenrotyAnalysisV2();

 }