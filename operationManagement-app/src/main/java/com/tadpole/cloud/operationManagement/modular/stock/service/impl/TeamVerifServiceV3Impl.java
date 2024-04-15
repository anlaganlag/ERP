package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.authority.column.annotation.DatalimitColumn;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.entity.VerifInfoRecord;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.TeamVerifMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TeamVerifyExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResultV3;
import com.tadpole.cloud.operationManagement.modular.stock.service.*;
import com.tadpole.cloud.operationManagement.modular.stock.utils.StockMergeRules;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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
public class TeamVerifServiceV3Impl extends ServiceImpl<TeamVerifMapper, TeamVerif> implements ITeamVerifV3Service {

    public static int BATCH_SIZE = StockConstant.BATCH_SIZE;

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

    @Autowired
    IVerifInfoRecordService verifInfoRecordService;


    @DataSource(name = "stocking")
    @Override
    @DatalimitColumn(clazz = List.class,name = "数据列过滤")
    public List<TeamVerifResult> queryList(OperApplyInfoReqV3 reqVO) {
        log.info("Team审核列表查询开始");
        long start = System.currentTimeMillis();
        reqVO.setOperator(this.getUserAccount());
        List<TeamVerifResult> resultList = this.baseMapper.queryList(reqVO);
        log.info("Team审核列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return resultList;
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
    public List<TeamVerifResult> exportExcel(OperApplyInfoReqV3 param) {
        param.setOperator(this.getUserAccount());
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return this.baseMapper.queryList(param);
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData overTimeAction(StockApprovaltimeParameterResult parameter) {

        if (!operApplyInfoService.checkOverTime(parameter)) {
            return ResponseData.success("未到自动提交时间");
        }

//        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();

//        wrapper.eq(TeamVerif::getAllComit, StockConstant.ALL_COMIT_NO)//
//                .eq(TeamVerif::getBillType,"RCBH")
//                .in(TeamVerif::getVerifStatus, StockConstant.TEAM_STOCK_STATUS_WAIT, StockConstant.TEAM_STOCK_STATUS_COMIT)//
//                .isNull(TeamVerif::getNote)//非黑名单的asin
//                .select("PLATFORM || MATERIAL_CODE || TEAM ");

        QueryWrapper<TeamVerif> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("PLATFORM || MATERIAL_CODE || TEAM ");
        queryWrapper.lambda()
                .eq(TeamVerif::getAllComit, StockConstant.ALL_COMIT_NO)//
                .eq(TeamVerif::getBillType,"RCBH")
                .in(TeamVerif::getVerifStatus, StockConstant.TEAM_STOCK_STATUS_WAIT, StockConstant.TEAM_STOCK_STATUS_COMIT)//
//                .isNull(TeamVerif::getNote);//非黑名单的asin
                .and(wrapper->wrapper.isNull(TeamVerif::getNote).or().like(TeamVerif::getNote,StockConstant.BLACKLIST_OBSOLETE));


        List<Object> dataList = mapper.selectObjs(queryWrapper);


        if (ObjectUtil.isEmpty(dataList)) {
            return ResponseData.success("人工全部已经提交，无需系统自动提交");
        }

        Set<String> platformMaterialCodeTeamSet = dataList.stream().map(d -> String.valueOf(d)).collect(Collectors.toSet());


        LambdaUpdateWrapper<TeamVerif> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeamVerif::getAllComit, StockConstant.ALL_COMIT_NO)//
                .eq(TeamVerif::getBillType,"RCBH")
                .in(TeamVerif::getVerifStatus, StockConstant.TEAM_STOCK_STATUS_WAIT, StockConstant.TEAM_STOCK_STATUS_COMIT)
//                .isNull(TeamVerif::getNote)//非黑名单的asin
                .and(wrapper->wrapper.isNull(TeamVerif::getNote).or().like(TeamVerif::getNote,StockConstant.BLACKLIST_OBSOLETE))

                .set(StockConstant.OVERTIME_ACTION_NO.equals(parameter.getParameterResult()),TeamVerif::getStockQtyArea,BigDecimal.ZERO)//超时不备货  V3版本概念调整： 不备货,即把申请区域数量置为0
                .set(TeamVerif::getOverTimeNot,StockConstant.ORACLE_OVERTIME_ACTION_NO)
                .set(TeamVerif::getAllComit,StockConstant.ALL_COMIT_YES)
                .set(TeamVerif::getVerifStatus,StockConstant.TEAM_STOCK_STATUS_COMIT)
                .set(TeamVerif::getVerifDate,new Date())
                .set(TeamVerif::getUpdateTime,new Date())
                .set(TeamVerif::getVerifPersonNo,"System")
                .set(TeamVerif::getVerifPersonName,"System")
                .set(TeamVerif::getStockReason,"系统自动处理自动备货类型为[" + parameter.getParameterResult() + "],Y代表超时自动备货，N代表超时不备货");

        this.update(updateWrapper);

//        Set<String> platformMaterialCodeTeamSet = new TreeSet<>();

//        OverTimeNot  0 备货   1 不备货 || V3版本概念调整： 不备货,即把申请区域数量置为0，但是所有数据还是需要提交到Team审核，即所有数据都流转到下一节点OverTimeNot=0
  /*      dataList.forEach(d -> {

            if (StockConstant.TEAM_STOCK_STATUS_WAIT.equals(d.getVerifStatus())) {

                d.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_COMIT);
            }

            if (StockConstant.OVERTIME_ACTION_NO.equals(parameter.getParameterResult())) {//超时不备货  V3版本概念调整： 不备货,即把申请区域数量置为0
                d.setStockQtyArea(BigDecimal.ZERO);

            }
            //所有数据还是需要提交到Team审核，即所有数据都流转到下一节点OverTimeNot=0
            d.setOverTimeNot(StockConstant.ORACLE_OVERTIME_ACTION_NO);
            d.setAllComit(StockConstant.ALL_COMIT_YES);

            d.setVerifDate(new Date());
            d.setUpdateTime(new Date());
            d.setVerifPersonNo("System");
            d.setVerifPersonName("System");
            d.setStockReason("系统自动处理自动备货类型为[" + parameter.getParameterResult() + "],Y代表超时自动备货，N代表超时不备货---"+d.getStockReason());


            platformMaterialCodeTeamSet.add(d.getPlatform() + d.getMaterialCode() + d.getTeam());

        });
        this.updateBatchById(dataList);*/
//        List<BigDecimal> idList = dataList.stream().map(t -> t.getId()).collect(Collectors.toList());

        this.createPurchaseOrder(null,platformMaterialCodeTeamSet);

    /*    //提交下一节点集合
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
        }*/
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



    @Override
    @DataSource(name = "stocking")
    public ResponseData batchNoStocking(List<String> idArrayList) {


        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        if (ObjectUtil.isEmpty(idArrayList)) {
            return ResponseData.error("id不能为空");
        }

        List<BigDecimal> idList = this.getDetailIdList(idArrayList);

        Date date = new Date();

        if (ObjectUtil.isEmpty(idList)) {
            return ResponseData.error("传入ID异常"+ JSON.toJSONString(idList));
        }
        LambdaUpdateWrapper<TeamVerif> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(TeamVerif::getStockQtyArea, BigDecimal.ZERO);
        updateWrapper.set(TeamVerif::getUpdateTime, date);
        updateWrapper.set(TeamVerif::getVerifPersonName,userName);
        updateWrapper.set(TeamVerif::getVerifPersonNo,userAccount);
        updateWrapper.set(TeamVerif::getStockReason,userName+"-->在Team节点"+date+"操作[不备货]");

        List<List<BigDecimal>> splitIdList = CollectionUtil.split(idList, BATCH_SIZE);
        for (List<BigDecimal> ids : splitIdList) {
            updateWrapper.in(TeamVerif::getId, ids);
            this.update(updateWrapper);
        }
        return ResponseData.success(idList.size());
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

    /**
     * 采购订单部分属性值初始化
     *
     * @param purchaseOrders
     */
    private void initValuesV3(PurchaseOrders purchaseOrders) {

        purchaseOrders.setId(IdWorker.getIdStr());
        purchaseOrders.setOrderStatus(StockConstant.ORDER_STATUS_WAIT);

        //建议采购申请数量，采购申请数量默认值处理
//                '默认值：sum(Team审核.申请区域备货数量2)-distinct（Team审核.国内可用库存）-distinct（Team审核.采购未完成数量）-distinct（Team审核.申请审核中数量）

//        2、日常备货采购申请单中的“建议采购申请数量”信息项变更保留计算结果的负数情况，负数可间接表明富余的库存；
//        3、日常备货采购申请单中的“采购申请数量”信息项默认值如果是0，则默认该事业部的该物料不需要备货；
//        4、对默认值为0的日常备货申请记录，不可再重新考虑是否需要备货，即无论是采用何种操作（导入、人工审核、自动审核），“采购申请单状态”信息项值均是"不备货"；（本采购申请单自动完结，将不再进行计划部审批、PMC审批操作）
        BigDecimal adviceApplyQty = purchaseOrders.getAdviceApplyQty(); //sum(Team审核.申请区域备货数量2)
        BigDecimal newQty = adviceApplyQty.subtract(purchaseOrders.getCanuseqty())
                .subtract(purchaseOrders.getUnpurchase())
                .subtract(purchaseOrders.getApproveQty());
        purchaseOrders.setAdviceApplyQty(newQty);
        purchaseOrders.setPurchaseApplyQty(newQty);
        if (newQty.compareTo(BigDecimal.ZERO) <= 0) {
            purchaseOrders.setPurchaseApplyQty(BigDecimal.ZERO);
            purchaseOrders.setOrderStatus(StockConstant.ORDER_STATUS_N0_STOCK);
        }


//        BigDecimal salesDemand = purchaseOrders.getSalesDemand().max(purchaseOrders.getTotalVolume());

//        BigDecimal salesDemand = purchaseOrders.getTotalVolume().add(purchaseOrders.getCanuseqty()).add(purchaseOrders.getUnpurchase()).add(purchaseOrders.getApproveQty()).add(purchaseOrders.getPurchaseApplyQty());


        BigDecimal dayavgqty = purchaseOrders.getDayavgqty();

        // 申请备货后周转天数: '实时计算：（AZ海外总库存D4+国内可用库存+采购未完成数量+申请审核中数量+采购申请数量)/日均销量D4     8-10修改
        if (purchaseOrders.getPurchaseApplyQty().compareTo(BigDecimal.ZERO)==0  || ObjectUtil.isNull(dayavgqty) || dayavgqty.compareTo(BigDecimal.ZERO) == 0) {
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

    @DataSource(name = "stocking")
    @Override
    public List<BigDecimal> getDetailIdList(List<String> idArrayList) {
        List<BigDecimal> idList = new ArrayList<>();

        for (String ids : idArrayList) {
            if (StringUtils.isNotEmpty(ids)) {
                String[] idArray = ids.split(",");
                for (String idStr : idArray) {
                    idList.add(BigDecimal.valueOf(Long.valueOf(idStr)));
                }
            }
        }
        return idList;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData mergeCommitBatchFast(List<String> idArrayList, List<BigDecimal> idList) {


        if (ObjectUtil.isEmpty(idList)) {
            idList = this.getDetailIdList(idArrayList);
        }

        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();
        List<TeamVerif> teamVerifList = new ArrayList<>();
        List<List<BigDecimal>> splitIds = CollectionUtil.split(idList, BATCH_SIZE);
        for (List<BigDecimal> ids : splitIds) {
            teamVerifList.addAll(this.baseMapper.selectBatchIds(ids));
        }


        //检查是否有重复提交的数据
        List<TeamVerif> alreadySubmitList = teamVerifList.stream()
                .filter(op -> ! StockConstant.TEAM_STOCK_STATUS_WAIT .equals(op.getVerifStatus()) ).collect(Collectors.toList());

        if (ObjectUtil.isNotEmpty(alreadySubmitList)) {
            Set<String> alreadySubmitData = alreadySubmitList.stream()
                    .map(as -> as.getPlatform() + "|" + as.getArea() + "|" + as.getDepartment() + "|"
                            + as.getTeam() + "|" + as.getMaterialCode() + "|数据id->" + as.getId())
                    .collect(Collectors.toSet());
           return ResponseData.error(500, "以下数据已经提交过，不能重复提交[]", alreadySubmitData);
        }

        //平台+物料编码+team集合
        Set<String> platformMaterialCodeTeamSet = teamVerifList.stream()
                .map(t->t.getPlatform() + t.getMaterialCode() + t.getTeam()).collect(Collectors.toSet());

        //新增运营申请维度数据是否全部提交校验,过滤掉还未全部提交的数据
        ResponseData checkResult = stockMergeRules.teamCreateOrderCheck(platformMaterialCodeTeamSet);

        if (ObjectUtil.isEmpty(platformMaterialCodeTeamSet)) {
            return checkResult;
        }


        List<TeamVerif> filterResult = new ArrayList<>();

        Map<String, List<TeamVerif>> initData = teamVerifList.stream().collect(Collectors.groupingBy(p -> p.getPlatform() + p.getMaterialCode() + p.getTeam()));
        for (Map.Entry<String, List<TeamVerif>> entry : initData.entrySet()) {
            String key = entry.getKey();
            if (platformMaterialCodeTeamSet.contains(key)) {
                filterResult.addAll(entry.getValue());
            }

        }


        //提交--->过滤出还未全部提交的，不做更新操作
//        List<TeamVerif> filterResult = teamVerifList.stream()
//                .filter(p -> platformMaterialCodeTeamSet.contains(p.getPlatform() + p.getMaterialCode() + p.getTeam())).collect(Collectors.toList());

        List<BigDecimal> filterIdList = filterResult.stream().map(t -> t.getId()).collect(Collectors.toList());

        Date date = new Date();
        List<List<BigDecimal>> splitData = CollectionUtil.split(filterIdList, BATCH_SIZE);

        for (List<BigDecimal> ids : splitData) {

            LambdaUpdateWrapper<TeamVerif> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .set(TeamVerif::getVerifPersonNo,userAccount)
                    .set(TeamVerif::getVerifPersonName,userName)
                    .set(TeamVerif::getVerifStatus,StockConstant.TEAM_STOCK_STATUS_COMIT)
                    .set(TeamVerif::getVerifDate,date)
                    .set(TeamVerif::getUpdateTime,date)
                    .eq(TeamVerif::getVerifStatus,StockConstant.TEAM_STOCK_STATUS_WAIT);
            updateWrapper.in(TeamVerif::getId, ids);
            this.update(updateWrapper);
            updateWrapper.clear();
        }

  /*      for (TeamVerif t : teamVerifList) {
            t.setVerifPersonNo(userAccount);
            t.setVerifPersonName(userName);
            t.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_COMIT);
            t.setVerifDate(date);
            t.setUpdateTime(date);
            platformMaterialCodeTeamSet.add(t.getPlatform() + t.getMaterialCode() + t.getTeam());
        }
            this.updateBatchById(filterResult);

*/
        this.createPurchaseOrder(null,platformMaterialCodeTeamSet);

        return checkResult;
    }


    @DataSource(name = "stocking")
    @NotNull
    private void createPurchaseOrder( List<TeamVerif> teamVerifList,Set<String> platformMaterialCodeTeamSetParm) {

        Set<String> platformMaterialCodeTeamSet = new TreeSet<>();

        if (ObjectUtil.isEmpty(platformMaterialCodeTeamSetParm)) {
            for (TeamVerif t : teamVerifList) {
                platformMaterialCodeTeamSet.add(t.getPlatform() + t.getMaterialCode() + t.getTeam());
            }
        } else {
            platformMaterialCodeTeamSet = platformMaterialCodeTeamSetParm;
        }


        //更新全部都提交了

        Date date = new Date();


        List<List<String>> splitSet = CollectionUtil.split(platformMaterialCodeTeamSet, BATCH_SIZE);
        for (List<String> platformMatTeamList : splitSet) {
            this.baseMapper.updateStockAreaListV3(platformMatTeamList);
        }


        List<TeamVerif> watiCreateOrderTeamVerif = new ArrayList<>();
        List<TeamVerif> watiUpdateTeamVerif = new ArrayList<>();


        for (List<String> platformMatTeamList : splitSet) {
            watiCreateOrderTeamVerif.addAll(this.baseMapper.selectCommitBatch(platformMatTeamList)) ;
            watiUpdateTeamVerif.addAll(this.baseMapper.selectMergeDetail(platformMatTeamList)) ;
        }


        List<PurchaseOrders> purchaseOrderList = new ArrayList<>();
        List<VerifInfoRecord> verifInfoRecordList = new ArrayList<>();

        for (TeamVerif teamVerif : watiCreateOrderTeamVerif) {

            if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                teamVerif.setVersionDes("");
            }
            PurchaseOrders purchaseOrder = BeanUtil.copyProperties(teamVerif, PurchaseOrders.class);

            this.initValuesV3(purchaseOrder);
            purchaseOrder.setCreateTime(date);
            purchaseOrderList.add(purchaseOrder);
            if (StockConstant.ORDER_STATUS_N0_STOCK==purchaseOrder.getOrderStatus()) {
                VerifInfoRecord verifInfoRecord = this.autoCreatVerifInfo(purchaseOrder);
                verifInfoRecordList.add(verifInfoRecord);
            }
        }


        //保存订单
        iPurchaseOrdersService.saveBatch(purchaseOrderList);//todo:批量插入
        //保存申请数量为0的系统自动审核记录
        if (ObjectUtil.isNotEmpty(verifInfoRecordList)) {
            verifInfoRecordService.saveBatch(verifInfoRecordList);
        }


        Map<String, String> updateDataMap = new HashMap<>();
        for (PurchaseOrders order : purchaseOrderList) {
            updateDataMap.put(order.getPlatform() + order.getMaterialCode() + order.getTeam(), order.getId());
        }

        Map<String, List<TeamVerif>> waitUpdateTeamVerifGroup = watiUpdateTeamVerif.stream()
                .collect(Collectors.groupingBy(t -> t.getPlatform() + t.getMaterialCode() + t.getTeam()));

        //回写teamVerif信息  //todo:批量更新
        List<TeamVerif> allUpTeamVerif = new ArrayList<>();
        for (Map.Entry<String, List<TeamVerif>> entry : waitUpdateTeamVerifGroup.entrySet()) {
            String platformMaterialCodeTeam = entry.getKey();
            List<TeamVerif> teamVerifs = entry.getValue();
            String orderId = updateDataMap.get(platformMaterialCodeTeam);
            for (TeamVerif teamVerif : teamVerifs) {
                teamVerif.setPurchaseApplyNo(orderId);
                teamVerif.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_APPLY);
                teamVerif.setUpdateTime(date);
                teamVerif.setVerifDate(date);
            }
            allUpTeamVerif.addAll(teamVerifs);
        }

        List<List<TeamVerif>> splitData = CollectionUtil.split(allUpTeamVerif, BATCH_SIZE);
        for (List<TeamVerif> verifList : splitData) {
            mapper.updateBatch(verifList);
        }


//        this.updateBatchById(allUpTeamVerif);

        //更新最近一次下单时间
        iPurchaseOrdersService.flashLastOrderTime();
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData mergeDetail(List<BigDecimal> idList) {
        LambdaQueryWrapper<TeamVerif> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TeamVerif::getId, idList);
        List<TeamVerif> teamVerifList = this.baseMapper.selectList(queryWrapper);
        return ResponseData.success(teamVerifList);
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData updateDetailBatch(List<TeamVerif> teamVerifList) {

        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        List<BigDecimal> idList = teamVerifList.stream().map(a -> a.getId()).collect(Collectors.toList());
        LambdaQueryWrapper<TeamVerif> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TeamVerif::getId, idList);
        List<TeamVerif> needUpdateTeamVerif = this.baseMapper.selectList(queryWrapper);

        Map<BigDecimal, List<TeamVerif>> listMapRq = teamVerifList.stream().collect(Collectors.groupingBy(TeamVerif::getId));


        for (TeamVerif tf : needUpdateTeamVerif) {

            TeamVerif rq = listMapRq.get(tf.getId()).get(0);

            tf.setOperCurMonthQty(rq.getOperCurMonthQty());
            tf.setOperCurMonthAdd1Qty(rq.getOperCurMonthAdd1Qty());
            tf.setOperCurMonthAdd2Qty(rq.getOperCurMonthAdd2Qty());
            tf.setOperCurMonthAdd3Qty(rq.getOperCurMonthAdd3Qty());
            tf.setOperCurMonthAdd4Qty(rq.getOperCurMonthAdd4Qty());
            tf.setOperCurMonthAdd5Qty(rq.getOperCurMonthAdd5Qty());
            tf.setOperCurMonthAdd6Qty(rq.getOperCurMonthAdd6Qty());


            //销售需求备货天数1
            tf.setSalesStockDays(rq.getSalesStockDays());
            //销售需求覆盖日期1     expectedDeliveryDate
            tf.setOperCoversSalesDate(rq.getOperCoversSalesDate());
            //额外备货天数
            tf.setExtraDays(rq.getExtraDays());
            //销售需求1
            tf.setSalesDemand(rq.getSalesDemand());
            //申请区域备货数量1
            tf.setStockQtyArea(rq.getStockQtyArea());
            //申请区域备货后周转天数1
            tf.setTurnoverDaysArea(rq.getTurnoverDaysArea());
            //运营期望交期1
            tf.setOperExpectedDate(rq.getOperExpectedDate());

            tf.setStockReason(rq.getStockReason());
            tf.setVerifPersonName(userName);
            tf.setVerifPersonNo(userAccount);
            tf.setVerifDate(new Date());
            tf.setUpdateTime(new Date());
        }

         this.updateBatchById(needUpdateTeamVerif);
        return ResponseData.success();

    }


    /**
     * 自动审核的时候创建审核信息记录
     *
     * @param order
     * @return
     */
    private VerifInfoRecord autoCreatVerifInfo(PurchaseOrders order) {
        Date date = new Date();
        VerifInfoRecord verifInfoRecord = VerifInfoRecord.builder()
                .id(IdWorker.getIdStr())
                .qty(BigDecimal.ZERO)
                .purchaseOrderId(order.getId())
                .verifPersonName("System")
                .verifPersonNo("Syste")
                .verifReason("所有Team提交记录汇总数据申请数量小于等于0,则表示不需要备货")
                .verifDate(date)
                .createTime(date)
                .verifBizType(10)//事业部审核10，计划部审核20，PMC审核30  */
                .verifType("0")//值域{"0:自动"/"1:人工"}
                .verifResult("0")
                .build();
        return verifInfoRecord;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData commitDetailBatch(List<TeamVerif> teamVerifList) {
        this.updateDetailBatch(teamVerifList);
        List<BigDecimal> idList = teamVerifList.stream().map(a -> a.getId()).collect(Collectors.toList());
        return this.mergeCommitBatchFast(null, idList);
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData applyQtyCountMat(String department, String materialCode, Date bizDate) {

        LambdaQueryWrapper<TeamVerif> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamVerif::getMaterialCode, materialCode);
        queryWrapper.eq(TeamVerif::getDepartment, department);
        queryWrapper.eq(TeamVerif::getBizdate, bizDate);
        queryWrapper.eq(TeamVerif::getBillType, "RCBH");
        List<TeamVerifResultV3> applyQtyCountMatList = this.mapper.applyQtyCountMat(department,materialCode,bizDate);
        return ResponseData.success(applyQtyCountMatList);

    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData queryAsinListByTeamNo(List<String> teamNoList) {

        LambdaQueryWrapper<OperApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OperApplyInfo::getTeamVerifNo, teamNoList);
        queryWrapper.eq(OperApplyInfo::getStockStatus, StockConstant.OPER_STOCK_STATUS_APPLY);
        queryWrapper.orderByDesc(OperApplyInfo::getTeamVerifNo);
        List<OperApplyInfo> applyInfoList = operApplyInfoService.getBaseMapper().selectList(queryWrapper);

        return ResponseData.success(applyInfoList);
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
