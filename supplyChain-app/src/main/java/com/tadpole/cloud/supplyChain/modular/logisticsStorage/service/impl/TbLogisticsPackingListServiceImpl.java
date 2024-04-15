package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.RpMaterial;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ErpWarehouseCode;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.RpMaterialConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasInWarehouseService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasOutWarehouseService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsClearanceService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.k3.PackingRecordSyncBoxDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.k3.PackingRecordSyncMainResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.k3.PackingRecordSyncSkuDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.*;
import com.tadpole.cloud.supplyChain.modular.manage.consumer.K3CloudWebApiConsumer;
import com.tadpole.cloud.supplyChain.modular.manage.consumer.ShopEntityConsumer;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 出货清单信息-金蝶+海外仓;(tb_logistics_packing_list)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackingListServiceImpl extends ServiceImpl<TbLogisticsPackingListMapper, TbLogisticsPackingList> implements TbLogisticsPackingListService {
    @Resource
    private TbLogisticsPackingListMapper tbLogisticsPackingListMapper;

    @Resource
    private TbLogisticsTransferRecordService tbLogisticsTransferRecordService;

    @Resource
    private TbLogisticsTransferRecordDetService recordDetService;
    @Resource
    private TbLogisticsPackingListDet2Service det2Service;

    @Resource
    private TbLogisticsPackListBoxRecService packListBoxRecService;

    @Resource
    private TbLogisticsPackListBoxRecDetService packListBoxRecDetService;

    @Resource
    private TbBscOverseasWayService tbBscOverseasWayService;

    @Resource
    private TbLogisticsPackListBoxRecMapper tbLogisticsPackListBoxRecMapper;

    @Resource
    private TbLogisticsPackListBoxRecDetMapper tbLogisticsPackListBoxRecDetMapper;

    @Resource
    private TbLogisticsPackListBoxRecDetService tbLogisticsPackListBoxRecDetService;

    @Resource
    private TbLogisticsPackingListDet1Mapper tbLogisticsPackingListDet1Mapper;

    @Resource
    private TbLogisticsPackingListDet2Mapper tbLogisticsPackingListDet2Mapper;

    @Resource
    private TbLogisticsListToHeadRouteDetMapper tbLogisticsListToHeadRouteDetMapper;

    @Resource
    private K3CloudWebApiConsumer k3CloudWebApiConsumer;


    @Resource
    private TbLogisticsNewPriceService tbLogisticsNewPriceService;

    @Resource
    private RpMaterialConsumer rpMaterialConsumer;

    @Resource
    private IOverseasInWarehouseService overseasInWarehouseService;

    @Resource
    private IOverseasOutWarehouseService overseasOutWarehouseService;

    @Resource
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    @Resource
    private ILogisticsSettlementService logisticsSettlementService;

    @Resource
    private TbLogisticsListToHeadRouteService tbLogisticsListToHeadRouteService;
    @Resource
    private TbLogisticsListToHeadRouteDetService tbLogisticsListToHeadRouteDetService;

    @Resource
    private TbLogisticsListToEndRouteService tbLogisticsListToEndRouteService;

    @Resource
    private TbLogisticsListToEndRouteDetService tbLogisticsListToEndRouteDetService;

    @Resource
    private ShopEntityConsumer shopEntityConsumer;

    @Resource
    private TbLogisticsPackListDetToBoxMapper tbLogisticsPackListDetToBoxMapper;

    @Resource
    private TbLogisticsPackListDetService tbLogisticsPackListDetService;

    @Resource
    private TbLogisticsPackListService tbLogisticsPackListService;

    @Resource
    private TbLogisticsShipmentDetService tbLogisticsShipmentDetService;

    @Resource
    private TbLogisticsShipmentService tbLogisticsShipmentService;

    @Resource
    private TbLogisticsPackingListDet1Service tbLogisticsPackingListDet1Service;

    @Resource
    private TbLogisticsPackingListDet2Service tbLogisticsPackingListDet2Service;

    @Resource
    private ITgCustomsClearanceService tgCustomsClearanceService;

    /**
     * 通过ID查询单条数据
     *
     * @param packCode 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingList queryById(String packCode) {
        return tbLogisticsPackingListMapper.selectById(packCode);
    }

    /**
     * 分页查询
     *
     * @param param   筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsPackingListResult> pageQuery(TbLogisticsPackingListParam param, long current, long size) {
        //1. 构建动态查询条件
        MPJLambdaWrapper<TbLogisticsPackingList> queryWrapper = new MPJLambdaWrapper<>();
//        queryWrapper.selectAll(TbLogisticsPackingList.class)
        queryWrapper.selectFilter(TbLogisticsPackingList.class, i -> !i.getColumn().contains("shipment_id"))
                .select(TbLogisticsPackListBoxRec::getSysId,
                        TbLogisticsPackListBoxRec::getShipmentId,
                        TbLogisticsPackListBoxRec::getShipTo,
                        TbLogisticsPackListBoxRec::getAmaRecState,
                        TbLogisticsPackListBoxRec::getShipmentRealStatus,
                        TbLogisticsPackListBoxRec::getPackListCode)
                .join("LEFT JOIN", TbLogisticsPackListBoxRec.class, on -> on.eq(TbLogisticsPackListBoxRec::getPackCode, TbLogisticsPackingList::getPackCode));
//                .leftJoin(TbLogisticsPackListBoxRec.class,TbLogisticsPackListBoxRec::getPackCode, TbLogisticsPackingList::getPackCode);
//                .innerJoin(TbLogisticsPackListBoxRec.class,TbLogisticsPackListBoxRec::getPackCode, TbLogisticsPackingList::getPackCode);

        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopPlatName()), TbLogisticsPackingList::getShopPlatName, param.getShopPlatName());//平台
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()), TbLogisticsPackingList::getShopNameSimple, param.getShopNameSimple());//账号
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()), TbLogisticsPackingList::getCountryCode, param.getCountryCode());//站点
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackUploadState()), TbLogisticsPackingList::getPackUploadState, param.getPackUploadState());//状态
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getPackCode()), TbLogisticsPackingList::getPackCode, param.getPackCode());//出货清单号
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getShipmentId()), TbLogisticsPackListBoxRec::getShipmentId, param.getShipmentId());//ShipmentId

        //物料编码 和sku
        if (ObjectUtil.isNotEmpty(param.getMateCode()) || ObjectUtil.isNotEmpty(param.getItemSku())) {

            queryWrapper.inSql(ObjectUtil.isNotEmpty(param.getMateCode()),
                    TbLogisticsPackingList::getPackCode,
                    "select DISTINCT pack_code  FROM tb_logistics_packing_list_det2  a WHERE a.MATE_CODE LIKE '%" + param.getMateCode() + "%' ");

            queryWrapper.inSql(ObjectUtil.isNotEmpty(param.getItemSku()),
                    TbLogisticsPackingList::getPackCode,
                    "select DISTINCT pack_code  FROM tb_logistics_packing_list_det2  a WHERE a.SKU LIKE '%" + param.getItemSku() + "%' ");
        }
        queryWrapper.orderByDesc(TbLogisticsPackingList::getSysUpdDatetime);

        //2. 执行分页查询
        Page<TbLogisticsPackingListResult> pagin = new Page<>(current, size, true);
        IPage<TbLogisticsPackingListResult> selectResult = tbLogisticsPackingListMapper.selectJoinPage(pagin, TbLogisticsPackingListResult.class, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsPackingList 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingList insert(TbLogisticsPackingList tbLogisticsPackingList) {
        tbLogisticsPackingListMapper.insert(tbLogisticsPackingList);
        return tbLogisticsPackingList;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingList update(TbLogisticsPackingListParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackingList> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackingList>(tbLogisticsPackingListMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackPerCode()), TbLogisticsPackingList::getPackPerCode, param.getPackPerCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackPerName()), TbLogisticsPackingList::getPackPerName, param.getPackPerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()), TbLogisticsPackingList::getShopNameSimple, param.getShopNameSimple());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()), TbLogisticsPackingList::getCountryCode, param.getCountryCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackUploadState()), TbLogisticsPackingList::getPackUploadState, param.getPackUploadState());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackLogState()), TbLogisticsPackingList::getPackLogState, param.getPackLogState());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackShipmentClaimState()), TbLogisticsPackingList::getPackShipmentClaimState, param.getPackShipmentClaimState());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackStoreHouseType()), TbLogisticsPackingList::getPackStoreHouseType, param.getPackStoreHouseType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackStoreHouseName()), TbLogisticsPackingList::getPackStoreHouseName, param.getPackStoreHouseName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackAbnormalReason()), TbLogisticsPackingList::getPackAbnormalReason, param.getPackAbnormalReason());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()), TbLogisticsPackingList::getShipmentId, param.getShipmentId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComWarehouseType()), TbLogisticsPackingList::getComWarehouseType, param.getComWarehouseType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComWarehouseName()), TbLogisticsPackingList::getComWarehouseName, param.getComWarehouseName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComShopPlatName()), TbLogisticsPackingList::getComShopPlatName, param.getComShopPlatName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComShopNameSimple()), TbLogisticsPackingList::getComShopNameSimple, param.getComShopNameSimple());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComCountryCode()), TbLogisticsPackingList::getComCountryCode, param.getComCountryCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShopPlatName()), TbLogisticsPackingList::getShopPlatName, param.getShopPlatName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getDeliveryPointName()), TbLogisticsPackingList::getDeliveryPointName, param.getDeliveryPointName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBillType()), TbLogisticsPackingList::getBillType, param.getBillType());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackingList::getPackCode, param.getPackCode());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getPackCode());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param packCode 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String packCode) {
        int total = tbLogisticsPackingListMapper.deleteById(packCode);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param packCodeList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> packCodeList) {
        int delCount = tbLogisticsPackingListMapper.deleteBatchIds(packCodeList);
        if (packCodeList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 根据出货清单号查询，出货清单信息明细1,2
     *
     * @param packCode
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingListResultDet1AndDet2 getLogisticsShipmentListDetail(String packCode) {
        MPJLambdaWrapper<TbLogisticsPackingList> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper
                .select(TbLogisticsPackingList::getPackCode)
                .selectAll(TbLogisticsPackingListDet1.class)
                .selectAll(TbLogisticsPackingListDet2.class)
                .join("JOIN", TbLogisticsPackingListDet1.class, on -> on.eq(TbLogisticsPackingListDet1::getPackCode, TbLogisticsPackingList::getPackCode))
                .join("JOIN", TbLogisticsPackingListDet2.class, on -> on.eq(TbLogisticsPackingListDet2::getPackCode, TbLogisticsPackingList::getPackCode))
                .selectCollection(TbLogisticsPackingListDet1.class, TbLogisticsPackingListResultDet1AndDet2::getTbLogisticsPackingListDet1s)
                .selectCollection(TbLogisticsPackingListDet2.class, TbLogisticsPackingListResultDet1AndDet2::getTbLogisticsPackingListDet2s)
                .eq(TbLogisticsPackingList::getPackCode, packCode);
        return tbLogisticsPackingListMapper.selectJoinOne(TbLogisticsPackingListResultDet1AndDet2.class, queryWrapper);
    }

    /**
     * 获取通过数据
     *
     * @param packCode
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbClearancModel> getClearanceData(String packCode) {
        return tbLogisticsPackingListMapper.getClearanceData(packCode);
    }

    /**
     * 出货清单 转仓
     *
     * @param request
     * @return
     */
    @DataSource(name = "logistics")
    @Transactional
    @Override
    public ResponseData transformWarehouse(TbLogisticsPackingListParam request) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<ErpWarehouseCode> organizeList = tbLogisticsPackingListMapper.getetErpWarehouseCodeList();//获取ERP组织结构  WAREHOUSE.ERP_WAREHOUSE_CODE
        TbLogisticsPackingList packingList = tbLogisticsPackingListMapper.selectById(request.getPackCode());//根据PackCode获取出货清单信息

        String outElePlatformName = tbLogisticsPackingListMapper.getElePlatformName(packingList.getShopNameSimple(), packingList.getCountryCode());//根据账号站点获取所属电商平台
        String transferOutHouseName = outElePlatformName + "_" + packingList.getShopNameSimple() + "_" + packingList.getCountryCode() + "_" + "仓库";//调出仓库名称
        Optional<ErpWarehouseCode> erpWarehouseCode = organizeList.stream()
                .filter(x -> x.getOrganizationName().equals(transferOutHouseName)
                        && "warehouse".equals(x.getOrganizationType())
                        && x.getUseOrganization().equals(outElePlatformName + "_" + packingList.getShopNameSimple() + "_" + packingList.getCountryCode())).findFirst();
        if (!erpWarehouseCode.isPresent()) {
            return ResponseData.error("仓库编码未找到");
        }


        String transferOutHouseCode = erpWarehouseCode.get().getOrganizationCode();//调出仓库编码

        TbLogisticsTransferRecord transferRecord = new TbLogisticsTransferRecord();
        String inElePlatformName = tbLogisticsPackingListMapper.getElePlatformName(request.getShopNameSimple(), request.getCountryCode());//根据账号站点获取所属电商平台

        transferRecord.setTransferInOrganizeName(inElePlatformName + "_" + request.getShopNameSimple() + "_" + request.getCountryCode());//调入组织名称

        Optional<ErpWarehouseCode> erpWarehouseCode1 = organizeList.stream()
                .filter(x -> x.getOrganizationName().equals(transferRecord.getTransferInOrganizeName())
                        && "organization".equals(x.getOrganizationType())
                        && x.getUseOrganization().equals(transferRecord.getTransferInOrganizeName())).findFirst();

        if (!erpWarehouseCode1.isPresent()) {
            return ResponseData.error("组织编码未找到");
        }
        transferRecord.setTransferInOrganizeCode(erpWarehouseCode1.get().getOrganizationCode());//调入组织编码

        String transferInHouseName = "海外仓".equals(request.getPackStoreHouseType()) ? request.getPackStoreHouseName() : inElePlatformName + "_" + request.getShopNameSimple() + "_" + request.getCountryCode() + "_" + "仓库";//调入仓库名称
        transferRecord.setTransferOutOrganizeName(outElePlatformName + "_" + packingList.getShopNameSimple() + "_" + packingList.getCountryCode());//调出组织名称


        Optional<ErpWarehouseCode> erpWarehouseCode2 = organizeList.stream().filter(x -> x.getOrganizationName().equals(transferRecord.getTransferOutOrganizeName())
                && "organization".equals(x.getOrganizationType())
                && x.getUseOrganization().equals(transferRecord.getTransferOutOrganizeName())).findFirst();

        if (!erpWarehouseCode2.isPresent()) {
            return ResponseData.error("调出组织编码未找到");
        }
        transferRecord.setTransferOutOrganizeCode(erpWarehouseCode2.get().getOrganizationCode());//调出组织编码

        transferRecord.setTransfersType(transferRecord.getTransferInOrganizeName().equals(transferRecord.getTransferOutOrganizeName()) ? "组织内调拨" : "跨组织调拨");//调拨类型
        transferRecord.setTransfersSource("海外仓".equals(request.getPackStoreHouseType()) ? "FBA仓发海外仓调拨" : "欧洲站点调拨");//调拨来源;//调拨来源
        transferRecord.setPackCode(request.getPackCode());//出货清单号
        transferRecord.setTransferDate(new Date());//调拨日期
        transferRecord.setTransfersOperator(loginUser.getName());//操作人
        transferRecord.setTransfersOperatorCode(loginUser.getAccount());//操作人工号
        transferRecord.setTransfersDirection("普通");//调拨方向
        transferRecord.setStatus("未处理");//处理状态

//        -------------------------------------------------------------------------------------------

        ErpWarehouseCode SearchTransferInHouseCode = organizeList.stream()
                .filter(x -> x.getOrganizationName().equals(transferInHouseName)
                        && "warehouse".equals(x.getOrganizationType()) && x.getUseOrganization().equals(inElePlatformName + "_" + request.getShopNameSimple() + "_" + request.getCountryCode())).findFirst().get();
        if (ObjectUtil.isNull(SearchTransferInHouseCode)) {
            return ResponseData.error("调入仓库编码不存在");
        }

        String transferInHouseCode = SearchTransferInHouseCode.getOrganizationCode();//调入仓库编码
        boolean result = false;
        result = this.transformWarehouse(request.getPackCode(), request.getCountryCode(), request.getPackStoreHouseType(), request.getPackStoreHouseName(), request.getPackUploadState());//转仓操作
        if (result) {
            List<TbLogisticsTransferRecord> recordList = tbLogisticsTransferRecordService.getByPackCode(request.getPackCode());
            if (ObjectUtil.isEmpty(recordList)) {
                tbLogisticsTransferRecordService.save(transferRecord);//转仓清单
                BigDecimal lastID = transferRecord.getSysId();
                this.createLogisticsTransferRecordDetFromShipment(request.getPackCode(), transferInHouseCode, transferInHouseName, transferOutHouseCode, transferOutHouseName, lastID, null);//转仓清单明细
                //如果是转到海外仓，需新增相应的海外仓库存

            }

            return ResponseData.success("转仓成功");
        } else {
            return ResponseData.error("转仓失败");
        }
    }


    /**
     * 创建转仓清单明细【来源出货清单】
     *
     * @param packCode
     * @param transferInHouseCode
     * @param transferInHouseName
     * @param transferOutHouseCode
     * @param transferOutHouseName
     * @param lastID
     * @param boxNumList
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public int createLogisticsTransferRecordDetFromShipment(String packCode, String transferInHouseCode, String transferInHouseName,
                                                            String transferOutHouseCode, String transferOutHouseName, BigDecimal lastID, List<Integer> boxNumList) {

        LambdaQueryWrapper<TbLogisticsPackingListDet2> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(packCode), TbLogisticsPackingListDet2::getPackCode, packCode);
        wrapper.in(ObjectUtil.isNotEmpty(boxNumList), TbLogisticsPackingListDet2::getPackDetBoxNum, boxNumList);
        List<TbLogisticsPackingListDet2> det2List = det2Service.list(wrapper);
        if (ObjectUtil.isNotEmpty(det2List)) {
            List<TbLogisticsTransferRecordDet> recordDetList = new ArrayList<>();
            for (TbLogisticsPackingListDet2 p : det2List) {
                TbLogisticsTransferRecordDet recordDet = new TbLogisticsTransferRecordDet();
                recordDet.setMateCode(p.getMateCode());
                recordDet.setPackDetBoxCode(p.getPackDetBoxCode());
                recordDet.setPackDetBoxNum(p.getPackDetBoxNum().toString());
                recordDet.setSku(p.getSku());
                recordDet.setFnSku(p.getFnSku());
                recordDet.setTransferInHouseCode(transferInHouseCode);
                recordDet.setTransferInHouseName(transferInHouseName);
                recordDet.setTransferOutHouseCode(transferOutHouseCode);
                recordDet.setTransferOutHouseName(transferOutHouseName);
                recordDet.setTransferQuantity(p.getQuantity());
                recordDet.setSysId(lastID);

                recordDetList.add(recordDet);
            }
            if (recordDetService.saveBatch(recordDetList)) {
                return recordDetList.size();
            }
        }
        return 0;
    }

    /**
     * 转仓
     *
     * @param packCode
     * @param countryCode
     * @param packStoreHouseType
     * @param packStoreHouseName
     * @param packUploadState
     * @return
     */
    @DataSource(name = "logistics")
    @Transactional
    @Override
    public boolean transformWarehouse(String packCode, String countryCode, String packStoreHouseType, String packStoreHouseName, String packUploadState) {
        packListBoxRecService.deleteByPackCode(packCode);
        packListBoxRecDetService.deleteByPackCode(packCode);

        LambdaUpdateWrapper<TbLogisticsPackingList> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TbLogisticsPackingList::getPackCode, packCode)
                .set(TbLogisticsPackingList::getPackAbnormalReason, "")
                .set(TbLogisticsPackingList::getPackAbnormalStatus, false)
                .set(TbLogisticsPackingList::getPackStoreHouseType, packStoreHouseType)
                .set(TbLogisticsPackingList::getCountryCode, countryCode)
                .set(TbLogisticsPackingList::getPackStoreHouseName, packStoreHouseName);
        this.update(updateWrapper);
        //删除发货汇总数据
        tbBscOverseasWayService.deleteByPackCode(packCode);
        return true;
    }

    /**
     * FBA回传至ERP
     *
     * @param packCode
     * @param shipmentID
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData fbaBackhaulErp(String packCode, String shipmentID) {


        //3.更新FBA
        // 推送->组装传递参数
        String fba = "";
        List<JSONObject> boxDets = new ArrayList<>();

        HashMap<String, Object> parMap = new HashMap<>();
        parMap.put("Pack_Code", packCode);

        List<TbLogisticsPackListBoxRec> packListRec = tbLogisticsPackListBoxRecMapper.selectByMap(parMap);
        List<TbLogisticsPackListBoxRecDet> packListRecDet = tbLogisticsPackListBoxRecDetMapper.selectByMap(parMap);
        List<TbLogisticsPackingListDet1> packListData = tbLogisticsPackingListDet1Mapper.selectByMap(parMap);
        List<TbLogisticsPackingListDet2> packInfoData = tbLogisticsPackingListDet2Mapper.selectByMap(parMap);

//        List<JSONObject> skuDets = new ArrayList<>();

//        this.QueryPackInfoNew(packCode, ref packListRec, ref packListRecDet, ref packListData, ref packInfoData);


        packListData.forEach(l ->
        {
//                        skuDets = new List<JSONObject>();
            List<JSONObject> skuDets = new ArrayList<>();

//        Map<String,Object> modelBox = new HashMap<>();
            JSONObject modelBox = new JSONObject();
            modelBox.put("packDetBoxCode", l.getPackDetBoxCode());
            modelBox.put("packDetBoxNum", l.getPackDetBoxNum());


            packInfoData.stream()
                    .filter(i -> i.getPackCode().equals(l.getPackCode()) && i.getPackDetBoxCode().equals(l.getPackDetBoxCode()) && Objects.equals(i.getPackDetBoxNum(), l.getPackDetBoxNum()))
                    .collect(Collectors.toList())
                    .forEach(x ->
                    {
                        JSONObject modelSku = new JSONObject();
                        modelSku.put("SKU", x.getSku());
                        modelSku.put("Quantity", x.getQuantity());
                        modelSku.put("PickListCode", x.getPickListCode());
                        skuDets.add(modelSku);
                    });


            modelBox.put("SkuDet", skuDets);

            boxDets.add(modelBox);
        });

        if (ObjectUtil.isNotEmpty(packListRec)) {
            Set<String> shipmentIdSet = packListRec.stream().map(p -> p.getShipmentId()).collect(Collectors.toSet());
            shipmentIdSet.remove(null);
            if (ObjectUtil.isNotEmpty(shipmentIdSet)) {
                shipmentID = CollectionUtil.join(shipmentIdSet, ";");
            }
        }
        fba = shipmentID;


        List<JSONObject> modelMains = new ArrayList<>();

        JSONObject modelMain = new JSONObject();
        modelMain.put("PackCode", packCode);
        modelMain.put("FBA", fba);
        modelMain.put("BoxDet", boxDets);
        modelMains.add(modelMain);


        ResponseData responseData = k3CloudWebApiConsumer.updateFBAExtents(modelMain);
        return responseData;
    }

    /**
     * 返仓
     *
     * @param packCode
     * @param packStoreHouseType
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData returnWarehouse(String packCode, String packStoreHouseType) {

        HashMap<String, Object> parMap = new HashMap<>();
        parMap.put("Pack_Code", packCode);

        List<TbLogisticsListToHeadRouteDet> routeDets = tbLogisticsListToHeadRouteDetMapper.selectByMap(parMap);
        if (ObjectUtil.isNotEmpty(routeDets)) {
            return ResponseData.error("此单存在物流发货记录,请移步物流跟踪进行退回操作！");
        }

        try {
            LambdaUpdateWrapper<TbLogisticsPackingList> updateChainWrapper = new LambdaUpdateWrapper<>();
            updateChainWrapper.eq(TbLogisticsPackingList::getPackCode, packCode)
                    .set(TbLogisticsPackingList::getPackLogState, "已返仓");
            tbLogisticsPackingListMapper.update(null, updateChainWrapper);

            LambdaUpdateWrapper<TbLogisticsPackingListDet1> det1UpdateChainWrapper = new LambdaUpdateWrapper<>();
            det1UpdateChainWrapper.eq(TbLogisticsPackingListDet1::getPackCode, packCode)
                    .set(TbLogisticsPackingListDet1::getPackDetBoxLogState, "已返仓");
            tbLogisticsPackingListDet1Mapper.update(null, det1UpdateChainWrapper);
            if ("海外仓".equals(packStoreHouseType)) {

            }
            //更新发货清单物流发货状态
            tbBscOverseasWayService.updBscOverseasWayDeliverStatus(packCode, "已返仓");
            return ResponseData.success("此票单返仓成功！");
        } catch (Exception e) {

            log.error("[{}]返仓失败:{}", packCode, e.getMessage());
            return ResponseData.error("此票单返仓失败" + e.getMessage());
        }
    }

    /**
     * 作废出货清单
     *
     * @param packCode
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData delShipmentList(String packCode) {

        LambdaUpdateWrapper<TbLogisticsPackListBoxRec> packListWrapper = new LambdaUpdateWrapper<>();
        packListWrapper.eq(TbLogisticsPackListBoxRec::getPackCode, packCode);
        tbLogisticsPackListBoxRecMapper.delete(packListWrapper);

        LambdaUpdateWrapper<TbLogisticsPackingList> packingListWrapper = new LambdaUpdateWrapper<>();
        packingListWrapper.eq(TbLogisticsPackingList::getPackCode, packCode);
        tbLogisticsPackingListMapper.delete(packingListWrapper);

        LambdaUpdateWrapper<TbLogisticsPackingListDet1> Det1Wrapper = new LambdaUpdateWrapper<>();
        Det1Wrapper.eq(TbLogisticsPackingListDet1::getPackCode, packCode);
        tbLogisticsPackingListDet1Mapper.delete(Det1Wrapper);

        LambdaUpdateWrapper<TbLogisticsPackingListDet2> Det2Wrapper = new LambdaUpdateWrapper<>();
        Det2Wrapper.eq(TbLogisticsPackingListDet2::getPackCode, packCode);
        tbLogisticsPackingListDet2Mapper.delete(Det2Wrapper);

        //删除发货清单
        tbBscOverseasWayService.deleteByPackCode(packCode);
        return ResponseData.success();
    }

    /**
     * 更新出货清单状态
     *
     * @param packCode
     * @param packUploadState
     * @param shipmentID
     * @param shipTo
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    @Transactional
    public boolean updateShipmentStatus(String packCode, String packUploadState, String shipmentID, String shipTo) {
        log.info("执行货件回传>>>>>>>>传入参数：出货清单号【{}】 绑定关系【{}】 ShipmentID【{}】 ShipTo【{}】 ", packCode, packUploadState, shipmentID, shipTo);
        boolean result = false;

        if ("未绑定".equals(packUploadState)) {
            log.info("执行货件回传 系统自动绑定 >>>>>>>>开始执行");


            TbLogisticsPackingList packingList = tbLogisticsPackingListMapper.selectById(packCode);//根据PackCode获取出货清单信息


            LambdaQueryWrapper<TbLogisticsPackListBoxRecDet> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TbLogisticsPackListBoxRecDet::getShipmentId, shipmentID)
                    .eq(TbLogisticsPackListBoxRecDet::getPackCode, packCode);
            result = tbLogisticsPackListBoxRecDetMapper.selectCount(wrapper) > 0;

            if (result) {
                return true;
            }


            LambdaQueryWrapper<TbLogisticsPackListBoxRecDet> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(TbLogisticsPackListBoxRecDet::getShipmentId, shipmentID);
            List<TbLogisticsPackListBoxRecDet> boxRecDetList = tbLogisticsPackListBoxRecDetMapper.selectList(wrapper2);


            LambdaUpdateWrapper<TbLogisticsPackingListDet2> Det2Wrapper = new LambdaUpdateWrapper<>();
            Det2Wrapper.eq(TbLogisticsPackingListDet2::getPackCode, packCode);
            List<TbLogisticsPackingListDet2> packingDet2List = tbLogisticsPackingListDet2Mapper.selectList(Det2Wrapper);

            TbLogisticsPackListBoxRec tbLogisticsPackListBoxRec = new TbLogisticsPackListBoxRec();
            tbLogisticsPackListBoxRec.setShipmentId(shipmentID);
            tbLogisticsPackListBoxRec.setPackCode(packCode);
            tbLogisticsPackListBoxRec.setComWarehouseType(packingList.getComWarehouseType());
            tbLogisticsPackListBoxRec.setOwName(packingList.getComWarehouseName());
            tbLogisticsPackListBoxRec.setShipTo(shipTo);
            tbLogisticsPackListBoxRec.setCountryCode(packingList.getCountryCode());
            tbLogisticsPackListBoxRec.setShopNameSimple(packingList.getShopNameSimple());
            tbLogisticsPackListBoxRec.setCreateTime(new Date());
            tbLogisticsPackListBoxRec.setSysUpdateDate(new Date());
            tbLogisticsPackListBoxRec.setShipmentRealStatus("进行中");

            if (tbLogisticsPackListBoxRecMapper.insert(tbLogisticsPackListBoxRec) <= 0) {
                return false;
            }

            if (!boxRecDetList.isEmpty()) {
                int maxNum = boxRecDetList.stream().map(b -> b.getPackDetBoxNum()).max((b1, b2) -> b1 - b2).get();
                List<TbLogisticsPackListBoxRecDet> detList = new ArrayList<>();

                for (TbLogisticsPackingListDet2 l : packingDet2List) {
                    TbLogisticsPackListBoxRecDet det = new TbLogisticsPackListBoxRecDet();
                    det.setSysId(tbLogisticsPackListBoxRec.getSysId());
                    det.setShipmentId(shipmentID);
                    det.setMerchantSku(l.getSku());
                    det.setPackDetBoxNumUpload("Box " + maxNum + " - QTY");
                    det.setOriginPackDetBoxNumUpload(l.getPackDetBoxNumUpload());
                    det.setQuantity(l.getQuantity());
                    det.setPackCode(l.getPackCode());
                    det.setPackDetBoxCode(l.getPackDetBoxCode());
                    det.setPackDetBoxNum(maxNum);
                    det.setOwName(packingList.getComWarehouseName());
                    det.setShipmentRealStatus("进行中");
                    detList.add(det);
                    maxNum++;
                }
                result = tbLogisticsPackListBoxRecDetService.saveBatch(detList);
                log.info("执行货件回传 系统自动绑定 >>>>>>>>执行成功");
            } else {

                List<TbLogisticsPackListBoxRecDet> detList = new ArrayList<>();
                for (TbLogisticsPackingListDet2 l : packingDet2List) {
                    TbLogisticsPackListBoxRecDet det = new TbLogisticsPackListBoxRecDet();
                    det.setSysId(tbLogisticsPackListBoxRec.getSysId());
                    det.setShipmentId(shipmentID);
                    det.setMerchantSku(l.getSku());
                    det.setPackDetBoxNumUpload(l.getPackDetBoxNumUpload());//
                    det.setOriginPackDetBoxNumUpload(l.getPackDetBoxNumUpload());
                    det.setQuantity(l.getQuantity());
                    det.setPackCode(l.getPackCode());
                    det.setPackDetBoxCode(l.getPackDetBoxCode());
                    det.setPackDetBoxNum(l.getPackDetBoxNum());
                    det.setOwName(packingList.getComWarehouseName());
                    det.setShipmentRealStatus("进行中");
                    detList.add(det);
                }
                result = tbLogisticsPackListBoxRecDetService.saveBatch(detList);
                log.info("执行货件回传 系统自动绑定 >>>>>>>>执行成功");
            }

        } else {
            result = true;
        }

        if (result) {
            LambdaUpdateWrapper<TbLogisticsPackingList> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TbLogisticsPackingList::getPackCode, packCode)
                    .set(TbLogisticsPackingList::getPackUploadState, "已回传")
                    .set(TbLogisticsPackingList::getFbaTurnDate, new Date());
            result = this.update(updateWrapper);

            if (result) {
                // 删除发货清单
                tbBscOverseasWayService.deleteByPackCode(packCode);
                //新增发货清单
                tbBscOverseasWayService.createBscOverseasWay(packCode);
                result = true;
            }
            log.info("执行货件回传>>>>>>>>执行完成");
            return result;
        }
        return result;
    }

    /**
     * 生成直接调拨单
     *
     * @param packCode
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData generateDirectOrder(String packCode) {

        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);

        LoginUser loginUser = LoginContext.me().getLoginUser();

        TbLogisticsPackingList model = this.getById(packCode);

        TbLogisticsTransferRecord logisticsTransferRecord = new TbLogisticsTransferRecord();

        List<ErpWarehouseCode> organizeList = tbLogisticsPackingListMapper.getetErpWarehouseCodeList();//获取ERP组织结构  WAREHOUSE.ERP_WAREHOUSE_CODE
        TbLogisticsPackingList shipmentList = this.getById(packCode);//根据PackCode获取出货清单信息

        String outElePlatformName = tbLogisticsPackingListMapper.getElePlatformName(shipmentList.getShopNameSimple(), shipmentList.getCountryCode());//根据账号站点获取所属电商平台
        String transferOutHouseName = "";
        String useOrganization = "";
        if ("亚马逊仓".equals(model.getComWarehouseType()) && shipmentList.getShopPlatName() != null) {
            transferOutHouseName = shipmentList.getComShopPlatName() + "_" + shipmentList.getComShopNameSimple() + "_" + shipmentList.getComCountryCode() + "_" + "仓库"; //调出仓库名称
            useOrganization = shipmentList.getComShopPlatName() + "_" + shipmentList.getComShopNameSimple() + "_" + shipmentList.getComCountryCode();
            logisticsTransferRecord.setTransferOutOrganizeName(shipmentList.getComShopPlatName() + "_" + shipmentList.getComShopNameSimple() + "_" + shipmentList.getComCountryCode());
            logisticsTransferRecord.setTransfersSource("FBA仓发海外仓调拨");//调拨来源
        } else {
            transferOutHouseName = "海外仓".equals(model.getComWarehouseType()) ? model.getComWarehouseName() : outElePlatformName + "_" + model.getShopNameSimple() + "_" + model.getCountryCode() + "_" + "仓库";//调出仓库名称

            useOrganization = outElePlatformName + "_" + shipmentList.getShopNameSimple() + "_" + shipmentList.getCountryCode();
            logisticsTransferRecord.setTransferOutOrganizeName(!"海外仓".equals(model.getPackStoreHouseType()) ? outElePlatformName + "_" + shipmentList.getShopNameSimple() + "_" + shipmentList.getCountryCode() : model.getPackStoreHouseName());//调出组织名称
            logisticsTransferRecord.setTransfersSource("海外仓".equals(model.getComWarehouseType()) ? "海外仓发FBA仓调拨" : "欧洲站点调拨");//调拨来源
        }

        String finalTransferOutHouseName = transferOutHouseName;
        String finalUseOrganization = useOrganization;
        String TransferOutHouseCode = organizeList.stream().filter(x -> x.getOrganizationName().equals(finalTransferOutHouseName) && "warehouse".equals(x.getOrganizationType()) && x.getUseOrganization().equals(finalUseOrganization))
                .collect(Collectors.toList()).get(0).getOrganizationCode();//调出仓库编码


        String inElePlatformName = tbLogisticsPackingListMapper.getElePlatformName(model.getShopNameSimple(), model.getCountryCode());//根据账号站点获取所属电商平台

        logisticsTransferRecord.setTransferInOrganizeName(inElePlatformName + "_" + model.getShopNameSimple() + "_" + model.getCountryCode());//调入组织名称

        logisticsTransferRecord.setTransferInOrganizeCode(organizeList.stream().filter(x -> x.getOrganizationName().equals(logisticsTransferRecord.getTransferInOrganizeName()) && "organization".equals(x.getOrganizationType()) && x.getUseOrganization().equals(logisticsTransferRecord.getTransferInOrganizeName())).findFirst().get().getOrganizationCode());//调入组织编码
        String transferInHouseName = "海外仓".equals(model.getPackStoreHouseType()) ? model.getPackStoreHouseName() : inElePlatformName + "_" + model.getShopNameSimple() + "_" + model.getCountryCode() + "_" + "仓库";//调入仓库名称


        logisticsTransferRecord.setTransferOutOrganizeCode(organizeList.stream().filter(x -> x.getOrganizationName().equals(logisticsTransferRecord.getTransferOutOrganizeName()) && "organization".equals(x.getOrganizationType()) && x.getUseOrganization().equals(logisticsTransferRecord.getTransferOutOrganizeName())).findFirst().get().getOrganizationCode());//调出组织编码

        logisticsTransferRecord.setTransfersType(logisticsTransferRecord.getTransferInOrganizeName().equals(logisticsTransferRecord.getTransferOutOrganizeName()) ? "组织内调拨" : "跨组织调拨");//调拨类型

        logisticsTransferRecord.setPackCode(model.getPackCode());//出货清单号
        logisticsTransferRecord.setTransferDate(new Date());//调拨日期
        logisticsTransferRecord.setTransfersOperator(loginUser.getName());// 操作人
        logisticsTransferRecord.setTransfersOperatorCode(loginUser.getAccount());
        logisticsTransferRecord.setTransfersDirection("普通");//调拨方向
        logisticsTransferRecord.setStatus("未处理");//处理状态

        ErpWarehouseCode searchTransferInHouseCode = organizeList.stream().filter(x -> x.getOrganizationName().equals(transferInHouseName)
                        && "warehouse".equals(x.getOrganizationType())
                        && x.getUseOrganization().equals(inElePlatformName + "_" + model.getShopNameSimple() + "_" + model.getCountryCode()))
                .findFirst().orElse(null);//调出仓库编码;

        if (searchTransferInHouseCode == null) {
            responseData.setSuccess(false);
            responseData.setCode(500);
            responseData.setMessage("调入仓库编码不存在");
        }
        String transferInHouseCode = searchTransferInHouseCode.getOrganizationCode();//调入仓库编码

        List<TbLogisticsTransferRecord> recordList = tbLogisticsTransferRecordService.getByPackCode(model.getPackCode());
        if (ObjectUtil.isEmpty(recordList)) {
            tbLogisticsTransferRecordService.save(logisticsTransferRecord);//转仓清单
            this.createLogisticsTransferRecordDetFromShipment(model.getPackCode(), transferInHouseCode, transferInHouseName, TransferOutHouseCode, transferOutHouseName, logisticsTransferRecord.getSysId(), null);//转仓清单明细
        }

        return responseData;
    }

    /**
     * 出货清单报表查询
     *
     * @param reqVO
     * @param isExport
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public PageResult<TbLogisticsShipmentReportResult> getLogisticsShipmentReport(TbLogisticsShipmentReportParam reqVO, boolean isExport) {
        //2. 执行分页查询
        Page pageContext;
        if (isExport) {
            pageContext = PageFactory.defaultPage();
            pageContext.setSize(Integer.MAX_VALUE);
        } else {
            pageContext = reqVO.getPageContext();
        }

        IPage<TbLogisticsShipmentReportResult> page = tbLogisticsPackingListMapper.queryPage(pageContext, reqVO);
        return new PageResult<>(page);
    }

    /**
     * 货物清单报表查询
     *
     * @param reqVO
     * @param isExport
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public PageResult<LogisticsGoodsListViewModel> getLogisticsGoodsListReport(TbLogisticsGoodsListReportParam reqVO, boolean isExport) {
        //2. 执行分页查询
        Page pageContext;
        if (isExport) {
            pageContext = PageFactory.defaultPage();
            pageContext.setSize(Integer.MAX_VALUE);
        } else {
            pageContext = reqVO.getPageContext();
        }

        IPage<LogisticsGoodsListViewModel> page = tbLogisticsPackingListMapper.getLogisticsGoodsListReport(pageContext, reqVO);
        return new PageResult<>(page);
    }

    /**
     * 收货差异报表查询
     *
     * @param reqVO
     * @param isExport
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public PageResult<LogisticsReceiveReportViewModel> getLogisticsReceiveReport(TbLogisticsReceiveReportParam reqVO, boolean isExport) {
        //2. 执行分页查询
        Page pageContext;
        if (isExport) {
            pageContext = PageFactory.defaultPage();
            pageContext.setSize(Integer.MAX_VALUE);
        } else {
            pageContext = reqVO.getPageContext();
        }
        IPage<LogisticsReceiveReportViewModel> page = tbLogisticsPackingListMapper.getLogisticsReceiveReport(pageContext, reqVO);
        return new PageResult<>(page);

    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData lpsr(String packStoreHouseType, String packStoreHouseName) {
        List<Map<String, Object>> result = tbLogisticsPackingListMapper.lpsr(packStoreHouseType, packStoreHouseName);
        return ResponseData.success(result);
    }

    @Override
    @DataSource(name = "logistics")
    public List<TbShipmentApplyDetModel> lpsrDet(String packStoreHouseType, String packStoreHouseName) {
        if ("亚马逊仓".equals(packStoreHouseType) || "沃尔玛仓".equals(packStoreHouseType)) {
            return tbLogisticsPackingListMapper.lpsrDetAmazonOrWalmart(packStoreHouseType, packStoreHouseName);
        }
        return tbLogisticsPackingListMapper.lpsrDetOther(packStoreHouseType, packStoreHouseName);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData createLogisticsOrder(TbLogisticsListToHeadRouteParam request) {

        Set<String> packCodeSet = new HashSet<>();        //本次提交的出货清单号
        LoginUser loginUser = LoginContext.me().getLoginUser();
        //1---初始化头程信息
        TbLogisticsListToHeadRoute model = this.initTbLogisticsListToHeadRoute(request, loginUser);
        // 1.2转换为头程信息---表明细项
        List<TbLogisticsListToHeadRouteDet> logisticsListToHeadRouteDetList = new ArrayList<>();
        List<TbLogisticsListToHeadRouteDetParam> headerRouteDetList = request.getHeaderRouteDetList();
        for (TbLogisticsListToHeadRouteDetParam det : headerRouteDetList) {
            TbLogisticsListToHeadRouteDet modelDet = BeanUtil.copyProperties(det, TbLogisticsListToHeadRouteDet.class);
            modelDet.setLhrCode(model.getLhrCode());//发货批次
            modelDet.setLhrOddNumb(model.getLhrOddNumb());//头程物流单号
            modelDet.setSysAddDate(new Date());
            modelDet.setLhrdState("待发货");//物流状态
            //计费重量 = if(装箱重量>箱号体积重)?装箱重量：箱号体积重
            modelDet.setLerChargeWeight(det.getPackDetBoxWeight().compareTo(det.getLerChargeWeight()) > 0 ? det.getPackDetBoxWeight() : det.getLerChargeWeight());
            logisticsListToHeadRouteDetList.add(modelDet);
            packCodeSet.add(det.getPackCode());

        }
        //头程信息表头-设置 总重量
        BigDecimal sumChargeWeight = logisticsListToHeadRouteDetList.stream().map(TbLogisticsListToHeadRouteDet::getLerChargeWeight).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        model.setLhrChargeWeight(sumChargeWeight);

        //2--初始化尾程信息
        TbLogisticsListToEndRoute logisticsListToEndRouteModel = this.initTbLogisticsListToEndRoute(request, model, loginUser);

        //计费量
        BigDecimal logFeeWeight = model.getLogComfirmBillVolume();

        // 查询物流价格条件
        List<TbLogisticsNewPriceDetResult> logistPriceList = tbLogisticsNewPriceService.getTbLogisticsNewPriceDet(model, request.getLhrSendGoodDate(), request.getLogGoodCharacter(), logisticsListToEndRouteModel.getLerPreChargType());

        // 计算头程，尾程的一些费用 根据不同的计费方式 计算
        if (ObjectUtil.isEmpty(logistPriceList)
                || this.existsIntersectionRange(logistPriceList, logisticsListToEndRouteModel.getLerPreChargType())) {

            //没有找到物流价格 或者 物流价格有重叠部分  都按没有物流商报价处理计算
            this.baseLogistPriceCalc(request, logisticsListToEndRouteModel, logFeeWeight);

        } else {
            //有物流商的报价
            TbLogisticsNewPriceDetResult mode = this.filterPriceDet(logistPriceList, logFeeWeight, logisticsListToEndRouteModel.getLerPreChargType());

            if (ObjectUtil.isNull(mode)) {
                //没找到合适的价格区间报价  按没有报价处理计算
                this.baseLogistPriceCalc(request, logisticsListToEndRouteModel, logFeeWeight);
            } else {
                //计算相关费用
                this.calcFee(request, logisticsListToEndRouteModel, mode, logFeeWeight);
            }

            model.setLhrLogFeeWeight(logFeeWeight);

            logisticsListToEndRouteModel.setLerLogFeeWeight(logFeeWeight);

            logisticsListToEndRouteModel.setLerLogAddAndSundryFee(request.getLhrLogAddAndSundryFee());
            logisticsListToEndRouteModel.setLerLogBusySeasonAddFee(request.getLhrLogBusySeasonAddFee());
            logisticsListToEndRouteModel.setLerLogCustClearanceFee(request.getLhrLogCustClearanceFee());
            logisticsListToEndRouteModel.setLerLogCustDlearanceFee(request.getLogpIsEntry() == 1 ? request.getLhrLogCustDlearanceFee() : BigDecimal.ZERO);
            logisticsListToEndRouteModel.setLerLogFuelFee(request.getLhrLogFuelFee());
            logisticsListToEndRouteModel.setLerLogUnitPrice(request.getLhrLogUnitPrice());
            logisticsListToEndRouteModel.setLerLogFee(model.getLhrLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerLogTaxFee(request.getLhrLogTaxFee());
            logisticsListToEndRouteModel.setLerLogFeeTotalNew(
                    logisticsListToEndRouteModel.getLerLogAddAndSundryFee()
                            .add(logisticsListToEndRouteModel.getLerLogAddAndSundryFee())//todo:重复累加了？
                            .add(logisticsListToEndRouteModel.getLerLogTaxFee())
                            .add(logisticsListToEndRouteModel.getLerLogCustDlearanceFee())
                            .add(logisticsListToEndRouteModel.getLerLogCustClearanceFee())
                            .add(logisticsListToEndRouteModel.getLerLogFee())
                            .add(logisticsListToEndRouteModel.getLerLogFuelFee())
                            .setScale(2, RoundingMode.HALF_UP));
        }

        //初始化 头程物流价格信息
        this.initHeadRoutePrice(request, model, logisticsListToEndRouteModel, logFeeWeight);

        //数据推送
        ResponseData responseData = this.pushData(request, packCodeSet, model, logisticsListToEndRouteModel, logFeeWeight);
        if (responseData != null) {
            return responseData;
        }

        List<TbLogisticsListToEndRouteDet> logisticsListToEndRouteDetList = new ArrayList<>();
        request.getHeaderRouteDetList().forEach(l ->
        {
            TbLogisticsListToEndRouteDet LogisticsListToEndRouteDetModel = new TbLogisticsListToEndRouteDet();
            LogisticsListToEndRouteDetModel.setLhrCode(model.getLhrCode());//发货批次
            LogisticsListToEndRouteDetModel.setLhrOddNumb(model.getLhrOddNumb());//头程物流单号
            LogisticsListToEndRouteDetModel.setLerOddNumb(model.getLhrOddNumb());//尾程物流单号
            LogisticsListToEndRouteDetModel.setSysAddDate(new Date());//创建时间
            LogisticsListToEndRouteDetModel.setPackCode(l.getPackCode());//出货清单号
            LogisticsListToEndRouteDetModel.setPackDetBoxNum(l.getPackDetBoxNum());//箱号
            LogisticsListToEndRouteDetModel.setPackDetBoxCode(l.getPackDetBoxCode());//箱条码
            LogisticsListToEndRouteDetModel.setShipmentId(l.getShipmentId());//ShipmentID
            LogisticsListToEndRouteDetModel.setLerdState("");//物流状态
            logisticsListToEndRouteDetList.add(LogisticsListToEndRouteDetModel);
        });

        //创建物流单
        return this.createLogisticsOrder(model, logisticsListToHeadRouteDetList, logisticsListToEndRouteModel, logisticsListToEndRouteDetList);
    }

    @Override
    @DataSource(name = "logistics")
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateLogisticsOrder(TbLogisticsListToHeadRouteParam request) {

        TbLogisticsListToHeadRoute model = BeanUtil.copyProperties(request, TbLogisticsListToHeadRoute.class);
        model.setSysUpdDatetime(new Date());

        //
        String lerPreChargType = model.getLogComfirmBillType();
        // 查询物流价格条件
        List<TbLogisticsNewPriceDetResult> logistPriceList = tbLogisticsNewPriceService.getTbLogisticsNewPriceDet(model, request.getLhrSendGoodDate(), request.getLogGoodCharacter(), lerPreChargType);
        LogisticsBillCalcFeeModel calcFeeModel = new LogisticsBillCalcFeeModel();
//
//        BigDecimal preLogFee = BigDecimal.ZERO; // 预估物流费
//        BigDecimal preLogFuelFee = BigDecimal.ZERO; //预估燃油附加费
//        BigDecimal preLogSurFeeTotal = BigDecimal.ZERO; // 预估物流附加费合计
//        BigDecimal preLogTaxFeeTotal = BigDecimal.ZERO; //预估税费
//        BigDecimal preLogFeeTotal = BigDecimal.ZERO; //预估总费用
//        BigDecimal preLogBusySeasonAddFee = BigDecimal.ZERO; //预估旺季附加费
//        BigDecimal preLogAddAndSundryFee = BigDecimal.ZERO; //预估附加费及杂费
//        BigDecimal preLogCustDlearanceFee = BigDecimal.ZERO; //预估报关费
//        BigDecimal preLogCustClearanceFee = BigDecimal.ZERO; //预估清关费
//        BigDecimal preLogTaxFee = BigDecimal.ZERO; //预估税费
//        BigDecimal preLogFeeTotalManual = BigDecimal.ZERO; //预估
//        BigDecimal preLogUnitPrice =BigDecimal.ZERO; //预估单价
//        BigDecimal logFee = BigDecimal.ZERO; //物流费
//        BigDecimal logTaxFee = BigDecimal.ZERO; //税费
//        BigDecimal logFeeTotal = BigDecimal.ZERO; //总费用

        //计费量
        BigDecimal logFeeWeight = model.getLogComfirmBillVolume();

        calcFeeModel.setLogFee(model.getLhrLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));

        calcFeeModel.setLogFeeTotal(model.getLhrLogBusySeasonAddFee()
                .add(model.getLhrLogAddAndSundryFee())
                .add(model.getLhrLogTaxFee())
                .add(model.getLhrLogCustDlearanceFee())
                .add(model.getLhrLogCustClearanceFee())
                .add(calcFeeModel.getLogFee())
                .add(model.getLhrLogFuelFee())
                .setScale(2, RoundingMode.HALF_UP)
        );

        calcFeeModel.setLogTaxFee(model.getLhrLogTaxFee().compareTo(BigDecimal.ZERO) > 0 ? model.getLhrLogTaxFee() : BigDecimal.ZERO);
        calcFeeModel.setPreLogTaxFee(model.getLhrLogTaxFee().compareTo(BigDecimal.ZERO) > 0 ? model.getLhrLogTaxFee() : BigDecimal.ZERO);
        calcFeeModel.setPreLogFeeTotalManual(
                ObjectUtil.isNotNull(model.getLhrPreLogFeeTotalManual())
                        && model.getLhrPreLogFeeTotalManual().compareTo(BigDecimal.ZERO) > 0
                        ? model.getLhrPreLogFeeTotalManual() : BigDecimal.ZERO);

        calcFeeModel.setLerPreChargType(lerPreChargType);


        if (ObjectUtil.isEmpty(logistPriceList)
                || this.existsIntersectionRange(logistPriceList, lerPreChargType)) {

            //没有找到物流价格 或者 物流价格有重叠部分  都按没有物流商报价处理计算
            this.updateBaseCalc(request, calcFeeModel, logFeeWeight);

        } else {
            //有物流商的报价
            TbLogisticsNewPriceDetResult mode = this.filterPriceDet(logistPriceList, logFeeWeight, lerPreChargType);

            if (ObjectUtil.isNull(mode)) {
                //没找到合适的价格区间报价  按没有报价处理计算
                this.updateBaseCalc(request, calcFeeModel, logFeeWeight);
            } else {
                //计算相关费用
                this.updateCalcFee(request, calcFeeModel, mode, logFeeWeight);
            }
        }


        //头程物流单更新  更具发货批次号 lhrCode
        boolean update = this.updateHeadRoute(model, calcFeeModel, logFeeWeight);

        //头程物流单明细更新
        LambdaUpdateWrapper<TbLogisticsListToHeadRouteDet> headDetWrapper = new LambdaUpdateWrapper<>();
        headDetWrapper.eq(TbLogisticsListToHeadRouteDet::getLhrCode, model.getLhrCode());
        headDetWrapper.set(TbLogisticsListToHeadRouteDet::getLhrOddNumb, model.getLhrOddNumb());
        tbLogisticsListToHeadRouteDetService.update(headDetWrapper);


        ////尾程物流单更新
        boolean update1 = updateEndRoute(model, calcFeeModel, logFeeWeight);
        //尾程物流单明细更新
        LambdaUpdateWrapper<TbLogisticsListToEndRouteDet> endDetWrapper = new LambdaUpdateWrapper<>();
        endDetWrapper.eq(TbLogisticsListToEndRouteDet::getLhrCode, model.getLhrCode());
        endDetWrapper.set(TbLogisticsListToEndRouteDet::getLhrOddNumb, model.getLhrOddNumb());
        tbLogisticsListToEndRouteDetService.update(endDetWrapper);

        //更新出货清单明细1 头程物流单号 货运方式2
        tbLogisticsPackingListDet1Mapper.updateLogNoAndTraMode2(model.getLhrCode(), model.getLhrOddNumb(), model.getLogTraMode2(), model.getLogTraMode1());
        // 更新发货清单货运方式
        tbBscOverseasWayService.updBscOverseasWayDeliveryType(model.getLhrCode(), model.getLogTraMode2());


        List<TbLogisticsListToHeadRouteDet> routeDetList = this.getToHeadRouteDets(request.getLhrCode(), request.getLhrOddNumb());

        EbmsLogisticsSettlementParam param = this.createEbmsLogisticsSettlementParam(request, model, calcFeeModel);

        if (!"CNY".equals(param.getCurrency())) {
            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
            rateParam.setOriginalCurrency(param.getCurrency());
            rateParam.setEffectDate(DateUtil.beginOfDay(param.getShipmentDate()));
            FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
            if (fixedExchangeRate == null) {
                return ResponseData.error("ERP固定汇率不存在！币别：" + param.getCurrency() + "，发货日期：" + param.getShipmentDate());
            }
            param.setDirectRate(fixedExchangeRate.getDirectRate());
        }

        return ResponseData.success(param);
    }

    private EbmsLogisticsSettlementParam createEbmsLogisticsSettlementParam(TbLogisticsListToHeadRouteParam request, TbLogisticsListToHeadRoute model, LogisticsBillCalcFeeModel calcFeeModel) {
        List<TbLogisticsListToHeadRouteDet> routeDets = tbLogisticsListToHeadRouteDetService.queryByLhrCode(request.getLhrCode());
        List<String> shipmentIdList = routeDets.stream().map(rd -> rd.getShipmentId()).distinct().collect(Collectors.toList());

        List<TbLogisticsPackingListDet2> packingListDet2s = det2Service.queryByLhrCode(request.getLhrCode());
        Integer shipmentQuantity = packingListDet2s.stream().map(TbLogisticsPackingListDet2::getQuantity).reduce(Integer::sum).get();

        List<TbLogisticsListToHeadRoute> headRouteList = tbLogisticsListToHeadRouteService.queryByLhrCode(request.getLhrCode());
        TbLogisticsListToHeadRoute modeHead = headRouteList.get(0);
//        LoginUser modeHead = LoginContext.me().getLoginUser();
        EbmsLogisticsSettlementParam param = new EbmsLogisticsSettlementParam();
        param.setShipmentDate(model.getLhrSendGoodDate());
        param.setShipmentNum(model.getLhrCode());
//                param.setSysShopsName(model.getShopNameSimple());
//                param.setSysSite( model.getCountryCode());
//                param.setLcCode(model.getLcCode());
        param.setCreateTime(new Date());
        param.setCreateUser(modeHead.getSysPerCode() + "_" + modeHead.getSysPerName());
        param.setFreightCompany(model.getLogTraMode1());
        param.setIsCustoms(model.getLogpIsEntry() == 1 ? "是" : "否");
        param.setLogisticsChannel(model.getLogSeaTraRoute());
        param.setLogisticsOrder(model.getLhrOddNumb());
        param.setHasTax(model.getLogpIsIncTax() == 1 ? "是" : "否");
        param.setOrderType(model.getLogRedOrBlueList());
        param.setTransportType(model.getLogTraMode2());
        param.setShipmentUnit(Long.valueOf(modeHead.getLhrOutGoodNum()));
        param.setVolumeWeight(
                "快递".equals(model.getLogTraMode2()) ?
                        model.getLhrOutGoodVolume().multiply(BigDecimal.valueOf(1000000)).divide(BigDecimal.valueOf(5000), 2, RoundingMode.HALF_UP)
                        : model.getLhrOutGoodVolume().multiply(BigDecimal.valueOf(1000000)).divide(BigDecimal.valueOf(6000), 2, RoundingMode.HALF_UP)
        );
        param.setVolume(model.getLhrOutGoodVolume());
        param.setWeight(model.getLhrOutGoodWeight());
        param.setConfirmCountFee(model.getLogComfirmBillVolume());
        param.setConfirmFeeType(calcFeeModel.getLerPreChargType());
        param.setShipmentQuantity(Long.valueOf(shipmentQuantity));
        param.setShipmentId(String.join(",", shipmentIdList));
//                param.setLogisticsCurrency(calcFeeModel.getLerLogComfirmBillCurrency());
        param.setPredictUnitPrice(calcFeeModel.getPreLogUnitPrice());
        param.setPredictBusySeasonFee(calcFeeModel.getPreLogBusySeasonAddFee());
//                param.setPredictClearCustomsFee(calcFeeModel.getPreLogCustClearanceFee());
        param.setPredictLogisticsFee(calcFeeModel.getPreLogFee());
        param.setPredictOilFee(calcFeeModel.getPreLogFuelFee());
        param.setPredictOthersFee(calcFeeModel.getPreLogAddAndSundryFee());
//                param.setPredictCustomsFee(calcFeeModel.getPreLogCustDlearanceFee());
        param.setPredictTaxFee(calcFeeModel.getPreLogTaxFee());
        param.setPredictTotalFee(calcFeeModel.getPreLogFeeTotal());
        param.setUpdateTime(new Date());
        param.setUpdateUser(modeHead.getSysPerCode() + "_" + modeHead.getSysPerName());
//                param.setUnitPrice(model.getLhrLogUnitPrice());
//                param.setBusySeasonFee(model.getLhrLogBusySeasonAddFee());
//                param.setClearCustomsFee(model.getLhrLogCustClearanceFee());
//                param.setLogisticsFee(calcFeeModel.getLogFee());
//                param.setOilFee(model.getLhrLogFuelFee());
//                param.setOthersFee(model.getLhrLogAddAndSundryFee());
//                param.setCustomsFee(model.LhrLogCustDlearanceFee());
//                param.setTaxFee(calcFeeModel.getLogTaxFee());
//                param.setTotalFee(calcFeeModel.getLogFeeTotal());

        param.setCurrency(request.getLogComfirmBillCurrency());

        return param;
    }

    private List<TbLogisticsListToHeadRouteDet> getToHeadRouteDets(String lhrCode, String lhrOddNumb) {
        List<TbLogisticsListToHeadRouteDet> routeDets = tbLogisticsListToHeadRouteDetService.queryByLhrCode(lhrCode);
        if (ObjectUtil.isNotEmpty(lhrOddNumb)) {
            routeDets = routeDets.stream().filter(det -> det.getLhrOddNumb().equals(lhrOddNumb)).collect(Collectors.toList());
        }
        return routeDets;
    }

    private boolean updateEndRoute(TbLogisticsListToHeadRoute model, LogisticsBillCalcFeeModel calcFeeModel, BigDecimal logFeeWeight) {
        //尾程物流单更新
        TbLogisticsListToEndRoute endRoute = new TbLogisticsListToEndRoute();

        endRoute.setLhrOddNumb(model.getLhrOddNumb());
        endRoute.setLogEndRouteLink(model.getLogHeadRouteLink());
        endRoute.setLogTraMode1(model.getLogTraMode1());
        endRoute.setLerPreChargType(calcFeeModel.getLerPreChargType());
        endRoute.setLerPreLogFee(calcFeeModel.getPreLogFee());// 预估物流费
        endRoute.setLerPreLogSurFeeTotal(calcFeeModel.getPreLogSurFeeTotal());//预估物流附加费合计

        endRoute.setLerPreLogSurFeeDet("");//预估物流附加费明细
        endRoute.setLerPreLogFuelFee(calcFeeModel.getPreLogFuelFee());//预估燃油附加费
        endRoute.setLerPreLogTaxFeeTotal(calcFeeModel.getPreLogTaxFeeTotal());//预估税费
        endRoute.setLerPreLogBusySeasonAddFee(calcFeeModel.getPreLogBusySeasonAddFee()); //预估旺季附加费
        endRoute.setLerPreLogAddAndSundryFee(calcFeeModel.getPreLogAddAndSundryFee()); //预估附加费及杂费
        endRoute.setLerPreLogCustDlearanceFee(calcFeeModel.getPreLogCustDlearanceFee()); //预估报关费
        endRoute.setLerPreLogCustClearanceFee(calcFeeModel.getPreLogCustClearanceFee()); //预估清关费
        endRoute.setLerPreLogTaxFee(calcFeeModel.getPreLogTaxFee()); //预估税费
        endRoute.setLerPreLogFeeTotalManual(calcFeeModel.getPreLogFeeTotalManual()); //预估总费用(人工维护)
        endRoute.setLerPreLogFeeTotal(calcFeeModel.getPreLogFeeTotal());//预估总费用
        endRoute.setLerLogFeeWeight(logFeeWeight);//预估使用的计费量
        endRoute.setLerLogComfirmBillCurrency(calcFeeModel.getLerLogComfirmBillCurrency()); //计费币种
        endRoute.setLerPreLogUnitPrice(calcFeeModel.getPreLogUnitPrice()); //预估单价
        endRoute.setLerPreLogUnitPriceType(calcFeeModel.getLerPreLogUnitPriceType()); //计费单价类型
        endRoute.setLerPreLogAddAndSundryFeeRemark(model.getLhrPreLogAddAndSundryFeeRemark());//预估附加费及杂费备注
        endRoute.setLerLogTaxFee(calcFeeModel.getPreLogTaxFee()); //税费
        endRoute.setLerLogFeeTotalNew(calcFeeModel.getLogFeeTotal()); //总费用
        endRoute.setLerPreLogFeeTotalNew(calcFeeModel.getPreLogFeeTotal()); //预估总费用
        endRoute.setLerLogAddAndSundryFee(model.getLhrLogAddAndSundryFee());//附加费及杂费
        endRoute.setLerLogBusySeasonAddFee(model.getLhrLogBusySeasonAddFee());//旺季附加费
        endRoute.setLerLogCustClearanceFee(model.getLhrLogCustClearanceFee()); //清关费
        endRoute.setLerLogCustDlearanceFee(model.getLogpIsEntry() == 1 ? model.getLhrLogCustDlearanceFee() : BigDecimal.ZERO); //报关费
        endRoute.setLerLogFuelFee(model.getLhrLogFuelFee()); //燃油附加费
        endRoute.setLerLogUnitPrice(model.getLhrLogUnitPrice()); //单价
        endRoute.setLerLogFee(calcFeeModel.getLogFee());//物流费

        LambdaUpdateWrapper<TbLogisticsListToEndRoute> endRouteUpdateWrapper = new LambdaUpdateWrapper<>();
        endRouteUpdateWrapper.eq(TbLogisticsListToEndRoute::getLhrCode, model.getLcCode());

        boolean update1 = tbLogisticsListToEndRouteService.update(endRoute, endRouteUpdateWrapper);
        return update1;
    }

    private boolean updateHeadRoute(TbLogisticsListToHeadRoute model, LogisticsBillCalcFeeModel calcFeeModel, BigDecimal logFeeWeight) {

        // //头程物流单更新  更具发货批次号 lhrCode

        LambdaUpdateWrapper<TbLogisticsListToHeadRoute> wrapper = new LambdaUpdateWrapper<>();
        //一个批次号对应一条数据
        List<TbLogisticsListToHeadRoute> headRouteList = tbLogisticsListToHeadRouteService.queryByLhrCode(model.getLhrCode());
        TbLogisticsListToHeadRoute headRoute = headRouteList.get(0);


        model.setLhrPreLogTaxFee(calcFeeModel.getPreLogTaxFee()); //预估税费
        model.setLhrPreLogFeeTotalManual(calcFeeModel.getPreLogFeeTotal()); //预估总费用(人工维护)
        model.setLhrLogFeeWeight(logFeeWeight); //预估使用的计费量
        model.setLhrPreLogAddAndSundryFee(calcFeeModel.getPreLogAddAndSundryFee());//预估附加费及杂费
        model.setLhrPreLogBusySeasonAddFee(calcFeeModel.getPreLogBusySeasonAddFee()); //预估旺季附加费
        model.setLhrPreLogCustClearanceFee(calcFeeModel.getPreLogCustClearanceFee()); //预估清关费
        model.setLhrPreLogCustDlearanceFee(calcFeeModel.getPreLogCustDlearanceFee()); //预估报关费
        model.setLhrPreLogFuelFee(calcFeeModel.getPreLogFuelFee()); //预估燃油附加费
        model.setLhrPreLogUnitPrice(calcFeeModel.getPreLogUnitPrice()); //预估单价
        model.setLogComfirmBillCurrency(calcFeeModel.getLerLogComfirmBillCurrency()); //计费币种
        model.setLhrPreLogUnitPriceType(calcFeeModel.getLerPreLogUnitPriceType()); //计费单价类型
        //        model.setLhrPreLogAddAndSundryFeeRemark = model.LhrPreLogAddAndSundryFeeRemark, //预估附加费及杂费备注1
        model.setLhrLogTaxFee(calcFeeModel.getPreLogTaxFee()); //税费
        model.setLhrLogFeeTotalNew(calcFeeModel.getLogFeeTotal()); //总费用
        model.setLhrPreLogFeeTotalNew(calcFeeModel.getPreLogFeeTotal());//预估总费用
//        model.setLhrLogAddAndSundryFee = model.LhrLogAddAndSundryFee,//附加费及杂费1
//        model.setLhrLogBusySeasonAddFee = model.LhrLogBusySeasonAddFee, //旺季附加费1
//        model.setLhrLogCustClearanceFee = model.LhrLogCustClearanceFee, //清关费1
        model.setLhrLogCustDlearanceFee(model.getLogpIsEntry() == 1 ? model.getLhrLogCustDlearanceFee() : BigDecimal.ZERO); //报关费
//        model.setLhrLogFuelFee = model.LhrLogFuelFee, //燃油附加费1
//        model.setLhrLogUnitPrice = model.LhrLogUnitPrice, //单价1
        model.setLhrLogFee(calcFeeModel.getLogFee()); //物流费
        model.setLhrPreLogFee(calcFeeModel.getPreLogFee());
//        model.eq(TbLogisticsListToHeadRoute::getLhrCode, model.getLcCode());
        model.setId(headRoute.getId());
        return tbLogisticsListToHeadRouteService.updateById(model);
    }

    private void updateCalcFee(TbLogisticsListToHeadRouteParam request, LogisticsBillCalcFeeModel logisticsListToEndRouteModel, TbLogisticsNewPriceDetResult mode, BigDecimal logFeeWeight) {

        if ("重量".equals(logisticsListToEndRouteModel.getLerPreChargType())) {
            //实际重量

            //有报价 计算
            if (mode.getBusLogpDetUnitPrice().compareTo(BigDecimal.ZERO) > 0) {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("系统匹配");
            } else {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("");
            }

            logisticsListToEndRouteModel.setPreLogBusySeasonAddFee(mode.getBusLogpDetBusySeasonAddFee().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogAddAndSundryFee(mode.getBusLogpDetAddAndSundryFee().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogTaxFee(logisticsListToEndRouteModel.getPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogTaxFeeTotal(logisticsListToEndRouteModel.getPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogCustDlearanceFee(request.getLogpIsEntry() == 1 ? mode.getBusLogpDetCustDlearanceFee().setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            logisticsListToEndRouteModel.setPreLogCustClearanceFee(mode.getBusLogpDetCustClearanceFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogUnitPrice(mode.getBusLogpDetUnitPrice().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogFee(logisticsListToEndRouteModel.getPreLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogFuelFee(
                    logisticsListToEndRouteModel.getPreLogFee().multiply(mode.getBusLogpDetFuelFee().divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP)));


            BigDecimal lerPreLogFeeTotal = logisticsListToEndRouteModel.getPreLogBusySeasonAddFee()
                    .add(logisticsListToEndRouteModel.getPreLogAddAndSundryFee())
                    .add(logisticsListToEndRouteModel.getPreLogTaxFee())
                    .add(logisticsListToEndRouteModel.getPreLogCustDlearanceFee())
                    .add(logisticsListToEndRouteModel.getPreLogCustClearanceFee())
                    .add(logisticsListToEndRouteModel.getPreLogFee())
                    .add(logisticsListToEndRouteModel.getPreLogFuelFee())
                    .setScale(2, RoundingMode.HALF_UP);
            logisticsListToEndRouteModel.setPreLogFeeTotal(lerPreLogFeeTotal);
            logisticsListToEndRouteModel.setLerLogComfirmBillCurrency(
                    ObjectUtil.isNotEmpty(request.getLogComfirmBillCurrency()) ? request.getLogComfirmBillCurrency() : mode.getBusLogpChargCurrency());
            logisticsListToEndRouteModel.setPreLogFeeTotalNew(lerPreLogFeeTotal);


        } else if ("体积".equals(logisticsListToEndRouteModel.getLerPreChargType())) {
            //体积重量
            if (mode.getBusLogpDetUnitPrice().compareTo(BigDecimal.ZERO) > 0) {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("系统匹配");
            } else {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("");
            }

            logisticsListToEndRouteModel.setPreLogBusySeasonAddFee(BigDecimal.ZERO);
            logisticsListToEndRouteModel.setPreLogAddAndSundryFee(BigDecimal.ZERO);
            logisticsListToEndRouteModel.setPreLogTaxFee(logisticsListToEndRouteModel.getPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogTaxFeeTotal(logisticsListToEndRouteModel.getPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogCustDlearanceFee(request.getLogpIsEntry() == 1 ? mode.getBusLogpDetCustDlearanceFee().setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            logisticsListToEndRouteModel.setPreLogCustClearanceFee(mode.getBusLogpDetCustClearanceFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogUnitPrice(mode.getBusLogpDetUnitPrice().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogFee(logisticsListToEndRouteModel.getPreLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setPreLogFuelFee(
                    logisticsListToEndRouteModel.getPreLogFee().multiply(mode.getBusLogpDetFuelFee().divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP)));


            BigDecimal lerPreLogFeeTotal = logisticsListToEndRouteModel.getPreLogBusySeasonAddFee()
                    .add(logisticsListToEndRouteModel.getPreLogAddAndSundryFee())
                    .add(logisticsListToEndRouteModel.getPreLogTaxFee())
                    .add(logisticsListToEndRouteModel.getPreLogCustDlearanceFee())
                    .add(logisticsListToEndRouteModel.getPreLogCustClearanceFee())
                    .add(logisticsListToEndRouteModel.getPreLogFee())
                    .add(logisticsListToEndRouteModel.getPreLogFuelFee())
                    .setScale(2, RoundingMode.HALF_UP);
            logisticsListToEndRouteModel.setPreLogFeeTotal(lerPreLogFeeTotal);
            logisticsListToEndRouteModel.setLerLogComfirmBillCurrency(
                    ObjectUtil.isNotEmpty(request.getLogComfirmBillCurrency()) ? request.getLogComfirmBillCurrency() : mode.getBusLogpChargCurrency());
            logisticsListToEndRouteModel.setPreLogFeeTotalNew(lerPreLogFeeTotal);

        } else {
            //其他计费类型  走基础计算
            this.updateBaseCalc(request, logisticsListToEndRouteModel, logFeeWeight);
        }


    }

    private void updateBaseCalc(TbLogisticsListToHeadRouteParam request, LogisticsBillCalcFeeModel calcFeeModel, BigDecimal logFeeWeight) {
        calcFeeModel.setPreLogFuelFee(BigDecimal.ZERO);
        calcFeeModel.setPreLogSurFeeTotal(BigDecimal.ZERO);
        calcFeeModel.setPreLogSurFeeTotal(calcFeeModel.getPreLogTaxFee());
        calcFeeModel.setPreLogBusySeasonAddFee(BigDecimal.ZERO);
        calcFeeModel.setPreLogAddAndSundryFee(BigDecimal.ZERO);
        calcFeeModel.setPreLogCustDlearanceFee(BigDecimal.ZERO);
        calcFeeModel.setPreLogCustClearanceFee(BigDecimal.ZERO);
        calcFeeModel.setLerLogComfirmBillCurrency(ObjectUtil.isNotEmpty(request.getLogComfirmBillCurrency()) ? request.getLogComfirmBillCurrency() : null);
        calcFeeModel.setPreLogUnitPrice(BigDecimal.ZERO);
        calcFeeModel.setPreLogFee(calcFeeModel.getPreLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
        calcFeeModel.setPreLogFeeTotal(calcFeeModel.getPreLogBusySeasonAddFee().
                add(calcFeeModel.getPreLogAddAndSundryFee())
                .add(calcFeeModel.getPreLogTaxFee())
                .add(calcFeeModel.getPreLogCustDlearanceFee())
                .add(calcFeeModel.getPreLogCustClearanceFee())
                .add(calcFeeModel.getPreLogFee())
                .add(calcFeeModel.getPreLogFuelFee())
                .setScale(2, RoundingMode.HALF_UP));
        calcFeeModel.setLerPreLogUnitPriceType("");
    }

    /**
     * 根据批次号和快递单号 更新出货清单信息 状态
     *
     * @param lhrCode
     * @param lhrOddNumb
     * @param packLogState
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public int updatePackLogState(String lhrCode, String lhrOddNumb, String packLogState) {
        return tbLogisticsPackingListMapper.updatePackLogState(lhrCode, lhrOddNumb, packLogState);
    }

    @Override
    @DataSource(name = "logistics")
    public List<TbLogisticsPackingList> queryByLhrCode(String lhrCode) {
        return tbLogisticsPackingListMapper.queryByLhrCode(lhrCode);

    }

    @Override
    @DataSource(name = "logistics")
    public List<TbLogisticsPackingList> getLogisticsShipmentList(String shopNameSimple, String countryCode, String comWarehouseName) {
        LambdaUpdateWrapper<TbLogisticsPackingList> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TbLogisticsPackingList::getShopNameSimple, shopNameSimple)
                .eq(TbLogisticsPackingList::getCountryCode, countryCode)
                .eq(TbLogisticsPackingList::getComWarehouseName, comWarehouseName)
                .eq(TbLogisticsPackingList::getPackUploadState, "未绑定")
                .and(e -> e.isNull(TbLogisticsPackingList::getPackAbnormalStatus).or().ne(TbLogisticsPackingList::getPackAbnormalStatus, 1));
        return tbLogisticsPackingListMapper.selectList(wrapper);
    }

    /**
     * 更新出货清单状态
     *
     * @param packCode
     * @param packUploadState
     * @param packAbnormalStatus
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public int upPackUploadStateAndPackAbnormalStatus(String packCode, String packUploadState, int packAbnormalStatus) {
        LambdaUpdateWrapper<TbLogisticsPackingList> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TbLogisticsPackingList::getPackCode, packCode)
                .set(TbLogisticsPackingList::getPackUploadState, packUploadState)
                .eq(TbLogisticsPackingList::getPackAbnormalStatus, packAbnormalStatus);

        return tbLogisticsPackingListMapper.update(null, wrapper);
    }

    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData receiveK3PushPackingList(TbLogisticsPackingListK3Param request) throws Exception {
        log.info("接收金蝶推送的出货清单信息[{}]", JSON.toJSONString(request));

        try {
            boolean checkMark = false;
            String PackStoreHouseName = "";
            String PackStoreHouseType = "亚马逊仓";

            ResponseData responseData = shopEntityConsumer.queryById(request.getDemandOrg());
            //1.1 校验需求组织
            if (!responseData.getSuccess() || responseData.getData() == null) {
                return ResponseData.error(-1,"【需求组织】在JCERP中不存在!");
            }

            String[] shop = request.getDemandOrg().split("_");
            String elePlatformName = shop[0]; //平台
            String shopNameSimple = shop[1]; //店铺简称
            String countryCode = shop[2]; //站点

            //1.2 校验上传状态
            if (ObjectUtil.isEmpty(request.getPackCode())) {
                return ResponseData.error(-1,"【装箱清单号】不能为空!");
            }
            TbLogisticsPackingList packModel = tbLogisticsPackingListMapper.selectById(request.getPackCode());


            if (packModel != null && "已上传".equals(packModel.getPackUploadState())) {
                return ResponseData.error(-1,"业务流程执行完毕，不能进行此操作!");
            }


            List<PackingRecordSyncBoxDetResult> boxDetResults = new ArrayList<>();
            List<PackingRecordSyncSkuDetResult> skuDetResults = null;

            List<TbLogisticsPackingListDet1> packListData = new ArrayList<>();
            List<TbLogisticsPackingListDet2> packInfoData = new ArrayList<>();

            for (TbLogisticsPackingListK3Param.BoxDetDTO l : request.getBoxDet()) {

                skuDetResults = new ArrayList<>();

                ResponseData checkedresult = checkLengthAndWeigUnit(l);
                if (!checkedresult.getSuccess()) {
                    checkedresult.setCode(-1);
                    return checkedresult;
                }
                TbLogisticsPackingListDet1 logisticsPackingListDet1 = createTbLogisticsPackingListDet1(l);


                PackingRecordSyncBoxDetResult boxDetResult = new PackingRecordSyncBoxDetResult();
                boxDetResult.setPackDetBoxCode(l.getPackDetBoxCode());
                boxDetResult.setPackDetBoxNum(l.getPackDetBoxNum());

//            // 查询标签信息   根据countryCode + Sku + FnSKU + MateCode
                List<String> csfm = l.getSkuDet().stream().map(d -> countryCode + d.getSku() + d.getFnSKU() + d.getMateCode()).distinct().collect(Collectors.toList());
                List<String> lableCheck = tbLogisticsPackListService.getTbComShippingLableFromEbms(csfm);

                for (TbLogisticsPackingListK3Param.BoxDetDTO.SkuDetDTO x : l.getSkuDet()) {

                    PackingRecordSyncSkuDetResult skuDetResult = new PackingRecordSyncSkuDetResult();
                    skuDetResult.setSku(x.getSku());
                    skuDetResult.setPickListCode(x.getPickListCode());
                    skuDetResult.setQuantity(x.getQuantity());
                    String errorMsg = "";

                    //3.1 sku+FNSKU+MateCode 在标签表中是否存在

                    String mergeFildStr = countryCode + x.getSku() + x.getFnSKU() + x.getMateCode();
                    if (ObjectUtil.isEmpty(lableCheck) || (!lableCheck.contains(mergeFildStr) && "Amazon".equals(elePlatformName))) {
                        errorMsg = "在标签库中不存在。";
                        skuDetResult.setErrorMsg(errorMsg);
                        skuDetResults.add(skuDetResult);
                        checkMark = true;
                        continue;
                    }
                    //3.2 数量为整数
                    if (x.getQuantity() == 0) {
                        errorMsg = "Sku数量需大于0。";
                        skuDetResult.setErrorMsg(errorMsg);
                        skuDetResults.add(skuDetResult);
                        checkMark = true;
                        continue;
                    }
                    //3.3 PickListCode、DepName、TeamName、PerName 非空
                    if (ObjectUtil.isEmpty(x.getPickListCode())) {
                        errorMsg = "PickListCode信息项不能为空。";
                        skuDetResult.setErrorMsg(errorMsg);
                        skuDetResults.add(skuDetResult);
                        checkMark = true;
                        continue;
                    }
                    //if (string.IsNullOrEmpty(x.DepName))
                    //{
                    //    errorMsg = "DepName信息项不能为空。";
                    //    skuDetResult.ErrorMsg = errorMsg;
                    //    skuDetResults.Add(skuDetResult);
                    //    checkMark = true;
                    //    continue;
                    //}
                    if (ObjectUtil.isEmpty(x.getTeamName())) {
                        errorMsg = "TeamName信息项不能为空。";
                        skuDetResult.setErrorMsg(errorMsg);
                        skuDetResults.add(skuDetResult);
                        checkMark = true;
                        continue;
                    }
                    if (ObjectUtil.isEmpty(x.getPerName())) {
                        errorMsg = "PerName信息项不能为空。";
                        skuDetResult.setErrorMsg(errorMsg);
                        skuDetResults.add(skuDetResult);
                        checkMark = true;
                        continue;
                    }

                    TbLogisticsPackingListDet2 logisticsPackingListDet2 = new TbLogisticsPackingListDet2();
                    BeanUtil.copyProperties(x, logisticsPackingListDet2);
                    logisticsPackingListDet2.setPackDetBoxNumUpload("Box " + l.getPackDetBoxNum() + " - QTY");
                    PackStoreHouseName = x.getPackStoreHouseName();
                    logisticsPackingListDet2.setPackDetBoxNum(l.getPackDetBoxNum());
                    logisticsPackingListDet2.setPackDetBoxCode(l.getPackDetBoxCode());
                    logisticsPackingListDet2.setFnSku(x.getFnSKU());
                    packInfoData.add(logisticsPackingListDet2);

                    skuDetResult.setErrorMsg(errorMsg);
                    skuDetResults.add(skuDetResult);
                }

                boxDetResult.setSkuDet(skuDetResults);
                boxDetResults.add(boxDetResult);
                packListData.add(logisticsPackingListDet1);
            }


            PackingRecordSyncMainResult result = new PackingRecordSyncMainResult();
            result.setPackCode(request.getPackCode());
            result.setBillType(request.getBillType());
            result.setBoxDet(boxDetResults);

            if (PackStoreHouseName.toUpperCase().contains("AMAZON")) {
                PackStoreHouseType = "亚马逊仓";
                PackStoreHouseName = "亚马逊仓";
            } else {
                PackStoreHouseType = "海外仓";
            }

            if (request.getDemandOrg().toUpperCase().contains("WALMART")) {
                PackStoreHouseType = "沃尔玛仓";
                PackStoreHouseName = "沃尔玛仓";
            }

            TbLogisticsPackingList logistics = createTbLogisticsPackingList(request, countryCode, elePlatformName, shopNameSimple, PackStoreHouseName, PackStoreHouseType);


            if (!checkMark) {
                // 体积重和实重计算
                BigDecimal packTotalWeight = packListData.stream().map(TbLogisticsPackingListDet1::getPackDetBoxWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal packTotalVolume = packListData.stream().map(TbLogisticsPackingListDet1::getPackDetBoxVolume).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal packTotalVolumeWeight;

                Map<String, List<TbLogisticsPackingListDet2>> collect = packInfoData.stream().collect(Collectors.groupingBy(TbLogisticsPackingListDet2::getPackSugShipMethod));

                if (packInfoData.get(0).getPackSugShipMethod().equals("快递")
                        && packInfoData.stream().collect(Collectors.groupingBy(TbLogisticsPackingListDet2::getPackSugShipMethod)).size() == 1) {
                    //有快递，并且只有快递
                    packTotalVolumeWeight = packTotalVolume.multiply(BigDecimal.valueOf(1000000)).divide(BigDecimal.valueOf(5000), 2, RoundingMode.HALF_UP);
                } else {
                    packTotalVolumeWeight = packTotalVolume.multiply(BigDecimal.valueOf(1000000)).divide(BigDecimal.valueOf(6000), 2, RoundingMode.HALF_UP);
                }


                logistics.setPackTotalWeight(packTotalWeight.setScale(2, RoundingMode.HALF_UP));
                logistics.setPackTotalVolume(packTotalVolume.setScale(4, RoundingMode.HALF_UP));
                logistics.setPackTotalVolumeWeight(packTotalVolumeWeight);

                //4.1 清数据 +保存新的数据
                if (this.clearPackRecord(request.getPackCode())) {
                    //4.2 保存数据
                    if (this.createPackList(logistics, packListData, packInfoData)) {
                        return ResponseData.success(0, "接口访问成功.", result);
                    } else {
                        return ResponseData.error(-1, "接口访问成功,装箱单记录重新生成过程中出现异常.", result);
                    }
                } else {
                    return ResponseData.error(-1, "接口访问成功,装箱单记录清理过程中出现异常.", result);
                }
            } else {
                return ResponseData.success(0, "接口访问成功,数据校验结果出现异常情况.", result);
            }
        } catch (Exception e) {
            log.error("JCERP处理k3推送的出货清单过程中发生异常【{}】", JSON.toJSONString(e));
            return ResponseData.error(-1, "JCERP处理过程中发生异常.", null);
        }
    }


    @DataSource(name = "logistics")
    private boolean createPackList(TbLogisticsPackingList packList,
                                   List<TbLogisticsPackingListDet1> packListData,
                                   List<TbLogisticsPackingListDet2> packInfoData) {

        if (this.save(packList)) {
            if (ObjectUtil.isNotEmpty(packListData)) {
                packListData.forEach(x -> {
                    x.setPackCode(packList.getPackCode());
                });
                tbLogisticsPackingListDet1Service.saveBatch(packListData);
            }

            if (ObjectUtil.isNotEmpty(packInfoData)) {
                packInfoData.forEach(x -> {
                    x.setPackCode(packList.getPackCode());
                });
                tbLogisticsPackingListDet2Service.saveBatch(packInfoData);
            }
            return true;
        }

        return false;
    }

    @DataSource(name = "logistics")
    @Override
    public boolean clearPackRecord(String packCode) {
        // 删除 物流-PackList装箱明细
        tbLogisticsPackListDetToBoxMapper.deleteByPackCode(packCode);

        // 删除 物流-PackList明细
        tbLogisticsPackListDetService.deleteByPackCode(packCode);

        // 删除 物流-PackList
        tbLogisticsPackListService.deleteByPackCode(packCode);

        // 删除 物流-Shipment明细
        tbLogisticsShipmentDetService.deleteByPackCode(packCode);

        // 删除 物流-Shipment
        tbLogisticsShipmentService.deleteByPackCode(packCode);

        // 删除 物流-装箱单明细1
        tbLogisticsPackingListDet1Service.deleteByPackCode(packCode);

        // 删除 物流-装箱单明细2
        tbLogisticsPackingListDet2Service.deleteByPackCode(packCode);

        // 删除 物流-装箱单
        LambdaQueryWrapper<TbLogisticsPackingList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbLogisticsPackingList::getPackCode, packCode);
        tbLogisticsPackingListMapper.delete(wrapper);
        return true;
    }



    private static TbLogisticsPackingList createTbLogisticsPackingList(TbLogisticsPackingListK3Param request, String countryCode, String elePlatformName, String shopNameSimple, String PackStoreHouseName, String PackStoreHouseType) {
        TbLogisticsPackingList logistics = new TbLogisticsPackingList();
        logistics.setCountryCode(countryCode);
        logistics.setShopPlatName(elePlatformName);
        logistics.setPackCode(request.getPackCode());
        logistics.setPackDate(DateUtil.parse(request.getPackDate()));
        logistics.setPackPerName(request.getPackPerName());
        logistics.setShopNameSimple(shopNameSimple);
        logistics.setPackUploadState("未绑定");
        logistics.setSysUpdDatetime(new Date());
        logistics.setPackLogState("未发货");
        logistics.setPackShipmentClaimState("待处理");
        logistics.setPackStoreHouseName(PackStoreHouseName);
        logistics.setPackStoreHouseType(PackStoreHouseType);
        logistics.setComWarehouseType("国内仓");
        logistics.setComWarehouseName("国内仓");
        logistics.setPackAbnormalStatus(0);//false
        logistics.setDeliveryPointName(request.getDeliveryPointName());
        logistics.setBillType(request.getBillType());
        return logistics;
    }

    private static TbLogisticsPackingListDet1 createTbLogisticsPackingListDet1(TbLogisticsPackingListK3Param.BoxDetDTO l) {
        TbLogisticsPackingListDet1 logisticsPackingListDet1 = new TbLogisticsPackingListDet1();
        BeanUtil.copyProperties(l, logisticsPackingListDet1);
        logisticsPackingListDet1.setPackDetBoxNumUpload("Box " + l.getPackDetBoxNum() + " - QTY");
        logisticsPackingListDet1.setPackDetBoxVolume(new BigDecimal(l.getPackDetBoxVolume()).divide(new BigDecimal(1000000), 4, BigDecimal.ROUND_HALF_UP));
        logisticsPackingListDet1.setPackDetBoxVoluUnit("m³");
        logisticsPackingListDet1.setPackDetBoxPlanState("未申请");
        logisticsPackingListDet1.setPackDetBoxBillState("未结单");
        logisticsPackingListDet1.setPackDetBoxEndLogNo("");
        logisticsPackingListDet1.setPackDetBoxFirLogNo("");
        logisticsPackingListDet1.setPackDetBoxLogClaimState("待处理");
        logisticsPackingListDet1.setPackDetBoxLogCostNo("");
        logisticsPackingListDet1.setPackDetBoxLogState("未发货");
        logisticsPackingListDet1.setPackDetBoxTaxCostNo("");
        return logisticsPackingListDet1;
    }

    private static ResponseData checkLengthAndWeigUnit(TbLogisticsPackingListK3Param.BoxDetDTO l) {
        if (!l.getPackDetBoxWeigUnit().equals("kg") && !l.getPackDetBoxWeigUnit().equals("KG") && !l.getPackDetBoxWeigUnit().equals("G") && !l.getPackDetBoxWeigUnit().equals("g")) {
            return ResponseData.error(-1, "箱条码->[" + l.getPackDetBoxCode() + "] 箱流水号->[" + l.getPackDetBoxNum() + "] 重量单位异常;JCERP支持单位为KG、G、kg、g.", null);
        }
        // 2.3 箱长、箱宽、箱高、体积、重量   大于0
        if (l.getPackDetBoxLength() <= 0) {
            return ResponseData.error(-1, "箱条码->[" + l.getPackDetBoxCode() + "] 箱流水号->[" + l.getPackDetBoxNum() + "] 箱长应大于0.", null);
        }
        if (l.getPackDetBoxWidth() <= 0) {
            return ResponseData.error(-1, "箱条码->[" + l.getPackDetBoxCode() + "] 箱流水号->[" + l.getPackDetBoxNum() + "] 箱宽应大于0.", null);
        }
        if (l.getPackDetBoxHeight() <= 0) {
            return ResponseData.error(-1, "箱条码->[" + l.getPackDetBoxCode() + "] 箱流水号->[" + l.getPackDetBoxNum() + "] 箱高应大于0.", null);
        }
        if (l.getPackDetBoxVolume() <= 0) {
            return ResponseData.error(-1, "箱条码->[" + l.getPackDetBoxCode() + "] 箱流水号->[" + l.getPackDetBoxNum() + "] 体积应大于0.", null);
        }
        if (l.getPackDetBoxWeight() <= 0) {
            return ResponseData.error(-1, "箱条码->[" + l.getPackDetBoxCode() + "] 箱流水号->[" + l.getPackDetBoxNum() + "] 重量应大于0.", null);
        }
        return ResponseData.success();
    }

    /**
     * 推送数据
     *
     * @param request
     * @param packCodeSet
     * @param model
     * @param logisticsListToEndRouteModel
     * @param logFeeWeight
     * @return
     */
    private ResponseData pushData(TbLogisticsListToHeadRouteParam request, Set<String> packCodeSet, TbLogisticsListToHeadRoute model, TbLogisticsListToEndRoute logisticsListToEndRouteModel, BigDecimal logFeeWeight) {
        // 推送海外仓入库任务
        if ("海外仓".equals(request.getPackStoreHouseType())) {

            ResponseData responseData = this.generateInWarehouseToMCMSV2(request, packCodeSet);
            if (responseData.getSuccess()) {
                log.info("推送海外仓入库任务>>>>>>>>执行成功：{}", responseData);
            } else {
                log.info("推送海外仓入库任务>>>>>>>>执行失败：{}", responseData);
                return responseData;
            }
        }


        // 推送出货清单状态至海外仓出库任务

        if (request.getHeaderRouteDetList().stream().filter(l -> l.getPackCode().contains("CKQD")).count() > 0L) {

            ResponseData responseData = this.pushLogisticsStatusToMCMS(request);
            if (responseData.getSuccess()) {
                log.info("推送物流状态至海外仓出库任务>>>>>>>>执行成功：{}", responseData);
            } else {
                log.info("推送物流状态至海外仓出库任务>>>>>>>>执行失败：{}", responseData);
                return responseData;
            }
        }

        // 推送物流实际结算任务
        ResponseData responseData = this.receiveLogisticsSettlement(request.getHeaderRouteDetList(), model, logisticsListToEndRouteModel, logFeeWeight);

        if (responseData.getSuccess()) {
            log.info("推送物流实际结算任务>>>>>>>>执行成功：{}", responseData);
        } else {
            log.info("推送物流实际结算任务>>>>>>>>执行失败：{}", responseData);
            return responseData;
        }
        return null;
    }

    /**
     * 计算相关费用
     *
     * @param request
     * @param logisticsListToEndRouteModel
     * @param mode
     * @param logFeeWeight
     */
    private void calcFee(TbLogisticsListToHeadRouteParam request, TbLogisticsListToEndRoute logisticsListToEndRouteModel, TbLogisticsNewPriceDetResult mode, BigDecimal logFeeWeight) {
        if ("重量".equals(logisticsListToEndRouteModel.getLerPreChargType())) {
            //实际重量

            //有报价 计算
            if (mode.getBusLogpDetUnitPrice().compareTo(BigDecimal.ZERO) > 0) {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("系统匹配");
            } else {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("");
            }

            logisticsListToEndRouteModel.setLerPreLogBusySeasonAddFee(mode.getBusLogpDetBusySeasonAddFee().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogAddAndSundryFee(mode.getBusLogpDetAddAndSundryFee().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogTaxFee(logisticsListToEndRouteModel.getLerPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogTaxFeeTotal(logisticsListToEndRouteModel.getLerPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogCustDlearanceFee(request.getLogpIsEntry() == 1 ? mode.getBusLogpDetCustDlearanceFee().setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            logisticsListToEndRouteModel.setLerPreLogCustClearanceFee(mode.getBusLogpDetCustClearanceFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogUnitPrice(mode.getBusLogpDetUnitPrice().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogFee(logisticsListToEndRouteModel.getLerPreLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogFuelFee(
                    logisticsListToEndRouteModel.getLerPreLogFee().multiply(mode.getBusLogpDetFuelFee().divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP)));


            BigDecimal lerPreLogFeeTotal = logisticsListToEndRouteModel.getLerPreLogBusySeasonAddFee()
                    .add(logisticsListToEndRouteModel.getLerPreLogAddAndSundryFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogTaxFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogCustDlearanceFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogCustClearanceFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogFuelFee())
                    .setScale(2, RoundingMode.HALF_UP);
            logisticsListToEndRouteModel.setLerPreLogFeeTotal(lerPreLogFeeTotal);
            logisticsListToEndRouteModel.setLerLogComfirmBillCurrency(
                    ObjectUtil.isNotEmpty(request.getLogComfirmBillCurrency()) ? request.getLogComfirmBillCurrency() : mode.getBusLogpChargCurrency());
            logisticsListToEndRouteModel.setLerPreLogFeeTotalNew(lerPreLogFeeTotal);


        } else if ("体积".equals(logisticsListToEndRouteModel.getLerPreChargType())) {
            //体积重量
            if (mode.getBusLogpDetUnitPrice().compareTo(BigDecimal.ZERO) > 0) {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("系统匹配");
            } else {
                logisticsListToEndRouteModel.setLerPreLogUnitPriceType("");
            }

            logisticsListToEndRouteModel.setLerPreLogBusySeasonAddFee(BigDecimal.ZERO);
            logisticsListToEndRouteModel.setLerPreLogAddAndSundryFee(BigDecimal.ZERO);
            logisticsListToEndRouteModel.setLerPreLogTaxFee(logisticsListToEndRouteModel.getLerPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogTaxFeeTotal(logisticsListToEndRouteModel.getLerPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogCustDlearanceFee(request.getLogpIsEntry() == 1 ? mode.getBusLogpDetCustDlearanceFee().setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            logisticsListToEndRouteModel.setLerPreLogCustClearanceFee(mode.getBusLogpDetCustClearanceFee().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogUnitPrice(mode.getBusLogpDetUnitPrice().setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogFee(logisticsListToEndRouteModel.getLerPreLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
            logisticsListToEndRouteModel.setLerPreLogFuelFee(
                    logisticsListToEndRouteModel.getLerPreLogFee().multiply(mode.getBusLogpDetFuelFee().divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP)));


            BigDecimal lerPreLogFeeTotal = logisticsListToEndRouteModel.getLerPreLogBusySeasonAddFee()
                    .add(logisticsListToEndRouteModel.getLerPreLogAddAndSundryFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogTaxFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogCustDlearanceFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogCustClearanceFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogFee())
                    .add(logisticsListToEndRouteModel.getLerPreLogFuelFee())
                    .setScale(2, RoundingMode.HALF_UP);
            logisticsListToEndRouteModel.setLerPreLogFeeTotal(lerPreLogFeeTotal);
            logisticsListToEndRouteModel.setLerLogComfirmBillCurrency(
                    ObjectUtil.isNotEmpty(request.getLogComfirmBillCurrency()) ? request.getLogComfirmBillCurrency() : mode.getBusLogpChargCurrency());
            logisticsListToEndRouteModel.setLerPreLogFeeTotalNew(lerPreLogFeeTotal);

        } else {
            //其他计费类型  走基础计算
            this.baseLogistPriceCalc(request, logisticsListToEndRouteModel, logFeeWeight);
        }
    }

    /**
     * 初始化头程物流价格信息
     *
     * @param request
     * @param model
     * @param logisticsListToEndRouteModel
     * @param logFeeWeight
     */
    private void initHeadRoutePrice(TbLogisticsListToHeadRouteParam request,
                                    TbLogisticsListToHeadRoute model,
                                    TbLogisticsListToEndRoute logisticsListToEndRouteModel,
                                    BigDecimal logFeeWeight) {
        model.setLhrPreLogAddAndSundryFee(logisticsListToEndRouteModel.getLerPreLogAddAndSundryFee());
        model.setLhrPreLogBusySeasonAddFee(logisticsListToEndRouteModel.getLerPreLogBusySeasonAddFee());
        model.setLhrPreLogCustClearanceFee(logisticsListToEndRouteModel.getLerPreLogCustClearanceFee());
        model.setLhrPreLogCustDlearanceFee(request.getLogpIsEntry() == 1 ? logisticsListToEndRouteModel.getLerPreLogCustDlearanceFee() : BigDecimal.ZERO);
        model.setLhrPreLogFuelFee(logisticsListToEndRouteModel.getLerPreLogFuelFee());
        model.setLhrPreLogUnitPrice(logisticsListToEndRouteModel.getLerPreLogUnitPrice());
        model.setLhrPreLogUnitPriceType(logisticsListToEndRouteModel.getLerPreLogUnitPriceType());
        model.setLhrPreLogFee(model.getLhrPreLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
        model.setLhrPreLogTaxFee(request.getLhrLogTaxFee());
        model.setLhrPreLogFeeTotalNew(
                model.getLhrPreLogBusySeasonAddFee()
                        .add(model.getLhrPreLogAddAndSundryFee())
                        .add(model.getLhrPreLogTaxFee())
                        .add(model.getLhrPreLogCustDlearanceFee())
                        .add(model.getLhrPreLogCustClearanceFee()).add(model.getLhrPreLogFee())
                        .add(model.getLhrPreLogFuelFee())
                        .setScale(2, RoundingMode.HALF_UP));

        model.setLhrLogAddAndSundryFee(request.getLhrLogAddAndSundryFee());
        model.setLhrLogBusySeasonAddFee(request.getLhrLogBusySeasonAddFee());
        model.setLhrLogCustClearanceFee(request.getLhrLogCustClearanceFee());
        model.setLhrLogCustDlearanceFee(request.getLogpIsEntry() == 1 ? request.getLhrLogCustDlearanceFee() : BigDecimal.ZERO);
        model.setLhrLogFuelFee(request.getLhrLogFuelFee());
        model.setLhrLogUnitPrice(request.getLhrLogUnitPrice());
        model.setLhrLogFee(model.getLhrLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));
        model.setLhrLogTaxFee(request.getLhrLogTaxFee());
        model.setLhrLogFeeTotalNew(
                model.getLhrLogAddAndSundryFee().add(model.getLhrLogAddAndSundryFee())
                        .add(model.getLhrLogTaxFee())
                        .add(model.getLhrLogCustDlearanceFee())
                        .add(model.getLhrLogCustClearanceFee())
                        .add(model.getLhrLogFee())
                        .add(model.getLhrLogFuelFee())
                        .setScale(2, RoundingMode.HALF_UP));

        model.setLogComfirmBillType(logisticsListToEndRouteModel.getLerPreChargType());
        model.setLogComfirmBillCurrency(logisticsListToEndRouteModel.getLerLogComfirmBillCurrency());
    }

    /**
     * 初始化尾程信息
     *
     * @param request
     * @param model
     * @param loginUser
     * @return
     */
    private TbLogisticsListToEndRoute initTbLogisticsListToEndRoute(TbLogisticsListToHeadRouteParam request, TbLogisticsListToHeadRoute model, LoginUser loginUser) {
        TbLogisticsListToEndRoute logisticsListToEndRouteModel = new TbLogisticsListToEndRoute();

        logisticsListToEndRouteModel.setLhrCode(model.getLhrCode());//发货批次
        logisticsListToEndRouteModel.setLhrOddNumb(model.getLhrOddNumb());//头程物流单号
        logisticsListToEndRouteModel.setLerOddNumb(model.getLhrOddNumb());//尾程物流单号
        logisticsListToEndRouteModel.setSysAddDate(new Date());//创建日期
        logisticsListToEndRouteModel.setSysPerCode(loginUser.getAccount());//员工编号
        logisticsListToEndRouteModel.setSysPerName(loginUser.getName());//员工姓名
        logisticsListToEndRouteModel.setSysUpdDatetime(new Date());//最后更新日期
        logisticsListToEndRouteModel.setLerOutGoodNum(model.getLhrOutGoodNum());//发货件数
        logisticsListToEndRouteModel.setLerOutGoodWeight(model.getLhrOutGoodWeight());//发货重量
        logisticsListToEndRouteModel.setLerOutGoodVolume(model.getLhrOutGoodVolume());//发货体积
        logisticsListToEndRouteModel.setLogTraMode1(model.getLogTraMode1());//货运方式1
        logisticsListToEndRouteModel.setLogEndRouteLink(model.getLogHeadRouteLink());//尾程物流单链接
        logisticsListToEndRouteModel.setLerState("");//尾程物流单状态
        logisticsListToEndRouteModel.setLerPreLogAddAndSundryFeeRemark(model.getLhrPreLogAddAndSundryFeeRemark()); //预估附加费及杂费备注
        //物流预估计算
        // 设置预估税费
        BigDecimal preLogTaxFee = request.getLhrPreLogTaxFee();
        BigDecimal lerPreLogTaxFee = (preLogTaxFee != null && preLogTaxFee.compareTo(BigDecimal.ZERO) > 0) ? preLogTaxFee : BigDecimal.ZERO;
        logisticsListToEndRouteModel.setLerPreLogTaxFee(lerPreLogTaxFee);

        //预估总费用
        BigDecimal preLogFeeTotalManual = request.getLhrPreLogFeeTotalManual();
        BigDecimal lerPreLogFeeTotalManual = (preLogFeeTotalManual != null && preLogFeeTotalManual.compareTo(BigDecimal.ZERO) > 0) ? preLogFeeTotalManual : BigDecimal.ZERO;
        logisticsListToEndRouteModel.setLerPreLogFeeTotalManual(lerPreLogFeeTotalManual);
        logisticsListToEndRouteModel.setLerPreChargType(model.getLogComfirmBillType());
        return logisticsListToEndRouteModel;
    }

    /**
     * 初始化 头程信息---表头
     *
     * @param request
     * @param loginUser
     * @return
     */
    private TbLogisticsListToHeadRoute initTbLogisticsListToHeadRoute(TbLogisticsListToHeadRouteParam request, LoginUser loginUser) {
        // 1.1转换为头程信息---表头
        TbLogisticsListToHeadRoute model = BeanUtil.copyProperties(request, TbLogisticsListToHeadRoute.class);
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 设置日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS");
        // 格式化日期
        String currentDateTime = now.format(formatter);

        model.setLhrCode("FHPC" + currentDateTime);
        model.setSysAddDate(new Date());//创建日期
        model.setSysPerCode(loginUser.getAccount());//员工编号
        model.setSysPerName(loginUser.getName());//员工姓名
        model.setSysUpdDatetime(new Date());//最后更新日期

        model.setLhrState("待发货");
        model.setLhrDataSynState("未同步");

        //需要计算的属性
        model.setLhrOutGoodNum(request.getHeaderRouteDetList().size());//发货件数=count(物流单明细记录)
        model.setLhrOutGoodWeight(request.getHeaderRouteDetList().stream().map(TbLogisticsListToHeadRouteDetParam::getPackDetBoxWeight).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));//发货重量=Sum(物流单明细记录1.计费重量)
        model.setLhrOutGoodVolume(request.getHeaderRouteDetList().stream().map(TbLogisticsListToHeadRouteDetParam::getPackDetBoxVolume).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));//出仓体积=Sum(物流单明细记录1.装箱体积)
        return model;
    }

    /**
     * 创建物流单
     *
     * @param logisticsListToHeadRouteModel
     * @param logisticsListToHeadRouteDetList
     * @param logisticsListToEndRouteModel
     * @param logisticsListToEndRouteDetList
     * @return
     */
    private ResponseData createLogisticsOrder(TbLogisticsListToHeadRoute logisticsListToHeadRouteModel, List<TbLogisticsListToHeadRouteDet> logisticsListToHeadRouteDetList, TbLogisticsListToEndRoute logisticsListToEndRouteModel, List<TbLogisticsListToEndRouteDet> logisticsListToEndRouteDetList) {


        LambdaQueryWrapper<TbLogisticsListToHeadRoute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbLogisticsListToHeadRoute::getLhrOddNumb, logisticsListToHeadRouteModel.getLhrOddNumb());

//        bool havingHeadRoute = _db.Tables.TbLogisticsListToHeadRoutes.Where(x => x.LhrOddNumb == LogisticsListToHeadRouteModel.LhrOddNumb).Count() > 0;
        if (tbLogisticsListToHeadRouteService.count(wrapper) > 0) {
            return ResponseData.error("头程信息已经存在");
        }
        tbLogisticsListToHeadRouteService.save(logisticsListToHeadRouteModel);//创建头程物流单
        tbLogisticsListToHeadRouteDetService.saveBatch(logisticsListToHeadRouteDetList);


        //更新 出货清单明细1-箱子长宽高重-金蝶+海外仓  的状态
        List<String> mergeFieldList3 = new ArrayList<>();//合并字段3 （getPackCode + getPackDetBoxNum + getPackDetBoxCode）
        List<String> mergeFieldList2 = new ArrayList<>();//合并字段2 （getPackCode + getPackDetBoxNum）
        Set<String> packCodeList = new HashSet<>();
        logisticsListToHeadRouteDetList.forEach(headDet -> {
            String mergeField3 = "'" + headDet.getPackCode() + headDet.getPackDetBoxNum() + headDet.getPackDetBoxCode() + "'";
            mergeFieldList3.add(mergeField3);
            String mergeField2 = "'" + headDet.getPackCode() + headDet.getPackDetBoxNum() + "'";
            mergeFieldList2.add(mergeField2);
            packCodeList.add(headDet.getPackCode());
        });

        UpdateWrapper<TbLogisticsPackingListDet1> updateWrapper = new UpdateWrapper<>();
        String mergeFieldList3Str = String.join(",", mergeFieldList3);
        updateWrapper.inSql("pack_code || pack_det_box_num || pack_det_box_code", mergeFieldList3Str);
        updateWrapper.set("pack_det_box_log_state", "待发货");
        updateWrapper.set("pack_det_box_plan_state", "已申请");
        updateWrapper.set("pack_det_box_end_log_no", ObjectUtil.isNotNull(logisticsListToEndRouteModel.getLhrCode()) ? logisticsListToHeadRouteModel.getLhrOddNumb() : "");
        updateWrapper.set("pack_det_box_fir_log_no", logisticsListToHeadRouteModel.getLhrOddNumb());
        updateWrapper.set("log_tra_mode2", logisticsListToHeadRouteModel.getLogTraMode2());
        updateWrapper.set(ObjectUtil.isNotEmpty(logisticsListToHeadRouteModel.getLogTraMode1()), "log_tra_mode1", logisticsListToHeadRouteModel.getLogTraMode1());//todo:取消货运方式1 logTraMode1
        tbLogisticsPackingListDet1Mapper.update(null, updateWrapper);

        //创建头程物流单明细
        if (ObjectUtil.isNotNull(logisticsListToEndRouteModel.getLhrCode())) {   //创建尾程物流单
            tbLogisticsListToEndRouteService.insert(logisticsListToEndRouteModel);
            //创建尾程物流单明细
            tbLogisticsListToEndRouteDetService.saveBatch(logisticsListToEndRouteDetList);
        }

        // 更新发货清单货运方式|物流发货状态  发货汇总表

        UpdateWrapper<TbBscOverseasWay> overseasWayWrapper = new UpdateWrapper<>();
        String mergeFieldList2Str = String.join(",", mergeFieldList2);
        overseasWayWrapper.inSql("pack_code || pack_det_box_num", mergeFieldList2Str);
        overseasWayWrapper.set("deliver_Type", logisticsListToHeadRouteModel.getLogTraMode2());
        overseasWayWrapper.set("deliver_Status", "待发货");
        tbBscOverseasWayService.update(overseasWayWrapper);

        //更新 出货清单明细1-箱子长宽高重-金蝶+海外仓

        UpdateWrapper<TbLogisticsPackingListDet1> updateWrapper2 = new UpdateWrapper<>();

        updateWrapper2.inSql("pack_code || pack_det_box_num", mergeFieldList2Str);
        updateWrapper2.set("pack_det_box_log_state", "待发货");
        updateWrapper2.set("log_tra_mode2", logisticsListToHeadRouteModel.getLogTraMode2());
        updateWrapper2.set(ObjectUtil.isNotEmpty(logisticsListToHeadRouteModel.getLogTraMode1()), "log_tra_mode1", logisticsListToHeadRouteModel.getLogTraMode1());
        tbLogisticsPackingListDet1Mapper.update(null, updateWrapper2);

        //明细还有未发货的，更新出货清单主表状态为 待发货
        LambdaQueryWrapper<TbLogisticsPackingListDet1> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TbLogisticsPackingListDet1::getPackCode, packCodeList);
        queryWrapper.eq(TbLogisticsPackingListDet1::getPackDetBoxLogState, "未发货");


        List<TbLogisticsPackingListDet1> unSendDetList = tbLogisticsPackingListDet1Mapper.selectList(queryWrapper);
        if (!unSendDetList.isEmpty()) {
            //明细还有  未发货  的
            Set<String> haveUnSendPackCodeSet = unSendDetList.stream().map(TbLogisticsPackingListDet1::getPackCode).collect(Collectors.toSet());
            packCodeList.removeAll(haveUnSendPackCodeSet);
        }
        if (ObjectUtil.isNotEmpty(packCodeList)) {
            //剩下的出货清单 没有明细项是 未发货的 更新出货清单 状态
            LambdaUpdateWrapper<TbLogisticsPackingList> packingListUpdateWrapper = new LambdaUpdateWrapper<>();
            packingListUpdateWrapper.in(TbLogisticsPackingList::getPackCode, packCodeList);
            packingListUpdateWrapper.set(TbLogisticsPackingList::getPackLogState, "待发货");
            this.update(packingListUpdateWrapper);
        }

        return ResponseData.success();
    }

    /**
     * 推送物流实际结算任务
     *
     * @param headerRouteDetList
     * @param model
     * @param logisticsListToEndRouteModel
     * @param logFeeWeight 计费重量
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData receiveLogisticsSettlement(List<TbLogisticsListToHeadRouteDetParam> headerRouteDetList, TbLogisticsListToHeadRoute model, TbLogisticsListToEndRoute logisticsListToEndRouteModel, BigDecimal logFeeWeight) {

        LoginUser loginUser = LoginContext.me().getLoginUser();


        List<String> shipments = new ArrayList<>();
        List<String> mergeField = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("(");
        headerRouteDetList.forEach(l ->
        {
            shipments.add(l.getShipmentId());
            mergeField.add(l.getPackCode() + l.getPackDetBoxNum() + l.getPackDetBoxCode());
            stringBuilder.append("'" + l.getPackCode() + l.getPackDetBoxNum() + l.getPackDetBoxCode() + "',");
        });
//        stringBuilder.substring(0, stringBuilder.length() - 1);
//        stringBuilder.append(")");

        QueryWrapper<TbLogisticsPackingListDet2> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("pack_code || pack_det_box_num || pack_det_box_code", stringBuilder.substring(0, stringBuilder.length() - 1));
        List<TbLogisticsPackingListDet2> shipmetDet = tbLogisticsPackingListDet2Mapper.selectList(queryWrapper);


        //封装参数

        EbmsLogisticsSettlementParam param = new EbmsLogisticsSettlementParam();
        param.setShipmentDate(model.getLhrSendGoodDate());
        param.setCurrency(model.getLogComfirmBillCurrency());//todo:是否传递
        param.setShipmentNum(model.getLhrCode());
//        param.setSysShopsName(model.getShopNameSimple());
//        param.setSysSite(model.getCountryCode());
//        param.setLcCode(model.getLcCode());
        param.setCreateTime(new Date());
        param.setCreateUser(loginUser.getAccount() + "_" + loginUser.getName());
        param.setFreightCompany(model.getLogTraMode1());
        param.setIsCustoms(model.getLogpIsEntry() == 1 ? "是" : "否");
        param.setLogisticsChannel(model.getLogSeaTraRoute());
        param.setLogisticsOrder(model.getLhrOddNumb());
        param.setHasTax(model.getLogpIsIncTax() == 1 ? "是" : "否");
        param.setOrderType(model.getLogRedOrBlueList());
        param.setTransportType(model.getLogTraMode2());
        param.setShipmentUnit(Long.valueOf(model.getLhrOutGoodNum()));

        BigDecimal volumeWeight = "快递".equals(model.getLogTraMode2()) ?
                model.getLhrOutGoodVolume().multiply(BigDecimal.valueOf(1000000)).divide(BigDecimal.valueOf(5000), 2, RoundingMode.HALF_UP)
                :
                model.getLhrOutGoodVolume().multiply(BigDecimal.valueOf(1000000)).divide(BigDecimal.valueOf(6000), 2, RoundingMode.HALF_UP);

        param.setVolumeWeight(volumeWeight);
        param.setVolume(model.getLhrOutGoodVolume());
        param.setWeight(model.getLhrOutGoodWeight());
        param.setConfirmCountFee(logFeeWeight);
        param.setConfirmFeeType(model.getLogComfirmBillType());
        param.setShipmentQuantity(Long.valueOf(shipmetDet.stream().map(TbLogisticsPackingListDet2::getQuantity).reduce(0, Integer::sum)));
        param.setShipmentId(String.join(",", shipments));
//        param.setLogisticsCurrency(model.getLogComfirmBillCurrency());
        param.setPredictUnitPrice(logisticsListToEndRouteModel.getLerPreLogUnitPrice());
        param.setPredictBusySeasonFee(logisticsListToEndRouteModel.getLerPreLogBusySeasonAddFee());
//        param.setPredictClearCustomsFee(logisticsListToEndRouteModel.getLerPreLogCustClearanceFee());
        param.setPredictLogisticsFee(logisticsListToEndRouteModel.getLerPreLogFee());
        param.setPredictOilFee(logisticsListToEndRouteModel.getLerPreLogFuelFee());
        param.setPredictOthersFee(logisticsListToEndRouteModel.getLerPreLogAddAndSundryFee());
//        param.setPredictCustomsFee(logisticsListToEndRouteModel.getLerPreLogCustDlearanceFee());
        param.setPredictTaxFee(logisticsListToEndRouteModel.getLerPreLogTaxFee());
        param.setPredictTotalFee(logisticsListToEndRouteModel.getLerPreLogFeeTotalNew());
        param.setUpdateTime(new Date());
        param.setUpdateUser(loginUser.getAccount() + "_" + loginUser.getName());
//        param.setUnitPrice(logisticsListToEndRouteModel.getLerLogUnitPrice());
//        param.setBusySeasonFee(logisticsListToEndRouteModel.getLerLogBusySeasonAddFee());
//        param.setClearCustomsFee(logisticsListToEndRouteModel.getLerLogCustClearanceFee());
//        param.setLogisticsFee(logisticsListToEndRouteModel.getLerLogFee());
//        param.setOilFee(logisticsListToEndRouteModel.getLerLogFuelFee());
//        param.setOthersFee(logisticsListToEndRouteModel.getLerPreLogAddAndSundryFee());
//        param.setCustomsFee(logisticsListToEndRouteModel.getLerLogCustDlearanceFee());
//        param.setTaxFee(logisticsListToEndRouteModel.getLerLogTaxFee());
//        param.setTotalFee(logisticsListToEndRouteModel.getLerLogFeeTotalNew());

        List<EbmsLogisticsSettlementParam> params = new ArrayList<>();


        if (!"CNY".equals(param.getCurrency())) {
            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
            rateParam.setOriginalCurrency(param.getCurrency());
            rateParam.setEffectDate(DateUtil.beginOfDay(param.getShipmentDate()));
            FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
            if (fixedExchangeRate == null) {
                return ResponseData.error("ERP固定汇率不存在！币别：" + param.getCurrency() + "，发货日期：" + param.getShipmentDate());
            }
            param.setDirectRate(fixedExchangeRate.getDirectRate());
        }

        params.add(param);

        return logisticsSettlementService.receiveLogisticsSettlement(params);
    }

    /**
     * 推送物流状态至海外仓出库任务
     */
    private ResponseData pushLogisticsStatusToMCMS(TbLogisticsListToHeadRouteParam request) {

        List<EbmsOverseasOutWarehouseLogisticsParam> list = new ArrayList<>();

        List<TbLogisticsListToHeadRouteDetParam> routeDetList = request.getHeaderRouteDetList().stream().filter(l -> l.getPackCode().contains("CKQD")).collect(Collectors.toList());
        for (TbLogisticsListToHeadRouteDetParam detParam : routeDetList) {
            EbmsOverseasOutWarehouseLogisticsParam para = new EbmsOverseasOutWarehouseLogisticsParam();
            para.setLogisticsCompany(request.getLogTraMode1());
            para.setLogisticsNum(request.getLhrOddNumb());
            para.setPackCode(detParam.getPackCode());
            para.setPackDetBoxCode(detParam.getPackDetBoxCode());
            para.setPackDetBoxNum(BigDecimal.valueOf(detParam.getPackDetBoxNum()));
            list.add(para);
        }
        return overseasOutWarehouseService.receiveLogisticsByEBMS(list);
    }

    /**
     * 生成海外仓入库任务
     *
     * @param request          出货清单号 集合
     * @param packCodeSetParam
     * @return
     */
    private ResponseData generateInWarehouseToMCMSV2(TbLogisticsListToHeadRouteParam request, Set<String> packCodeSetParam) {

        boolean isJp = Objects.equals(request.getPackStoreHouseName(), "日本仓"); // 是否是日本仓 区分
        Set<String> packCodeSet = packCodeSetParam;// 出货清单号 集合

//        var packingLists = _db.Query<TbLogisticsPackingList>($@"SELECT * FROM dbo.TbLogisticsPackingList WHERE {sqlPackWhere}").ToList();
        LambdaQueryWrapper<TbLogisticsPackingList> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(TbLogisticsPackingList::getPackCode, packCodeSet);
        List<TbLogisticsPackingList> packingLists = tbLogisticsPackingListMapper.selectList(wrapper);

//        var packingListDet2s = _db.Query<TbLogisticsPackingListDet2>($@"SELECT * FROM dbo.TbLogisticsPackingListDet2 WHERE {sqlPackDetWhere}").ToList();
        LambdaQueryWrapper<TbLogisticsPackingListDet2> wrapperDet2 = new LambdaQueryWrapper<>();
        wrapperDet2.in(TbLogisticsPackingListDet2::getPackCode, packCodeSet);
        List<TbLogisticsPackingListDet2> packingListDet2s = tbLogisticsPackingListDet2Mapper.selectList(wrapperDet2);
//
//        var packingListDet1s = _db.Query<TbLogisticsPackingListDet1>($@"SELECT * FROM dbo.TbLogisticsPackingListDet1 WHERE {sqlPackDetWhere}").ToList();
        LambdaQueryWrapper<TbLogisticsPackingListDet1> wrapperDet1 = new LambdaQueryWrapper<>();
        wrapperDet1.in(TbLogisticsPackingListDet1::getPackCode, packCodeSet);
        List<TbLogisticsPackingListDet1> packingListDet1s = tbLogisticsPackingListDet1Mapper.selectList(wrapperDet1);

//
//        var materiels = _db.Query<TbComMateriel>($@"SELECT T2.* FROM dbo.TbLogisticsPackingListDet2 T1,TbComMateriel T2 WHERE ({sqlPackDetWhere}) AND T1.mateCode=T2.matCode").ToList();

        List<String> matCodeList = packingListDet2s.stream().map(TbLogisticsPackingListDet2::getMateCode).collect(Collectors.toList());
        List<RpMaterial> materiels = rpMaterialConsumer.getMaterialInfoList(matCodeList);

        List<EbmsOverseasInWarehouseParam> params = new ArrayList<>();

        LoginUser loginUser = LoginContext.me().getLoginUser();

        for (TbLogisticsPackingList l : packingLists) {
            if (isJp) {
                if (!("日本仓".equals(l.getPackStoreHouseName()) && "海外仓".equals(l.getPackStoreHouseType()))) {
                    continue;
                }
            }

            EbmsOverseasInWarehouseParam model = new EbmsOverseasInWarehouseParam();
            model.setInOrder(l.getPackCode());
            model.setSysShopsName(l.getShopNameSimple());
            model.setSysSite(l.getCountryCode());
            model.setPlatform(l.getShopPlatName());
            model.setOperateType(l.getBillType());
            model.setOutWarehouseName(l.getComWarehouseName());
            model.setInWarehouseName(l.getPackStoreHouseName());
            model.setCreateUser(loginUser.getAccount() + "_" + loginUser.getName());

            List<TbLogisticsPackingListDet2> det = packingListDet2s.stream().filter(p -> p.getPackCode().equals(l.getPackCode())).collect(Collectors.toList());

            BigDecimal totalPackageNum = BigDecimal.valueOf(packingListDet1s.stream().filter(d1 -> d1.getPackCode().equals(l.getPackCode())).count());
            model.setTotalPackageNum(totalPackageNum);

            BigDecimal skuNum = BigDecimal.valueOf(det.stream().map(TbLogisticsPackingListDet2::getSku).collect(Collectors.toSet()).size());
            model.setSkuNum(skuNum);

            Integer shouldInventoryQuantity = det.stream().map(TbLogisticsPackingListDet2::getQuantity).reduce(0, Integer::sum);
            model.setShouldInventoryQuantity(BigDecimal.valueOf(shouldInventoryQuantity));

            List<TbLogisticsListToHeadRouteDetParam> sendDet = request.getHeaderRouteDetList().stream()
                    .filter(n -> Objects.equals(n.getPackCode(), l.getPackCode())).collect(Collectors.toList());

            List<EbmsOverseasInWarehouseDetailParam> lstDet = new ArrayList<>();

            for (TbLogisticsPackingListDet2 x : det) {
                RpMaterial mat = materiels.stream().filter(m -> m.getMaterialCode().equals(x.getMateCode())).findFirst().get();
                TbLogisticsPackingListDet1 det1 = packingListDet1s.stream()
                        .filter(n -> n.getPackCode().equals(x.getPackCode()) && n.getPackDetBoxCode().equals(x.getPackDetBoxCode()) && n.getPackDetBoxNum().equals(x.getPackDetBoxNum()))
                        .findFirst().get();
                if ("已申请".equals(det1.getPackDetBoxPlanState())) {
                    //不处理
                } else {
                    EbmsOverseasInWarehouseDetailParam overseas = new EbmsOverseasInWarehouseDetailParam();

                    overseas.setInOrder(l.getPackCode());
                    overseas.setPackageBarCode(x.getPackDetBoxCode());
                    overseas.setPackageNum(BigDecimal.valueOf(x.getPackDetBoxNum()));
                    overseas.setFnSku(x.getFnSku());
                    overseas.setSku(x.getSku());
                    overseas.setQuantity(BigDecimal.valueOf(x.getQuantity()));
                    overseas.setMaterialCode(x.getMateCode());
                    overseas.setDepartment(x.getDepName());
                    overseas.setTeam(x.getTeamName());
                    overseas.setLogisticsCompany(""); //
                    overseas.setSuggestTransType(x.getPackSugShipMethod());
                    overseas.setLogisticsNum("");//
//                            overseas.setCreateUser(loginUser.getAccount() + "_" + loginUser.getName());
                    overseas.setNeedsUser(x.getPerName());
                    overseas.setMaterialName(mat.getProductName());
                    overseas.setPackDetBoxHeight(det1.getPackDetBoxHeight());
                    overseas.setPackDetBoxLength(det1.getPackDetBoxLength());
                    overseas.setPackDetBoxWeight(det1.getPackDetBoxWeight());
                    overseas.setPackDetBoxWidth(det1.getPackDetBoxWidth());
                    overseas.setShipmentDate(request.getLhrSendGoodDate());
                    overseas.setShipStatus("未发货");
                    overseas.setPackDirectCode(x.getPackDirectCode());

                    TbLogisticsListToHeadRouteDetParam sed = sendDet.stream().filter(n -> n.getPackCode().equals(x.getPackCode())
                                    && n.getPackDetBoxCode().equals(x.getPackDetBoxCode())
                                    && n.getPackDetBoxNum().equals(x.getPackDetBoxNum()))
                            .findFirst().get();

                    if (ObjectUtil.isNotNull(sed)) {
                        overseas.setLogisticsCompany(request.getLogTraMode1());
                        overseas.setLogisticsNum(request.getLhrOddNumb());
                        overseas.setShipStatus("发货中");
                        overseas.setLogisticsStatus("已发货");

                    }

                    lstDet.add(overseas);
                }

                model.setDetailList(lstDet);
            }
            params.add(model);

        }
        if (ObjectUtil.isNotEmpty(params)) {
            return overseasInWarehouseService.generateInWarehouseByEBMS(params);
        }
        return ResponseData.success("没有需要推送至海外仓入库任务的出货清单");

    }


    /**
     * 过滤报价明细
     *
     * @param logistPriceList
     * @param logFeeWeight    计费的重量
     * @param lerPreChargType 计费的类型 重量，体积
     * @return
     */
    private TbLogisticsNewPriceDetResult filterPriceDet(List<TbLogisticsNewPriceDetResult> logistPriceList, BigDecimal logFeeWeight, String lerPreChargType) {
        TbLogisticsNewPriceDetResult mode = null;

        if ("重量".equals(lerPreChargType)) {
            mode = logistPriceList.stream()
                    .filter(p -> logFeeWeight.compareTo(p.getBusLogpDetWeightGreater()) >= 0
                            && logFeeWeight.compareTo(p.getBusLogpDetWeightLess()) <= 0
                            && p.getBusLogpDetStatus() == 1)
                    .findFirst().orElseGet(null);

            if (mode == null) {
                //没有正常启用的报价，查找失效的报价
                mode = logistPriceList.stream()
                        .filter(p -> logFeeWeight.compareTo(p.getBusLogpDetWeightGreater()) >= 0
                                && logFeeWeight.compareTo(p.getBusLogpDetWeightLess()) <= 0
                                && p.getBusLogpDetStatus() == -1)
                        .findFirst().orElseGet(null);
            }


        } else if ("".equals(lerPreChargType)) {
            mode = logistPriceList.stream()
                    .filter(p -> logFeeWeight.compareTo(p.getBusLogpDetVolumeGreater()) >= 0
                            && logFeeWeight.compareTo(p.getBusLogpDetVolumeLess()) <= 0
                            && p.getBusLogpDetStatus() == 1)
                    .findFirst().orElseGet(null);

            if (mode == null) {
                //没有正常启用的报价，查找失效的报价
                mode = logistPriceList.stream()
                        .filter(p -> logFeeWeight.compareTo(p.getBusLogpDetVolumeGreater()) >= 0
                                && logFeeWeight.compareTo(p.getBusLogpDetVolumeLess()) <= 0
                                && p.getBusLogpDetStatus() == -1)
                        .findFirst().orElseGet(null);
            }

        }
        return mode;
    }

    /**
     * 没有找到物流价格计算
     *
     * @param request
     * @param logisticsListToEndRouteModel
     * @param logFeeWeight
     */
    private void baseLogistPriceCalc(TbLogisticsListToHeadRouteParam request, TbLogisticsListToEndRoute logisticsListToEndRouteModel, BigDecimal logFeeWeight) {
        logisticsListToEndRouteModel.setLerPreLogFuelFee(BigDecimal.ZERO);
        logisticsListToEndRouteModel.setLerPreLogSurFeeTotal(BigDecimal.ZERO);
        logisticsListToEndRouteModel.setLerPreLogTaxFeeTotal(logisticsListToEndRouteModel.getLerPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
        logisticsListToEndRouteModel.setLerPreLogBusySeasonAddFee(BigDecimal.ZERO);
        logisticsListToEndRouteModel.setLerPreLogAddAndSundryFee(BigDecimal.ZERO);
        logisticsListToEndRouteModel.setLerPreLogCustDlearanceFee(BigDecimal.ZERO);
        logisticsListToEndRouteModel.setLerPreLogCustClearanceFee(BigDecimal.ZERO);
        logisticsListToEndRouteModel.setLerPreLogTaxFee(logisticsListToEndRouteModel.getLerPreLogTaxFee().setScale(2, RoundingMode.HALF_UP));
        logisticsListToEndRouteModel.setLerPreLogFeeTotalManual(logisticsListToEndRouteModel.getLerPreLogFeeTotalManual().setScale(2, RoundingMode.HALF_UP));
        logisticsListToEndRouteModel.setLerLogComfirmBillCurrency(ObjectUtil.isNotEmpty(request.getLogComfirmBillCurrency()) ? request.getLogComfirmBillCurrency() : null);
        logisticsListToEndRouteModel.setLerPreLogUnitPrice(BigDecimal.ZERO);
        logisticsListToEndRouteModel.setLerPreLogFee(logisticsListToEndRouteModel.getLerPreLogUnitPrice().multiply(logFeeWeight).setScale(2, RoundingMode.HALF_UP));

        BigDecimal lerPreLogFeeTotal = logisticsListToEndRouteModel.getLerPreLogBusySeasonAddFee()
                .add(logisticsListToEndRouteModel.getLerPreLogAddAndSundryFee())
                .add(logisticsListToEndRouteModel.getLerPreLogTaxFee())
                .add(logisticsListToEndRouteModel.getLerPreLogCustDlearanceFee())
                .add(logisticsListToEndRouteModel.getLerPreLogCustClearanceFee())
                .add(logisticsListToEndRouteModel.getLerPreLogFee())
                .add(logisticsListToEndRouteModel.getLerPreLogFuelFee())
                .setScale(2, RoundingMode.HALF_UP);

        logisticsListToEndRouteModel.setLerPreLogFeeTotal(lerPreLogFeeTotal);
        logisticsListToEndRouteModel.setLerPreLogFeeTotalNew(lerPreLogFeeTotal);
        logisticsListToEndRouteModel.setLerPreLogUnitPriceType("");
    }


    /**
     * 报价区间是否重复校验
     *
     * @param ranges
     * @param lerPreChargType 实重 或者 体积
     * @return
     */
    public boolean existsIntersectionRange(List<TbLogisticsNewPriceDetResult> ranges, String lerPreChargType) {
        for (int i = 0; i < ranges.size(); i++) {
            for (int j = i + 1; j < ranges.size(); j++) {
                if ("重量".equals(lerPreChargType)) {
                    if (ranges.get(i).getBusLogpDetWeightLess().compareTo(ranges.get(j).getBusLogpDetWeightGreater()) > 0
                            && ranges.get(i).getBusLogpDetWeightGreater().compareTo(ranges.get(j).getBusLogpDetWeightLess()) < 0) {
                        // 如果找到了相交的范围，返回true
                        return true;
                    }
                } else if ("体积".equals(lerPreChargType)) {
                    if (ranges.get(i).getBusLogpDetVolumeLess().compareTo(ranges.get(j).getBusLogpDetVolumeGreater()) > 0
                            && ranges.get(i).getBusLogpDetVolumeGreater().compareTo(ranges.get(j).getBusLogpDetVolumeLess()) < 0) {
                        // 如果找到了相交的范围，返回true
                        return true;
                    }
                }

            }
        }
        // 如果没有找到相交的范围，返回false
        return false;
    }


    @DataSource(name = "logistics")
    @Override
    public ResponseData initClearanceCalc(TgCustomsClearanceCalcParam param) {
        //获取清关税费测算 所勾选的出货清单明细信息 并按sku汇总
        List<String> packCodeList = param.getPackCodeList();
        if (ObjUtil.isEmpty(packCodeList)) {
            return ResponseData.error("勾选的出货清单不能为空");
        }
        List<TbLogisticsPackingListDet2> det2List = tbLogisticsPackingListDet2Service.queryByPackCode(packCodeList);

        if (ObjUtil.isEmpty(det2List)) {
            return ResponseData.error("勾选的出货清单明细为空");
        }

        Map<String, List<TbLogisticsPackingListDet2>> skuMap = det2List.stream().collect(Collectors.groupingBy(TbLogisticsPackingListDet2::getSku));

        List<TgCustomsClearanceCalcDetailParam> calcDetailParamList = new ArrayList<>();

        for (Map.Entry<String, List<TbLogisticsPackingListDet2>> entry : skuMap.entrySet()) {
            String sku = entry.getKey();
            List<TbLogisticsPackingListDet2> listDet2s = entry.getValue();
            Integer countQuantity = listDet2s.stream().map(TbLogisticsPackingListDet2::getQuantity).reduce(Integer::sum).get();
            TbLogisticsPackingListDet2 det2 = listDet2s.get(0);

            TgCustomsClearanceCalcDetailParam calcDetailParam = new TgCustomsClearanceCalcDetailParam();
            calcDetailParam.setSku(sku);
            calcDetailParam.setIsDeal("否");
            calcDetailParam.setIsSelect(true);
            calcDetailParam.setMaterialCode(det2.getMateCode());
            calcDetailParam.setPackCode(det2.getPackCode());
            calcDetailParam.setQuantity(BigDecimal.valueOf(countQuantity));
            calcDetailParam.setCurrency(param.getCurrency());

            calcDetailParamList.add(calcDetailParam);
        }

        param.setClearanceDetails(calcDetailParamList);
        param.setDataType("1");
        return tgCustomsClearanceService.clearanceCalc(param);
    }
}