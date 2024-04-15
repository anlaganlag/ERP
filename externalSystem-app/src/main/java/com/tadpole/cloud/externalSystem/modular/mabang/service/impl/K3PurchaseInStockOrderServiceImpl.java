package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3PurchaseInStockOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3PurchaseInStockOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3PurchaseInStockOrderMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3PurchaseOrderInStockParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3PurchaseInStockOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3PurchaseInStockOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3PurchaseInStockOrderItemService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3PurchaseInStockOrderService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrdersService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * K3采购入库单 服务实现类
 * </p>
 *
 * @author S20190161
 * @since 2023-05-17
 */
@Service
@Slf4j
public class K3PurchaseInStockOrderServiceImpl extends ServiceImpl<K3PurchaseInStockOrderMapper, K3PurchaseInStockOrder> implements IK3PurchaseInStockOrderService {

    @Resource
    private K3PurchaseInStockOrderMapper mapper;

    @Autowired
    private IMabangOrdersService mabangOrdersService;

    @Autowired
    private IK3PurchaseInStockOrderItemService itemService;


    @Autowired
    private SyncServiceImpl syncService;

    @DataSource(name = "external")
    @Override
    public PageResult<K3PurchaseInStockOrderResult> findPageBySpec(K3PurchaseInStockOrderParam param) {
        Page pageContext = PageFactory.defaultPage();
        IPage<K3PurchaseInStockOrderResult> page = mapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);


    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData createPurchaseInStock() {
        MabangOrdersParam param = new MabangOrdersParam();
        param.setShopName("平台发展");
        List<MabangOrdersResult> mabangOrderList = mabangOrdersService.getWaitCreatePurchaseOrder(param);
        if (ObjectUtil.isEmpty(mabangOrderList)) {
            log.info("未找到需要创建k3采购入库单的 马帮销售订单信息");
            return ResponseData.success("未找到需要创建k3采购入库单的 马帮销售订单信息");
        }

        Map<String, List<MabangOrdersResult>> platformOrderIdMap = mabangOrderList.stream().collect(Collectors.groupingBy(MabangOrdersResult::getPlatformOrderId));
        for (Map.Entry<String, List<MabangOrdersResult>> entry : platformOrderIdMap.entrySet()) {
            List<MabangOrdersResult> ordersResultList = entry.getValue();
            if (ObjectUtil.isEmpty(ordersResultList)) {
                continue;
            }
            this.createPurchaseInStock(ordersResultList);
        }
        return ResponseData.success();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public boolean createPurchaseInStock(List<MabangOrdersResult> ordersResultList) {
        if (ObjectUtil.isEmpty(ordersResultList)) {
            return false;
        }
        MabangOrdersResult ordersResult = ordersResultList.get(0);
        K3PurchaseInStockOrder k3PurchaseInStockOrder = getK3PurchaseOrderHead(ordersResult);

        List<K3PurchaseInStockOrderItem> itemList = new ArrayList<>();

        for (MabangOrdersResult result : ordersResultList) {
            BigDecimal sellPrice = result.getSellPrice();
            if (ObjectUtil.isNull(sellPrice) || BigDecimal.ZERO.equals(sellPrice)) {
                sellPrice = result.getCostPrice();
            }

            K3PurchaseInStockOrderItem orderItem = new K3PurchaseInStockOrderItem();
            BigDecimal quantity = new BigDecimal(result.getQuantity());

            orderItem.setId(IdWorker.getIdStr());
            orderItem.setFBillNo(ordersResult.getPlatformOrderId());
            orderItem.setFMaterialId(result.getStockSku());
            orderItem.setFUnitId("PCS");
            orderItem.setFRealQty(quantity);
            orderItem.setFPriceUnitId("PCS");
            orderItem.setFownertypeid("BD_OwnerOrg");
            orderItem.setFRemainInStockUnitId("PCS");
            orderItem.setFRemainInStockQty(quantity);
            orderItem.setFTaxPrice(sellPrice);
            orderItem.setFCostPrice(sellPrice);
            orderItem.setFownerid("002");
            orderItem.setFPaezBase2(ordersResult.getRemark());
            orderItem.setFStockId("YT05 009");//雁田-待入仓
            orderItem.setFMustQty(quantity);
            orderItem.setFPriceCoefficient(BigDecimal.ONE);
            orderItem.setFpurbasenum(quantity);
            orderItem.setFStockBaseDen(quantity);
            orderItem.setFsrcbizunitid("PCS");
            orderItem.setCreatedBy("System");
            //备注：2.0增加BTB业务
            if (ObjectUtil.isNotEmpty(ordersResult.getShopName())) {
                orderItem.setFStockId(getStockId(ordersResult.getShopName(), ordersResult.getRemark()));
            }
            itemList.add(orderItem);
        }

        this.save(k3PurchaseInStockOrder);
        itemService.saveBatch(itemList);

        return true;
    }

    /**
     * 按业务规则获取仓库ID
     *
     * @param shopName
     * @param remark
     * @return
     */
    private String getStockId(String shopName, String remark) {

        if (ObjectUtil.isEmpty(remark)) {
            return "YT05 009";//雁田-待入仓
        }

        if ("平台发展".equals(shopName) && "国内待销毁Team".equals(remark)) {
            return "YT05_011";
        }
        if ("BTB".equals(shopName) && "国内待销毁Team".equals(remark)) {
            return "BTB-待销毁仓";
        }

        //订单备注：
        //仓库ID规则：
        //1--  店铺：（BTB or 平台发展）  备注： 非（BTB组 and 平台发展组） 默认在 雁田-待入仓  (其他平台向小平台借货)
        if (("BTB".equals(shopName) || "平台发展".equals(shopName)) && (!"BTB组".equals(remark) && !"平台发展组".equals(remark))) {
            return "YT05 009";//雁田-待入仓
        }

        //2：---------------
        //店铺：BTB ，备注是：平台发展组  默认在 雁田定制仓  （相互借货）
        //店铺：BTB ，备注是：BTB   ?  销毁仓库  --

        //店铺：平台发展 ，备注是：BTB组  默认在  雁田-BTB仓库  （相互借货）
        //店铺：平台发展 ，备注是：平台发展组  ?  销毁仓库
        if ("BTB".equals(shopName)) {
            if ("平台发展组".equals(remark)) {
                return "YT05_008";// （相互借货） 雁田定制仓
            } else if ("BTB组".equals(remark)) {
                return "YT05 009";// 销毁  雁田-待入仓
            }

        } else if ("平台发展".equals(shopName)) {
            if ("BTB组".equals(remark)) {
                return "YT06_BTB";//（相互借货） 雁田-BTB仓库  YT06_BTB
            } else if ("平台发展组".equals(remark)) {
                return "YT05 009";//销毁  雁田-待入仓
            }
        }
        return "YT05 009";
    }

    private static K3PurchaseInStockOrder getK3PurchaseOrderHead(MabangOrdersResult ordersResult) {
        //供应商id取值逻辑：
        //店铺名称：BTB  供应商ID:BTB  |  店铺名称：平台发展  供应商ID:平台发展
        String fSupplierId = "平台发展".equals(ordersResult.getShopName()) ? "平台发展" : "BTB";

        K3PurchaseInStockOrder k3PurchaseInStockOrder = new K3PurchaseInStockOrder();
        k3PurchaseInStockOrder.setId(ordersResult.getPlatformOrderId());
        k3PurchaseInStockOrder.setFBillNo(ordersResult.getPlatformOrderId());
        k3PurchaseInStockOrder.setFBillTypeId("RKD09_SYS");
        k3PurchaseInStockOrder.setFDate(new Date());
        k3PurchaseInStockOrder.setFStockOrgId("002");
        k3PurchaseInStockOrder.setFPurchaseOrgId("002");
        k3PurchaseInStockOrder.setFSupplierId(fSupplierId);
        k3PurchaseInStockOrder.setFOwnerTypeIdHead("BD_OwnerOrg");
        k3PurchaseInStockOrder.setFOwnerIdHead("002");
        k3PurchaseInStockOrder.setFSettleOrgId("002");
        k3PurchaseInStockOrder.setFSettleCurrId("PRE001");
        k3PurchaseInStockOrder.setFPriceTimePoint("1");//todo:
        k3PurchaseInStockOrder.setFSettleCurrId("PRE001");
        k3PurchaseInStockOrder.setSyncType("0");
        return k3PurchaseInStockOrder;
    }


    @DataSource(name = "external")
    @Override
    public ResponseData syncPurchaseInStock() {
        log.info("同步的k3采购入库单到K3系统--开始执行！");

        LambdaQueryWrapper<K3PurchaseInStockOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(K3PurchaseInStockOrder::getSyncStatus, "-1");//只同步待同步的，失败的先不考虑再同步
        queryWrapper.orderByDesc(K3PurchaseInStockOrder::getCreatedTime);

        List<K3PurchaseInStockOrder> orderList = mapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(orderList)) {
            log.info("未找到需要同步的k3采购入库单到K3系统！");
            return ResponseData.success();
        }

        for (K3PurchaseInStockOrder stockOrder : orderList) {
            LambdaQueryWrapper<K3PurchaseInStockOrderItem> itemQuery = new LambdaQueryWrapper<>();
            itemQuery.eq(K3PurchaseInStockOrderItem::getFBillNo, stockOrder.getFBillNo());
            List<K3PurchaseInStockOrderItem> itemList = itemService.getBaseMapper().selectList(itemQuery);
            if (ObjectUtil.isEmpty(itemList)) {
                log.error("采购入库单号：{}未找到入库明细项", stockOrder.getFBillNo());
                stockOrder.setSyncStatus("0");
                stockOrder.setSyncRequstPar("采购入单头未关联上入库明细项");
                stockOrder.setSyncTime(new Date());
                this.updateById(stockOrder);
                continue;
            }
            List<K3PurchaseInStockOrderItem> noTeamData = itemList.stream().filter(it -> ObjectUtil.isEmpty(it.getFPaezBase2())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(noTeamData)) {
                log.error("采购入库单号：{}未找到需求Team", stockOrder.getFBillNo());
                stockOrder.setSyncStatus("-1");
                stockOrder.setSyncRequstPar("采购入单明细项没有需求Team，补充上需求Team再同步");
                stockOrder.setSyncTime(new Date());
                this.updateById(stockOrder);
                continue;
            }
            this.syncPurchaseInStockToErp(stockOrder, itemList);
        }

        log.info("同步的k3采购入库单到K3系统--结束执行！");
        return ResponseData.success();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncPurchaseInStockToErp(K3PurchaseInStockOrder stockOrder, List<K3PurchaseInStockOrderItem> itemList) {

        K3PurchaseOrderInStockParam requstParm = this.getRequstParm(stockOrder, itemList);
        String jsonParm = JSON.toJSONString(requstParm);
        stockOrder.setSyncRequstPar(jsonParm);
        //保存
        ResponseData responseData = syncService.saveInStockOrder(jsonParm);

        if (responseData.getSuccess() && ObjectUtil.isNotNull(responseData.getData())) {
            Integer id = (Integer) responseData.getData();
            log.info("k3采购入库单【{}】同步到erp保存成功", stockOrder.getFBillNo());

            ResponseData commitResponseData = syncService.inStockCommit(stockOrder.getFBillNo(), id);
            //提交
            if (commitResponseData.getSuccess() && ObjectUtil.isNotNull(commitResponseData)) {
                stockOrder.setSyncStatus("1");
                stockOrder.setSyncTime(new Date());
                stockOrder.setSyncResultMsg("采购入库单（保存+提交）同步成功k3返回id:" + id);
                log.info("k3采购入库单【{}】保存+提交--同步成功k3【{}】", stockOrder.getFBillNo(), commitResponseData.getMessage());
            } else {
                stockOrder.setSyncStatus("0");
                stockOrder.setSyncTime(new Date());
                stockOrder.setSyncResultMsg("采购入库单提交失败:" + commitResponseData.getMessage());
                log.info("k3采购入库单【{}】同步到erp提交失败【{}】", stockOrder.getFBillNo(), commitResponseData.getMessage());
            }
            this.updateById(stockOrder);
            return commitResponseData;
        } else {
            stockOrder.setSyncStatus("0");
            stockOrder.setSyncTime(new Date());
            stockOrder.setSyncResultMsg("采购入库单保存失败：" + responseData.getMessage());
            log.info("k3采购入库单【{}】同步到erp保存失败【{}】", stockOrder.getFBillNo(), responseData.getMessage());
        }

        this.updateById(stockOrder);
        return responseData;
    }

    @DataSource(name = "external")
    @Override
    public ResponseData updateVerifyDate(String billNo) {

        LambdaQueryWrapper<K3PurchaseInStockOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(K3PurchaseInStockOrder::getSyncStatus, "1");//同步成功的，且没有审核时间的数据
        queryWrapper.isNull(K3PurchaseInStockOrder::getVerifyDate);//同步成功的，且没有审核时间的数据
        queryWrapper.orderByDesc(K3PurchaseInStockOrder::getCreatedTime);
        List<K3PurchaseInStockOrder> orderList = mapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(orderList)) {
            return ResponseData.success("没有找到需要更新的k3采购入库单");
        }

        for (K3PurchaseInStockOrder order : orderList) {
            Date verifyDate = syncService.queryInStockVerifyDate(order.getFBillNo());
            if (ObjectUtil.isNotNull(verifyDate)) {
                K3PurchaseInStockOrder upo = new K3PurchaseInStockOrder();
                upo.setId(order.getId());
                upo.setVerifyDate(verifyDate);
                upo.setUpdatedTime(new Date());
                this.updateById(upo);
            }

        }
        return ResponseData.success();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateTeam(K3PurchaseInStockOrderParam param) {
        if (ObjectUtil.isEmpty(param.getFPaezBase2()) || ObjectUtil.isEmpty(param.getFBillNo())) {
            return ResponseData.error("需求Team,FbillNo都不能为空!");
        }

        LambdaQueryWrapper<K3PurchaseInStockOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(K3PurchaseInStockOrder::getFBillNo, param.getFBillNo());
        List<K3PurchaseInStockOrder> inStockOrders = this.mapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(inStockOrders)) {
            return ResponseData.error("传入的FBillNo有误，未找到k3采购入库单信息");
        }
        K3PurchaseInStockOrder stockOrder = inStockOrders.get(0);
        if (MabangConstant.SYNC_SUCCESS.equals(stockOrder.getSyncStatus())) {
            return ResponseData.error("k3采购入库单同步成功不允许修改需求Team");
        }
        stockOrder.setSyncStatus(MabangConstant.SYNC_INIT);
        stockOrder.setUpdatedTime(new Date());

        MabangOrders mabangOrder = mabangOrdersService.getMabangOrderByPlatformOrderId(param.getFBillNo());
        //备注：2.0增加BTB业务
        String upStockId ="";
        if (ObjectUtil.isNotEmpty(mabangOrder.getShopName())) {
             upStockId = getStockId(mabangOrder.getShopName(), param.getFPaezBase2());
        }

        LambdaUpdateWrapper<K3PurchaseInStockOrderItem> itemLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        itemLambdaUpdateWrapper.set(K3PurchaseInStockOrderItem::getFPaezBase2, param.getFPaezBase2());
        itemLambdaUpdateWrapper.set(ObjectUtil.isNotEmpty(upStockId),K3PurchaseInStockOrderItem::getFStockId, upStockId);
        itemLambdaUpdateWrapper.eq(K3PurchaseInStockOrderItem::getFBillNo, param.getFBillNo());

        itemService.update(itemLambdaUpdateWrapper);
        this.updateById(stockOrder);
        return ResponseData.success();
    }

    private K3PurchaseOrderInStockParam getRequstParm(K3PurchaseInStockOrder stockOrder, List<K3PurchaseInStockOrderItem> itemList) {
        K3PurchaseOrderInStockParam.ModelDTO model = new K3PurchaseOrderInStockParam.ModelDTO();
        BeanUtil.copyProperties(stockOrder, model);

        K3PurchaseOrderInStockParam.ModelDTO.FInStockFinDTO fInStockFin = new K3PurchaseOrderInStockParam.ModelDTO.FInStockFinDTO();
        fInStockFin.setFSettleOrgId("002");
        fInStockFin.setFSettleCurrId("PRE001");
        fInStockFin.setFPriceTimePoint("1");
        model.setFInStockFin(fInStockFin);


        List<K3PurchaseOrderInStockParam.ModelDTO.FInStockEntryDTO> fInStockEntryList = new ArrayList<>();

        for (K3PurchaseInStockOrderItem item : itemList) {
            K3PurchaseOrderInStockParam.ModelDTO.FInStockEntryDTO fInStockItem = new K3PurchaseOrderInStockParam.ModelDTO.FInStockEntryDTO();
            BeanUtil.copyProperties(item, fInStockItem);
            fInStockEntryList.add(fInStockItem);
        }
        model.setFInStockEntry(fInStockEntryList);

        K3PurchaseOrderInStockParam requstParm = new K3PurchaseOrderInStockParam();
        requstParm.setModel(model);

        return requstParm;
    }
}
