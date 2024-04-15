package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.TeamVerifMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TeamVerifyExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPmcVerifInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifService;
import com.tadpole.cloud.operationManagement.modular.stock.utils.StockMergeRules;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * Team审核 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
@Service
@Slf4j
public class TeamVerifServiceImpl extends ServiceImpl<TeamVerifMapper, TeamVerif> implements ITeamVerifService {

    @Resource
    private TeamVerifMapper mapper;


    @Autowired
    private IPurchaseOrdersService iPurchaseOrdersService;

    @Autowired
    private IOperApplyInfoService operApplyInfoService;

    @Autowired
    StockMergeRules stockMergeRules;

    @Autowired
    IPmcVerifInfoService pmcVerifInfoService;


    @DataSource(name = "stocking")
    @Override
    public PageResult<TeamVerifResult> queryPage(OperApplyInfoReqVO reqVO) {
        log.info("Team审核列表查询开始");
        long start = System.currentTimeMillis();
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();
        eform.setOperator(this.getUserAccount());
        reqVO.setEform(eform);

        Page pageContext = reqVO.getPageContext();
        IPage<TeamVerifResult> page = this.baseMapper.queryPage(pageContext, reqVO);
        log.info("Team审核列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData commitBatch(List<OperApplyReqVO> reqParamList) {

        log.info("team审核提交处理开始");
        long start = System.currentTimeMillis();
        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();


        // 1. 数据状态变更，提交人，提交时间补充
        /**
         * 1、批量保存备货申请信息
         */
        //物料编码集合
        Set<String> platformMaterialCodeTeamSet = new HashSet<>();

        reqParamList.stream().forEach(i -> {
            i.setRequireBy(userAccount);
            i.setRequireByName(userName);
            platformMaterialCodeTeamSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam());

        });


        //新增运营申请维度数据是否全部提交校验,过滤掉还未全部提交的数据
        ResponseData checkResult = stockMergeRules.teamCreateOrderCheck(platformMaterialCodeTeamSet);

        if (ObjectUtil.isEmpty(platformMaterialCodeTeamSet)) {
            return checkResult;
        }


        //提交--->过滤出还未全部提交的，不做更新操作
        List<OperApplyReqVO> filterResult = reqParamList.stream()
                .filter(p -> platformMaterialCodeTeamSet.contains(p.getPlatform() + p.getMaterialCode() + p.getTeam())).collect(Collectors.toList());

        this.baseMapper.commitBatch(filterResult);

        // 2. 变更物料、team、区域是否都已经提交
        if (CollectionUtil.isNotEmpty(platformMaterialCodeTeamSet)) {
            this.baseMapper.updateStockAreaList(platformMaterialCodeTeamSet);
            Date date = new Date();

            // 3. 物料、team、区域、下Asin都已提交的插入team审核表
            for (String platformMaterialCodeTeam : platformMaterialCodeTeamSet) {

                //取出物料、team、区域明细汇总  HHT210006Team15
                TeamVerif teamVerif = this.baseMapper.selectCommit(platformMaterialCodeTeam);
                if (ObjectUtil.isNull(teamVerif)) {
                    continue;
                }
                if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                    teamVerif.setVersionDes("");
                }

                PurchaseOrders purchaseOrders = BeanUtil.copyProperties(teamVerif, PurchaseOrders.class);

                //建议采购申请数量，采购申请数量默认值处理
//                '默认值：sum(Team审核.申请区域备货数量2)-distinct（Team审核.国内可用库存）-distinct（Team审核.采购未完成数量）-distinct（Team审核.申请审核中数量）
                this.initValues(purchaseOrders);
                purchaseOrders.setCreateTime(date);
                purchaseOrders.setOrderLastTime(iPurchaseOrdersService.lastOrderTime(purchaseOrders.getPlatform(),purchaseOrders.getTeam(),purchaseOrders.getMaterialCode()));

                iPurchaseOrdersService.save(purchaseOrders);

                //修改状态为已申请  反写id
                this.baseMapper.updateCommitStatus(platformMaterialCodeTeam, purchaseOrders.getId());
            }
        }
        return checkResult;

    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData getByPurchaseNo(String purchaseNo) {

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamVerif::getPurchaseApplyNo, purchaseNo);
        List<TeamVerif> teamVerifList = this.baseMapper.selectList(wrapper);
        if (ObjectUtil.isNotEmpty(teamVerifList)) {
            return ResponseData.success(teamVerifList);
        }
        return ResponseData.error("未找到对应采购编号的team审核记录");
    }


    @Override
    @DataSource(name = "stocking")
    public List<TeamVerifResult> exportExcel(OperApplyInfoReqVO param) {
        OperApplyInfoReqVO.Eform eform = param.getEform();
        eform.setOperator(this.getUserAccount());
        param.setEform(eform);
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<TeamVerifResult> page = this.baseMapper.queryPage(pageContext, param);
        return page.getRecords();

    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData overTimeAction(StockApprovaltimeParameterResult parameter) {

        if (!operApplyInfoService.checkOverTime(parameter)) {
            return ResponseData.success("未到自动提交时间");
        }

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(TeamVerif::getAllComit, StockConstant.ALL_COMIT_NO)//
                .eq(TeamVerif::getBillType,"RCBH")
                .in(TeamVerif::getVerifStatus, StockConstant.TEAM_STOCK_STATUS_WAIT, StockConstant.TEAM_STOCK_STATUS_COMIT)//
                .isNull(TeamVerif::getNote);//非黑名单的asin

        List<TeamVerif> dataList = mapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(dataList)) {
            return ResponseData.success("人工全部已经提交，无需系统自动提交");
        }


//        OverTimeNot  0 备货   1 不备货
        dataList.forEach(d -> {
            if (StockConstant.OVERTIME_ACTION_YES.equals(parameter.getParameterResult())) {//超时备货
                if (StockConstant.TEAM_STOCK_STATUS_WAIT.equals(d.getVerifStatus())) {
                    d.setOverTimeNot(StockConstant.ORACLE_OVERTIME_ACTION_NO);
                    d.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_COMIT);
                }
                d.setAllComit(StockConstant.ALL_COMIT_YES);

            } else {//超时不备货
                if (StockConstant.TEAM_STOCK_STATUS_WAIT.equals(d.getVerifStatus())) {
                    d.setOverTimeNot(BigDecimal.ONE);
                    d.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_NO);
                } else {
                    d.setAllComit(StockConstant.ALL_COMIT_YES);
                }
            }
            d.setVerifDate(new Date());
            d.setVerifPersonNo("System");
            d.setVerifPersonName("System");
            d.setStockReason("系统自动处理自动备货类型为[" + parameter.getParameterResult() + "],Y代表超时自动备货，N代表超时不备货");
        });

        this.updateBatchById(dataList);

        //提交下一节点集合
        TreeSet<String> platformMaterialCodeTeamSet = new TreeSet<>();

        dataList.stream().forEach(i -> {
            if (StockConstant.ALL_COMIT_YES.equals(i.getAllComit())) {
                platformMaterialCodeTeamSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam() );
            }
        });
        if (CollectionUtil.isEmpty(platformMaterialCodeTeamSet)) {
            return ResponseData.success();
        }
        // 2. 变更物料、team、区域是否都已经提交

        // 3. 物料、team、区域、下Asin都已提交的插入team审核表
        Date date = new Date();
        for (String platformMaterialCodeTeam : platformMaterialCodeTeamSet) {
            //取出物料、team、区域明细汇总
            TeamVerif teamVerif = this.baseMapper.selectCommit(platformMaterialCodeTeam);
            if (ObjectUtil.isNull(teamVerif)) {
                continue;
            }
            if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                teamVerif.setVersionDes("");
            }

            PurchaseOrders purchaseOrders = BeanUtil.copyProperties(teamVerif, PurchaseOrders.class);

            this.initValues(purchaseOrders);

            purchaseOrders.setOrderStatus(StockConstant.ORDER_STATUS_WAIT);
            purchaseOrders.setId(IdWorker.getIdStr());
            purchaseOrders.setCreateTime(date);
            purchaseOrders.setOrderLastTime(iPurchaseOrdersService.lastOrderTime(purchaseOrders.getPlatform(),purchaseOrders.getTeam(),purchaseOrders.getMaterialCode()));
            iPurchaseOrdersService.save(purchaseOrders);

            //修改状态为已申请  反写id
            this.baseMapper.updateCommitStatus(platformMaterialCodeTeam, purchaseOrders.getId());
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public List<TeamVerifyExcelExportParam> teamVerifyExport(String teamStockStatusWait) {
       return this.baseMapper.teamExport(StockConstant.VERIFY_WAIT, this.getUserAccount());
//       return this.baseMapper.teamExport(StockConstant.VERIFY_WAIT, "S20160074");
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTeamverif(TeamVerif teamVerif) {
          return   this.getBaseMapper().insert(teamVerif)>0;
    }


    /**
     * 采购订单部分属性值初始化
     *
     * @param purchaseOrders
     */
    private void initValues(PurchaseOrders purchaseOrders) {
        //建议采购申请数量，采购申请数量默认值处理
//                '默认值：sum(Team审核.申请区域备货数量2)-distinct（Team审核.国内可用库存）-distinct（Team审核.采购未完成数量）-distinct（Team审核.申请审核中数量）
        BigDecimal adviceApplyQty = purchaseOrders.getAdviceApplyQty(); //sum(Team审核.申请区域备货数量2)
        BigDecimal newQty = adviceApplyQty.subtract(purchaseOrders.getCanuseqty())
                .subtract(purchaseOrders.getUnpurchase())
                .subtract(purchaseOrders.getApproveQty());

        if (newQty.compareTo(BigDecimal.ZERO) <= 0) {
            newQty = BigDecimal.ZERO;
        }
        purchaseOrders.setAdviceApplyQty(newQty);
        purchaseOrders.setPurchaseApplyQty(newQty);


        purchaseOrders.setOrderStatus(StockConstant.ORDER_STATUS_WAIT);
        purchaseOrders.setId(IdWorker.getIdStr());

//        BigDecimal salesDemand = purchaseOrders.getSalesDemand().max(purchaseOrders.getTotalVolume());

        BigDecimal salesDemand = purchaseOrders.getTotalVolume().add(purchaseOrders.getCanuseqty()).add(purchaseOrders.getUnpurchase()).add(purchaseOrders.getApproveQty()).add(purchaseOrders.getPurchaseApplyQty());


        BigDecimal dayavgqty = purchaseOrders.getDayavgqty();

        // 申请备货后周转天数: '实时计算：（AZ海外总库存D4+国内可用库存+采购未完成数量+申请审核中数量+采购申请数量)/日均销量D4     8-10修改
        if (ObjectUtil.isNull(dayavgqty) || dayavgqty.compareTo(BigDecimal.ZERO) == 0) {
            purchaseOrders.setTurnoverDays(new BigDecimal(99999));
        } else {
                purchaseOrders.setTurnoverDays(
                        BigDecimal.ZERO
                        .add(ObjectUtil.isNull(purchaseOrders.getTotalVolume()) ? BigDecimal.ZERO : purchaseOrders.getTotalVolume())
                        .add(ObjectUtil.isNull(purchaseOrders.getCanuseqty()) ? BigDecimal.ZERO : purchaseOrders.getCanuseqty())
                        .add(ObjectUtil.isNull(purchaseOrders.getUnpurchase()) ? BigDecimal.ZERO : purchaseOrders.getUnpurchase())
                        .add(ObjectUtil.isNull(purchaseOrders.getApproveQty()) ? BigDecimal.ZERO : purchaseOrders.getApproveQty())
                        .add(ObjectUtil.isNull(purchaseOrders.getPurchaseApplyQty()) ? BigDecimal.ZERO : purchaseOrders.getPurchaseApplyQty())
                        .divide(dayavgqty, 0, RoundingMode.DOWN)
                );
        }

        //计算AZ超180天库龄数量占比
        if (purchaseOrders.getInStockQty().compareTo(BigDecimal.ZERO) != 0) {
            purchaseOrders.setOverD180InvAgeQtyRate(purchaseOrders.getOverD180InvAgeQty()
                    .divide(purchaseOrders.getInStockQty(), 8, BigDecimal.ROUND_HALF_UP));
        } else {
            purchaseOrders.setOverD180InvAgeQtyRate(new BigDecimal(99999));
        }
        //AZ海外总库存供货天数
        if (purchaseOrders.getPreSaleQty().compareTo(BigDecimal.ZERO) != 0 && purchaseOrders.getTotalBackDays().compareTo(BigDecimal.ZERO) != 0) {
            purchaseOrders.setPrepareDays(purchaseOrders.getTotalVolume().divide(purchaseOrders.getPreSaleQty().divide(purchaseOrders.getTotalBackDays(), 8, BigDecimal.ROUND_DOWN), 0, BigDecimal.ROUND_DOWN));
        } else {
            purchaseOrders.setPrepareDays(new BigDecimal(99999));
        }
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
