package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.SysDict;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.DictServiceConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3TransferMabangSummaryService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISyncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant.SYNC_K3_AVAILABLE_QTY_TO_MABANG;

/**
 * <p>
 * K3调拨单同步概要 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2022-06-15
 */
@RestController
@ApiResource(name = "k3调拨单同步马帮概要", path = "/k3TransferMabangSummary")
@Api(tags = "k3调拨单同步马帮概要")
@Slf4j
public class K3TransferMabangSummaryController {

    @Resource
    private IK3TransferMabangSummaryService service;

    @Resource
    private ISyncService syncService;

    @Resource
    private DictServiceConsumer dictServiceConsumer;

    @Resource
    private RedisTemplate redisTemplate;


    @PostResource(name = "同步k3所有调拨单到马帮", path = "/syncAllTransfer", requiredPermission = false)
    @ApiOperation(value = "同步k3所有调拨单到马帮", response = ResponseData.class)
    public ResponseData syncAllTransfer() {
        return syncService.syncToManbang();
    }


    @GetResource(name = "k3直接调拨单反审核查询", path = "/anitAudit", requiredPermission = false)
    @ApiOperation(value = "k3直接调拨单反审核查询", response = ResponseData.class)
    public ResponseData anitAudit(@RequestParam String billNo) {
        return syncService.anitAudit(billNo);
    }


    @GetResource(name = "k3指定仓库所有物料可用数量-销售出库到马帮erp", path = "/syncAllMatAvailableQty", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "k3指定仓库所有物料可用数量-销售出库到马帮erp", response = ResponseData.class)
    public ResponseData syncAllMatAvailableQty(@RequestParam(required = false) List<String> warehouseList) {
        // 同步k3可用库存数量到马帮  可能冲突 需要加锁来排除冲突
        Object shipmentTrackingKey = redisTemplate.opsForValue().get(SYNC_K3_AVAILABLE_QTY_TO_MABANG);
        if (ObjectUtil.isNotNull(shipmentTrackingKey)) {
            return ResponseData.success("已有任务在执行-【同步k3可用库存到马帮】，本次请求结束！");
        }

        redisTemplate.boundValueOps(SYNC_K3_AVAILABLE_QTY_TO_MABANG)
                .set(SYNC_K3_AVAILABLE_QTY_TO_MABANG, Duration.ofMinutes(5));
        log.info("同步k3可用库存数量到马帮--执行开始：【{}】",new Date());
        if (ObjectUtil.isEmpty(warehouseList)) {
            //没有指定需要同步的仓库 则读取数据字段 已启用的仓库
            //获取发货字典>>交货地点>>parentId
            List<SysDict> warehouseListDic = dictServiceConsumer.getDictListByTypeCode("PlatformDevelopERP");
            Long parentId = null;
            try {
                parentId = warehouseListDic.stream().filter(dict -> dict.getDictCode().equals("KCTB")).collect(Collectors.toList()).get(0).getDictId();
            } catch (Exception e) {
                throw new RuntimeException("请先配置平台发展ERP>>K3库存同步仓库列表(KCTB)");
            }

            Long finalParentId = parentId;
            List<SysDict> dictList = warehouseListDic.stream().filter(dict -> dict.getParentId().equals(finalParentId) && dict.getStatus() == 1).collect(Collectors.toList());
            if (ObjectUtil.isEmpty(dictList)) {
                return ResponseData.error("未找到需要同步k3仓库库存信息的字典配置(启用状态的)");
            }
            warehouseList = dictList.stream().map(d -> d.getDictName()).collect(Collectors.toList());

        }

        try {
            ResponseData responseData = syncService.syncAllMatAvailableQty(warehouseList);
            log.info("同步k3可用库存数量到马帮--执行结束：【{}】",new Date());
            return responseData;
        } catch (Exception e) {
            log.error("同步k3可用库存数量到马帮执行异常：[" + e.getMessage() + "]");
            return ResponseData.error("同步k3可用库存数量到马帮执行异常：[" + e.getMessage() + "]");
        }finally {
            redisTemplate.delete(SYNC_K3_AVAILABLE_QTY_TO_MABANG);
        }
    }


    @GetResource(name = "更新物料库存核算价", path = "/updateMatStockPrice", requiredPermission = false)
    @ApiOperation(value = "更新物料库存核算价", response = ResponseData.class)
    public ResponseData updateMatStockPrice(Integer fallbackMonths) {
        return syncService.updateMatStockPrice(fallbackMonths);
    }

}
