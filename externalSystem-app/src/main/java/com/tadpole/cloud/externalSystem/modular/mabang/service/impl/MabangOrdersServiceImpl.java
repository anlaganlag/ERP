package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.RpMaterialConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangEmployee;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangProductStock;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangEmployeeMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangOrdersMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangProductStockMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.PurOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.SkuStockQtyParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrderItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.*;
import com.tadpole.cloud.externalSystem.modular.mabang.task.UpdateSkuStockTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 马帮订单列表 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-09
 */
@Service
@Slf4j
public class MabangOrdersServiceImpl extends ServiceImpl<MabangOrdersMapper, MabangOrders> implements IMabangOrdersService {


    public static List<String> platList = Arrays.asList(ShopListEnum.Mercadolibre.getCode(), ShopListEnum.eBay.getCode(), ShopListEnum.LAZADA.getCode(), ShopListEnum.Shopee.getCode(), ShopListEnum.速卖通.getCode());


    @Resource
    private MabangOrdersMapper mapper;


    @Resource
    IMabangRequstService mabangRequstService;
    @Resource
    IMabangOrderItemService itemService;

    @Resource
    IMabangProductStockService stockService;

    @Resource
    K3CustMatMappingServiceImpl k3Service;

    @Resource
    IK3TransferMabangItemService transItemService;

    @Resource
    MabangEmployeeMapper employeeMapper;


    @Resource
    RpMaterialConsumer rpMaterialConsumer;

    @Resource
    MabangProductStockMapper stockMapper;


    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public static final ThreadPoolExecutor excutor = new ThreadPoolExecutor(50, 100, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),new ThreadPoolExecutor.DiscardPolicy());

    final String OrderStartDateStr = "2022-06-24";


    @DataSource(name = "external")
    @Override
    public PageResult<MabangOrdersResult> list(MabangOrdersParam param) {
        Page pageContext = getPageContext();
        IPage<MabangOrdersResult> page = mapper.list(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "external")
    @Override
    public List<MabangOrdersResult> exportList(MabangOrdersParam param) {
        return mapper.exportList(param);
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(MabangResult param) {
        try {
            String ordersListJson = JSON.toJSONString(((Map<?, ?>) param.getData()).get("data"));
            List<MabangOrders> ordersList = JSON.parseArray(ordersListJson, MabangOrders.class);
            if (CollectionUtil.isEmpty(ordersList)) {
                log.info(DateUtil.date() + ">>MabangOrders.add>>数据为空!");
                return;
            }
            //遍历单头
            for (MabangOrders mabangOrders : ordersList) {
                try {
                    if (!platList.contains(mabangOrders.getPlatformId())) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_no_aim_plat.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_no_aim_plat.getCode()));
                    }
                    //是否海外仓标识(默认非海外仓)
                    boolean isSea = false;
                    //拆单导致的单身明细为空
                    boolean isSplitEmpty = false;
                    //拆单或者重发明细id需重置
                    boolean isSplit = false;
                    //明细item 目标仓  含有雁田定制仓
                    boolean isNoAimWarehouse = false;
                    mabangOrders.setId(mabangOrders.getErpOrderId());
                    mabangOrders.setSyncTime(DateUtil.date());
                    String platOrdId = mabangOrders.getPlatformId().equals(ShopListEnum.Mercadolibre.getCode()) ? mabangOrders.getPlatformOrderId() : mabangOrders.getSalesRecordNumber();
                    mabangOrders.setPlatOrdId(platOrdId);
                    if (mabangOrders.getPlatformOrderId().contains("_")) {
                        isSplit = true;
                    }
                    //订单明细项是否包含已经发货的商品
                    boolean haveShippedStatus = false;
                    //遍历单身为空时修改状态为isSplitEmpty 为 true
                    if (CollUtil.isNotEmpty(Arrays.asList(mabangOrders.getOrderItem()))) {
                        List<MabangOrderItem> itemList = JSON.parseArray(JSON.toJSONString(mabangOrders.getOrderItem()), MabangOrderItem.class);
                        int idx = 1;
                        for (MabangOrderItem item : itemList) {
                            item.setId(mabangOrders.getId() + "_" + idx);
                            item.setUpdateTime(DateUtil.date());
                            idx++;
                            if ("1049872".equals(item.getStockWarehouseId())) {
                                isSea = true;
                            }
                            //含有雁田定制仓-1047844
                            if ("1047844".equals(item.getStockWarehouseId())) {
                                isNoAimWarehouse = true;
                            }


                            if (isSplit) {
                                item.setItemRemark(ObjectUtil.defaultIfEmpty(item.getItemRemark(), () -> item.getItemRemark() + "|" + item.getOriginOrderId(), item.getOriginOrderId()));
                                item.setOriginOrderId(mabangOrders.getErpOrderId());
                            }
                            String stockSku = item.getStockSku();
                            String platformSku = item.getPlatformSku();
                            if (ObjectUtil.isEmpty(platformSku) || (!stockSku.equals(platformSku) && ((stockSku.length() == 8 && platformSku.length() == 8) || (stockSku.length() == 9 && platformSku.length() == 9)))) {
                                item.setPlatformSku(stockSku);
                            }
                            if ("3".equals(item.getStatus())) {//订单明细项是否包含已经发货的商品
                                haveShippedStatus = true;
                            }
                        }
                        itemService.saveOrUpdateBatch(itemList);
                        //明细为空则认为是拆单
                    } else {
                        isSplitEmpty = true;
                    }

                    //不为海外仓且不拆单为空无需改状态

                    //海外仓
                    if (isSea && !isSplitEmpty) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.sea_warehouse.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.sea_warehouse.getCode()));
                    }
                    //拆单为空
                    if (isSplitEmpty && !isSea) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.split_empty.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.split_empty.getCode()));
                    }
                    //同时海外仓和拆单为空
                    if (isSea && isSplitEmpty) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.sea_split.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.sea_split.getCode()));
                    }
                    if (!isNoAimWarehouse) { //明细ITEM 全都不是雁田定制仓  则不需要创建物料分配和销售出库 和 跨组织调拨单
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_no_aim_warehouse.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_no_aim_warehouse.getCode()));
                    }
                    //马帮销售给K3系统，然后k3系统进行采购入库处理 这类订单不需要做 （物料分配，跨组织调拨，销售处理）2023-05-16
                    //(平台发展 || BTB) && (采购中心)
                    if (("平台发展".equals(mabangOrders.getShopName()) || "BTB".equals(mabangOrders.getShopName()))
                            && ("采购中心".equals(mabangOrders.getBuyerName()) || "采购中心".equals(mabangOrders.getBuyerUserId()))) {

                        if (haveShippedStatus) { //明细项有已发货的 需要创建k3采购入库单
                            mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_k3_purchase_order.getCode()));
                            mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_k3_purchase_order.getCode()));
                            mabangOrders.setCreateSaleOutOrder(new BigDecimal(ShopListEnum.is_k3_purchase_order.getCode()));
                        } else {
                            mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_k3_purchase_order_no_item.getCode()));
                            mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_k3_purchase_order_no_item.getCode()));
                            mabangOrders.setCreateSaleOutOrder(new BigDecimal(ShopListEnum.is_k3_purchase_order_no_item.getCode()));
                        }

                    }
                    if (ObjectUtil.isEmpty(mabangOrders.getBuyerName())) {
                        mabangOrders.setBuyerName("BuyerName为空");
                    }

                    //插入或更新单头
                    this.saveOrUpdate(mabangOrders);
                } catch (Exception e) {
                    log.error("MabangOrders.add单条插入异常>>" + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("MabangOrders.add异常>>" + e.getMessage());

        }
    }



    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNew(MabangResult param) {
        try {
            String ordersListJson = JSON.toJSONString(((Map<?, ?>) param.getData()).get("data"));
            List<MabangOrders> ordersList = JSON.parseArray(ordersListJson, MabangOrders.class);
            if (CollectionUtil.isEmpty(ordersList)) {
                log.info(DateUtil.date() + ">>MabangOrders.add>>数据为空!");
                return;
            }
            Map<String, String> operIdNameMap = new LambdaQueryChainWrapper<>(employeeMapper)
                    .list().stream().collect(Collectors.toMap(MabangEmployee::getEmployeeId, MabangEmployee::getName));

            //遍历单头
            for (MabangOrders mabangOrders : ordersList) {
                try {
                    if (!platList.contains(mabangOrders.getPlatformId())) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_no_aim_plat.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_no_aim_plat.getCode()));
                    }
                    //是否海外仓标识(默认非海外仓)
                    boolean isSea = false;
                    //拆单导致的单身明细为空
                    boolean isSplitEmpty = false;
                    //拆单或者重发明细id需重置
                    boolean isSplit = false;
                    //明细item 目标仓  含有雁田定制仓
                    boolean isNoAimWarehouse = false;
                    mabangOrders.setId(mabangOrders.getErpOrderId());
                    mabangOrders.setSyncTime(DateUtil.date());
                    String platOrdId = mabangOrders.getPlatformId().equals(ShopListEnum.Mercadolibre.getCode()) ? mabangOrders.getPlatformOrderId() : mabangOrders.getSalesRecordNumber();
                    mabangOrders.setPlatOrdId(platOrdId);
                    if (mabangOrders.getPlatformOrderId().contains("_")) {
                        isSplit = true;
                    }
                    //订单明细项是否包含已经发货的商品
                    boolean haveShippedStatus = false;
                    //遍历单身为空时修改状态为isSplitEmpty 为 true
                    if (CollUtil.isNotEmpty(Arrays.asList(mabangOrders.getOrderItem()))) {
                        List<MabangOrderItem> itemList = JSON.parseArray(JSON.toJSONString(mabangOrders.getOrderItem()), MabangOrderItem.class);
                        String operId = itemList.get(0).getOperId();
                        String operName = operIdNameMap.get(operId);
                        mabangOrders.setShopEmployeeName(StrUtil.isNotEmpty(operName) ? (operName) : operId);
                        mabangOrders.setShopEmployeeId(operId);
                        int idx = 1;
                        for (MabangOrderItem item : itemList) {
                            item.setId(mabangOrders.getId() + "_" + idx);
                            item.setUpdateTime(DateUtil.date());
                            idx++;
                            if ("1049872".equals(item.getStockWarehouseId())) {
                                isSea = true;
                            }
                            //含有雁田定制仓-1047844
                            if ("1047844".equals(item.getStockWarehouseId())) {
                                isNoAimWarehouse = true;
                            }


                            if (isSplit) {
                                item.setItemRemark(ObjectUtil.defaultIfEmpty(item.getItemRemark(), () -> item.getItemRemark() + "|" + item.getOriginOrderId(), item.getOriginOrderId()));
                                item.setOriginOrderId(mabangOrders.getErpOrderId());
                            }
                            String stockSku = item.getStockSku();
                            String platformSku = item.getPlatformSku();
                            if (ObjectUtil.isEmpty(platformSku) || (!stockSku.equals(platformSku) && ((stockSku.length() == 8 && platformSku.length() == 8) || (stockSku.length() == 9 && platformSku.length() == 9)))) {
                                item.setPlatformSku(stockSku);
                            }
                            if ("3".equals(item.getStatus())) {//订单明细项是否包含已经发货的商品
                                haveShippedStatus = true;
                            }
                        }
                        itemService.saveOrUpdateBatch(itemList);
                        //明细为空则认为是拆单
                    } else {
                        isSplitEmpty = true;
                    }

                    //不为海外仓且不拆单为空无需改状态

                    //海外仓
                    if (isSea && !isSplitEmpty) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.sea_warehouse.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.sea_warehouse.getCode()));
                    }
                    //拆单为空
                    if (isSplitEmpty && !isSea) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.split_empty.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.split_empty.getCode()));
                    }
                    //同时海外仓和拆单为空
                    if (isSea && isSplitEmpty) {
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.sea_split.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.sea_split.getCode()));
                    }
                    if (!isNoAimWarehouse) { //明细ITEM 全都不是雁田定制仓  则不需要创建物料分配和销售出库 和 跨组织调拨单
                        mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_no_aim_warehouse.getCode()));
                        mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_no_aim_warehouse.getCode()));
                    }
                    //马帮销售给K3系统，然后k3系统进行采购入库处理 这类订单不需要做 （物料分配，跨组织调拨，销售处理）2023-05-16
                    //(平台发展 || BTB) && (采购中心)
                    if (("平台发展".equals(mabangOrders.getShopName()) || "BTB".equals(mabangOrders.getShopName()))
                            && ("采购中心".equals(mabangOrders.getBuyerName()) || "采购中心".equals(mabangOrders.getBuyerUserId()))) {

                        if (haveShippedStatus) { //明细项有已发货的 需要创建k3采购入库单
                            mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_k3_purchase_order.getCode()));
                            mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_k3_purchase_order.getCode()));
                            mabangOrders.setCreateSaleOutOrder(new BigDecimal(ShopListEnum.is_k3_purchase_order.getCode()));
                        } else {
                            mabangOrders.setCreateMat(new BigDecimal(ShopListEnum.is_k3_purchase_order_no_item.getCode()));
                            mabangOrders.setCreateCrossTransfer(new BigDecimal(ShopListEnum.is_k3_purchase_order_no_item.getCode()));
                            mabangOrders.setCreateSaleOutOrder(new BigDecimal(ShopListEnum.is_k3_purchase_order_no_item.getCode()));
                        }

                    }
                    //插入或更新单头
                    this.saveOrUpdate(mabangOrders);
                } catch (Exception e) {
                    log.error("MabangOrders.add单条插入异常>>" + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("MabangOrders.add异常>>" + e.getMessage());

        }
    }


    @DataSource(name = "external")
    @Override
    public Integer getByPlatformOrderId(String id) {
        return this.baseMapper.getByPlatformOrderId(id);


    }

    @DataSource(name = "external")
    @Override
    public void deleteByPlatformOrderId(String id) {
        this.baseMapper.deleteByPlatformOrderId(id);


    }

    @DataSource(name = "external")
    @Override
    public List<MabangOrdersResult> getWaitCreatePurchaseOrder(MabangOrdersParam param) {
        List<MabangOrdersResult> ordersResultList = mapper.getWaitCreatePurchaseOrder(param);
        return ordersResultList;
    }

    @DataSource(name = "external")
    @Override
    public ResponseData getPurOrderList(PurOrderParam param) {
        try {
            MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-get-purchase-list", param);
            MabangResult mabangResult = mabangRequstService.getPurOrderList(mabangHeadParm);
            if ("200".equals(mabangResult.getCode())) {
                Integer pageCount = (Integer) (((Map<?, ?>) mabangResult.getData()).get("pageCount"));
                for (int i = 0; i < pageCount; i++) {
                    param.setPage(i + 1);
                    MabangHeadParm mabangHeadParmPage = new MabangHeadParm("pur-get-purchase-list", param);
                    MabangResult mabangResultPage = mabangRequstService.getPurOrderList(mabangHeadParmPage);
                    ArrayList<Map> data = (ArrayList<Map>) (((Map<?, ?>) mabangResultPage.getData()).get("data"));
                    String s = JSONUtil.toJsonStr(data.get(0));
                }
            }
        } catch (Exception e) {
            log.error("MabangOrders.getPurOrderList异常>>" + e.getMessage());
            return ResponseData.error(e.getMessage());
        }
        return ResponseData.success();
    }
    @DataSource(name = "external")
    @Override
    public ResponseData refreshAllMatStockQty() {
        List<String> matList = rpMaterialConsumer.distinctMatList();
        List<List<String>> splitLists = ListUtil.split(matList, 100);
        for (List<String> splitedList : splitLists) {
            String stockSkusString = splitedList.stream().collect(Collectors.joining(","));
            SkuStockQtyParam skuStockQtyParam = new SkuStockQtyParam().builder().stockSkus(stockSkusString).build();
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                log.error("主线程异常:" + e.getMessage());
                return ResponseData.error(e.getMessage());
            }
            UpdateSkuStockTask updateSkuStockTask = new UpdateSkuStockTask(skuStockQtyParam);
            excutor.execute(updateSkuStockTask);
        }
        return ResponseData.success();

    }

    /**
     * 获取库存数量
     *
     * @param param sku库存参数
     * @return 返回响应数据
     */
    @DataSource(name = "external")
    @Override
    public ResponseData getMatStockQty(SkuStockQtyParam param) {
        if (ObjectUtil.isEmpty(param.getStockSkus()) && ObjectUtil.isEmpty(param.getUpdateTime())) {
            param.setUpdateTime(DateUtil.today());
        }
        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-get-stock-quantity", param);
        MabangResult mabangResult = mabangRequstService.getSkuStockQty(mabangHeadParm);
        if (!"200".equals(mabangResult.getCode())) {
            log.error("对应SKU无数据{}", param.getStockSkus());
            return ResponseData.error(StrUtil.format("对应SKU无数据{}", param.getStockSkus()));
        }
        Integer pageCount = (Integer) (((Map<?, ?>) mabangResult.getData()).get("page"));
        for (int i = 0; i < pageCount; i++) {
            param.setPage(i + 1);
            MabangHeadParm mabangHeadParmPage = new MabangHeadParm("stock-get-stock-quantity", param);
            MabangResult mabangResultPage = mabangRequstService.getSkuStockQty(mabangHeadParmPage);

            if (ObjectUtil.isEmpty(mabangResultPage) || ObjectUtil.isEmpty(mabangResultPage.getData())) {
                log.error(DateUtil.now() + "返回结果为空mabangResultPage" + mabangResultPage);
                return ResponseData.error("返回结果为空mabangResultPage");
            }
            List<Map<String, Object>> skuMapList = (List<Map<String, Object>>) (((Map<String, Object>) mabangResultPage.getData()).get("data"));

            List<MabangProductStock> itemList = new ArrayList<>();
            List<String> delSkuList = new ArrayList<>();
            for (Map<String, Object> skuMap : skuMapList) {
                String stockSku = (String) skuMap.get("stockSku");
                delSkuList.add(stockSku);
//                        String stockQuantity =  skuMap.get("stockQuantity").toString();
                List<Map<String, Object>> warehouses = (List<Map<String, Object>>) skuMap.get("warehouse");
                for (Map<String, Object> map : warehouses) {
                    MabangProductStock item = new MabangProductStock();
//                            item.setStockAllQuantity(stockQuantity);
                    item.setStockSku(stockSku);
                    item.setWarehouseId((String.valueOf(map.get("warehouseId"))));
                    item.setWarehouseName((String.valueOf(map.get("warehouseName"))));
                    item.setStockQuantity(new BigDecimal((Integer) map.getOrDefault("stockQuantity", 0)));
                    //未发货数量
                    item.setWaitingQuantity((BigDecimal.valueOf((Integer) map.getOrDefault("waitingQuantity", 0))));
                    //调拨在途
                    item.setAllotShippingQuantity((BigDecimal.valueOf((Integer) map.getOrDefault("allotShippingQuantity", 0))));
                    //采购在途数量
                    item.setShippingQuantity((BigDecimal.valueOf((Integer) map.getOrDefault("shippingQuantity", 0))));
                    //加工在途量
                    item.setProcessingQuantity((BigDecimal.valueOf((Integer) map.getOrDefault("processingQuantity", 0))));
                    //fba未发货量
                    item.setFbaWaitingQuantity((BigDecimal.valueOf((Integer) map.getOrDefault("fbaWaitingQuantity", 0))));
                    //仓位
                    item.setGridCode((String) map.get("gridCode"));
                    itemList.add(item);
                }
            }
            new LambdaUpdateChainWrapper<>(stockMapper).in(MabangProductStock::getStockSku, delSkuList).remove();
            stockService.saveBatch(itemList);
        }
        return ResponseData.success();

    }

    /**
     * 抓取已完成订单
     *
     * @param param
     * @return
     */
    @DataSource(name = "external")
    @Override
    public ResponseData getFinishedOrderList(OrderParm param) {
        try {
            if (ObjectUtil.isEmpty(param)) {
                param = new OrderParm();
            }
            if (ObjectUtil.isEmpty(param.getPlatformOrderIds()) && (param.getExpressTimeStart() == null || param.getExpressTimeEnd() == null || param.getStatus() == null)) {
                this.initMabangQueryParam(param);
            }
            if (ObjectUtil.isEmpty(param.getStatus())) {
                param.setStatus(MabangConstant.FINISHED);
            }
            MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list-new", param);
            if (vaildSuccessCall(param, mabangHeadParm)) {
                return ResponseData.success();
            }
            return ResponseData.error("拉取已完成订单失败");
        } catch (Exception e) {
            log.error("MabangOrders.getFinishedOrderList异常>>" + e.getMessage());
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 马帮新接口 白天获取订单的区间设置
     * @param param
     */
    private void initMabangQueryParam(OrderParm param) {
        int queryDay = ObjectUtil.isNull(param.getQueryDay()) ? 1 : param.getQueryDay();
        Date expressTimeStart = new Date(new Date().getTime() - 24L * 60 * 59 * 1000 * queryDay);//往前推24小时 * 天数 得到的时间
        param.setExpressTimeStart(expressTimeStart);
        param.setExpressTimeEnd(DateUtil.date());
    }


    @DataSource(name = "external")
    @Override
    public ResponseData getHisOrderList(OrderParm param) {
        //拉历史订单的起始日期,订单拉取时间点前4个月
        Date endDate = ObjectUtil.isNotEmpty(param.getExpressTimeEnd() ) ? param.getExpressTimeEnd(): DateUtil.date();
        Date startDate = DateUtil.offsetDay(endDate,-7);
        Date beginDate = DateUtil.offsetMonth(DateUtil.date(), -4);

        param.setExpressTimeStart(startDate);
        param.setExpressTimeEnd(endDate);
        if (ObjectUtil.isEmpty(param.getStatus())) {
            param.setStatus(MabangConstant.EXPRESSED);
        }
        while (startDate.equals(beginDate) || startDate.after(beginDate)) {
            MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list-new", param);
            if (!vaildSuccessCall(param, mabangHeadParm)) {
                break;
            }
            DateTime minus7Days = DateUtil.offsetDay(startDate, -7);
            startDate = minus7Days.before(beginDate) ? beginDate : DateUtil.offsetDay(startDate, -7);
            endDate = DateUtil.offsetDay(startDate, 7);
            param.setExpressTimeStart(startDate);
            param.setExpressTimeEnd(endDate);
        }
        return ResponseData.success();
    }

    private Boolean vaildSuccessCall(OrderParm param, MabangHeadParm mabangHeadParm) {
        MabangResult mabangResult = mabangRequstService.getOrderListNew(mabangHeadParm);
        String code = mabangResult.getCode();
        if ("0".equals(code)) {
            return Boolean.FALSE;
        }
        if ("200".equals(code) &&  ObjectUtil.isNotEmpty(mabangResult.getData())) {
            getOrderProccess(param, mabangResult);
        }
        return Boolean.TRUE;
    }

    private void getOrderProccess(OrderParm param, MabangResult mabangResult) {
        String nextCursor = (String) (((Map<?, ?>) mabangResult.getData()).get("nextCursor"));
        Boolean hasNext = (Boolean) (((Map<?, ?>) mabangResult.getData()).get("hasNext"));
        this.addNew(mabangResult);
        while (hasNext) {
            param.setCursor(nextCursor);
            MabangHeadParm mabangHeadParmPage = new MabangHeadParm("order-get-order-list-new", param);
            MabangResult mabangResultPage = mabangRequstService.getOrderListNew(mabangHeadParmPage);
            nextCursor = (String) (((Map<?, ?>) mabangResultPage.getData()).get("nextCursor"));
            hasNext = (Boolean) (((Map<?, ?>) mabangResultPage.getData()).get("hasNext"));
            this.addNew(mabangResultPage);
        }
    }


    @DataSource(name = "external")
    @Override
    public ResponseData getHisFinishedOrderList(OrderParm param) {
        //拉历史订单的起始日期beginDate,订单拉取时间点前4个月
        Date endDate = ObjectUtil.isNotEmpty(param.getExpressTimeEnd() ) ? param.getExpressTimeEnd(): DateUtil.date();
        Date startDate = DateUtil.offsetDay(endDate,-7);
        Date beginDate = DateUtil.offsetMonth(DateUtil.date(), -4);

        param.setExpressTimeStart(startDate);
        param.setExpressTimeEnd(endDate);
        param.setExpressTimeEnd(endDate);
        if (ObjectUtil.isEmpty(param.getStatus())) {
            param.setStatus(MabangConstant.FINISHED);
        }
        while (startDate.equals(beginDate) || startDate.after(beginDate)) {
            MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list-new", param);
            if (!vaildSuccessCall(param, mabangHeadParm)) {
                break;
            }
            DateTime minus7Days = DateUtil.offsetDay(startDate, -7);
            startDate = minus7Days.before(beginDate) ? beginDate : minus7Days;
            endDate = DateUtil.offsetDay(startDate, 7);
            param.setExpressTimeStart(startDate);
            param.setExpressTimeEnd(endDate);
        }
        return ResponseData.success();
    }


    @DataSource(name = "external")
    @Override
    public PageResult<MabangOrderItemResult> display(String id) {
        Page pageContext = getPageContext();
        IPage<MabangOrderItemResult> page = mapper.display(pageContext, id);
        return new PageResult<>(page);
    }


    /**
     * 抓取已发货
     *
     * @param param
     * @return
     */
    @DataSource(name = "external")
    @Override
    public ResponseData getOrderList(OrderParm param) {
        try {
            if (ObjectUtil.isEmpty(param)) {
                param = new OrderParm();
            }
            if (ObjectUtil.isEmpty(param.getPlatformOrderIds()) && (param.getExpressTimeStart() == null || param.getExpressTimeEnd() == null )) {
                this.initMabangQueryParam(param);
            }
            if (ObjectUtil.isEmpty(param.getStatus())) {
                param.setStatus(MabangConstant.EXPRESSED);
            }
            MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list-new", param);
            if (vaildSuccessCall(param, mabangHeadParm)) {
                return ResponseData.success();
            }
            return ResponseData.error("拉取已发货订单失败");
        } catch (Exception e) {
            log.error("MabangOrders.getOrderList异常>>" + e.getMessage());
            return ResponseData.error(e.getMessage());
        }
    }

    @Override
    public ResponseData getOrderListByOrderId(String orderId) {
        try {
            OrderParm param = new OrderParm();
            param.setPlatformOrderIds(orderId);
            param.setAllstatus("1");
            MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list-new", param);
            MabangResult mabangResult = mabangRequstService.getOrderListNew(mabangHeadParm);
            if ("200".equals(mabangResult.getCode())) {
                String ordersListJson = JSON.toJSONString(((Map<?, ?>) mabangResult.getData()).get("data"));
                List<MabangOrders> ordersList = JSON.parseArray(ordersListJson, MabangOrders.class);
                if(CollectionUtil.isEmpty(ordersList)){
                    return ResponseData.error("获取马帮BTB订单响应数据为空");
                }
                return ResponseData.success(ordersList);
            }else{
                return ResponseData.error(mabangResult.getMessage());
            }
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    @DataSource(name = "external")
    @Override
    public MabangOrders getMabangOrderByPlatformOrderId(String platOrdId) {
        return mapper.getMabangOrderByPlatformOrderId(platOrdId);
    }

//    @DataSource(name = "external")
//    @Override
//    public ResponseData getAllOrderList(OrderParm param) {
//
//        MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list", param);
//
//        MabangResult mabangResult = mabangRequstService.getOrderList(mabangHeadParm);
//        if ("200".equals(mabangResult.getCode())) {
//            return this.add(mabangResult);
//        }
//        return ResponseData.error(mabangResult.getMessage());
//    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }




}
