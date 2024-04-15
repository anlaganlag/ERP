package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRouteDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToEndRouteDetResult;

 /**
 * 物流单尾程信息-明细;(tb_logistics_list_to_end_route_det)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsListToEndRouteDetMapper  extends BaseMapper<TbLogisticsListToEndRouteDet>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsListToEndRouteDetResult> selectByPage(IPage<TbLogisticsListToEndRouteDetResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsListToEndRouteDet> wrapper);
}