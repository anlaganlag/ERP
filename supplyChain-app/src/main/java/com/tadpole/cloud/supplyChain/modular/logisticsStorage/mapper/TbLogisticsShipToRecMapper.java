package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipToRec;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipToRecResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 平台货件接收地址;(tb_logistics_ship_to_rec)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsShipToRecMapper  extends BaseMapper<TbLogisticsShipToRec>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsShipToRecResult> selectByPage(IPage<TbLogisticsShipToRecResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsShipToRec> wrapper);

    @Select("select distinct log_rec_house_name from  tb_logistics_ship_to_rec where log_rec_house_name is not null ")
    List<String> logRecHouseNameList();
}