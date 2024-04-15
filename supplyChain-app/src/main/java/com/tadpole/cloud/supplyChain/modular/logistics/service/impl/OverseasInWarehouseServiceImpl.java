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
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.RemovalShipmentDetailConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.*;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasInWarehouseDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasInWarehouseMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 *  海外仓入库管理服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Service
@Slf4j
public class OverseasInWarehouseServiceImpl extends ServiceImpl<OverseasInWarehouseMapper, OverseasInWarehouse> implements IOverseasInWarehouseService {

    @Autowired
    private OverseasInWarehouseMapper mapper;
    @Autowired
    private OverseasInWarehouseDetailMapper detailMapper;
    @Autowired
    private RemovalShipmentDetailConsumer removalShipmentDetailConsumer;
    @Autowired
    private IOverseasInWarehouseService overseasInWarehouseService;
    @Autowired
    private IOverseasInWarehouseDetailService overseasInWarehouseDetailService;
    @Autowired
    private IOverseasWarehouseManageService overseasWarehouseManageService;
    @Autowired
    private IOverseasWarehouseManageRecordService recordService;
    @Autowired
    private ISyncTransferToErpService syncTransferToErpService;
    @Autowired
    private IOverseasWarehouseAgeService ageService;
    @Autowired
    private IOverseasOutWarehouseDetailService overseasOutWarehouseDetailService;
    @Autowired
    private IOverseasOutWarehouseService overseasOutWarehouseService;
    @Value("${logistics.order.env}")
    private String env;
    @Value("${logistics.order.expires-in-value}")
    private Long expiresInValue;

    @DataSource(name = "logistics")
    @Override
    public PageResult<OverseasInWarehouseResult> queryListPage(OverseasInWarehouseParam param) {

        //1-1.箱条码、SKU、物料编码查询入库单号列表
        if(StrUtil.isEmpty(param.getInOrder())){
            if(StrUtil.isNotEmpty(param.getMaterialCode())
                    || StrUtil.isNotEmpty(param.getSku())
                    || StrUtil.isNotEmpty(param.getPackageBarCode())
                    || StrUtil.isNotEmpty(param.getLogisticsNum())
                    || CollectionUtil.isNotEmpty(param.getLogisticsStatuss())){
                QueryWrapper<OverseasInWarehouseDetail> qw = new QueryWrapper<>();
                qw.eq(StrUtil.isNotEmpty(param.getPackageBarCode()),"PACKAGE_BAR_CODE", param.getPackageBarCode())
                        .eq(StrUtil.isNotEmpty(param.getSku()),"SKU", param.getSku())
                        .eq(StrUtil.isNotEmpty(param.getMaterialCode()),"MATERIAL_CODE", param.getMaterialCode())
                        .eq(StrUtil.isNotEmpty(param.getLogisticsNum()),"LOGISTICS_NUM", param.getLogisticsNum())
                        .in(CollectionUtil.isNotEmpty(param.getLogisticsStatuss()),"LOGISTICS_STATUS", param.getLogisticsStatuss());

                List<OverseasInWarehouseDetail> list = overseasInWarehouseDetailService.list(qw);

                if(CollectionUtil.isNotEmpty(list)){
                    List<String> inOrderList= list.stream().map(OverseasInWarehouseDetail::getInOrder).distinct().collect(Collectors.toList());
                    param.setInOrders(inOrderList);
                }else{
                    param.setInOrder("NULL");
                }
            }
        }
        //1-2.空值筛选
        List<String> inlist=param.getInWarehouseNames();
        List<String> outlist=param.getOutWarehouseNames();
        if(CollectionUtil.isNotEmpty(inlist)){
            if(inlist.contains("空")){
                param.setInWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(outlist)){
            if(outlist.contains("空")) {
                param.setOutWarehouseName("空");
            }
        }
        if(StringUtils.isNotBlank(param.getCompleteEndTime())){
            param.setCompleteEndTime(param.getCompleteEndTime() + " 23:59:59");
        }
        if(StringUtils.isNotBlank(param.getConfirmEndDate())){
            param.setConfirmEndDate(param.getConfirmEndDate() + " 23:59:59");
        }
        Page pageContext = param.getPageContext();
        IPage<OverseasInWarehouseResult> page = this.baseMapper.queryListPage(pageContext, param);

        return new PageResult<>(page);
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData queryPageTotal(OverseasInWarehouseParam param) {
        //1-1.箱条码、SKU、物料编码查询入库单号列表
        if(StrUtil.isEmpty(param.getInOrder())){
            if(StrUtil.isNotEmpty(param.getMaterialCode())
                    || StrUtil.isNotEmpty(param.getSku())
                    || StrUtil.isNotEmpty(param.getPackageBarCode())
                    || StrUtil.isNotEmpty(param.getLogisticsNum())
                    || CollectionUtil.isNotEmpty(param.getLogisticsStatuss())){
                QueryWrapper<OverseasInWarehouseDetail> qw = new QueryWrapper<>();
                qw.eq(StrUtil.isNotEmpty(param.getPackageBarCode()),"PACKAGE_BAR_CODE", param.getPackageBarCode())
                        .eq(StrUtil.isNotEmpty(param.getSku()),"SKU", param.getSku())
                        .eq(StrUtil.isNotEmpty(param.getMaterialCode()),"MATERIAL_CODE", param.getMaterialCode())
                        .eq(StrUtil.isNotEmpty(param.getLogisticsNum()),"LOGISTICS_NUM", param.getLogisticsNum())
                        .in(CollectionUtil.isNotEmpty(param.getLogisticsStatuss()),"LOGISTICS_STATUS", param.getLogisticsStatuss());

                List<OverseasInWarehouseDetail> list = overseasInWarehouseDetailService.list(qw);

                if(CollectionUtil.isNotEmpty(list)){
                    List<String> inOrderList= list.stream().map(OverseasInWarehouseDetail::getInOrder).distinct().collect(Collectors.toList());
                    param.setInOrders(inOrderList);
                }else{
                    param.setInOrder("NULL");
                }
            }
        }
        //1-2.空值筛选
        List<String> inlist=param.getInWarehouseNames();
        List<String> outlist=param.getOutWarehouseNames();
        if(CollectionUtil.isNotEmpty(inlist)){
            if(inlist.contains("空")){
                param.setInWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(outlist)){
            if(outlist.contains("空")) {
                param.setOutWarehouseName("空");
            }
        }
        if(StringUtils.isNotBlank(param.getCompleteEndTime())){
            param.setCompleteEndTime(param.getCompleteEndTime() + " 23:59:59");
        }
        if(StringUtils.isNotBlank(param.getConfirmEndDate())){
            param.setConfirmEndDate(param.getConfirmEndDate() + " 23:59:59");
        }
        return ResponseData.success(this.baseMapper.queryPageTotal(param));
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData allowAllSign(OverseasInWarehouseParam param) {
        if(StringUtils.isBlank(param.getInOrder())){
            ResponseData.error("入库单号不能为空");
        }
        Map<String, Boolean> returnMap = new HashMap<>();
        Boolean allowAllSign = false;
        if(this.baseMapper.allowAllSign(param.getInOrder()) == 0){
            allowAllSign = true;
        }
        returnMap.put("allowAllSign", allowAllSign);
        return ResponseData.success(returnMap);
    }

    @DataSource(name = "logistics")
    @Override
    public PageResult<OverseasReportResult> queryReportListPage(OverseasReportParam param) {

        //1-1.空值筛选
        List<String> inlist=param.getInWarehouseNames();
        List<String> outlist=param.getOutWarehouseNames();
        List<String> transTypeList=param.getTransTypeList();
        if(CollectionUtil.isNotEmpty(inlist)){
            if(inlist.contains("空")){
                param.setInWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(outlist)){
            if(outlist.contains("空")) {
                param.setOutWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(transTypeList)){
            if(transTypeList.contains("空")) {
                param.setSuggestTransType("空");
            }
        }
        param.setOperateType(InBusinessTypeEnum.INSIDE_TO_OVERSEAS.getName());
        Page pageContext = param.getPageContext();
        IPage<OverseasReportResult> page = this.baseMapper.queryReportListPage(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "logistics")
    @Override
    public PageResult<OverseasInWarehouseDetailResult> list(OverseasInWarehouseDetailParam param) {
        Page pageContext = param.getPageContext();
        IPage<OverseasInWarehouseDetailResult> page = this.baseMapper.list(pageContext, param);
        return new PageResult<>(page);
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasInWarehouseResultVO> export(OverseasInWarehouseParam param) {
        List<String> inlist=param.getInWarehouseNames();
        List<String> outlist=param.getOutWarehouseNames();
        if(CollectionUtil.isNotEmpty(inlist)){
            if(inlist.contains("空")){
                param.setInWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(outlist)){
            if(outlist.contains("空")) {
                param.setOutWarehouseName("空");
            }
        }
        return this.baseMapper.allList(param);
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasReportResult> exportOverseasReport(OverseasReportParam param) {
        //1-1.空值筛选
        List<String> inlist=param.getInWarehouseNames();
        List<String> outlist=param.getOutWarehouseNames();
        List<String> transTypeList=param.getTransTypeList();
        if(CollectionUtil.isNotEmpty(inlist)){
            if(inlist.contains("空")){
                param.setInWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(outlist)){
            if(outlist.contains("空")) {
                param.setOutWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(transTypeList)){
            if(transTypeList.contains("空")) {
                param.setSuggestTransType("空");
            }
        }
        param.setOperateType(InBusinessTypeEnum.INSIDE_TO_OVERSEAS.getName());
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        List<OverseasReportResult> records = this.baseMapper.queryReportListPage(pageContext, param).getRecords();
        return records;
    }

    @Override
    @DataSource(name = "logistics")
    public List<Map<String, Object>> outWarehouseSelect() {
        QueryWrapper<OverseasInWarehouse> wrapper = new QueryWrapper<>();
        wrapper.select("OUT_WAREHOUSE_NAME").groupBy("OUT_WAREHOUSE_NAME").orderByDesc("OUT_WAREHOUSE_NAME");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        if(selectList.contains(null)){
            Map<String, Object> map = new HashMap<>();
            map.put("OUT_WAREHOUSE_NAME", "空");
            Collections.replaceAll(selectList, null, map);
        }
        return selectList;
    }

    @Override
    @DataSource(name = "logistics")
    public List<Map<String, Object>> suggestTransTypeSelect() {
        QueryWrapper<OverseasInWarehouseDetail> wrapper = new QueryWrapper<>();
        wrapper.select("SUGGEST_TRANS_TYPE").groupBy("SUGGEST_TRANS_TYPE").orderByDesc("SUGGEST_TRANS_TYPE");
        List<Map<String, Object>> selectList = overseasInWarehouseDetailService.listMaps(wrapper);
        if(selectList.contains(null)){
            Map<String, Object> map = new HashMap<>();
            map.put("SUGGEST_TRANS_TYPE", "空");
            Collections.replaceAll(selectList, null, map);
        }
        return selectList;
    }

    @Override
    @DataSource(name = "logistics")
    public List<Map<String, Object>> inWarehouseSelect() {
        QueryWrapper<OverseasInWarehouse> wrapper = new QueryWrapper<>();
        wrapper.select("IN_WAREHOUSE_NAME").groupBy("IN_WAREHOUSE_NAME").orderByDesc("IN_WAREHOUSE_NAME");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        if(selectList.contains(null)){
            Map<String, Object> map = new HashMap<>();
            map.put("IN_WAREHOUSE_NAME", "空");
            Collections.replaceAll(selectList, null, map);
        }
        return selectList;
    }

    @Override
    @DataSource(name = "logistics")
    public List<String> matCodeList(OverseasInWarehouseDetailParam param) {
        return mapper.matCodeList(param.getInOrder());
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(OverseasInWarehouseParam param) {

        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        if(ObjectUtil.isNull(param.getId())){
            return ResponseData.error("ID不能为空!");
        }
        OverseasInWarehouse overseasInWarehouse = this.getById(param.getId());
        if(overseasInWarehouse == null){
            return ResponseData.error("不存在此记录，编辑收货仓失败!");
        }
        //原收货仓名称
        String oldInWarehouseName = overseasInWarehouse.getInWarehouseName();
        if(!OperateTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(overseasInWarehouse.getOperateType())){
            return ResponseData.error("非亚马逊仓发海外仓业务不支持编辑收货仓!");
        }
        if(ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(overseasInWarehouse.getConfirmStatus())){
            return ResponseData.error("已签收数据不能编辑收货仓，编辑收货仓失败!");
        }
        //针对JP站点部分签收，不能修改收货仓名称
        if(ConfirmStatusEnum.PART_CONFIRM.getName().equals(overseasInWarehouse.getConfirmStatus())
                && !Objects.equals(param.getInWarehouseName(), oldInWarehouseName)){
            return ResponseData.error("部分签收数据不能修改收货仓名称，编辑收货仓失败!");
        }

        //新收货仓名称与原收货仓名称一致，不做更新，返回异常提示
        if(Objects.equals(param.getInWarehouseName(), oldInWarehouseName)){
            return ResponseData.error("收货仓名称不能与原收货仓名称相同，编辑收货仓失败!");
        }

        //更新海外仓入库管理收货仓名称
        OverseasInWarehouse ent = new OverseasInWarehouse();
        ent.setId(param.getId());
        ent.setInWarehouseName(param.getInWarehouseName());
        ent.setUpdateUser(accountAndName);
        ent.setUpdateTime(DateUtil.date());
        this.baseMapper.updateWareNameById(ent);
        return ResponseData.success("保存成功！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData editRemark(OverseasInWarehouseParam param) {

        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        if(ObjectUtil.isNull(param.getId())){
            return ResponseData.error("ID不能为空!");
        }
        OverseasInWarehouse ent=new OverseasInWarehouse();
        ent.setId(param.getId());
        ent.setRemark(param.getRemark());
        ent.setUpdateUser(accountAndName);
        ent.setUpdateTime(DateUtil.date());
        this.updateById(ent);

        return ResponseData.success("保存成功！");
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSignStatus(OverseasInWarehouse param,String businessType) throws Exception{

        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        try{
            LambdaUpdateWrapper<OverseasInWarehouse> warehouseWrapper=new LambdaUpdateWrapper<>();

            warehouseWrapper.eq(OverseasInWarehouse::getId,param.getId())
                    .set(OverseasInWarehouse::getUpdateTime,DateUtil.date())
                    .set(ObjectUtil.isEmpty(param.getConfirmStartTime()),OverseasInWarehouse::getConfirmStartTime,DateUtil.date())
                    .set(OverseasInWarehouse::getConfirmEndTime,DateUtil.date())
                    .set(OverseasInWarehouse::getConfirmUser,accountAndName)
                    .set(OverseasInWarehouse::getUpdateUser,accountAndName)
                    .set(OverseasInWarehouse::getConfirmStatus,businessType);

            mapper.update(null,warehouseWrapper);
            log.info("海外仓入库单号[{}],签收状态更新成功！",param.getInOrder());
        }catch(Exception e){
            log.error("海外仓入库单号[{}],签收状态更新失败！信息：[{}]",param.getInOrder(),e.getMessage());
        }
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData sign(OverseasInWarehouseParam overseasInWarehouseParam,OverseasInWarehouseDetailParam param) throws Exception{

        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;
        List<OverseasInWarehouseDetail> signList=new ArrayList<>();

        if(ObjectUtil.isNull(param.getId()) || StrUtil.isEmpty(param.getInOrder()) || StrUtil.isEmpty(param.getAllSign())){
            return ResponseData.error("ID、入库单号、签收类型不能为空!");
        }

        LambdaQueryWrapper<OverseasInWarehouse> query=new LambdaQueryWrapper<>();
        query.eq(OverseasInWarehouse::getId,param.getId());
        OverseasInWarehouse inWarehouse = overseasInWarehouseService.getOne(query);
        if(inWarehouse == null){
            return ResponseData.error("入库信息不存在，签收失败！");
        }
        if(ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(inWarehouse.getConfirmStatus())){
            return ResponseData.error("入库信息已经签收完成，不允许重复签收！");
        }
        inWarehouse.setUpdateSysShopsName(overseasInWarehouseParam.getUpdateSysShopsName());
        inWarehouse.setConfirmUpdateStatus(overseasInWarehouseParam.getConfirmUpdateStatus());
        inWarehouse.setConfirmUpdateQuantity(overseasInWarehouseParam.getConfirmUpdateQuantity());
        inWarehouse.setNewSysShopsName(overseasInWarehouseParam.getNewSysShopsName());
        inWarehouse.setConfirmNewStatus(overseasInWarehouseParam.getConfirmNewStatus());
        inWarehouse.setConfirmNewQuantity(overseasInWarehouseParam.getConfirmNewQuantity());
        inWarehouse.setInWarehouseName(overseasInWarehouseParam.getInWarehouseName());
        BeanUtils.copyProperties(inWarehouse,overseasInWarehouseParam);

        /**
         * 0：国内仓发海外仓全部签收，1：国内仓发海外仓部分签收，2亚马逊发海外仓签收
         * 针对【国内仓发往海外仓】，物流跟踪状态为空或者已发货（为空或者已发货，实际物流还没真正发货），不能签收
         */
        //1.批量出货/亚马逊出货->箱件签收
        //2.亚马逊仓发海外仓->sku签收
        switch (param.getAllSign()){
            case "0":
                //判断物流状态为null或者是已发货的，不允许全部签收
                LambdaQueryWrapper<OverseasInWarehouseDetail> detailQueryWrapper = new LambdaQueryWrapper();
                detailQueryWrapper.eq(OverseasInWarehouseDetail :: getInOrder, param.getInOrder())
                        .eq(OverseasInWarehouseDetail::getConfirmStatus,ConfirmStatusEnum.NOT_CONFIRM.getName())
                        .and(wrapper ->
                                wrapper.eq(OverseasInWarehouseDetail :: getLogisticsStatus, LogisticsTrackStatusEnum.STATUS_1.getName())
                                .or()
                                .isNull(OverseasInWarehouseDetail :: getLogisticsStatus)
                        );
                if(detailMapper.selectCount(detailQueryWrapper) > 0){
                    return ResponseData.error("物流状态不能为已发货或者为空，全部签收失败!");
                }

                //1-1-1.签收明细--->>>到货数量
                LambdaUpdateWrapper<OverseasInWarehouseDetail> detailWrapper=new LambdaUpdateWrapper<>();
                detailWrapper.eq(OverseasInWarehouseDetail::getInOrder,param.getInOrder())
                        .eq(OverseasInWarehouseDetail::getConfirmStatus,ConfirmStatusEnum.NOT_CONFIRM.getName())
//                            .eq(OverseasInWarehouseDetail::getLogisticsStatus,LogisticsTrackStatusEnum.STATUS_1.getName())
                        .set(OverseasInWarehouseDetail::getConfirmTime, DateUtil.date())
                        .set(OverseasInWarehouseDetail::getConfirmUser,accountAndName)
                        .set(OverseasInWarehouseDetail::getUpdateTime,DateUtil.date())
                        .set(OverseasInWarehouseDetail::getConfirmStatus,ConfirmStatusEnum.ALREADY_CONFIRM.getName())
                        .set(OverseasInWarehouseDetail::getUpdateUser,accountAndName)
                        .setSql("ACTUAL_QUANTITY = QUANTITY, USER_ACTUAL_QUANTITY = QUANTITY");
                detailMapper.update(null,detailWrapper);

                //签收明细
                LambdaQueryWrapper<OverseasInWarehouseDetail> signQuery=new LambdaQueryWrapper<>();
                signQuery.eq(OverseasInWarehouseDetail::getInOrder,param.getInOrder());
                signList=overseasInWarehouseDetailService.list(signQuery);

                //1-1-2.全部签收--->>>更新签收状态
                updateSignStatus(inWarehouse,ConfirmStatusEnum.ALREADY_CONFIRM.getName());
                break;
            case "1":
                //查询明细数据并做校验
                List<BigDecimal> detailIdList = new ArrayList<>();
                if(CollectionUtil.isEmpty(param.getDetailList())){
                    return ResponseData.error("签收明细数据为空，入库单号：" + param.getInOrder());
                }
                for (OverseasInWarehouseDetail paramDetail : param.getDetailList()) {
                    detailIdList.add(paramDetail.getId());
                    if(BigDecimal.ZERO.compareTo(paramDetail.getActualQuantity()) > 0){
                        return ResponseData.error("签收数量不能为负数，签收失败！入库单号：" + paramDetail.getInOrder() + "，箱号：" + paramDetail.getPackageNum());
                    }
                    if(paramDetail.getQuantity().compareTo(paramDetail.getActualQuantity()) < 0){
                        return ResponseData.error("签收数量只能小于等于应入库的数量，签收失败！入库单号：" + paramDetail.getInOrder() + "，箱号：" + paramDetail.getPackageNum());
                    }
                    paramDetail.setUserActualQuantity(paramDetail.getActualQuantity());
                }
                
                List<OverseasInWarehouseDetail> resultDetailList = detailMapper.selectBatchIds(detailIdList);
                for (OverseasInWarehouseDetail inDetail : resultDetailList) {
                    if(ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(inDetail.getConfirmStatus())){
                        return ResponseData.error("存在签收完成的数据，入库单号：" + inDetail.getInOrder() + "，箱号：" + inDetail.getPackageNum());
                    }

                    if(StringUtils.isBlank(inDetail.getLogisticsNum()) || LogisticsTrackStatusEnum.STATUS_1.getName().equals(inDetail.getLogisticsNum())){
                        return ResponseData.error("物流单号不能为空且物流状态不能为已发货或者为空，部分签收失败!入库单号：" + inDetail.getInOrder() + "，箱号：" + inDetail.getPackageNum());
                    }
                }

                signList=param.getDetailList();

                //1-2-1.签收明细--->>>到货数量
                if(CollectionUtil.isNotEmpty(param.getDetailList())){
                    List<OverseasInWarehouseDetail> detailList=param.getDetailList();
                    for(OverseasInWarehouseDetail ent:detailList){
                        LambdaUpdateWrapper<OverseasInWarehouseDetail> dataWrapper=new LambdaUpdateWrapper<>();
                        dataWrapper.eq(OverseasInWarehouseDetail::getId,ent.getId())
                                .eq(OverseasInWarehouseDetail::getConfirmStatus,ConfirmStatusEnum.NOT_CONFIRM.getName())
                                .set(OverseasInWarehouseDetail::getConfirmTime, DateUtil.date())
                                .set(OverseasInWarehouseDetail::getUserActualQuantity,ent.getUserActualQuantity())
                                .set(OverseasInWarehouseDetail::getConfirmUser,accountAndName)
                                .set(OverseasInWarehouseDetail::getUpdateTime,DateUtil.date())
                                .set(OverseasInWarehouseDetail::getUpdateUser,accountAndName)
                                .set(OverseasInWarehouseDetail::getConfirmStatus,ConfirmStatusEnum.ALREADY_CONFIRM.getName())
                                .setSql("ACTUAL_QUANTITY = QUANTITY");
                        detailMapper.update(null,dataWrapper);
                    }
                }
                //1-2-2.部分签收--->>>更新签收状态
                //param.setOperateType("1");
                LambdaQueryWrapper<OverseasInWarehouseDetail> detailQuery=new LambdaQueryWrapper();
                detailQuery.eq(OverseasInWarehouseDetail::getInOrder,param.getInOrder())
                        .eq(OverseasInWarehouseDetail::getConfirmStatus,ConfirmStatusEnum.NOT_CONFIRM.getName());

                if(overseasInWarehouseDetailService.count(detailQuery)>0){
                    updateSignStatus(inWarehouse,ConfirmStatusEnum.PART_CONFIRM.getName());
                }
                else{
                    updateSignStatus(inWarehouse,ConfirmStatusEnum.ALREADY_CONFIRM.getName());
                }
                break;
            case "2":
                if(StrUtil.isEmpty(param.getSku()) || StrUtil.isEmpty(param.getFnSku())){
                    return ResponseData.error("SKU、FNSKU不为空，签收失败！");
                }

                //用户实际签收的值
                BigDecimal userActualQuantity = null;
                //主表
                LambdaUpdateWrapper<OverseasInWarehouse> updateWrapper = new LambdaUpdateWrapper<>();
                //明细表
                LambdaUpdateWrapper<OverseasInWarehouseDetail> listWrapper = new LambdaUpdateWrapper<>();
                if("JP".equals(param.getSysSite())){
                    //JP站点签收
                    if(StringUtils.isNotBlank(overseasInWarehouseParam.getUpdateSysShopsName())
                            && !ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(overseasInWarehouseParam.getConfirmUpdateStatus())){
                        //原账号签收，签收数量==应入库数量时，签收完成； 签收数量<应入库数量时，签收数量为应入库数量，同时生成一条盘亏记录
                        if(param.getActualQuantity() == null){
                            return ResponseData.error("签收数量不能为空，签收失败！");
                        }
                        if(BigDecimal.ZERO.compareTo(param.getActualQuantity()) > 0){
                            return ResponseData.error("签收数量不能为负数，签收失败！");
                        }
                        if(overseasInWarehouseParam.getShouldInventoryQuantity().compareTo(param.getActualQuantity()) < 0){
                            return ResponseData.error("签收数量不能大于应入库数量，签收失败！");
                        }
                        updateWrapper.set(OverseasInWarehouse::getUpdateSysShopsName, overseasInWarehouseParam.getUpdateSysShopsName())
                                .set(OverseasInWarehouse::getConfirmUpdateStatus, ConfirmStatusEnum.ALREADY_CONFIRM.getName())
                                .set(OverseasInWarehouse::getConfirmUpdateQuantity, param.getActualQuantity())
                                .set(OverseasInWarehouse::getConfirmUpdateTime, DateUtil.date());
                        overseasInWarehouseParam.setConfirmUpdateQuantity(param.getActualQuantity());//原账号实际签收值

                        userActualQuantity = param.getActualQuantity();
                        listWrapper.set(OverseasInWarehouseDetail::getUserActualQuantity,param.getActualQuantity());//记录用户实际签收值
                    } else if (StringUtils.isNotBlank(overseasInWarehouseParam.getNewSysShopsName())
                            && !ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(overseasInWarehouseParam.getConfirmNewStatus())){
                        //新账号（TS）签收，签收数量==应入库数量时，签收完成； 签收数量<应入库数量时，签收完成，同时生成一条TS签收记录和原账号签收记录
                        if(param.getActualNewQuantity() == null){
                            return ResponseData.error("签收数量不能为空，签收失败！");
                        }
                        if(BigDecimal.ZERO.compareTo(param.getActualNewQuantity()) > 0){
                            return ResponseData.error("签收数量不能为负数，签收失败！");
                        }
                        if(overseasInWarehouseParam.getShouldInventoryQuantity().compareTo(param.getActualNewQuantity()) < 0){
                            return ResponseData.error("签收数量不能大于应入库数量，签收失败！");
                        }
                        if(overseasInWarehouseParam.getShouldInventoryQuantity().compareTo(param.getActualNewQuantity()) > 0){
                            //生成原账号签收单
                            BigDecimal subQuantity = overseasInWarehouseParam.getShouldInventoryQuantity().subtract(param.getActualNewQuantity());
                            updateWrapper.set(OverseasInWarehouse::getUpdateSysShopsName, overseasInWarehouseParam.getSysShopsName())
                                    .set(OverseasInWarehouse::getConfirmUpdateStatus, ConfirmStatusEnum.ALREADY_CONFIRM.getName())
                                    .set(OverseasInWarehouse::getConfirmUpdateQuantity, subQuantity)
                                    .set(OverseasInWarehouse::getConfirmUpdateTime, DateUtil.date());
                            overseasInWarehouseParam.setConfirmUpdateQuantity(param.getQuantity().subtract(param.getActualNewQuantity()));//原账号实际签收值
                            overseasInWarehouseParam.setUpdateSysShopsName(overseasInWarehouseParam.getSysShopsName());//原账号

                        }
                        updateWrapper.set(OverseasInWarehouse::getNewSysShopsName, overseasInWarehouseParam.getNewSysShopsName())
                                .set(OverseasInWarehouse::getConfirmNewStatus, ConfirmStatusEnum.ALREADY_CONFIRM.getName())
                                .set(OverseasInWarehouse::getConfirmNewQuantity, param.getActualNewQuantity())
                                .set(OverseasInWarehouse::getConfirmNewTime, DateUtil.date());
                        overseasInWarehouseParam.setConfirmNewQuantity(param.getActualNewQuantity());
                        userActualQuantity = param.getActualNewQuantity();
                        listWrapper.set(OverseasInWarehouseDetail::getUserActualQuantity,param.getActualNewQuantity());//记录用户实际签收值
                    }
                } else {
                    //非JP站点签收
                    if(param.getActualQuantity() == null){
                        return ResponseData.error("签收数量不能为空，签收失败！");
                    }
                    if(BigDecimal.ZERO.compareTo(param.getActualQuantity()) > 0){
                        return ResponseData.error("签收数量不能为负数，签收失败！");
                    }
                    if(overseasInWarehouseParam.getShouldInventoryQuantity().compareTo(param.getActualQuantity()) < 0){
                        return ResponseData.error("签收数量不能大于应入库数量，签收失败！");
                    }

                    userActualQuantity = param.getActualQuantity();
                    listWrapper.set(OverseasInWarehouseDetail::getUserActualQuantity,param.getActualQuantity());//记录用户实际签收值
                }

                OverseasInWarehouseDetail ent = new OverseasInWarehouseDetail();
                BeanUtils.copyProperties(param,ent);
                ent.setUserActualQuantity(userActualQuantity);
                signList.add(ent);

                //2-1-1.签收明细--->>>实际到货数量增加
                listWrapper.eq(OverseasInWarehouseDetail::getInOrder,param.getInOrder())
                        .eq(OverseasInWarehouseDetail::getSku,param.getSku())
                        .eq(OverseasInWarehouseDetail::getFnSku,param.getFnSku())
                        .set(OverseasInWarehouseDetail::getConfirmUser,accountAndName)
                        .set(OverseasInWarehouseDetail::getConfirmTime, DateUtil.date())
                        .set(OverseasInWarehouseDetail::getUpdateTime,DateUtil.date())
                        .set(OverseasInWarehouseDetail::getUpdateUser,accountAndName)
                        .set(OverseasInWarehouseDetail::getConfirmStatus,ConfirmStatusEnum.ALREADY_CONFIRM.getName())
                        .setSql("ACTUAL_QUANTITY = QUANTITY");
                detailMapper.update(null,listWrapper);

                //2-1-2.sku签收--->>>更新签收状态
                updateSignStatus(inWarehouse,ConfirmStatusEnum.ALREADY_CONFIRM.getName());

                //3-1.已到货数量=应入库数量
                updateWrapper.eq(OverseasInWarehouse::getId,param.getId())
                        .set(OverseasInWarehouse::getUpdateTime,DateUtil.date())
                        .set(OverseasInWarehouse::getUpdateUser,accountAndName)
                        .set(OverseasInWarehouse::getInWarehouseName,overseasInWarehouseParam.getInWarehouseName())
                        .set(OverseasInWarehouse::getNotInventoryQuantity,BigDecimal.ZERO)
                        .setSql("ALREADY_INVENTORY_QUANTITY = SHOULD_INVENTORY_QUANTITY");
                mapper.update(null,updateWrapper);

                //3-3.海外仓库存管理->签收完成库存增加
                ResponseData responseData = inWarehouseAddInventory(overseasInWarehouseParam, signList, accountAndName);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return responseData;
                }
                break;
        }

        if(param.getAllSign().equals("0") || param.getAllSign().equals("1")){
            //3-1.已到货数量=明细已签收数量汇总
            BigDecimal alreadyInvQty = mapper.signTotalQty(param.getInOrder(),ConfirmStatusEnum.ALREADY_CONFIRM.getName());

            //3-2.未到货数量=应入库数量-已到货数量
            if(ObjectUtil.isNotNull(overseasInWarehouseParam.getShouldInventoryQuantity()) && ObjectUtil.isNotNull(alreadyInvQty)){
                //应入库数量
                //BigDecimal shouldInventoryQty=inWarehouse.getShouldInventoryQuantity();

                LambdaUpdateWrapper<OverseasInWarehouse> updateWrapper=new LambdaUpdateWrapper<>();
                updateWrapper.eq(OverseasInWarehouse::getId,param.getId())
                        .set(OverseasInWarehouse::getUpdateTime,DateUtil.date())
                        .set(OverseasInWarehouse::getUpdateUser,accountAndName)
                        .set(OverseasInWarehouse::getAlreadyInventoryQuantity,alreadyInvQty)
                        .set(OverseasInWarehouse::getNotInventoryQuantity,overseasInWarehouseParam.getShouldInventoryQuantity().subtract(alreadyInvQty));
                mapper.update(null,updateWrapper);

                //3-3.海外仓库存管理->签收完成库存增加
                ResponseData responseData = inWarehouseAddInventory(overseasInWarehouseParam, signList, accountAndName);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return responseData;
                }
            }
        }
        return ResponseData.success("签收成功！");
    }

    /**
     * 海外仓库存管理帐存处理
     * @param inWarehouse
     * @param params
     * @param accountAndName
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseData inWarehouseAddInventory(OverseasInWarehouseParam inWarehouse,List<OverseasInWarehouseDetail> params, String accountAndName) throws Exception{
        //库存管理
        if(CollectionUtil.isNotEmpty(params)){
            //盘亏集合
            List<OverseasInWarehouseDetail> checkSubList = new ArrayList<>();
            for(OverseasInWarehouseDetail detailParam : params){
                String platform = "";
                String sysShopsName = "";
                //亚马逊发海外仓JP站点同时签收多账号：新签收账号TS
                LambdaQueryWrapper<OverseasWarehouseManage> newQueryWrapper = new LambdaQueryWrapper<>();

                //亚马逊仓发海外仓
                if(InBusinessTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(inWarehouse.getOperateType())){
                    if("JP".equals(inWarehouse.getSysSite())){
                        //亚马逊发海外仓JP站点签收
                        //签收2个账号：原账号和新账号
                        if(StringUtils.isNotBlank(inWarehouse.getUpdateSysShopsName()) && StringUtils.isNotBlank(inWarehouse.getNewSysShopsName())){
                            //原账号处理
                            platform = inWarehouse.getPlatform();
                            sysShopsName = inWarehouse.getUpdateSysShopsName();

                            //新账号处理
                            newQueryWrapper.eq(OverseasWarehouseManage :: getPlatform, inWarehouse.getNewPlatform())
                                    .eq(OverseasWarehouseManage :: getSysShopsName, inWarehouse.getNewSysShopsName())
                                    .eq(OverseasWarehouseManage :: getSysSite, inWarehouse.getSysSite())
                                    .eq(OverseasWarehouseManage :: getWarehouseName, inWarehouse.getInWarehouseName())
                                    .eq(OverseasWarehouseManage :: getFnSku, detailParam.getMaterialCode());//如果平台是Rakuten，FNSKU值为物料编码
                            OverseasWarehouseManage nqw = overseasWarehouseManageService.getOne(newQueryWrapper);
                            if(ObjectUtil.isEmpty(nqw)){
                                //亚马逊仓发海外仓没有账存信息则初始化海外仓管理账存信息，并修改
                                OverseasWarehouseManage newManage = new OverseasWarehouseManage();
                                newManage.setPlatform(inWarehouse.getNewPlatform());
                                newManage.setSysShopsName(inWarehouse.getNewSysShopsName());
                                newManage.setSysSite(inWarehouse.getSysSite());
                                newManage.setWarehouseName(inWarehouse.getInWarehouseName());//新的收货仓名称
                                newManage.setFnSku(detailParam.getMaterialCode());
                                newManage.setSku(detailParam.getSku());
                                newManage.setMaterialCode(detailParam.getMaterialCode());
                                newManage.setComeQuantity(BigDecimal.ZERO);
                                newManage.setInventoryQuantity(BigDecimal.ZERO);
                                newManage.setCreateUser(accountAndName);
                                newManage.setCreateTime(DateUtil.date());
                                newManage.setDataSource(InitDataSourceEnum.FBA_TO_OVERSEAS.getCode());
                                overseasWarehouseManageService.save(newManage);
                                nqw = newManage;
                            }

                            //新账号海外仓入库管理->库存增加
                            //注(产品需求)：针对于乐天平台，海外仓入库时候不校验SKU(只校验物料编码+FNSKU)
                            LambdaUpdateWrapper<OverseasWarehouseManage> newWarehouseWrapper = new LambdaUpdateWrapper<>();
                            newWarehouseWrapper.eq(OverseasWarehouseManage :: getId, nqw.getId())
                                    .set(OverseasWarehouseManage :: getUpdateUser, accountAndName)
                                    .set(OverseasWarehouseManage :: getUpdateTime, DateUtil.date())
                                    .set(OverseasWarehouseManage :: getInventoryQuantity, nqw.getInventoryQuantity().add(inWarehouse.getConfirmNewQuantity()));
                            overseasWarehouseManageService.update(null, newWarehouseWrapper);

                            //新账号TS新增操作记录
                            OverseasWarehouseManageRecord inTsRecord = new OverseasWarehouseManageRecord();
                            BeanUtils.copyProperties(detailParam, inTsRecord);
                            inTsRecord.setParentId(nqw.getId());
                            inTsRecord.setSysSite(inWarehouse.getSysSite());
                            inTsRecord.setWarehouseName(inWarehouse.getInWarehouseName());
                            inTsRecord.setOperateType(inWarehouse.getOperateType());
                            inTsRecord.setOperateTypeDetail(inWarehouse.getOperateType());
                            inTsRecord.setBusinessType(OverseasBusinessTypeEnum.IN.getName()); //入库
                            inTsRecord.setInventoryQuantity(nqw.getInventoryQuantity());
                            inTsRecord.setChangeInventoryQuantity(inWarehouse.getConfirmNewQuantity());
                            inTsRecord.setNowInventoryQuantity(nqw.getInventoryQuantity().add(inWarehouse.getConfirmNewQuantity()));
                            inTsRecord.setComeQuantity(nqw.getComeQuantity()); //原来货数量
                            inTsRecord.setChangeComeQuantity(BigDecimal.ZERO);
                            inTsRecord.setNowComeQuantity(nqw.getComeQuantity());
                            inTsRecord.setCreateTime(DateUtil.date());
                            inTsRecord.setCreateUser(accountAndName);
                            inTsRecord.setOutOrg(inWarehouse.getOutOrg());
                            inTsRecord.setOutOrgName(inWarehouse.getOutOrgName());
                            inTsRecord.setIsChangeOrg("0");
                            inTsRecord.setIsChangeMaterialCode("0");
                            inTsRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                            if(StringUtils.isBlank(inWarehouse.getRemark())){
                                inTsRecord.setRemark(inWarehouse.getSysRemark());
                            } else {
                                inTsRecord.setRemark(inWarehouse.getRemark() + inWarehouse.getSysRemark());
                            }
                            inTsRecord.setPlatform(inWarehouse.getNewPlatform());
                            inTsRecord.setSysShopsName(inWarehouse.getNewSysShopsName());
                            inTsRecord.setInOrder(inWarehouse.getInOrder() + "_" + inWarehouse.getNewSysShopsName());
                            inTsRecord.setInOrg(inWarehouse.getInTsOrg());
                            inTsRecord.setInOrgName(inWarehouse.getInTsOrgName());
                            inTsRecord.setFnSku(nqw.getFnSku());
                            recordService.save(inTsRecord);

                            //推送ERP->创建跨组织调拨单 业务类型亚马逊仓发海外仓
                            try{
                                ResponseData responseData = syncTransferToErpService.save(inTsRecord.getId(),null, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS_OVER);
                                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                                    log.error("亚马逊仓发海外仓创建调拨单同步异常！操作记录ID：[{}]，信息：[{}]", inTsRecord.getId(), responseData.getMessage());
                                }
                            }catch(Exception e){
                                log.error("亚马逊仓发海外仓创建调拨单同步异常！操作记录ID：[{}]，信息：[{}]", inTsRecord.getId(), e.getMessage());
                            }

                            //处理库龄报表入库
                            List<OverseasWarehouseAge> ageInParam = new ArrayList<>();
                            OverseasWarehouseAge ageIn = new OverseasWarehouseAge();
                            ageIn.setPlatform(inTsRecord.getPlatform());
                            ageIn.setSysShopsName(inTsRecord.getSysShopsName());
                            ageIn.setSysSite(inTsRecord.getSysSite());
                            ageIn.setWarehouseName(inTsRecord.getWarehouseName());
                            ageIn.setFnSku(inTsRecord.getFnSku());
                            ageIn.setSku(inTsRecord.getSku());
                            ageIn.setMaterialCode(inTsRecord.getMaterialCode());
                            ageIn.setSignQuantity(inTsRecord.getChangeInventoryQuantity());
                            ageIn.setInventoryQuantity(inTsRecord.getChangeInventoryQuantity());
                            ageIn.setSignDate(inTsRecord.getCreateTime());
                            ageIn.setCreateTime(inTsRecord.getCreateTime());
                            ageIn.setCreateUser(inTsRecord.getCreateUser());
                            ageInParam.add(ageIn);
                            ageService.batchAgeIn(ageInParam);
                        }
                        //签收原账号
                        if(StringUtils.isNotBlank(inWarehouse.getUpdateSysShopsName()) && StringUtils.isBlank(inWarehouse.getNewSysShopsName())){
                            platform = inWarehouse.getPlatform();
                            sysShopsName = inWarehouse.getUpdateSysShopsName();
                        }
                        //签收新账号
                        if(StringUtils.isNotBlank(inWarehouse.getNewSysShopsName()) && StringUtils.isBlank(inWarehouse.getUpdateSysShopsName()) ){
                            platform = inWarehouse.getNewPlatform();
                            sysShopsName = inWarehouse.getNewSysShopsName();
                            //如果平台是Rakuten，FNSKU值为物料编码
                            detailParam.setFnSku(detailParam.getMaterialCode());
                        }
                    } else {
                        //亚马逊发海外仓非JP站点签收
                        platform = inWarehouse.getPlatform();
                        sysShopsName = inWarehouse.getSysShopsName();
                    }
                } else {
                    if(StrUtil.isEmpty(detailParam.getSku()) || StrUtil.isEmpty(detailParam.getFnSku()) || ObjectUtil.isNull(detailParam.getActualQuantity())){
                        return ResponseData.error("SKU、FNSKU、签收数量不为空！签收失败！");
                    }
                    //国内仓发海外仓
                    platform = inWarehouse.getPlatform();
                    sysShopsName = inWarehouse.getSysShopsName();
                }

                //查询海外仓账存信息
                //获取海外仓库存管理数据，注(产品需求)：针对于乐天平台，海外仓入库时候不校验SKU(只校验物料编码+FNSKU)
                LambdaQueryWrapper<OverseasWarehouseManage> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(OverseasWarehouseManage :: getPlatform, platform)
                        .eq(OverseasWarehouseManage :: getSysShopsName, sysShopsName)
                        .eq(OverseasWarehouseManage :: getSysSite, inWarehouse.getSysSite())
                        .eq(OverseasWarehouseManage :: getWarehouseName, inWarehouse.getInWarehouseName())
                        .eq(!platform.equals("Rakuten"), OverseasWarehouseManage :: getSku, detailParam.getSku())
                        .eq(OverseasWarehouseManage :: getFnSku, detailParam.getFnSku());
                OverseasWarehouseManage qw = overseasWarehouseManageService.getOne(queryWrapper);
                if(ObjectUtil.isEmpty(qw)){
                    if(InBusinessTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(inWarehouse.getOperateType())){
                        //亚马逊仓发海外仓没有账存信息则初始化海外仓管理账存信息，并修改
                        OverseasWarehouseManage newManage = new OverseasWarehouseManage();
                        newManage.setPlatform(platform);
                        newManage.setSysShopsName(sysShopsName);
                        newManage.setSysSite(inWarehouse.getSysSite());
                        newManage.setWarehouseName(inWarehouse.getInWarehouseName());//新的收货仓名称
                        newManage.setFnSku(detailParam.getFnSku());
                        newManage.setSku(detailParam.getSku());
                        newManage.setMaterialCode(detailParam.getMaterialCode());
                        newManage.setComeQuantity(BigDecimal.ZERO);
                        newManage.setInventoryQuantity(BigDecimal.ZERO);
                        newManage.setCreateUser(accountAndName);
                        newManage.setCreateTime(DateUtil.date());
                        newManage.setDataSource(InitDataSourceEnum.FBA_TO_OVERSEAS.getCode());
                        overseasWarehouseManageService.save(newManage);
                        qw = newManage;
                    } else {
                        log.error("海外仓入库单号[{}]=>>明细sku[{}]没有对应海外仓库存！", detailParam.getInOrder(), detailParam.getSku());
                        return ResponseData.error("海外仓入库单号["+detailParam.getInOrder()+"],明细sku["+detailParam.getSku()+"]没有对应海外仓库存！");
                    }
                }

                //海外仓入库管理->库存增加
                //注(产品需求)：针对于乐天平台，海外仓入库时候不校验SKU(只校验物料编码+FNSKU)
                LambdaUpdateWrapper<OverseasWarehouseManage> warehouseWrapper = new LambdaUpdateWrapper<>();
                warehouseWrapper.eq(OverseasWarehouseManage :: getId, qw.getId())
                        .set(OverseasWarehouseManage :: getUpdateUser, accountAndName)
                        .set(OverseasWarehouseManage :: getUpdateTime, DateUtil.date());
                BigDecimal finallyNowComeQty;
                //账存增加：现账存数量 = 原账存数量 + 实际到货数量
                BigDecimal nowInventoryQty = BigDecimal.ZERO;

                //新增操作记录
                OverseasWarehouseManageRecord inRecord = new OverseasWarehouseManageRecord();
                BeanUtils.copyProperties(detailParam, inRecord);
                inRecord.setParentId(qw.getId());
                inRecord.setSysSite(inWarehouse.getSysSite());
                inRecord.setWarehouseName(inWarehouse.getInWarehouseName());
                inRecord.setOperateType(inWarehouse.getOperateType());
                inRecord.setOperateTypeDetail(inWarehouse.getOperateType());
                inRecord.setBusinessType(OverseasBusinessTypeEnum.IN.getName()); //入库
                inRecord.setInventoryQuantity(qw.getInventoryQuantity());
                inRecord.setComeQuantity(qw.getComeQuantity()); //原来货数量
                inRecord.setCreateTime(DateUtil.date());
                inRecord.setCreateUser(accountAndName);
                inRecord.setOutOrg(inWarehouse.getOutOrg());
                inRecord.setOutOrgName(inWarehouse.getOutOrgName());
                inRecord.setIsChangeOrg("0");
                inRecord.setIsChangeMaterialCode("0");
                inRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                inRecord.setRemark(inWarehouse.getRemark());

                if(InBusinessTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(inWarehouse.getOperateType())){
                    if("JP".equals(inWarehouse.getSysSite())){
                        //签收2个账号：原账号和新账号（账存增加原账号的签收数量，新账号的签收数量在前面已做增加）
                        if(StringUtils.isNotBlank(inWarehouse.getUpdateSysShopsName()) && StringUtils.isNotBlank(inWarehouse.getNewSysShopsName())){
                            inRecord.setPlatform(inWarehouse.getPlatform());
                            inRecord.setSysShopsName(inWarehouse.getUpdateSysShopsName());
                            inRecord.setInOrder(inWarehouse.getInOrder() + "_" + inWarehouse.getUpdateSysShopsName());
                            inRecord.setChangeInventoryQuantity(inWarehouse.getConfirmUpdateQuantity());
                            nowInventoryQty = qw.getInventoryQuantity().add(inWarehouse.getConfirmUpdateQuantity());
                            inRecord.setInOrg(inWarehouse.getInOrg());
                            inRecord.setInOrgName(inWarehouse.getInOrgName());
                        }
                        //签收原账号
                        if(StringUtils.isNotBlank(inWarehouse.getUpdateSysShopsName()) && StringUtils.isBlank(inWarehouse.getNewSysShopsName())){
                            inRecord.setPlatform(inWarehouse.getPlatform());
                            inRecord.setSysShopsName(inWarehouse.getUpdateSysShopsName());
                            inRecord.setInOrder(inWarehouse.getInOrder() + "_" + inWarehouse.getUpdateSysShopsName());
                            inRecord.setChangeInventoryQuantity(detailParam.getQuantity());
                            nowInventoryQty = qw.getInventoryQuantity().add(detailParam.getQuantity());
                            inRecord.setInOrg(inWarehouse.getInOrg());
                            inRecord.setInOrgName(inWarehouse.getInOrgName());
                        }
                        //签收新账号
                        if(StringUtils.isNotBlank(inWarehouse.getNewSysShopsName()) && StringUtils.isBlank(inWarehouse.getUpdateSysShopsName())){
                            inRecord.setPlatform(inWarehouse.getNewPlatform());
                            inRecord.setSysShopsName(inWarehouse.getNewSysShopsName());
                            inRecord.setInOrder(inWarehouse.getInOrder() + "_" + inWarehouse.getNewSysShopsName());
                            inRecord.setChangeInventoryQuantity(detailParam.getQuantity());
                            nowInventoryQty = qw.getInventoryQuantity().add(detailParam.getQuantity());
                            inRecord.setInOrg(inWarehouse.getInTsOrg());
                            inRecord.setInOrgName(inWarehouse.getInTsOrgName());
                            if(StringUtils.isBlank(inRecord.getRemark())){
                                inRecord.setRemark(inWarehouse.getSysRemark());
                            } else {
                                inRecord.setRemark(inRecord.getRemark() + inWarehouse.getSysRemark());
                            }
                        }
                    } else {
                        inRecord.setPlatform(inWarehouse.getPlatform());
                        inRecord.setSysShopsName(inWarehouse.getSysShopsName());
                        inRecord.setInOrder(inWarehouse.getInOrder());
                        inRecord.setChangeInventoryQuantity(detailParam.getQuantity());
                        nowInventoryQty = qw.getInventoryQuantity().add(detailParam.getQuantity());
                        inRecord.setInOrg(inWarehouse.getInOrg());
                        inRecord.setInOrgName(inWarehouse.getInOrgName());
                    }
                    //亚马逊发海外仓不计入来货，故不用来货扣减
                    finallyNowComeQty = qw.getComeQuantity();
                    warehouseWrapper.set(OverseasWarehouseManage :: getInventoryQuantity, nowInventoryQty);
                    overseasWarehouseManageService.update(null, warehouseWrapper);
                    inRecord.setChangeComeQuantity(BigDecimal.ZERO);
                } else {
                    //来货扣减：现来货数量 = 原来货数量 - 实际到货数量
                    BigDecimal nowComeQty = qw.getComeQuantity().subtract(detailParam.getQuantity());
                    nowInventoryQty = qw.getInventoryQuantity().add(detailParam.getQuantity());
                    warehouseWrapper.set(OverseasWarehouseManage :: getComeQuantity, nowComeQty);
                    if(detailParam.getQuantity().compareTo(qw.getComeQuantity()) > 0){
                        return ResponseData.error("海外仓入库单号["+detailParam.getInOrder()+"],明细sku["+detailParam.getSku()+"]扣减数量大于来货数量，来货数量扣减失败，签收失败！");
                    }
                    finallyNowComeQty = nowComeQty;
                    warehouseWrapper.set(OverseasWarehouseManage :: getInventoryQuantity, nowInventoryQty);
                    overseasWarehouseManageService.update(null, warehouseWrapper);

                    inRecord.setPlatform(inWarehouse.getPlatform());
                    inRecord.setSysShopsName(inWarehouse.getSysShopsName());
                    inRecord.setInOrder(inWarehouse.getInOrder());
                    inRecord.setChangeInventoryQuantity(detailParam.getQuantity());
                    inRecord.setChangeComeQuantity(detailParam.getQuantity()); //更新的来货数量
                    inRecord.setInOrg(inWarehouse.getInOrg());
                    inRecord.setInOrgName(inWarehouse.getInOrgName());
                    inRecord.setSyncErpStatus(BaseSyncStatusEnum.NOT.getCode());
                }
                //入库操作记录
                inRecord.setNowInventoryQuantity(nowInventoryQty);
                inRecord.setNowComeQuantity(finallyNowComeQty);
                recordService.save(inRecord);

                //推送ERP->创建调拨单 业务类型亚马逊仓发海外仓
                try{
                    if(inWarehouse.getOperateType().equals(InBusinessTypeEnum.AMAZON_TO_OVERSEAS.getName())){
                        ResponseData responseData;
                        if(inRecord.getInOrg().equals(inRecord.getOutOrg())){
                            responseData = syncTransferToErpService.save(inRecord.getId(),null, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS);
                        }else{
                            responseData = syncTransferToErpService.save(inRecord.getId(),null, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS_OVER);
                        }
                        if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                            log.error("亚马逊仓发海外仓创建调拨单同步异常！操作记录ID：[{}]，信息：[{}]", inRecord.getId(), responseData.getMessage());
                        }
                    }
                }catch(Exception e){
                    log.error("亚马逊仓发海外仓创建调拨单同步异常！操作记录ID：[{}]，信息：[{}]", inRecord.getId(), e.getMessage());
                }

                //处理库龄报表入库
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

                //签收数量是否小于应入库数量，盘亏
                if(InBusinessTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(inWarehouse.getOperateType()) && "JP".equals(inWarehouse.getSysSite())){
                    //亚马逊发海外仓且JP站点，签收账号为原账号且签收数量小于应入库数量
                    if(StringUtils.isNotBlank(inWarehouse.getUpdateSysShopsName())
                            && StringUtils.isBlank(inWarehouse.getNewSysShopsName())
                            && detailParam.getQuantity().compareTo(detailParam.getUserActualQuantity()) > 0
                    ){
                        checkSubList.add(detailParam);
                    }
                } else {
                    if(detailParam.getQuantity().compareTo(detailParam.getUserActualQuantity()) > 0){
                        checkSubList.add(detailParam);
                    }
                }
            }

            //盘亏
            dealCheckSubList(inWarehouse, checkSubList, accountAndName);
            return ResponseData.success("签收成功！");
        }
        return ResponseData.success();
    }

    /**
     * 处理海外仓入库签收盘点
     * @param inWarehouse
     * @param checkSubList
     * @param accountAndName
     */
    private void dealCheckSubList(OverseasInWarehouseParam inWarehouse, List<OverseasInWarehouseDetail> checkSubList, String accountAndName){
        if(CollectionUtil.isNotEmpty(checkSubList)){
            //其他出库单id集合（盘亏）
            List<BigDecimal> outIdList = new ArrayList<>();
            //处理库龄报表出库
            List<OverseasWarehouseAge> ageOutParam = new ArrayList<>();
            for (OverseasInWarehouseDetail inWarehouseDetail : checkSubList) {
                //查询海外仓账存信息
                //获取海外仓库存管理数据，注(产品需求)：针对于乐天平台，海外仓入库时候不校验SKU(只校验物料编码+FNSKU)
                LambdaQueryWrapper<OverseasWarehouseManage> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(OverseasWarehouseManage :: getPlatform, inWarehouse.getPlatform())
                        .eq(OverseasWarehouseManage :: getSysShopsName, inWarehouse.getSysShopsName())
                        .eq(OverseasWarehouseManage :: getSysSite, inWarehouse.getSysSite())
                        .eq(OverseasWarehouseManage :: getWarehouseName, inWarehouse.getInWarehouseName())
                        .eq(!inWarehouse.getPlatform().equals("Rakuten"), OverseasWarehouseManage :: getSku, inWarehouseDetail.getSku())
                        .eq(OverseasWarehouseManage :: getFnSku, inWarehouseDetail.getFnSku());
                OverseasWarehouseManage qw = overseasWarehouseManageService.getOne(queryWrapper);
                if(ObjectUtil.isNotEmpty(qw)){
                    //1、更新账存
                    //盘亏=应入库数量-签收数量
                    BigDecimal subQuantity = inWarehouseDetail.getQuantity().subtract(inWarehouseDetail.getUserActualQuantity());
                    BigDecimal inventoryQuantity = qw.getInventoryQuantity();
                    //库存扣减
                    BigDecimal nowInventoryQuantity = inventoryQuantity.subtract(subQuantity);
                    qw.setInventoryQuantity(nowInventoryQuantity);
                    qw.setUpdateTime(DateUtil.date());
                    qw.setUpdateUser(accountAndName);
                    overseasWarehouseManageService.updateById(qw);

                    //2、插入操作记录表
                    OverseasWarehouseManageRecord record = new OverseasWarehouseManageRecord();
                    BeanUtils.copyProperties(qw, record);
                    record.setId(null);
                    record.setParentId(qw.getId());
                    record.setOperateType(OperateTypeEnum.CHECK.getName());
                    record.setCreateTime(DateUtil.date());
                    record.setCreateUser(accountAndName);
                    record.setUpdateTime(null);
                    record.setUpdateUser(null);
                    record.setDepartment(inWarehouseDetail.getDepartment());
                    record.setTeam(inWarehouseDetail.getTeam());
                    record.setInOrg(inWarehouse.getInOrg());
                    record.setOutOrg(inWarehouse.getOutOrg());
                    record.setInOrgName(inWarehouse.getInOrgName());
                    record.setOutOrgName(inWarehouse.getOutOrgName());
                    record.setRemark("盘点原因：丢失");
                    record.setFnSku(qw.getFnSku());
                    record.setComeQuantity(qw.getComeQuantity());
                    record.setChangeComeQuantity(BigDecimal.ZERO);
                    record.setNowComeQuantity(qw.getComeQuantity());
                    record.setInventoryQuantity(inventoryQuantity);
                    record.setChangeInventoryQuantity(subQuantity);
                    record.setNowInventoryQuantity(nowInventoryQuantity);
                    record.setIsChangeOrg("0");
                    record.setIsChangeMaterialCode("0");
                    record.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                    //从redis取入库单号
                    String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
                    String order = overseasWarehouseManageService.getLogisticsOrder(env + "_"  + OrderTypePreEnum.PD.getCode() + pureDate, OrderTypePreEnum.PD, 5);
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
                    overseasOutWarehouseService.save(out);

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
                    overseasOutWarehouseDetailService.save(outDetail);
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
                    ageOutParam.add(ageOut);
                }
            }

            if(CollectionUtil.isNotEmpty(outIdList)){
                //K3其他出库
                try {
                    overseasWarehouseManageService.syncBatchCheckOutStockToErp(outIdList);
                }catch(Exception e){
                    log.error("调用K3其他出库单异常，盘点失败！异常信息[{}]", e.getMessage());
                }

                //处理库龄报表出库
                ageService.batchAgeOut(ageOutParam);
            }
        }
    }

    /**
     * 海外仓入库单同步到K3组织内调拨单(定时)
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData reSyncTransferToErp(){

        LambdaQueryWrapper<OverseasWarehouseManageRecord> qw = new LambdaQueryWrapper<>();
        qw.in(OverseasWarehouseManageRecord::getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode(),
                BaseSyncStatusEnum.AUDIT_ERROR.getCode())
                .eq(OverseasWarehouseManageRecord::getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                .orderByDesc(OverseasWarehouseManageRecord::getCreateTime);
        List<OverseasWarehouseManageRecord> recordList = recordService.list(qw);
        if (CollectionUtil.isNotEmpty(recordList)) {
            log.info("海外仓入库单同步到K3组织内调拨单(定时)开始！");
            /*for (OverseasWarehouseManageRecord record : recordList) {
                try {
                    syncTransferToErpService.save(record.getId(), null, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS);
                } catch (Exception e) {
                    log.error("海外仓入库单同步到K3组织内调拨单(定时)失败！返回信息[{}]", e.getMessage());
                }
            }*/
        }

        return ResponseData.success("海外仓入库单同步到K3组织内调拨单(定时)成功！");
    }


    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateInWarehouseByEBMS(List<EbmsOverseasInWarehouseParam> params) {
        try {
            log.info("接收EBMS出货清单（国内仓发海外仓入库）请求，数据[{}]", JSONObject.toJSON(params));
            for(EbmsOverseasInWarehouseParam param : params){
                if(StrUtil.isEmpty(param.getInOrder())){
                    return ResponseData.error("入库单号不能为空!");
                }

                LambdaQueryWrapper<OverseasInWarehouse> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(OverseasInWarehouse::getInOrder,param.getInOrder());
                if(this.count(queryWrapper) == 0){
                    OverseasInWarehouse ent=new OverseasInWarehouse();
                    BeanUtils.copyProperties(param,ent);
                    ent.setOperateType(InBusinessTypeEnum.INSIDE_TO_OVERSEAS.getName());
                    ent.setConfirmStatus(ConfirmStatusEnum.NOT_CONFIRM.getName());
                    ent.setCreateUser(param.getCreateUser());
                    ent.setCreateTime(DateUtil.date());
                    ent.setConfirmStartTime(null);
                    ent.setConfirmEndTime(null);
                    ent.setNotInventoryQuantity(param.getShouldInventoryQuantity());//未到货数量等于应入库数量
                    overseasInWarehouseService.save(ent);
                }

                if(CollectionUtil.isNotEmpty(param.getDetailList())){
                    for(EbmsOverseasInWarehouseDetailParam detailParam : param.getDetailList()){
                        LambdaQueryWrapper<OverseasInWarehouseDetail> oiDetailQueryWrapper = new LambdaQueryWrapper<>();
                        oiDetailQueryWrapper.eq(OverseasInWarehouseDetail :: getInOrder, detailParam.getInOrder())
                                .eq(OverseasInWarehouseDetail :: getPackageNum, detailParam.getPackageNum())
                                .eq(OverseasInWarehouseDetail :: getSku, detailParam.getSku())
                                .eq(OverseasInWarehouseDetail :: getPackDirectCode, detailParam.getPackDirectCode());
                        OverseasInWarehouseDetail inWarehouseDetail = overseasInWarehouseDetailService.getOne(oiDetailQueryWrapper);
                        if(inWarehouseDetail == null){
                            //新增
                            OverseasInWarehouseDetail detail=new OverseasInWarehouseDetail();
                            BeanUtils.copyProperties(detailParam,detail);
                            detail.setInOrder(param.getInOrder());
                            detail.setMaterialName(detailParam.getMaterialName());
                            detail.setLength(detailParam.getPackDetBoxLength());
                            detail.setWidth(detailParam.getPackDetBoxWidth());
                            detail.setHeight(detailParam.getPackDetBoxHeight());
                            detail.setWeight(detailParam.getPackDetBoxWeight());
                            detail.setShipmentDate(detailParam.getShipmentDate());
                            detail.setConfirmStatus(ConfirmStatusEnum.NOT_CONFIRM.getName());
                            detail.setCreateUser(param.getCreateUser());
                            detail.setCreateTime(DateUtil.date());
                            detail.setConfirmTime(null);
                            overseasInWarehouseDetailService.save(detail);
                        }else{
                            //更新承运商和物流发货状态
                            if(ShipStatusEnum.SHIP_ING .getName().equals(detailParam.getShipStatus())){
                                LambdaUpdateWrapper<OverseasInWarehouseDetail> oiWarehouseDetailUpdateWrapper = new LambdaUpdateWrapper<>();
                                oiWarehouseDetailUpdateWrapper.eq(OverseasInWarehouseDetail :: getInOrder, detailParam.getInOrder())
                                        .eq(OverseasInWarehouseDetail :: getPackageNum, detailParam.getPackageNum())
                                        .eq(OverseasInWarehouseDetail :: getSku, detailParam.getSku())
                                        .set(OverseasInWarehouseDetail :: getLogisticsCompany, detailParam.getLogisticsCompany())
                                        .set(OverseasInWarehouseDetail :: getLogisticsNum, detailParam.getLogisticsNum())
                                        .set(OverseasInWarehouseDetail :: getShipmentDate, detailParam.getShipmentDate())
                                        .set(OverseasInWarehouseDetail :: getLogisticsStatus, detailParam.getLogisticsStatus())
                                        .set(OverseasInWarehouseDetail :: getUpdateTime, new Date());
                                overseasInWarehouseDetailService.update(null, oiWarehouseDetailUpdateWrapper);
                            }
                        }

                        //查询库存sku信息列表
                        //注(产品需求)：针对于乐天平台，海外仓入库时候不校验SKU(只校验物料编码+FNSKU)
                        //发货中
                        if(ShipStatusEnum.SHIP_ING.getName().equals(detailParam.getShipStatus())){
                            //根据入库单号、箱号、SKU获取来货记录数
                            LambdaQueryWrapper<OverseasWarehouseManageRecord> recordComeQueryWrapper = new LambdaQueryWrapper<>();
                            recordComeQueryWrapper.eq(OverseasWarehouseManageRecord :: getInOrder, detailParam.getInOrder())
                                    .eq(OverseasWarehouseManageRecord :: getPackageNum, detailParam.getPackageNum())
                                    .eq(OverseasWarehouseManageRecord :: getSku, detailParam.getSku())
                                    .eq(OverseasWarehouseManageRecord :: getBusinessType, OverseasBusinessTypeEnum.COME.getName())
                                    .eq(OverseasWarehouseManageRecord :: getPackDirectCode, detailParam.getPackDirectCode());
                            int comeCount = recordService.count(recordComeQueryWrapper);

                            //根据入库单号、箱号、SKU获取来货记录数
                            LambdaQueryWrapper<OverseasWarehouseManageRecord> recordCloseQueryWrapper = new LambdaQueryWrapper<>();
                            recordCloseQueryWrapper.eq(OverseasWarehouseManageRecord :: getInOrder, detailParam.getInOrder())
                                    .eq(OverseasWarehouseManageRecord :: getPackageNum, detailParam.getPackageNum())
                                    .eq(OverseasWarehouseManageRecord :: getSku, detailParam.getSku())
                                    .eq(OverseasWarehouseManageRecord :: getBusinessType, OverseasBusinessTypeEnum.CLOSE_SHIPMENT.getName())
                                    .eq(OverseasWarehouseManageRecord :: getPackDirectCode, detailParam.getPackDirectCode());
                            int closeCount = recordService.count(recordCloseQueryWrapper);

                            //可以入库和计入来货
                            if(comeCount == 0 || (comeCount - closeCount) < 1){
                                BigDecimal nowComeQuantity;
                                BigDecimal nowInventoryQuantity = BigDecimal.ZERO;

                                LambdaQueryWrapper<OverseasWarehouseManage> overseasWarehouseQueryWrapper=new LambdaQueryWrapper<>();
                                overseasWarehouseQueryWrapper.eq(OverseasWarehouseManage::getPlatform,param.getPlatform())
                                        .eq(OverseasWarehouseManage::getSysShopsName,param.getSysShopsName())
                                        .eq(OverseasWarehouseManage::getSysSite,param.getSysSite())
                                        .eq(OverseasWarehouseManage::getWarehouseName,param.getInWarehouseName())
                                        //.eq(OverseasWarehouseManage::getSku,detailParam.getSku())
                                        .eq(!param.getPlatform().equals("Rakuten"),OverseasWarehouseManage::getSku,detailParam.getSku())
                                        .eq(OverseasWarehouseManage::getFnSku,detailParam.getFnSku());
                                OverseasWarehouseManage qw=overseasWarehouseManageService.getOne(overseasWarehouseQueryWrapper);
                                Boolean init = false;
                                if(qw == null){
                                    //海外仓没有sku库存信息，默认新增一个库存信息，并初始化为0
                                    OverseasWarehouseManage entManage=new OverseasWarehouseManage();
                                    entManage.setPlatform(param.getPlatform());
                                    entManage.setSysShopsName(param.getSysShopsName());
                                    entManage.setSysSite(param.getSysSite());
                                    entManage.setWarehouseName(param.getInWarehouseName());
                                    entManage.setFnSku(detailParam.getFnSku());
                                    entManage.setSku(detailParam.getSku());
                                    entManage.setMaterialCode(detailParam.getMaterialCode());
                                    entManage.setComeQuantity(detailParam.getQuantity());
                                    entManage.setInventoryQuantity(BigDecimal.ZERO);
                                    entManage.setCreateUser(param.getCreateUser());
                                    entManage.setCreateTime(new Date());
                                    entManage.setDataSource(InitDataSourceEnum.INSIDE_TO_OVERSEAS.getCode());
                                    overseasWarehouseManageService.save(entManage);
                                    qw = entManage;
                                    nowComeQuantity = detailParam.getQuantity();
                                    init = true;
                                }else{
                                    BigDecimal comeQty=qw.getComeQuantity();
                                    //应入库数量计入来货数量
                                    //注(产品需求)：针对于乐天平台，海外仓入库时候不校验SKU(只校验物料编码+FNSKU)
                                    LambdaUpdateWrapper<OverseasWarehouseManage> warehouseWrapper=new LambdaUpdateWrapper<>();
                                    warehouseWrapper.eq(OverseasWarehouseManage::getPlatform,param.getPlatform())
                                            .eq(OverseasWarehouseManage::getSysShopsName,param.getSysShopsName())
                                            .eq(OverseasWarehouseManage::getSysSite,param.getSysSite())
                                            .eq(OverseasWarehouseManage::getWarehouseName,param.getInWarehouseName())
                                            //.eq(OverseasWarehouseManage::getSku,detailParam.getSku())
                                            .eq(!param.getPlatform().equals("Rakuten"),OverseasWarehouseManage::getSku,detailParam.getSku())
                                            .eq(OverseasWarehouseManage::getFnSku,detailParam.getFnSku())
                                            .set(OverseasWarehouseManage::getComeQuantity,comeQty.add(detailParam.getQuantity()))
                                            .set(OverseasWarehouseManage::getUpdateUser,param.getCreateUser())
                                            .set(OverseasWarehouseManage::getUpdateTime,new Date());
                                    overseasWarehouseManageService.update(null,warehouseWrapper);

                                    nowComeQuantity = comeQty.add(detailParam.getQuantity());
                                    nowInventoryQuantity = qw.getInventoryQuantity();
                                }

                                //生成1条来货记录
                                OverseasWarehouseManageRecord goodsRecord = new OverseasWarehouseManageRecord();
                                BeanUtils.copyProperties(param, goodsRecord);
                                goodsRecord.setId(null);
                                goodsRecord.setParentId(qw.getId());//新入库id
                                goodsRecord.setCreateTime(new Date());
                                goodsRecord.setCreateUser(param.getCreateUser());
                                goodsRecord.setMaterialCode(qw.getMaterialCode());
                                goodsRecord.setWarehouseName(param.getInWarehouseName());
                                goodsRecord.setSku(detailParam.getSku());
                                goodsRecord.setFnSku(detailParam.getFnSku());
                                goodsRecord.setInventoryQuantity(qw.getInventoryQuantity());
                                goodsRecord.setChangeInventoryQuantity(BigDecimal.ZERO);
                                goodsRecord.setComeQuantity(qw.getComeQuantity()); //原来货数量
                                if(init){
                                    goodsRecord.setComeQuantity(BigDecimal.ZERO);
                                }
                                goodsRecord.setChangeComeQuantity(detailParam.getQuantity()); //新增来货数量
                                goodsRecord.setOperateType(OperateTypeEnum.INSIDE_TO_OVERSEAS.getName());
                                goodsRecord.setOperateTypeDetail(OperateTypeEnum.INSIDE_TO_OVERSEAS.getName());
                                goodsRecord.setBusinessType(OverseasBusinessTypeEnum.COME.getName()); //来货
                                goodsRecord.setNowComeQuantity(nowComeQuantity);
                                goodsRecord.setNowInventoryQuantity(nowInventoryQuantity);
                                goodsRecord.setIsChangeOrg("0");
                                goodsRecord.setIsChangeMaterialCode("0");
                                goodsRecord.setPackageNum(detailParam.getPackageNum());
                                goodsRecord.setSyncErpStatus(BaseSyncStatusEnum.NOT.getCode());
                                goodsRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                                goodsRecord.setPackDirectCode(detailParam.getPackDirectCode());
                                recordService.save(goodsRecord);
                            }
                        }
                    }
                }
            }
            return ResponseData.success("EBMS推送海外仓入库任务数据同步成功！");
        }catch(Exception e){
            log.error("获取EBMS推送海外仓入库任务数据异常，信息[{}]",e.getMessage());
            return ResponseData.error("获取EBMS推送海外仓入库任务数据异常，信息："+e.getMessage());
        }
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateLogistics(List<EbmsOiwLogisticsParam> params) {
        log.info("接收EBMS出货清单物流信息接口请求，数据[{}]", JSONObject.toJSON(params));
        for (EbmsOiwLogisticsParam oiwLogisticsParam : params) {
            LambdaUpdateWrapper<OverseasInWarehouseDetail> oiWarehouseDetailUpdateWrapper = new LambdaUpdateWrapper<>();
            oiWarehouseDetailUpdateWrapper.eq(OverseasInWarehouseDetail :: getInOrder, oiwLogisticsParam.getInOrder())
                    .eq(OverseasInWarehouseDetail :: getPackageNum, oiwLogisticsParam.getPackageNum())
                    .set(OverseasInWarehouseDetail :: getLogisticsCompany, oiwLogisticsParam.getLogisticsCompany())
                    .set(OverseasInWarehouseDetail :: getLogisticsNum, oiwLogisticsParam.getLogisticsNum())
                    .set(OverseasInWarehouseDetail :: getUpdateTime, new Date())
                    .set(OverseasInWarehouseDetail :: getUpdateUser, oiwLogisticsParam.getOperator());
            overseasInWarehouseDetailService.update(null, oiWarehouseDetailUpdateWrapper);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateLogisticsStatus(List<EbmsOiwLogisticsParam> params) {
        log.info("接收EBMS出货清单物流跟踪状态接口请求，数据[{}]", JSONObject.toJSON(params));
        for (EbmsOiwLogisticsParam oiwLogisticsParam : params) {
            LambdaUpdateWrapper<OverseasInWarehouseDetail> oiWarehouseDetailUpdateWrapper = new LambdaUpdateWrapper<>();
            oiWarehouseDetailUpdateWrapper.eq(OverseasInWarehouseDetail :: getInOrder, oiwLogisticsParam.getInOrder())
                    .eq(OverseasInWarehouseDetail :: getPackageNum, oiwLogisticsParam.getPackageNum())
                    .set(OverseasInWarehouseDetail :: getLogisticsStatus, oiwLogisticsParam.getLogisticsStatus())
                    .set(OverseasInWarehouseDetail :: getUpdateTime, new Date())
                    .set(OverseasInWarehouseDetail :: getUpdateUser, oiwLogisticsParam.getOperator());
            overseasInWarehouseDetailService.update(null, oiWarehouseDetailUpdateWrapper);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData deleteLogistics(List<EbmsOiwLogisticsParam> params) {
        log.info("接收EBMS出货清单物流删除接口请求，数据[{}]", JSONObject.toJSON(params));
        for (EbmsOiwLogisticsParam oiwLogisticsParam : params) {
            //根据入库单号查询主表
            LambdaQueryWrapper<OverseasInWarehouse> oiwQueryWrapper = new LambdaQueryWrapper();
            oiwQueryWrapper.eq(OverseasInWarehouse :: getInOrder, oiwLogisticsParam.getInOrder());
            OverseasInWarehouse overseasInWarehouse = overseasInWarehouseService.getOne(oiwQueryWrapper);
            if(overseasInWarehouse == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("入库单号：" + oiwLogisticsParam.getInOrder() + "不存在，删除失败！");
            }
            if(ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(overseasInWarehouse.getConfirmStatus())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("入库单号：" + oiwLogisticsParam.getInOrder() + "已做签收，不允许删除，删除失败！");
            }

            //根据入库单号查询明细
            /**
             * 取出物流单号相同或者为null的数据，跟总的明细比较记录数：
             * 1、记录数相同：说明此订单对应明细的物流单号只有1个，做海外仓管理来货数扣减、操作记录入库并执行【删除】单号操作；
             * 2、记录数不相同：说明此订单对应明细的物流单号有多个，做海外仓管理来货数扣减、操作记录入库并执行【更新】物流单号操作。
             */
            LambdaUpdateWrapper<OverseasInWarehouseDetail> oiWarehouseDetailUpdateWrapper = new LambdaUpdateWrapper<>();
            oiWarehouseDetailUpdateWrapper.eq(OverseasInWarehouseDetail :: getInOrder, oiwLogisticsParam.getInOrder());
            List<OverseasInWarehouseDetail> inOrderDetailList = overseasInWarehouseDetailService.list(oiWarehouseDetailUpdateWrapper);
            if(CollectionUtil.isEmpty(inOrderDetailList)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("入库单号：" + oiwLogisticsParam.getInOrder() + "明细不存在，删除失败！");
            }
            //物流单相同或为null的数据（为了比对数据条数，判断是删除入库信息还是更新物流单号）
            List<OverseasInWarehouseDetail> logisticsNumDetailList = inOrderDetailList.stream().filter(d ->
                    d.getLogisticsNum() == null || oiwLogisticsParam.getLogisticsNum().equals(d.getLogisticsNum())
            ).collect(Collectors.toList());
            //物流单相同（真正需要处理的数据）
            List<OverseasInWarehouseDetail> sameDetailList = logisticsNumDetailList.stream().filter(d -> oiwLogisticsParam.getLogisticsNum().equals(d.getLogisticsNum())).collect(Collectors.toList());
            for (OverseasInWarehouseDetail detail : sameDetailList) {
                if(ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(detail.getConfirmStatus())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("入库单号：" + oiwLogisticsParam.getInOrder() + "，物流单号:" + oiwLogisticsParam.getLogisticsNum() + "已做签收操作，不可操作删除！");
                }

                //海外仓管理来货扣减
                LambdaQueryWrapper<OverseasWarehouseManage> overseasWarehouseQueryWrapper = new LambdaQueryWrapper<>();
                overseasWarehouseQueryWrapper.eq(OverseasWarehouseManage :: getPlatform, overseasInWarehouse.getPlatform())
                        .eq(OverseasWarehouseManage :: getSysShopsName, overseasInWarehouse.getSysShopsName())
                        .eq(OverseasWarehouseManage :: getSysSite, overseasInWarehouse.getSysSite())
                        .eq(OverseasWarehouseManage :: getWarehouseName, overseasInWarehouse.getInWarehouseName())
                        .eq(!overseasInWarehouse.getPlatform().equals("Rakuten"), OverseasWarehouseManage :: getSku, detail.getSku())
                        .eq(OverseasWarehouseManage :: getFnSku, detail.getFnSku());
                OverseasWarehouseManage overseasWarehouseManage = overseasWarehouseManageService.getOne(overseasWarehouseQueryWrapper);
                if(overseasWarehouseManage == null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error("入库单号对应的海外仓管理记录不存在，删除失败！入库单号[{}]，物流单号[{}]，平台[{}]，账号[{}]，站点[{}]，仓库名称[{}]，sku[{}]，fnsku[{}]",
                            oiwLogisticsParam.getInOrder(),
                            oiwLogisticsParam.getLogisticsNum(),
                            overseasInWarehouse.getPlatform(),
                            overseasInWarehouse.getSysShopsName(),
                            overseasInWarehouse.getSysSite(),
                            overseasInWarehouse.getInWarehouseName(),
                            detail.getSku(),
                            detail.getFnSku()
                    );
                    return ResponseData.error("入库单号：" + oiwLogisticsParam.getInOrder() + "，物流单号：" + oiwLogisticsParam.getLogisticsNum() + "对应的海外仓管理记录不存在，删除失败！");
                }
                if(overseasWarehouseManage.getComeQuantity().compareTo(detail.getQuantity()) < 0){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error("入库单号对应的海外仓管理来货数量[{}]小于扣减数量[{}]，删除失败！入库单号[{}]，物流单号[{}]，平台[{}]，账号[{}]，站点[{}]，仓库名称[{}]，sku[{}]，fnsku[{}]",
                            overseasWarehouseManage.getComeQuantity(),
                            detail.getQuantity(),
                            oiwLogisticsParam.getInOrder(),
                            oiwLogisticsParam.getLogisticsNum(),
                            overseasInWarehouse.getPlatform(),
                            overseasInWarehouse.getSysShopsName(),
                            overseasInWarehouse.getSysSite(),
                            overseasInWarehouse.getInWarehouseName(),
                            detail.getSku(),
                            detail.getFnSku()
                    );
                    return ResponseData.error(
                        "入库单号：" + oiwLogisticsParam.getInOrder()
                        + "，物流单号：" + oiwLogisticsParam.getLogisticsNum()
                        + "对应的海外仓管理来货数量："+ overseasWarehouseManage.getComeQuantity()
                        +"小于扣减数量：" + detail.getQuantity() + "，删除失败！"
                    );
                }
                BigDecimal oldComeQuantity = overseasWarehouseManage.getComeQuantity();
                overseasWarehouseManage.setComeQuantity(overseasWarehouseManage.getComeQuantity().subtract(detail.getQuantity()));
                overseasWarehouseManage.setUpdateTime(DateUtil.date());
                overseasWarehouseManage.setUpdateUser(oiwLogisticsParam.getOperator());
                overseasWarehouseManageService.updateById(overseasWarehouseManage);

                //扣减来货入库海外仓管理操作记录
                OverseasWarehouseManageRecord manageRecord = new OverseasWarehouseManageRecord();
                manageRecord.setParentId(overseasWarehouseManage.getId());//新入库id
                manageRecord.setPlatform(overseasWarehouseManage.getPlatform());
                manageRecord.setSysShopsName(overseasWarehouseManage.getSysShopsName());
                manageRecord.setSysSite(overseasWarehouseManage.getSysSite());
                manageRecord.setWarehouseName(overseasWarehouseManage.getWarehouseName());
                manageRecord.setFnSku(overseasWarehouseManage.getFnSku());
                manageRecord.setSku(overseasWarehouseManage.getSku());
                manageRecord.setMaterialCode(overseasWarehouseManage.getMaterialCode());
                manageRecord.setInventoryQuantity(overseasWarehouseManage.getInventoryQuantity());
                manageRecord.setChangeInventoryQuantity(BigDecimal.ZERO);
                manageRecord.setNowInventoryQuantity(overseasWarehouseManage.getInventoryQuantity());
                manageRecord.setComeQuantity(oldComeQuantity); //原来货数量
                manageRecord.setChangeComeQuantity(detail.getQuantity()); //扣减来货数量
                manageRecord.setNowComeQuantity(oldComeQuantity.subtract(detail.getQuantity()));
                manageRecord.setInOrder(oiwLogisticsParam.getInOrder());
                manageRecord.setOperateType(OperateTypeEnum.INSIDE_TO_OVERSEAS_SHIPMENT_DEL.getName());//国内仓发往海外仓物流单删除
                manageRecord.setOperateTypeDetail(OperateTypeEnum.INSIDE_TO_OVERSEAS_SHIPMENT_DEL.getName());
                manageRecord.setBusinessType(OverseasBusinessTypeEnum.CLOSE_SHIPMENT.getName()); //关闭来货
                manageRecord.setCreateUser(oiwLogisticsParam.getOperator());
                manageRecord.setCreateTime(DateUtil.date());
                manageRecord.setIsChangeOrg("0");
                manageRecord.setIsChangeMaterialCode("0");
                manageRecord.setPackageNum(detail.getPackageNum());
                manageRecord.setSyncErpStatus(BaseSyncStatusEnum.NOT.getCode());
                manageRecord.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                manageRecord.setPackDirectCode(detail.getPackDirectCode());
                recordService.save(manageRecord);
            }

            //入库单号包含相同的物流单，则删除，否则置空物流单号、物流商和发货时间
            if(inOrderDetailList.size() == logisticsNumDetailList.size()){
                //删除入库主表记录
                overseasInWarehouseService.removeById(overseasInWarehouse);

                //删除入库明细记录
                LambdaUpdateWrapper<OverseasInWarehouseDetail> oiwdRemoveWrapper = new LambdaUpdateWrapper<>();
                oiwdRemoveWrapper.eq(OverseasInWarehouseDetail :: getInOrder, oiwLogisticsParam.getInOrder());
                overseasInWarehouseDetailService.remove(oiwdRemoveWrapper);
            }else{
                List<BigDecimal> updateIdList = new ArrayList<>();
                sameDetailList.stream().forEach(i ->{
                    updateIdList.add(i.getId());
                });
                //根据id批量更新入库明细物流状态
                detailMapper.updateBatchLogisticsById(updateIdList, oiwLogisticsParam.getOperator());
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData generateInWarehouseByFBA(List<OverseasInWarehouseFBAResult> resultList) {
        String user;
        try {
            String account = LoginContext.me().getLoginUser().getAccount();
            String name = LoginContext.me().getLoginUser().getName();
            user = account + "_" + name;
        } catch (Exception e) {
            user = null;
        }
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);

        //海外仓入库管理主表
        List<OverseasInWarehouse> overseasInWarehouseList = new ArrayList<>();
        //海外仓入库管理明细表
        List<OverseasInWarehouseDetail> detailList = new ArrayList<>();
        List<BigDecimal> rsdIdList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(resultList)){
            for (OverseasInWarehouseFBAResult fbaResult : resultList) {
                //从redis取入库单号
                String inOrder = overseasWarehouseManageService.getLogisticsOrder(env + "_"  + OrderTypePreEnum.AZYCRK.getCode() + pureDate, OrderTypePreEnum.AZYCRK, 5);
                OverseasInWarehouse overseasInWarehouse = new OverseasInWarehouse();
                BeanUtils.copyProperties(fbaResult, overseasInWarehouse);
                overseasInWarehouse.setInOrder(inOrder);
                overseasInWarehouse.setAlreadyInventoryQuantity(BigDecimal.ZERO);
                overseasInWarehouse.setNotInventoryQuantity(overseasInWarehouse.getShouldInventoryQuantity());
                overseasInWarehouse.setCreateTime(new Date());
                overseasInWarehouse.setCreateUser(user);
                overseasInWarehouseList.add(overseasInWarehouse);

                OverseasInWarehouseDetail detail = new OverseasInWarehouseDetail();
                BeanUtils.copyProperties(fbaResult, detail);
                detail.setInOrder(inOrder);
                detail.setCreateTime(new Date());
                detail.setCreateUser(user);
                detail.setDepartment(fbaResult.getDepartment());
                detail.setTeam(fbaResult.getTeam());
                detailList.add(detail);
                rsdIdList.add(fbaResult.getParentId());
            }
        }

        if(CollectionUtil.isNotEmpty(overseasInWarehouseList)){
            overseasInWarehouseService.saveBatch(overseasInWarehouseList);
        }

        if(CollectionUtil.isNotEmpty(detailList)){
            overseasInWarehouseDetailService.saveBatch(detailList);
        }

        //更新Removal Shipment Detail状态
        if(CollectionUtil.isNotEmpty(rsdIdList)){
            List<List<BigDecimal>> splitList = ListUtil.split(rsdIdList, 1000);
            for (List<BigDecimal> updateList : splitList) {
                overseasInWarehouseService.updateGenerateHwc(updateList);
            }
        }

        //亚马逊发海外仓刷新物料编码为空的数据
        mapper.refreshOiwMaterialCode();
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public void refreshOiwMaterialCode(){
        mapper.refreshOiwMaterialCode();
    }

    @Override
    @DataSource(name = "warehouse")
    @Transactional(rollbackFor = Exception.class)
    public void updateGenerateHwc(List<BigDecimal> rsdIdList){
        removalShipmentDetailConsumer.updateGenerateHwc(rsdIdList);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadFbaSign(MultipartFile file, List<String> platformList, List<String> shopList, List<String> siteList, List<String> departmentList, List<String> teamList, List<Map<String, String>> warehouseNameList) {
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OwInFbaSignParam>();
            EasyExcel.read(buffer, OwInFbaSignParam.class, listener).sheet()
                    .doRead();

            List<OwInFbaSignParam> dataList = listener.getDataList();
            log.info("批量签收FBA退海外仓导入入参数据[{}]", JSONObject.toJSON(dataList));
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("批量签收FBA退海外仓导入数据为空，导入失败！");
            }
            return ResponseData.success(this.validSignFBA(dataList, platformList, shopList, siteList, departmentList, teamList, warehouseNameList));
        } catch (Exception e) {
            log.error("批量签收FBA退海外仓导入处理异常，导入失败！", e);
            return ResponseData.error("批量签收FBA退海外仓导入处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("批量签收FBA退海外仓导入关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入签收FBA退海外仓数据校验处理
     * @param dataList
     * @param platformList
     * @param shopList
     * @param siteList
     * @param departmentList
     * @param teamList
     * @param warehouseNameList
     * @return
     */
    private List<OwInFbaSignParam> validSignFBA(List<OwInFbaSignParam> dataList,
                                                                 List<String> platformList,
                                                                 List<String> shopList,
                                                                 List<String> siteList,
                                                                 List<String> departmentList,
                                                                 List<String> teamList,
                                                                 List<Map<String, String>> warehouseNameList) {

        Iterator<OwInFbaSignParam> iterator = dataList.listIterator();
        List<String> owNameList = new ArrayList<>();
        Map<String, String> owNameMap = new ConcurrentHashMap<>();
        warehouseNameList.stream().forEach(i -> {
            owNameList.add(i.get("owName"));
            owNameMap.put(i.get("countryCode"), owNameMap.get(i.get("countryCode")) == null ? i.get("owName") : owNameMap.get(i.get("countryCode")) + i.get("owName"));
        });
        while(iterator.hasNext()) {
            OwInFbaSignParam param = iterator.next();
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if(param.getPlatform() == null
                    || param.getSysShopsName() == null
                    || param.getSysSite() == null
                    || param.getWarehouseName() == null
                    || param.getSku() == null
                    || param.getFnSku() == null
                    || param.getSignQuantity() == null
                    || param.getDepartment() == null
                    || param.getTeam() == null
            ){
                //不为空校验
                validInfo.append("平台、账号、站点、收货仓名称、SKU、FNSKU、签收数量、需求部门、需求Team为必填项");
            } else {
                if (!platformList.contains(param.getPlatform())) {
                    validInfo.append("系统不存在此平台!");
                }
                if (!shopList.contains(param.getSysShopsName())) {
                    validInfo.append("系统不存在此账号!");
                }
                if (!siteList.contains(param.getSysSite())) {
                    validInfo.append("系统不存在此站点!");
                }
                if (!departmentList.contains(param.getDepartment())) {
                    validInfo.append("系统不存在此需求部门!");
                }
                if (!teamList.contains(param.getTeam())) {
                    validInfo.append("系统不存在此需求Team!");
                }
                if (!owNameList.contains(param.getWarehouseName())) {
                    validInfo.append("系统不存在此收货仓名称!");
                }
                if(!owNameMap.get(param.getSysSite()).contains(param.getWarehouseName())){
                    validInfo.append("站点对应的收货仓名称不存在!");
                }
                if(BigDecimal.ZERO.equals(param.getSignQuantity())){
                    validInfo.append("签收数量必须大于0!");
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
    public ResponseData batchFbaSign(List<OwInFbaSignParam> params) {
        List<OwInFbaSignParam> errorList = new ArrayList<>();
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;
        //直接调拨单id集合（FBA退海外仓入库）
        List<BigDecimal> inIdList = new ArrayList<>();
        //处理库龄报表入库
        List<OverseasWarehouseAge> ageInParam = new ArrayList<>();
        forFlag1 : for (OwInFbaSignParam param : params) {
            if(StringUtils.isNotEmpty(param.getUploadRemark())){
                errorList.add(param);
                continue;
            }

            //根据维度查询FBA退海外仓数据：部分签收或者签收新账号为TS的不支持批量签收
            List<OwInFbaSignParam> respInWarehouse = mapper.getInWareHouseFBA(param);
            if(CollectionUtil.isEmpty(respInWarehouse)){
                List<String> northAmericaList = Arrays.asList("CA", "MX");
                List<String> europeList = Arrays.asList("UK", "FR", "IT", "ES", "PL", "CZ", "SE", "AT", "BE", "NL");
                if(northAmericaList.contains(param.getSysSite())){
                    param.setSysSiteTemp("US");
                    param.setSysSite(null);
                    respInWarehouse = mapper.getInWareHouseFBA(param);
                }else if(europeList.contains(param.getSysSite())){
                    param.setSysSiteTemp("DE");
                    param.setSysSite(null);
                    respInWarehouse = mapper.getInWareHouseFBA(param);
                }else {
                    param.setUploadRemark("不存在未签收的数据!");
                    errorList.add(param);
                    continue;
                }
            }

            //导入维度签收数量不能超过维度总待签收数量
            BigDecimal allQuantity = respInWarehouse.stream().map(OwInFbaSignParam::getShouldInventoryQuantity).reduce(BigDecimal.ZERO,BigDecimal::add);
            if(param.getSignQuantity().compareTo(allQuantity) > 0 ){
                param.setUploadRemark("签收数量不能大于应入库数量！");
                errorList.add(param);
                continue;
            }

            //按照时间升序，取最接近维度的签收数量的记录做签收
            //循环记录总数：
            BigDecimal sumSingQuantity = new BigDecimal(0);
            //异常记录总数：
            BigDecimal sumSingErrorQuantity = new BigDecimal(0);
            //异常信息
            StringBuffer errorInfo = new StringBuffer();
            forFlag2 : for (OwInFbaSignParam updateOwInFbaSign : respInWarehouse) {
                //如果单条签收失败，记录异常信息，不影响整体签收
                /**
                 * 1、当导入签收数量等于总的签收数量时，已到货数量等于应到货数量，未到货数量等于0，签收完成；
                 * 2、当导入签收数量小于总的签收数量时，判断签收数量和当前已签收数量
                 */
                BigDecimal alreadyInventoryQuantity;//已到货数量
                BigDecimal notInventoryQuantity;//未到货数量
                if(param.getSignQuantity().compareTo(allQuantity) == 0 ){
                    alreadyInventoryQuantity = updateOwInFbaSign.getShouldInventoryQuantity();
                    notInventoryQuantity = BigDecimal.ZERO;
                } else {
                    //未签收数量 = 导入签收数量 - 已签收数量（包含异常未签收数量）
                    BigDecimal notSignQuantity = param.getSignQuantity().subtract(sumSingQuantity);
                    //当未签收数量小于当前应入库数量时，已到货数量 = 未签收数量，未到货数量 = 应入库数量 - 未签收数量
                    if(notSignQuantity.compareTo(updateOwInFbaSign.getShouldInventoryQuantity()) < 0){
                        alreadyInventoryQuantity = notSignQuantity;
                        notInventoryQuantity = updateOwInFbaSign.getShouldInventoryQuantity().subtract(notSignQuantity);
                    }else{
                        alreadyInventoryQuantity = updateOwInFbaSign.getShouldInventoryQuantity();
                        notInventoryQuantity = BigDecimal.ZERO;
                    }
                }

                try {
                    //将入参数值copy
                    updateOwInFbaSign.setSignQuantity(param.getSignQuantity());
                    updateOwInFbaSign.setDepartment(param.getDepartment());
                    updateOwInFbaSign.setTeam(param.getTeam());
                    updateOwInFbaSign.setRemark(param.getRemark());
                    updateOwInFbaSign.setSysSiteTemp(param.getSysSiteTemp());
                    updateOwInFbaSign.setWarehouseName(param.getWarehouseName());
                    updateOwInFbaSign.setInOrg(param.getInOrg());
                    updateOwInFbaSign.setOutOrg(param.getOutOrg());
                    updateOwInFbaSign.setFnSku(param.getFnSku());//六位码（fnSku后六位）

                    //根据维度查询新海外仓账存信息（新的）
                    QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
                    queryWrapper.eq("PLATFORM", updateOwInFbaSign.getPlatform())
                            .eq("SYS_SHOPS_NAME", updateOwInFbaSign.getSysShopsName())
                            .eq("SYS_SITE", updateOwInFbaSign.getSysSite())
                            .eq("WAREHOUSE_NAME", updateOwInFbaSign.getWarehouseName())
                            .eq("SKU", updateOwInFbaSign.getSku())
                            .eq("MATERIAL_CODE", updateOwInFbaSign.getMaterialCode())
                            .likeLeft("FN_SKU", updateOwInFbaSign.getFnSku());
                    OverseasWarehouseManage overseasWarehouseManage = overseasWarehouseManageService.getOne(queryWrapper);

                    //单条签收
                    //1、更新FBA发海外仓入库管理主表签收状态
                    OverseasInWarehouse inWarehouse = new OverseasInWarehouse();
                    inWarehouse.setId(updateOwInFbaSign.getId());
                    //有值则需要更新站点
                    if(StringUtils.isNotEmpty(updateOwInFbaSign.getSysSiteTemp())){
                        inWarehouse.setSysSite(updateOwInFbaSign.getSysSite());
                    }
                    inWarehouse.setAlreadyInventoryQuantity(alreadyInventoryQuantity);
                    inWarehouse.setNotInventoryQuantity(notInventoryQuantity);
                    inWarehouse.setInWarehouseName(updateOwInFbaSign.getWarehouseName());
                    inWarehouse.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
                    inWarehouse.setConfirmStartTime(DateUtil.date());
                    inWarehouse.setConfirmEndTime(DateUtil.date());
                    inWarehouse.setConfirmUser(accountAndName);
                    inWarehouse.setRemark(updateOwInFbaSign.getRemark());
                    inWarehouse.setUpdateTime(DateUtil.date());
                    inWarehouse.setUpdateUser(accountAndName);
                    mapper.updateById(inWarehouse);

                    //2、更新FBA发海外仓入库管理明细签收状态
                    OverseasInWarehouseDetail inWarehouseDetail = new OverseasInWarehouseDetail();
                    inWarehouseDetail.setId(updateOwInFbaSign.getDetailId());
                    //实际签收数量
                    inWarehouseDetail.setActualQuantity(inWarehouse.getAlreadyInventoryQuantity());
                    inWarehouseDetail.setDepartment(updateOwInFbaSign.getDepartment());
                    inWarehouseDetail.setTeam(updateOwInFbaSign.getTeam());
                    inWarehouseDetail.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
                    inWarehouseDetail.setConfirmTime(DateUtil.date());
                    inWarehouseDetail.setConfirmUser(accountAndName);
                    detailMapper.updateById(inWarehouseDetail);

                    //3、海外仓管理账存增加
                    Boolean init = false;
                    BigDecimal oldComeQuantity = BigDecimal.ZERO;
                    BigDecimal nowInventoryQuantity;
                    BigDecimal nowComeQuantity = BigDecimal.ZERO;
                    if(overseasWarehouseManage == null){
                        //初始化海外仓管理账存信息，并修改
                        OverseasWarehouseManage newManage = new OverseasWarehouseManage();
                        newManage.setPlatform(updateOwInFbaSign.getPlatform());
                        newManage.setSysShopsName(updateOwInFbaSign.getSysShopsName());
                        newManage.setSysSite(updateOwInFbaSign.getSysSite());
                        newManage.setWarehouseName(updateOwInFbaSign.getWarehouseName());//新的收货仓名称
                        newManage.setFnSku(updateOwInFbaSign.getFullFnSku());
                        newManage.setSku(updateOwInFbaSign.getSku());
                        newManage.setMaterialCode(updateOwInFbaSign.getMaterialCode());
                        newManage.setComeQuantity(BigDecimal.ZERO);
                        newManage.setInventoryQuantity(inWarehouse.getAlreadyInventoryQuantity());
                        newManage.setCreateUser(accountAndName);
                        newManage.setCreateTime(DateUtil.date());
                        newManage.setDataSource(InitDataSourceEnum.FBA_TO_OVERSEAS.getCode());
                        overseasWarehouseManageService.save(newManage);
                        overseasWarehouseManage = newManage;
                        nowInventoryQuantity = inWarehouse.getAlreadyInventoryQuantity();
                        init = true;
                    }else{
                        //更新海外仓管理
                        OverseasWarehouseManage updateManage = new OverseasWarehouseManage();
                        updateManage.setId(overseasWarehouseManage.getId());
                        updateManage.setInventoryQuantity(overseasWarehouseManage.getInventoryQuantity().add(inWarehouse.getAlreadyInventoryQuantity()));
                        updateManage.setUpdateUser(accountAndName);
                        updateManage.setUpdateTime(DateUtil.date());
                        overseasWarehouseManageService.updateById(updateManage);
                        nowInventoryQuantity = overseasWarehouseManage.getInventoryQuantity().add(inWarehouse.getAlreadyInventoryQuantity());
                    }

                    //4、入库海外仓管理操作记录表
                    OverseasWarehouseManageRecord record = new OverseasWarehouseManageRecord();
                    BeanUtils.copyProperties(overseasWarehouseManage, record);
                    record.setId(null);
                    if(init){
                        record.setInventoryQuantity(BigDecimal.ZERO);
                    }
                    record.setParentId(overseasWarehouseManage.getId());
                    record.setInOrder(updateOwInFbaSign.getInOrder());
                    record.setOperateType(OperateTypeEnum.AMAZON_TO_OVERSEAS.getName());
                    record.setOperateTypeDetail(OperateTypeEnum.AMAZON_TO_OVERSEAS.getName());
                    record.setBusinessType(OverseasBusinessTypeEnum.IN.getName());
                    record.setCreateTime(DateUtil.date());
                    record.setCreateUser(accountAndName);
                    record.setUpdateTime(null);
                    record.setUpdateUser(null);
                    record.setDepartment(updateOwInFbaSign.getDepartment());
                    record.setTeam(updateOwInFbaSign.getTeam());
                    record.setInOrg(updateOwInFbaSign.getInOrg());
                    record.setOutOrg(updateOwInFbaSign.getOutOrg());
                    record.setRemark(updateOwInFbaSign.getRemark());
                    record.setChangeInventoryQuantity(inWarehouse.getAlreadyInventoryQuantity());
                    record.setNowInventoryQuantity(nowInventoryQuantity);
                    record.setComeQuantity(overseasWarehouseManage.getComeQuantity());
                    record.setChangeComeQuantity(BigDecimal.ZERO);
                    record.setNowComeQuantity(overseasWarehouseManage.getComeQuantity());
                    record.setRsdId(updateOwInFbaSign.getFbaId());
                    record.setIsChangeOrg("0");
                    record.setIsChangeMaterialCode("0");
                    record.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
                    recordService.save(record);
                    inIdList.add(record.getId());

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
                } catch (Exception e){
                    sumSingErrorQuantity = sumSingErrorQuantity.add(alreadyInventoryQuantity);
                    errorInfo.append("异常单号:")
                            .append(updateOwInFbaSign.getInOrder())
                            .append("，异常数量：")
                            .append(alreadyInventoryQuantity);
                    log.error("FBA发海外仓签收失败，异常单号[{}]，异常数量[{}]，异常信息[{}]", updateOwInFbaSign.getInOrder(), alreadyInventoryQuantity, e.getMessage());
                    continue;
                }finally {
                    //累加签收数量
                    sumSingQuantity = sumSingQuantity.add(updateOwInFbaSign.getShouldInventoryQuantity());
                    //当签收总数量刚好等于或者超过签收数量时，剩余的数据停止签收
                    if(sumSingQuantity.compareTo(param.getSignQuantity()) >= 0){
                        break forFlag2;
                    }
                }
            }

            //导入签收总数，实际签收总数，异常签收总数
            String errorInfoStr = errorInfo.toString();
            if(StringUtils.isNotEmpty(errorInfoStr)){
                String uploadRemark = "导入签收总数：" + param.getSignQuantity() + "，实际签收总数：" + param.getSignQuantity().subtract(sumSingErrorQuantity) + "，异常签收总数：" + sumSingErrorQuantity;
                param.setUploadRemark(uploadRemark + errorInfoStr);
                errorList.add(param);
                param.setUploadRemark(uploadRemark);
            }
        }

        //批量推送ERP->创建组织内调拨单 业务类型亚马逊仓发海外仓
        if(CollectionUtil.isNotEmpty(inIdList)){
            syncTransferToErpService.overseasFbaInSyncERP(inIdList, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS);

            //处理库龄报表入库
            ageService.batchAgeIn(ageInParam);
        }

        if(CollectionUtil.isNotEmpty(errorList)){
            String fileName = this.dealBatchSaveSignErrorList(errorList);
            if(errorList.size() == params.size()){
                //全部失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,"FBA退海外仓批量签收保存失败！", fileName);
            }
            //部分导入成功
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "FBA退海外仓批量签收部分保存成功，存在异常数据数据", fileName);
        }
        return ResponseData.success("FBA退海外仓批量签收保存成功！");
    }

    private String dealBatchSaveSignErrorList(List<OwInFbaSignParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, OwInFbaSignParam.class)
                    .sheet("FBA退海外仓批量签收导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("FBA退海外仓批量签收-保存异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("FBA退海外仓批量签收-保存异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData queryFbaReportPage(OverseasFbaReportParam param) {
        if(StringUtils.isNotEmpty(param.getConfirmStartDate())){
            param.setConfirmStartDate(param.getConfirmStartDate() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getConfirmEndDate())){
            param.setConfirmEndDate(param.getConfirmEndDate() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getShipmentStartDate())){
            param.setShipmentStartDate(param.getShipmentStartDate() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getShipmentEndDate())){
            param.setShipmentEndDate(param.getShipmentEndDate() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryFbaReportPage(PageFactory.defaultPage(), param));
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData queryFbaReportTotal(OverseasFbaReportParam param) {
        if(StringUtils.isNotEmpty(param.getConfirmStartDate())){
            param.setConfirmStartDate(param.getConfirmStartDate() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getConfirmEndDate())){
            param.setConfirmEndDate(param.getConfirmEndDate() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getShipmentStartDate())){
            param.setShipmentStartDate(param.getShipmentStartDate() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getShipmentEndDate())){
            param.setShipmentEndDate(param.getShipmentEndDate() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryFbaReportTotal(param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasFbaReportResult> exportFbaReport(OverseasFbaReportParam param) {
        if(StringUtils.isNotEmpty(param.getConfirmStartDate())){
            param.setConfirmStartDate(param.getConfirmStartDate() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getConfirmEndDate())){
            param.setConfirmEndDate(param.getConfirmEndDate() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getShipmentStartDate())){
            param.setShipmentStartDate(param.getShipmentStartDate() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getShipmentEndDate())){
            param.setShipmentEndDate(param.getShipmentEndDate() + " 23:59:59");
        }
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryFbaReportPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchFbaSignNew(List<OwInFbaSignParam> params) {
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        //直接调拨单id集合（FBA退海外仓入库）
        List<BigDecimal> inIdList = new ArrayList<>();
        //处理库龄报表入库
        List<OverseasWarehouseAge> ageInParam = new ArrayList<>();
        for (OwInFbaSignParam param : params) {
            OverseasInWarehouse inWarehouse = overseasInWarehouseService.getById(param.getId());
            //1、更新FBA发海外仓入库管理主表签收状态
            /**
             * AT/BE/CZ/DE/ES/FR/IT/NL/PL/SE:德国仓
             * CA:加拿大仓
             * US/MX:美国3仓
             * JP:日本仓
             * UK:英国仓
             */
            switch (inWarehouse.getSysSite()) {
                case "AT":
                case "BE":
                case "CZ":
                case "DE":
                case "ES":
                case "FR":
                case "IT":
                case "NL":
                case "PL":
                case "SE":
                    inWarehouse.setInWarehouseName("德国仓");
                    break;
                case "CA":
                    inWarehouse.setInWarehouseName("加拿大仓");
                    break;
                case "US":
                case "MX":
                    inWarehouse.setInWarehouseName("美国3仓");
                    break;
                case "JP":
                    inWarehouse.setInWarehouseName("日本仓");
                    break;
                case "UK":
                    inWarehouse.setInWarehouseName("英国仓");
                    break;
            }
            inWarehouse.setAlreadyInventoryQuantity(inWarehouse.getShouldInventoryQuantity());
            inWarehouse.setNotInventoryQuantity(BigDecimal.ZERO);
            inWarehouse.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
            inWarehouse.setConfirmStartTime(DateUtil.date());
            inWarehouse.setConfirmEndTime(DateUtil.date());
            inWarehouse.setConfirmUser(accountAndName);
            inWarehouse.setUpdateTime(DateUtil.date());
            inWarehouse.setUpdateUser(accountAndName);
            mapper.updateById(inWarehouse);

            //2、更新FBA发海外仓入库管理明细签收状态
            LambdaQueryWrapper<OverseasInWarehouseDetail> detailWrapper = new LambdaQueryWrapper<>();
            detailWrapper.eq(OverseasInWarehouseDetail :: getInOrder, inWarehouse.getInOrder())
                    .eq(OverseasInWarehouseDetail :: getConfirmStatus, ConfirmStatusEnum.NOT_CONFIRM.getName());
            OverseasInWarehouseDetail inWarehouseDetail = detailMapper.selectOne(detailWrapper);
            inWarehouseDetail.setConfirmStatus(ConfirmStatusEnum.ALREADY_CONFIRM.getName());
            inWarehouseDetail.setConfirmTime(DateUtil.date());
            inWarehouseDetail.setConfirmUser(accountAndName);
            inWarehouseDetail.setUpdateTime(DateUtil.date());
            inWarehouseDetail.setUpdateUser(accountAndName);
            inWarehouseDetail.setActualQuantity(inWarehouseDetail.getQuantity());
            inWarehouseDetail.setUserActualQuantity(inWarehouseDetail.getQuantity());
            detailMapper.updateById(inWarehouseDetail);

            //3、海外仓管理账存增加
            //原账存数量
            BigDecimal oldInventoryQuantity;
            //现账存数量
            BigDecimal nowInventoryQuantity;
            //根据维度查询新海外仓账存信息
            QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
            queryWrapper.eq("PLATFORM", inWarehouse.getPlatform())
                    .eq("SYS_SHOPS_NAME", inWarehouse.getSysShopsName())
                    .eq("SYS_SITE", inWarehouse.getSysSite())
                    .eq("WAREHOUSE_NAME", inWarehouse.getInWarehouseName())
                    .eq("SKU", inWarehouseDetail.getSku())
                    .eq("MATERIAL_CODE", inWarehouseDetail.getMaterialCode())
                    .likeLeft("FN_SKU", inWarehouseDetail.getFnSku());
            OverseasWarehouseManage overseasWarehouseManage = overseasWarehouseManageService.getOne(queryWrapper);
            if(overseasWarehouseManage == null){
                //初始化海外仓管理账存信息，并修改
                OverseasWarehouseManage newManage = new OverseasWarehouseManage();
                newManage.setPlatform(inWarehouse.getPlatform());
                newManage.setSysShopsName(inWarehouse.getSysShopsName());
                newManage.setSysSite(inWarehouse.getSysSite());
                newManage.setWarehouseName(inWarehouse.getInWarehouseName());//新的收货仓名称
                newManage.setFnSku(inWarehouseDetail.getFnSku());
                newManage.setSku(inWarehouseDetail.getSku());
                newManage.setMaterialCode(inWarehouseDetail.getMaterialCode());
                newManage.setComeQuantity(BigDecimal.ZERO);
                newManage.setInventoryQuantity(inWarehouse.getAlreadyInventoryQuantity());
                newManage.setCreateUser(accountAndName);
                newManage.setCreateTime(DateUtil.date());
                newManage.setDataSource(InitDataSourceEnum.FBA_TO_OVERSEAS.getCode());
                overseasWarehouseManageService.save(newManage);
                overseasWarehouseManage = newManage;
                nowInventoryQuantity = inWarehouse.getAlreadyInventoryQuantity();
                oldInventoryQuantity = BigDecimal.ZERO;
            }else{
                //更新海外仓管理
                oldInventoryQuantity = overseasWarehouseManage.getInventoryQuantity();
                nowInventoryQuantity = overseasWarehouseManage.getInventoryQuantity().add(inWarehouse.getAlreadyInventoryQuantity());
                overseasWarehouseManage.setInventoryQuantity(overseasWarehouseManage.getInventoryQuantity().add(inWarehouse.getAlreadyInventoryQuantity()));
                overseasWarehouseManage.setUpdateUser(accountAndName);
                overseasWarehouseManage.setUpdateTime(DateUtil.date());
                overseasWarehouseManageService.updateById(overseasWarehouseManage);
            }

            //4、入库海外仓管理操作记录表
            OverseasWarehouseManageRecord record = new OverseasWarehouseManageRecord();
            BeanUtils.copyProperties(overseasWarehouseManage, record);
            record.setId(null);
            record.setParentId(overseasWarehouseManage.getId());
            record.setInOrder(inWarehouse.getInOrder());
            record.setOperateType(OperateTypeEnum.AMAZON_TO_OVERSEAS.getName());
            record.setOperateTypeDetail(OperateTypeEnum.AMAZON_TO_OVERSEAS.getName());
            record.setBusinessType(OverseasBusinessTypeEnum.IN.getName());
            record.setCreateTime(DateUtil.date());
            record.setCreateUser(accountAndName);
            record.setUpdateTime(null);
            record.setUpdateUser(null);
            record.setDepartment(inWarehouseDetail.getDepartment());
            record.setTeam(inWarehouseDetail.getTeam());
            record.setInOrg(param.getInOrg());
            record.setInOrgName(param.getInOrgName());
            record.setOutOrg(param.getOutOrg());
            record.setOutOrgName(param.getOutOrgName());
            record.setRemark(inWarehouse.getRemark());
            record.setInventoryQuantity(oldInventoryQuantity);
            record.setChangeInventoryQuantity(inWarehouse.getAlreadyInventoryQuantity());
            record.setNowInventoryQuantity(nowInventoryQuantity);
            record.setComeQuantity(overseasWarehouseManage.getComeQuantity());
            record.setChangeComeQuantity(BigDecimal.ZERO);
            record.setNowComeQuantity(overseasWarehouseManage.getComeQuantity());
            record.setRsdId(inWarehouse.getParentId());
            record.setIsChangeOrg("0");
            record.setIsChangeMaterialCode("0");
            record.setSyncEbmsStatus(BaseSyncStatusEnum.NOT.getCode());
            recordService.save(record);
            inIdList.add(record.getId());

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

        //批量推送ERP->创建组织内调拨单 业务类型亚马逊仓发海外仓
        if(CollectionUtil.isNotEmpty(inIdList)){
            try{
                syncTransferToErpService.overseasFbaInSyncERP(inIdList, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS);
            }catch(Exception e){
                log.error("亚马逊仓发海外仓创建调拨单同步异常！操作记录ID：[{}]，信息：[{}]", JSONObject.toJSON(inIdList), e.getMessage());
            }
            //处理库龄报表入库
            ageService.batchAgeIn(ageInParam);
        }
        return ResponseData.success("FBA退海外仓批量签收成功！");
    }

    @Override
    @DataSource(name = "logistics")
    public OverseasInWarehouse getById(Serializable id) {
        return this.getBaseMapper().selectById(id);
    }
}
