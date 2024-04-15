package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferMabangSummary;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3TransferItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3TransferMabangSummaryMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * K3调拨单同步概要 服务实现类
*
* @author lsy
* @since 2022-06-15
*/
@Slf4j
@Service
public class K3TransferMabangSummaryServiceImpl extends ServiceImpl<K3TransferMabangSummaryMapper, K3TransferMabangSummary> implements IK3TransferMabangSummaryService {


    @Resource
    K3TransferItemMapper k3TransferItemMapper;

    @Resource
    K3TransferMabangSummaryMapper mapper;

    @Autowired
    private IK3TransferItemService k3TransferItemService;

    @Autowired
    IMabangRequstService mabangRequstService;

    @Autowired
    IMabangAddPurchaseOrderService purchaseOrderService;

    @Autowired
    IMabangAllocationWarehouseService allocationWarehouseService;

    @Autowired
    IMabangWarehouseService mabangWarehouseService;

    @Autowired
    @Lazy
    private IK3TransferMabangSummaryService summaryService;




    /**
     * 1-》直接调拨单  正常--同步-->马帮：
     *     1.1 直接调拨单 按照调拨方向（小平台入库，小平台出库）分组调拨单明细
     *
     *      1.1.1  入库（调拨单的<入仓>入仓>是小平台仓库）->走马帮  新增采购单
     *          1.1.1.1  按照入仓字段分组数据，（如果入仓是小平台仓库两个或者多个仓库）则新增对应仓库个数的 采购单 并调用马帮《新增采购单》接口推送数据
     *                      生成同步记录数据  （如果当前直接调拨单，含有出库和入库操作，定义为混合业务调拨单，打上标识，则不允许反审核操作）
     *                      生成同步记录明细数据
     *
     *      1.1.2  出库（调拨单的<出仓>是小平台仓库）->走马帮的 创建分仓调拨单
     *          1.1.2.1  按照出仓字段分组数据，（如果出仓是小平台仓库两个或者多个仓库）则创建对应仓库个数的 分仓调拨单 并调用马帮《创建分仓调拨》接口推送数据
     *                      生成分仓调拨单数据
     *
     *
     */

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)

    public K3TransferMabangSummary doSync(Map<String, String> mabangWarehouse, List<K3TransferItem> itemList) {

        //判断调拨单是否已经同步 ，判断依据（调拨单号+审核通过时间）相同 ------>  数据优化
        K3TransferItem k3Item = itemList.get(0);
        Date approveDate = k3Item.getApproveDate();
        String billNo = k3Item.getBillNo();
        LambdaQueryWrapper<K3TransferMabangSummary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(K3TransferMabangSummary::getBillNo, billNo);
        queryWrapper.eq(K3TransferMabangSummary::getApproveDate, approveDate);
//        queryWrapper.eq(K3TransferMabangSummary::getSyncStatus, MabangConstant.SYNC_SUCCESS);
        List<K3TransferMabangSummary> summaryList = mapper.selectList(queryWrapper);
        if (summaryList.size()>0) {
//            （调拨单号+审核通过时间）相同 不需要同步
            return null;
        }

        //未同步的数据开始同步处理

        int transferDirection =0;  //0:其他仓库-->小平台仓库（入库增加库存），1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存)，2：包含前两种的混合业务（调拨单里面既有入库，又有出库操作的业务）
        int splitOrder=0;//分拆  新增采购订单 总数量
        int splitWarehou=0;//分拆 创建分仓订单 总数量
        int splitTotal=0;// 总的分拆数量 splitOrder + splitWarehou

        if (ObjectUtil.isEmpty(itemList)) {
            return null;
        }

        List<K3TransferItem> mabangIn = new ArrayList<>();
        List<K3TransferItem> mabangOut = new ArrayList<>();

        for (K3TransferItem item : itemList) {
//                此处不考虑出仓和入仓都是属于马帮的情况

            //调入 仓库是属于马帮的仓库
            if (mabangWarehouse.containsKey(item.getDeststockCode())) {
                mabangIn.add(item);
            }
            //调出 仓库是属于马帮的仓库
            if (mabangWarehouse.containsKey(item.getSrcstockCode())) {
                mabangOut.add(item);
            }
        }
        //概要数据ID
        String summaryId = IdWorker.getIdStr();



        K3TransferMabangSummary summary = BeanUtil.copyProperties(k3Item, K3TransferMabangSummary.class);
        Date date = new Date();
        summary.setId(summaryId);
        summary.setSplitTotalAllocationWarehou(BigDecimal.ZERO);
        summary.setSplitTotalPurchaseOrder(BigDecimal.ZERO);
        summary.setSplitTotal(BigDecimal.ZERO);

        //判断是否属于混合业务类型
        if (mabangIn.size()>0 && mabangOut.size()>0) {
            transferDirection=2;
            summary.setMixedBusiness(MabangConstant.TRANSFER_DIRECTION_OUT);
        }

        summary.setTransferDirection(transferDirection);
        summary.setSyncStatus(MabangConstant.SYNC_FAIL);
        summary.setSyncTime(date);
        summary.setCreateTime(date);

        mapper.insert(summary);

        String exceptionMsg="";//是否异常
        boolean purchaseOrderException=false;//是否异常
        boolean allocationWarehouseException=false;//是否异常

        //入库操作
        if (ObjectUtil.isNotEmpty(mabangIn)) {
            ResponseData resultCreat =  purchaseOrderService.add(summaryId,mabangIn);
            splitOrder=(Integer)resultCreat.getData();
            if (! resultCreat.getSuccess()) {
                exceptionMsg = exceptionMsg + resultCreat.getMessage();
                purchaseOrderException=true;
            }
        }

        //出库操作
        if (ObjectUtil.isNotEmpty(mabangOut)) {
            transferDirection=1;
            ResponseData resultCreat = allocationWarehouseService.create(summaryId,mabangOut);
            splitWarehou=(Integer)resultCreat.getData();
            if (! resultCreat.getSuccess()) {
                exceptionMsg = exceptionMsg + resultCreat.getMessage();
                allocationWarehouseException=true;
            }
        }


        splitTotal=splitOrder+splitWarehou;
        //创建同步概要数据

        summary.setSplitTotalAllocationWarehou(BigDecimal.valueOf(splitWarehou));
        summary.setSplitTotalPurchaseOrder(BigDecimal.valueOf(splitOrder));
        summary.setSplitTotal(BigDecimal.valueOf(splitTotal));

        //判断是否属于混合业务类型
        if (mabangIn.size()>0 && mabangOut.size()>0) {
            transferDirection=2;
            summary.setMixedBusiness(MabangConstant.TRANSFER_DIRECTION_OUT);
        }

        summary.setTransferDirection(transferDirection);

        //有异常的情况
        if (purchaseOrderException || allocationWarehouseException) {
            summary.setSyncResultMsg(exceptionMsg);
            summary.setSyncTime(new Date());
        }else {
            //无异常情况
            summary.setSyncStatus(MabangConstant.SYNC_SUCCESS);
            summary.setSyncResultMsg("success");
        }
        summary.setSyncTime(new Date());
        summary.setUpdateTime(new Date());

        mapper.updateById(summary);

        log.info("k3直接调拨单{}同步成功,调拨单业务类型为:{},总拆单数量:{},采购订单拆分数量:{}，分仓调拨单拆分数量:{},同步时间:{}"
                ,k3Item.getBillNo(),summary.getMixedBusiness(),splitTotal,splitOrder,splitWarehou,date);
        return summary;
    }




    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)

    public K3TransferMabangSummary doSync2(Map<String, String> mabangWarehouse, List<K3TransferItem> itemList) {
        int transferDirection =0;  //0:其他仓库-->小平台仓库（入库增加库存），1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存)，2：包含前两种的混合业务（调拨单里面既有入库，又有出库操作的业务）
        int splitOrder=0;//分拆  新增采购订单 总数量
        int splitWarehou=0;//分拆 创建分仓订单 总数量
        int splitTotal=0;// 总的分拆数量 splitOrder + splitWarehou

        if (ObjectUtil.isEmpty(itemList)) {
            return null;
        }

        List<K3TransferItem> mabangIn = new ArrayList<>();
        List<K3TransferItem> mabangOut = new ArrayList<>();

        for (K3TransferItem item : itemList) {
//                此处不考虑出仓和入仓都是属于马帮的情况

            //调入 仓库是属于马帮的仓库
            if (mabangWarehouse.containsKey(item.getDeststockCode())) {
                mabangIn.add(item);
            }
            //调出 仓库是属于马帮的仓库
            if (mabangWarehouse.containsKey(item.getSrcstockCode())) {
                mabangOut.add(item);
            }
        }

        //概要数据ID
        String summaryId = IdWorker.getIdStr();


        //入库操作
        if (ObjectUtil.isNotEmpty(mabangIn)) {
//            splitOrder = purchaseOrderService.add(summaryId,mabangIn);
        }

        //出库操作
        if (ObjectUtil.isNotEmpty(mabangOut)) {
            transferDirection=1;
//            splitWarehou = allocationWarehouseService.create(summaryId,mabangOut);
        }

        K3TransferItem item = itemList.get(0);
        splitTotal=splitOrder+splitWarehou;
        //创建同步概要数据
        K3TransferMabangSummary summary = BeanUtil.copyProperties(item, K3TransferMabangSummary.class);
        Date date = new Date();
        summary.setId(summaryId);
        summary.setSplitTotalAllocationWarehou(BigDecimal.valueOf(splitWarehou));
        summary.setSplitTotalPurchaseOrder(BigDecimal.valueOf(splitOrder));
        summary.setSplitTotal(BigDecimal.valueOf(splitTotal));

        //判断是否属于混合业务类型
        if (mabangIn.size()>0 && mabangOut.size()>0) {
            transferDirection=2;
            summary.setMixedBusiness(MabangConstant.TRANSFER_DIRECTION_OUT);
        }

        summary.setTransferDirection(transferDirection);
        summary.setSyncStatus(MabangConstant.SYNC_SUCCESS);
        summary.setSyncTime(date);
        summary.setCreateTime(date);

        mapper.insert(summary);

        log.info("k3直接调拨单{}同步成功,调拨单业务类型为:{},总拆单数量:{},采购订单拆分数量:{}，分仓调拨单拆分数量:{},同步时间:{}"
                ,item.getBillNo(),summary.getMixedBusiness(),splitTotal,splitOrder,splitWarehou,date);
        return summary;
    }


    /**
     * 查出k3系统马帮专属仓库信息
     * @return
     */
    @DataSource(name = "k3cloud")
    @Override

    public Map<String, String> getK3MabangWarehouse() {

        //查出k3系统马帮专属仓库信息
        List<Map<String, String>> warehouseMap = k3TransferItemMapper.k3MabangWarehouse();
        Map<String, String> map = new HashMap<>();
        for (Map<String, String> warehouse : warehouseMap) {
            map.put(warehouse.get("FNUMBER"), warehouse.get("FNAME"));
        }
        return map;
    }


    /**
     ——————————————————————————————————————————<2->反审核查询接口 入参：直接调拨单号AAA>————————————————————————————————————————————————————————————————————————————————————————
     * 直接调拨单 反审核查询接口(提供给k3系统在反审核时查询属于马帮的直接调拨单是否允许反审核)   入参：直接调拨单号AAA
     *     1    查询mcms同步记录 是否已经同步了 直接调拨单号AAA
     *        1.1  未同步直接调拨单AAA  ，直接返回 true  允许反审核
     *
     *        1.2  已经同步了直接调拨单号AAA  如果该直接调拨单 在第一次同步时是属于《混合业务》  则 返回false  不允许反审核
     *
     *          1.2.1  直接调拨单号AAA第一次同步时 都是入库操作
     *                  AAA调拨单入仓可能是多个采购单（采购单的入仓仓库不一样）
     *                  根据每个新增的采购订单号，查询采购订单状态，只要任何一个采购订单状态 flag>=5  则 返回false  不允许反审核
     *                  所有采购订单状态flag <5 根据每个采购订单  作废采购订单 当所有采购订单作废  返回 true  允许反审核
     *
     *          1.2.2  直接调拨单号AAA第一次同步时 都是出库操作
     *                        AAA调拨单出库可能是多分仓调拨单（分仓调拨单的起始仓库不一样）
     *                        根据每个分仓调拨单的信息 从新创建一个新的分仓调拨单  和第一次的分仓调拨单信息起始仓库ID和目标仓库ID对调  在调创建分仓调拨接口
     *                        当所有分仓调拨单都创建成功后  返回 true  允许反审核
     *
     * @param billNo
     * @return
     */
    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData anitAudit(String billNo) {

        LambdaQueryWrapper<K3TransferMabangSummary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(K3TransferMabangSummary::getBillNo,billNo);

        List<K3TransferMabangSummary> summaryList = mapper.selectList(queryWrapper);
        //从未同步过的单 可以允许反审核
        if (ObjectUtil.isEmpty(summaryList)) {
            return ResponseData.success(200,"未找到直接调拨单号，允许反审核",true);
        }

        if (summaryList.stream().anyMatch(s ->MabangConstant.SYNC_FAIL.equals(s.getSyncStatus()))) {
            return ResponseData.success(200,"该直接调拨单【"+billNo+"】在同步到马帮erp系统时存在异常，需相关人员核实排查，不允许反审核",false);
        }


        //能找到同步记录，并且该记录还未触发反审核操作，数据库只有一条这样 的记录
        if (summaryList.stream().anyMatch(s -> s.getAntiAudit()==MabangConstant.ANTI_AUDIT_UN)) {
            //当前同步的时候 业务是不是混合业务
            Optional<K3TransferMabangSummary> summaryOptional = summaryList.stream().filter(s -> s.getAntiAudit() == MabangConstant.ANTI_AUDIT_UN).findFirst();
            if (summaryOptional.isPresent()) {

                K3TransferMabangSummary summary = summaryOptional.get();
                ResponseData responseData = null;
                if (MabangConstant.MIXED_BUSINESS_YES==summary.getMixedBusiness()) {
                    return ResponseData.success(200, "调拨单属于混合业务不允许反审核", false);
                }

                    //新增采购单业务
                if (MabangConstant.TRANSFER_DIRECTION_IN == summary.getTransferDirection()) {
                    responseData=  purchaseOrderService.anitAudit(summary);
                    summary.setAntiAuditAction(MabangConstant.ANTI_AUDIT_ACTION_PURCHASE_ORDER_DEL);
                }


                    //分仓调拨单业务
                if (MabangConstant.TRANSFER_DIRECTION_OUT == summary.getTransferDirection()) {
                    responseData=   allocationWarehouseService.anitAudit(summary);
                    summary.setAntiAuditAction(MabangConstant.ANTI_AUDIT_ACTION_REVERSE_ALLOCATION);
                }


                summary.setAntiAudit(MabangConstant.ANTI_AUDIT_DO);
                summary.setUpdateTime(new Date());
                mapper.updateById(summary);
                return responseData;

            }
        }
        return ResponseData.success(200,"之前同步的记录已经触发了反审核，且通过了，本次允许反审核",true);
    }



}
