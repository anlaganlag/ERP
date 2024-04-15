package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbBscOverseasWayAnalysisNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbBscOverseasWayAnalysisNewV2Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * 发货单数据;(Tb_Bsc_Overseas_Way_Analysis_New_V2)表数据库访问层
 * @author : LSY
 * @date : 2024-3-18
 */
@Mapper
public interface TbBscOverseasWayAnalysisNewV2Mapper  extends BaseMapper<TbBscOverseasWayAnalysisNewV2>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbBscOverseasWayAnalysisNewV2Result> selectByPage(IPage<TbBscOverseasWayAnalysisNewV2Result> page , @Param(Constants.WRAPPER) Wrapper<TbBscOverseasWayAnalysisNewV2> wrapper);

    @Update("TRUNCATE TABLE Tb_bsc_overseas_way_analysis_new_v2")
     void truncateTableTbReceivedInvenrotyAnalysis();


    void initSendData();
}