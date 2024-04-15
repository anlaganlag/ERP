package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToEndRouteResult;

 /**
 * 物流单尾程信息;(tb_logistics_list_to_end_route)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsListToEndRouteMapper  extends BaseMapper<TbLogisticsListToEndRoute>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsListToEndRouteResult> selectByPage(IPage<TbLogisticsListToEndRouteResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsListToEndRoute> wrapper);
}