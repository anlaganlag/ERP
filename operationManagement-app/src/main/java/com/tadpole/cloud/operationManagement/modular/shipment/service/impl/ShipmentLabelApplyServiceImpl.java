package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.k3.model.params.ShipmentLabelApplyK3Param;
import com.tadpole.cloud.operationManagement.modular.shipment.consumer.K3CloudWebApiConsumer;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.InvProductGallery;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentLabelApply;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.ShipmentLabelApplyMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.InvProductGalleryParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentLabelApplyExcelParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentLabelApplyParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentLabelApplyResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IOtherModularService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.InvProductGalleryService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ShipmentLabelApplyService;
import com.tadpole.cloud.operationManagement.modular.shipment.utils.GeneratorShipmentNoUtil;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.service.RpMaterialService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 发货标签申请;(SHIPMENT_LABEL_APPLY)表服务实现类
 *
 * @author : LSY
 * @date : 2024-3-21
 */
@Service
@Transactional
@Slf4j
public class ShipmentLabelApplyServiceImpl extends ServiceImpl<ShipmentLabelApplyMapper, ShipmentLabelApply> implements ShipmentLabelApplyService {
    @Resource
    private ShipmentLabelApplyMapper shipmentLabelApplyMapper;


    @Resource
    IOtherModularService otherModularService;

    @Resource
    SyncToErpConsumer syncToErpConsumer;

    @Resource
    GeneratorShipmentNoUtil generatorShipmentNoUtil;

    @Resource
    private InvProductGalleryService invProductGalleryService;

    @Resource
    private RpMaterialService materialService;

    @Resource
    private K3CloudWebApiConsumer k3CloudWebApiConsumer;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public ShipmentLabelApply queryById(BigDecimal id) {
        return shipmentLabelApplyMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param param   筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    public Page<ShipmentLabelApplyResult> paginQuery(ShipmentLabelApplyParam param, long current, long size) {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        //查询当前登录人，所申请的


        //1. 构建动态查询条件
        LambdaQueryWrapper<ShipmentLabelApply> queryWrapper = new LambdaQueryWrapper<>();

        Date[] applyDateList = param.getApplyDateList();
        if (ObjectUtil.isNotEmpty(applyDateList)) {
            queryWrapper.ge(ShipmentLabelApply::getApplyDate, applyDateList[0]);
            queryWrapper.le(ShipmentLabelApply::getApplyDate, DateUtil.endOfDay(applyDateList[1]));
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMaterialCode()), ShipmentLabelApply::getMaterialCode, param.getMaterialCode());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getMaterialCodeList()), ShipmentLabelApply::getMaterialCode, param.getMaterialCodeList());


        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAsin()), ShipmentLabelApply::getAsin, param.getAsin());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getAsinList()), ShipmentLabelApply::getAsin, param.getAsinList());


        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysSite()), ShipmentLabelApply::getSysSite, param.getSysSite());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getSysSiteList()), ShipmentLabelApply::getSysSite, param.getSysSiteList());

        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSku()), ShipmentLabelApply::getSku, param.getSku());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getSkuList()), ShipmentLabelApply::getSku, param.getSkuList());


        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysShopsName()), ShipmentLabelApply::getSysShopsName, param.getSysShopsName());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getSysShopsNameList()), ShipmentLabelApply::getSysShopsName, param.getSysShopsNameList());


        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillNo()), ShipmentLabelApply::getBillNo, param.getBillNo());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getBillNoList()), ShipmentLabelApply::getBillNo, param.getBillNoList());


        queryWrapper.eq(ShipmentLabelApply::getApllyPersonNo, loginUser.getAccount());
        queryWrapper.orderByDesc(ShipmentLabelApply::getCreatedTime);

        //2. 执行分页查询
        Page<ShipmentLabelApplyResult> pagin = new Page<>(current, size, true);
        IPage<ShipmentLabelApplyResult> selectResult = shipmentLabelApplyMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param shipmentLabelApply 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public ShipmentLabelApply insert(ShipmentLabelApply shipmentLabelApply) {
        shipmentLabelApplyMapper.insert(shipmentLabelApply);
        return shipmentLabelApply;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public ShipmentLabelApply update(ShipmentLabelApplyParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<ShipmentLabelApply> wrapper = new LambdaUpdateChainWrapper<ShipmentLabelApply>(shipmentLabelApplyMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getBatchNo()), ShipmentLabelApply::getBatchNo, param.getBatchNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getDeliverypointNo()), ShipmentLabelApply::getDeliverypointNo, param.getDeliverypointNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getDeliverypoint()), ShipmentLabelApply::getDeliverypoint, param.getDeliverypoint());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPlatform()), ShipmentLabelApply::getPlatform, param.getPlatform());
        wrapper.set(ObjectUtil.isNotEmpty(param.getArea()), ShipmentLabelApply::getArea, param.getArea());
        wrapper.set(ObjectUtil.isNotEmpty(param.getDepartment()), ShipmentLabelApply::getDepartment, param.getDepartment());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTeam()), ShipmentLabelApply::getTeam, param.getTeam());
        wrapper.set(ObjectUtil.isNotEmpty(param.getMaterialCode()), ShipmentLabelApply::getMaterialCode, param.getMaterialCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getMaterialInfo()), ShipmentLabelApply::getMaterialInfo, param.getMaterialInfo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getProductName()), ShipmentLabelApply::getProductName, param.getProductName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getAsin()), ShipmentLabelApply::getAsin, param.getAsin());
        wrapper.set(ObjectUtil.isNotEmpty(param.getProductType()), ShipmentLabelApply::getProductType, param.getProductType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShopName()), ShipmentLabelApply::getShopName, param.getShopName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysSite()), ShipmentLabelApply::getSysSite, param.getSysSite());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSku()), ShipmentLabelApply::getSku, param.getSku());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysLabelType()), ShipmentLabelApply::getSysLabelType, param.getSysLabelType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysShopsName()), ShipmentLabelApply::getSysShopsName, param.getSysShopsName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getFbaNo()), ShipmentLabelApply::getFbaNo, param.getFbaNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBillNo()), ShipmentLabelApply::getBillNo, param.getBillNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getReceiveWarehouse()), ShipmentLabelApply::getReceiveWarehouse, param.getReceiveWarehouse());
        wrapper.set(ObjectUtil.isNotEmpty(param.getReceiveWarehouseCode()), ShipmentLabelApply::getReceiveWarehouseCode, param.getReceiveWarehouseCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getOrgCode()), ShipmentLabelApply::getOrgCode, param.getOrgCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransportationType()), ShipmentLabelApply::getTransportationType, param.getTransportationType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getUnwType()), ShipmentLabelApply::getUnwType, param.getUnwType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getRemark1()), ShipmentLabelApply::getRemark1, param.getRemark1());
        wrapper.set(ObjectUtil.isNotEmpty(param.getRemark2()), ShipmentLabelApply::getRemark2, param.getRemark2());
        wrapper.set(ObjectUtil.isNotEmpty(param.getRemark3()), ShipmentLabelApply::getRemark3, param.getRemark3());
        wrapper.set(ObjectUtil.isNotEmpty(param.getDataSourceType()), ShipmentLabelApply::getDataSourceType, param.getDataSourceType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getApllyPerson()), ShipmentLabelApply::getApllyPerson, param.getApllyPerson());
        wrapper.set(ObjectUtil.isNotEmpty(param.getApllyPersonNo()), ShipmentLabelApply::getApllyPersonNo, param.getApllyPersonNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSyncRequestMsg()), ShipmentLabelApply::getSyncRequestMsg, param.getSyncRequestMsg());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSyncResultMsg()), ShipmentLabelApply::getSyncResultMsg, param.getSyncResultMsg());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCreatedBy()), ShipmentLabelApply::getCreatedBy, param.getCreatedBy());
        wrapper.set(ObjectUtil.isNotEmpty(param.getUpdatedBy()), ShipmentLabelApply::getUpdatedBy, param.getUpdatedBy());
        //2. 设置主键，并更新
        wrapper.eq(ShipmentLabelApply::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal id) {
        int total = shipmentLabelApplyMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = shipmentLabelApplyMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @DataSource(name = "stocking")
    @Transactional
    @Override
    public List<ShipmentLabelApply> addBatch(List<ShipmentLabelApply> shipmentLabelApplyList) {

        LoginUser loginUser = LoginContext.me().getLoginUser();


        //调入仓库默认名称
        Set<String> warehouseNameSet = shipmentLabelApplyList.stream().map(e -> "Amazon_" + e.getSysShopsName() + "_" + e.getSysSite() + "_仓库").collect(Collectors.toSet());
        Map<String, String> shopWarehouseCodeMap = new HashMap<>();
        shopWarehouseCodeMap = otherModularService.getWarehouseCode(warehouseNameSet);

        //校验 表格填入的调入仓库
        if (ObjectUtil.isEmpty(shopWarehouseCodeMap) || ObjectUtil.isEmpty(shopWarehouseCodeMap.values())) {
//             return ResponseData.error("填入的调入仓库,未能匹配上正确的仓库信息");
        }


        for (ShipmentLabelApply apply : shipmentLabelApplyList) {

            apply.setReceiveWarehouse("Amazon_" + apply.getSysShopsName() + "_" + apply.getSysSite() + "_仓库");
            apply.setReceiveWarehouseCode(shopWarehouseCodeMap.get(apply.getReceiveWarehouse()));


            apply.setDeliverypoint("供应商");
            apply.setDeliverypointNo("FHD03");

            apply.setOrgCode(apply.getReceiveWarehouseCode());

            String billNo = generatorShipmentNoUtil.getPrintLabelBatchNo("BQDY");
            apply.setBillNo(billNo);

//            apply.setTransportationType("海运-普船");
            apply.setUnwType("批量出货");


            apply.setApplyDate(new Date());
            apply.setApllyPerson(loginUser.getName());
            apply.setApllyPersonNo(loginUser.getAccount());
            apply.setCreatedBy(loginUser.getName());
        }
        this.saveBatch(shipmentLabelApplyList);
        return shipmentLabelApplyList;

    }


    /**
     * 同步发货标签到金蝶K3  批量操作
     *
     * @param labelApplyList
     * @return
     */
    @DataSource(name = "stocking")
    @Transactional
    @Override
    public ResponseData syncLabelToK3(List<ShipmentLabelApply> labelApplyList) {

        List<ShipmentLabelApply> noIdList = labelApplyList.stream().filter(l -> ObjectUtil.isNull(l.getId())).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(noIdList)) { //没有id的时候 批量选择
            labelApplyList = this.addBatch(noIdList);
        }


        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();

        for (ShipmentLabelApply labelApply : labelApplyList) {

//            labelApply = this.syncToK3(labelApply);
            ResponseData responseData = this.syncToK3WebApi(labelApply);

            if (responseData.getSuccess()) {
                successList.add(labelApply.getBillNo());
            } else {
                failList.add(labelApply.getBillNo());
            }
        }

        if (ObjectUtil.isNotEmpty(failList)) {
            return ResponseData.error("以下标签打印申请同步到K3失败：" + failList.toString());
        }

        return ResponseData.success("标签打印申请同步到K3成功");
    }

    @DataSource(name = "stocking")
    @Override
    public ShipmentLabelApply syncToK3(ShipmentLabelApply item) {
        try {
            Map<String, Object> mapParm = BeanUtil.beanToMap(item);
            mapParm.put("sendQty", item.getPrintQty());
            mapParm.put("fbaNo", item.getBillNo());
            mapParm.put("team", "Team6");
//                    String syncResult = syncToErpUtil.createTransferMap(mapParm);
            JSONObject syncResultJson = syncToErpConsumer.createTransferMapJson(mapParm);
            String syncResult = syncResultJson.toString();
            item.setSyncTime(new Date());
            item.setSyncRequestMsg(String.valueOf(mapParm.get("syncRequestMsg")));
            item.setSyncResultMsg(syncResult);


            //<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<string xmlns=\"http://tempuri.org/\">{\"result\":true,\"msg\":\"保存成功\",\"BillNo\":\"MC-FH-202302130007\"}</string>
            if (ObjectUtil.isNotEmpty(syncResult) && syncResult.contains("保存成功")) {
                if (ObjectUtil.isNull(item.getSyncCount())) {
                    item.setSyncCount(1);
                } else {
                    item.setSyncCount(item.getSyncCount() + 1);
                }
                item.setApplyStatus(1);
                item.setSyncStatus(1);

            } else {
                //同批次有失败的调拨单，则需要删除之前保存成功的
                item.setSyncStatus(2);

            }

            //返回结果处理
        } catch (Exception e) {
            //同批次有失败的调拨单，则需要删除之前保存成功的
            item.setSyncStatus(2);
            item.setSyncResultMsg(JSONUtil.toJsonStr(e));
        }

        this.updateById(item);
        return item;
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData syncToK3WebApi(ShipmentLabelApply item) {
        ResponseData responseData=new ResponseData();
        try {
            ShipmentLabelApplyK3Param applyK3Param = BeanUtil.copyProperties(item, ShipmentLabelApplyK3Param.class);
            responseData = k3CloudWebApiConsumer.tranferApplySave(applyK3Param);
            if (responseData.getSuccess()) {
                if (ObjectUtil.isNull(item.getSyncCount())) {
                    item.setSyncCount(1);
                } else {
                    item.setSyncCount(item.getSyncCount() + 1);
                }
                item.setApplyStatus(1);
                item.setSyncStatus(1);
            } else {
                //同批次有失败的调拨单，则需要删除之前保存成功的
                item.setSyncStatus(2);
            }
            item.setSyncResultMsg(responseData.getMessage());
            //返回结果处理
        } catch (Exception e) {
            //同批次有失败的调拨单，则需要删除之前保存成功的
            item.setSyncStatus(2);
            item.setSyncResultMsg(JSONUtil.toJsonStr(e));

            responseData=ResponseData.error("【发货标签申请】同步异常："+e.getMessage());
        }
        item.setUpdatedTime(new Date());
        this.updateById(item);
        return responseData;
    }


    @DataSource(name = "basicdata")
    @Override
    public ResponseData importCreateSku(MultipartFile file) {

        BufferedInputStream buffer = null;

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        if (ObjectUtil.isNull(currentUser)) {
            return ResponseData.error("请登录后再申请创建");
        }


        List<ShipmentLabelApplyExcelParam> excelDataList = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<ShipmentLabelApplyExcelParam>();
            EasyExcel.read(buffer, ShipmentLabelApplyExcelParam.class, listener).sheet().doRead();

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
        List<ShipmentLabelApplyExcelParam> filterResultList = excelDataList.stream().filter(e ->
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
            return ResponseData.error("导入数据跟当前登录者所属部门+team不匹配,请检查表格数据是否正确！");
        }


        Map<String, List<ShipmentLabelApplyExcelParam>> excelDataMap = excelDataList.stream()
                .collect(Collectors.groupingBy(e -> e.getSysShopsName() + "_" + e.getSysSite() + "_" + e.getSku()));

        Map<String, List<InvProductGallery>> productSkuMap = productSkuList.stream()
                .collect(Collectors.groupingBy(ps -> ps.getSysShopsName() + "_" + ps.getSysSite() + "_" + ps.getSku()));

        List<InvProductGallery> productSkuFilterList = productSkuList.stream()
                .filter(ps -> excelDataMap.containsKey(ps.getSysShopsName() + "_" + ps.getSysSite() + "_" + ps.getSku())).collect(Collectors.toList());


        //查询物料信息
        Map<String, List<InvProductGallery>> matCodeMap =
                productSkuFilterList.stream().collect(Collectors.groupingBy(InvProductGallery::getMaterialCode));

        List<String> matCodeList = matCodeMap.keySet().stream().collect(Collectors.toList());
        List<Material> materialList = materialService.getMaterials(matCodeList);
        Map<String, List<Material>> materialMap = materialList.stream().collect(Collectors.groupingBy(Material::getMaterialCode));

        if (ObjectUtil.isEmpty(materialList)) {
            return ResponseData.error("未找到关联的物料信息，请确认物料编码填写是否正确");
        }

        List<ShipmentLabelApply> applyItemList = new ArrayList<>();

        //返回解析成功的数据

        for (ShipmentLabelApplyExcelParam excelParam : excelDataList) {

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

            ShipmentLabelApply item = new ShipmentLabelApply();
            BeanUtil.copyProperties(ipg, item);
            item.setMaterialCode(ipg.getMaterialCode());
            item.setMaterialInfo(materialJsonStr);
            item.setProductName(material.getProductName());
            item.setProductType(material.getProductType());
            item.setCreatedTime(new Date());

            //表格录入数据

            item.setPrintQty(excelParam.getPrintQty());
            item.setTransportationType(excelParam.getTransportationType());
//            item.setUnwType(excelParam.getUnwType());
            item.setRemark1(excelParam.getRemark1());
            item.setRemark2(excelParam.getRemark2());
            item.setRemark3(excelParam.getRemark3());
            item.setDataSourceType("表格导入");
            item.setPlatform("Amazon");
            applyItemList.add(item);
        }

        //按物料编码排序
        if (ObjectUtil.isNotEmpty(applyItemList)) {
            applyItemList = applyItemList.stream().sorted(Comparator.comparing(ShipmentLabelApply::getMaterialCode)).collect(Collectors.toList());
        }

        return ResponseData.success(applyItemList);
    }
}