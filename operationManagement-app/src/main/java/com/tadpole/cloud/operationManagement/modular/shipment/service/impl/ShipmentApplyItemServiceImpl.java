package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.*;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.ShipmentApplyItemMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.*;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.*;
import com.tadpole.cloud.operationManagement.modular.shipment.service.*;
import com.tadpole.cloud.operationManagement.modular.shipment.utils.GeneratorShipmentNoUtil;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsDayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsDayResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ILogisticsDayService;
import com.tadpole.cloud.operationManagement.modular.stock.service.RpMaterialService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 发货申请明细项 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
@Service
@Slf4j
public class ShipmentApplyItemServiceImpl extends ServiceImpl<ShipmentApplyItemMapper, ShipmentApplyItem> implements IShipmentApplyItemService {

    @Resource
    private ShipmentApplyItemMapper mapper;


    @Resource
    private ITrackingTransferService trackingTransferService;

    @Resource
    private InvProductGalleryService invProductGalleryService;


    @Resource
    private RpMaterialService materialService;

    @Resource
    private IShipmentRecommendationService recommendationService;


    @Resource
    GeneratorShipmentNoUtil generatorShipmentNoUtil;

    @Resource
    IOtherModularService otherModularService;

    @Resource
    IShipmentRecomSnapshotService shipmentRecomSnapshotService;

    @Resource
    IShipmentTrackingService shipmentTrackingService;


    @Resource
    private ILogisticsDayService logDayService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static String VERIF_KEY_HEAD = "Shipment_Verif_Key_";


    /**
     * 发货申请，自选创建
     *
     * @param param
     * @return
     */
    @Override
    @DataSource(name = "basicdata")
    public ResponseData selectCreateSku(InvProductGalleryParam param) {

        //获取可选择的sku
        List<InvProductGallery> productSkuList = invProductGalleryService.querySkuDatalimit(param);
        System.out.println(JSONUtil.toJsonStr(productSkuList));
        if (ObjectUtil.isEmpty(productSkuList)) {
            return ResponseData.error("未找到关联的sku，请确认创建条件是否正确");
        }
        Map<String, List<InvProductGallery>> matCodeMap =
                productSkuList.stream().collect(Collectors.groupingBy(InvProductGallery::getMaterialCode));


        String pkJsonStr = JSONUtil.toJsonStr(productSkuList);

        //查询物料信息
        List<String> matCodeList = matCodeMap.keySet().stream().collect(Collectors.toList());
        List<Material> materialList = materialService.getMaterials(matCodeList);

        if (ObjectUtil.isEmpty(materialList)) {
            return ResponseData.error("未找到关联的物料信息，请确认创建条件是否正确");
        }

        Map<String, List<Material>> materialMap = materialList.stream().collect(Collectors.groupingBy(Material::getMaterialCode));

        List<ShipmentApplyItem> applyItemList = new ArrayList<>();

        for (Map.Entry<String, List<InvProductGallery>> entry : matCodeMap.entrySet()) {
            String matCode = entry.getKey();

            List<Material> materials = materialMap.get(matCode);
            if (ObjectUtil.isEmpty(materials)) {
                continue;
            }
            Material material = materials.get(0);
            String materialJsonStr = JSONUtil.toJsonStr(material);

            for (InvProductGallery productGallery : entry.getValue()) {
                ShipmentApplyItem item = new ShipmentApplyItem();
                BeanUtil.copyProperties(productGallery, item);
                item.setMaterialCode(matCode);
                item.setMaterialInfo(materialJsonStr);
                item.setProductName(material.getProductName());
                item.setProductType(material.getProductType());
                item.setCreatedTime(new Date());
                item.setPlatform("Amazon");
                item.setDataSourceType("手动选择");
                applyItemList.add(item);
            }
        }
        return ResponseData.success(applyItemList);
    }


    /**
     * 文件导入创建发货sku
     *
     * @param file
     * @return
     */
    @Override
    @DataSource(name = "basicdata")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importCreateSku(MultipartFile file, Map<String, String> deliveryMap) {
        if (ObjectUtil.isEmpty(deliveryMap)) {
            log.info("未设置发货点字典:{}", JSONUtil.toJsonStr(deliveryMap));
            return ResponseData.error("未设置发货点字典");
        }

        BufferedInputStream buffer = null;

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        if (ObjectUtil.isNull(currentUser)) {
            return ResponseData.error("请登录后再申请创建");
        }


        List<ShipmentApplyExcelParam> excelDataList = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<ShipmentApplyExcelParam>();
            EasyExcel.read(buffer, ShipmentApplyExcelParam.class, listener).sheet().doRead();

            excelDataList = listener.getDataList();

            log.info("excel解析出来数据" + excelDataList.size());

            if (CollectionUtil.isEmpty(excelDataList)) {
                return ResponseData.error("解析出来的数据为空，导入失败！");
            }
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！{}", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常{}", e);
                }
            }
        }
        // 导入数据校验

        //不允许AU、SG、NL、PL、SE、TR、BE站点SKU发货
        List<ShipmentApplyExcelParam> filterResultList = excelDataList.stream().filter(e ->
                "AU".equalsIgnoreCase(e.getSysSite())
                        || "SG".equalsIgnoreCase(e.getSysSite())
                        || "NL".equalsIgnoreCase(e.getSysSite())
                        || "PL".equalsIgnoreCase(e.getSysSite())
                        || "SE".equalsIgnoreCase(e.getSysSite())
                        || "TR".equalsIgnoreCase(e.getSysSite())
                        || "BE".equalsIgnoreCase(e.getSysSite())).collect(Collectors.toList());

        if (ObjectUtil.isNotEmpty(filterResultList)) {
            return ResponseData.error("不允许AU、SG、NL、PL、SE、TR、BE站点SKU发货");
        }


        //excelDataList的账号和站点转大写
        excelDataList.forEach(e -> {
            e.setSysShopsName(e.getSysShopsName().toUpperCase());
            e.setSysSite(e.getSysSite().toUpperCase());
        });

        InvProductGalleryParam param = new InvProductGalleryParam();
        param.setDepartment(currentUser.getDepartment());
        param.setTeam(currentUser.getTeam());


        //账号
        List<String> sysShopsNameList = excelDataList.stream().map(e -> e.getSysShopsName()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        param.setSysShopsNameList(sysShopsNameList);
        //站点
        List<String> sysSiteList = excelDataList.stream().map(e -> e.getSysSite()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        param.setSysSiteList(sysSiteList);
        //SKU
        List<String> skuList = excelDataList.stream().map(e -> e.getSku()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        param.setSkuList(skuList);

        List<InvProductGallery> productSkuList = invProductGalleryService.querySku(param);
        if (ObjectUtil.isEmpty(productSkuList)) {
            return ResponseData.error("根据当前登录者所属部门+team,导入数据未关联上系统发货sku，请检查表格数据是否正确！");
        }


        Map<String, List<ShipmentApplyExcelParam>> excelDataMap = excelDataList.stream()
                .collect(Collectors.groupingBy(e -> e.getSysShopsName() + "_" + e.getSysSite() + "_" + e.getSku()));

        Map<String, List<InvProductGallery>> productSkuMap = productSkuList.stream()
                .collect(Collectors.groupingBy(ps -> ps.getSysShopsName() + "_" + ps.getSysSite() + "_" + ps.getSku()));

        List<InvProductGallery> productSkuFilterList = productSkuList.stream()
                .filter(ps -> excelDataMap.containsKey(ps.getSysShopsName() + "_" + ps.getSysSite() + "_" + ps.getSku())).collect(Collectors.toList());
        //物流校验错误信息
        StringBuffer logErrorInfo = new StringBuffer();
        for (ShipmentApplyExcelParam ex : excelDataList) {
            String exKey = ex.getSysShopsName() + "_" + ex.getSysSite() + "_" + ex.getSku();
            for (InvProductGallery sku : productSkuFilterList) {
                String skuKey = sku.getSysShopsName() + "_" + sku.getSysSite() + "_" + sku.getSku();
                if (exKey.equals(skuKey)) {
                    List<String> logisticsModeList = logDayService.findPageBySpec(LogisticsDayParam.builder().area(sku.getArea()).build()).getRows().stream().map(LogisticsDayResult::getLogisticsMode).collect(Collectors.toList());
                    if (!logisticsModeList.contains(ex.getTransportationType())) {
                        logErrorInfo.append(StrUtil.format("{}物流方式[{}]不在物流时效列表中:{}\n", exKey, ex.getTransportationType(), JSONUtil.toJsonStr(logisticsModeList)));
                    }
                }
            }
        }
        if (StrUtil.isNotEmpty(logErrorInfo.toString())) {
            return ResponseData.error(logErrorInfo.toString());
        }

        //查询物料信息
        Map<String, List<InvProductGallery>> matCodeMap =
                productSkuFilterList.stream().collect(Collectors.groupingBy(InvProductGallery::getMaterialCode));

        List<String> matCodeList = matCodeMap.keySet().stream().collect(Collectors.toList());
        List<Material> materialList = materialService.getMaterials(matCodeList);
        Map<String, List<Material>> materialMap = materialList.stream().collect(Collectors.groupingBy(Material::getMaterialCode));

        if (ObjectUtil.isEmpty(materialList)) {
            return ResponseData.error("未找到关联的物料信息，请确认创建条件是否正确");
        }

        List<ShipmentApplyItem> applyItemList = new ArrayList<>();


        //获取仓库编码（海外仓编码和名称一样）
        Set<String> receiveWarehouseCodeSet = excelDataList.stream().map(e -> e.getReceiveWarehouse()).collect(Collectors.toSet());

        //调入仓库默认名称
        Set<String> warehouseNameSet = excelDataList.stream().map(
                e -> "Amazon_" + e.getSysShopsName() + "_" + e.getSysSite() + "_仓库").collect(Collectors.toSet());
        warehouseNameSet.addAll(receiveWarehouseCodeSet);
        Map<String, String> shopWarehouseCodeMap = new HashMap<>();

        shopWarehouseCodeMap = otherModularService.getWarehouseCode(warehouseNameSet);

        //校验 表格填入的调入仓库
        if (ObjectUtil.isEmpty(shopWarehouseCodeMap) || ObjectUtil.isEmpty(shopWarehouseCodeMap.values())) {
            return ResponseData.error("填入的调入仓库,未能匹配上正确的仓库信息");
        }

        //原调入仓库编码和名称对应关系,编码->名称,
//        HashMap<String, String> warehousecodeNameMap = new HashMap<>();
//        for (Map.Entry<String, String> entry : shopWarehouseCodeMap.entrySet()) {
//            if (ObjectUtil.isNotEmpty(entry.getValue())) {
//                warehousecodeNameMap.put(entry.getValue(), entry.getKey());
//            }
//        }

        List<ShipmentApplyExcelParam> checkFailList = new ArrayList<>();
        for (ShipmentApplyExcelParam excelParam : excelDataList) {
            if (!shopWarehouseCodeMap.containsKey(excelParam.getReceiveWarehouse())) {
                checkFailList.add(excelParam);
            }
        }

        if (ObjectUtil.isNotEmpty(checkFailList)) {
            String collect = checkFailList.stream().map(i -> i.getSysShopsName() + " " + i.getSysSite() + " " + i.getSku() + " " + i.getReceiveWarehouse()).collect(Collectors.joining("\n"));
            return ResponseData.error(500, StrUtil.format("以下{}条表格录入的数据,未能匹配上正确的仓库信息:\n{}", checkFailList.size(), collect), checkFailList);
        }

        //返回解析成功的数据

        for (ShipmentApplyExcelParam excelParam : excelDataList) {

            if (ObjectUtil.isEmpty(excelParam.getSku())
                    || ObjectUtil.isEmpty(excelParam.getSysShopsName())
                    || ObjectUtil.isEmpty(excelParam.getSysSite())) {
                continue;
            }

            String key = excelParam.getSysShopsName() + "_" + excelParam.getSysSite() + "_" + excelParam.getSku();

            if (!productSkuMap.containsKey(key)) {
                continue;
            }
            List<InvProductGallery> invProductGalleryList = productSkuMap.get(key);

            if (ObjectUtil.isEmpty(invProductGalleryList)) {
                continue;
            }
            InvProductGallery ipg = invProductGalleryList.get(0);

            List<Material> materials = materialMap.get(ipg.getMaterialCode());
            if (ObjectUtil.isEmpty(materials)) {
                continue;
            }
            Material material = materials.get(0);

            String materialJsonStr = JSONUtil.toJsonStr(material);

            ShipmentApplyItem item = new ShipmentApplyItem();
            BeanUtil.copyProperties(ipg, item);
            item.setMaterialCode(ipg.getMaterialCode());
            item.setMaterialInfo(materialJsonStr);
            item.setProductName(material.getProductName());
            item.setProductType(material.getProductType());
            item.setCreatedTime(new Date());

            //表格录入数据

            item.setReceiveWarehouse(excelParam.getReceiveWarehouse());//表格导入时 填写的是仓库名称
            item.setReceiveWarehouseCode(shopWarehouseCodeMap.get(excelParam.getReceiveWarehouse()));

            if (ObjectUtil.isEmpty(deliveryMap.get(excelParam.getDeliverypoint()))) {
                return ResponseData.error(500, "{}未能匹配上正确的发货点编码", excelParam.getDeliverypoint());
            } else {
                item.setDeliverypoint(excelParam.getDeliverypoint());
                item.setDeliverypointNo(deliveryMap.get(excelParam.getDeliverypoint()));
            }

            item.setSendQty(excelParam.getSendQty());
            item.setTransportationType(excelParam.getTransportationType());
            item.setUnwType(excelParam.getUnwType());
            item.setRemark1(excelParam.getRemark1());
            item.setRemark2(excelParam.getRemark2());
            item.setRemark3(excelParam.getRemark3());
            item.setDataSourceType("表格导入");
            item.setPlatform("Amazon");
            applyItemList.add(item);
        }

        //按物料编码排序
        if (ObjectUtil.isNotEmpty(applyItemList)) {
            applyItemList = applyItemList.stream().sorted(Comparator.comparing(ShipmentApplyItem::getMaterialCode)).collect(Collectors.toList());
        }

        return ResponseData.success(applyItemList);
    }


    /**
     * 查询申请单数据
     *
     * @param applyStauts 申请单状态
     * @param bizType     业务类型 SQ申请,SH审核
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    public ResponseData applyList(String applyStauts, String bizType) {

        //查询当前登录人，所申请的
        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        if (ObjectUtil.isNull(currentUser)) {
            return ResponseData.error("未获取到登录信息！");
        }

        LambdaQueryWrapper<ShipmentApplyItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(applyStauts), ShipmentApplyItem::getApplyStatus, applyStauts);

        if ("SQ".equalsIgnoreCase(bizType)) {//申请看个人数据
            queryWrapper.eq(ShipmentApplyItem::getApllyPersonNo, currentUser.getAccount());
        } else if ("SH".equalsIgnoreCase(bizType)) {//审核看全部门数据
            queryWrapper.eq(ShipmentApplyItem::getDepartment, currentUser.getDepartment());
        }

//        queryWrapper.groupBy(ShipmentApplyItem::getApplyBatchNo);
        queryWrapper.orderByDesc(ShipmentApplyItem::getApplyBatchNo);
        List<ShipmentApplyItem> applyItemList = mapper.selectList(queryWrapper);

        if (ObjectUtil.isNotEmpty(applyItemList)) {
            Map<String, List<ShipmentApplyItem>> resulMap = applyItemList.stream().collect(Collectors.groupingBy(ShipmentApplyItem::getApplyBatchNo));
            Collection<List<ShipmentApplyItem>> listCollection = resulMap.values();

            return ResponseData.success(listCollection);
        }
        return ResponseData.success("未找到发货申请数据");
    }


    @Override
    @DataSource(name = "stocking")
    public ResponseData verifyList(ShipmentTrackingParam trackingParam) {
//        LoginUser loginUser = LoginContext.me().getLoginUser();
//        if (ObjectUtil.isNull(loginUser) || ObjectUtil.isEmpty(loginUser.getDepartment())) {
//            return ResponseData.error("未获取到登录信息！");
//        }
//        trackingParam.setDepartment(loginUser.getDepartment());
        List<ShipmentApplyItem> applyItemList = mapper.selectVerifyListDatalimit(trackingParam);
        return ResponseData.success(applyItemList);
    }


    @Override
    @DataSource(name = "stocking")
    public ResponseData exportVerifyList(ShipmentTrackingParam trackingParam) {
        String userAccount = this.getUserAccount();
// 测试导出
//        if (ObjectUtil.isEmpty(userAccount)) {
//            userAccount = "S20160058";
//        }
        if (ObjectUtil.isEmpty(userAccount)) {
            return ResponseData.error("未获取到登录信息！");
        }
        List<ExportVerifyListResult> applyItemList = mapper.exportVerifyList(trackingParam);
        return ResponseData.success(applyItemList);
    }


    /**
     * 发货分析
     *
     * @param applyItemList
     * @param again         属于重新分析 ture是 ，false 否
     * @param needSave      是否保存更新分析结果 ture是 ，false 否
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData shipmentAnalyze(List<ShipmentApplyItem> applyItemList, boolean again, boolean needSave) {
        if (ObjectUtil.isEmpty(applyItemList)) {
            return ResponseData.error("待分析数据不能为空");
        }
        //提交分析的数据如果是excel导入的，已经在导入的时候就去亚马逊sku表做了校验,因此分析时不去校验

        Map<String, List<ShipmentApplyItem>> groupItemD6Map = applyItemList.stream().collect(Collectors.groupingBy(
                a -> a.getPlatform() + a.getArea() + a.getDepartment() + a.getTeam() + a.getMaterialCode() + a.getAsin()));

        //setp1:校验是否存在重复、零散发货（判断规则为【站点+SKU+发货方式】相同）
        //重复、零散发货 前端校验
        Map<String, List<ShipmentApplyItem>> repeatApplyMap = new HashMap<>();
        //单一申请，没有重复的
        Map<String, List<ShipmentApplyItem>> singleApplyMap = new HashMap<>();

        for (Map.Entry<String, List<ShipmentApplyItem>> entry : groupItemD6Map.entrySet()) {
            String groupFild = entry.getKey();
            List<ShipmentApplyItem> itemList = entry.getValue();

            //校验是否存在重复、零散发货（判断规则为【D6维度+SKU+发货方式】相同）
            Map<String, List<ShipmentApplyItem>> siteSkuModeMap = itemList.stream()
                    .collect(Collectors.groupingBy(a ->
                            a.getPlatform() + a.getArea() + a.getDepartment() + a.getTeam() + a.getMaterialCode() + a.getAsin() +
                                    a.getSku() + a.getTransportationType() + a.getApplyBatchNo()));

            boolean isRepeat = false;
            for (Map.Entry<String, List<ShipmentApplyItem>> siteSkuModeEntry : siteSkuModeMap.entrySet()) {
                if (siteSkuModeEntry.getValue().size() > 1) {
                    //存在重复、零散发货 改D6维度的数据终止分析
                    isRepeat = true;
                    repeatApplyMap.put(groupFild, itemList);
                    break;
                }
            }
            if (!isRepeat) { //没有重复的数据
                singleApplyMap.put(groupFild, itemList);
            }
        }

        if (ObjectUtil.isNotEmpty(repeatApplyMap)) {
            return ResponseData.error("存在重复、零散发货 ,请去重合并后再分析！");
        }

//        关联数据分析
        //a)按【平台+区域+事业部+Team+物料编码+ASIN】关联本日发货申请推荐记录，获取发货申请所需求信息项追加至本批次；（注，如未关联上推荐记录，所有日期型、数值型信息项为Null，字符型信息项为“-”）
        List<String> groupKeys = singleApplyMap.keySet().stream().collect(Collectors.toList());

        List<ShipmentRecommendation> recommendationList = recommendationService.getListByPK(groupKeys);

        boolean noRecomData = false;
        if (ObjectUtil.isEmpty(recommendationList)) {
            log.info("所有提交缺少每日发货推荐的关联数据");
            noRecomData = true;
//            return ResponseData.error("所有提交缺少每日发货推荐的关联数据");
        }

        Map<String, List<ShipmentRecommendation>> recMap = null;
        if (!noRecomData) {
            recMap = recommendationList.stream().collect(
                    Collectors.groupingBy(r -> r.getPlatform() + r.getPreArea() + r.getDepartment() + r.getTeam() + r.getMaterialCode() + r.getAsin()));
        }


        //没匹配上推荐记录的数据
        List<ShipmentApplyItem> noRecommendation = new ArrayList<>();

        //合理分析
        List<ShipmentApplyItemResult> analyzeResultList = new ArrayList<>();

        //分析结果需要更新到数据库
        List<ShipmentApplyItem> analyzeResultUpdateList = new ArrayList<>();

        for (Map.Entry<String, List<ShipmentApplyItem>> singleEntry : singleApplyMap.entrySet()) {
            String key = singleEntry.getKey();
            List<ShipmentApplyItem> itemList = singleEntry.getValue();

            if (noRecomData || !recMap.containsKey(key)) {
                //未匹配到推荐记录
                //是否再次分析，需要更新批次号，自选创建，申请人  数据已经保存过了

                String newShipmentBatchNo = generatorShipmentNoUtil.getBatchNo("FHSQ");
                Date date = new Date();
                for (ShipmentApplyItem item : itemList) {
                    if (again) {
                        item.setApplyBatchNo(newShipmentBatchNo);
                        item.setApplyDate(date);
                        item.setDataSourceType("自选创建");
                    }
                    item.setReasonableCheck("-");
                    //没有推荐值，也需要查询库存信息
                    ShipmentApplyItemResult itemResult = new ShipmentApplyItemResult();
                    BeanUtil.copyProperties(item, itemResult);
                    analyzeResultList.add(itemResult);
                }
                noRecommendation.addAll(itemList);
                continue;
            }

            ShipmentRecommendation recommendation = recMap.get(key).get(0);

            analyzeResultList.addAll(this.batchSkuAnalyze(itemList, recommendation, again, analyzeResultUpdateList));

        }

        if (needSave) {
            if (ObjectUtil.isNotEmpty(noRecommendation)) {
                this.saveOrUpdateBatch(noRecommendation);//无推荐记录的也允许保存
            }
            if (ObjectUtil.isNotEmpty(analyzeResultUpdateList)) {
                this.saveOrUpdateBatch(analyzeResultUpdateList);
            }
        }

        //库存信息

        return ResponseData.success(this.inventoryCheck(analyzeResultList));
    }

    /**
     * 库存校验
     *
     * @param resultItemrList
     */
    @DataSource(name = "stocking")
    private List<ShipmentApplyItemResult> inventoryCheck(List<ShipmentApplyItemResult> resultItemrList) {

        //1--获取erp库存
        Set<String> teamMatCodeDeliverypointNoSet = new TreeSet<>();
        Set<String> teamSet = new TreeSet<>();
        Set<String> matCodeSet = new TreeSet<>();
        Set<String> deliverypointNoSet = new TreeSet<>();
        Set<String> applyBatchNoSet = new HashSet<>();

        for (ShipmentApplyItemResult s : resultItemrList) {
            String deliverypointNo = s.getDeliverypointNo();
            if (ObjectUtil.isEmpty(deliverypointNo)) {
                deliverypointNo = "FHD06";
            }

            if (ObjectUtil.isNotEmpty(s.getApplyBatchNo())) {
                applyBatchNoSet.add(s.getApplyBatchNo());
            }

            teamMatCodeDeliverypointNoSet.add(s.getTeam() + s.getMaterialCode() + deliverypointNo);
            teamSet.add(s.getTeam());
            matCodeSet.add(s.getMaterialCode());
            deliverypointNoSet.add(deliverypointNo);
        }

        List<ErpTeamAvailableQytResult> availableQytList = trackingTransferService.erpAvailableQty(teamSet, matCodeSet, deliverypointNoSet, teamMatCodeDeliverypointNoSet);
        boolean allNoAvailableQty = false;

        Map<String, ErpTeamAvailableQytResult> erpQtyMap = new HashMap<>();

        if (ObjectUtil.isEmpty(availableQytList)) {
            allNoAvailableQty = true;
        } else {
            for (ErpTeamAvailableQytResult result : availableQytList) {
                erpQtyMap.put(result.getMergeField3(), result);
            }
        }


        //2--占用库存 (team+物料+发货点编码)  跟踪记录的状态 申请执行状态：还未同步到erp的申请单

        List<String> applyBatchNoList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(applyBatchNoSet)) {
            applyBatchNoList = applyBatchNoSet.stream().collect(Collectors.toList());
        }

        List<OccupyQytResult> allOccupyList = trackingTransferService.batchQueryOccupyQty(teamMatCodeDeliverypointNoSet, applyBatchNoList);

        boolean allNoOccupyQyt = false;
        Map<String, OccupyQytResult> allOccupyMap = new HashMap<>();

        if (ObjectUtil.isEmpty(allOccupyList)) {
            allNoOccupyQyt = true;
        } else {
            for (OccupyQytResult result : allOccupyList) {
                allOccupyMap.put(result.getMergeField3(), result);
            }

        }


        //设置erp可调拨数量 和 占用库存数量
        for (ShipmentApplyItemResult item : resultItemrList) {

            BigDecimal erpCanTransferQty = BigDecimal.ZERO;
            String deliverypointNo = item.getDeliverypointNo();
            if (ObjectUtil.isEmpty(deliverypointNo)) {
                deliverypointNo = "FHD06";
            }
            String mergeField3 = item.getTeam() + item.getMaterialCode() + deliverypointNo;

            if (!allNoAvailableQty && ObjectUtil.isNotEmpty(erpQtyMap.get(mergeField3))) {
                erpCanTransferQty = erpQtyMap.get(mergeField3).getQty();
            }
            item.setErpCanTransferQty(erpCanTransferQty);

            BigDecimal occupyQyt = BigDecimal.ZERO;
            if (!allNoOccupyQyt && ObjectUtil.isNotEmpty(allOccupyMap.get(mergeField3))) {
                occupyQyt = allOccupyMap.get(mergeField3).getQty();

            }
            item.setOccupyQty(occupyQyt);
        }
        //按物料编码排序
        if (ObjectUtil.isNotEmpty(resultItemrList)) {
            resultItemrList = resultItemrList.stream().sorted(Comparator.comparing(ShipmentApplyItemResult::getMaterialCode)).collect(Collectors.toList());
        }
        return resultItemrList;
    }


    /**
     * 按批次多个sku分析
     *
     * @param itemList       通一D6维度的sku集合
     * @param recommendation BI推荐数据
     * @param again          再次分析
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    public List<ShipmentApplyItemResult> batchSkuAnalyze(List<ShipmentApplyItem> itemList, ShipmentRecommendation recommendation, boolean again, List<ShipmentApplyItem> analyzeResultUpdateList) {
        //b)实时计算分析“发货后周转天数”，追加至本批次；（注，如未关联上推荐记录，该信息项为Null）
        //计算规则(AZ海外总库存D6+申请发货数量)/日均销量

        //AZ海外总库存D6
        BigDecimal azOverseaTotalQty = ObjectUtil.isNull(recommendation.getAzOverseaTotalQty()) ? BigDecimal.ZERO : recommendation.getAzOverseaTotalQty();

        //申请发货数量
        BigDecimal sumSendQty = itemList.stream().map(ShipmentApplyItem::getSendQty).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal dayAvgSell = recommendation.getDayavgsell();
        BigDecimal afterTurnoverDay = BigDecimal.ZERO;
        boolean needReasonableCheck = Boolean.TRUE;
        if (ObjectUtil.isNull(dayAvgSell) || dayAvgSell.equals(BigDecimal.ZERO)) {
            //日均销量为0
            afterTurnoverDay = BigDecimal.valueOf(-99999);
            needReasonableCheck = Boolean.FALSE;
        } else {
            afterTurnoverDay = azOverseaTotalQty.add(sumSendQty).divideToIntegralValue(dayAvgSell);
        }

        //合理性分析

        String reasonableCheckResult = "-";

        if (needReasonableCheck) {

            if (afterTurnoverDay.compareTo(recommendation.getSendTotalDays().multiply(BigDecimal.valueOf(1.05))) > 0) {
                //发货后周转超标：	发货后周转天数 > 总发货天数*105%
                reasonableCheckResult = "发货后周转超标!";

            } else if (afterTurnoverDay.compareTo(recommendation.getSendTotalDays().multiply(BigDecimal.valueOf(0.95))) <= 0
                    && recommendation.getDomesticTransferAvailQty().subtract(sumSendQty).compareTo(BigDecimal.ZERO) > 0) {
                //发货不足：（发货申请）【发货后周转天数 <= 总发货天数*95% && （国内仓可调拨数量-发货申请占用量）> 0 】---	发货不足
                reasonableCheckResult = "发货不足";

            } else if (afterTurnoverDay.compareTo(recommendation.getSendTotalDays().multiply(BigDecimal.valueOf(0.95))) >= 0
                    && afterTurnoverDay.compareTo(recommendation.getSendTotalDays().multiply(BigDecimal.valueOf(1.05))) <= 0) {
                //正常发货：（发货申请）【总发货天数*95%<=发货后周转天数 <= 总发货天数*105% 】---	正常发货
                reasonableCheckResult = "正常发货";
            }
        }

        //是否再次分析，需要更新批次号，自选创建，申请人  数据已经保存过了
        if (again) {
            String newShipmentBatchNo = generatorShipmentNoUtil.getBatchNo("FHSQ");
            Date date = new Date();
            for (ShipmentApplyItem item : itemList) {
                item.setApplyBatchNo(newShipmentBatchNo);
                item.setApplyDate(date);
                item.setDataSourceType("自选创建");
            }

        }

        List<ShipmentApplyItemResult> itemResultList = new ArrayList<>();
        for (ShipmentApplyItem item : itemList) {
            //发货后周转天数
            item.setTurnoverAfterSendDays(afterTurnoverDay);
            //合理性分析结果
            item.setReasonableCheck(reasonableCheckResult);

            analyzeResultUpdateList.add(item);

            ShipmentApplyItemResult itemResult = new ShipmentApplyItemResult();
            BeanUtil.copyProperties(item, itemResult);
            itemResult.setTurnoverBeforeSendDays(recommendation.getTurnoverBeforeSendDays());
            itemResult.setDomesticRecommSendQty(recommendation.getDomesticRecommSendQty());
            itemResultList.add(itemResult);
        }

        return itemResultList;
    }


    /**
     * erp系统team下的可用物料数量
     *
     * @param team
     * @param materialCode
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    public ErpTeamAvailableQytResult erpTeamAvailableQty(String team, String materialCode, String deliverypointNo) {
        Set<String> mergeFieldList = new TreeSet<>();
        mergeFieldList.add(team + materialCode + deliverypointNo);
        List<ErpTeamAvailableQytResult> resultList = trackingTransferService.erpTeamAvailableQty(mergeFieldList);
        if (ObjectUtil.isNotEmpty(resultList)) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * erp系统team下的可用物料数量
     *
     * @param team
     * @param materialCode
     * @param deliverypointNo
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    public ErpTeamAvailableQytResult erpAvailableQty(String team, String materialCode, String deliverypointNo) {


        Set<String> teamSet = new TreeSet<>();
        teamSet.add(team);

        Set<String> materialCodeSet = new TreeSet<>();
        materialCodeSet.add(materialCode);


        Set<String> deliverypointNoSet = new TreeSet<>();
        deliverypointNoSet.add(deliverypointNo);

        Set<String> mergeFieldList = new TreeSet<>();
        mergeFieldList.add(team + materialCode + deliverypointNo);


        List<ErpTeamAvailableQytResult> resultList = trackingTransferService.erpAvailableQty(teamSet, materialCodeSet, deliverypointNoSet, mergeFieldList);
        if (ObjectUtil.isNotEmpty(resultList)) {
            return resultList.get(0);
        }
        return null;
    }


    /**
     * 发货重新分析
     *
     * @param applyBatchNoList
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData shipmentAgainAnalyze(List<String> applyBatchNoList) {
        LambdaQueryWrapper<ShipmentApplyItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ShipmentApplyItem::getApplyBatchNo, applyBatchNoList);
        List<ShipmentApplyItem> applyItemList = this.baseMapper.selectList(wrapper);
        if (ObjectUtil.isEmpty(applyBatchNoList)) {
            return ResponseData.error("传入批次号不正确,未能找到需要分析的明细数据");
        }
        ResponseData analyzeResult = this.shipmentAnalyze(applyItemList, true, true);

        return analyzeResult;
    }


    /**
     * 根据批次号删除申请
     *
     * @param applyBatchNoList
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    public ResponseData deleteByBatchNo(List<String> applyBatchNoList) {
        if (ObjectUtil.isEmpty(applyBatchNoList)) {
            return ResponseData.error("传入批次号不能为空！");
        }
        LambdaQueryWrapper<ShipmentApplyItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ShipmentApplyItem::getApplyBatchNo, applyBatchNoList);
        if (this.remove(wrapper)) {
            return ResponseData.success();
        }
        return ResponseData.error("删除失败！");
    }


    /**
     * 分析并保存
     *
     * @param applyItemList
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData analyzeSave(List<ShipmentApplyItem> applyItemList) {

        //重新初始化 billNo  ,有可能编辑的时候更改了发货仓库
        //获取调入仓编码
        Set<String> warehouseSet = applyItemList.stream().map(s -> "Amazon_" + s.getSysShopsName() + "_" + s.getSysSite() + "_仓库").collect(Collectors.toSet());
        Map<String, String> shopWarehouseCodeMap = otherModularService.getWarehouseCode(warehouseSet);
        if (ObjectUtil.isEmpty(shopWarehouseCodeMap)) {
            return ResponseData.error("未获取到店铺调入仓code！");
        }

        //item 可能有新增，有删除，有修改
        ShipmentApplyItem item = applyItemList.stream().filter(a -> ObjectUtil.isNotEmpty(a.getApplyBatchNo())).findFirst().orElse(null);
        if (ObjectUtil.isNull(item)) {
            return ResponseData.error("分析的明细数据缺少批次编号");
        }
        List<String> batchNoList = Arrays.asList(item.getApplyBatchNo());
        this.deleteByBatchNo(batchNoList);

        for (ShipmentApplyItem applyItem : applyItemList) {
            String warehouseCode = shopWarehouseCodeMap.get("Amazon_" + applyItem.getSysShopsName() + "_" + applyItem.getSysSite() + "_仓库");
            String billNo = generatorShipmentNoUtil.getBillNo(applyItem.getUnwType(), warehouseCode);
            applyItem.setOrgCode(warehouseCode);
            applyItem.setBillNo(billNo);
            if (applyItem.getReceiveWarehouse().startsWith("Amazon")) {
                applyItem.setReceiveWarehouseCode(shopWarehouseCodeMap.get(applyItem.getReceiveWarehouse()));//店铺调入仓编码
            } else {
                applyItem.setReceiveWarehouseCode(applyItem.getReceiveWarehouse());//海外仓调入编码
            }

            if (ObjectUtil.isEmpty(applyItem.getDeliverypointNo())) {
                applyItem.setDeliverypointNo("FHD06");
                applyItem.setDeliverypoint("联泰");
            }
        }
        ResponseData responseData = this.shipmentAnalyze(applyItemList, false, true);
        return responseData;
    }


    /**
     * 自选创建-保存
     *
     * @param itemGroupList
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData createSave(List<List<ShipmentApplyItem>> itemGroupList) {
        if (ObjectUtil.isEmpty(itemGroupList)) {
            return ResponseData.error("自选创建申请项不能为空！");
        }

        //查询当前登录人，所申请的
        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        if (ObjectUtil.isNull(currentUser)) {
            return ResponseData.error("未获取到登录信息！");
        }

        //获取调入仓编码
        Set<String> warehouseSet = itemGroupList.stream().flatMap(
                itemList -> itemList.stream().map(s -> "Amazon_" + s.getSysShopsName() + "_" + s.getSysSite() + "_仓库")
        ).collect(Collectors.toSet());


        Map<String, String> shopWarehouseCodeMap = new HashMap<>();

        shopWarehouseCodeMap = otherModularService.getWarehouseCode(warehouseSet);

        if (ObjectUtil.isEmpty(shopWarehouseCodeMap)) {
            return ResponseData.error("未获取到店铺调入仓code！");
        }

        List<ShipmentApplyItem> watiSaveItem = new ArrayList<>();
        Date date = new Date();
        String creatBatchNo = generatorShipmentNoUtil.getBatchNo("FHSQ-CJ");//发货申请创建批次号内部系统使用
        for (List<ShipmentApplyItem> applyItemList : itemGroupList) {
            if (ObjectUtil.isEmpty(applyItemList)) {
                continue;
            }
            String applyBatchNo = generatorShipmentNoUtil.getBatchNo("FHSQ");
            for (ShipmentApplyItem applyItem : applyItemList) {
                String warehouseCode = shopWarehouseCodeMap.get("Amazon_" + applyItem.getSysShopsName() + "_" + applyItem.getSysSite() + "_仓库");
                String billNo = generatorShipmentNoUtil.getBillNo(applyItem.getUnwType(), warehouseCode);
                applyItem.setOrgCode(warehouseCode);
                applyItem.setBillNo(billNo);
                applyItem.setApplyDate(date);
                applyItem.setBatchNo(creatBatchNo);
                applyItem.setApplyBatchNo(applyBatchNo);
                applyItem.setApllyPerson(currentUser.getName());
                applyItem.setApllyPersonNo(currentUser.getAccount());
                applyItem.setCreatedBy(currentUser.getName());
                if (applyItem.getReceiveWarehouse().startsWith("Amazon")) {
                    applyItem.setReceiveWarehouseCode(shopWarehouseCodeMap.get(applyItem.getReceiveWarehouse()));//店铺调入仓编码
                } else {
                    applyItem.setReceiveWarehouseCode(applyItem.getReceiveWarehouse());//海外仓调入编码
                }

                if (ObjectUtil.isEmpty(applyItem.getDeliverypointNo())) {
                    applyItem.setDeliverypointNo("FHD06");
                    applyItem.setDeliverypoint("联泰");
                }

                watiSaveItem.add(applyItem);
            }
        }
        if (ObjectUtil.isNotEmpty(watiSaveItem)) {
            if (this.saveBatch(watiSaveItem)) {
                return ResponseData.success(watiSaveItem);
            }
        }
        return ResponseData.error("自选创建-保存异常");
    }

    /**
     * @param itemList
     * @return
     */

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData batchSave(List<ShipmentApplyItem> itemList) {
        if (this.saveBatch(itemList)) {

            return ResponseData.success();
        }
        return ResponseData.error("保存失败");
    }

    /**
     * 获取仓库编码
     *
     * @param warehouseSet
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> getWarehouseCode(Set<String> warehouseSet) {

        return otherModularService.getWarehouseCode(warehouseSet);
    }

    /**
     * 自选创建-保存并提交
     *
     * @param itemGroupList
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData createSaveComit(List<List<ShipmentApplyItem>> itemGroupList) {
        ResponseData saveResponse = this.createSave(itemGroupList);
        if (!saveResponse.getSuccess()) {
            return saveResponse;
        }
        //
        List<ShipmentApplyItem> itemList = (List<ShipmentApplyItem>) saveResponse.getData();
        List<String> applyBatchNoList = itemList.stream().map(ShipmentApplyItem::getApplyBatchNo).collect(Collectors.toSet())
                .stream().collect(Collectors.toList());

        return this.comitByBatchNo(applyBatchNoList);
    }

    /**
     * 提交申请
     *
     * @param applyBatchNoList 批次编号List
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData comitByBatchNo(List<String> applyBatchNoList) {
        if (ObjectUtil.isEmpty(applyBatchNoList)) {
            return ResponseData.error("申请批次号不能为空！");
        }

        int applySize = applyBatchNoList.size();
        List<String> allFailSku = new ArrayList<>();
        List<String> failApplyNo = new ArrayList<>();

        //sku校验
        for (String applyBatchNo : applyBatchNoList) {
            List<SkuCheckParam> skuCheckParamList = new LambdaQueryChainWrapper<>(mapper).eq(ShipmentApplyItem::getApplyBatchNo, applyBatchNo).list().stream()
                    .map(s -> SkuCheckParam.builder().sku(s.getSku()).shopName(s.getSysShopsName()).site(s.getSysSite()).applyBatchNo(s.getApplyBatchNo()).build())
                    .filter(s -> ObjectUtil.isNotEmpty(s.getSku()) || ObjectUtil.isNotEmpty(s.getShopName()) || ObjectUtil.isNotEmpty(s.getSite())).collect(Collectors.toList());
            if (ObjectUtil.isEmpty(skuCheckParamList)) {
                return ResponseData.error(StrUtil.format("申请批次号{}未找到sku明细数据！", applyBatchNo));
            }
            List<String> failSku = recommendationService.skuBatchCheck(skuCheckParamList);
            if (ObjectUtil.isNotEmpty(failSku)) {
                failApplyNo.add(applyBatchNo);
                allFailSku.addAll(failSku);
            }
        }
        applyBatchNoList.removeAll(failApplyNo);


        if (ObjectUtil.isEmpty(applyBatchNoList)) {
            return ResponseData.error(StrUtil.format("SKU校验未通过！{}", allFailSku));
        }


        //库存校验
        List<String> successBatchNo = this.verifComitCheck(applyBatchNoList, true);

        if (ObjectUtil.isEmpty(successBatchNo)) {
            return ResponseData.error(500, "提交的数据按照D6维度进行库存校验，以下批次的发货申请单未通过库存校验【" + JSONUtil.toJsonStr(applyBatchNoList) + "】");
        }


        LambdaQueryWrapper<ShipmentApplyItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ShipmentApplyItem::getApplyBatchNo, successBatchNo);
        queryWrapper.eq(ShipmentApplyItem::getApplyStatus, 0);
        List<ShipmentApplyItem> applyItemList = this.list(queryWrapper);

        if (ObjectUtil.isEmpty(applyItemList)) {
            return ResponseData.error("申请批次号下未找到sku明细数据！");
        }


        Map<String, List<ShipmentApplyItem>> itemGroupMap = applyItemList.stream()
                .collect(Collectors.groupingBy(a -> a.getPlatform() + a.getArea() + a.getDepartment() + a.getTeam() + a.getMaterialCode() + a.getAsin()));

        //获取applyBatchNoList对应的applyBatchNo对应物料编码;
        Map<String, List<String>> matCodeMap = applyItemList.stream().collect(Collectors.groupingBy(ShipmentApplyItem::getApplyBatchNo, Collectors.mapping(ShipmentApplyItem::getMaterialCode, Collectors.toList())));

        List<String> groupFieldsPk = itemGroupMap.keySet().stream().collect(Collectors.toList());

        List<ShipmentRecommendation> recommendationList = recommendationService.getListByPK(groupFieldsPk);

        ArrayList<ShipmentApplyItem> waitUpdateItem = new ArrayList<>();

        boolean noRecomData = false;

        if (ObjectUtil.isEmpty(recommendationList)) {

            log.info("[" + JSONUtil.toJsonStr(groupFieldsPk) + "]提交时未找到BI发货推荐数据！");
            noRecomData = true;
        }
        Map<String, ShipmentRecomSnapshot> saveRecomGroup = new HashMap<>();
        Map<String, List<ShipmentRecommendation>> recomGroupMap = null;

        if (!noRecomData) {
            recomGroupMap = recommendationList.stream()
                    .collect(Collectors.groupingBy(a -> a.getPlatform() + a.getPreArea() + a.getDepartment() + a.getTeam() + a.getMaterialCode() + a.getAsin()));
        }

        for (Map.Entry<String, List<ShipmentApplyItem>> itemEntry : itemGroupMap.entrySet()) {
            String groupFields = itemEntry.getKey();
            //取快照ID
            BigDecimal RecomSnapshotId = null;
            if (!noRecomData) {
                List<ShipmentRecommendation> recomList = recomGroupMap.get(groupFields);
                if (ObjectUtil.isNotEmpty(recomList)) {
                    ShipmentRecommendation recommendation = recomList.get(0);
                    ShipmentRecomSnapshot snapshot = new ShipmentRecomSnapshot();
                    BeanUtil.copyProperties(recommendation, snapshot);
                    saveRecomGroup.put(groupFields, snapshot);
                    RecomSnapshotId = recommendation.getId();
                }
            }

            for (ShipmentApplyItem item : itemEntry.getValue()) {
                item.setRecomSnapshotId(RecomSnapshotId);
                item.setApplyStatus(1);
                item.setUpdatedTime(new Date());
                waitUpdateItem.add(item);
            }
        }


        //保持推荐数据快照
        if (ObjectUtil.isNotEmpty(saveRecomGroup)) {
            shipmentRecomSnapshotService.saveOrUpdateBatch(saveRecomGroup.values());
        }

        //插入跟踪记录
        List<ShipmentTracking> shipmentTrackingList = new ArrayList<>();
        Map<String, List<ShipmentApplyItem>> waitUpdateItemGroupMap = waitUpdateItem.stream().collect(Collectors.groupingBy(ShipmentApplyItem::getApplyBatchNo));
        for (Map.Entry<String, List<ShipmentApplyItem>> entry : waitUpdateItemGroupMap.entrySet()) {
            String applyBatchNo = entry.getKey();
            List<ShipmentApplyItem> itemList = entry.getValue();
            BigDecimal sumQty = itemList.stream().map(ShipmentApplyItem::getSendQty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Set transportationSet = itemList.stream().map(ShipmentApplyItem::getTransportationType).collect(Collectors.toSet());
            ShipmentApplyItem applyItem = itemList.get(0);
            ShipmentTracking shipmentTracking = new ShipmentTracking();
            BeanUtil.copyProperties(applyItem, shipmentTracking);
            shipmentTracking.setSendQty(sumQty);
            shipmentTracking.setTransportationType(transportationSet.toString());
            List<String> matCodeList = matCodeMap.get(applyBatchNo);
            if (ObjectUtil.isNotEmpty(matCodeList)) {
                Material materialInfo = materialService.queryMaterialInfo(matCodeList.get(0));
                shipmentTracking.setMaterialInfo(JSONUtil.toJsonStr(materialInfo));
            }
            shipmentTrackingList.add(shipmentTracking);
        }
        shipmentTrackingService.saveBatch(shipmentTrackingList);

        //更新申请明细项
        this.updateBatchById(waitUpdateItem);


        if (successBatchNo.size() < applySize) {
            //库存校验没通过的批次号
            applyBatchNoList.removeAll(successBatchNo);
            String skuMsg = allFailSku.size() > 0 ? StrUtil.format("发货申请申请失败: [发货标签未同步]:【{}】\n", JSONUtil.toJsonStr(allFailSku)) : "";
            String invMsg = applyBatchNoList.size() > 0 ? "提交的数据按照D6维度进行库存校验，以下批次的发货申请单未通过库存校验【" + JSONUtil.toJsonStr(applyBatchNoList) + "】" : "";
            return ResponseData.error(500, skuMsg + invMsg);
        }

        return ResponseData.success();
    }


    /**
     * 发货审核-通过-不通过
     *
     * @param verifParam
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData verif(VerifParam verifParam) {
        log.info("发货审核请求参数【{}】时间【{}】", JSONUtil.toJsonStr(verifParam), new Date());
        VerifParam cloneVerifParm = BeanUtil.copyProperties(verifParam, VerifParam.class);


        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        if (ObjectUtil.isNull(currentUser)) {
            return ResponseData.error("请登录后再审核");
        }

        List<String> batchNoList = verifParam.getApplyBatchNoList();
        //重复提交校验
        ResponseData responseData = this.repeatCheck(cloneVerifParm);
        if (!responseData.getSuccess()) {
            return responseData;
        }
        try {
            boolean verifResult = verifParam.getStatus() == 1 ? true : false;

            //同步成功的批次号更新数据状态
            List<String> successBatchNo = new ArrayList<>();
            List<String> createTransferSuccess = new ArrayList<>();
            List<String> createTransferFail = new ArrayList<>();
            if (verifResult) {

                //库存校验：
                successBatchNo = this.verifComitCheck(batchNoList, false);

                //库存校验通过的批次号
                if (ObjectUtil.isEmpty(successBatchNo)) {
                    return ResponseData.error(500, "审核通过的数据按照D6维度进行库存校验，以下批次申请单未通过库存校验【" + JSONUtil.toJsonStr(batchNoList) + "】");
                } else {
                    batchNoList.removeAll(successBatchNo);
                    //库存校验通过--同步erp
                    createTransferSuccess = otherModularService.createTransfer(successBatchNo, verifParam, currentUser);
                    if (ObjectUtil.isNull(createTransferSuccess)) {
                        return ResponseData.error("创建调拨单时-数据状态已经发生变化，请刷新页面!");
                    }

                }


            } else {
                //审核不通过的，直接批量更新申请明细
                LambdaUpdateWrapper<ShipmentApplyItem> applyItemUpdateWrapper = new LambdaUpdateWrapper<>();
                applyItemUpdateWrapper.set(ObjectUtil.isNotEmpty(verifParam.getReson()), ShipmentApplyItem::getCheckReason, verifParam.getReson());
                applyItemUpdateWrapper.set(ShipmentApplyItem::getCheckStatus, verifParam.getStatus());
                applyItemUpdateWrapper.set(ShipmentApplyItem::getCheckPerson, currentUser.getName());
                applyItemUpdateWrapper.set(ShipmentApplyItem::getCheckPersonNo, currentUser.getAccount());
                applyItemUpdateWrapper.set(ShipmentApplyItem::getUpdatedTime, new Date());
                applyItemUpdateWrapper.in(ShipmentApplyItem::getApplyBatchNo, batchNoList);
                this.update(applyItemUpdateWrapper);
            }


            //更新追踪记录状态
            LambdaUpdateWrapper<ShipmentTracking> trackingUpdateWrapper = new LambdaUpdateWrapper<>();
            trackingUpdateWrapper.set(ObjectUtil.isNotEmpty(verifParam.getReson()), ShipmentTracking::getCheckReason, verifParam.getReson());
            trackingUpdateWrapper.set(ShipmentTracking::getCheckStatus, verifParam.getStatus());
            trackingUpdateWrapper.set(ShipmentTracking::getCheckPerson, currentUser.getName());
            trackingUpdateWrapper.set(ShipmentTracking::getCheckPersonNo, currentUser.getAccount());
            trackingUpdateWrapper.set(ShipmentTracking::getCheckDate, new Date());
            trackingUpdateWrapper.set(ShipmentTracking::getUpdatedTime, new Date());

            if (verifResult) {

                if (createTransferSuccess.size() <= successBatchNo.size()) {
                    successBatchNo.removeAll(createTransferSuccess);
                }

                if (ObjectUtil.isNotEmpty(createTransferSuccess)) {
                    trackingUpdateWrapper.set(ShipmentTracking::getTrackingStatus, "执行中");
                    trackingUpdateWrapper.in(ShipmentTracking::getApplyBatchNo, createTransferSuccess);
                    shipmentTrackingService.update(trackingUpdateWrapper);
                }


            } else {
                trackingUpdateWrapper.set(ShipmentTracking::getTrackingStatus, "已完结");
                trackingUpdateWrapper.set(ShipmentTracking::getNeedTrack, 1);
                trackingUpdateWrapper.in(ShipmentTracking::getApplyBatchNo, batchNoList);
                shipmentTrackingService.update(trackingUpdateWrapper);
            }

            if (verifResult && (ObjectUtil.isNotEmpty(batchNoList) || ObjectUtil.isNotEmpty(successBatchNo))) {
                //库存校验没通过的批次号
                String msg = "";
                if (ObjectUtil.isNotEmpty(batchNoList)) {
                    msg = "审核通过的数据按照D6维度进行库存校验，以下批次申请单未通过库存校验【" + JSONUtil.toJsonStr(batchNoList) + "】----";
                }
                //同步到erp创建调拨单失败的
                if (ObjectUtil.isNotEmpty(successBatchNo)) {
                    msg = msg + "/r/n以下批次编号的发货申请同步到erp创建调拨单失败【" + JSONUtil.toJsonStr(successBatchNo) + "】";
                }
                return ResponseData.error(500, msg);
            }
        } catch (Exception e) {
            log.error("发货审核请求参数【{}】执行异常：{}", JSONUtil.toJsonStr(cloneVerifParm), JSONUtil.toJsonStr(e));
            String erMsg = "发货审核执行异常" + JSONUtil.toJsonStr(e);
            return ResponseData.error(500, erMsg);
        } finally {
            //释放审核时的redis锁
            this.delVerifKey(cloneVerifParm.getApplyBatchNoList());
        }
        log.info("发货审核结束时间【{}】", new Date());
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData warehouse(String platform, String site) {
        List<Map<String, String>> warehouseList = mapper.warehouse(platform, site);
        return ResponseData.success(warehouseList);
    }

    /**
     * 审核通过时--校验库存
     *
     * @param batchNoList
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public List<String> verifComitCheck(List<String> batchNoList, Boolean isCommit) {

        //通过库存校验的批次编号
        Set<String> successBatchNoSet = new TreeSet<>();

        LambdaQueryWrapper<ShipmentApplyItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ShipmentApplyItem::getApplyBatchNo, batchNoList);
        wrapper.eq(ShipmentApplyItem::getCheckStatus, 0);//只校验待审核的数据
        wrapper.ne(ShipmentApplyItem::getSyncStatus, 1);//已经同步成功的不需要校验
        List<ShipmentApplyItem> itemList = this.list(wrapper);
        if (ObjectUtil.isEmpty(itemList)) {
            log.info("提交的批次号【{}】，已经同步了，时间{}请刷新！", JSONUtil.toJsonStr(batchNoList), new Date());
            return new ArrayList<>();
        }

        //1--获取erp库存
        Set<String> teamSet = new TreeSet<>();
        Set<String> matCodeSet = new TreeSet<>();
        Set<String> deliverypointNoSet = new TreeSet<>();
        Set<String> applyBatchNoSet = new HashSet<>();

        for (ShipmentApplyItem s : itemList) {
            if (ObjectUtil.isNotEmpty(s.getApplyBatchNo())) {
                applyBatchNoSet.add(s.getApplyBatchNo());
            }
            teamSet.add(s.getTeam());
            matCodeSet.add(s.getMaterialCode());
            deliverypointNoSet.add(s.getDeliverypointNo());
        }

        Map<String, List<ShipmentApplyItem>> teamMatCodeDeliverypointNoMap = itemList.stream().collect(Collectors.groupingBy(i -> i.getTeam() + i.getMaterialCode() + i.getDeliverypointNo()));

        //按提交汇的汇总物料数量

        Set<String> teamMatCodeDeliverypointNoSet = teamMatCodeDeliverypointNoMap.keySet();

        //erp可调拨数量
        HashMap<String, BigDecimal> erpQtyMap = new HashMap<>();

        List<ErpTeamAvailableQytResult> erpResultList = trackingTransferService.erpAvailableQty(teamSet, matCodeSet, deliverypointNoSet, teamMatCodeDeliverypointNoSet);
        for (ErpTeamAvailableQytResult erpQtyResult : erpResultList) {
            erpQtyMap.put(erpQtyResult.getMergeField3(), erpQtyResult.getQty());
        }

        //占用数量
        HashMap<String, BigDecimal> occupyQtyMap = new HashMap<>();
        if (isCommit) {
            List<OccupyQytResult> occupyQytResultList = trackingTransferService.batchQueryOccupyQty(teamMatCodeDeliverypointNoSet, batchNoList);
            for (OccupyQytResult occupyQtyResult : occupyQytResultList) {
                occupyQtyMap.put(occupyQtyResult.getMergeField3(), occupyQtyResult.getQty());
            }
        }

        //申请数量
        HashMap<String, BigDecimal> applyQytMap = new HashMap<>();
        for (Map.Entry<String, List<ShipmentApplyItem>> entry : teamMatCodeDeliverypointNoMap.entrySet()) {
            BigDecimal sumApplyQty = entry.getValue().stream().map(ShipmentApplyItem::getSendQty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            applyQytMap.put(entry.getKey(), sumApplyQty);
        }


        List<ShipmentApplyItem> updateListItem = new ArrayList<>();


        //实际可调拨库存比较
        for (Map.Entry<String, List<ShipmentApplyItem>> entry : teamMatCodeDeliverypointNoMap.entrySet()) {
            String teamMatCodeDeliverypointNo = entry.getKey();
            List<ShipmentApplyItem> items = entry.getValue();
            BigDecimal applyQyt = applyQytMap.get(teamMatCodeDeliverypointNo);

            BigDecimal erpQty = erpQtyMap.get(teamMatCodeDeliverypointNo);
            if (ObjectUtil.isNull(erpQty) || erpQty.compareTo(BigDecimal.ZERO) <= 0) {
                //erp没有库存
                log.info("【" + teamMatCodeDeliverypointNo + "】ERP无可调拨的物料！" + LocalDateTime.now().toString());
                erpQty = BigDecimal.ZERO;
            }
            BigDecimal occupyQty = BigDecimal.ZERO;
            //申请时需校验占用数量
            if (isCommit) {
                occupyQty = occupyQtyMap.get(teamMatCodeDeliverypointNo);
                if (ObjectUtil.isNull(occupyQty) || occupyQty.compareTo(BigDecimal.ZERO) <= 0) {
                    //没有占用中的数量
                    log.info("【" + teamMatCodeDeliverypointNo + "】无占用数量，继续执行校验！" + LocalDateTime.now());
                    occupyQty = BigDecimal.ZERO;
                }
            }
            BigDecimal avilibeQty = erpQty.subtract(occupyQty).subtract(applyQyt);

            Set<String> batchNoSet = items.stream().map(ShipmentApplyItem::getApplyBatchNo).collect(Collectors.toSet());

            //库存校验结果
            String stockCheckResult = "申请批次号【" + batchNoSet + "】Team+物料编码【" + teamMatCodeDeliverypointNo + "】本次提交申请总数量【" + applyQyt + "】ERP可调拨数量【" + erpQty + "】" + (isCommit ? "处理中占用库存数量【" + occupyQty + "】" : "");

            if (avilibeQty.compareTo(BigDecimal.ZERO) >= 0) {
                stockCheckResult = "库存校验【通过】：" + stockCheckResult;
                successBatchNoSet.addAll(batchNoSet);
            } else {
                stockCheckResult = "库存校验【没通过】：" + stockCheckResult;
            }

            for (ShipmentApplyItem item : items) {
                item.setStockCheck(stockCheckResult);
                item.setUpdatedTime(new Date());
                //申请提交时更新字段 COMMITED_AVAIL_QTY申请后国内可调拨库存
                if (isCommit) {
                    item.setCommitedAvailQty(avilibeQty);
                }
            }
            updateListItem.addAll(items);

        }
        this.updateBatchById(updateListItem);

        if (ObjectUtil.isNotEmpty(successBatchNoSet)) {
            return new ArrayList<>(successBatchNoSet);
        }

        return null;
    }


    /**
     * 删除审核时的redis 锁
     *
     * @param batchNoList
     * @return
     */
    private ResponseData delVerifKey(List<String> batchNoList) {
        if (ObjectUtil.isEmpty(batchNoList)) {
            return ResponseData.success();
        }
        try {
            List<String> keyList = batchNoList.stream().map(b -> VERIF_KEY_HEAD + b).collect(Collectors.toList());
            Long delete = redisTemplate.delete(keyList);
            log.info("发货审核释放redis锁成功：【{}】，释放时间【{}】", keyList, new Date());
        } catch (Exception e) {
            log.info("发货审核释放redis锁异常batchNo：【{}】，异常时间【{}】", batchNoList, new Date());
        }
        return ResponseData.success();
    }

    /**
     * 审核重复提交校验
     *
     * @param verifParam
     * @return
     */
    private ResponseData repeatCheck(VerifParam verifParam) {

        List<String> batchNoList = verifParam.getApplyBatchNoList();
        List<String> succList = new ArrayList<>();
        List<String> failList = new ArrayList<>();

        for (String batchNo : batchNoList) {
            //重复提交校验
            String redisBatchNo = (String) redisTemplate.boundValueOps(VERIF_KEY_HEAD + batchNo).get();
            if (ObjectUtil.isEmpty(redisBatchNo)) {
                succList.add(batchNo);

            } else {
                failList.add(batchNo);
            }
        }

        if (ObjectUtil.isNotEmpty(failList)) {
            String msg = "以下申请批次号系统正在审核处理中，请勿重复提交!";
            msg = msg + "【" + JSONUtil.toJsonStr(failList) + "】";
            return ResponseData.error(500, msg, failList);
        }
        //没有重复提交的数据 缓存到redis
        if (ObjectUtil.isNotEmpty(succList)) {
            for (String succBatchNo : succList) {
                redisTemplate.boundValueOps(VERIF_KEY_HEAD + succBatchNo).set(succBatchNo, Duration.ofMinutes(60 * 2));
            }
        }
        log.info("发货审核添加redis锁成功：【{}】，添加时间【{}】", succList, new Date());

        //审核通过时  需要校验页面提交的批次号是否已经处理了
        //页面缓存重复提交已经处理成功的数据
        LambdaQueryWrapper<ShipmentApplyItem> wrapper = new LambdaQueryWrapper<ShipmentApplyItem>();
        wrapper.in(ShipmentApplyItem::getApplyBatchNo, batchNoList);
        wrapper.eq(ShipmentApplyItem::getApplyStatus, 1);

        List<ShipmentApplyItem> applyItemList = this.baseMapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(applyItemList)) {
            return ResponseData.error(500, "发货审核提交的批次号未找到对应的数据", batchNoList);
        }

        //已经审核过的数据
        List<ShipmentApplyItem> checkedData = applyItemList.stream().filter(a -> !a.getCheckStatus().equals(0)).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(checkedData)) {
            Set<String> checkedBatchNo = checkedData.stream().map(a -> a.getApplyBatchNo()).collect(Collectors.toSet());
            String msg = "批次号【" + JSONUtil.toJsonStr(checkedBatchNo) + "】已做了审核处理，请刷新页面！";
            log.info("批次号【{}】已做了审核处理，请刷新页面！", JSONUtil.toJsonStr(checkedBatchNo));
            this.delVerifKey(succList);
            return ResponseData.error(500, msg, checkedBatchNo);
        }

        if (verifParam.getStatus().equals(1)) {
            List<ShipmentApplyItem> succBatchNoItem = applyItemList.stream().filter(a -> a.getSyncStatus().equals(1)).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(succBatchNoItem)) {
                Set<String> succBatchNo = succBatchNoItem.stream().map(a -> a.getApplyBatchNo()).collect(Collectors.toSet());
                String msg = "以下批次号【" + JSONUtil.toJsonStr(succBatchNo) + "】已经存在同步成功的单据，请刷新页面！";
                log.info("以下批次号【{}】已经存在同步成功的单据，请刷新页面！", JSONUtil.toJsonStr(succBatchNo));
                this.delVerifKey(succList);
                return ResponseData.error(500, msg, succBatchNo);
            }

        }

        return ResponseData.success();

    }


    /**
     * 发货审核-已通过-jcerp补调拨单数据
     *
     * @param supplementaryTransferParam
     * @return
     */
    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData supplementaryTransfer(SupplementaryTransferParam supplementaryTransferParam) {

        LoginUser currentUser = new LoginUser();
        currentUser.setName(supplementaryTransferParam.getName());
        currentUser.setAccount(supplementaryTransferParam.getAccount());

        //同步成功的批次号更新数据状态
        List<String> createTransferSuccess = new ArrayList<>();

        //补调拨单信息
        createTransferSuccess = otherModularService.supplementaryTransfer(supplementaryTransferParam, currentUser);

        //更新追踪记录状态
        LambdaUpdateWrapper<ShipmentTracking> trackingUpdateWrapper = new LambdaUpdateWrapper<>();
        trackingUpdateWrapper.set(ObjectUtil.isNotEmpty(supplementaryTransferParam.getReson()), ShipmentTracking::getCheckReason, supplementaryTransferParam.getReson());
        trackingUpdateWrapper.set(ShipmentTracking::getCheckStatus, supplementaryTransferParam.getStatus());
        trackingUpdateWrapper.set(ShipmentTracking::getCheckPerson, currentUser.getName());
        trackingUpdateWrapper.set(ShipmentTracking::getCheckPersonNo, currentUser.getAccount());
        trackingUpdateWrapper.set(ShipmentTracking::getCheckDate, new Date());
        trackingUpdateWrapper.set(ShipmentTracking::getUpdatedTime, new Date());

        if (ObjectUtil.isNotEmpty(createTransferSuccess)) {
            trackingUpdateWrapper.set(ShipmentTracking::getTrackingStatus, "执行中");
            trackingUpdateWrapper.in(ShipmentTracking::getApplyBatchNo, createTransferSuccess);
            shipmentTrackingService.update(trackingUpdateWrapper);
        }
        return ResponseData.success();
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }

}
