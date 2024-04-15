package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbComOverseasWarehouse;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbComOverseasWarehouseResult;

 /**
 * 海外仓信息;(Tb_Com_Overseas_Warehouse)表数据库访问层
 * @author : LSY
 * @date : 2024-1-19
 */
@Mapper
public interface TbComOverseasWarehouseMapper  extends BaseMapper<TbComOverseasWarehouse>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComOverseasWarehouseResult> selectByPage(IPage<TbComOverseasWarehouseResult> page , @Param(Constants.WRAPPER) Wrapper<TbComOverseasWarehouse> wrapper);
}