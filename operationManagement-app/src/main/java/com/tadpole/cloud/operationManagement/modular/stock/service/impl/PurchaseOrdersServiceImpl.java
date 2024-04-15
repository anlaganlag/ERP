package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.util.K3GeneratorNoUtil;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.*;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.PurchaseOrdersMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SpecialApplyInfoHistoryMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SpecialApplyInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyItem;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrders2Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.*;
import com.tadpole.cloud.operationManagement.modular.stock.service.*;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.PurchaseOrdersReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 采购订单 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
@Slf4j
@Service
public class PurchaseOrdersServiceImpl extends ServiceImpl<PurchaseOrdersMapper, PurchaseOrders> implements IPurchaseOrdersService {

    @Resource
    private PurchaseOrdersMapper mapper;

    @Resource
    private SpecialApplyInfoMapper specialMapper;
    @Resource
    private SpecialApplyInfoHistoryMapper specialHisMapper;

    @Autowired
    SyncToErpConsumer syncToErpConsumer;

    @Autowired
    IVerifInfoRecordService verifInfoRecordService;


    @Autowired
    private ITeamVerifService teamVerifService;

    @Autowired
    K3GeneratorNoUtil k3GeneratorNoUtil;

    @Autowired
    IPmcVerifInfoService pmcVerifInfoService;

    @Autowired
    private IOperApplyInfoService operApplyInfoService;

    @Autowired
    private IOperApplyInfoHistoryService operApplyInfoHistoryService;

    @Autowired
    private IPurchaseOrdersService service;


    @DataSource(name = "stocking")
    @Override
    public PageResult<PurchaseOrdersResult> queryPage(OperApplyInfoReqVO reqVO) {
        log.info("采购申请单列表查询开始");
        long start = System.currentTimeMillis();
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();
        eform.setOperator(this.getUserAccount());
//        eform.setOperator("S20200056");
        reqVO.setEform(eform);

        Page pageContext = reqVO.getPageContext();
//        pageContext.setCurrent(reqVO.getPageNo());
//        pageContext.setSize(reqVO.getPageSize());
//
//        pageContext.setCurrent(0);
//        pageContext.setSize(10);

        IPage<PurchaseOrdersResult> page = this.baseMapper.queryPage(pageContext, reqVO);

        log.info("采购申请单列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override
    public PageResult<PurchaseOrdersVerifyResult>  applyRecord(PurchaseOrdersReqVO reqVO) {
        log.info("采购申请记录查询开始");
        long start = System.currentTimeMillis();
        Page pageContext = reqVO.getPageContext();
        IPage<PurchaseOrdersVerifyResult> page = this.baseMapper.purchaseOrderRecord(pageContext, reqVO);

        log.info("采购申请记录查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData getByPurchaseNo(@RequestParam String purchaseNo) {
        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamVerif::getPurchaseApplyNo, purchaseNo);
        List<TeamVerif> teamVerifList = teamVerifService.getBaseMapper().selectList(wrapper);
        if (ObjectUtil.isNotEmpty(teamVerifList)) {
            return ResponseData.success(teamVerifList);
        }
        return ResponseData.error("未找到对应采购编号的team审核记录");
    }

    @DataSource(name = "stocking")
    @Override
    public PageResult<OperApplyInfoResult> recordList(OperApplyInfoReqVO reqVO) {
        log.info("采购单列表查询开始");
        long start = System.currentTimeMillis();
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();

        reqVO.setEform(eform);

        //如果传了采购编号
        if (ObjectUtil.isNull(eform.getPurchaseApplyNo())  || eform.getPurchaseApplyNo().equals("")) {
            return new PageResult<>();
        }
        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
            if (ObjectUtil.isNotEmpty(eform.getBillType()) && eform.getBillType().equals("RCBH")) {
                wrapper.select(TeamVerif::getId).eq(TeamVerif::getPurchaseApplyNo,eform.getPurchaseApplyNo());
            List<TeamVerif> teams = teamVerifService.getBaseMapper().selectList(wrapper);
            if (teams.isEmpty()) {
                return new PageResult<>();
            }
            eform.setTeamVerifNoLists(teams.stream().map(t->t.getId()).collect(Collectors.toList()));
            Page pageContext = reqVO.getPageContext();

            pageContext.setSize(Integer.MAX_VALUE);
            IPage<OperApplyInfoResult> page = this.baseMapper.recordList(pageContext, eform.getTeamVerifNoLists());
            log.info("采购单列表查询结束，耗时---------->" +(System.currentTimeMillis() - start) + "ms");
            return new PageResult<>(page);
            } else if (ObjectUtil.isNotEmpty(eform.getBillType()) && !eform.getBillType().equals("RCBH")) {
                wrapper.eq(TeamVerif::getPurchaseApplyNo, eform.getPurchaseApplyNo());
                List<TeamVerif> teamVerifList =teamVerifService.list(wrapper);
                if (teamVerifList.isEmpty()) {
                    return new PageResult<>();
                }
                List<String> idList = null;
                if (ObjectUtil.isNotEmpty(teamVerifList)) {
                    idList = teamVerifList.stream().map(teamVerif -> teamVerif.getId().toString()).collect(Collectors.toList());
                }

                Page pageContext = reqVO.getPageContext();
                pageContext.setSize(Integer.MAX_VALUE);

//                List<SpecialApplyInfo> specialApplyInfos = specialMapper.selectList(
//                        new LambdaQueryWrapper<SpecialApplyInfo>().in(SpecialApplyInfo::getId, idList));

                IPage<OperApplyInfoResult> specialApplyInfos = specialMapper.recordSpecialList(pageContext,idList);
                if (specialApplyInfos!= null) {
                    return new PageResult<>(specialApplyInfos);

                } else {
                    IPage<OperApplyInfoResult> specialHisApplyInfos = specialHisMapper.recordSpecialHisList(pageContext,idList);
                    return new PageResult<>(specialHisApplyInfos);
                }

            }

        return new PageResult<>();


    }

    @DataSource(name = "stocking")
    @Override
    @Transactional
    public ResponseData batchSave(List<PurchaseOrdersParam> ordersParamList) {
        if (ObjectUtil.isEmpty(ordersParamList)) {
            return ResponseData.error("批量保存所传递数据为空");
        }

        List<PurchaseOrdersParam> errorList = new ArrayList<>();

        for (PurchaseOrdersParam ordersParam : ordersParamList) {
            ResponseData result = this.savePurchaseOrders(ordersParam);
            if (! result.getSuccess()) {
                errorList.add(ordersParam);
            }
        }

        if (ObjectUtil.isNotEmpty(errorList)) {
            return ResponseData.error(500,"以下数据保存失败",errorList);
        }

        return ResponseData.success();
    }


    @Override
    @DataSource(name = "stocking")
    public List<PurchaseOrdersResult> exportExcel(OperApplyInfoReqVO param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        OperApplyInfoReqVO.Eform eform = param.getEform();
        eform.setOperator(this.getUserAccount());
        param.setEform(eform);
        IPage<PurchaseOrdersResult> page = this.baseMapper.queryPage(pageContext, param);
        return page.getRecords();

    }


    @Override
    @DataSource(name = "stocking")
    public List<PurchaseOrdersExcelResult> queryByCondition(OperApplyInfoReqVO param) {

        OperApplyInfoReqVO.Eform eform = param.getEform();
  /*      if (ObjectUtil.isNull(eform)) {
            new OperApplyInfoReqVO.Eform();
        }*/
        eform.setOperator(this.getUserAccount());
//        eform.setOperator("S20170091");
        param.setEform(eform);
        List<PurchaseOrdersExcelResult> purchaseOrdersResultList = this.baseMapper.queryByCondition(param);
        return purchaseOrdersResultList;

    }

    @Override
    @DataSource(name = "stocking")
    public List<PurchaseOrdersVerifyResult> exportRecordExcel(PurchaseOrdersReqVO param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<PurchaseOrdersVerifyResult> page = this.baseMapper.purchaseOrderRecord(pageContext, param);
        return page.getRecords();

    }

    @DataSource(name = "stocking")
    @Override
    @Transactional
    public ResponseData overTimeAction(StockApprovaltimeParameterResult parameter) {

        if (! operApplyInfoService.checkOverTime(parameter)) {
            return ResponseData.success("未到自动提交时间");
        }

        LambdaQueryWrapper<PurchaseOrders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrders::getOrderStatus, StockConstant.ORDER_STATUS_WAIT)
                .eq(PurchaseOrders::getBillType,"RCBH");//日常备货

        List<PurchaseOrders> ordersList = mapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(ordersList)) {
            return ResponseData.success("未找到需要自动处理的采购申请单");
        }

        ArrayList<VerifInfoRecord> recordList = new ArrayList<>();
        boolean action = "Y".equals(parameter.getParameterResult());//ture 超时通过  false 超时不通过

        for (PurchaseOrders order : ordersList) {

            boolean qtyPass = ObjectUtil.isNotNull(order.getPurchaseApplyQty()) && order.getPurchaseApplyQty().compareTo(BigDecimal.ZERO) > 0;
           boolean doAction= action && qtyPass;
            VerifInfoRecord verifInfoRecord = this.autoCreatVerifInfo(doAction, order, StockConstant.VERIF_BIZ_TYPE_DEPARTMENT);
            recordList.add(verifInfoRecord);
            if (doAction) {
                order.setOrderStatus(StockConstant.ORDER_STATUS_PLAN_WAIT);
                order.setOverTimeNot(StockConstant.ORACLE_OVERTIME_ACTION_YES.longValue());
                order.setOrderLastTime(this.lastOrderTime(order.getPlatform(),order.getTeam(),order.getMaterialCode()));
            } else {
                order.setOverTimeNot(StockConstant.ORACLE_OVERTIME_ACTION_NO.longValue());
                order.setOrderStatus(StockConstant.ORDER_STATUS_N0_STOCK);
            }
            order.setUpdateTime(new Date());
        }

        verifInfoRecordService.saveBatch(recordList);

        this.updateBatchById(ordersList);

        return ResponseData.success("自动处理的采购申请单:"+ordersList.size()+"条");

    }

    @DataSource(name = "stocking")
    @Override
    @Transactional
    public ResponseData planOverTimeAction(StockApprovaltimeParameterResult parameter) {

        if (! operApplyInfoService.checkOverTime(parameter)) {
            return ResponseData.success("未到自动提交时间");
        }

        LambdaQueryWrapper<PurchaseOrders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrders::getOrderStatus, StockConstant.ORDER_STATUS_PLAN_WAIT)
                .eq(PurchaseOrders::getBillType,"RCBH");//日常备货

        List<PurchaseOrders> ordersList = mapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(ordersList)) {
            return ResponseData.success();
        }

        ArrayList<VerifInfoRecord> recordList = new ArrayList<>();
        boolean action = "Y".equals(parameter.getParameterResult());//ture 超时通过  false 超时不通过

        for (PurchaseOrders order : ordersList) {
            VerifInfoRecord verifInfoRecord = this.autoCreatVerifInfo(action, order, StockConstant.VERIF_BIZ_TYPE_PLAN);
            recordList.add(verifInfoRecord);
        }

        ordersList.forEach(o->{
            if (action) {
                o.setOrderStatus(StockConstant.ORDER_STATUS_PMC_WAIT);
                o.setOverTimeNot(StockConstant.ORACLE_OVERTIME_ACTION_NO.longValue());
            } else {
                o.setOverTimeNot(StockConstant.ORACLE_OVERTIME_ACTION_YES.longValue());
                o.setOrderStatus(StockConstant.ORDER_STATUS_N0_STOCK);
            }
            o.setUpdateTime(new Date());
        });

        verifInfoRecordService.saveBatch(recordList);

        this.updateBatchById(ordersList);

        if (action) {
            Map<String, List<PurchaseOrders>> orderMap = ordersList.stream().collect(Collectors.groupingBy(PurchaseOrders::getId));
            Map<String, List<VerifInfoRecord>> recordMap = recordList.stream().collect(Collectors.groupingBy(VerifInfoRecord::getPurchaseOrderId));
            for (Map.Entry<String, List<PurchaseOrders>> entry : orderMap.entrySet()) {
                String orderId = entry.getKey();
                PurchaseOrders order = entry.getValue().get(0);
                VerifInfoRecord record = recordMap.get(orderId).get(0);
                pmcVerifInfoService.createPmcVerifyInfo(record, order);
            }
        }

        return ResponseData.success();
    }


    /**
     * 查询当前登录人员可以访问的物料编码
     *
     * @param reqVO
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    public Set<String> queryMaterialCodeList(OperApplyInfoReqVO reqVO) {
        log.info("采购申请单物料编码list查询开始");
        Set<String> mateCodeList = new HashSet<>();
        long start = System.currentTimeMillis();
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();
//        eform.setOperator(this.getUserAccount());
        reqVO.setEform(eform);

        List<PurchaseOrders> ordersList = this.baseMapper.queryOrderList("");
        if (ObjectUtil.isNotEmpty(ordersList)) {
            mateCodeList = ordersList.stream().map(o -> o.getMaterialCode()).collect(Collectors.toSet());
        }
        log.info("采购申请单物料编码list查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return mateCodeList;
    }


    /**
     * 采购申请单保存
     *
     * @param ordersParam
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData savePurchaseOrders(PurchaseOrdersParam ordersParam) {

    if (ObjectUtil.isEmpty(ordersParam) || ObjectUtil.isNull(ordersParam.getId())) {
            return ResponseData.error("id必传");
        }
        PurchaseOrders purchaseOrder = this.getById(ordersParam.getId());

        if (ObjectUtil.isEmpty(purchaseOrder)) {
            return ResponseData.error("id:" + ordersParam.getId() + "未找到相应的数据记录");
        }

        if (StockConstant.ORDER_STATUS_WAIT != purchaseOrder.getOrderStatus()) {
            return ResponseData.error("当前订单已经不处‘采购申请待审核’状态");
        }

        purchaseOrder.setPurchaseApplyQty(ordersParam.getPurchaseApplyQty());
        if (ObjectUtil.isNotEmpty(ordersParam.getPurchaseApplyQty()) && ordersParam.getPurchaseApplyQty().compareTo(BigDecimal.ZERO) <= 0) {
            purchaseOrder.setPurchaseApplyQty(BigDecimal.ZERO);
        }

        if (ObjectUtil.isNotEmpty(ordersParam.getPurchaseReason())) {
            purchaseOrder.setPurchaseReason(ordersParam.getPurchaseReason());
        }
        if (ObjectUtil.isNotEmpty(ordersParam.getOrderStatus())
                &&( (ordersParam.getOrderStatus() == StockConstant.ORDER_STATUS_PLAN_WAIT)
                    || ordersParam.getOrderStatus() == StockConstant.ORDER_STATUS_N0_STOCK )) {
            purchaseOrder.setOrderStatus(ordersParam.getOrderStatus());
        }

        purchaseOrder.setUpdateTime(new Date());
        Date date = this.lastOrderTime(purchaseOrder.getPlatform(),purchaseOrder.getTeam(),purchaseOrder.getMaterialCode());
        purchaseOrder.setOrderLastTime(date);
        purchaseOrder.setTurnoverDays(ordersParam.getTurnoverDays());

        if (this.updateById(purchaseOrder)) {
            return ResponseData.success(purchaseOrder);
        }
        return ResponseData.error("保存失败");

    }


    /**
     * 单个审核提交
     *
     * @param ordersParam
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData comitPurchaseOrders(PurchaseOrdersParam ordersParam) {

        ordersParam.setOrderStatus(StockConstant.ORDER_STATUS_PLAN_WAIT);//待计划部审批
        boolean pass = ordersParam.getPurchaseApplyQty().compareTo(BigDecimal.ZERO) <= 0;
        if (pass) {
            ordersParam.setOrderStatus(StockConstant.ORDER_STATUS_N0_STOCK);//申请数量小于等于0为不备货
        }

        ResponseData responseData = this.savePurchaseOrders(ordersParam);
        if (!responseData.getSuccess() || pass) {
            return responseData;
        }

        PurchaseOrders purchaseOrder = (PurchaseOrders) responseData.getData();
        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        VerifInfoRecord verifInfoRecord = this.creatVerifInfo(ordersParam.getPurchaseReason(), ordersParam.getPurchaseApplyQty(), ordersParam.getId(), currentUser, StockConstant.VERIF_BIZ_TYPE_DEPARTMENT);

        if (verifInfoRecordService.save(verifInfoRecord)) {
            return ResponseData.success();
        }
        return ResponseData.error("提交失败");
    }


    /**
     * 批量提交
     *
     * @param idList
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchSubmit(List<String> idList) {
        try {
            log.info(new Date()+"批量提交入参"+idList.toString());
            List<PurchaseOrders> ordersList = this.baseMapper.selectBatchIds(idList);
            List<PurchaseOrders> successList = new ArrayList<>();
            List<PurchaseOrders> failIdList = new ArrayList<>();

            if (idList.size() != ordersList.size()) {
                ResponseData.error("数据传递的id个体数和实体查询出来的数据记录条数不相等");
            }

            ordersList.forEach(order -> {
                boolean pass = order.getPurchaseApplyQty().compareTo(BigDecimal.ZERO) <= 0;
                if (pass) {
                    order.setOrderStatus(StockConstant.ORDER_STATUS_N0_STOCK);//申请数量小于0不备货
                    failIdList.add(order);
                } else {
                    order.setOrderStatus(StockConstant.ORDER_STATUS_PLAN_WAIT);
                    successList.add(order);
                }
                order.setUpdateTime(new Date());
            });

            this.updateBatchById(ordersList);


            //id,申请数量>0
            Map<String, BigDecimal> dbOrderMap = new HashMap<String, BigDecimal>();
            if (ObjectUtil.isNotEmpty(successList)) {
                dbOrderMap = successList.stream().filter(i -> i != null && i.getId() != null && i.getPurchaseApplyQty() != null)
                        .collect(Collectors.toMap(PurchaseOrders::getId, PurchaseOrders::getPurchaseApplyQty));
            }
            Map<String, String> dbOrderVerifReasonMap = successList.stream()
                    .filter(i -> i != null && i.getId() != null && i.getPurchaseReason() != null)
                    .collect(Collectors.toMap(PurchaseOrders::getId, PurchaseOrders::getPurchaseReason));
            LoginContext current = SpringContext.getBean(LoginContext.class);
            LoginUser currentUser = current.getLoginUser();

            ArrayList<VerifInfoRecord> verifist = new ArrayList<>();

            for (Map.Entry<String, BigDecimal> entry : dbOrderMap.entrySet()) {
                String reason = dbOrderVerifReasonMap.get(entry.getKey());
                VerifInfoRecord verifInfoRecord = this.creatVerifInfo(reason, entry.getValue(), entry.getKey(), currentUser, StockConstant.VERIF_BIZ_TYPE_DEPARTMENT);
                verifInfoRecord.setVerifResult(StockConstant.VERIFY_SUCESS);
                verifist.add(verifInfoRecord);
            }


            if (verifInfoRecordService.saveBatch(verifist)) {
                return ResponseData.success();
            }

            return ResponseData.error("批量提交失败");
        } catch (Exception  e) {
            log.error(e.getMessage());
            return ResponseData.error(StrUtil.format("批量提交失败{}",e.getMessage()));
        }
    }


    /**
     * 计划部审批+PMC审批查询列表
     *
     * @param reqVO
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<PurchaseOrdersResult> queryplanVerifList(PurchaseOrdersParam reqVO) {
        log.info("计划部审批查询");
        reqVO.setOperator(this.getUserAccount());
        long start = System.currentTimeMillis();
        Page pageContext = reqVO.getPageContext();
        IPage<PurchaseOrdersResult> page = this.baseMapper.queryplanVerifList(pageContext, reqVO);

        log.info("计划部审批+PMC审批查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    /**
     * 计划部审批+PMC审批查询列表
     *
     * @param reqVO
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<PlanPurchaseOrdersResult> planVerifList(PurchaseOrdersParam reqVO) {
        log.info("计划部审批查询");
        reqVO.setOperator(this.getUserAccount());
        long start = System.currentTimeMillis();
        Page pageContext = reqVO.getPageContext();
        IPage<PlanPurchaseOrdersResult> page = this.baseMapper.planVerifList(pageContext, reqVO);

        log.info("计划部审批+PMC审批查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public PageResult<PlanPurchaseOrders2Result> planVerifList2(PurchaseOrders2Param req) {
        log.info("计划部审批查询");
        long start = System.currentTimeMillis();
        Page pageContext = req.getPageContext();
        IPage<PlanPurchaseOrders2Result> page = this.baseMapper.planVerifList2(pageContext, req);

        log.info("计划部审批+PMC审批查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }


    /**
     * 计划部审核
     *
     * @param param
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData verif(PurchaseOrdersParam param) {

        try {
            boolean createPmcVerif = false;
            PurchaseOrders order = this.getById(param.getId());

            if (ObjectUtil.isNotNull(order) &&  StockConstant.ORDER_STATUS_PLAN_WAIT != order.getOrderStatus()) {
              return   ResponseData.error("该订单已经不处于‘待计划部审核’状态,不能重复审核");
            }

            LoginContext current = SpringContext.getBean(LoginContext.class);
            LoginUser currentUser = current.getLoginUser();

            String result = param.getVerifResult();

            if (StockConstant.VERIFY_SUCESS.equals(result)) {//审核通过
                order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_WAIT); //4:计划通过待pmc审核
                createPmcVerif = true;
            } else {
                order.setOrderStatus(StockConstant.ORDER_STATUS_PLAN_NO); //3:计划未通过
            }

            BigDecimal applyQty = param.getQty();

            if (applyQty.compareTo(BigDecimal.ZERO) <= 0) {
                applyQty = order.getPurchaseApplyQty();
            }

            VerifInfoRecord verifInfoRecord =
                    this.creatVerifInfo(param.getVerifReason(), applyQty, order.getId(), currentUser, StockConstant.VERIF_BIZ_TYPE_PLAN);
            verifInfoRecord.setVerifResult(result);

            if (verifInfoRecordService.save(verifInfoRecord) && this.updateById(order)) {
                if (!createPmcVerif) {
                    return ResponseData.success();
                }
                return pmcVerifInfoService.createPmcVerifyInfo(verifInfoRecord, order);
            }


        } catch (Exception e) {
            log.error("计划部审批提交异常：{}", e);
            return ResponseData.error("计划部审批提交异常" + JSONUtil.toJsonStr(e));
        }

        return ResponseData.error("计划部审批提交失败");
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData detail(PurchaseOrdersParam param) {

        String id = param.getId();
        if (ObjectUtil.isEmpty(id)) {
            ResponseData.error("id不能为空");
        }
        PurchaseOrders order = this.baseMapper.selectById(id);

        LambdaQueryWrapper<TeamVerif> queryWrapper = new LambdaQueryWrapper<>();

        if (param.getIsSameType()) {
            queryWrapper.eq(TeamVerif::getPurchaseApplyNo, id);
        } else {
            LambdaQueryWrapper<PurchaseOrders> orderWrapper = new LambdaQueryWrapper<>();
            orderWrapper.eq(PurchaseOrders::getMaterialCode, order.getMaterialCode())
                    .eq(PurchaseOrders::getDepartment, order.getDepartment())
                    .eq(PurchaseOrders::getTeam, order.getTeam())
                    .eq(PurchaseOrders::getOrderStatus, StockConstant.ORDER_STATUS_PLAN_WAIT)
                    .ne(PurchaseOrders::getId, order.getId());
            List<PurchaseOrders> ordersList = this.baseMapper.selectList(orderWrapper);

            if (ObjectUtil.isNotEmpty(ordersList)) {
                queryWrapper.in(TeamVerif::getPurchaseApplyNo, ordersList.stream().map(o -> o.getId()).collect(Collectors.toList()));
            } else {
                return ResponseData.success();
            }
        }
        List<TeamVerif> verifList = teamVerifService.getBaseMapper().selectList(queryWrapper);
        return ResponseData.success(verifList);
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData getAsinStockReason(PurchaseOrdersParam param) {

        String id = param.getId();
        if (ObjectUtil.isEmpty(id)) {
            return ResponseData.error("传入Id为空");
        }
        LambdaQueryWrapper<OperApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OperApplyInfo::getTeamVerifNo, id);
        List<OperApplyInfo> verifList = operApplyInfoService.getBaseMapper().selectList(queryWrapper);
        if (CollectionUtil.isEmpty(verifList)) {
            LambdaQueryWrapper<OperApplyInfoHistory> queryHisWrapper = new LambdaQueryWrapper<>();
            queryHisWrapper.eq(OperApplyInfoHistory::getTeamVerifNo, id);
            List<OperApplyInfoHistory> verifHisList = operApplyInfoHistoryService.getBaseMapper().selectList(queryHisWrapper);
            Map<String, String> collect = verifHisList.stream().collect(
                    Collectors.toMap(OperApplyInfoHistory::getAsin, i-> Optional.ofNullable(i.getStockReason()).orElse(""), (v1, v2) -> v1));

            return ResponseData.success(collect);
        }
        Map<String, String> collect = verifList.stream().collect(
                Collectors.toMap(OperApplyInfo::getAsin, i-> Optional.ofNullable(i.getStockReason()).orElse(""), (v1, v2) -> v1));
        return ResponseData.success(collect);
    }


    /**
     * pmc 审核
     *
     * @param param
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pmcVerif(PurchaseOrdersParam param) {
        PurchaseOrders order = this.getById(param.getId());
        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        String result = param.getVerifResult();

        boolean needCallK3 = false;

        if (StockConstant.VERIFY_SUCESS.equals(result)) {
            order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_YES); //PMC通过
            needCallK3 = true;
        } else {
            order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_NO); //PMC未通过
        }

        BigDecimal applyQty = param.getQty();

        if (applyQty.compareTo(BigDecimal.ZERO) <= 0) {
            applyQty = order.getPurchaseApplyQty();
        } else {
            order.setPurchaseApplyQty(applyQty);
        }


        VerifInfoRecord verifInfoRecord =
                this.creatVerifInfo(order.getPurchaseReason(), applyQty, order.getId(), currentUser, StockConstant.VERIF_BIZ_TYPE_PMC);
        verifInfoRecord.setVerifResult(result);


        if (verifInfoRecordService.save(verifInfoRecord) && this.updateById(order)) {
            if (!needCallK3) {
                return ResponseData.success();
            }
            if (callK3(verifInfoRecord, order)) {
                return ResponseData.success();
            }
        }
        return ResponseData.error("审核提交失败");
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean callK3(VerifInfoRecord verifInfoRecord, PurchaseOrders order) {

        boolean syncResult = false;

        String billNo = k3GeneratorNoUtil.getNoByKey("", StockConstant.SYNC_K3_PURCHASE_ORDER_KEY, 6);

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamVerif::getPurchaseApplyNo, order.getId());
        List<TeamVerif> teamVerifList = teamVerifService.list(wrapper);

        ArrayList<K3PurchaseOrderApplyItem> itemList = new ArrayList<>();

        for (TeamVerif teamVerif : teamVerifList) {
            K3PurchaseOrderApplyItem applyItem = new K3PurchaseOrderApplyItem();
            applyItem.setFPaezBase(teamVerif.getTeam());
            applyItem.setFPaezBase2(teamVerif.getVerifPersonNo());
            applyItem.setFMaterialId(teamVerif.getMaterialCode());
            applyItem.setFReqQty(teamVerif.getSalesDemand().intValue());
            applyItem.setFPurchaserId(verifInfoRecord.getPurchasePersonId());
            applyItem.setFSuggestSupplierId(verifInfoRecord.getAdviceSupplierId());
            applyItem.setFEntryNote(verifInfoRecord.getRemark());
            applyItem.setFBscHdate(order.getOperExpectedDate());
            itemList.add(applyItem);
        }

        K3PurchaseOrderApplyParam orderApplyParam = new K3PurchaseOrderApplyParam();
        orderApplyParam.setFCreateDate(order.getCreateTime());
        orderApplyParam.setFBillNo(billNo);
        orderApplyParam.setFApplicationDate(order.getCreateTime());
        orderApplyParam.setFCreatorId(currentUser.getAccount());
        orderApplyParam.setFApplicantId(currentUser.getAccount());
        orderApplyParam.setFNote(verifInfoRecord.getRemark());
        orderApplyParam.setFEntity(itemList);

        String jsonString = JSON.toJSONString(orderApplyParam);
        JSONArray jsonArray = JSON.parseArray(jsonString);

        verifInfoRecord.setBillNo(billNo);
        verifInfoRecord.setSyncRequestMsg(jsonString);

        String code = null;
        try {
            code = syncToErpConsumer.syncPurschase(jsonArray);
            verifInfoRecord.setSyncResultMsg(code);
        } catch (Exception e) {
            log.error("备货2.0调用k3采购订单接口异常:{}", e);
            verifInfoRecord.setSyncResultMsg(JSON.toJSONString(e));
        }

        if (ObjectUtil.isNotEmpty(code) && code.equals("0")) {
            //同步成功修改状态为1
            verifInfoRecord.setSyncStatus(StockConstant.SYNC_SUCESS);
            syncResult = true;
        } else {
            verifInfoRecord.setSyncStatus(StockConstant.SYNC_FAIL);
        }
        verifInfoRecord.setSyncTime(new Date());

        verifInfoRecordService.updateById(verifInfoRecord);

        return syncResult;
    }


    /**
     * 计划部批量审批
     *
     * @param paramList
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchVerif(List<PurchaseOrdersParam> paramList) {

        if (ObjectUtil.isEmpty(paramList)) {
            return ResponseData.error("提交数据不能为空");
        }

        List<PurchaseOrdersParam> errorList = new ArrayList<>();

        for (PurchaseOrdersParam purchaseOrdersParam : paramList) {
            ResponseData responseData = this.verif(purchaseOrdersParam);
            if (!responseData.getSuccess()) {
                errorList.add(purchaseOrdersParam);
            }
        }

        if (ObjectUtil.isEmpty(errorList)) {
            return ResponseData.success();
        }
        log.error("plan审批部分失败{}", errorList);
        return ResponseData.error(500, "部分审批失败", errorList);
    }


    /**
     * 创建审核信息记录
     *
     * @param reason       审批原因
     * @param qty          审批数量
     * @param id
     * @param currentUser  操作人
     * @param verifBizType 业务类型
     * @return
     */
    private VerifInfoRecord creatVerifInfo(String reason, BigDecimal qty, String id, LoginUser currentUser, int verifBizType) {
        Date date = new Date();
        VerifInfoRecord verifInfoRecord = VerifInfoRecord.builder()
                .id(IdWorker.getIdStr())
                .qty(qty)
                .purchaseOrderId(id)
                .verifPersonName(currentUser.getName())
                .verifPersonNo(currentUser.getAccount())
//                .verifReason(reason)
                .verifDate(date)
                .createTime(date)
                .verifBizType(verifBizType)//事业部审核10，计划部审核20，PMC审核30  */
                .verifType("0")//值域{"0:自动"/"1:人工"}
                .build();

        if (ObjectUtil.isNotEmpty(reason)) {
            verifInfoRecord.setVerifReason(reason);
        }
        return verifInfoRecord;
    }


    /**
     * 自动审核的时候创建审核信息记录
     *
     * @param action       超时是否通过
     * @param order
     * @param verifBizType 业务类型
     * @return
     */
    private VerifInfoRecord autoCreatVerifInfo(boolean action, PurchaseOrders order, int verifBizType) {


        String reson = "System超时不通过";
        BigDecimal qty = BigDecimal.ZERO;
        String verifResult = "0";
        boolean qtyPass = ObjectUtil.isNotNull(order.getPurchaseApplyQty()) && order.getPurchaseApplyQty().compareTo(BigDecimal.ZERO) > 0;
        if (!qtyPass) {
            reson = reson + "--申请数量需要大于0";
        }

        if (action && qtyPass ) {//超时通过 //申请数量大于0的
            reson= "System超时自动通过";
            verifResult = "1";
            qty=order.getPurchaseApplyQty();
        }
        reson=reson+"--业务类型："+verifBizType;

        Date date = new Date();
        VerifInfoRecord verifInfoRecord = VerifInfoRecord.builder()
                .id(IdWorker.getIdStr())
                .qty(qty)
                .purchaseOrderId(order.getId())
                .verifPersonName("System")
                .verifPersonNo("Syste")
                .verifReason(reson)
                .verifDate(date)
                .createTime(date)
                .verifBizType(verifBizType)//事业部审核10，计划部审核20，PMC审核30  */
                .verifType("0")//值域{"0:自动"/"1:人工"}
                .verifResult(verifResult)
                .build();
        return verifInfoRecord;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional
    public Date lastOrderTime(String platform, String team, String materialCode) {
        LambdaQueryWrapper<PurchaseOrders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrders::getOrderStatus, StockConstant.ORDER_STATUS_PMC_YES)
                .eq(PurchaseOrders::getPlatform,platform)
                .eq(PurchaseOrders::getTeam,team)
                .eq(PurchaseOrders::getMaterialCode,materialCode)
                .orderByDesc(PurchaseOrders::getUpdateTime);
        List<PurchaseOrders> purchaseOrders = this.baseMapper.selectList(wrapper);

        if (ObjectUtil.isNotEmpty(purchaseOrders)) {
            return purchaseOrders.get(0).getUpdateTime();
        }
        return null;
    }


    @DataSource(name = "stocking")
    @Override
    public void flashLastOrderTime() {
        mapper.flashLastOrderTime();
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional
    public PurchaseOrders initPurchaseOrders(List<TeamVerif> teamVerifList) {


        return null;
    }

    @DataSource(name = "stocking")
    @Override
    public PageResult<PurchaseOrdersResult> queryPageV3(OperApplyInfoReqV3 reqVO) {

        log.info("采购申请单列表查询开始");
        long start = System.currentTimeMillis();
        LoginUser loginUser = LoginContext.me().getLoginUser();
        reqVO.setOperator(this.getUserAccount());
        reqVO.setDepartment(loginUser.getDepartment());

        Page pageContext = reqVO.getPageContext();
        IPage<PurchaseOrdersResult> page = this.baseMapper.queryPageV3(pageContext, reqVO);

        log.info("采购申请单列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override
    public List<PurchaseOrdersResult> exportExcelV3(OperApplyInfoReqV3 param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        param.setOperator(this.getUserAccount());

        IPage<PurchaseOrdersResult> page = this.baseMapper.queryPageV3(pageContext, param);
        return page.getRecords();
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
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
