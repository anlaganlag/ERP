package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;

import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;

import com.tadpole.cloud.operationManagement.modular.stock.mapper.SpecialApplyInfoHistoryMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SpecialApplyInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.TeamVerifMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoExcelParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.*;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 特殊备货申请信息 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-24
 */
@Service
@Slf4j
public class SpecialApplyInfoServiceImpl extends ServiceImpl<SpecialApplyInfoMapper, SpecialApplyInfo> implements ISpecialApplyInfoService {

    @Resource
    private SpecialApplyInfoMapper mapper;


    @Resource
    private SpecialApplyInfoHistoryMapper hisMapper;

    @Resource
    private TeamVerifMapper teamVerifMapper;


    @Autowired
    private ITeamVerifService teamVerifService;


    @Autowired
    private IPurchaseOrdersService purchaseOrdersOriService;


    @Autowired
    private IPurchaseOrdersService purchaseOrdersService;

    @Autowired
    IVerifInfoRecordService verifInfoRecordService;

    @Autowired
    IPmcVerifInfoService pmcVerifInfoService;


    @DataSource(name = "stocking")
    @Override
    public PageResult<SpecialApplyInfoResult> queryPage(SpecialApplyInfoReqVO reqVO) {

        log.info("特殊备货申请列表查询开始");
        long start = System.currentTimeMillis();
        SpecialApplyInfoReqVO.Eform eform = reqVO.getEform();
        String operator = this.getUserAccount();
        eform.setOperator(operator);
        reqVO.setEform(eform);

        Page pageContext = reqVO.getPageContext();
        pageContext.setCurrent(reqVO.getPageNo());
        pageContext.setSize(reqVO.getPageSize());
        IPage<SpecialApplyInfoResult> page = this.baseMapper.queryPage(pageContext, reqVO);
        log.info("特殊备货申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override
    public List<SpecialApplyInfoResult> specialExport(SpecialApplyInfoReqVO reqVO) {
        log.info("特殊备货导出>>>特殊申请列表查询开始");
        long start = System.currentTimeMillis();
        String operator = ObjectUtil.isNotEmpty(this.getUserAccount()) ? this.getUserAccount() : null;
        SpecialApplyInfoReqVO.Eform eform = reqVO.getEform();
        eform.setOperator(operator);
        reqVO.setEform(eform);


        List<SpecialApplyInfoResult> operExportList = this.baseMapper.specialExport(reqVO);
        log.info("运营申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return operExportList;
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData update(SpecialApplyInfo applyInfo) {

        this.baseMapper.updateById(applyInfo);

        return ResponseData.success();
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData commitBatch(List<SpecialApplyReqVO> reqParamList) {

        log.info("特殊备货申请保存提交处理开始");
        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        // 1. 数据状态变更，提交人，提交时间补充
        /**
         * 1、批量保存备货申请信息
         */
        //区域五维度(物料+Team+区域)
        Set<String> platformMaterialCodeTeamAreaSet = new HashSet<>();
        //物料四维度(物料+Team)
        Set<String> platformMaterialCodeTeamSet = new HashSet<>();
//        Set<String> reasonSet = new HashSet<>();

        String finalUserAccount = userAccount;
        reqParamList.stream().forEach(i -> {
            i.setRequireBy(finalUserAccount);
            platformMaterialCodeTeamAreaSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam() + i.getArea());
            platformMaterialCodeTeamSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam());

        });

        //提交 批量修改状态requireBy和修改stockStatus值为2即已提交,
        this.baseMapper.commitUpdateBatch(reqParamList);

        //提交下一节点集合
        List<TeamVerif> TeamVerifLists = new ArrayList<>();
        List<PurchaseOrders> PurchaseOrdersLists = new ArrayList<>();

        // 2. 变更物料、team、区域、下Asin是否都已经提交
        if (CollectionUtil.isNotEmpty(platformMaterialCodeTeamAreaSet)) {

            // 3. 物料、team、区域都已提交的插入team审核表
            for (String platformMaterialCodeTeamArea : platformMaterialCodeTeamAreaSet) {
                //取出物料、team、区域明细汇总即合并维度
                SpecialApplyInfo commitAreaApply = this.baseMapper.selectAreaCommit(platformMaterialCodeTeamArea, reqParamList);
                if (ObjectUtil.isEmpty(commitAreaApply)) {
                    continue;
                }
                TeamVerif teamVerif = BeanUtil.copyProperties(commitAreaApply, TeamVerif.class);
                if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                    teamVerif.setVersionDes("");
                }
                //补充销售需求覆盖日期
                if (ObjectUtil.isEmpty(commitAreaApply.getSalesStockDays())) {
                    teamVerif.setRecomBackOverDays(DateUtil.date());
                } else {
                    teamVerif.setRecomBackOverDays(DateUtil.offsetMonth(DateUtil.date(), commitAreaApply.getSalesStockDays().intValue()));
                }
                if (teamVerif.getTotalVolume() == null) {
                    teamVerif.setTotalVolume(new BigDecimal(0));
                }
                BigDecimal salesDemand = teamVerif.getSalesDemand().max(teamVerif.getTotalVolume());
                if (ObjectUtil.isEmpty(salesDemand)) {
                    teamVerif.setTurnoverDays(BigDecimal.ZERO);
                } else if (teamVerif.getDayavgqty().equals(BigDecimal.ZERO)) {
                    teamVerif.setTurnoverDays(new BigDecimal(99999));
                } else {
                    teamVerif.setTurnoverDays(salesDemand.divide(teamVerif.getDayavgqty(), 4, RoundingMode.HALF_UP));
                }

                //计算AZ超180天库龄数量占比
                if (teamVerif.getInStockQty().compareTo(BigDecimal.ZERO) != 0) {
                    teamVerif.setOverD180InvAgeQtyRate(teamVerif.getOverD180InvAgeQty()
                            .divide(teamVerif.getInStockQty(), 2, BigDecimal.ROUND_HALF_UP));
                } else {
                    teamVerif.setOverD180InvAgeQtyRate(new BigDecimal(99999));
                }
                //AZ海外总库存供货天数
                if (teamVerif.getPreSaleQty().compareTo(BigDecimal.ZERO) != 0 && teamVerif.getTotalBackDays().compareTo(BigDecimal.ZERO) != 0) {
                    teamVerif.setPrepareDays(teamVerif.getTotalVolume().divide(teamVerif.getPreSaleQty()
                            .divide(teamVerif.getTotalBackDays(), 8, BigDecimal.ROUND_HALF_UP), 0, BigDecimal.ROUND_DOWN));
                } else {
                    teamVerif.setPrepareDays(new BigDecimal(99999));
                }
                //审核状态是2
                teamVerif.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_APPLY);
                teamVerif.setBizdate(DateUtil.date());
                teamVerif.setVerifPersonNo(userAccount);
                teamVerif.setVerifPersonName(userName);
                teamVerif.setOverTimeNot(BigDecimal.ONE);
                teamVerifService.save(teamVerif);
                TeamVerifLists.add(teamVerif);
                String teamVerifNo = teamVerif.getId().toString();
                this.baseMapper.updateTeamVerifNo(platformMaterialCodeTeamArea, teamVerifNo, userAccount, userName, reqParamList);

            }
            // 无可成功提交team审核的数据
            if (TeamVerifLists.size() == 0) {
                return ResponseData.success();
            }

            for (String platformMaterialCodeTeam : platformMaterialCodeTeamSet) {
                //取出物料、team明细汇总
                List<PurchaseOrdersResult> commitMatApplyList = this.baseMapper.selectMatCommit(platformMaterialCodeTeam, TeamVerifLists);
                if (CollectionUtil.isEmpty(commitMatApplyList)) {
                    continue;
                }
                for (PurchaseOrdersResult purchaseOrdersResult : commitMatApplyList) {
                    PurchaseOrders orders = BeanUtil.copyProperties(purchaseOrdersResult, PurchaseOrders.class);
                    //0待审核
                    orders.setOrderStatus(StockConstant.ORDER_STATUS_WAIT);


                    // 申请备货数量计算逻辑错误，需要修改成采购申请数量=SUM(特殊备货申请.申请区域备货数量D6）-国内可用库存-采购未完成数量-申请审核中数量
                    BigDecimal applyQty = orders.getPurchaseApplyQty().subtract(orders.getCanuseqty()).subtract(orders.getUnpurchase()).subtract(orders.getApproveQty());
                    orders.setAdviceApplyQty(applyQty);
                    orders.setPurchaseApplyQty(applyQty);

                    //补充销售需求覆盖日期
                    if (ObjectUtil.isNull(purchaseOrdersResult.getSalesStockDays())) {
                        orders.setRecomBackOverDays(DateUtil.date());
                    } else {
                        orders.setRecomBackOverDays(DateUtil.offsetMonth(DateUtil.date(), purchaseOrdersResult.getSalesStockDays().intValue()));
                    }
                    //补充 申请区域备货后周转天数
                    if (orders.getTotalVolume() == null) {
                        orders.setTotalVolume(new BigDecimal(0));
                    }
//                    BigDecimal salesDemand = orders.getSalesDemand().max(orders.getTotalVolume());
                    BigDecimal salesDemand = orders.getTotalVolume().add(orders.getCanuseqty()).add(orders.getUnpurchase()).add(orders.getApproveQty()).add(orders.getPurchaseApplyQty());
                    BigDecimal dayavgqty = orders.getDayavgqty();

                    //申请备货后周转天数
                    //turnover_days_area '实时计算：（AZ海外总库存D4+国内可用库存+采购未完成数量+申请审核中数量+采购申请数量(建议采购申请数量)/日均销量D4
                    if (ObjectUtil.isNull(salesDemand) || salesDemand.compareTo(BigDecimal.ZERO) == 0) {
                        orders.setTurnoverDays(BigDecimal.ZERO);
                    } else if (ObjectUtil.isNull(dayavgqty) || dayavgqty.compareTo(BigDecimal.ZERO) == 0) {
                        orders.setTurnoverDays(new BigDecimal(99999));
                    } else {
                        orders.setTurnoverDays(salesDemand.divide(dayavgqty, 0, RoundingMode.HALF_UP));
                    }
                    //计算AZ超180天库龄数量占比
                    if (orders.getInStockQty().compareTo(BigDecimal.ZERO) != 0) {
                        orders.setOverD180InvAgeQtyRate(orders.getOverD180InvAgeQty()
                                .divide(orders.getInStockQty(), 2, BigDecimal.ROUND_HALF_UP));
                    } else {
                        orders.setOverD180InvAgeQtyRate(new BigDecimal(99999));
                    }
                    //AZ海外总库存供货天数
                    if (orders.getPreSaleQty().compareTo(BigDecimal.ZERO) != 0 && orders.getTotalBackDays().compareTo(BigDecimal.ZERO) != 0) {
                        orders.setPrepareDays(orders.getTotalVolume().divide(orders.getPreSaleQty()
                                .divide(orders.getTotalBackDays(), 8, BigDecimal.ROUND_HALF_UP), 0, BigDecimal.ROUND_DOWN));
                    } else {
                        orders.setPrepareDays(new BigDecimal(99999));
                    }
                    PurchaseOrdersLists.add(orders);
                    orders.setId(IdWorker.getIdStr());
                    orders.setOverTimeNot(1L);
                    orders.setOrderLastTime(purchaseOrdersService.lastOrderTime(orders.getPlatform(),orders.getTeam(),orders.getMaterialCode()));

                    purchaseOrdersService.save(orders);
                    String purchaseApplyNo = orders.getId();
                    String billType = orders.getBillType();
                    //修改状态为已申请  反写id
                    this.baseMapper.updatePurchaseApplyNo(platformMaterialCodeTeam, purchaseApplyNo, billType, TeamVerifLists);
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData specialVerifySubmit(PurchaseOrdersParam ordersParam) {

        ordersParam.setOrderStatus(2);//待计划部审批

        ResponseData responseData = purchaseOrdersOriService.savePurchaseOrders(ordersParam);
        if (!responseData.getSuccess()) {
            return responseData;
        }

        PurchaseOrders purchaseOrder = (PurchaseOrders) responseData.getData();
        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        VerifInfoRecord verifInfoRecord = this.creatVerifInfo(ordersParam.getPurchaseReason(), ordersParam.getPurchaseApplyQty(), ordersParam.getId(), currentUser, 10);

        if (verifInfoRecordService.save(verifInfoRecord)) {
            return ResponseData.success();
        }
        return ResponseData.error("提交失败");
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData specialVerifySave(PurchaseOrdersParam ordersParam) {

        if (ObjectUtil.isEmpty(ordersParam) || ObjectUtil.isNull(ordersParam.getId())) {
            return ResponseData.error("id必传");
        }
        PurchaseOrders purchaseOrder = purchaseOrdersOriService.getById(ordersParam.getId());

        if (ObjectUtil.isEmpty(purchaseOrder)) {
            return ResponseData.error("id:" + ordersParam.getId() + "未找到相应的数据记录");
        }

        if (ObjectUtil.isNotEmpty(ordersParam.getPurchaseApplyQty()) && ordersParam.getPurchaseApplyQty().compareTo(BigDecimal.ZERO) > 0) {
            purchaseOrder.setPurchaseApplyQty(ordersParam.getPurchaseApplyQty());
        }

        if (ObjectUtil.isNotEmpty(ordersParam.getPurchaseReason())) {
            purchaseOrder.setPurchaseReason(ordersParam.getPurchaseReason());
        }
        if (ObjectUtil.isNotEmpty(ordersParam.getOrderStatus()) && (ordersParam.getOrderStatus() == 2)) {
            purchaseOrder.setOrderStatus(ordersParam.getOrderStatus());
        }

        purchaseOrder.setUpdateTime(new Date());
        if (purchaseOrdersOriService.updateById(purchaseOrder)) {
            return ResponseData.success(purchaseOrder);
        }
        return ResponseData.error("保存失败");
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData getByPurchaseId(String purchaseId) {

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamVerif::getPurchaseApplyNo, purchaseId);
//        List<TeamVerif> teamVerifList = teamVerifService.getBaseMapper().selectList(wrapper);
//        List<TeamVerif> teamVerifList = teamVerifService.list(wrapper);
        List<TeamVerif> teamVerifList = teamVerifMapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(teamVerifList)) {
            return ResponseData.error("未找到对应采购编号的ASIN明细记录(无对应Team审核列表)");
        }
        List<BigDecimal> idList = teamVerifList.stream().map(teamVerif -> teamVerif.getId()).collect(Collectors.toList());
        LambdaQueryWrapper<SpecialApplyInfo> specialWrapper = new LambdaQueryWrapper<>();
        specialWrapper.in(SpecialApplyInfo::getTeamVerifNo, idList);

        List<SpecialApplyInfo> specialApplyInfos = mapper.selectList(specialWrapper);
        if (CollectionUtil.isNotEmpty(specialApplyInfos)) {
            return ResponseData.success(specialApplyInfos);
        }
        LambdaQueryWrapper<SpecialApplyInfoHistory> specialHistoryWrapper = new LambdaQueryWrapper<>();
        specialHistoryWrapper.in(SpecialApplyInfoHistory::getTeamVerifNo, idList);
        List<SpecialApplyInfoHistory> SpecialApplyInfoHistories = hisMapper.selectList(specialHistoryWrapper);
        return ResponseData.success(SpecialApplyInfoHistories);


    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData checkData(List<SpecialApplyInfoExcelParam> dataList) {
        LambdaQueryWrapper<SpecialApplyInfo> wrapper = new LambdaQueryWrapper<>();
        Set<BigDecimal> idSet = dataList.stream().map(SpecialApplyInfoExcelParam::getId).collect(Collectors.toSet());
        wrapper.in(SpecialApplyInfo::getId, idSet);
        List<SpecialApplyInfo> specialApplyInfoList = this.baseMapper.selectList(wrapper);
        if (ObjectUtil.isEmpty(specialApplyInfoList)) {
            dataList.clear();
            return ResponseData.error(500,"表格导入的<全部>数据对应的ID未能在系统中找到对应的数据，请检查表格是否是最新下载的,未匹配到的id为：",idSet);
        }

        Set<BigDecimal> resultIdSet = specialApplyInfoList.stream().map(SpecialApplyInfo::getId).collect(Collectors.toSet());
        if (idSet.size() == resultIdSet.size()) {
            return ResponseData.success();
        }

        idSet.removeAll(resultIdSet);

        dataList=dataList.stream().filter(d-> ! idSet.contains(d.getId())).collect(Collectors.toList());

        return ResponseData.error(500,"表格导入的<部分数据>对应的ID未能在系统中找到对应的数据，请检查表格数据对应id是否为最新的,未匹配到的id为：",idSet);
    }

    /**
     * 创建审核信息记录
     *
     * @param reason
     * @param qty
     * @param id
     * @param currentUser
     * @param verifBizType
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
