package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangMatSync;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangMatSysncMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangWarehouseMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangMatSyncParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.*;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangMatSyncResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangWarehouseResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangMatSysncService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant.MAT_STATUS_NORMAL;
import static com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant.MAT_STATUS_STOP;

/**
 * <p>
 * 马帮物料同步记录表 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-10-24
 */
@Service
@Slf4j
public class MabangMatSysncServiceImpl extends ServiceImpl<MabangMatSysncMapper, MabangMatSync> implements IMabangMatSysncService {

    @Resource
    private MabangMatSysncMapper mapper;

    @Resource
    private MabangWarehouseMapper warehouseMapper;

    @Resource
    IMabangRequstService mabangRequstService;


    @DataSource(name = "external")
    @Override
    public ResponseData addMaterielFromEbms(List<TbComMateriel> materielList, List<MabangWarehouseResult> warehouseList ) {

        if (ObjectUtil.isEmpty(warehouseList)) {
          warehouseList = this.getMabangWarehouseResults();
        }
        for (TbComMateriel materiel : materielList) {
            try {
                this.syncAddDataFromEbms(materiel, warehouseList);
                log.info("从EBMS同步物料[{}]到马帮出成功",materiel.getMatCode());
            } catch (Exception e) {
                log.error("从EBMS同步物料[{}]到马帮出现异常[{}]",materiel.getMatCode(), JSON.toJSONString(e));
            }
        }
        return ResponseData.success();
    }



    public void syncAddDataFromEbms(TbComMateriel materiel, List<MabangWarehouseResult> warehouseList) {
        StringBuilder builder = new StringBuilder();

        MabangGoodsAddParm goodsAddParm = new MabangGoodsAddParm();
            goodsAddParm.setStockSku(materiel.getMatCode());
            String goodsName = this.getGoodsName(materiel);
            goodsAddParm.setNameCN(goodsName);//商品名称
            String status= "启用".equals(materiel.getMatStatus()) ? MAT_STATUS_NORMAL : MAT_STATUS_STOP;
            goodsAddParm.setStatus(status);//商品状态
            //商品图片处理
            String picUrl=null;
            if (ObjectUtil.isNotEmpty(materiel.getMatImagePath())) {
                picUrl= materiel.getMatImagePath().split(";")[0].replace("192.168.2.217","jcbsc.dns0755.net:2180");
            }
            goodsAddParm.setPicture(picUrl);
            goodsAddParm.setLength(String.valueOf(materiel.getMatNetLengthOrg()));//长
            goodsAddParm.setWidth(String.valueOf(materiel.getMatNetWidthOrg()));//宽
            goodsAddParm.setHeight(String.valueOf(materiel.getMatNetHeightOrg()));//高
            goodsAddParm.setWeight(String.valueOf(materiel.getMatNetWeightOrg()));//重量
            goodsAddParm.setParentCategoryName("虚拟一级目录");//todo 一级目录名称
            goodsAddParm.setDeclareName(materiel.getMatProName());////申报中文名称
            goodsAddParm.setDeclareCode(materiel.getMatCustomsCode());//报关编码
            goodsAddParm.setPurchasePrice("1");//最新采购价
            goodsAddParm.setParentName(materiel.getMatComBrand());//brandName
            goodsAddParm.setHasBattery("是".equals(materiel.getMatIsCharged()) ? "1" : "2");//带电池 1.是 2.否
            goodsAddParm.setCategoryName("虚拟二级目录");//二级目录名称 todo
            goodsAddParm.setDefaultCost("1");//统一成本价
//            goodsAddParm.setBuyerId(materiel.getMatProcurementStaff());//采购员名称
            goodsAddParm.setBuyerId("虚拟采购员");//采购员名称
//            goodsAddParm.setDeveloperName(materiel.getMatDeveloper());//开发员名称
//            goodsAddParm.setSalesName(materiel.getMatSpecificOperators());//销售员名称
            goodsAddParm.setCreatorName("深圳市金畅创新技术有限公司");//创建员工
            //Json字符串
        //关联供应商信息
            MabangGoodsSupplierParm supplierParm = new MabangGoodsSupplierParm();
            supplierParm.setName("虚拟供应商");
            supplierParm.setProductLinkAddress("虚拟供应商地址");
            supplierParm.setFlag("1");
            ArrayList<MabangGoodsSupplierParm> supplierParmList = new ArrayList<>();
            supplierParmList.add(supplierParm);
        goodsAddParm.setSuppliersData(JSONUtil.toJsonStr(supplierParmList));

        //仓库信息
            ArrayList<MabangGoodsWarehouseParm> warehouseParmArrayList = new ArrayList<>();
            for (MabangWarehouseResult warehouse : warehouseList) {
                MabangGoodsWarehouseParm warehouseParm = new MabangGoodsWarehouseParm();
                warehouseParm.setName(warehouse.getName());
                warehouseParm.setStockCost("1");//仓库成本价
                warehouseParm.setPurchaseDays(materiel.getMatProCycle());//采购天数
                warehouseParm.setMinPurchaseQuantity(String.valueOf(materiel.getMatMinOrderQty()));//最小采购数量
                warehouseParm.setIsDefault("2");//1:默认仓库2.非默认仓库
                if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                    warehouseParm.setIsDefault("1");//1:默认仓库2.非默认仓库
                }

                warehouseParmArrayList.add(warehouseParm);
            }
            goodsAddParm.setWarehouseData(JSONUtil.toJsonStr(warehouseParmArrayList));

//            addParmList.add(goodsAddParm);
//
            ArrayList<MabangMatSync> tempMatSysncList = new ArrayList<>();
            for (MabangWarehouseResult warehouse : warehouseList) {
                //生成同步信息
                MabangMatSync mabangMatSync = new MabangMatSync();
                mabangMatSync.setMatCode(materiel.getMatCode());
                mabangMatSync.setStockSku(materiel.getMatCode());
                mabangMatSync.setMatName(materiel.getMatProName());
                mabangMatSync.setMergeMatName(goodsName);

                mabangMatSync.setBizType("新增");
                mabangMatSync.setStatus(status);
                mabangMatSync.setMabangWarehouseId(warehouse.getId());
                mabangMatSync.setMabangWarehouseName(warehouse.getName());
                mabangMatSync.setMatCreateTime(materiel.getMatCreatDate());
                mabangMatSync.setMatUpdateTime(materiel.getMatLastUpdateDate());
                mabangMatSync.setParentCategoryName("虚拟一级目录");//todo 一级目录
                mabangMatSync.setCategoryName("虚拟二级目录");//todo 二级目录
                mabangMatSync.setIsDefault("2");//1:默认仓库2.非默认仓库
                if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                    mabangMatSync.setIsDefault("1");//1:默认仓库2.非默认仓库
                }
                mabangMatSync.setId(IdWorker.getIdStr());
                tempMatSysncList.add(mabangMatSync);
            }

            this.saveBatch(tempMatSysncList);

            MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-do-add-stock", goodsAddParm);
            String stockId =null ;
            String syncStatus ="0" ;
            String syncResultMsg ="" ;
            try {
                log.info("物料[{}]-<新增>-到马帮erp请求接口时间：【{}】,请求参数{}",goodsAddParm.getStockSku(),new Date(),JSONUtil.toJsonStr(mabangHeadParm));
                MabangResult mabangResult = mabangRequstService.stockDoSearchSkuList(mabangHeadParm);
                syncResultMsg=JSONUtil.toJsonStr(mabangResult);
                log.info("物料[{}]-<新增>-到马帮erp接口响应时间：【{}】,返回结果{}",goodsAddParm.getStockSku(),new Date(),syncResultMsg);
                if (ObjectUtil.isNotNull(mabangResult) && "200".equals(mabangResult.getCode())) {
                    stockId= String.valueOf(JSONUtil.parseObj(JSONUtil.toJsonStr(mabangResult.getData())).get("stockId"));
                    syncStatus = "1";
                }else if (ObjectUtil.isNotNull(mabangResult)
                        && ObjectUtil.isNotEmpty(mabangResult.getMessage())
                        && mabangResult.getMessage().contains("已经存在")) {
                    syncStatus = "3";
                }

//                MabangResult(code=0, message=库存sku[ASW220213]已经存在, data=null)
            } catch (Exception e) {
                syncResultMsg =JSONUtil.toJsonStr(e);
                log.info("物料[{}]-<新增>-到马帮erp调用接口出现异常{}",syncResultMsg);
            }

        if (syncStatus.equals("3")) {
            List<String> idList = tempMatSysncList.stream().map(MabangMatSync::getId).collect(Collectors.toList());
            this.removeByIds(idList);
        }else{
            for (MabangMatSync matSysnc : tempMatSysncList) {
                if (ObjectUtil.isNotEmpty(stockId)) {
                    matSysnc.setStockId(stockId);
                }
                matSysnc.setSyncStatus(syncStatus);
                matSysnc.setSyncTime(new Date());
                matSysnc.setSyncRequstPar(JSONUtil.toJsonStr(mabangHeadParm));
                matSysnc.setSyncResultMsg(syncResultMsg);
                matSysnc.setUpdateTime(new Date());
            }
            this.updateBatchById(tempMatSysncList);
        }
            log.info("物料[{}]-<新增>-到马帮erp的同步记录更新完成",goodsAddParm.getStockSku());

    }

    @NotNull
    private String getGoodsName(TbComMateriel materiel) {

        StringBuilder builder = new StringBuilder();
        //matProName+'-'+matStyle+'-'+matMainMaterial+'-'+matPattern+'-'+matBrand+'-'+matModel+'-'+matColor+'-'+matSize+'-'+matPackQty+'-'+matVerson  as 库存SKU名称
        String nameCN = builder
                .append(materiel.getMatProName()).append("-")
                .append(materiel.getMatStyle()).append("-")
                .append(materiel.getMatMainMaterial()).append("-")
                .append(materiel.getMatPattern()).append("-")
                .append(materiel.getMatBrand()).append("-")
                .append(materiel.getMatModel()).append("-")
                .append(materiel.getMatColor()).append("-")
                .append(materiel.getMatSize()).append("-")
                .append(materiel.getMatPackQty()).append("-")
                .append(materiel.getMatVerson()).toString();
        return nameCN;
    }

    @DataSource(name = "external")
    @Override
    public ResponseData updateFromEbms(List<TbComMateriel> materielList, List<MabangWarehouseResult> warehouseList) {

        if (ObjectUtil.isEmpty(warehouseList)) {
            warehouseList = this.getMabangWarehouseResults();
        }
        List<String> warehouseIdList = warehouseList.stream().map(w -> w.getId()).collect(Collectors.toList());
        List<String> matCodeList = materielList.stream().map(tm -> tm.getMatCode()).collect(Collectors.toList());
        List<MabangMatSync> mabangMatSysnList = this.getMabangMatSysnList(matCodeList,warehouseIdList);
        Map<String, List<MabangMatSync>> listMap = mabangMatSysnList.stream().collect(Collectors.groupingBy(MabangMatSync::getMatCode));

        for (TbComMateriel materiel : materielList) {
            List<MabangMatSync> sysncList = listMap.get(materiel.getMatCode());
            try {
                this.syncUpdateFromEbms(materiel, warehouseList,sysncList);
            } catch (Exception e) {
                log.error("同步物料:{}出现异常,异常信息{}",JSONUtil.toJsonStr(materiel),JSONUtil.toJsonStr(e));
            }
        }
        return ResponseData.success();

    }



    @DataSource(name = "external")
    @Override
    public List<MabangMatSync> getSyncList(List<String> matCodeList,String warehouseId) {
        List<MabangMatSync> resultList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(matCodeList)) {
            List<List<String>> splitList = CollUtil.split(matCodeList, 800);
            for (List<String> matCodes : splitList) {
                LambdaQueryWrapper<MabangMatSync> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(MabangMatSync::getMatCode, matCodes);
                queryWrapper.eq(MabangMatSync::getSyncStatus, "1");
                queryWrapper.eq(ObjectUtil.isNotEmpty(warehouseId), MabangMatSync::getMabangWarehouseId, warehouseId);
                List<MabangMatSync> result = this.baseMapper.selectList(queryWrapper);
                resultList.addAll(result);
            }
        } else {
            LambdaQueryWrapper<MabangMatSync> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(warehouseId), MabangMatSync::getMabangWarehouseId, warehouseId);
            queryWrapper.eq(MabangMatSync::getSyncStatus, "1");
            resultList = this.baseMapper.selectList(queryWrapper);
        }
        return resultList;
    }


    @DataSource(name = "external")
    @Override
    public ResponseData stockChangeGrid() {
        LambdaQueryWrapper<MabangMatSync> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(MabangMatSync::getGridList);
        wrapper.in(MabangMatSync::getMabangWarehouseId,"1047844","1067832");
//        wrapper.ge(MabangMatSync::getMatCreateTime, DateUtil.parse("2022-09-30"));//2022-9-30之后使用api分配默认仓位
        wrapper.eq(MabangMatSync::getSyncStatus,MabangConstant.SYNC_SUCCESS);
        List<MabangMatSync> syncList = this.baseMapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(syncList)) {
            return ResponseData.success();
        }

        for (MabangMatSync sync : syncList) {
            String gridCode="1047844".equals(sync.getMabangWarehouseId() )?"A01":"B01";
            MabangGoodsChangeGrid mabangGoodsChangeGrid = new MabangGoodsChangeGrid();
            mabangGoodsChangeGrid.setStockSku(sync.getStockSku());
            mabangGoodsChangeGrid.setWarehouseId(sync.getMabangWarehouseId());
            mabangGoodsChangeGrid.setGridCode(gridCode);
            MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-change-grid",mabangGoodsChangeGrid);
            MabangResult mabangResult = mabangRequstService.stockChangeGrid(mabangHeadParm);
            if (ObjectUtil.isNotNull(mabangResult) && "200".equals(mabangResult.getCode())) {
                sync.setGridList(ObjectUtil.isEmpty(sync.getGridList()) ? gridCode : sync.getGridList() + ","+gridCode);
            } else if (JSONUtil.toJsonStr(mabangResult).contains("仓位号已存在") && ObjectUtil.isEmpty(sync.getGridList())){
                sync.setGridList(gridCode);
            }
            sync.setUpdateTime(new Date());
            sync.setSyncTime(new Date());
        }
        this.updateBatchById(syncList);

        return  ResponseData.success();

    }


    @DataSource(name = "external")
    @Override
    public ResponseData addAllMatToNewWarehouse() {
        //获取新仓库  2022-10-30号以后新建的仓库 判断条件就是仓库的创建时间
        DateTime dateTime = DateUtil.parseDate("2022-10-10");
        List<MabangWarehouse> newWarehouseList = this.mapper.getNewWarehouseList(dateTime);

        return null;
    }

    @DataSource(name = "external")
    @Override
    public List<MabangWarehouse>  getNewWarehouseList(Date creatStartDate) {
        return this.mapper.getNewWarehouseList(creatStartDate);
    }

    @DataSource(name = "external")
    @Override
    public ResponseData addAllMatToNewWarehouse(List<MabangWarehouse> newWarehouseList, List<TbComMateriel> allMatList) {
        //目前不考虑 新增仓库 全量推送物料
        return null;
    }


    @DataSource(name = "external")
    @Override
    public PageResult<MabangMatSyncResult> listPage(MabangMatSyncParam param) {

        Page pageContext = param.getPageContext();
        IPage<MabangMatSyncResult> page = mapper.listPage(pageContext, param);
        return new PageResult<>(page);
    }
    @DataSource(name = "external")
    @Override
    public List<MabangWarehouseResult> getNewWarehouseByName(String warehouse) {
        return warehouseMapper.getNewWarehouseByName(warehouse);

    }

    @DataSource(name = "external")
    @Override
    public List<MabangMatSync> getSyncSuccessMatCode(List<String> matCodeList,String warehouseId) {
        List<MabangMatSync> resultList =new ArrayList<>();
        if (ObjectUtil.isNotEmpty(matCodeList)) {
            List<List<String>> splitList = CollUtil.split(matCodeList, 900);
            for (List<String> matCodes : splitList) {
                QueryWrapper<MabangMatSync> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("MAT_CODE", matCodes);
                queryWrapper.eq("SYNC_STATUS", "1");
                if (ObjectUtil.isNotEmpty(warehouseId)) {
                    queryWrapper.eq("MABANG_WAREHOUSE_ID", warehouseId);
                }
                queryWrapper.select("DISTINCT MAT_CODE");
                List<MabangMatSync> mabangMatSyncs = mapper.selectList(queryWrapper);
                resultList.addAll(mabangMatSyncs);
            }
        } else {
            QueryWrapper<MabangMatSync> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYNC_STATUS", "1");
            if (ObjectUtil.isNotEmpty(warehouseId)) {
                queryWrapper.eq("MABANG_WAREHOUSE_ID", warehouseId);
            }
            queryWrapper.select("DISTINCT MAT_CODE");
            List<MabangMatSync> mabangMatSyncs = mapper.selectList(queryWrapper);
            resultList.addAll(mabangMatSyncs);
        }

        return resultList;
    }


    private void addMatToNewWarehouse(TbComMateriel materiel, List<MabangWarehouseResult> warehouseList, List<MabangMatSync> sysncLis) {


        MabangGoodsModifyParm modifyParm = new MabangGoodsModifyParm();
        modifyParm.setStockSku(materiel.getMatCode());
        modifyParm.setNameCN(this.getGoodsName(materiel));//商品名称
        String status= "否".equals(materiel.getMatIsAvailableSale()) ? MAT_STATUS_STOP : MAT_STATUS_NORMAL;
        modifyParm.setStatus(status);//商品状态
        //商品图片处理
        String picUrl= materiel.getMatImagePath().split(";")[0].replace("192.168.2.217","jcbsc.dns0755.net:2180");
        modifyParm.setPicture(picUrl);
        modifyParm.setLength(String.valueOf(materiel.getMatNetLengthOrg()));//长
        modifyParm.setWidth(String.valueOf(materiel.getMatNetWidthOrg()));//宽
        modifyParm.setHeight(String.valueOf(materiel.getMatNetHeightOrg()));//高
        modifyParm.setWeight(String.valueOf(materiel.getMatNetWeightOrg()));//重量
//        modifyParm.setParentCategoryName("虚拟一级目录");//todo 一级目录名称
//        modifyParm.setCategoryName("虚拟二级目录");//二级目录名称 todo
        modifyParm.setDeclareName(materiel.getMatProName());////申报中文名称
//        modifyParm.setDeclareCode(materiel.getMatCustomsCode());//报关编码
//        modifyParm.setPurchasePrice("1");//最新采购价
        modifyParm.setBrandName(materiel.getMatComBrand());//brandName
        modifyParm.setHasBattery("是".equals(materiel.getMatIsCharged()) ? "1" : "2");//带电池 1.是 2.否
//        modifyParm.setDefaultCost("1");//统一成本价
//            goodsAddParm.setBuyerId(materiel.getMatProcurementStaff());//采购员名称
//        modifyParm.setBuyerId("虚拟采购员");//采购员名称
//            goodsAddParm.setDeveloperName(materiel.getMatDeveloper());//开发员名称
//            goodsAddParm.setSalesName(materiel.getMatSpecificOperators());//销售员名称

//        modifyParm.setCreatorName("深圳市金畅创新技术有限公司");//创建员工

        //Json字符串
        //关联供应商信息 更新的时候 不需要更新（新增的时候也是固定的）
       /* MabangGoodsSupplierParm supplierParm = new MabangGoodsSupplierParm();
        supplierParm.setName("虚拟供应商");
        supplierParm.setProductLinkAddress("虚拟供应商地址");
        supplierParm.setFlag("1");
        ArrayList<MabangGoodsSupplierParm> supplierParmList = new ArrayList<>();
        supplierParmList.add(supplierParm);
        modifyParm.setSuppliersData(JSONUtil.toJsonStr(supplierParmList));*/

        //仓库信息
        ArrayList<MabangGoodsWarehouseParm> warehouseParmArrayList = new ArrayList<>();
        for (MabangWarehouseResult warehouse : warehouseList) {
            MabangGoodsWarehouseParm warehouseParm = new MabangGoodsWarehouseParm();
            warehouseParm.setName(warehouse.getName());
            warehouseParm.setStockCost("1");//仓库成本价
            warehouseParm.setPurchaseDays(materiel.getMatProCycle());//采购天数
            warehouseParm.setMinPurchaseQuantity(String.valueOf(materiel.getMatMinOrderQty()));//最小采购数量
            warehouseParm.setIsDefault("2");//1:默认仓库2.非默认仓库
            if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                warehouseParm.setIsDefault("1");//1:默认仓库2.非默认仓库
            }
            warehouseParmArrayList.add(warehouseParm);
        }
        modifyParm.setWarehouseData(JSONUtil.toJsonStr(warehouseParmArrayList));


        //更新同步记录
        ArrayList<MabangMatSync> tempMatSysncList = new ArrayList<>();

        if (ObjectUtil.isEmpty(sysncLis)) { //物料已经通过EXCEL同步到马帮erp,现在物料更新，补上同步记录
            for (MabangWarehouseResult warehouse : warehouseList) {
                //生成同步信息
                MabangMatSync mabangMatSync = new MabangMatSync();
                mabangMatSync.setMatCode(materiel.getMatCode());
                mabangMatSync.setStockSku(materiel.getMatCode());
                mabangMatSync.setBizType("更新");
                mabangMatSync.setStatus(status);
                mabangMatSync.setMabangWarehouseId(warehouse.getId());
                mabangMatSync.setMabangWarehouseName(warehouse.getName());
                mabangMatSync.setMatCreateTime(materiel.getMatCreatDate());
                mabangMatSync.setMatUpdateTime(materiel.getMatLastUpdateDate());
                mabangMatSync.setParentCategoryName("虚拟一级目录");//todo 一级目录
                mabangMatSync.setCategoryName("虚拟二级目录");//todo 二级目录
                mabangMatSync.setIsDefault("2");//1:默认仓库2.非默认仓库
                if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                    mabangMatSync.setIsDefault("1");//1:默认仓库2.非默认仓库
                }
                mabangMatSync.setId(IdWorker.getIdStr());
                mabangMatSync.setRemark("物料已经通过EXCEL同步到马帮erp,现在物料更新，补上同步记录");
                tempMatSysncList.add(mabangMatSync);
            }
        } else { //已经有同步记录了，需要判断同步仓库的信息是否全

            for (MabangWarehouseResult warehouse : warehouseList) {
                MabangMatSync sysnc = sysncLis.stream().filter(s -> warehouse.getId().equals(s.getMabangWarehouseId())).findAny().orElse(null);
                if (ObjectUtil.isNull(sysnc)) {
                    MabangMatSync mabangMatSync = new MabangMatSync();
                    mabangMatSync.setMatCode(materiel.getMatCode());
                    mabangMatSync.setStockSku(materiel.getMatCode());
                    mabangMatSync.setBizType("更新");
                    mabangMatSync.setStatus(status);
                    mabangMatSync.setMabangWarehouseId(warehouse.getId());
                    mabangMatSync.setMatCreateTime(materiel.getMatCreatDate());
                    mabangMatSync.setMatUpdateTime(materiel.getMatLastUpdateDate());
                    mabangMatSync.setParentCategoryName("虚拟一级目录");//todo 一级目录
                    mabangMatSync.setCategoryName("虚拟二级目录");//todo 二级目录
                    mabangMatSync.setIsDefault("2");//1:默认仓库2.非默认仓库
                    if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                        mabangMatSync.setIsDefault("1");//1:默认仓库2.非默认仓库
                    }
                    mabangMatSync.setId(IdWorker.getIdStr());
                    mabangMatSync.setRemark("物料已经通过EXCEL同步到马帮erp,现在物料更新，补上同步记录");
                    tempMatSysncList.add(mabangMatSync);
                } else {
                    sysnc.setMatCreateTime(materiel.getMatCreatDate());
                    sysnc.setMatUpdateTime(materiel.getMatLastUpdateDate());
                    sysnc.setSyncTime(new Date());
                    sysnc.setUpdateTime(new Date());
                    sysnc.setBizType("更新");
//                    sysnc.setRemark(sysnc.getRemark()+"|更新");
                    tempMatSysncList.add(sysnc);
                }
            }
        }

        this.saveOrUpdateBatch(tempMatSysncList);

        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-do-change-stock", modifyParm);
        String stockId =null ;
        String syncStatus ="0" ;
        String syncResultMsg ="" ;
        try {
            log.info("物料[{}]-<更新>-到马帮erp请求接口时间：【{}】,请求参数{}",modifyParm.getStockSku(),new Date(),JSONUtil.toJsonStr(mabangHeadParm));
            MabangResult mabangResult = mabangRequstService.stockDoSearchSkuList(mabangHeadParm);
            syncResultMsg=JSONUtil.toJsonStr(mabangResult);
            log.info("物料[{}]-<更新>-到马帮erp接口响应时间：【{}】,返回结果{}",modifyParm.getStockSku(),new Date(),syncResultMsg);
            if (ObjectUtil.isNotNull(mabangResult) && "200".equals(mabangResult.getCode())) {
                stockId= String.valueOf(JSONUtil.parseObj(JSONUtil.toJsonStr(mabangResult.getData())).get("stockId"));
                syncStatus = "1";
            }
        } catch (Exception e) {
            syncResultMsg =JSONUtil.toJsonStr(e);
            log.info("物料[{}]-<更新>-到马帮erp调用接口出现异常{}",syncResultMsg);
        }

        for (MabangMatSync matSysnc : tempMatSysncList) {
            if (ObjectUtil.isNotEmpty(stockId)) {
                matSysnc.setStockId(stockId);
            }
            matSysnc.setSyncStatus(syncStatus);
            matSysnc.setSyncTime(new Date());
            matSysnc.setSyncRequstPar(JSONUtil.toJsonStr(mabangHeadParm));
            matSysnc.setSyncResultMsg(syncResultMsg);
            matSysnc.setUpdateTime(new Date());
        }

        this.updateBatchById(tempMatSysncList);
        log.info("物料[{}]-<更新>-到马帮erp的同步记录更新完成",modifyParm.getStockSku());


    }


    private void syncUpdateFromEbms(TbComMateriel materiel, List<MabangWarehouseResult> warehouseList, List<MabangMatSync> sysncLis) {


        MabangGoodsModifyParm modifyParm = new MabangGoodsModifyParm();
        modifyParm.setStockSku(materiel.getMatCode());
        String goodsName = this.getGoodsName(materiel);
        modifyParm.setNameCN(goodsName);//商品名称
        String status= "否".equals(materiel.getMatIsAvailableSale()) ? MAT_STATUS_STOP : MAT_STATUS_NORMAL;
        modifyParm.setStatus(status);//商品状态
        //商品图片处理
        String picUrl= materiel.getMatImagePath().split(";")[0].replace("192.168.2.217","jcbsc.dns0755.net:2180");
        modifyParm.setPicture(picUrl);
        modifyParm.setLength(String.valueOf(materiel.getMatNetLengthOrg()));//长
        modifyParm.setWidth(String.valueOf(materiel.getMatNetWidthOrg()));//宽
        modifyParm.setHeight(String.valueOf(materiel.getMatNetHeightOrg()));//高
        modifyParm.setWeight(String.valueOf(materiel.getMatNetWeightOrg()));//重量
//        modifyParm.setParentCategoryName("虚拟一级目录");//todo 一级目录名称
//        modifyParm.setCategoryName("虚拟二级目录");//二级目录名称 todo
        modifyParm.setDeclareName(materiel.getMatProName());////申报中文名称
//        modifyParm.setDeclareCode(materiel.getMatCustomsCode());//报关编码
//        modifyParm.setPurchasePrice("1");//最新采购价
        modifyParm.setBrandName(materiel.getMatComBrand());//brandName
        modifyParm.setHasBattery("是".equals(materiel.getMatIsCharged()) ? "1" : "2");//带电池 1.是 2.否
//        modifyParm.setDefaultCost("1");//统一成本价
//            goodsAddParm.setBuyerId(materiel.getMatProcurementStaff());//采购员名称
//        modifyParm.setBuyerId("虚拟采购员");//采购员名称
//            goodsAddParm.setDeveloperName(materiel.getMatDeveloper());//开发员名称
//            goodsAddParm.setSalesName(materiel.getMatSpecificOperators());//销售员名称

//        modifyParm.setCreatorName("深圳市金畅创新技术有限公司");//创建员工

        //Json字符串
        //关联供应商信息 更新的时候 不需要更新（新增的时候也是固定的）
       /* MabangGoodsSupplierParm supplierParm = new MabangGoodsSupplierParm();
        supplierParm.setName("虚拟供应商");
        supplierParm.setProductLinkAddress("虚拟供应商地址");
        supplierParm.setFlag("1");
        ArrayList<MabangGoodsSupplierParm> supplierParmList = new ArrayList<>();
        supplierParmList.add(supplierParm);
        modifyParm.setSuppliersData(JSONUtil.toJsonStr(supplierParmList));*/

        //仓库信息
        ArrayList<MabangGoodsWarehouseParm> warehouseParmArrayList = new ArrayList<>();
        for (MabangWarehouseResult warehouse : warehouseList) {
            MabangGoodsWarehouseParm warehouseParm = new MabangGoodsWarehouseParm();
            warehouseParm.setName(warehouse.getName());
            warehouseParm.setStockCost("1");//仓库成本价
            warehouseParm.setPurchaseDays(materiel.getMatProCycle());//采购天数
            warehouseParm.setMinPurchaseQuantity(String.valueOf(materiel.getMatMinOrderQty()));//最小采购数量
            warehouseParm.setIsDefault("2");//1:默认仓库2.非默认仓库
            if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                warehouseParm.setIsDefault("1");//1:默认仓库2.非默认仓库
            }
            warehouseParmArrayList.add(warehouseParm);
        }
        modifyParm.setWarehouseData(JSONUtil.toJsonStr(warehouseParmArrayList));


        //更新同步记录
        ArrayList<MabangMatSync> tempMatSysncList = new ArrayList<>();

        if (ObjectUtil.isEmpty(sysncLis)) { //物料已经通过EXCEL同步到马帮erp,现在物料更新，补上同步记录
            for (MabangWarehouseResult warehouse : warehouseList) {
                //生成同步信息
                MabangMatSync mabangMatSync = new MabangMatSync();
                mabangMatSync.setMatCode(materiel.getMatCode());
                mabangMatSync.setStockSku(materiel.getMatCode());
                mabangMatSync.setMatName(materiel.getMatProName());
                mabangMatSync.setMergeMatName(goodsName);
                mabangMatSync.setBizType("更新");
                mabangMatSync.setStatus(status);
                mabangMatSync.setMabangWarehouseId(warehouse.getId());
                mabangMatSync.setMabangWarehouseName(warehouse.getName());
                mabangMatSync.setMatCreateTime(materiel.getMatCreatDate());
                mabangMatSync.setMatUpdateTime(materiel.getMatLastUpdateDate());
                mabangMatSync.setParentCategoryName("虚拟一级目录");//todo 一级目录
                mabangMatSync.setCategoryName("虚拟二级目录");//todo 二级目录
                mabangMatSync.setIsDefault("2");//1:默认仓库2.非默认仓库
                if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                    mabangMatSync.setIsDefault("1");//1:默认仓库2.非默认仓库
                }
                mabangMatSync.setId(IdWorker.getIdStr());
                mabangMatSync.setRemark("物料已经通过EXCEL同步到马帮erp,现在物料更新，补上同步记录");
                tempMatSysncList.add(mabangMatSync);
            }
        } else { //已经有同步记录了，需要判断同步仓库的信息是否全

            for (MabangWarehouseResult warehouse : warehouseList) {
                MabangMatSync sysnc = sysncLis.stream().filter(s -> warehouse.getId().equals(s.getMabangWarehouseId())).findAny().orElse(null);
                if (ObjectUtil.isNull(sysnc)) {
                    MabangMatSync mabangMatSync = new MabangMatSync();
                    mabangMatSync.setMatCode(materiel.getMatCode());
                    mabangMatSync.setStockSku(materiel.getMatCode());
                    mabangMatSync.setMatName(materiel.getMatProName());
                    mabangMatSync.setMergeMatName(goodsName);
                    mabangMatSync.setBizType("更新");
                    mabangMatSync.setStatus(status);
                    mabangMatSync.setMabangWarehouseId(warehouse.getId());
                    mabangMatSync.setMabangWarehouseName(warehouse.getName());
                    mabangMatSync.setMatCreateTime(materiel.getMatCreatDate());
                    mabangMatSync.setMatUpdateTime(materiel.getMatLastUpdateDate());
                    mabangMatSync.setParentCategoryName("虚拟一级目录");//todo 一级目录
                    mabangMatSync.setCategoryName("虚拟二级目录");//todo 二级目录
                    mabangMatSync.setIsDefault("2");//1:默认仓库2.非默认仓库
                    if (warehouse.getName().contains("雁田定制仓") || warehouse.getId().equals("1047844")) {
                        mabangMatSync.setIsDefault("1");//1:默认仓库2.非默认仓库
                    }
                    mabangMatSync.setId(IdWorker.getIdStr());
                    mabangMatSync.setRemark("物料已经通过EXCEL同步到马帮erp,现在物料更新，补上同步记录");
                    tempMatSysncList.add(mabangMatSync);
                } else {
                    sysnc.setMatCreateTime(materiel.getMatCreatDate());
                    sysnc.setMatUpdateTime(materiel.getMatLastUpdateDate());
                    sysnc.setSyncTime(new Date());
                    sysnc.setUpdateTime(new Date());
                    sysnc.setBizType("更新");
//                    sysnc.setRemark(sysnc.getRemark()+"|更新");
                    tempMatSysncList.add(sysnc);
                }
            }
        }

        this.saveOrUpdateBatch(tempMatSysncList);

        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-do-change-stock", modifyParm);
        String stockId =null ;
        String syncStatus ="0" ;
        String syncResultMsg ="" ;
        try {
            log.info("物料[{}]-<更新>-到马帮erp请求接口时间：【{}】,请求参数{}",modifyParm.getStockSku(),new Date(),JSONUtil.toJsonStr(mabangHeadParm));
            MabangResult mabangResult = mabangRequstService.stockDoSearchSkuList(mabangHeadParm);
            syncResultMsg=JSONUtil.toJsonStr(mabangResult);
            log.info("物料[{}]-<更新>-到马帮erp接口响应时间：【{}】,返回结果{}",modifyParm.getStockSku(),new Date(),syncResultMsg);
            if (ObjectUtil.isNotNull(mabangResult) && "200".equals(mabangResult.getCode())) {
                stockId= String.valueOf(JSONUtil.parseObj(JSONUtil.toJsonStr(mabangResult.getData())).get("stockId"));
                syncStatus = "1";
            } else if (ObjectUtil.isNotNull(mabangResult)
                    && ObjectUtil.isNotEmpty(mabangResult.getMessage())
                    && mabangResult.getMessage().contains("已经存在")) {
                syncStatus = "1";
            }
        } catch (Exception e) {
            syncResultMsg =JSONUtil.toJsonStr(e);
            log.info("物料[{}]-<更新>-到马帮erp调用接口出现异常{}",syncResultMsg);
        }

        for (MabangMatSync matSysnc : tempMatSysncList) {
            if (ObjectUtil.isNotEmpty(stockId)) {
                matSysnc.setStockId(stockId);
            }
            matSysnc.setSyncStatus(syncStatus);
            matSysnc.setSyncTime(new Date());
            matSysnc.setSyncRequstPar(JSONUtil.toJsonStr(mabangHeadParm));
            matSysnc.setSyncResultMsg(syncResultMsg);
            matSysnc.setUpdateTime(new Date());
        }

        this.updateBatchById(tempMatSysncList);
        log.info("物料[{}]-<更新>-到马帮erp的同步记录更新完成",modifyParm.getStockSku());
    }


    private List<MabangWarehouseResult> getMabangWarehouseResults() {
        //1获取启用的仓库信息
        MabangWarehouseParam mabangWarehouseParam = new MabangWarehouseParam();
//        mabangWarehouseParam.setStatus("1");
        List<MabangWarehouseResult> warehouseList = warehouseMapper.warehouseList(mabangWarehouseParam);
        return warehouseList;
    }


    private List<MabangMatSync> getMabangMatSysnList(List<String> matCodeList, List<String> warehouseIdList) {
        List<List<String>> splitList = CollUtil.split(matCodeList, MabangConstant.BATCH_SIZE);
        List<MabangMatSync> matSysncList = new ArrayList<>();
        for (List<String> codes : splitList) {
            LambdaQueryWrapper<MabangMatSync> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(MabangMatSync::getMatCode, codes);
            wrapper.in(MabangMatSync::getMabangWarehouseId, warehouseIdList);
            matSysncList.addAll( this.baseMapper.selectList(wrapper));
        }

        return matSysncList;
    }
}
