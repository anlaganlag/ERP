package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.LogisticsStorageTimedTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
@Slf4j
public class LogisticsStorageTimedTaskServiceImpl implements LogisticsStorageTimedTaskService {

    @Resource
    LogisticsStorageTimedTaskMapper logisticsStorageTimedTaskMapper;

    @Resource
    TbBscOverseasWayAnalysisNewV2Mapper tbBscOverseasWayAnalysisNewV2Mapper;

    @Resource
    TbAmazonInGoodsQtyNewV2Mapper tbAmazonInGoodsQtyNewV2Mapper;

    @Resource
    private TbReceivedInvenrotyAnalysisV2Mapper tbReceivedInvenrotyAnalysisV2Mapper;

    @Resource
    private TbLogisticsPackListDetMapper tbLogisticsPackListDetMapper;

    @Resource
    private TbLogisticsPackListBoxRecDetMapper tbLogisticsPackListBoxRecDetMapper;

    @Resource
    private TbBscOverseasWayMapper tbBscOverseasWayMapper;

    @Resource
    private TbLogisticsPackListMapper tbLogisticsPackListMapper;

    @Resource
    private TbLogisticsPackListBoxRecMapper tbLogisticsPackListBoxRecMapper;
    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData analysisAmazonInGoodsQtyNewV4() {

        //TbBscOverseasWayAnalysisNewV2 删除 数据
        tbBscOverseasWayAnalysisNewV2Mapper.truncateTableTbReceivedInvenrotyAnalysis();

        //TbAmazonInGoodsQtyNewV2 删除 数据
        tbAmazonInGoodsQtyNewV2Mapper.truncateTableTbAmazonInGoodsQtyNewV2();

        // 生成 发货单数据
        tbBscOverseasWayAnalysisNewV2Mapper.initSendData();

        //生成 Amazon在途库存报表
        tbAmazonInGoodsQtyNewV2Mapper.initOnTheWayData();
        return ResponseData.success();
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData syncTbReceivedInvenrotyAnalysisV2() {

        tbReceivedInvenrotyAnalysisV2Mapper.truncateTableTbReceivedInvenrotyAnalysisV2();

        tbReceivedInvenrotyAnalysisV2Mapper.syncTbReceivedInvenrotyAnalysisV2();
        return ResponseData.success();
    }

    @DataSource(name = "logistics")
    @Override
    @Transactional
    public ResponseData updateBscOverseasWayShipmentStatusNew() {

        //清空表 Tb_Bsc_Overseas_Way_Ship_Status_Analysis_New
        tbAmazonInGoodsQtyNewV2Mapper.truncateTableTbBscOverseasWayShipStatusAnalysisNew();
        //初始化实际货件状态数据(已完成) 数据  Tb_Bsc_Overseas_Way_Ship_Status_Analysis_New
        tbAmazonInGoodsQtyNewV2Mapper.initFinishData();
        // 更新PackList明细实际货物状态
        tbLogisticsPackListDetMapper.updateShipmentRealStatus();
        //更新PackList明细实际货物状态
        tbLogisticsPackListBoxRecDetMapper.updateShipmentRealStatus();
        //更新发货数据实际货物状态
        tbBscOverseasWayMapper.updateShipmentRealStatus();
        //更新PackList实际货物状态
        tbLogisticsPackListMapper.updateShipmentRealStatus();
        //更新出货清单实际货物状态
        tbLogisticsPackListBoxRecMapper.updateShipmentRealStatus();

        return ResponseData.success();
    }

    @DataSource(name = "logistics")
    @Override
    @Transactional
    public ResponseData updateBscOverseasWayStatus() {

        tbBscOverseasWayMapper.updateBscOverseasWayStatus();
        return ResponseData.success();
    }


}
