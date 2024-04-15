package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.SyncToErpConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.*;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasWarehouseManageMapper;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseManageResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OwVolumeReportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.RakutenOverseasShipmentResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ValidateLabelResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import com.tadpole.cloud.supplyChain.modular.logistics.utils.ExcelAnalysisHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 海外仓库存管理服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@Service
@Slf4j
public class OverseasWarehouseManageServiceImpl extends ServiceImpl<OverseasWarehouseManageMapper, OverseasWarehouseManage> implements IOverseasWarehouseManageService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OverseasWarehouseManageMapper mapper;
    @Autowired
    private IOverseasWarehouseManageService service;
    @Autowired
    private IOverseasWarehouseManageRecordService recordService;
    @Autowired
    private IOverseasOutWarehouseService outWarehouseService;
    @Autowired
    private IOverseasOutWarehouseDetailService outWarehouseDetailService;
    @Autowired
    private IOverseasInWarehouseService inWarehouseService;
    @Autowired
    private IOverseasInWarehouseDetailService inWarehouseDetailService;
    @Autowired
    private ISyncTransferToErpService syncTransferToErpService;
    @Autowired
    private IOverseasWarehouseAgeService ageService;
    @Autowired
    private SyncToErpConsumer syncToErpConsumer;
    @Autowired
    private ITgCustomsApplyService applyService;
    @Autowired
    private ITgCustomsClearanceService clearanceService;

    /**
     * 乐天出库保存
     */
    private static final String RAKUTEN_SAVE = "RAKUTEN_SAVE";

    @Value("${logistics.order.env}")
    private String env;

    @Value("${logistics.order.expires-in-value}")
    private Long expiresInValue;

    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData save(List<RakutenOverseasShipmentResult> params) {

        if(CollectionUtil.isEmpty(params)){
          return  ResponseData.error("保存失败，有效数据集合为空！");
        }
        log.info("乐天海外仓出库导入Excel保存入参[{}]", JSONObject.toJSON(params));

        //防止重复入库
        for (RakutenOverseasShipmentResult overseasShipment : params) {
            //1-3. 查询操作记录表是否已上传发货订单
            LambdaQueryWrapper<OverseasWarehouseManageRecord> recordWrapper=new LambdaQueryWrapper<>();
            recordWrapper.eq(OverseasWarehouseManageRecord::getMaterialCode,overseasShipment.getCustomerGoodsCode())
                    .eq(OverseasWarehouseManageRecord::getPlatform,overseasShipment.getPlatform())
                    .eq(OverseasWarehouseManageRecord::getSysShopsName,"TS")//扣除TS的库存，所以操作记录表对应的账号也是TS
                    .eq(OverseasWarehouseManageRecord::getSysSite,overseasShipment.getSite())
                    .likeRight(OverseasWarehouseManageRecord::getOutOrder,overseasShipment.getOrderNo());
            if(recordService.count(recordWrapper)>0){
                overseasShipment.setUploadRemark(overseasShipment.getUploadRemark() == null ? "数据已存在！" : overseasShipment.getUploadRemark() + "，数据已存在！");
            }
        }

        //保存效验库存信息
        List<RakutenOverseasShipmentResult> errorList=params.stream().filter(a -> StrUtil.isNotEmpty(a.getUploadRemark()))
                .collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(errorList)){
            String fileName = this.dealImportRakutenErrorList(params);
            //保存失败，返回异常数据
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "保存失败，存在异常数据", fileName);
        }
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String user = account + "_" + name;

        if(redisTemplate.hasKey(RAKUTEN_SAVE)){
            return ResponseData.error("乐天海外仓出库保存中，请稍后再试!");
        }

        try{
            redisTemplate.boundValueOps(RAKUTEN_SAVE).set("乐天海外仓出库保存中", Duration.ofMinutes(20));

            List<BigDecimal> idList = new ArrayList<>();
            //处理库龄报表出库
            List<OverseasWarehouseAge> ageOutParam = new ArrayList<>();
            for(RakutenOverseasShipmentResult shipment:params){

                //注(产品需求)：乐天平台出货清单的到货账号都是TS
                BigDecimal invQty=mapper.selectStockQty(shipment);

                if(ObjectUtil.isNotNull(invQty) && invQty.compareTo(shipment.getOrderQty()) >= 0){
                    BigDecimal newQty=invQty.subtract(shipment.getOrderQty());

                    //1-1.海外仓商品出货--->>>>>>帐存数量扣减
                    LambdaQueryWrapper<OverseasWarehouseManage> queryWrapper=new LambdaQueryWrapper<>();
                    queryWrapper.eq(OverseasWarehouseManage::getPlatform,shipment.getPlatform())
                            //.eq(OverseasWarehouseManage::getSysShopsName,shipment.getShopName())
                            //注(产品需求)：乐天平台出货清单的到货账号都是TS，扣减库存也是扣减TS账号的库存
                            .eq(OverseasWarehouseManage::getSysShopsName,"TS")
                            .eq(OverseasWarehouseManage::getSysSite,shipment.getSite())
                            .eq(OverseasWarehouseManage::getMaterialCode,shipment.getCustomerGoodsCode())
                            .ne(OverseasWarehouseManage::getInventoryQuantity,0);

                    OverseasWarehouseManage ent=this.getOne(queryWrapper);

                    if(ObjectUtil.isNotEmpty(ent)){
                        ent.setInventoryQuantity(newQty);
                        this.updateById(ent);

                        //查询sku
                        String getSku=mapper.selectSku(shipment.getSite(), shipment.getCustomerGoodsCode());
                        //1-1-2.乐天海外仓出库--->>>>>>新增操作记录
                        OverseasWarehouseManageRecord record=new OverseasWarehouseManageRecord();
                        record.setParentId(ent.getId());
                        record.setPlatform(shipment.getPlatform());
                        record.setSysShopsName("TS");//扣除TS的库存，所以操作记录表对应的账号也是TS
                        record.setSysSite(shipment.getSite());
                        record.setMaterialCode(shipment.getCustomerGoodsCode());
                        record.setInventoryQuantity(invQty);
                        record.setChangeInventoryQuantity(shipment.getOrderQty());
                        record.setNowInventoryQuantity(newQty);
                        record.setOutOrder(shipment.getOrderNo());
                        record.setOperateType(OperateTypeEnum.RAKUTEN.getName());
                        record.setOperateTypeDetail(OutBusinessTypeEnum.RAKUTEN.getName());
                        record.setBusinessType(OverseasBusinessTypeEnum.OUT.getName()); //出库
                        record.setDepartment("事业五部");
                        record.setWarehouseName("日本仓");
                        if(StrUtil.isNotEmpty(getSku)){
                            record.setSku(getSku);
                        }
                        record.setFnSku(shipment.getCustomerGoodsCode());
                        record.setTeam(shipment.getTeam());
                        record.setInOrg(shipment.getInOrg());
                        record.setOutOrg(shipment.getOutOrg());
                        record.setCreateUser(user);
                        record.setNeedsUser(user);
                        record.setCreateTime(DateUtil.date());
                        record.setComeQuantity(ent.getComeQuantity());
                        record.setChangeComeQuantity(BigDecimal.ZERO);
                        record.setNowComeQuantity(ent.getComeQuantity());
                        record.setIsChangeOrg("0");
                        record.setIsChangeMaterialCode("0");
                        record.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                        record.setRemark("实际出货组织名称：" + shipment.getPlatform()  + "_" + shipment.getShopName() + "_" + shipment.getSite());
                        recordService.save(record);
                        idList.add(record.getId());

                        //处理库龄报表出库
                        OverseasWarehouseAge ageOut = new OverseasWarehouseAge();
                        ageOut.setPlatform(record.getPlatform());
                        ageOut.setSysShopsName(record.getSysShopsName());
                        ageOut.setSysSite(record.getSysSite());
                        ageOut.setWarehouseName(record.getWarehouseName());
                        ageOut.setFnSku(record.getFnSku());
                        ageOut.setSku(record.getSku());
                        ageOut.setMaterialCode(record.getMaterialCode());
                        ageOut.setInventoryQuantity(record.getChangeInventoryQuantity());
//                        ageOut.setRemark(record.getOutOrder() + ":" + record.getChangeInventoryQuantity());
                        ageOutParam.add(ageOut);

                        //1-1-3.乐天海外仓出库--->>>>>>新增出库管理任务
                        LambdaQueryWrapper<OverseasOutWarehouse> qw = new LambdaQueryWrapper<>();
                        qw.eq(OverseasOutWarehouse::getOutOrder,shipment.getOrderNo());
                        OverseasOutWarehouse outWarehouseCheck = outWarehouseService.getOne(qw);

                        //乐天订单编号存在，则不新加记录
                        if (ObjectUtil.isEmpty(outWarehouseCheck)){
                            OverseasOutWarehouse outWarehouse=new OverseasOutWarehouse();
                            outWarehouse.setParentId(record.getId());
                            outWarehouse.setOutOrder(shipment.getOrderNo());
                            outWarehouse.setPlatform(shipment.getPlatform());
                            outWarehouse.setSysShopsName(shipment.getShopName());
                            outWarehouse.setSysSite(shipment.getSite());
                            outWarehouse.setOperateType(OperateTypeEnum.RAKUTEN.getName());
                            outWarehouse.setShouldOutQuantity(shipment.getOrderQty());
                            outWarehouse.setSkuNum(BigDecimal.ONE);
                            outWarehouse.setOutWarehouseName("日本仓");
                            outWarehouse.setLogisticsUser(user);
                            outWarehouse.setCreateUser(user);
                            outWarehouseService.save(outWarehouse);
                        }else{
                            List<String> skuTypeList = mapper.typeList(shipment.getOrderNo());
                            //相同订单编号，sku种类存在多条，需要累加数量
                            LambdaUpdateWrapper<OverseasOutWarehouse> updateWrapper = new LambdaUpdateWrapper<>();
                            updateWrapper.eq(OverseasOutWarehouse::getOutOrder,shipment.getOrderNo())
                                    .set(OverseasOutWarehouse::getShouldOutQuantity,outWarehouseCheck.getShouldOutQuantity().add(shipment.getOrderQty()));
                            if(!skuTypeList.contains(getSku)){
                                updateWrapper.set(OverseasOutWarehouse::getSkuNum,outWarehouseCheck.getSkuNum().add(BigDecimal.ONE));
                            }
                            outWarehouseService.update(updateWrapper);
                        }

                        //1-1-4.出库任务明细
                        OverseasOutWarehouseDetail outWarehouseDetail=new OverseasOutWarehouseDetail();
                        outWarehouseDetail.setOutOrder(shipment.getOrderNo());

                        if(StrUtil.isNotEmpty(getSku)){
                            outWarehouseDetail.setSku(getSku);
                        }
                        outWarehouseDetail.setQuantity(shipment.getOrderQty());
                        outWarehouseDetail.setFnSku(shipment.getCustomerGoodsCode());
                        outWarehouseDetail.setMaterialCode(shipment.getCustomerGoodsCode());
                        outWarehouseDetail.setNeedsUser(user);
                        outWarehouseDetail.setDepartment("事业五部");
                        outWarehouseDetail.setTeam(shipment.getTeam());
                        outWarehouseDetail.setShipmentDate(shipment.getDeliveryTime());
                        outWarehouseDetailService.save(outWarehouseDetail);

                    }
                }
            }

            //处理库龄
            ageService.batchAgeOut(ageOutParam);

            //2.1、乐天海外仓出库->调用ERP销售出库
            String errorMsg = syncOutWarehouseToErp(idList);
            if(errorMsg != null){
                return ResponseData.error("乐天海外仓出库单同步k3销售出库单存在异常！"+errorMsg);
            }
            return ResponseData.success("保存成功！");
        }catch (Exception e){
            log.error("RakutenOverseasShipment>>>save保存，异常信息[{}]",e.getMessage());
            return ResponseData.error("保存异常，请联系管理员！");
        }finally {
            redisTemplate.delete(RAKUTEN_SAVE);
        }
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData importExcel(MultipartFile file) {
        BufferedInputStream buffer = null;
        try {
            if(!file.getOriginalFilename().endsWith(".xlsx")){
                return ResponseData.error("导入失败，请导入xlsx文件！");
            }
            buffer = new BufferedInputStream(file.getInputStream());
            List<RakutenOverseasShipmentResult> dataList=new ExcelAnalysisHelper().getList(file,RakutenOverseasShipmentResult.class);

            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            log.info("乐天海外仓出库导入Excel入参[{}]", JSONObject.toJSON(dataList));

            //数据集合
            List<RakutenOverseasShipmentResult> errorList = new ArrayList<>();
            this.validation(dataList,errorList);

            if(CollectionUtil.isNotEmpty(errorList)){
                //导入校验异常，返回全部数据及异常提示
                String fileName = this.dealImportRakutenErrorList(dataList);
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, fileName, dataList);
            }else{
                //返回上传出货数据集合
                if(CollectionUtil.isNotEmpty(dataList)){
                    return ResponseData.success(dataList);
                }
            }

            return ResponseData.success("导入失败！导入数据为空！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    private void validation(List<RakutenOverseasShipmentResult> dataList,List<RakutenOverseasShipmentResult> errorList) {

        //1.重复校验->导入订单编号+物料编码
        Set<String> allSet=new HashSet<>();

        for(RakutenOverseasShipmentResult overseasShipment:dataList){
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();

            if(overseasShipment.getOrderNo()==null
                || overseasShipment.getCustomerGoodsCode()==null
                || overseasShipment.getShipmentsCompany()==null
                || overseasShipment.getOrderQty()==null){
                //不为空校验
                validInfo.append("订单编号、客户商品编码、发件公司、商品数量不为空！");
            }else{
                String sb=overseasShipment.getCustomerGoodsCode()+overseasShipment.getOrderNo();
                //集合中存在添加重复集合
                if(allSet.contains(sb)){
                    validInfo.append("订单编号+物料编码存在重复！");
                    overseasShipment.setUploadRemark(validInfo.toString());
                    errorList.add(overseasShipment);
                    continue;
                }
                allSet.add(sb);

                if(!"THTECH".equals(overseasShipment.getShipmentsCompany()) && !"TIMOVO".equals(overseasShipment.getShipmentsCompany())){
                    validInfo.append("发件公司错误！");
                    overseasShipment.setUploadRemark(validInfo.toString());
                    errorList.add(overseasShipment);
                    continue;
                }

                //1.乐天海外仓出库导入效验
                if(StrUtil.isNotEmpty(overseasShipment.getShipmentsCompany())){
                    switch (overseasShipment.getShipmentsCompany()){
                        case "THTECH":
                            overseasShipment.setPlatform("Rakuten");
                            overseasShipment.setShopName("TS");
                            overseasShipment.setSite("JP");
                            break;
                        case "TIMOVO":
                            overseasShipment.setPlatform("Rakuten");
                            overseasShipment.setShopName("TM");
                            overseasShipment.setSite("JP");
                            break;
                    }
                }

                //1-1. 乐天出货上传商品->数量汇总
                BigDecimal sumQty = dataList.stream().filter(shipment->
                        shipment.getCustomerGoodsCode().equals(overseasShipment.getCustomerGoodsCode()))
                        .map(RakutenOverseasShipmentResult::getOrderQty).reduce(BigDecimal::add).get();

                //1-1. 海外仓库存-》》客户商品编号+站点
                LambdaQueryWrapper<OverseasWarehouseManage> queryWrapper=new LambdaQueryWrapper();
                queryWrapper.eq(OverseasWarehouseManage::getMaterialCode,overseasShipment.getCustomerGoodsCode())
                        .eq(OverseasWarehouseManage::getPlatform,overseasShipment.getPlatform())
                        //.eq(OverseasWarehouseManage::getSysShopsName,overseasShipment.getShopName())
                        //注(产品需求)：乐天平台出货清单的到货账号都是TS，扣减库存也是扣减TS账号的库存
                        .eq(OverseasWarehouseManage::getSysShopsName,"TS")
                        .eq(OverseasWarehouseManage::getSysSite,overseasShipment.getSite());
                List<OverseasWarehouseManage> warehouseManageList = service.list(queryWrapper);
                if(CollectionUtil.isEmpty(warehouseManageList)){
                    overseasShipment.setInvStatus("无库存");
                    validInfo.append("无库存");
                }else{
                    OverseasWarehouseManage warehouseManage;
                    if(warehouseManageList.size() == 1){
                        warehouseManage = warehouseManageList.get(0);
                    }else{
                        //存在多条的只取有账存的
                        queryWrapper.ne(OverseasWarehouseManage::getInventoryQuantity,0);
                        warehouseManage = service.getOne(queryWrapper);
                    }
                    if(ObjectUtil.isNotEmpty(warehouseManage)){
                        BigDecimal invQty=warehouseManage.getInventoryQuantity();
                        if(ObjectUtil.isNotNull(sumQty) && sumQty.compareTo(invQty)==1){
                            overseasShipment.setInvStatus("库存不足");
                            validInfo.append("库存不足");
                        }else{
                            overseasShipment.setInvStatus("有库存");
                        }
                        if(BigDecimal.ZERO.equals(invQty)){
                            overseasShipment.setInvStatus("无库存");
                            validInfo.append("无库存");
                        }
                        overseasShipment.setInventory(invQty);
                    }else{
                        overseasShipment.setInvStatus("无库存");
                        validInfo.append("无库存");
                    }
                }

                //1-3. 查询操作记录表是否已上传发货订单
                LambdaQueryWrapper<OverseasWarehouseManageRecord> recordWrapper=new LambdaQueryWrapper<>();
                recordWrapper.eq(OverseasWarehouseManageRecord::getMaterialCode,overseasShipment.getCustomerGoodsCode())
                        .eq(OverseasWarehouseManageRecord::getPlatform,overseasShipment.getPlatform())
                        .eq(OverseasWarehouseManageRecord::getSysShopsName,"TS")//扣除TS的库存，所以操作记录表对应的账号也是TS
                        .eq(OverseasWarehouseManageRecord::getSysSite,overseasShipment.getSite())
                        .likeRight(OverseasWarehouseManageRecord::getOutOrder,overseasShipment.getOrderNo());
                if(recordService.count(recordWrapper)>0){
                    overseasShipment.setIsUpload("是");
                    validInfo.append("数据已存在！");
                }else{
                    overseasShipment.setIsUpload("否");
                }
            }

            if(StringUtils.isNotEmpty(validInfo)){
                overseasShipment.setUploadRemark(validInfo.toString());
                errorList.add(overseasShipment);
            }
        }
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(OverseasWarehouseManageParam param) {
        return ResponseData.success(mapper.queryPage(PageFactory.defaultPage(), param));
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData queryPageTotal(OverseasWarehouseManageParam param) {
        return ResponseData.success(mapper.queryPageTotal(param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasWarehouseManageResult> export(OverseasWarehouseManageParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData exchange(OverseasWarehouseChangeParam param, OverseasWarehouseManage overseasWarehouseManage) {
        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        //换标账存数量
        BigDecimal changeQuantity = param.getInventoryQuantity();
        //换标前老标账存数量
        BigDecimal oldQuantity = overseasWarehouseManage.getInventoryQuantity();
        //换标后老标账存数量
        BigDecimal afterOldQuantity = oldQuantity.subtract(changeQuantity);

        //1、根据维度查询新换标海外仓账存信息
        QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("PLATFORM", param.getPlatform())
                .eq("SYS_SHOPS_NAME", param.getSysShopsName())
                .eq("SYS_SITE", param.getSysSite())
                .eq("WAREHOUSE_NAME", param.getWarehouseName())
                .eq("FN_SKU", param.getFnSku())
                .eq("SKU", param.getSku())
                .eq("MATERIAL_CODE", param.getMaterialCode());
        OverseasWarehouseManage changeManage = service.getOne(queryWrapper);
        if(changeManage == null){
            //不存在新换标库存信息，默认新增一个库存信息，并初始化为0
            OverseasWarehouseManage newManage = new OverseasWarehouseManage();
            newManage.setPlatform(param.getPlatform());
            newManage.setSysShopsName(param.getSysShopsName());
            newManage.setSysSite(param.getSysSite());
            newManage.setWarehouseName(param.getWarehouseName());
            newManage.setFnSku(param.getFnSku());
            newManage.setSku(param.getSku());
            newManage.setMaterialCode(param.getMaterialCode());
            newManage.setComeQuantity(BigDecimal.ZERO);
            newManage.setInventoryQuantity(BigDecimal.ZERO);
            newManage.setCreateUser(accountAndName);
            newManage.setCreateTime(new Date());
            newManage.setDataSource(InitDataSourceEnum.CHANGE.getCode());
            service.save(newManage);
            changeManage = newManage;
        }
        //换标前新标账存数量
        BigDecimal newQuantity = changeManage.getInventoryQuantity();
        changeManage.setInventoryQuantity(changeManage.getInventoryQuantity().add(changeQuantity));

        //2、更新原标账存
        OverseasWarehouseManage updateInventory = new OverseasWarehouseManage();
        updateInventory.setId(overseasWarehouseManage.getId());
        updateInventory.setInventoryQuantity(afterOldQuantity);//换标后账存数量
        updateInventory.setUpdateTime(new Date());
        updateInventory.setUpdateUser(accountAndName);
        this.baseMapper.updateById(updateInventory);

        //更新新换标仓账存
        service.updateById(changeManage);

        //2、同时生成出库和入库操作记录
        List<OverseasWarehouseManageRecord> insertRecordList = new ArrayList<>();
        String isChangeOrg = param.getIsChangeOrg() == true ? "1" : "0";
        String isChangeMaterialCode = param.getIsChangeMaterialCode() == true ? "1" : "0";

        //生成1条出库记录
        OverseasWarehouseManageRecord outRecord = new OverseasWarehouseManageRecord();
        BeanUtils.copyProperties(overseasWarehouseManage, outRecord);
        outRecord.setId(null);
        outRecord.setParentId(overseasWarehouseManage.getId());
        outRecord.setOperateType(OperateTypeEnum.CHANGE.getName());
        outRecord.setCreateTime(new Date());
        outRecord.setCreateUser(accountAndName);
        outRecord.setUpdateTime(null);
        outRecord.setUpdateUser(null);
        outRecord.setChangeComeQuantity(BigDecimal.ZERO);
        outRecord.setNowComeQuantity(overseasWarehouseManage.getComeQuantity());
        outRecord.setChangeInventoryQuantity(changeQuantity);
        outRecord.setNowInventoryQuantity(afterOldQuantity);
        outRecord.setDepartment(param.getDepartment());
        outRecord.setTeam(param.getTeam());
        outRecord.setNeedsUser(param.getNeedsUser());
        outRecord.setInOrg(param.getInOrg());
        outRecord.setInOrgName(param.getInOrgName());
        outRecord.setOutOrg(param.getOutOrg());
        outRecord.setOutOrgName(param.getOutOrgName());
        outRecord.setBusinessType(OverseasBusinessTypeEnum.OUT.getName());
        outRecord.setIsChangeOrg(isChangeOrg);
        outRecord.setIsChangeMaterialCode(isChangeMaterialCode);
        outRecord.setInWarehouseName(param.getWarehouseName());//为跨组织同物料直接调拨单使用
        outRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
        if(!param.getIsChangeMaterialCode() && !param.getIsChangeOrg()){
            outRecord.setSyncErpStatus(BaseSyncStatusEnum.NOT.getCode());
        }

        //生成1条入库记录
        OverseasWarehouseManageRecord inRecord = new OverseasWarehouseManageRecord();
        BeanUtils.copyProperties(param, inRecord);
        inRecord.setId(null);
        inRecord.setParentId(changeManage.getId());//换标后新入库id
        inRecord.setOperateType(OperateTypeEnum.CHANGE.getName());
        inRecord.setCreateTime(new Date());
        inRecord.setCreateUser(accountAndName);
        inRecord.setMaterialCode(changeManage.getMaterialCode());
        inRecord.setComeQuantity(changeManage.getComeQuantity());
        inRecord.setChangeComeQuantity(BigDecimal.ZERO);
        inRecord.setNowComeQuantity(changeManage.getComeQuantity());
        inRecord.setInventoryQuantity(newQuantity);
        inRecord.setChangeInventoryQuantity(changeQuantity);
        inRecord.setNowInventoryQuantity(changeManage.getInventoryQuantity());
        inRecord.setInOrg(param.getInOrg());
        inRecord.setInOrgName(param.getInOrgName());
        inRecord.setOutOrg(param.getOutOrg());
        inRecord.setOutOrgName(param.getOutOrgName());
        inRecord.setBusinessType(OverseasBusinessTypeEnum.IN.getName());
        inRecord.setIsChangeOrg(isChangeOrg);
        inRecord.setIsChangeMaterialCode(isChangeMaterialCode);
        inRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
        if(!param.getIsChangeMaterialCode()){
            //同物料编码且不同组织，调用K3直接调拨单（跨组织调拨），用的是换标出库记录，换标入库记录不用同步K3；同物料编码且同组织，不与K3做交互
            inRecord.setSyncErpStatus(BaseSyncStatusEnum.NOT.getCode());
        }

        //判断是否跨站点换标
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        if(param.getIsChangeSite()){
            //从redis取跨站点换标单号
            String order = this.getLogisticsOrder(env + "_" + OrderTypePreEnum.KZHB.getCode() + pureDate, OrderTypePreEnum.KZHB, 5);
            outRecord.setOperateTypeDetail(OutBusinessTypeEnum.DIFF_SITE_OUT_CHANGE.getName());
            outRecord.setOutOrder(order);
            inRecord.setOperateTypeDetail(InBusinessTypeEnum.DIFF_SITE_IN_CHANGE.getName());
            inRecord.setInOrder(order);
        } else {
            //从redis取同站点换标单号
            String order = this.getLogisticsOrder(env + "_"  + OrderTypePreEnum.TZHB.getCode() + pureDate, OrderTypePreEnum.TZHB, 5);
            outRecord.setOperateTypeDetail(OutBusinessTypeEnum.SAME_SITE_OUT_CHANGE.getName());
            outRecord.setOutOrder(order);
            inRecord.setOperateTypeDetail(InBusinessTypeEnum.SAME_SITE_IN_CHANGE.getName());
            inRecord.setInOrder(order);
        }
        insertRecordList.add(outRecord);
        insertRecordList.add(inRecord);
        recordService.saveBatch(insertRecordList);

        //3、同时生成海外仓出库管理记录和海外仓入库管理记录
        //插入海外仓出库管理主表
        OverseasOutWarehouse out = new OverseasOutWarehouse();
        BeanUtils.copyProperties(outRecord, out);
        out.setParentId(outRecord.getId());
        out.setOperateType(outRecord.getOperateTypeDetail());
        out.setShouldOutQuantity(outRecord.getChangeInventoryQuantity());
        out.setSkuNum(BigDecimal.ONE);
        out.setInWarehouseName(changeManage.getWarehouseName());
        out.setOutWarehouseName(outRecord.getWarehouseName());
        out.setUpdateTime(null);
        out.setUpdateUser(null);
        outWarehouseService.save(out);

        //插入海外仓出库管理明细表
        OverseasOutWarehouseDetail outDetail = new OverseasOutWarehouseDetail();
        outDetail.setOutOrder(out.getOutOrder());
        outDetail.setSku(outRecord.getSku());
        outDetail.setFnSku(outRecord.getFnSku());
        outDetail.setMaterialCode(outRecord.getMaterialCode());
        outDetail.setCreateTime(outRecord.getCreateTime());
        outDetail.setUpdateUser(outRecord.getCreateUser());
        outDetail.setDepartment(outRecord.getDepartment());
        outDetail.setTeam(outRecord.getTeam());
        outDetail.setNeedsUser(outRecord.getNeedsUser());
        outDetail.setQuantity(outRecord.getChangeInventoryQuantity());
        outWarehouseDetailService.save(outDetail);

        //插入海外仓入库管理主表
        OverseasInWarehouse in = new OverseasInWarehouse();
        BeanUtils.copyProperties(inRecord, in);
        in.setParentId(inRecord.getId());
        in.setOperateType(inRecord.getOperateTypeDetail());
        in.setShouldInventoryQuantity(inRecord.getChangeInventoryQuantity());
        in.setAlreadyInventoryQuantity(inRecord.getChangeInventoryQuantity());
        in.setNotInventoryQuantity(BigDecimal.ZERO);
        in.setSkuNum(BigDecimal.ONE);
        in.setOutWarehouseName(overseasWarehouseManage.getWarehouseName());
        in.setInWarehouseName(inRecord.getWarehouseName());
        in.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
        in.setConfirmStartTime(inRecord.getCreateTime());
        in.setConfirmEndTime(inRecord.getCreateTime());
        in.setConfirmUser(inRecord.getCreateUser());
        in.setUpdateTime(null);
        in.setUpdateUser(null);
        inWarehouseService.save(in);

        //插入海外仓入库管理明细表
        OverseasInWarehouseDetail inDetail = new OverseasInWarehouseDetail();
        inDetail.setInOrder(in.getInOrder());
        inDetail.setSku(inRecord.getSku());
        inDetail.setFnSku(inRecord.getFnSku());
        inDetail.setQuantity(inRecord.getChangeInventoryQuantity());
        inDetail.setMaterialCode(inRecord.getMaterialCode());
        inDetail.setActualQuantity(inRecord.getChangeInventoryQuantity());
        inDetail.setConfirmStatus(in.getConfirmStatus());
        inDetail.setConfirmTime(inRecord.getCreateTime());
        inDetail.setConfirmUser(inRecord.getCreateUser());
        inDetail.setCreateTime(inRecord.getCreateTime());
        inDetail.setCreateUser(inRecord.getCreateUser());
        inDetail.setDepartment(inRecord.getDepartment());
        inDetail.setTeam(inRecord.getTeam());
        inDetail.setNeedsUser(inRecord.getNeedsUser());
        inWarehouseDetailService.save(inDetail);

        //4、调用ERP接口
        if(param.getIsChangeMaterialCode()){
            //不同物料编码，调用K3其他入库单、其他出库单接口
            //K3其他出库
            ResponseData outResponseData = syncChangeOutStockToErp(outRecord.getId());

            //K3其他入库
            ResponseData inResponseData = syncChangeInStockToErp(inRecord.getId());

            if(ResponseData.DEFAULT_ERROR_CODE.equals(outResponseData.getCode()) || ResponseData.DEFAULT_ERROR_CODE.equals(inResponseData.getCode())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("换标同步ERP其他出库单：" + outResponseData.getMessage() + "换标同步ERP其他入库单：" + inResponseData.getMessage());
            }
        }else{
            //同物料编码且不同组织，调用K3直接调拨单（跨组织调拨）；同物料编码且同组织，不与K3做交互
            if(param.getIsChangeOrg()){
                ResponseData transferResponseData = syncTransferToErpService.save(null, outRecord.getId(), TransferBusinessTypeEnum.DIFF_SITE_CHANGE);
                if(ResponseData.DEFAULT_ERROR_CODE.equals(transferResponseData.getCode())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("换标同步ERP直接调拨单失败，详情：" + transferResponseData.getMessage());
                }
            }
        }

        //处理库龄报表入库、出库
        //入库
        List<OverseasWarehouseAge> ageInParam = new ArrayList<>();
        OverseasWarehouseAge ageIn = new OverseasWarehouseAge();
        ageIn.setPlatform(inRecord.getPlatform());
        ageIn.setSysShopsName(inRecord.getSysShopsName());
        ageIn.setSysSite(inRecord.getSysSite());
        ageIn.setWarehouseName(inRecord.getWarehouseName());
        ageIn.setFnSku(inRecord.getFnSku());
        ageIn.setSku(inRecord.getSku());
        ageIn.setMaterialCode(inRecord.getMaterialCode());
        ageIn.setSignQuantity(inRecord.getChangeInventoryQuantity());
        ageIn.setInventoryQuantity(inRecord.getChangeInventoryQuantity());
        ageIn.setSignDate(inRecord.getCreateTime());
        ageIn.setCreateTime(inRecord.getCreateTime());
        ageIn.setCreateUser(inRecord.getCreateUser());
        ageInParam.add(ageIn);
        ageService.batchAgeIn(ageInParam);

        //出库
        List<OverseasWarehouseAge> ageOutParam = new ArrayList<>();
        OverseasWarehouseAge ageOut = new OverseasWarehouseAge();
        ageOut.setPlatform(outRecord.getPlatform());
        ageOut.setSysShopsName(outRecord.getSysShopsName());
        ageOut.setSysSite(outRecord.getSysSite());
        ageOut.setWarehouseName(outRecord.getWarehouseName());
        ageOut.setFnSku(outRecord.getFnSku());
        ageOut.setSku(outRecord.getSku());
        ageOut.setMaterialCode(outRecord.getMaterialCode());
        ageOut.setInventoryQuantity(outRecord.getChangeInventoryQuantity());
//        ageOut.setRemark(outRecord.getOutOrder() + ":" + outRecord.getChangeInventoryQuantity());
        ageOutParam.add(ageOut);
        ageService.batchAgeOut(ageOutParam);
        return ResponseData.success();
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData upload(MultipartFile file, List<String> platformList, List<String> shopList, List<String> siteList, List<String> departmentList, List<String> teamList, List<Map<String, Object>> userList) {
        log.info("批量换标导入处理开始");
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OverseasWarehouseManageExchangeParam>();
            EasyExcel.read(buffer, OverseasWarehouseManageExchangeParam.class, listener).sheet()
                    .doRead();

            List<OverseasWarehouseManageExchangeParam> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            return ResponseData.success(this.validExchange(dataList, platformList, shopList, siteList, departmentList, teamList, userList));
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入换标数据校验处理
     * @param dataList
     * @param platformList
     * @param shopList
     * @param siteList
     * @param departmentList
     * @param teamList
     * @param userList
     * @return
     */
    private List<OverseasWarehouseManageExchangeParam> validExchange(List<OverseasWarehouseManageExchangeParam> dataList,
                                                                     List<String> platformList,
                                                                     List<String> shopList,
                                                                     List<String> siteList,
                                                                     List<String> departmentList,
                                                                     List<String> teamList,
                                                                     List<Map<String, Object>> userList) {

        List<String> userNameList = userList.stream().map(i -> (String)i.get("name")).collect(Collectors.toList());
        Iterator<OverseasWarehouseManageExchangeParam> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            OverseasWarehouseManageExchangeParam param = iterator.next();
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if(param.getOldPlatform() == null
                    || param.getOldSysShopsName() == null
                    || param.getOldSysSite() == null
                    || param.getOldWarehouseName() == null
                    || param.getOldFnSku() == null
                    || param.getOldSku() == null
                    || param.getOldMaterialCode() == null
                    || param.getInventoryQuantity() == null
                    || param.getPlatform() == null
                    || param.getSysShopsName() == null
                    || param.getSysSite() == null
                    || param.getWarehouseName() == null
                    || param.getFnSku() == null
                    || param.getSku() == null
                    || param.getDepartment() == null
                    || param.getTeam() == null
                    || param.getNeedsUser() == null
            ){
                //不为空校验
                validInfo.append("所有列为必填项");
            } else {
                //去空格
                param.setOldPlatform(param.getOldPlatform().trim());
                param.setOldSysShopsName(param.getOldSysShopsName().trim());
                param.setOldSysSite(param.getOldSysSite().trim());
                param.setOldWarehouseName(param.getOldWarehouseName().trim());
                param.setOldFnSku(param.getOldFnSku().trim());
                param.setOldSku(param.getOldSku().trim());
                param.setOldMaterialCode(param.getOldMaterialCode().trim());
                param.setInventoryQuantity(new BigDecimal(param.getInventoryQuantity().toString().trim()));
                param.setPlatform(param.getPlatform().trim());
                param.setSysShopsName(param.getSysShopsName().trim());
                param.setSysSite(param.getSysSite().trim());
                param.setWarehouseName(param.getWarehouseName().trim());
                param.setFnSku(param.getFnSku().trim());
                param.setSku(param.getSku().trim());
                param.setDepartment(param.getDepartment().trim());
                param.setTeam(param.getTeam().trim());
                param.setNeedsUser(param.getNeedsUser().trim());

                if (!platformList.contains(param.getPlatform())) {
                    validInfo.append("系统不存在此新平台!");
                }
                if (!shopList.contains(param.getSysShopsName())) {
                    validInfo.append("系统不存在此新账号!");
                }
                if (!siteList.contains(param.getSysSite())) {
                    validInfo.append("系统不存在此新站点!");
                }
                if (!departmentList.contains(param.getDepartment())) {
                    validInfo.append("系统不存在此事业部!");
                }
                if (!teamList.contains(param.getTeam())) {
                    validInfo.append("系统不存在此Team组!");
                }
                if (userNameList.contains(param.getNeedsUser())) {
                    userList.stream().map(i -> {
                        if(param.getNeedsUser().equals(i.get("name"))){
                            param.setNeedsUser(i.get("account") + "_" + i.get("name"));
                        }
                        return null;
                    });
                }else{
                    validInfo.append("系统不存在此需求人员!");
                }
            }
            if(StringUtils.isNotEmpty(validInfo)){
                param.setUploadRemark(validInfo.toString());
            }
        }
        return dataList;
    }

    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData batchExchange(List<OverseasWarehouseManageExchangeParam> params, Boolean isValidPass){
        log.info("批量换标保存，数据[{}]", JSONObject.toJSON(params));
//        List<OverseasWarehouseManageExchangeParam> errorList = new ArrayList<>();
        if(!isValidPass){
            //存在校验不通过的数据,终止执行
            String fileName = this.dealImportErrorList(params);
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,"导入失败！", fileName);
        }

        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;
        //其他入库单id集合（不同物料编码换标入库）
        List<BigDecimal> inIdList = new ArrayList<>();
        //其他出库单id集合（不同物料编码换标出库）
        List<BigDecimal> outIdList = new ArrayList<>();
        //跨组织直接调拨单id集合（同物料编码不同组织换标）
        List<BigDecimal> transferIdList = new ArrayList<>();
        //库龄报表入库、出库数据集合
        List<OverseasWarehouseAge> ageInParam = new ArrayList<>();
        List<OverseasWarehouseAge> ageOutParam = new ArrayList<>();
        for (OverseasWarehouseManageExchangeParam update : params) {
            /*if(StringUtils.isNotEmpty(update.getUploadRemark())){
                errorList.add(update);
                continue;
            }*/
//            try {
                //1、更新原标库存
                OverseasWarehouseManage updateInventory = new OverseasWarehouseManage();
                updateInventory.setId(update.getId());
                updateInventory.setInventoryQuantity(update.getNewInventoryQuantity());
                updateInventory.setUpdateTime(new Date());
                updateInventory.setUpdateUser(accountAndName);
                this.baseMapper.updateById(updateInventory);

                //根据维度查询新换标海外仓账存信息
                QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
                queryWrapper.eq("PLATFORM", update.getPlatform())
                        .eq("SYS_SHOPS_NAME", update.getSysShopsName())
                        .eq("SYS_SITE", update.getSysSite())
                        .eq("WAREHOUSE_NAME", update.getWarehouseName())
                        .eq("SKU", update.getSku())
                        .eq("MATERIAL_CODE", update.getMaterialCode())
                        .eq("FN_SKU", update.getFnSku());
                OverseasWarehouseManage changeManage = service.getOne(queryWrapper);
                if(changeManage == null){
                    //不存在新换标库存信息，默认新增一个库存信息，并初始化为0
                    OverseasWarehouseManage newManage = new OverseasWarehouseManage();
                    newManage.setPlatform(update.getPlatform());
                    newManage.setSysShopsName(update.getSysShopsName());
                    newManage.setSysSite(update.getSysSite());
                    newManage.setWarehouseName(update.getWarehouseName());
                    newManage.setFnSku(update.getFnSku());
                    newManage.setSku(update.getSku());
                    newManage.setMaterialCode(update.getMaterialCode());
                    newManage.setComeQuantity(BigDecimal.ZERO);
                    newManage.setInventoryQuantity(BigDecimal.ZERO);
                    newManage.setCreateUser(accountAndName);
                    newManage.setCreateTime(new Date());
                    newManage.setDataSource(InitDataSourceEnum.CHANGE.getCode());
                    service.save(newManage);
                    changeManage = newManage;
                }
                //原来新标的账存数量
                BigDecimal newManageInventoryQuantity =  changeManage.getInventoryQuantity();
                changeManage.setInventoryQuantity(changeManage.getInventoryQuantity().add(update.getInventoryQuantity()));
                service.updateById(changeManage);

                //2、同时生成出库和入库操作记录
                List<OverseasWarehouseManageRecord> insertRecordList = new ArrayList<>();
                String isChangeOrg = update.getIsChangeOrg() == true ? "1" : "0";
                String isChangeMaterialCode = update.getIsChangeMaterialCode() == true ? "1" : "0";

                //生成1条出库记录
                OverseasWarehouseManageRecord outRecord = new OverseasWarehouseManageRecord();
                outRecord.setParentId(update.getId());
                outRecord.setPlatform(update.getOldPlatform());
                outRecord.setSysShopsName(update.getOldSysShopsName());
                outRecord.setSysSite(update.getOldSysSite());
                outRecord.setWarehouseName(update.getOldWarehouseName());
                outRecord.setFnSku(update.getOldValidFnSku());
                outRecord.setSku(update.getOldSku());
                outRecord.setMaterialCode(update.getOldMaterialCode());
                outRecord.setComeQuantity(update.getComeQuantity());
                outRecord.setChangeComeQuantity(BigDecimal.ZERO);
                outRecord.setNowComeQuantity(update.getComeQuantity());
                outRecord.setInventoryQuantity(update.getOldInventoryQuantity());
                outRecord.setChangeInventoryQuantity(update.getInventoryQuantity());
                outRecord.setNowInventoryQuantity(update.getNewInventoryQuantity());
                outRecord.setOperateType(OperateTypeEnum.CHANGE.getName());
                outRecord.setDepartment(update.getDepartment());
                outRecord.setTeam(update.getTeam());
                outRecord.setNeedsUser(update.getNeedsUser());
                outRecord.setCreateTime(new Date());
                outRecord.setCreateUser(accountAndName);
                outRecord.setInOrg(update.getInOrg());
                outRecord.setInOrgName(update.getInOrgName());
                outRecord.setOutOrg(update.getOutOrg());
                outRecord.setOutOrgName(update.getOutOrgName());
                outRecord.setBusinessType(OverseasBusinessTypeEnum.OUT.getName());
                outRecord.setIsChangeOrg(isChangeOrg);
                outRecord.setIsChangeMaterialCode(isChangeMaterialCode);
                outRecord.setInWarehouseName(update.getWarehouseName());//为跨组织同物料直接调拨单使用
                outRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                if(!update.getIsChangeMaterialCode() && !update.getIsChangeOrg()){
                    outRecord.setSyncErpStatus(BaseSyncStatusEnum.NOT.getCode());
                }

                //生成1条入库记录
                OverseasWarehouseManageRecord inRecord = new OverseasWarehouseManageRecord();
                BeanUtils.copyProperties(update, inRecord);
                inRecord.setId(null);
                inRecord.setParentId(changeManage.getId());//新换标主表id
                inRecord.setOperateType(OperateTypeEnum.CHANGE.getName());
                inRecord.setCreateTime(new Date());
                inRecord.setCreateUser(accountAndName);
                inRecord.setMaterialCode(update.getMaterialCode());
                inRecord.setInventoryQuantity(newManageInventoryQuantity);
                inRecord.setChangeInventoryQuantity(update.getInventoryQuantity());
                inRecord.setNowInventoryQuantity(changeManage.getInventoryQuantity());
                inRecord.setComeQuantity(changeManage.getComeQuantity());
                inRecord.setChangeComeQuantity(BigDecimal.ZERO);
                inRecord.setNowComeQuantity(changeManage.getComeQuantity());
                inRecord.setBusinessType(OverseasBusinessTypeEnum.IN.getName());
                inRecord.setIsChangeOrg(isChangeOrg);
                inRecord.setIsChangeMaterialCode(isChangeMaterialCode);
                inRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                if(!update.getIsChangeMaterialCode()){
                    //同物料编码且不同组织，调用K3直接调拨单（跨组织调拨），用的是换标出库记录，换标入库记录不用同步K3；同物料编码且同组织，不与K3做交互
                    inRecord.setSyncErpStatus(BaseSyncStatusEnum.NOT.getCode());
                }

                //判断是否跨站点换标
                String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
                if(update.getIsChangeSite()){
                    //跨站点换标
                    //从redis取出库单号
                    String order = this.getLogisticsOrder(env + "_"  + OrderTypePreEnum.KZHB.getCode() + pureDate, OrderTypePreEnum.KZHB, 5);
                    outRecord.setOperateTypeDetail(OutBusinessTypeEnum.DIFF_SITE_OUT_CHANGE.getName());
                    outRecord.setOutOrder(order);
                    inRecord.setOperateTypeDetail(InBusinessTypeEnum.DIFF_SITE_IN_CHANGE.getName());
                    inRecord.setInOrder(order);
                } else {
                    //同站点换标
                    //从redis取出库单号
                    String order = this.getLogisticsOrder(env + "_"  + OrderTypePreEnum.TZHB.getCode() + pureDate, OrderTypePreEnum.TZHB, 5);
                    outRecord.setOperateTypeDetail(OutBusinessTypeEnum.SAME_SITE_OUT_CHANGE.getName());
                    outRecord.setOutOrder(order);
                    inRecord.setOperateTypeDetail(InBusinessTypeEnum.SAME_SITE_IN_CHANGE.getName());
                    inRecord.setInOrder(order);
                }
                insertRecordList.add(outRecord);
                insertRecordList.add(inRecord);
                recordService.saveBatch(insertRecordList);

                //3、同时生成海外仓出库管理记录和海外仓入库管理记录
                //插入海外仓出库管理主表
                OverseasOutWarehouse out = new OverseasOutWarehouse();
                BeanUtils.copyProperties(outRecord, out);
                out.setParentId(outRecord.getId());
                out.setOperateType(outRecord.getOperateTypeDetail());
                out.setShouldOutQuantity(outRecord.getChangeInventoryQuantity());
                out.setSkuNum(BigDecimal.ONE);
                out.setInWarehouseName(changeManage.getWarehouseName());
                out.setOutWarehouseName(outRecord.getWarehouseName());
                out.setUpdateTime(null);
                out.setUpdateUser(null);
                outWarehouseService.save(out);

                //插入海外仓出库管理明细表
                OverseasOutWarehouseDetail outDetail = new OverseasOutWarehouseDetail();
                outDetail.setOutOrder(out.getOutOrder());
                outDetail.setSku(outRecord.getSku());
                outDetail.setFnSku(outRecord.getFnSku());
                outDetail.setMaterialCode(outRecord.getMaterialCode());
                outDetail.setCreateTime(outRecord.getCreateTime());
                outDetail.setUpdateUser(outRecord.getCreateUser());
                outDetail.setQuantity(outRecord.getChangeInventoryQuantity());
                outDetail.setDepartment(outRecord.getDepartment());
                outDetail.setTeam(outRecord.getTeam());
                outDetail.setNeedsUser(outRecord.getNeedsUser());
                outWarehouseDetailService.save(outDetail);

                //插入海外仓入库管理主表
                OverseasInWarehouse in = new OverseasInWarehouse();
                BeanUtils.copyProperties(inRecord, in);
                in.setParentId(inRecord.getId());
                in.setOperateType(inRecord.getOperateTypeDetail());
                in.setShouldInventoryQuantity(inRecord.getChangeInventoryQuantity());
                in.setAlreadyInventoryQuantity(inRecord.getChangeInventoryQuantity());
                in.setNotInventoryQuantity(BigDecimal.ZERO);
                in.setSkuNum(BigDecimal.ONE);
                in.setOutWarehouseName(update.getOldWarehouseName());
                in.setInWarehouseName(inRecord.getWarehouseName());
                in.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
                in.setConfirmStartTime(inRecord.getCreateTime());
                in.setConfirmEndTime(inRecord.getCreateTime());
                in.setConfirmUser(inRecord.getCreateUser());
                in.setUpdateTime(null);
                in.setUpdateUser(null);
                inWarehouseService.save(in);

                //插入海外仓入库管理明细表
                OverseasInWarehouseDetail inDetail = new OverseasInWarehouseDetail();
                inDetail.setInOrder(in.getInOrder());
                inDetail.setSku(inRecord.getSku());
                inDetail.setFnSku(inRecord.getFnSku());
                inDetail.setQuantity(inRecord.getChangeInventoryQuantity());
                inDetail.setMaterialCode(inRecord.getMaterialCode());
                inDetail.setActualQuantity(inRecord.getChangeInventoryQuantity());
                inDetail.setConfirmStatus(in.getConfirmStatus());
                inDetail.setConfirmTime(inRecord.getCreateTime());
                inDetail.setConfirmUser(inRecord.getCreateUser());
                inDetail.setCreateTime(inRecord.getCreateTime());
                inDetail.setCreateUser(inRecord.getCreateUser());
                inDetail.setDepartment(inRecord.getDepartment());
                inDetail.setTeam(inRecord.getTeam());
                inDetail.setNeedsUser(inRecord.getNeedsUser());
                inWarehouseDetailService.save(inDetail);

                //4、调用ERP接口
                if(update.getIsChangeMaterialCode()){
                    //不同物料编码，调用K3其他入库单、其他出库单接口
                    outIdList.add(outRecord.getId());
                    inIdList.add(inRecord.getId());
                }else{
                    //同物料编码且不同组织，调用K3直接调拨单（跨组织调拨）；同物料编码且同组织，不与K3做交互
                    if(update.getIsChangeOrg()){
                        transferIdList.add(outRecord.getId());
                    }
                }

                //处理库龄报表入库、出库
                OverseasWarehouseAge ageIn = new OverseasWarehouseAge();
                ageIn.setPlatform(inRecord.getPlatform());
                ageIn.setSysShopsName(inRecord.getSysShopsName());
                ageIn.setSysSite(inRecord.getSysSite());
                ageIn.setWarehouseName(inRecord.getWarehouseName());
                ageIn.setFnSku(inRecord.getFnSku());
                ageIn.setSku(inRecord.getSku());
                ageIn.setMaterialCode(inRecord.getMaterialCode());
                ageIn.setSignQuantity(inRecord.getChangeInventoryQuantity());
                ageIn.setInventoryQuantity(inRecord.getChangeInventoryQuantity());
                ageIn.setSignDate(inRecord.getCreateTime());
                ageIn.setCreateTime(inRecord.getCreateTime());
                ageIn.setCreateUser(inRecord.getCreateUser());
                ageInParam.add(ageIn);

                OverseasWarehouseAge ageOut = new OverseasWarehouseAge();
                ageOut.setPlatform(outRecord.getPlatform());
                ageOut.setSysShopsName(outRecord.getSysShopsName());
                ageOut.setSysSite(outRecord.getSysSite());
                ageOut.setWarehouseName(outRecord.getWarehouseName());
                ageOut.setFnSku(outRecord.getFnSku());
                ageOut.setSku(outRecord.getSku());
                ageOut.setMaterialCode(outRecord.getMaterialCode());
                ageOut.setInventoryQuantity(outRecord.getChangeInventoryQuantity());
//                ageOut.setRemark(outRecord.getOutOrder() + ":" + outRecord.getChangeInventoryQuantity());
                ageOutParam.add(ageOut);

            /*} catch (Exception e){
                update.setUploadRemark("数据处理异常，换标失败！异常信息：" + e.getMessage());
                errorList.add(update);
                log.error("数据处理异常，换标失败！参数[{}]，异常信息[{}]", JSONObject.toJSON(update), e.getMessage());
            }*/
        }

        //K3其他出库
        if(CollectionUtil.isNotEmpty(outIdList)){
            try {
                syncBatchChangeOutStockToErp(outIdList);
            } catch (Exception e){
                log.error("换标调用K3其他出库异常，换标失败！异常信息[{}]", e.getMessage());
            }
        }

        //K3其他入库
        if(CollectionUtil.isNotEmpty(inIdList)){
            try {
                syncBatchChangeInStockToErp(inIdList);
            } catch (Exception e){
                log.error("换标调用K3其他入库异常，换标失败！异常信息[{}]", e.getMessage());
            }
        }

        //K3直接调拨单（跨组织调拨）
        if(CollectionUtil.isNotEmpty(transferIdList)){
            try {
                log.error("批量换标调用K3直接调拨单，入参信息[{}]", JSONObject.toJSON(transferIdList));
                syncTransferToErpService.diffSiteChangeSyncERP(transferIdList);
            } catch (Exception e){
                log.error("换标调用K3直接调拨单异常，换标失败！异常信息[{}]", e.getMessage());
            }
        }

        //处理库龄报表入库、出库
        //入库
        ageService.batchAgeIn(ageInParam);

        //出库
        ageService.batchAgeOut(ageOutParam);
        return ResponseData.success("批量换标保存成功！");
    }

    private String dealImportErrorList(List<OverseasWarehouseManageExchangeParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, OverseasWarehouseManageExchangeParam.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("批量换标-保存异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("批量换标-保存异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    private String dealImportRakutenErrorList(List<RakutenOverseasShipmentResult> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, RakutenOverseasShipmentResult.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("导入Excel异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入Excel异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData check(OverseasWarehouseManageParam param, OverseasWarehouseManage overseasWarehouseManage) {
        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        //盘点账存数量
        BigDecimal checkQuantity = param.getInventoryQuantity();
        //盘点前账存数量
        BigDecimal oldQuantity = overseasWarehouseManage.getInventoryQuantity();
        if(checkQuantity.compareTo(oldQuantity) == 0){
            return ResponseData.error("盘点失败，盘点账存数量与现有数量不能相同！");
        }
        String checkReason = param.getCheckReason();
        //盘盈，盘点原因必须为：库存不准、丢失找回
        if(checkQuantity.compareTo(oldQuantity) > 0 && !CheckReasonEnum.getAddCheckReasonValue().contains(checkReason)){
            return ResponseData.error("盘点失败，盘盈的盘点原因需为：库存不准、丢失找回！");
        }
        //盘亏，盘点原因必须为：销毁、丢失、库存不准
        if(checkQuantity.compareTo(oldQuantity) < 0 && !CheckReasonEnum.getSubCheckReasonValue().contains(checkReason)){
            return ResponseData.error("盘点失败，盘亏的盘点原因需为：销毁、丢失、库存不准！");
        }

        //1、更新盘点后现有库存
        OverseasWarehouseManage updateInventory = new OverseasWarehouseManage();
        updateInventory.setId(overseasWarehouseManage.getId());
        updateInventory.setInventoryQuantity(checkQuantity);
        updateInventory.setUpdateTime(new Date());
        updateInventory.setUpdateUser(accountAndName);
        this.baseMapper.updateById(updateInventory);

        //2、插入操作记录表
        OverseasWarehouseManageRecord record = new OverseasWarehouseManageRecord();
        BeanUtils.copyProperties(overseasWarehouseManage, record);
        record.setId(null);
        record.setParentId(overseasWarehouseManage.getId());
        record.setOperateType(OperateTypeEnum.CHECK.getName());
        record.setCreateTime(new Date());
        record.setCreateUser(accountAndName);
        record.setUpdateTime(null);
        record.setUpdateUser(null);
        record.setDepartment(param.getDepartment());
        record.setTeam(param.getTeam());
        record.setInOrg(param.getInOrg());
        record.setOutOrg(param.getOutOrg());
        record.setInOrgName(param.getInOrgName());
        record.setOutOrgName(param.getOutOrgName());
        record.setRemark("盘点原因：" + checkReason + "。" + param.getRemark());
        record.setChangeComeQuantity(BigDecimal.ZERO);
        record.setNowComeQuantity(overseasWarehouseManage.getComeQuantity());
        record.setNowInventoryQuantity(checkQuantity);
        record.setIsChangeOrg("0");
        record.setIsChangeMaterialCode("0");
        record.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());

        //3、做出/入库处理
        //盘盈
        //从redis取入库单号
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String order = this.getLogisticsOrder(env + "_"  + OrderTypePreEnum.PD.getCode() + pureDate, OrderTypePreEnum.PD, 5);
        if(checkQuantity.compareTo(oldQuantity) > 0){
            record.setChangeInventoryQuantity(checkQuantity.subtract(oldQuantity));
            record.setInOrder(order);
            record.setOperateTypeDetail(InBusinessTypeEnum.CHECK_ADD.getName());
            record.setBusinessType(OverseasBusinessTypeEnum.CHECK_ADD.getName());
            //插入操作记录表
            recordService.save(record);

            //插入海外仓入库管理主表
            OverseasInWarehouse in = new OverseasInWarehouse();
            in.setParentId(record.getId());
            in.setInOrder(record.getInOrder());
            in.setPlatform(record.getPlatform());
            in.setSysShopsName(record.getSysShopsName());
            in.setSysSite(record.getSysSite());
            in.setOperateType(record.getOperateTypeDetail());
            in.setShouldInventoryQuantity(record.getChangeInventoryQuantity());
            in.setAlreadyInventoryQuantity(record.getChangeInventoryQuantity());
            in.setNotInventoryQuantity(BigDecimal.ZERO);
            in.setSkuNum(BigDecimal.ONE);
            in.setInWarehouseName(record.getWarehouseName());
            in.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
            in.setConfirmStartTime(record.getCreateTime());
            in.setConfirmEndTime(record.getCreateTime());
            in.setConfirmUser(record.getCreateUser());
            in.setCreateTime(record.getCreateTime());
            in.setCreateUser(record.getCreateUser());
            in.setRemark(record.getRemark());
            inWarehouseService.save(in);

            //插入海外仓入库管理明细表
            OverseasInWarehouseDetail inDetail = new OverseasInWarehouseDetail();
            inDetail.setInOrder(in.getInOrder());
            inDetail.setSku(record.getSku());
            inDetail.setFnSku(record.getFnSku());
            inDetail.setQuantity(record.getChangeInventoryQuantity());
            inDetail.setMaterialCode(record.getMaterialCode());
            inDetail.setActualQuantity(record.getChangeInventoryQuantity());
            inDetail.setConfirmStatus(in.getConfirmStatus());
            inDetail.setConfirmTime(record.getCreateTime());
            inDetail.setConfirmUser(record.getCreateUser());
            inDetail.setCreateTime(record.getCreateTime());
            inDetail.setCreateUser(record.getCreateUser());
            inDetail.setDepartment(record.getDepartment());
            inDetail.setTeam(record.getTeam());
            inWarehouseDetailService.save(inDetail);
        }

        //盘亏
        if(checkQuantity.compareTo(oldQuantity) < 0){
            record.setChangeInventoryQuantity(oldQuantity.subtract(checkQuantity));
            record.setOutOrder(order);
            record.setOperateTypeDetail(OutBusinessTypeEnum.CHECK_SUB.getName());
            record.setBusinessType(OverseasBusinessTypeEnum.CHECK_SUB.getName());
            //插入操作记录表
            recordService.save(record);

            //插入海外仓出库管理主表
            OverseasOutWarehouse out = new OverseasOutWarehouse();
            out.setParentId(record.getId());
            out.setOutOrder(record.getOutOrder());
            out.setPlatform(record.getPlatform());
            out.setSysShopsName(record.getSysShopsName());
            out.setSysSite(record.getSysSite());
            out.setOperateType(record.getOperateTypeDetail());
            out.setShouldOutQuantity(record.getChangeInventoryQuantity());
            out.setSkuNum(BigDecimal.ONE);
            out.setOutWarehouseName(record.getWarehouseName());
            out.setCreateTime(record.getCreateTime());
            out.setCreateUser(record.getCreateUser());
            out.setRemark(record.getRemark());
            outWarehouseService.save(out);

            //插入海外仓出库管理明细表
            OverseasOutWarehouseDetail outDetail = new OverseasOutWarehouseDetail();
            outDetail.setOutOrder(out.getOutOrder());
            outDetail.setSku(record.getSku());
            outDetail.setFnSku(record.getFnSku());
            outDetail.setMaterialCode(record.getMaterialCode());
            outDetail.setQuantity(record.getChangeInventoryQuantity());
            outDetail.setCreateTime(record.getCreateTime());
            outDetail.setCreateUser(record.getCreateUser());
            outDetail.setDepartment(record.getDepartment());
            outDetail.setTeam(record.getTeam());
            outWarehouseDetailService.save(outDetail);
        }

        //4、调用ERP
        if(StringUtils.isNotBlank(record.getInOrder())){
            //K3其他入库
            ResponseData inResponseData = syncCheckInStockToErp(record.getId());
            if(ResponseData.DEFAULT_ERROR_CODE.equals(inResponseData.getCode())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("盘点盘盈同步ERP其他入库单：" + inResponseData.getMessage());
            }

            //处理库龄报表入库
            List<OverseasWarehouseAge> ageInParam = new ArrayList<>();
            OverseasWarehouseAge ageIn = new OverseasWarehouseAge();
            ageIn.setPlatform(record.getPlatform());
            ageIn.setSysShopsName(record.getSysShopsName());
            ageIn.setSysSite(record.getSysSite());
            ageIn.setWarehouseName(record.getWarehouseName());
            ageIn.setFnSku(record.getFnSku());
            ageIn.setSku(record.getSku());
            ageIn.setMaterialCode(record.getMaterialCode());
            ageIn.setSignQuantity(record.getChangeInventoryQuantity());
            ageIn.setInventoryQuantity(record.getChangeInventoryQuantity());
            ageIn.setSignDate(record.getCreateTime());
            ageIn.setCreateTime(record.getCreateTime());
            ageIn.setCreateUser(record.getCreateUser());
            ageInParam.add(ageIn);
            ageService.batchAgeIn(ageInParam);
        }

        if(StringUtils.isNotBlank(record.getOutOrder())){
            //K3其他出库
            ResponseData outResponseData = syncCheckOutStockToErp(record.getId());
            if(ResponseData.DEFAULT_ERROR_CODE.equals(outResponseData.getCode())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("盘点盘亏同步ERP其他出库单：" + outResponseData.getMessage());
            }

            //处理库龄报表出库
            List<OverseasWarehouseAge> ageOutParam = new ArrayList<>();
            OverseasWarehouseAge ageOut = new OverseasWarehouseAge();
            ageOut.setPlatform(record.getPlatform());
            ageOut.setSysShopsName(record.getSysShopsName());
            ageOut.setSysSite(record.getSysSite());
            ageOut.setWarehouseName(record.getWarehouseName());
            ageOut.setFnSku(record.getFnSku());
            ageOut.setSku(record.getSku());
            ageOut.setMaterialCode(record.getMaterialCode());
            ageOut.setInventoryQuantity(record.getChangeInventoryQuantity());
//            ageOut.setRemark(record.getOutOrder() + ":" + record.getChangeInventoryQuantity());
            ageOutParam.add(ageOut);
            ageService.batchAgeOut(ageOutParam);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadCheck(MultipartFile file, List<String> platformList, List<String> shopList, List<String> siteList, List<String> departmentList, List<String> teamList) {
        log.info("批量盘点导入处理开始");
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OverseasWarehouseManageCheckParam>();
            EasyExcel.read(buffer, OverseasWarehouseManageCheckParam.class, listener).sheet()
                    .doRead();

            List<OverseasWarehouseManageCheckParam> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            return ResponseData.success(this.validCheck(dataList, platformList, shopList, siteList, departmentList, teamList));
        } catch (Exception e) {
            log.error("批量盘点导入处理异常，导入失败！", e);
            return ResponseData.error("批量盘点导入处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("批量盘点导入关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入盘点数据校验处理
     * @param dataList
     * @param platformList
     * @param shopList
     * @param siteList
     * @param departmentList
     * @param teamList
     * @return
     */
    private List<OverseasWarehouseManageCheckParam> validCheck(List<OverseasWarehouseManageCheckParam> dataList,
                                                                     List<String> platformList,
                                                                     List<String> shopList,
                                                                     List<String> siteList,
                                                                     List<String> departmentList,
                                                                     List<String> teamList) {

        Iterator<OverseasWarehouseManageCheckParam> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            OverseasWarehouseManageCheckParam param = iterator.next();
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if(param.getPlatform() == null
                    || param.getSysShopsName() == null
                    || param.getSysSite() == null
                    || param.getWarehouseName() == null
                    || param.getFnSku() == null
                    || param.getSku() == null
                    || param.getMaterialCode() == null
                    || param.getInventoryQuantity() == null
                    || param.getCheckReason() == null
            ){
                //不为空校验
                validInfo.append("平台、账号、站点、仓库名称、FNSKU、SKU、物料编码、账存数量、盘点原因为必填项!");
            } else {
                //去空格
                param.setPlatform(param.getPlatform().trim());
                param.setSysShopsName(param.getSysShopsName().trim());
                param.setSysSite(param.getSysSite().trim());
                param.setWarehouseName(param.getWarehouseName().trim());
                param.setFnSku(param.getFnSku().trim());
                param.setSku(param.getSku().trim());
                param.setMaterialCode(param.getMaterialCode().trim());
                param.setInventoryQuantity(new BigDecimal(param.getInventoryQuantity().toString().trim()));
                if(StringUtils.isNotEmpty(param.getDepartment())){
                    param.setDepartment(param.getDepartment().trim());
                }
                if(StringUtils.isNotEmpty(param.getTeam())){
                    param.setTeam(param.getTeam().trim());
                }
                param.setCheckReason(param.getCheckReason().trim());

                if (!platformList.contains(param.getPlatform())) {
                    validInfo.append("系统不存在此平台!");
                }
                if (!shopList.contains(param.getSysShopsName())) {
                    validInfo.append("系统不存在此账号!");
                }
                if (!siteList.contains(param.getSysSite())) {
                    validInfo.append("系统不存在此站点!");
                }
                //优先根据账号、站点、sku系统匹配事业部和Team，系统匹配不出则取Excel数据
                InvProductGalleryParam productParam = new InvProductGalleryParam();
                productParam.setSysShopsName(param.getSysShopsName());
                productParam.setSysSite(param.getSysSite());
                productParam.setSku(param.getSku());
                List<InvProductGalleryParam> productList = service.getInvProductGallery(productParam);
                if(CollectionUtil.isNotEmpty(productList)){
                    param.setDepartment(productList.get(0).getDepartment());
                    param.setTeam(productList.get(0).getTeam());
                } else{
                    //校验导入Excel的事业部和Team值
                    if (!departmentList.contains(param.getDepartment())) {
                        validInfo.append("系统不存在此需求部门!");
                    }
                    if (!teamList.contains(param.getTeam())) {
                        validInfo.append("系统不存在此需求Team!");
                    }
                }
                if(StringUtils.isBlank(param.getDepartment())){
                    validInfo.append("系统不存在此需求部门且导入数据为空!");
                }
                if(StringUtils.isBlank(param.getTeam())){
                    validInfo.append("系统不存在此需求Team且导入数据为空!");
                }
            }
            if(StringUtils.isNotEmpty(validInfo)){
                param.setUploadRemark(validInfo.toString());
            }
        }
        return dataList;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchCheck(List<OverseasWarehouseManageCheckParam> params) {
        log.info("批量盘点保存，数据[{}]", JSONObject.toJSON(params));
//        List<OverseasWarehouseManageCheckParam> errorList = new ArrayList<>();
        Boolean isValidPass = true;
        for (OverseasWarehouseManageCheckParam update : params) {
            //根据维度查询盘点海外仓账存信息
            QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
            queryWrapper.eq("PLATFORM", update.getPlatform())
                    .eq("SYS_SHOPS_NAME", update.getSysShopsName())
                    .eq("SYS_SITE", update.getSysSite())
                    .eq("WAREHOUSE_NAME", update.getWarehouseName())
                    .eq("SKU", update.getSku())
                    .eq("MATERIAL_CODE", update.getMaterialCode())
                    .likeLeft("FN_SKU", update.getFnSku());
            OverseasWarehouseManage overseasWarehouseManage = service.getOne(queryWrapper);
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if (overseasWarehouseManage == null) {
                validInfo.append("盘点失败，不存在此库存信息！");
            } else {
                if (BigDecimal.ZERO.compareTo(update.getInventoryQuantity()) > 0) {
                    validInfo.append("盘点失败，盘点账存数量不能小于0！");
                }
                if (update.getInventoryQuantity().compareTo(overseasWarehouseManage.getInventoryQuantity()) == 0) {
                    validInfo.append("盘点失败，盘点账存数量与现有数量不能相同！");
                }
                String checkReason = update.getCheckReason();
                //盘盈，盘点原因必须为：库存不准、丢失找回
                if(update.getInventoryQuantity().compareTo(overseasWarehouseManage.getInventoryQuantity()) > 0 && !CheckReasonEnum.getAddCheckReasonValue().contains(checkReason)){
                    validInfo.append("盘点失败，盘盈的盘点原因需为：库存不准、丢失找回！");
                }
                //盘亏，盘点原因必须为：销毁、丢失、库存不准
                if(update.getInventoryQuantity().compareTo(overseasWarehouseManage.getInventoryQuantity()) < 0 && !CheckReasonEnum.getSubCheckReasonValue().contains(checkReason)){
                    validInfo.append("盘点失败，盘亏的盘点原因需为：销毁、丢失、库存不准！");
                }
            }

            if (StringUtils.isNotBlank(validInfo.toString())) {
                update.setUploadRemark(update.getUploadRemark() + validInfo.toString());
            }

            if (StringUtils.isNotEmpty(update.getUploadRemark())) {
                isValidPass = false;
            }
        }

        if(!isValidPass){
            String fileName = this.dealBatchSaveCheckErrorList(params);
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,"导入失败！", fileName);
        }

        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        //其他入库单id集合（盘盈）
        List<BigDecimal> inIdList = new ArrayList<>();
        //其他出库单id集合（盘亏）
        List<BigDecimal> outIdList = new ArrayList<>();
        //处理库龄报表入库、出库
        List<OverseasWarehouseAge> ageInParam = new ArrayList<>();
        List<OverseasWarehouseAge> ageOutParam = new ArrayList<>();
        for (OverseasWarehouseManageCheckParam update : params) {
            //根据维度查询盘点海外仓账存信息
            QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
            queryWrapper.eq("PLATFORM", update.getPlatform())
                    .eq("SYS_SHOPS_NAME", update.getSysShopsName())
                    .eq("SYS_SITE", update.getSysSite())
                    .eq("WAREHOUSE_NAME", update.getWarehouseName())
                    .eq("SKU", update.getSku())
                    .eq("MATERIAL_CODE", update.getMaterialCode())
                    .likeLeft("FN_SKU", update.getFnSku());
            OverseasWarehouseManage overseasWarehouseManage = service.getOne(queryWrapper);
            //通过导入fnSKU后六位匹配出对应的fnSKU
            update.setValidFnSku(overseasWarehouseManage.getFnSku());
            update.setId(overseasWarehouseManage.getId());
            update.setOldInventoryQuantity(overseasWarehouseManage.getInventoryQuantity());
            OverseasWarehouseManage updateInventory = new OverseasWarehouseManage();
            updateInventory.setId(update.getId());
            updateInventory.setInventoryQuantity(update.getInventoryQuantity());
            updateInventory.setUpdateTime(DateUtil.date());
            updateInventory.setUpdateUser(accountAndName);
            this.baseMapper.updateById(updateInventory);

            //2、插入操作记录表
            OverseasWarehouseManageRecord record = new OverseasWarehouseManageRecord();
            BeanUtils.copyProperties(update, record);
            record.setId(null);
            record.setParentId(update.getId());
            record.setOperateType(OperateTypeEnum.CHECK.getName());
            record.setCreateTime(DateUtil.date());
            record.setCreateUser(accountAndName);
            record.setUpdateTime(null);
            record.setUpdateUser(null);
            record.setDepartment(update.getDepartment());
            record.setTeam(update.getTeam());
            record.setInOrg(update.getInOrg());
            record.setOutOrg(update.getOutOrg());
            record.setInOrgName(update.getInOrgName());
            record.setOutOrgName(update.getOutOrgName());
            record.setRemark("盘点原因：" + update.getCheckReason() + "。" + update.getRemark());
            record.setFnSku(update.getValidFnSku());
            record.setChangeComeQuantity(BigDecimal.ZERO);
            record.setComeQuantity(overseasWarehouseManage.getComeQuantity());
            record.setNowComeQuantity(overseasWarehouseManage.getComeQuantity());
            record.setInventoryQuantity(update.getOldInventoryQuantity());
            record.setNowInventoryQuantity(update.getInventoryQuantity());
            record.setIsChangeOrg("0");
            record.setIsChangeMaterialCode("0");
            record.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
            //从redis取入库单号
            String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
            String order = this.getLogisticsOrder(env + "_"  + OrderTypePreEnum.PD.getCode() + pureDate, OrderTypePreEnum.PD, 5);

            //3、做出/入库处理
            //盘盈
            if(update.getInventoryQuantity().compareTo(update.getOldInventoryQuantity()) > 0){
                record.setChangeInventoryQuantity(update.getInventoryQuantity().subtract(update.getOldInventoryQuantity()));
                record.setInOrder(order);
                record.setOperateTypeDetail(InBusinessTypeEnum.CHECK_ADD.getName());
                record.setBusinessType(OverseasBusinessTypeEnum.CHECK_ADD.getName());
                //插入操作记录表
                recordService.save(record);

                //插入海外仓入库管理主表
                OverseasInWarehouse in = new OverseasInWarehouse();
                in.setParentId(record.getId());
                in.setInOrder(record.getInOrder());
                in.setPlatform(record.getPlatform());
                in.setSysShopsName(record.getSysShopsName());
                in.setSysSite(record.getSysSite());
                in.setOperateType(record.getOperateTypeDetail());
                in.setShouldInventoryQuantity(record.getChangeInventoryQuantity());
                in.setAlreadyInventoryQuantity(record.getChangeInventoryQuantity());
                in.setNotInventoryQuantity(BigDecimal.ZERO);
                in.setSkuNum(BigDecimal.ONE);
                in.setInWarehouseName(record.getWarehouseName());
                in.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
                in.setConfirmStartTime(record.getCreateTime());
                in.setConfirmEndTime(record.getCreateTime());
                in.setConfirmUser(record.getCreateUser());
                in.setCreateTime(record.getCreateTime());
                in.setCreateUser(record.getCreateUser());
                in.setRemark(record.getRemark());
                inWarehouseService.save(in);

                //插入海外仓入库管理明细表
                OverseasInWarehouseDetail inDetail = new OverseasInWarehouseDetail();
                inDetail.setInOrder(in.getInOrder());
                inDetail.setSku(record.getSku());
                inDetail.setFnSku(record.getFnSku());
                inDetail.setQuantity(record.getChangeInventoryQuantity());
                inDetail.setMaterialCode(record.getMaterialCode());
                inDetail.setActualQuantity(record.getChangeInventoryQuantity());
                inDetail.setConfirmStatus(in.getConfirmStatus());
                inDetail.setConfirmTime(record.getCreateTime());
                inDetail.setConfirmUser(record.getCreateUser());
                inDetail.setCreateTime(record.getCreateTime());
                inDetail.setCreateUser(record.getCreateUser());
                inDetail.setDepartment(record.getDepartment());
                inDetail.setTeam(record.getTeam());
                inWarehouseDetailService.save(inDetail);
                inIdList.add(record.getId());//其他入库单id集合

                //处理库龄报表入库
                OverseasWarehouseAge ageIn = new OverseasWarehouseAge();
                ageIn.setPlatform(record.getPlatform());
                ageIn.setSysShopsName(record.getSysShopsName());
                ageIn.setSysSite(record.getSysSite());
                ageIn.setWarehouseName(record.getWarehouseName());
                ageIn.setFnSku(record.getFnSku());
                ageIn.setSku(record.getSku());
                ageIn.setMaterialCode(record.getMaterialCode());
                ageIn.setSignQuantity(record.getChangeInventoryQuantity());
                ageIn.setInventoryQuantity(record.getChangeInventoryQuantity());
                ageIn.setSignDate(record.getCreateTime());
                ageIn.setCreateTime(record.getCreateTime());
                ageIn.setCreateUser(record.getCreateUser());
                ageInParam.add(ageIn);
            }

            //盘亏
            if(update.getInventoryQuantity().compareTo(update.getOldInventoryQuantity()) < 0){
                record.setChangeInventoryQuantity(update.getOldInventoryQuantity().subtract(update.getInventoryQuantity()));
                record.setOutOrder(order);
                record.setOperateTypeDetail(OutBusinessTypeEnum.CHECK_SUB.getName());
                record.setBusinessType(OverseasBusinessTypeEnum.CHECK_SUB.getName());
                //插入操作记录表
                recordService.save(record);

                //插入海外仓出库管理主表
                OverseasOutWarehouse out = new OverseasOutWarehouse();
                out.setParentId(record.getId());
                out.setOutOrder(record.getOutOrder());
                out.setPlatform(record.getPlatform());
                out.setSysShopsName(record.getSysShopsName());
                out.setSysSite(record.getSysSite());
                out.setOperateType(record.getOperateTypeDetail());
                out.setShouldOutQuantity(record.getChangeInventoryQuantity());
                out.setSkuNum(BigDecimal.ONE);
                out.setOutWarehouseName(record.getWarehouseName());
                out.setCreateTime(record.getCreateTime());
                out.setCreateUser(record.getCreateUser());
                out.setRemark(record.getRemark());
                outWarehouseService.save(out);

                //插入海外仓出库管理明细表
                OverseasOutWarehouseDetail outDetail = new OverseasOutWarehouseDetail();
                outDetail.setOutOrder(out.getOutOrder());
                outDetail.setSku(record.getSku());
                outDetail.setFnSku(record.getFnSku());
                outDetail.setMaterialCode(record.getMaterialCode());
                outDetail.setQuantity(record.getChangeInventoryQuantity());
                outDetail.setCreateTime(record.getCreateTime());
                outDetail.setCreateUser(record.getCreateUser());
                outDetail.setDepartment(record.getDepartment());
                outDetail.setTeam(record.getTeam());
                outWarehouseDetailService.save(outDetail);
                outIdList.add(record.getId());//其他出库单id集合

                //处理库龄报表入库、出库
                OverseasWarehouseAge ageOut = new OverseasWarehouseAge();
                ageOut.setPlatform(record.getPlatform());
                ageOut.setSysShopsName(record.getSysShopsName());
                ageOut.setSysSite(record.getSysSite());
                ageOut.setWarehouseName(record.getWarehouseName());
                ageOut.setFnSku(record.getFnSku());
                ageOut.setSku(record.getSku());
                ageOut.setMaterialCode(record.getMaterialCode());
                ageOut.setInventoryQuantity(record.getChangeInventoryQuantity());
//                ageOut.setRemark(record.getOutOrder() + ":" + record.getChangeInventoryQuantity());
                ageOutParam.add(ageOut);
            }
        }

        //4、批量调用ERP
        if(CollectionUtil.isNotEmpty(inIdList)){
            //K3其他入库
            try {
                syncBatchCheckInStockToErp(inIdList);
            }catch(Exception e){
                log.error("调用K3其他入库单异常，盘点失败！异常信息[{}]", e.getMessage());
            }

            //处理库龄报表入库
            ageService.batchAgeIn(ageInParam);
        }

        if(CollectionUtil.isNotEmpty(outIdList)){
            //K3其他出库
            try {
                syncBatchCheckOutStockToErp(outIdList);
            }catch(Exception e){
                log.error("调用K3其他出库单异常，盘点失败！异常信息[{}]", e.getMessage());
            }

            //处理库龄报表出库
            ageService.batchAgeOut(ageOutParam);
        }
        return ResponseData.success("批量盘点保存成功！");
    }

    private String dealBatchSaveCheckErrorList(List<OverseasWarehouseManageCheckParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, OverseasWarehouseManageCheckParam.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("批量盘点-保存异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("批量盘点-保存异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    /**
     * 调用ERP盘点接口
     * @param record
     * @return
     */
    ResponseData syncCheckToErp(OverseasWarehouseManageRecord record){
        OverseasWarehouseManageRecord updateRecord = new OverseasWarehouseManageRecord();
        updateRecord.setId(record.getId());
        updateRecord.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());

        JSONArray Jarr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("FBillNo", record.getInOrder() == null ? record.getOutOrder() : record.getInOrder());
        object.put("FName", "海外仓盘点");
        object.put("FNote", record.getOperateType());
//        object.put("FCreatorId", loginUserInfo.getUserAccount());
        object.put("FCreatorId", "S20180229");
        //库存组织
        object.put("FStockOrgId", record.getOutOrg());
        //Inventory：按当前日期即时库存盘点 CloseDate：根据指定日期的库存盘点，同步传入一个截止日期。
        object.put("FBackUpType", "Inventory");
//        object.put("FBackUpType", "CloseDate");
//        object.put("FBackUpDATE", "2022-04-30");
        object.put("FZeroStockInCount", "1");
        object.put("FCheckQtyDefault", "0");

        JSONArray detailJarr = new JSONArray();
        JSONObject detailObj = new JSONObject();
        //仓库编码
        detailObj.put("FStockId", record.getOutOrg());
        detailObj.put("FMaterialId", record.getMaterialCode());
//        detailObj.put("FMaterialId", "COF210004");
        detailObj.put("FCountQty", record.getChangeInventoryQuantity());
        detailObj.put("F_REQUIRETEAM", record.getTeam());
        detailObj.put("F_REQUIREDEP", record.getDepartment());
        detailJarr.add(detailObj);
        object.put("FEntity", detailJarr);
        Jarr.add(object);
        JSONObject obj = syncToErpConsumer.endingInventory(Jarr);
        updateRecord.setSyncErpTime(new Date());
        if (obj.getString("Code") != null && obj.getString("Code").equals("0")) {
            updateRecord.setSyncErpStatus(BaseSyncStatusEnum.SUCCESS.getCode());
            recordService.updateById(updateRecord);
            return ResponseData.success();
        } else {
            recordService.updateById(updateRecord);
            String responseMsg = null;
            JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
            if (CollectionUtil.isNotEmpty(responseResult)) {
                responseMsg = responseResult.getJSONObject(0).getString("SubMessage");

            }
            log.error("同步erp盘点接口失败！异常信息[{}]", obj.getString("Message") + responseMsg);
            return ResponseData.error("同步erp盘点接口失败！" + obj.getString("Message") + responseMsg);
        }
    }

    /**
     * 调用ERP销售出库接口
     * @param idList
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public String syncOutWarehouseToErp(List<BigDecimal> idList){

        String errorMsg = null;
        if (CollectionUtil.isEmpty(idList)) {
            errorMsg = "ID字段集合不能为空！";
            return errorMsg;
        }

        String operateType = OperateTypeEnum.RAKUTEN.getName();

        long start = System.currentTimeMillis();
        List<List<BigDecimal>> lists = ListUtil.split(idList,500);
        for (List<BigDecimal> lst: lists) {
            log.info("乐天海外仓出库单同步到K3销售出库单开始");
            List<OverseasWarehouseManageRecord> recordList = this.baseMapper.getSyncRecordList(lst, operateType);
            if (CollectionUtil.isNotEmpty(recordList)) {
                for (OverseasWarehouseManageRecord record : recordList) {
                    try {
                        JSONArray Jarr = new JSONArray();
                        JSONObject object = new JSONObject();
                        object.put("FDate", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));
                        object.put("FCreatorId", "S20190109");
                        object.put("FSaleOrgId", record.getOutOrg());
                        object.put("FCustomerID", "店铺虚拟客户");
                        object.put("FCorrespondOrgId", "店铺虚拟客户");
                        object.put("FStockOrgId", record.getOutOrg());
                        object.put("FPayerID", "店铺虚拟客户");

                        //注(产品需求)：出库的时候判断出来的出货店铺放在备注字段处
                        List<OverseasWarehouseManageRecord> dlist = this.baseMapper.getSyncList(lst, record.getOutOrg());
                        object.put("FEntity", dlist);
                        Jarr.add(object);
                        JSONObject obj = syncToErpConsumer.saleOutStock(Jarr);
                        LambdaUpdateWrapper<OverseasWarehouseManageRecord> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, operateType)
                                .in(OverseasWarehouseManageRecord :: getId, lst)
                                .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date());

                        if (obj.getString("Code") != null && obj.getString("Code").equals("0")) {
                            updateWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                            log.info("乐天海外仓出库同步k3销售出库成功！操作记录ID[{}]", lst);
                        } else {
                            String message = obj.getString("Message");
                            if(StringUtils.isNotEmpty(message)){
                                errorMsg = errorMsg + message;
                            }
                            JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
                            String responseMsg = null;
                            if (CollectionUtil.isNotEmpty(responseResult)) {
                                responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
                                if(StringUtils.isNotEmpty(responseMsg)){
                                    errorMsg = errorMsg + responseMsg;
                                }
                            }
                            updateWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                            log.error("乐天海外仓出库同步k3销售出库接口失败！操作记录ID[{}]，异常信息[{}]", lst, obj.getString("Msg"));
                        }
                        recordService.update(null, updateWrapper);
                    } catch (Exception e) {
                        log.error("调用k3销售出库API异常,信息[{}]！", e.getMessage());
                    }
                }
            }
        }
        log.info("乐天海外仓出库单同步到K3销售出库单结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return errorMsg;
    }

    /**
     * 乐天海外仓出库单同步到K3销售出库单(定时)
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData syncAbnormalOutWarehouseToErp(){

        /*LambdaQueryWrapper<OverseasWarehouseManageRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OverseasWarehouseManageRecord::getSyncErpStatus,BaseSyncStatusEnum.ERROR.getCode())
                .eq(OverseasWarehouseManageRecord::getOperateType,OperateTypeEnum.RAKUTEN.getName())
                .orderByDesc(OverseasWarehouseManageRecord::getCreateTime);
        List<OverseasWarehouseManageRecord> recordList = recordService.list(queryWrapper);

        if(CollectionUtil.isNotEmpty(recordList)){
            log.info("乐天海外仓出库单同步k3销售出库单(定时)开始！");
            //取出字段ID的列表
            List<BigDecimal> idList = recordList.stream().map(p -> p.getId()).collect(Collectors.toList());
            //1、乐天海外仓出库->调用ERP销售出库
            String errorMsg = syncOutWarehouseToErp(idList);
            if(errorMsg != null){
                log.error("乐天海外仓出库单同步k3销售出库单(定时)存在异常！[{}]",errorMsg);
                return ResponseData.error("乐天海外仓出库单同步k3销售出库单(定时)存在异常！"+errorMsg);
            }
            return ResponseData.success("乐天海外仓出库单同步k3销售出库单(定时)同步成功！");
        }*/
        return ResponseData.success("乐天海外仓出库单同步k3销售出库单(定时)同步成功！");
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData warehouseNameBySiteSelect(String site, String warehouseName, String owState) {
        return ResponseData.success(this.baseMapper.warehouseNameBySiteSelect(site, warehouseName, owState));
    }

    @Override
    @DataSource(name = "EBMS")
    public List<ValidateLabelResult> validateLabel(ValidateLabelParam param) {
        return this.baseMapper.selectTbComShippingLabel(param);
    }

    @Override
    @DataSource(name = "erpcloud")
    public List<ValidateLabelResult> validateK3Label(ValidateLabelParam param) {
        return this.baseMapper.validateK3Label(param);
    }

    @Override
    @DataSource(name = "erpcloud")
    public String RakutenTeam(CustomerMaterialInfoParam param) {
        return this.baseMapper.selectRakutenTeam(param);
    }

    @Override
    public ResponseData generateOrder() {
        log.info("生成供应链订单号任务开始");
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String azycrkPre = env + "_" + OrderTypePreEnum.AZYCRK.getCode() + pureDate;
        String kzhbrkPre = env + "_" + OrderTypePreEnum.KZHBRK.getCode() + pureDate;
        String tzhbrkPre = env + "_" + OrderTypePreEnum.TZHBRK.getCode() + pureDate;
        String pdrkPre = env + "_" + OrderTypePreEnum.PDRK.getCode() + pureDate;
        String ckqdPre = env + "_" + OrderTypePreEnum.CKQD.getCode() + pureDate;
        String kzhbckPre = env + "_" + OrderTypePreEnum.KZHBCK.getCode() + pureDate;
        String tzhbckPre = env + "_" + OrderTypePreEnum.TZHBCK.getCode() + pureDate;
        String pdckPre = env + "_" + OrderTypePreEnum.PDCK.getCode() + pureDate;
        /*String bgdPre = env + "_" + OrderTypePreEnum.BGD.getCode() + pureDate;
        String qgdPre = env + "_" + OrderTypePreEnum.QGD.getCode() + pureDate;*/
        this.createLogisticsOrder(azycrkPre, OrderTypePreEnum.AZYCRK, 5);
        this.createLogisticsOrder(kzhbrkPre, OrderTypePreEnum.KZHBRK, 5);
        this.createLogisticsOrder(tzhbrkPre, OrderTypePreEnum.TZHBRK, 5);
        this.createLogisticsOrder(pdrkPre, OrderTypePreEnum.PDRK, 5);
        this.createLogisticsOrder(ckqdPre, OrderTypePreEnum.CKQD, 5);
        this.createLogisticsOrder(kzhbckPre, OrderTypePreEnum.KZHBCK, 5);
        this.createLogisticsOrder(tzhbckPre, OrderTypePreEnum.TZHBCK, 5);
        this.createLogisticsOrder(pdckPre, OrderTypePreEnum.PDCK, 5);
        /*this.createLogisticsOrder(bgdPre, OrderTypePreEnum.BGD, 4);
        this.createLogisticsOrder(qgdPre, OrderTypePreEnum.QGD, 4);*/
        log.info("生成供应链订单号任务结束");
        return ResponseData.success();
    }

    @Override
    public ResponseData handleGenerateOrder(String orderType, String key, Integer noLength) {
        log.info("生成海外仓订单号任务开始，类型[{}]，key[{}]", orderType, key);
        if(StringUtils.isNotEmpty(key)){
            String systemKey = "order595JC";
            if(!systemKey.equals(key)){
                return ResponseData.error("密钥错误！");
            }
        }
        OrderTypePreEnum orderTypePreEnum = OrderTypePreEnum.getEnumByValue(orderType);
        if(orderTypePreEnum == null){
            return ResponseData.error("不存在此订单类型！");
        }
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String redisKey = env + "_" + orderTypePreEnum.getCode() + pureDate;

        //查询缓存是否有值，没有则根据系统创建一条最新的订单
        if(!redisTemplate.hasKey(redisKey)){
            String nowOrder = recordService.getNowOrder(orderTypePreEnum.getCode() + pureDate);
            if(nowOrder == null){
                //没订单
                this.createLogisticsOrder(redisKey, orderTypePreEnum, noLength);
            }else{
                Integer newNum = Integer.parseInt(nowOrder.substring(nowOrder.length() - noLength)) + 1;
                String newNumStr = StringUtils.leftPad(newNum.toString(), noLength, "0");
                redisTemplate.boundValueOps(key).set(key.substring(key.indexOf("_") + 1) + newNumStr, Duration.ofSeconds(expiresInValue));
            }
        }
        log.info("生成海外仓订单号任务结束");
        return ResponseData.success();
    }

    /**
     * 获取redis海外仓订单号
     * @param redisKey env_编码_yyyymmdd
     * @param orderType
     * @param noLength 流水号长度
     * @return
     */

    @Override
    public String getLogisticsOrder(String redisKey, OrderTypePreEnum orderType, Integer noLength) {
        if(StringUtils.isEmpty(redisKey)){
            return null;
        }
        //流水号长度必传
        if(noLength == null){
            return null;
        }
        if(redisTemplate.hasKey(redisKey)){
            //订单号存在且未过期，直接从缓存取
            String order = (String) redisTemplate.boundValueOps(redisKey).get();
            Integer newNum = Integer.parseInt(order.substring(order.length() - noLength)) + 1;
            String newNumStr = StringUtils.leftPad(newNum.toString(), noLength, "0");
            redisTemplate.boundValueOps(redisKey).set(redisKey.substring(redisKey.indexOf("_") + 1) + newNumStr, Duration.ofSeconds(expiresInValue));
            return order;
        }
        //创建订单号
        createLogisticsOrder(redisKey, orderType, noLength);
        return getLogisticsOrder(redisKey, orderType, noLength);
    }

    /**
     * 创建redis海外仓订单号
     * @param redisKey env_编码_yyyymmdd
     * @param orderType
     * @param noLength 流水号长度
     */
    public void createLogisticsOrder(String redisKey, OrderTypePreEnum orderType, Integer noLength) {
        String orderPre = redisKey.substring(redisKey.indexOf("_") + 1);
        String nowOrder = null;
        switch(orderType) {
            case AZYCRK:
            case KZHBRK:
            case TZHBRK:
            case PDRK:
            case CKQD:
            case KZHBCK:
            case TZHBCK:
            case PDCK:
            case KZHB:
            case TZHB:
            case PD:
                nowOrder = recordService.getNowOrder(orderPre);
                break;
            /*case BGD:
                String nowBgdOrder = applyService.getNowBgdOrder(orderPre.substring(3));
                if(StringUtils.isNotBlank(nowBgdOrder)){
                    nowOrder = orderType.getCode() + nowBgdOrder;
                }
                break;
            case QGD:
                String nowQgdOrder = clearanceService.getNowQgdOrder(orderPre.substring(3));
                if(StringUtils.isNotBlank(nowQgdOrder)){
                    nowOrder = orderType.getCode() + nowQgdOrder;
                }
                break;*/
        }

        if(nowOrder == null){
            //没订单，设置redis
            String defaultNum = StringUtils.leftPad("1", noLength, "0");
            redisTemplate.boundValueOps(redisKey).set(orderPre + defaultNum, Duration.ofSeconds(expiresInValue));
        }else{
            Integer newNum = Integer.parseInt(nowOrder.substring(nowOrder.length() - noLength)) + 1;
            String newNumStr = StringUtils.leftPad(newNum.toString(), noLength, "0");
            redisTemplate.boundValueOps(redisKey).set(orderPre + newNumStr, Duration.ofSeconds(expiresInValue));
        }
    }

    @DataSource(name = "logistics")
    @Override
    public OverseasWarehouseManage getManageById(Serializable id){
        return this.getBaseMapper().selectById(id);
    }

    @DataSource(name = "logistics")
    @Override
    public List<OverseasWarehouseManage> getOverseasWarehouseManageList() {
        return this.baseMapper.selectList(null);
    }

    @DataSource(name = "logistics")
    @Override
    public OverseasWarehouseManage getWarehouseManage(OverseasWarehouseManage param){
        QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("PLATFORM", param.getPlatform())
                .eq("SYS_SHOPS_NAME", param.getSysShopsName())
                .eq("SYS_SITE", param.getSysSite())
                .eq("WAREHOUSE_NAME", param.getWarehouseName())
                .eq("SKU", param.getSku())
                .eq(StringUtils.isNotBlank(param.getMaterialCode()), "MATERIAL_CODE", param.getMaterialCode())
                .likeLeft("FN_SKU", param.getFnSku());
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 换标调用K3其他出库
     * @param id
     * @return
     */
    @DataSource(name = "logistics")
    public ResponseData syncChangeOutStockToErp(BigDecimal id){
        OverseasWarehouseManageRecord outManageRecord = recordService.getById(id);
        if(outManageRecord == null){
            return ResponseData.error("换标出库记录不存在！");
        }
        JSONArray Jarr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("FBillNo", outManageRecord.getOutOrder());//用MC生成的换标出库单号
        object.put("FCreatorId", "S20190109");//创建人
        object.put("FStockOrgId", outManageRecord.getOutOrg());
        object.put("FStockDirect", "普通");
        object.put("FDate", DateUtil.format(outManageRecord.getCreateTime(), "yyyy/MM/dd"));
        object.put("FCustId", "店铺虚拟客户");
        object.put("FBizType", "物料领用");
        object.put("FOwnerTypeIdHead", "业务组织");
        String remark = StringUtils.isBlank(outManageRecord.getRemark()) ? "海外仓换标出库" : "海外仓换标出库-MC备注：" + outManageRecord.getRemark();
        object.put("FNote", remark);
        object.put("FBaseCurrId", "PRE001");

        List<Map<String, Object>> detailList = new ArrayList<>();
        Map<String, Object> detail = new HashMap<>();
        detail.put("FMaterialId", outManageRecord.getMaterialCode());
        detail.put("FUnitID", "Pcs");
        detail.put("FQty", outManageRecord.getChangeInventoryQuantity());
        detail.put("FStockId", outManageRecord.getWarehouseName());
        detail.put("FOwnerTypeId", "业务组织");
        detail.put("FOwnerId", outManageRecord.getOutOrg());
        detail.put("FStockStatusId", "KCZT01_SYS");
        detail.put("FKeeperTypeId", "业务组织");
        detail.put("FDistribution", "True");
        detail.put("FKeeperId", outManageRecord.getOutOrg());
        detail.put("F_REQUIRETEAM", outManageRecord.getTeam());
        detail.put("F_REQUIREDEP", outManageRecord.getDepartment());
        detailList.add(detail);
        object.put("FEntity", detailList);
        Jarr.add(object);
        log.info("换标出库同步ERP其他出库单入参，[{}]", Jarr);
        JSONObject obj = syncToErpConsumer.outStock(Jarr);

        OverseasWarehouseManageRecord update = new OverseasWarehouseManageRecord();
        update.setId(id);
        update.setSyncErpTime(DateUtil.date());
        if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
            String responseMsg = null;
            JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
            if (CollectionUtil.isNotEmpty(responseResult)) {
                responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
            }
            log.error("换标出库同步ERP其他出库单失败，异常信息[{}]", obj.getString("Message") + "，详细信息:" + responseMsg);
            update.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
            recordService.updateById(update);
            return ResponseData.error("换标出库同步ERP其他出库单失败！异常信息:" + obj.getString("Message") + "，详细信息:" + responseMsg);
        } else {
            update.setSyncErpStatus(BaseSyncStatusEnum.SUCCESS.getCode());
            recordService.updateById(update);
            return ResponseData.success();
        }
    }

    /**
     * 换标调用K3其他入库
     * @param id
     * @return
     */
    @DataSource(name = "logistics")
    public ResponseData syncChangeInStockToErp(BigDecimal id){
        OverseasWarehouseManageRecord inManageRecord = recordService.getById(id);
        if(inManageRecord == null){
            return ResponseData.error("换标入库记录不存在！");
        }

        JSONArray Jarr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("FBillNo", inManageRecord.getInOrder());//用MC生成的换标入库单号
        object.put("FCreatorId", "S20190109");//创建人
        object.put("FBillTypeID", "标准其他入库单");
        object.put("FStockOrgId", inManageRecord.getInOrg());
        object.put("FStockDirect", "普通");
        object.put("FDate", DateUtil.format(inManageRecord.getCreateTime(), "yyyy/MM/dd"));
//        object.put("FDEPTID", inManageRecord.getDepartment());
        object.put("FDEPTID", "BM028");//物流部
        object.put("FOwnerTypeIdHead", "业务组织");
        object.put("FOwnerIdHead", inManageRecord.getInOrg());
        String remark = StringUtils.isBlank(inManageRecord.getRemark()) ? "海外仓换标入库" : "海外仓换标入库-MC备注：" + inManageRecord.getRemark();
        object.put("FNote", remark);
        object.put("FBaseCurrId", "PRE001");
        object.put("FSUPPLIERID", "002");

        List<Map<String, Object>> detailList = new ArrayList<>();
        Map<String, Object> detail = new HashMap<>();
        detail.put("FMaterialId", inManageRecord.getMaterialCode());
        detail.put("FUnitID", "Pcs");
        detail.put("FQty", inManageRecord.getChangeInventoryQuantity());
        detail.put("FStockId", inManageRecord.getWarehouseName());
        detail.put("FOwnerTypeId", "业务组织");
        detail.put("FOwnerId", inManageRecord.getInOrg());
        detail.put("FStockStatusId", "KCZT01_SYS");
        detail.put("FKeeperTypeId", "保管者类型");
        detail.put("FDistribution", "True");
        detail.put("FKeeperId", inManageRecord.getInOrg());
        detail.put("F_REQUIRETEAM", inManageRecord.getTeam());
        detail.put("F_REQUIREDEP", inManageRecord.getDepartment());
        detailList.add(detail);
        object.put("FEntity", detailList);
        Jarr.add(object);
        log.info("换标入库同步ERP其他入库单入参，[{}]", Jarr);
        JSONObject obj = syncToErpConsumer.inStock(Jarr);
        OverseasWarehouseManageRecord update = new OverseasWarehouseManageRecord();
        update.setId(id);
        update.setSyncErpTime(DateUtil.date());
        if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
            String responseMsg = null;
            JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
            if (CollectionUtil.isNotEmpty(responseResult)) {
                responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
            }
            log.error("换标入库同步ERP其他入库单失败，异常信息[{}]", obj.getString("Message") + "，详细信息:" + responseMsg);
            update.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
            recordService.updateById(update);
            return ResponseData.error("换标入库同步ERP其他入库单失败！异常信息:" + obj.getString("Message") + "，详细信息:" + responseMsg);
        } else {
            update.setSyncErpStatus(BaseSyncStatusEnum.SUCCESS.getCode());
            recordService.updateById(update);
            return ResponseData.success();
        }
    }

    /**
     * 换标批量调用K3其他出库
     * @param outIdList
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData syncBatchChangeOutStockToErp(List<BigDecimal> outIdList){
        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("OUT_ORG")
                .eq("OPERATE_TYPE", OperateTypeEnum.CHANGE.getName())
                .in("ID", outIdList).groupBy("OUT_ORG");
        List<OverseasWarehouseManageRecord> recordList = recordService.list(recordWrapper);
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("换标出库记录不存在！");
        }
        StringBuffer errorMsg = new StringBuffer();
        for (OverseasWarehouseManageRecord outManageRecord : recordList) {
            LambdaUpdateWrapper<OverseasWarehouseManageRecord> updateRecordWrapper = new LambdaUpdateWrapper<>();
            try {
                //K3异常信息
                String k3ErrorMsg = null;
                JSONArray Jarr = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("FCreatorId", "S20190109");//创建人
                object.put("FStockOrgId", outManageRecord.getOutOrg());
                object.put("FStockDirect", "普通");
                object.put("FDate", DateUtil.format(DateUtil.date(), "yyyy/MM/dd"));
                object.put("FCustId", "店铺虚拟客户");
                object.put("FBizType", "物料领用");
                object.put("FOwnerTypeIdHead", "业务组织");
                object.put("FBaseCurrId", "PRE001");

                //换标其他出库单明细
                QueryWrapper<OverseasWarehouseManageRecord> detailRecordWrapper = new QueryWrapper<>();
                detailRecordWrapper.eq("OPERATE_TYPE", OperateTypeEnum.CHANGE.getName())
                        .eq("OUT_ORG", outManageRecord.getOutOrg())
                        .in("ID", outIdList);
                List<OverseasWarehouseManageRecord> detailRecordList = recordService.list(detailRecordWrapper);
                List<Map<String, Object>> detailList = new ArrayList<>();
                for (OverseasWarehouseManageRecord detailRecord : detailRecordList) {
                    Map<String, Object> detail = new HashMap<>();
                    detail.put("FMaterialId", detailRecord.getMaterialCode());
                    detail.put("FUnitID", "Pcs");
                    detail.put("FQty", detailRecord.getChangeInventoryQuantity());
                    detail.put("FStockId", detailRecord.getWarehouseName());
                    detail.put("FOwnerTypeId", "业务组织");
                    detail.put("FOwnerId", detailRecord.getOutOrg());
                    detail.put("FStockStatusId", "KCZT01_SYS");
                    detail.put("FKeeperTypeId", "业务组织");
                    detail.put("FDistribution", "True");
                    detail.put("FKeeperId", detailRecord.getOutOrg());
                    detail.put("F_REQUIRETEAM", detailRecord.getTeam());
                    detail.put("F_REQUIREDEP", detailRecord.getDepartment());
                    //备注用MC生成的换标单号
                    String remark = StringUtils.isBlank(detailRecord.getRemark()) ? "MC单号：" + detailRecord.getOutOrder() : "MC单号：" + detailRecord.getOutOrder() + "，MC备注：" + detailRecord.getRemark();
                    detail.put("F_BSC_SubRemark1", remark);
                    detailList.add(detail);
                }
                object.put("FEntity", detailList);
                Jarr.add(object);
                log.info("换标出库同步ERP其他出库单入参，[{}]", Jarr);
                JSONObject obj = syncToErpConsumer.outStock(Jarr);
                updateRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrg, outManageRecord.getOutOrg())
                        .eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                        .in(OverseasWarehouseManageRecord :: getId, outIdList)
                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date());
                if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
                    String responseMsg = null;
                    JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
                    if (CollectionUtil.isNotEmpty(responseResult)) {
                        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
                    }
                    k3ErrorMsg = obj.getString("Message") + "，详细信息:" + responseMsg;
                    errorMsg.append(k3ErrorMsg);
                    log.error("换标出库同步ERP其他出库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", outManageRecord.getOutOrg(), JSONObject.toJSON(outIdList), k3ErrorMsg);
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                } else {
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                }
            } catch (Exception e){
                updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                log.error("盘点盘亏同步ERP其他出库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", outManageRecord.getOutOrg(), JSONObject.toJSON(outIdList), e.getMessage());
            }
            recordService.update(null, updateRecordWrapper);
        }
        if(StringUtils.isEmpty(errorMsg)){
            return ResponseData.success();
        }
        return ResponseData.error(errorMsg.toString());
    }

    /**
     * 换标批量调用K3其他入库
     * @param inIdList
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData syncBatchChangeInStockToErp(List<BigDecimal> inIdList){
        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("IN_ORG")
                .eq("OPERATE_TYPE", OperateTypeEnum.CHANGE.getName())
                .in("ID", inIdList).groupBy("IN_ORG");
        List<OverseasWarehouseManageRecord> recordList = recordService.list(recordWrapper);
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("换标入库记录不存在！");
        }
        StringBuffer errorMsg = new StringBuffer();
        for (OverseasWarehouseManageRecord inManageRecord : recordList) {
            LambdaUpdateWrapper<OverseasWarehouseManageRecord> updateRecordWrapper = new LambdaUpdateWrapper<>();
            try {
                //K3异常信息
                String k3ErrorMsg = null;
                JSONArray Jarr = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("FCreatorId", "S20190109");//创建人
                object.put("FBillTypeID", "标准其他入库单");
                object.put("FStockOrgId", inManageRecord.getInOrg());
                object.put("FStockDirect", "普通");
                object.put("FDate", DateUtil.format(DateUtil.date(), "yyyy/MM/dd"));
                object.put("FDEPTID", "BM028");//物流部
                object.put("FOwnerTypeIdHead", "业务组织");
                object.put("FOwnerIdHead", inManageRecord.getInOrg());
                object.put("FBaseCurrId", "PRE001");
                object.put("FSUPPLIERID", "002");

                //盘点其他入库单明细
                QueryWrapper<OverseasWarehouseManageRecord> detailRecordWrapper = new QueryWrapper<>();
                detailRecordWrapper.eq("OPERATE_TYPE", OperateTypeEnum.CHANGE.getName())
                        .eq("IN_ORG", inManageRecord.getInOrg())
                        .in("ID", inIdList);
                List<OverseasWarehouseManageRecord> detailRecordList = recordService.list(detailRecordWrapper);
                List<Map<String, Object>> detailList = new ArrayList<>();
                for (OverseasWarehouseManageRecord detailRecord : detailRecordList) {
                    Map<String, Object> detail = new HashMap<>();
                    detail.put("FMaterialId", detailRecord.getMaterialCode());
                    detail.put("FUnitID", "Pcs");
                    detail.put("FQty", detailRecord.getChangeInventoryQuantity());
                    detail.put("FStockId", detailRecord.getWarehouseName());
                    detail.put("FOwnerTypeId", "业务组织");
                    detail.put("FOwnerId", detailRecord.getInOrg());
                    detail.put("FStockStatusId", "KCZT01_SYS");
                    detail.put("FKeeperTypeId", "保管者类型");
                    detail.put("FDistribution", "True");
                    detail.put("FKeeperId", detailRecord.getInOrg());
                    detail.put("F_REQUIRETEAM", detailRecord.getTeam());
                    detail.put("F_REQUIREDEP", detailRecord.getDepartment());
                    //备注用MC生成的换标单号
                    String remark = StringUtils.isBlank(detailRecord.getRemark()) ? "MC单号：" + detailRecord.getInOrder() : "MC单号：" + detailRecord.getInOrder() + "，MC备注：" + detailRecord.getRemark();
                    detail.put("FEntryNote", remark);
                    detailList.add(detail);
                }
                object.put("FEntity", detailList);
                Jarr.add(object);
                log.info("换标入库同步ERP其他入库单入参，[{}]", Jarr);
                JSONObject obj = syncToErpConsumer.inStock(Jarr);

                updateRecordWrapper.eq(OverseasWarehouseManageRecord :: getInOrg, inManageRecord.getInOrg())
                        .eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                        .in(OverseasWarehouseManageRecord :: getId, inIdList)
                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date());
                if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
                    String responseMsg = null;
                    JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
                    if (CollectionUtil.isNotEmpty(responseResult)) {
                        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
                    }
                    k3ErrorMsg = obj.getString("Message") + "，详细信息:" + responseMsg;
                    errorMsg.append(k3ErrorMsg);
                    log.error("换标入库同步ERP其他入库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", inManageRecord.getInOrg(), JSONObject.toJSON(inIdList), k3ErrorMsg);
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                } else {
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                }
            } catch (Exception e){
                updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                log.error("换标入库同步ERP其他入库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", inManageRecord.getInOrg(), JSONObject.toJSON(inIdList), e.getMessage());
            }
            recordService.update(null, updateRecordWrapper);
        }
        if(StringUtils.isEmpty(errorMsg)){
            return ResponseData.success();
        }
        return ResponseData.error(errorMsg.toString());
    }

    /**
     * 盘点调用K3其他出库
     * @param id
     * @return
     */
    @DataSource(name = "logistics")
    public ResponseData syncCheckOutStockToErp(BigDecimal id){
        OverseasWarehouseManageRecord outManageRecord = recordService.getById(id);
        if(outManageRecord == null){
            return ResponseData.error("盘点盘亏记录不存在！");
        }
        JSONArray Jarr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("FBillNo", outManageRecord.getOutOrder());//用MC生成的盘点盘亏单号
//        object.put("FCreatorId", outManageRecord.getCreateUser().substring(0, outManageRecord.getCreateUser().indexOf("_")));//创建人
        object.put("FCreatorId", "S20190109");//创建人
        object.put("FStockOrgId", outManageRecord.getOutOrg());
        object.put("FStockDirect", "普通");
        object.put("FDate", DateUtil.format(DateUtil.date(), "yyyy/MM/dd"));
        object.put("FCustId", "店铺虚拟客户");
        object.put("FBizType", "物料领用");
        object.put("FOwnerTypeIdHead", "业务组织");
        String remark = StringUtils.isBlank(outManageRecord.getRemark()) ? "海外仓盘亏出库" : "海外仓盘亏出库-MC备注：" + outManageRecord.getRemark();
        object.put("FNote", remark);
        object.put("FBaseCurrId", "PRE001");

        List<Map<String, Object>> detailList = new ArrayList<>();
        Map<String, Object> detail = new HashMap<>();
        detail.put("FMaterialId", outManageRecord.getMaterialCode());
        detail.put("FUnitID", "Pcs");
        detail.put("FQty", outManageRecord.getChangeInventoryQuantity());
        detail.put("FStockId", outManageRecord.getWarehouseName());
        detail.put("FOwnerTypeId", "业务组织");
        detail.put("FOwnerId", outManageRecord.getOutOrg());
        detail.put("FStockStatusId", "KCZT01_SYS");
        detail.put("FKeeperTypeId", "业务组织");
        detail.put("FDistribution", "True");
        detail.put("FKeeperId", outManageRecord.getOutOrg());
        detail.put("F_REQUIRETEAM", outManageRecord.getTeam());
        detail.put("F_REQUIREDEP", outManageRecord.getDepartment());
        //备注用MC生成的盘点盘亏单号
        /*String remark = StringUtils.isBlank(outManageRecord.getRemark()) ? "MC单号：" + outManageRecord.getOutOrder() : "MC单号：" + outManageRecord.getOutOrder() + "，MC备注：" + outManageRecord.getRemark();
        detail.put("F_BSC_SubRemark1", remark);*/
        detailList.add(detail);
        object.put("FEntity", detailList);
        Jarr.add(object);
        log.info("盘点盘亏同步ERP其他出库单入参，[{}]", Jarr);
        JSONObject obj = syncToErpConsumer.outStock(Jarr);

        OverseasWarehouseManageRecord update = new OverseasWarehouseManageRecord();
        update.setId(id);
        update.setSyncErpTime(DateUtil.date());
        if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
            String responseMsg = null;
            JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
            if (CollectionUtil.isNotEmpty(responseResult)) {
                responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
            }
            log.error("盘点盘亏同步ERP其他出库单失败！MC单号[{}]，异常信息[{}]", outManageRecord.getOutOrder(), obj.getString("Message") + "，详细信息:" + responseMsg);
            update.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
            recordService.updateById(update);
            return ResponseData.error("盘点盘亏同步ERP其他出库单失败！MC单号：" + outManageRecord.getOutOrder() + "，异常信息:" + obj.getString("Message") + "，详细信息:" + responseMsg);
        } else {
            update.setSyncErpStatus(BaseSyncStatusEnum.SUCCESS.getCode());
            recordService.updateById(update);
            return ResponseData.success();
        }
    }

    /**
     * 盘点调用K3其他入库
     * @param id
     * @return
     */
    @DataSource(name = "logistics")
    public ResponseData syncCheckInStockToErp(BigDecimal id){
        OverseasWarehouseManageRecord inManageRecord = recordService.getById(id);
        if(inManageRecord == null){
            return ResponseData.error("盘点盘盈记录不存在！");
        }

        JSONArray Jarr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("FBillNo", inManageRecord.getInOrder());//用MC生成的盘点盘盈单号
//        object.put("FCreatorId", inManageRecord.getCreateUser().substring(0, inManageRecord.getCreateUser().indexOf("_")));//创建人
        object.put("FCreatorId", "S20190109");//创建人
        object.put("FBillTypeID", "标准其他入库单");
        object.put("FStockOrgId", inManageRecord.getInOrg());
        object.put("FStockDirect", "普通");
        object.put("FDate", DateUtil.format(DateUtil.date(), "yyyy/MM/dd"));
        object.put("FDEPTID", "BM028");//物流部
        object.put("FOwnerTypeIdHead", "业务组织");
        object.put("FOwnerIdHead", inManageRecord.getInOrg());
        String remark = StringUtils.isBlank(inManageRecord.getRemark()) ? "海外仓盘盈入库" : "海外仓盘盈入库-MC备注：" + inManageRecord.getRemark();
        object.put("FNote", remark);
        object.put("FBaseCurrId", "PRE001");
        object.put("FSUPPLIERID", "002");

        List<Map<String, Object>> detailList = new ArrayList<>();
        Map<String, Object> detail = new HashMap<>();
        detail.put("FMaterialId", inManageRecord.getMaterialCode());
        detail.put("FUnitID", "Pcs");
        detail.put("FQty", inManageRecord.getChangeInventoryQuantity());
        detail.put("FStockId", inManageRecord.getWarehouseName());
        detail.put("FOwnerTypeId", "业务组织");
        detail.put("FOwnerId", inManageRecord.getInOrg());
        detail.put("FStockStatusId", "KCZT01_SYS");
        detail.put("FKeeperTypeId", "保管者类型");
        detail.put("FDistribution", "True");
        detail.put("FKeeperId", inManageRecord.getInOrg());
        detail.put("F_REQUIRETEAM", inManageRecord.getTeam());
        detail.put("F_REQUIREDEP", inManageRecord.getDepartment());
        //备注用MC生成的盘点盘盈单号
        /*String remark = StringUtils.isBlank(inManageRecord.getRemark()) ? "MC单号：" + inManageRecord.getInOrder() : "MC单号：" + inManageRecord.getInOrder() + "，MC备注：" + inManageRecord.getRemark();
        detail.put("FEntryNote", remark);*/
        detailList.add(detail);
        object.put("FEntity", detailList);
        Jarr.add(object);
        log.info("盘点盘盈同步ERP其他入库单入参，[{}]", Jarr);
        JSONObject obj = syncToErpConsumer.inStock(Jarr);
        OverseasWarehouseManageRecord update = new OverseasWarehouseManageRecord();
        update.setId(id);
        update.setSyncErpTime(DateUtil.date());
        if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
            String responseMsg = null;
            JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
            if (CollectionUtil.isNotEmpty(responseResult)) {
                responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
            }
            log.error("盘点盘盈同步ERP其他入库单失败，异常信息[{}]", obj.getString("Message") + "，详细信息:" + responseMsg);
            update.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
            recordService.updateById(update);
            return ResponseData.error("盘点盘盈同步ERP其他入库单失败！异常信息:" + obj.getString("Message") + "，详细信息:" + responseMsg);
        } else {
            update.setSyncErpStatus(BaseSyncStatusEnum.SUCCESS.getCode());
            recordService.updateById(update);
            return ResponseData.success();
        }
    }

    /**
     * 盘点批量调用K3其他出库
     * @param outIdList
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData syncBatchCheckOutStockToErp(List<BigDecimal> outIdList){
        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("OUT_ORG")
                .eq("BUSINESS_TYPE", OverseasBusinessTypeEnum.CHECK_SUB.getName())
                .in("ID", outIdList).groupBy("OUT_ORG");
        List<OverseasWarehouseManageRecord> recordList = recordService.list(recordWrapper);
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("盘点盘亏记录不存在！");
        }
        StringBuffer errorMsg = new StringBuffer();
        for (OverseasWarehouseManageRecord outManageRecord : recordList) {
            LambdaUpdateWrapper<OverseasWarehouseManageRecord> updateRecordWrapper = new LambdaUpdateWrapper<>();
            try {
                //K3异常信息
                String k3ErrorMsg = null;
                JSONArray Jarr = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("FCreatorId", "S20190109");//创建人
                object.put("FStockOrgId", outManageRecord.getOutOrg());
                object.put("FStockDirect", "普通");
                object.put("FDate", DateUtil.format(DateUtil.date(), "yyyy/MM/dd"));
                object.put("FCustId", "店铺虚拟客户");
                object.put("FBizType", "物料领用");
                object.put("FOwnerTypeIdHead", "业务组织");
                object.put("FBaseCurrId", "PRE001");

                //盘点其他出库单明细
                QueryWrapper<OverseasWarehouseManageRecord> detailRecordWrapper = new QueryWrapper<>();
                detailRecordWrapper.eq("BUSINESS_TYPE", OverseasBusinessTypeEnum.CHECK_SUB.getName())
                        .eq("OUT_ORG", outManageRecord.getOutOrg())
                        .in("ID", outIdList);
                List<OverseasWarehouseManageRecord> detailRecordList = recordService.list(detailRecordWrapper);
                List<Map<String, Object>> detailList = new ArrayList<>();
                for (OverseasWarehouseManageRecord detailRecord : detailRecordList) {
                    Map<String, Object> detail = new HashMap<>();
                    detail.put("FMaterialId", detailRecord.getMaterialCode());
                    detail.put("FUnitID", "Pcs");
                    detail.put("FQty", detailRecord.getChangeInventoryQuantity());
                    detail.put("FStockId", detailRecord.getWarehouseName());
                    detail.put("FOwnerTypeId", "业务组织");
                    detail.put("FOwnerId", detailRecord.getOutOrg());
                    detail.put("FStockStatusId", "KCZT01_SYS");
                    detail.put("FKeeperTypeId", "业务组织");
                    detail.put("FDistribution", "True");
                    detail.put("FKeeperId", detailRecord.getOutOrg());
                    detail.put("F_REQUIRETEAM", detailRecord.getTeam());
                    detail.put("F_REQUIREDEP", detailRecord.getDepartment());
                    //备注用MC生成的盘点盘亏单号
                    String remark = StringUtils.isBlank(detailRecord.getRemark()) ? "MC单号：" + detailRecord.getOutOrder() : "MC单号：" + detailRecord.getOutOrder() + "，MC备注：" + detailRecord.getRemark();
                    detail.put("F_BSC_SubRemark1", remark);
                    detailList.add(detail);
                }
                object.put("FEntity", detailList);
                Jarr.add(object);
                log.info("盘点盘亏同步ERP其他出库单入参，[{}]", Jarr);
                JSONObject obj = syncToErpConsumer.outStock(Jarr);
                updateRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrg, outManageRecord.getOutOrg())
                        .eq(OverseasWarehouseManageRecord :: getBusinessType, OverseasBusinessTypeEnum.CHECK_SUB.getName())
                        .in(OverseasWarehouseManageRecord :: getId, outIdList)
                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date());
                if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
                    String responseMsg = null;
                    JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
                    if (CollectionUtil.isNotEmpty(responseResult)) {
                        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
                    }
                    k3ErrorMsg = obj.getString("Message") + "，详细信息:" + responseMsg;
                    errorMsg.append(k3ErrorMsg);
                    log.error("盘点盘亏同步ERP其他出库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", outManageRecord.getOutOrg(), JSONObject.toJSON(outIdList), k3ErrorMsg);
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                } else {
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                }
            } catch (Exception e){
                updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                log.error("盘点盘亏同步ERP其他出库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", outManageRecord.getOutOrg(), JSONObject.toJSON(outIdList), e.getMessage());
            }
            recordService.update(null, updateRecordWrapper);
        }
        if(StringUtils.isEmpty(errorMsg)){
            return ResponseData.success();
        }
        return ResponseData.error(errorMsg.toString());
    }

    /**
     * 盘点批量调用K3其他入库
     * @param inIdList
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData syncBatchCheckInStockToErp(List<BigDecimal> inIdList){
        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("IN_ORG")
                .eq("BUSINESS_TYPE", OverseasBusinessTypeEnum.CHECK_ADD.getName())
                .in("ID", inIdList).groupBy("IN_ORG");
        List<OverseasWarehouseManageRecord> recordList = recordService.list(recordWrapper);
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("盘点盘盈记录不存在！");
        }
        StringBuffer errorMsg = new StringBuffer();
        for (OverseasWarehouseManageRecord inManageRecord : recordList) {
            LambdaUpdateWrapper<OverseasWarehouseManageRecord> updateRecordWrapper = new LambdaUpdateWrapper<>();
            try {
                //K3异常信息
                String k3ErrorMsg = null;
                JSONArray Jarr = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("FCreatorId", "S20190109");//创建人
                object.put("FBillTypeID", "标准其他入库单");
                object.put("FStockOrgId", inManageRecord.getInOrg());
                object.put("FStockDirect", "普通");
                object.put("FDate", DateUtil.format(DateUtil.date(), "yyyy/MM/dd"));
                object.put("FDEPTID", "BM028");//物流部
                object.put("FOwnerTypeIdHead", "业务组织");
                object.put("FOwnerIdHead", inManageRecord.getInOrg());
                object.put("FBaseCurrId", "PRE001");
                object.put("FSUPPLIERID", "002");

                //盘点其他入库单明细
                QueryWrapper<OverseasWarehouseManageRecord> detailRecordWrapper = new QueryWrapper<>();
                detailRecordWrapper.eq("BUSINESS_TYPE", OverseasBusinessTypeEnum.CHECK_ADD.getName())
                        .eq("IN_ORG", inManageRecord.getInOrg())
                        .in("ID", inIdList);
                List<OverseasWarehouseManageRecord> detailRecordList = recordService.list(detailRecordWrapper);
                List<Map<String, Object>> detailList = new ArrayList<>();
                for (OverseasWarehouseManageRecord detailRecord : detailRecordList) {
                    Map<String, Object> detail = new HashMap<>();
                    detail.put("FMaterialId", detailRecord.getMaterialCode());
                    detail.put("FUnitID", "Pcs");
                    detail.put("FQty", detailRecord.getChangeInventoryQuantity());
                    detail.put("FStockId", detailRecord.getWarehouseName());
                    detail.put("FOwnerTypeId", "业务组织");
                    detail.put("FOwnerId", detailRecord.getInOrg());
                    detail.put("FStockStatusId", "KCZT01_SYS");
                    detail.put("FKeeperTypeId", "保管者类型");
                    detail.put("FDistribution", "True");
                    detail.put("FKeeperId", detailRecord.getInOrg());
                    detail.put("F_REQUIRETEAM", detailRecord.getTeam());
                    detail.put("F_REQUIREDEP", detailRecord.getDepartment());
                    //备注用MC生成的盘点盘盈单号
                    String remark = StringUtils.isBlank(detailRecord.getRemark()) ? "MC单号：" + detailRecord.getInOrder() : "MC单号：" + detailRecord.getInOrder() + "，MC备注：" + detailRecord.getRemark();
                    detail.put("FEntryNote", remark);
                    detailList.add(detail);
                }
                object.put("FEntity", detailList);
                Jarr.add(object);
                log.info("盘点盘盈同步ERP其他入库单入参，[{}]", Jarr);
                JSONObject obj = syncToErpConsumer.inStock(Jarr);

                updateRecordWrapper.eq(OverseasWarehouseManageRecord :: getInOrg, inManageRecord.getInOrg())
                        .eq(OverseasWarehouseManageRecord :: getBusinessType, OverseasBusinessTypeEnum.CHECK_ADD.getName())
                        .in(OverseasWarehouseManageRecord :: getId, inIdList)
                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date());
                if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
                    String responseMsg = null;
                    JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
                    if (CollectionUtil.isNotEmpty(responseResult)) {
                        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
                    }
                    k3ErrorMsg = obj.getString("Message") + "，详细信息:" + responseMsg;
                    errorMsg.append(k3ErrorMsg);
                    log.error("盘点盘盈同步ERP其他入库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", inManageRecord.getInOrg(), JSONObject.toJSON(inIdList), k3ErrorMsg);
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                } else {
                    updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                }
            } catch (Exception e){
                updateRecordWrapper.set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                log.error("盘点盘盈同步ERP其他入库单失败，异常组织编码[{}]，异常记录ID[{}]，异常信息[{}]", inManageRecord.getInOrg(), JSONObject.toJSON(inIdList), e.getMessage());
            }
            recordService.update(null, updateRecordWrapper);
        }
        if(StringUtils.isEmpty(errorMsg)){
            return ResponseData.success();
        }
        return ResponseData.error(errorMsg.toString());
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryOwVolumePage(OwVolumeReportParam param) {
        return ResponseData.success(mapper.queryOwVolumePage(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<OwVolumeReportResult> exportOwVolume(OwVolumeReportParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryOwVolumePage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public List<InvProductGalleryParam> getInvProductGallery(InvProductGalleryParam param) {
        return this.baseMapper.getInvProductGallery(param);
    }
}
