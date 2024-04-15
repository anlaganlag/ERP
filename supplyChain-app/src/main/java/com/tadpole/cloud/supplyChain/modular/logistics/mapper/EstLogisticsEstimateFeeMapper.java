package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFee;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.EstLogisticsEstimateFeeResult;

 /**
 * 物流费用测算;(EST_LOGISTICS_ESTIMATE_FEE)表数据库访问层
 * @author : LSY
 * @date : 2024-3-14
 */
@Mapper
public interface EstLogisticsEstimateFeeMapper  extends MPJBaseMapper<EstLogisticsEstimateFee> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<EstLogisticsEstimateFeeResult> selectByPage(IPage<EstLogisticsEstimateFeeResult> page , @Param(Constants.WRAPPER) Wrapper<EstLogisticsEstimateFee> wrapper);
}