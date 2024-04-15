package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToHeadRouteResult;

 /**
 * 物流单头程信息;(tb_logistics_list_to_head_route)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsListToHeadRouteMapper  extends MPJBaseMapper<TbLogisticsListToHeadRoute> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsListToHeadRouteResult> selectByPage(IPage<TbLogisticsListToHeadRouteResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsListToHeadRoute> wrapper);
}