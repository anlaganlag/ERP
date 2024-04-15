package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.util.K3GeneratorNoUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.k3.utils.SyncToErpUtil;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.dto.MabangOrdersDTO;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.SyncBaseStatusEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleOutOrderMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleOutOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SyncSaleOutOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleOutOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrdersService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderItemService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderService;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 销售出库单 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-08-24
 */
@Service
@Slf4j
public class SaleOutOrderServiceImpl extends ServiceImpl<SaleOutOrderMapper, SaleOutOrder> implements ISaleOutOrderService {

    @Resource
    SyncToErpUtil syncToErpUtil;

    @Resource
    K3GeneratorNoUtil k3GeneratorNoUtil;

    @Resource
    private IMabangOrdersService mabangOrderService;

    @Resource
    private SaleOutOrderMapper mapper;

    @Resource
    private ISaleOutOrderItemService saleOutOrderItemService;

    @DataSource(name = "external")
    @Override
    public ResponseData findPageBySpec(SaleOutOrderParam param) {
        String start = " 00:00:00";
        String end = " 23:59:59";
        if (StringUtils.isNotEmpty(param.getStartTime())) {
            param.setStartTime(param.getStartTime() + start);
        }
        if (StringUtils.isNotEmpty(param.getEndTime())) {
            param.setEndTime(param.getEndTime() + end);
        }
        return ResponseData.success(mapper.findPageBySpec(PageFactory.defaultPage(), param));

    }

    @DataSource(name = "external")
    @Override
    public List<ExportSaleOutOrderResult> export(SaleOutOrderParam param) {
        String start = " 00:00:00";
        String end = " 23:59:59";
        if (StringUtils.isNotEmpty(param.getStartTime())) {
            param.setStartTime(param.getStartTime() + start);
        }
        if (StringUtils.isNotEmpty(param.getEndTime())) {
            param.setEndTime(param.getEndTime() + end);
        }
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<ExportSaleOutOrderResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> platformSelect() {
        QueryWrapper<SaleOutOrderItem> wrapper = new QueryWrapper<>();
        wrapper.select("PLAT_NAME").groupBy("PLAT_NAME").orderByAsc("PLAT_NAME");
        List<Map<String, Object>> selectList = saleOutOrderItemService.listMaps(wrapper);
        return selectList;
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> yearSelect() {
        QueryWrapper<SaleOutOrder> wrapper = new QueryWrapper<>();
        wrapper.select("YEARS").groupBy("YEARS").orderByAsc("YEARS");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        return selectList;
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> monthSelect() {
        QueryWrapper<SaleOutOrder> wrapper = new QueryWrapper<>();
        wrapper.select("MONTH").groupBy("MONTH").orderByAsc("MONTH");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        return selectList;
    }

    @Override
    @DataSource(name = "external")

    public List<Map<String, Object>> shopSelect(List<String> platformNames) {
        List<Map<String, Object>> selectList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(platformNames)) {
            QueryWrapper<SaleOutOrderItem> wrapper = new QueryWrapper<>();
            wrapper.select("SHOP_NAME").in("PLAT_NAME", platformNames).groupBy("SHOP_NAME").orderByAsc("SHOP_NAME");
            selectList = saleOutOrderItemService.listMaps(wrapper);
        }
        return selectList;
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> siteSelect() {
        QueryWrapper<SaleOutOrderItem> wrapper = new QueryWrapper<>();
        wrapper.select("SITE_CODE").groupBy("SITE_CODE").orderByAsc("SITE_CODE");
        List<Map<String, Object>> selectList = saleOutOrderItemService.listMaps(wrapper);
        return selectList;
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ZZDistributeMcms> getFsaleOrgIdAndMat() {
        return mapper.getFsaleOrgIdAndMat();
    }

    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getFinanceName(String fNumber) {
        return mapper.getFinanceName(fNumber);
    }

    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getWarehouseName(String fNumber) {
        return mapper.getWarehouseName(fNumber);
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getSalOrgName() {
        return mapper.getSalOrgName();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalOrgName(String ownerId, String ownerName) {
        mapper.updateSalOrgName(ownerId, ownerName);
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWarehouseName(String ownerId, String ownerName) {
        mapper.updateWarehouseName(ownerId, ownerName);
    }

    @Override
    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateSaleOutOrderByMabangOrders() {

        //获取当前操作用户信息
        //String account = loginUserInfo.getUserAccount();
        log.info("生成K3销售出库单开始start-------");
        SyncSaleOutOrderParam syncParam = new SyncSaleOutOrderParam();

        //日期格式转换  (当月计算上一个月的销售出库)
        LocalDate localDate = LocalDate.now();
        String year = String.valueOf(localDate.getYear());
        String month1 = "00000" + String.valueOf(localDate.getMonthValue() - 1);
        String month = String.valueOf(localDate.getMonthValue() - 1);
        String DateStr = localDate.getYear() + "" + (month1.substring(month1.length() - 2, month1.length()));
        if (localDate.getMonthValue() == 1) {
            year = String.valueOf(localDate.getYear() - 1);
            month = String.valueOf(12);
            DateStr = year + month;
        }


//        String year = String.valueOf(DateUtil.year(new Date()));
//        String month = String.valueOf(DateUtil.month(new Date()));
//        String DateStr= DateUtil.format(new Date(),"yyyyMM");

        syncParam.setYear(year);
        syncParam.setMonth(month);
        syncParam.setYearMonth(DateStr);

        //效验是否已生成销售出库单记录
        LambdaQueryWrapper<SaleOutOrder> qw = new LambdaQueryWrapper();
        qw.eq(SaleOutOrder::getYears, year).eq(SaleOutOrder::getMonth, month);
        if (this.count(qw) > 0) {
            log.info("日期{}马帮发货订单已生成销售出库单！",DateStr);
//            return ResponseData.error("日期" + DateStr + "马帮发货订单已生成销售出库单！");
        }

        //1-1.马帮发货合并订单销售出库列表
        List<MabangOrdersDTO> list = mapper.mabangtOrdersMergeList(syncParam);
        if (CollectionUtil.isEmpty(list)) {
            log.info("日期{}马帮发货订单没有要生成的销售出库单！",DateStr);
            return ResponseData.error("日期" + DateStr + "马帮发货订单没有要生成的销售出库单！");
        }
        int sumCount = list.size();
        log.info("日期{}按条件查询出[{}]条发货订单需要生成销售出库单！",DateStr,sumCount);
        int curDataNo=0;
        Set<String> allOrder = new HashSet<>();
        for (MabangOrdersDTO lst : list) {

            SaleOutOrder order = new SaleOutOrder();

            syncParam.setFinanceCode(lst.getFinanceCode());
            syncParam.setSku(lst.getStockSku());
            syncParam.setShopId(lst.getShopId());
            syncParam.setSku(lst.getStockSku());
            syncParam.setOriginalFinanceCode(lst.getOriginalFinanceCode());

            List<MabangOrdersDTO> detailList = mapper.selectMabangtOrders(syncParam);

            if (CollectionUtil.isNotEmpty(detailList)) {

                List<List<MabangOrdersDTO>> lists = ListUtil.split(detailList, 300);

                for (List<MabangOrdersDTO> dtoList : lists) {

                    //1-1-1.销售出库单
                    String billNo = k3GeneratorNoUtil.getNoByKey(MabangConstant.SALEOUT_ORDER_PREFIX, MabangConstant.SYNC_K3_SALEOUT_ORDER_KEY, 6);
                    //销售出库数量汇总
                    BigDecimal sumQty = dtoList.stream().map(MabangOrdersDTO::getQuantity).reduce(BigDecimal::add).get();

                    //1-1-2.销售出库单生成记录保存
                    order.setBillNo(billNo);
                    order.setYears(year);
                    order.setMonth(month);
                    order.setFinanceCode(lst.getFinanceCode());
                    order.setStockSku(lst.getStockSku());
                    order.setFBillTypeId("XSCKD01_SYS");
                    order.setFBillNo(billNo);
                    order.setFDate(year + "-" + month + "-" + "01");
                    order.setFSaleOrgId(lst.getFinanceCode());
                    order.setSalOrgCode(lst.getFinanceCode());
                    order.setFCustomerId("店铺虚拟客户");
                    order.setFStockOrgId(lst.getFinanceCode());
                    order.setFNote("销售出库");
                    order.setOutQtySum(sumQty);
                    order.setCreateTime(new Date());
                    this.save(order);


                    List<SaleOutOrderItem> itemList = new ArrayList<>();
                    for (MabangOrdersDTO detail : dtoList) {
                        //1-1-3.销售出库单身明细保存
                        SaleOutOrderItem item = new SaleOutOrderItem();
                        item.setSaleOutOrderId(String.valueOf(order.getId()));
                        item.setMaOrderItemId(detail.getOrderDetailId());
                        item.setBillNo(billNo);
                        item.setDepartment("平台发展部");
                        item.setTeam("平台发展组");
                        item.setPlatName(detail.getPlatformId());
                        item.setShopName(detail.getShopName());
                        //站点(产品需求)：金蝶组织编码小尾巴中裁剪出站点，如无小尾巴，则默认：ALL
                        if (detail.getOriginalFinanceCode().contains("_")) {
                            String[] strList = StrUtil.splitToArray(detail.getOriginalFinanceCode(), "_");
                            if (StrUtil.isNotEmpty(strList[1])) {
                                item.setSiteCode(strList[1]);
                            }
                        } else {
                            item.setSiteCode("ALL");
                        }
                        item.setWarehouseId(lst.getFinanceCode());
                        item.setFStockId(lst.getFinanceCode());
                        //海外仓 固定传送规则： 美国纽约仓-递四方(新)1049872->eBay海外仓  AE虚拟优选仓1053746->速卖通海外仓  -- lsy 2022-11-3 新增
                        if ("1049872".equals(detail.getStockWarehouseId())) {
                            item.setWarehouseName("eBay海外仓");
                            item.setWarehouseId("eBay海外仓");
                            item.setFStockId("eBay海外仓");
                        } else if ("1053746".equals(detail.getStockWarehouseId())) {
                            item.setWarehouseName("速卖通海外仓");
                            item.setWarehouseId("速卖通海外仓");
                            item.setFStockId("速卖通海外仓");
                        }

                        item.setPlatOrdId(detail.getPlatOrdId());
                        item.setPlatSku(detail.getPlatformSku());
                        item.setShippedTime(detail.getExpressTime());
                        item.setOutQty(detail.getQuantity());
                        item.setFMaterialId(detail.getStockSku());
                        item.setFUnitId("Pcs");
                        item.setFRealQty(detail.getQuantity());
                        item.setFStockStatusId("KCZT01_SYS");
                        item.setFBscDept("平台发展部");
                        item.setFBscTeam("平台发展组");
                        item.setCreateTime(new Date());
                        itemList.add(item);

//                        if (!allOrder.contains(detail.getErpOrderId())) {
                            //1-1-4.更新同步马帮发货到销售组织同步状态

                            allOrder.add(detail.getErpOrderId());
//                        }
                    }
                    saleOutOrderItemService.saveBatch(itemList);

                }
            }
            curDataNo +=1;
            log.info("日期{}按条件查询出[{}]条发货订单需要生成销售出库单,已处理[{}]条！",DateStr,sumCount,curDataNo);
        }

        //                ----------------更新同步马帮发货到销售组织同步状态
        if (ObjectUtil.isNotEmpty(allOrder)) {
            List<String> orderIdList = allOrder.stream().collect(Collectors.toList());

            List<List<String>> orderIdListSplit = ListUtil.split(orderIdList, 900);
            for (List<String> ids : orderIdListSplit) {
                LambdaUpdateWrapper<MabangOrders> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.in(MabangOrders::getErpOrderId, ids)
                        .set(MabangOrders::getCreateSaleOutOrder, BigDecimal.ONE);
                mabangOrderService.update(null, updateWrapper);
            }

        }

        return ResponseData.success("发货订单生成销售出库单成功！");
    }


    @Override
    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncSaleOutOrderToErp(String year, String month) {

        log.info("syncSaleOutOrderToErp--start-->>");

        if (ObjectUtil.isEmpty(year) || ObjectUtil.isEmpty(month) ) {
            LocalDate localDate = LocalDate.now();

            if (localDate.getMonthValue() == 1) { //跨年的时候，1月取上一年的12月
                year = String.valueOf(localDate.getYear()-1);
                month = String.valueOf(12);
            } else {
                year = String.valueOf(DateUtil.year(new Date()));
                month = String.valueOf(localDate.getMonthValue()-1);
            }
        }

        String dataStr = year + "-" + month + "-1";
        LocalDate monthOfLastDate = LocalDate.parse(dataStr,
                DateTimeFormatter.ofPattern("yyyy-M-d")).with(TemporalAdjusters.lastDayOfMonth());
        TemporalAdjusters.firstDayOfMonth();
        String fDateStr = monthOfLastDate.toString();

        //日期格式转换
//        String year = String.valueOf(DateUtil.year(new Date()));
//        String month = String.valueOf(DateUtil.month(new Date()));

        //同步k3销售出库单
        LambdaQueryWrapper<SaleOutOrder> qw = new LambdaQueryWrapper<>();
        qw.eq(SaleOutOrder::getYears, year).eq(SaleOutOrder::getMonth, month)
                .in(SaleOutOrder::getSyncStatus, SyncBaseStatusEnum.DEFAULT.getCode(), SyncBaseStatusEnum.ERROR.getCode())
                .orderByDesc(SaleOutOrder::getCreateTime);

        List<SaleOutOrder> saleList = this.list(qw);

        if (CollectionUtil.isNotEmpty(saleList)) {
            log.info("马帮发货订单合并销售出库单，本次查询共计【{}】条需要推送！", saleList.size());
            for (SaleOutOrder sale : saleList) {
                try {
                    log.info("马帮发货订单合并销售出库单，正在处理billNo--{}", sale.getFBillNo());
                    List<SaleOutOrderItem> dlist = this.baseMapper.getSyncList(sale.getFBillNo());
                    JSONArray Jarr = new JSONArray();

                    JSONObject object = new JSONObject();
                    object.put("FBillNo", sale.getFBillNo());
                    object.put("FDate", fDateStr);
                    object.put("FCreatorId", "S20210055"); //测试工号
                    object.put("FSaleOrgId", sale.getFSaleOrgId());
                    object.put("FCustomerID", "店铺虚拟客户");
                    object.put("FCorrespondOrgId", "店铺虚拟客户");
                    object.put("FStockOrgId", sale.getFStockOrgId());
                    object.put("FNote", "销售出库");
                    object.put("FPayerID", "店铺虚拟客户");
                    object.put("FEntity", dlist);

                    Jarr.add(object);

                    log.info("马帮发货订单合并销售出库单[{}]，马帮erp销售出库请求k3参数:[{}]", sale.getFBillNo(), JSONUtil.toJsonStr(Jarr));

                    JSONObject obj = syncToErpUtil.saleOutStock(Jarr);

                    log.info("马帮发货订单合并销售出库单[{}]，马帮erp销售出库请求k3返回结果:[{}]", sale.getFBillNo(), JSONUtil.toJsonStr(obj));

                    if (obj.getString("Code") != null && obj.getString("Code").equals("0")) {
                        //
                        //                            sale.setSyncStatus( SyncBaseStatusEnum.SUCCESS.getCode());
                        //                            sale.setSyncRequestMsg(Jarr.toString());
                        //                            sale.setSyncResultMsg(obj.toString());
                        //                            sale.setSyncTime( DateUtil.date());
                        //                            sale.setUpdateTime(DateUtil.date());
                        //                    this.updateById(sale);

                        LambdaUpdateWrapper<SaleOutOrder> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(SaleOutOrder::getBillNo, sale.getFBillNo())
                                .set(SaleOutOrder::getSyncStatus, SyncBaseStatusEnum.SUCCESS.getCode())
                                .set(SaleOutOrder::getSyncRequestMsg, Jarr.toString())
                                .set(SaleOutOrder::getSyncResultMsg, obj.toString())
                                .set(SaleOutOrder::getSyncTime, DateUtil.date())
                                .set(SaleOutOrder::getUpdateTime, DateUtil.date());
                        this.update(null, updateWrapper);

                        log.info("马帮发货订单合并销售出库单[{}]，同步k3销售出库成功！", sale.getFBillNo());
                    } else {

                        log.info("syncSaleOutOrderToErp--同步接口异常-->>");
                        LambdaUpdateWrapper<SaleOutOrder> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(SaleOutOrder::getBillNo, sale.getFBillNo())
                                .set(SaleOutOrder::getSyncStatus, SyncBaseStatusEnum.ERROR.getCode())
                                .set(SaleOutOrder::getSyncRequestMsg, Jarr.toString())
                                .set(SaleOutOrder::getSyncResultMsg, obj.toString())
                                .set(SaleOutOrder::getSyncTime, DateUtil.date())
                                .set(SaleOutOrder::getUpdateTime, DateUtil.date());
                        this.update(null, updateWrapper);
                        log.error("马帮发货订单合并销售出库单[{}]，同步k3销售出库接口失败！异常信息[{}]", sale.getFBillNo(), obj.getString("Msg"));
                    }

                    log.info("马帮发货订单合并销售出库单，结束处理billNo--{}", sale.getFBillNo());
                } catch (Exception e) {
                    log.error("马帮发货订单合并销售出库单[{}],处理异常:[{}]", sale.getBillNo(), JSONUtil.toJsonStr(e.getMessage()));
                }
            }
        } else {
            log.info("马帮发货订单合并销售出库单，本次查询无数据需要推送！", saleList.size());
        }
        log.info("syncSaleOutOrderToErp--end--<<");
        return ResponseData.success();
    }
}
