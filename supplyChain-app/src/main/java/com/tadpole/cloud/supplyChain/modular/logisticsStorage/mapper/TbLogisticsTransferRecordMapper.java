package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsTransferRecord;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.ExportDirectTransfersOrderResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsTransferRecordResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流调拨记录;(tb_logistics_transfer_record)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsTransferRecordMapper  extends MPJBaseMapper<TbLogisticsTransferRecord> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsTransferRecordResult> selectByPage(IPage<TbLogisticsTransferRecordResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsTransferRecord> wrapper);

    List<ExportDirectTransfersOrderResult> export(List<String> packCodeList);
}