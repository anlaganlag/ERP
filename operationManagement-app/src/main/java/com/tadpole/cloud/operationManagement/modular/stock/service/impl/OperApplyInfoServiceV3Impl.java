package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.authority.column.annotation.DatalimitColumn;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.OperApplyInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResultV3;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoServiceV3;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifService;
import com.tadpole.cloud.operationManagement.modular.stock.utils.StockCalcRules;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 备货申请信息 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
@Service
@Slf4j
public class OperApplyInfoServiceV3Impl extends ServiceImpl<OperApplyInfoMapper, OperApplyInfo> implements IOperApplyInfoServiceV3 {

    public static int BATCH_SIZE = StockConstant.BATCH_SIZE;

    @Resource
    private OperApplyInfoMapper mapper;

    @Autowired
    private ITeamVerifService teamVerifService;

    @DataSource(name = "stocking")
    @Override
    public PageResult<OperApplyInfoResult> queryPage(OperApplyInfoReqVO reqVO) {

        log.info("运营申请列表查询开始");
        long start = System.currentTimeMillis();
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();
        eform.setOperator(this.getUserAccount());
        reqVO.setEform(eform);

        //如果传了采购编号
        if (eform.getPurchaseApplyNo() != null && !eform.getPurchaseApplyNo().equals("")) {
            QueryWrapper<TeamVerif> queryWrapper = new QueryWrapper();
            queryWrapper.select("ID").eq("PURCHASE_APPLY_NO", eform.getPurchaseApplyNo());
            List<TeamVerif> teams = teamVerifService.getBaseMapper().selectList(queryWrapper);
            if (teams.size() > 0) {
                eform.setTeamVerifNoLists(teams.stream().map(t -> t.getId()).collect(Collectors.toList()));
            }
        }

        Page pageContext = reqVO.getPageContext();
        IPage<OperApplyInfoResult> page = this.baseMapper.queryPage(pageContext, reqVO);
        log.info("运营申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData update(OperApplyInfo applyInfo) {
        applyInfo.setUpdateTime(new Date());

        this.baseMapper.updateById(applyInfo);

        return ResponseData.success();
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData commitBatch(List<OperApplyReqVO> reqParamList) {

        log.info("日常备货运营申请保存提交处理开始");
        long start = System.currentTimeMillis();
        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();


        // 1. 数据状态变更，提交人，提交时间补充
        /**
         * 1、批量保存备货申请信息
         */
        //物料编码集合
        Set<String> platformMaterialCodeTeamAreaSet = new HashSet<>();
        //Team集合
        Set<String> teamSet = new HashSet<>();
        //Area集合
        Set<String> areaSet = new HashSet<>();

        reqParamList.stream().forEach(i -> {
            i.setRequireBy(userAccount);
            i.setRequireByName(userName);
            platformMaterialCodeTeamAreaSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam() + i.getArea());

        });
        //提交
        this.baseMapper.commitBatch(reqParamList);

        //提交下一节点集合
        List<TeamVerif> TeamVerifLists = new ArrayList<>();

        // 2. 变更物料、team、区域、下Asin是否都已经提交
        if (CollectionUtil.isNotEmpty(platformMaterialCodeTeamAreaSet)) {
            this.baseMapper.updateStockAreaList(platformMaterialCodeTeamAreaSet);

            // 3. 物料、team、区域、下Asin都已提交的插入team审核表
            for (String platformMaterialCodeTeamArea : platformMaterialCodeTeamAreaSet) {
                //取出物料、team、区域明细汇总
                OperApplyInfo commitApply = this.baseMapper.selectCommit(platformMaterialCodeTeamArea);
                if (ObjectUtil.isNull(commitApply)) {
                    continue;
                }

                TeamVerif teamVerif = BeanUtil.copyProperties(commitApply, TeamVerif.class);

//                this.initValue(teamVerif);
                StockCalcRules.teamInitValue(teamVerif);

                teamVerif.setVerifStatus(StockConstant.VERIFY_WAIT);
                teamVerif.setCreateTime(new Date());
                if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                    teamVerif.setVersionDes("");
                }
                TeamVerifLists.add(teamVerif);
                teamVerifService.save(teamVerif);

                //修改状态为已申请  反写id
                this.baseMapper.updateCommitStatus(platformMaterialCodeTeamArea, teamVerif.getId().toString());
            }
        }
        return ResponseData.success();
    }

    /**
     * team审核部分属性值初始化
     *
     * @param teamVerif
     */
    private void initValue(TeamVerif teamVerif) {
        //计算AZ超180天库龄数量占比
        if (teamVerif.getInStockQty().compareTo(BigDecimal.ZERO) != 0) {
            teamVerif.setOverD180InvAgeQtyRate(teamVerif.getOverD180InvAgeQty()
                    .divide(teamVerif.getInStockQty(), 4, BigDecimal.ROUND_HALF_UP));
        } else {
            teamVerif.setOverD180InvAgeQtyRate(new BigDecimal(99999));
        }
        //AZ海外总库存供货天数
        if (teamVerif.getPreSaleQty().compareTo(BigDecimal.ZERO) != 0 && teamVerif.getTotalBackDays().compareTo(BigDecimal.ZERO) != 0) {
            teamVerif.setPrepareDays(teamVerif.getTotalVolume().divide(teamVerif.getPreSaleQty().divide(teamVerif.getTotalBackDays(), 8, BigDecimal.ROUND_DOWN), 0, BigDecimal.ROUND_DOWN));
        } else {
            teamVerif.setPrepareDays(new BigDecimal(99999));
        }
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData operImport(List<OperApplyInfoExcelParam> dataList) {

        for (OperApplyInfoExcelParam excelParam : dataList) {

        }

        return null;
    }


    @DataSource(name = "stocking")
    @Override
    public List<OperApplyInfo> operExport(Integer stockStatu) {
        log.info("运营申请列表查询开始");
        long start = System.currentTimeMillis();
        String operator = this.getUserAccount();
//        String operator = "S20200056";
        List<OperApplyInfo> operExportList = this.baseMapper.operExport(operator, stockStatu);
        log.info("运营申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return operExportList;
    }

    @DataSource(name = "stocking")
    @Override
    public PageResult<OperApplyInfoResult> recordList(OperApplyInfoReqVO reqVO) {
        log.info("运营申请列表查询开始");
        long start = System.currentTimeMillis();
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();

        reqVO.setEform(eform);

        //如果传了采购编号
        if (eform.getPurchaseApplyNo() != null && !eform.getPurchaseApplyNo().equals("")) {
            QueryWrapper<TeamVerif> queryWrapper = new QueryWrapper();
            queryWrapper.select("ID").eq("PURCHASE_APPLY_NO", eform.getPurchaseApplyNo());
            List<TeamVerif> teams = teamVerifService.getBaseMapper().selectList(queryWrapper);
            if (teams.size() > 0) {
                eform.setTeamVerifNoLists(teams.stream().map(t -> t.getId()).collect(Collectors.toList()));
            }
        }

        Page pageContext = reqVO.getPageContext();

        pageContext.setSize(Integer.MAX_VALUE);
        IPage<OperApplyInfoResult> page = this.baseMapper.recordList(pageContext, reqVO);
        log.info("运营申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override
    public List<OperApplyInfoExcelParam> operExportFast(int stockStatu, boolean note) {
        log.info("运营导出开始");
        long start = System.currentTimeMillis();
        String operator = this.getUserAccount();
//        String operator = "S20160074";
//        String operator = "S20170132";
        List<OperApplyInfoExcelParam> operExportList = this.baseMapper.operExportFast(operator, stockStatu);
        log.info("运营导出开始列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return operExportList;
    }

    @DataSource(name = "stocking")
    @Override
    public List<OperApplyInfoExcelExportParam> operExportFast2(int stockStatu, boolean note) {
        log.info("运营导出开始");
        long start = System.currentTimeMillis();
        String operator = this.getUserAccount();
        List<OperApplyInfoExcelExportParam> operExportList = this.baseMapper.operExportFast2(operator, stockStatu);
        log.info("运营导出开始列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return operExportList;
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData overTimeAction(StockApprovaltimeParameterResult parameter) {

        if (!this.checkOverTime(parameter)) {
            return ResponseData.success("未到自动提交时间");
        }


        QueryWrapper<OperApplyInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("PLATFORM || MATERIAL_CODE || TEAM || AREA");
        queryWrapper.lambda()
                .eq(OperApplyInfo::getAllComit, StockConstant.ALL_COMIT_NO.intValue())//还未合并生成5维度的数据
                .in(OperApplyInfo::getStockStatus, StockConstant.OPER_STOCK_STATUS_WAIT, StockConstant.OPER_STOCK_STATUS_COMIT)//
                .and(wrapper->wrapper.isNull(OperApplyInfo::getNote).or().like(OperApplyInfo::getNote,StockConstant.BLACKLIST_OBSOLETE));
//                .isNull(OperApplyInfo::getNote).or(OperApplyInfo);//非黑名单的asin

        List<Object> dataList = mapper.selectObjs(queryWrapper);

        if (ObjectUtil.isEmpty(dataList)) {
            return ResponseData.success("人工全部已经提交，无需系统自动提交");
        }


        //更新数据
        LambdaUpdateWrapper  <OperApplyInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OperApplyInfo::getAllComit, StockConstant.ALL_COMIT_NO.intValue())//还未合并生成5维度的数据
                    .in(OperApplyInfo::getStockStatus, StockConstant.OPER_STOCK_STATUS_WAIT, StockConstant.OPER_STOCK_STATUS_COMIT)//
//                    .isNull(OperApplyInfo::getNote)//非黑名单的asin
                    .and(wrapper->wrapper.isNull(OperApplyInfo::getNote).or().like(OperApplyInfo::getNote,StockConstant.BLACKLIST_OBSOLETE))

                    .set(StockConstant.OVERTIME_ACTION_NO.equals(parameter.getParameterResult()),OperApplyInfo::getStockQtyArea,BigDecimal.ZERO)//超时备货
                    .set(OperApplyInfo::getOverTimeNot,BigDecimal.ZERO)
                    .set(OperApplyInfo::getAllComit,BigDecimal.ONE)
                    .set(OperApplyInfo::getStockStatus,StockConstant.OPER_STOCK_STATUS_COMIT)
                    .set(OperApplyInfo::getApplyDate,new Date())
                    .set(OperApplyInfo::getUpdateTime,new Date())
                    .set(OperApplyInfo::getApplyPersonName,"System")
                    .set(OperApplyInfo::getApplyPersonNo,"System")
                    .set(OperApplyInfo::getStockReason,"系统自动处理自动备货类型为[" + parameter.getParameterResult() + "],Y代表超时自动备货，N代表超时不备货-申请区域数量为0");


        //        OverTimeNot  0 备货   1 不备货 | V3版本概念调整： 不备货,即把申请区域数量置为0，但是所有数据还是需要提交到Team审核，即所有数据都流转到下一节点OverTimeNot=0
/*        dataList.forEach(d -> {
            d.setOverTimeNot(BigDecimal.ZERO);
            d.setAllComit(BigDecimal.ONE);
            d.setStockStatus(StockConstant.OPER_STOCK_STATUS_COMIT);

            if (StockConstant.OVERTIME_ACTION_NO.equals(parameter.getParameterResult())) {//超时备货
                d.setStockQtyArea(BigDecimal.ZERO);
            }
            d.setApplyDate(new Date());
            d.setUpdateTime(new Date());
            d.setApplyPersonNo("System");
            d.setApplyPersonName("System");
            d.setStockReason("系统自动处理自动备货类型为[" + parameter.getParameterResult() + "],Y代表超时自动备货，N代表超时不备货-申请区域数量为0");

//            platformMaterialCodeTeamAreaSet.add(d.getPlatform() + d.getMaterialCode() + d.getTeam() + d.getArea());

        });
        this.updateBatchById(dataList);*/

        this.update(updateWrapper);


        Set<String> platformMaterialCodeTeamAreaSet = dataList.stream().map(d -> String.valueOf(d)).collect(Collectors.toSet());

    /*    LambdaQueryWrapper<OperApplyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperApplyInfo::getAllComit, BigDecimal.ONE)
                .eq(OperApplyInfo::getStockStatus, StockConstant.OPER_STOCK_STATUS_WAIT)//
                .eq(OperApplyInfo::getOverTimeNot, BigDecimal.ZERO);//

        List<OperApplyInfo> dataList = mapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(dataList)) {
            return ResponseData.success("人工全部已经提交，无需系统自动提交");
        }*/

        //超时批量操作 OverTimeNot  0 备货   1 不备货

      return   this.createTeamInfo(null,platformMaterialCodeTeamAreaSet);
/*

        //提交下一节点集合
        List<TeamVerif> TeamVerifLists = new ArrayList<>();


        Set<String> platformMaterialCodeTeamAreaSet = new TreeSet<>();

        dataList.stream().forEach(i -> {

        });

        if (CollectionUtil.isNotEmpty(platformMaterialCodeTeamAreaSet)) {
            Date date = new Date();
            for (String platformMaterialCodeTeamArea : platformMaterialCodeTeamAreaSet) {
                //取出物料、team、区域明细汇总
                OperApplyInfo commitApply = mapper.selectCommit(platformMaterialCodeTeamArea);

                if (ObjectUtil.isNotNull(commitApply) && ObjectUtil.isNotEmpty(commitApply)) {
                    TeamVerif teamVerif = BeanUtil.copyProperties(commitApply, TeamVerif.class);
                    teamVerif.setOperCurMonthQty(commitApply.getOperCurMonthQty());
                    teamVerif.setOperCurMonthAdd1Qty(commitApply.getOperCurMonthAdd1Qty());
                    teamVerif.setOperCurMonthAdd2Qty(commitApply.getOperCurMonthAdd2Qty());
                    teamVerif.setOperCurMonthAdd3Qty(commitApply.getOperCurMonthAdd3Qty());
                    teamVerif.setOperCurMonthAdd4Qty(commitApply.getOperCurMonthAdd4Qty());
                    teamVerif.setOperCurMonthAdd5Qty(commitApply.getOperCurMonthAdd5Qty());
                    teamVerif.setOperCurMonthAdd6Qty(commitApply.getOperCurMonthAdd6Qty());


                    //计算AZ超180天库龄数量占比
//                    this.initValue(teamVerif);
                    StockCalcRules.teamInitValue(teamVerif);
                    teamVerif.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_WAIT);
                    teamVerif.setCreateTime(date);
                    teamVerif.setUpdateTime(date);
                    if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                        teamVerif.setVersionDes("");
                    }
                    TeamVerifLists.add(teamVerif);

                    teamVerifService.save(teamVerif);

                    //修改状态为已申请  反写id
                    mapper.updateCommitStatus(platformMaterialCodeTeamArea, teamVerif.getId().toString());
                }
            }
        }

        return ResponseData.success();*/
    }


    /**
     * @param parameter
     * @return true 超时    false  未超时
     */
    @DataSource(name = "stocking")
    @Override
    public Boolean checkOverTime(StockApprovaltimeParameterResult parameter) {

        Date date = new Date();
        String day = parameter.getParameterDay();
        String time = parameter.getParameterTime();
        String result = parameter.getParameterResult();


        //取运营申请表数据的业务推荐日期  （极端情况下 审核表一条数据都没有）
        QueryWrapper<OperApplyInfo> wrapperOne = new QueryWrapper<>();
        wrapperOne.le("rownum", 1);
        OperApplyInfo operApplyInfo = mapper.selectOne(wrapperOne);
        if (ObjectUtil.isNull(operApplyInfo)) {
            log.info("运营申请表中未找到申请记录，无法确定是否超时的基准日期");
            return Boolean.FALSE;
        }


        LocalDate bizeDate = operApplyInfo.getBizdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime compDateTime = LocalDateTime.of(bizeDate, LocalTime.MIN)
                .plusDays(Long.valueOf(day))
                .plusHours(Long.valueOf(time.split(":")[0]))
                .plusMinutes(Long.valueOf(time.split(":")[1]));

        LocalDateTime nowTime = LocalDateTime.now();

        if (nowTime.compareTo(compDateTime) <= 0) {  //当前时间<= 推荐日期+参数配置的（天，时，分）
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData updateBatch(List<OperApplyInfo> applyInfos) {

        Boolean sa = this.updateBatchById(applyInfos);
        if (sa) {
            return ResponseData.success();
        }
        return ResponseData.error("修改失败");
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchFillSalesDemand(OperApplyInfoReqVO reqVO) {

        long start = System.currentTimeMillis();
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();
        eform.setOperator(this.getUserAccount());
        reqVO.setEform(eform);

        List<OperApplyInfo> applyInfoList = this.baseMapper.queryByConditions(reqVO);
        log.info("运营申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");

        return null;
    }


    @Override
    @DataSource(name = "stocking")
    @DatalimitColumn(clazz = List.class,name = "数据列过滤")
    public List<OperApplyInfoResultV3> queryList(OperApplyInfoReqV3 reqVO) {
        log.info("运营申请列表查询开始");
        long start = System.currentTimeMillis();

        reqVO.setOperator(this.getUserAccount());
        //如果传了采购编号
        if (reqVO.getPurchaseApplyNo() != null && !reqVO.getPurchaseApplyNo().equals("")) {
            QueryWrapper<TeamVerif> queryWrapper = new QueryWrapper();
            queryWrapper.select("ID").eq("PURCHASE_APPLY_NO", reqVO.getPurchaseApplyNo());
            List<TeamVerif> teams = teamVerifService.getBaseMapper().selectList(queryWrapper);
            if (teams.size() > 0) {
                reqVO.setTeamVerifNoLists(teams.stream().map(t -> t.getId()).collect(Collectors.toList()));
            }
        }
        LoginUser loginUser = LoginContext.me().getLoginUser();
        String department = loginUser.getDepartment();
        String team = loginUser.getTeam();
        List<OperApplyInfoResultV3> resultList = mapper.queryListV3(reqVO);
        resultList.forEach(
                i-> {if (ObjectUtil.isEmpty(i.getDepartment())) {
                        i.setDepartment(department);
                    }
                    if (ObjectUtil.isEmpty(i.getTeam())) {
                        i.setTeam(team);}}
        );

        log.info("运营申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return resultList;
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData fillSalesStockDays(OperApplyInfoReqVO reqVO) {

        if (ObjectUtil.isNull(reqVO) || ObjectUtil.isNull(reqVO.getEform())) {
          return   ResponseData.error("按条件批量填充推荐备货天数,查询的条件不能为空");
        }
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        OperApplyInfoReqVO.Eform eform = reqVO.getEform();
        eform.setOperator(userAccount);
        reqVO.setEform(eform);

        //如果传了采购编号
        if (eform.getPurchaseApplyNo() != null && !eform.getPurchaseApplyNo().equals("")) {
            QueryWrapper<TeamVerif> queryWrapper = new QueryWrapper();
            queryWrapper.select("ID").eq("PURCHASE_APPLY_NO", eform.getPurchaseApplyNo());
            List<TeamVerif> teams = teamVerifService.getBaseMapper().selectList(queryWrapper);
            if (teams.size() > 0) {
                eform.setTeamVerifNoLists(teams.stream().map(t -> t.getId()).collect(Collectors.toList()));
            }
        }
        List<OperApplyInfo> resultList = this.baseMapper.queryByCondition(reqVO);

        ResponseData responseData = StockCalcRules.batchFillCalc(resultList);
        for (OperApplyInfo operApplyInfo : resultList) {
            operApplyInfo.setApplyPersonNo(userAccount);
            operApplyInfo.setApplyPersonName(userName);
        }
        this.updateBatchById(resultList);
        return responseData;
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getAllComintByPlatformTeamMaterialCode(List<String> platformMaterialCodeTeamList) {
       return this.baseMapper.getAllComintByPlatformTeamMaterialCode(platformMaterialCodeTeamList);
    }

    @DataSource(name = "stocking")
    @Override
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
        LambdaUpdateWrapper<OperApplyInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(OperApplyInfo::getStockQtyArea, BigDecimal.ZERO);
        updateWrapper.set(OperApplyInfo::getUpdateTime, date);
        updateWrapper.set(OperApplyInfo::getApplyPersonName,userName);
        updateWrapper.set(OperApplyInfo::getApplyPersonNo,userAccount);
        updateWrapper.set(OperApplyInfo::getStockReason,userName+"-->在"+date+"操作[不备货]");

        List<List<BigDecimal>> splitIdList = CollectionUtil.split(idList, 500);
        for (List<BigDecimal> ids : splitIdList) {
            updateWrapper.in(OperApplyInfo::getId, ids);
            this.update(updateWrapper);
        }

        return ResponseData.success(idList.size());
    }



    @DataSource(name = "stocking")
    @Override
    public ResponseData batchUseAdvice(List<String> idArrayList) {
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        if (ObjectUtil.isEmpty(idArrayList)) {
            return ResponseData.error("id不能为空");
        }

        List<BigDecimal> idList = this.getDetailIdList(idArrayList);


        if (ObjectUtil.isEmpty(idList)) {
            return ResponseData.error("传入ID异常"+ JSON.toJSONString(idList));
        }
        List<OperApplyInfo> operApplyInfoList = new ArrayList<>();
        List<List<BigDecimal>> splitIds = CollectionUtil.split(idList, BATCH_SIZE);
        for (List<BigDecimal> ids : splitIds) {
//            operApplyInfoList.addAll(this.baseMapper.selectBatchIds(ids));
            mapper.batchUseAdvice(ids,userName,userAccount);
        }
        //使用推荐值



/*        Date date = new Date();
        for (OperApplyInfo applyInfo : operApplyInfoList) {

            applyInfo.setOperCurMonthQty(applyInfo.getCurMonthQty());
            applyInfo.setOperCurMonthAdd1Qty(applyInfo.getCurMonthAdd1Qty());
            applyInfo.setOperCurMonthAdd2Qty(applyInfo.getCurMonthAdd2Qty());
            applyInfo.setOperCurMonthAdd3Qty(applyInfo.getCurMonthAdd3Qty());
            applyInfo.setOperCurMonthAdd4Qty(applyInfo.getCurMonthAdd4Qty());
            applyInfo.setOperCurMonthAdd5Qty(applyInfo.getCurMonthAdd5Qty());
            applyInfo.setOperCurMonthAdd6Qty(applyInfo.getCurMonthAdd6Qty());

            //销售需求备货天数1     当赋推荐值时，代入 日常备货申请.总备货天数D6；
            applyInfo.setSalesStockDays(applyInfo.getTotalBackDays());
            //销售需求覆盖日期1     当赋推荐值时，代入 日常备货申请.推荐备货覆盖销售日期D6；
            applyInfo.setOperCoversSalesDate(applyInfo.getRecomBackOverDays());
            //额外备货天数          当赋推荐值时，代入 0；
            applyInfo.setExtraDays(BigDecimal.ZERO);
            //销售需求1             当赋推荐值时，代入 日常备货申请.预估销售数量D6；
            applyInfo.setSalesDemand(applyInfo.getPreSaleQty());
            //申请区域备货数量1       当赋推荐值时，代入 日常备货申请.建议备货数量D6；
            applyInfo.setStockQtyArea(applyInfo.getRecomPreQty());
            //申请区域备货后周转天数1  当赋推荐值时，代入 Max（总备货天数D6和AZ海外总库存供货天数D6）；
            int turnoverDaysArea = Math.max(applyInfo.getTotalBackDays().intValue(), applyInfo.getPrepareDays().intValue());
            applyInfo.setTurnoverDaysArea(BigDecimal.valueOf(turnoverDaysArea));
            //运营期望交期1           默认值：每日备货推荐.期望交期D6
            applyInfo.setOperExpectedDate(applyInfo.getExpectedDeliveryDate());

            applyInfo.setStockReason(userName+"-->在"+date+"操作[使用推荐值备货]");
            applyInfo.setApplyPersonName(userName);
            applyInfo.setApplyPersonNo(userAccount);
            applyInfo.setUpdateTime(date);
        }

        this.updateBatch(operApplyInfoList);*/

        return ResponseData.success(idList.size());
    }



    @DataSource(name = "stocking")
    @Override
    public ResponseData mergeCommitBatch(List<String> idArrayList) {

        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        if (ObjectUtil.isEmpty(idArrayList)) {
            return ResponseData.error("id不能为空");
        }

        List<BigDecimal> idList = this.getDetailIdList(idArrayList);

        List<OperApplyInfo> operApplyInfoList = this.baseMapper.selectBatchIds(idList);


        // 1. 数据状态变更，提交人，提交时间补充
        /**
         * 1、批量保存备货申请信息
         */
        //物料编码集合
        Set<String> platformMaterialCodeTeamAreaSet = new HashSet<>();
        //Team集合
        Set<String> teamSet = new HashSet<>();
        //Area集合
        Set<String> areaSet = new HashSet<>();

        Date date =new Date();

        for (OperApplyInfo applyInfo : operApplyInfoList) {
            applyInfo.setApplyPersonNo(userAccount);
            applyInfo.setApplyPersonName(userName);
            applyInfo.setStockStatus(StockConstant.OPER_STOCK_STATUS_COMIT);
            applyInfo.setUpdateTime(date);
            applyInfo.setApplyDate(date);
            platformMaterialCodeTeamAreaSet.add(applyInfo.getPlatform() + applyInfo.getMaterialCode() + applyInfo.getTeam() + applyInfo.getArea());
        }

        this.updateBatch(operApplyInfoList);

        //提交
//        this.baseMapper.commitBatch(reqParamList);

        //提交下一节点集合
        List<TeamVerif> TeamVerifLists = new ArrayList<>();

        // 2. 变更物料、team、区域、下Asin是否都已经提交
        if (CollectionUtil.isNotEmpty(platformMaterialCodeTeamAreaSet)) {
            this.baseMapper.updateStockAreaList(platformMaterialCodeTeamAreaSet);

            // 3. 物料、team、区域、下Asin都已提交的插入team审核表
            for (String platformMaterialCodeTeamArea : platformMaterialCodeTeamAreaSet) {
                //取出物料、team、区域明细汇总
                OperApplyInfo commitApply = this.baseMapper.selectCommit(platformMaterialCodeTeamArea);
                if (ObjectUtil.isNull(commitApply)) {
                    continue;
                }

                TeamVerif teamVerif = BeanUtil.copyProperties(commitApply, TeamVerif.class);

//                this.initValue(teamVerif);
                StockCalcRules.teamInitValue(teamVerif);

                teamVerif.setVerifStatus(StockConstant.VERIFY_WAIT);
                teamVerif.setCreateTime(new Date());
                if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                    teamVerif.setVersionDes("");
                }
                TeamVerifLists.add(teamVerif);
                teamVerifService.save(teamVerif);

                //修改状态为已申请  反写id
                this.baseMapper.updateCommitStatus(platformMaterialCodeTeamArea, teamVerif.getId().toString());
            }
        }
        return ResponseData.success();

    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData mergeCommitBatchFast(List<String> idArrayList,List<BigDecimal> idList) {


        if (ObjectUtil.isEmpty(idList)) {
            idList = this.getDetailIdList(idArrayList);
        }

        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        List<OperApplyInfo> operApplyInfoList = new ArrayList<>();
        List<List<BigDecimal>> splitIds = CollectionUtil.split(idList, BATCH_SIZE);
        for (List<BigDecimal> ids : splitIds) {
            operApplyInfoList.addAll(this.baseMapper.selectBatchIds(ids));
        }

        List<OperApplyInfo> alreadySubmitList = operApplyInfoList.stream().filter(op -> StockConstant.OPER_STOCK_STATUS_WAIT != op.getStockStatus()).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(alreadySubmitList)) {
            Set<String> alreadySubmitData = alreadySubmitList.stream().map(as -> as.getPlatform() + "|" + as.getArea() + "|" + as.getDepartment() + "|" + as.getTeam() + "|" + as.getMaterialCode() + "|" + as.getAsin() + "|" + as.getId()).collect(Collectors.toSet());
            ResponseData.error(500, "以下数据已经提交过，不能重复提交[]", alreadySubmitData);
        }


        // 1. 数据状态变更，提交人，提交时间补充
        /**
         * 1、批量保存备货申请信息
         */
        //物料编码集合
//        Set<String> platformMaterialCodeTeamAreaSet = new HashSet<>();
        //Team集合
        Set<String> teamSet = new HashSet<>();
        //Area集合
        Set<String> areaSet = new HashSet<>();

        Date date =new Date();


        List<List<BigDecimal>> splitOperIds = CollectionUtil.split(idList, BATCH_SIZE);
        for (List<BigDecimal> ids :splitOperIds) {

            LambdaUpdateWrapper<OperApplyInfo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .set(OperApplyInfo::getApplyPersonNo,userAccount)
                    .set(OperApplyInfo::getApplyPersonName,userName)
                    .set(OperApplyInfo::getStockStatus,StockConstant.OPER_STOCK_STATUS_COMIT)
                    .set(OperApplyInfo::getApplyDate,date)
                    .set(OperApplyInfo::getUpdateTime,date)
                    .eq(OperApplyInfo::getStockStatus,StockConstant.OPER_STOCK_STATUS_WAIT);
            updateWrapper.in(OperApplyInfo::getId, ids);
            this.update(updateWrapper);
            updateWrapper.clear();
        }



/*        for (OperApplyInfo applyInfo : operApplyInfoList) {
            applyInfo.setApplyPersonNo(userAccount);
            applyInfo.setApplyPersonName(userName);
            applyInfo.setStockStatus(StockConstant.OPER_STOCK_STATUS_COMIT);
            applyInfo.setApplyDate(date);
            applyInfo.setUpdateTime(date);
//            platformMaterialCodeTeamAreaSet.add(applyInfo.getPlatform() + applyInfo.getMaterialCode() + applyInfo.getTeam() + applyInfo.getArea());   // todo:前端传值 是否是PlatformName 还是Platform
        }

        this.updateBatch(operApplyInfoList);*/


        //提交处理

        return this.createTeamInfo(operApplyInfoList,null);

    }

    @DataSource(name = "stocking")
    @NotNull
    private ResponseData createTeamInfo(List<OperApplyInfo> operApplyInfoList,Set<String> platformMaterialCodeTeamAreaSetParm ) {

        //提交下一节点集合 更新提交状态
        Set<String> platformMaterialCodeTeamAreaSet = new TreeSet<>();
        if (ObjectUtil.isEmpty(platformMaterialCodeTeamAreaSetParm)) {

            platformMaterialCodeTeamAreaSet=  operApplyInfoList.stream()
                    .map(i -> i.getPlatform() + i.getMaterialCode() + i.getTeam() + i.getArea()).collect(Collectors.toSet());
        }else {
            platformMaterialCodeTeamAreaSet = platformMaterialCodeTeamAreaSetParm;
        }



        if (CollectionUtil.isEmpty(platformMaterialCodeTeamAreaSet)) {
            return ResponseData.error("数据异常没有可以合并提交到team审核的数据");
        }

        log.info("检查是否全部都提交materialCodeTeamArea 去重条数：" + platformMaterialCodeTeamAreaSet.size());

        //更新提交
        List<List<String>> splitPlatformMaterialCodeTeamAreaSet = CollectionUtil.split(platformMaterialCodeTeamAreaSet, BATCH_SIZE);

        for (List<String> mergeFiledList : splitPlatformMaterialCodeTeamAreaSet) {
            mapper.updateStockAreaListV3(mergeFiledList);
        }

        //查找所有已经提交的申请记录

        List<OperApplyInfo> sumList = new ArrayList<>();

        for (List<String> mergeFiledList : splitPlatformMaterialCodeTeamAreaSet) {
            List<OperApplyInfo> commitApplyList = mapper.batchSelectCommit(mergeFiledList);
            sumList.addAll(commitApplyList);
        }


//        log.info("查找所有已经提交的申请记录：" + sumList.size() + "耗时：" + (System.currentTimeMillis() - begin));

        //生成team审核的数据

        List<TeamVerif> teamVerifList = new ArrayList<>();
        Date teamInitDate = new Date();
        for (OperApplyInfo commitApply : sumList) {
            if (ObjectUtil.isNotEmpty(commitApply)) {
                TeamVerif teamVerif = BeanUtil.copyProperties(commitApply, TeamVerif.class);
                teamVerif.setOperCurMonthQty(commitApply.getOperCurMonthQty());
                teamVerif.setOperCurMonthAdd1Qty(commitApply.getOperCurMonthAdd1Qty());
                teamVerif.setOperCurMonthAdd2Qty(commitApply.getOperCurMonthAdd2Qty());
                teamVerif.setOperCurMonthAdd3Qty(commitApply.getOperCurMonthAdd3Qty());
                teamVerif.setOperCurMonthAdd4Qty(commitApply.getOperCurMonthAdd4Qty());
                teamVerif.setOperCurMonthAdd5Qty(commitApply.getOperCurMonthAdd5Qty());
                teamVerif.setOperCurMonthAdd6Qty(commitApply.getOperCurMonthAdd6Qty());
                if (ObjectUtil.isNull(teamVerif.getSalesStockDays())) {
                    teamVerif.setSalesStockDays(BigDecimal.ZERO);
                }


                teamVerif.setVerifStatus(StockConstant.VERIFY_WAIT);
                teamVerif.setCreateTime(teamInitDate);
                if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                    teamVerif.setVersionDes("");
                }

                StockCalcRules.teamInitValue(teamVerif);

                teamVerifList.add(teamVerif);

            }
        }

        log.info("生成team审核的数据：" + teamVerifList.size());

        teamVerifService.saveBatch(teamVerifList, BATCH_SIZE * 6);//todo ：批量插入

        log.info("保存team审核的数据：" + teamVerifList.size());

        //回写 team审核id到运营申请记录表
        List<Map<String, String>> platformMaterialCodeTeamAreaList = new ArrayList<>();
        List<String> teamVerifIdList = new ArrayList<>();

        teamVerifList.forEach(t -> {
            HashMap<String, String> map = new HashMap<>();
            StringBuffer bf = new StringBuffer();
            String key = bf.append(t.getPlatform()).append(t.getMaterialCode()).append(t.getTeam()).append(t.getArea()).toString();
            map.put("platformMaterialCodeTeamArea", key);
            map.put("teamVerifId", String.valueOf(t.getId().longValue()));
            platformMaterialCodeTeamAreaList.add(map);
            teamVerifIdList.add(key);
        });


        List<List<Map<String, String>>> splitPlatformMaterialCodeTeamAreaList = CollectionUtil.split(platformMaterialCodeTeamAreaList, BATCH_SIZE);
        List<List<String>> splitTeamVerifIdList = CollectionUtil.split(teamVerifIdList, BATCH_SIZE);


        //更新所有已提交，和回写team编号
        for (int i = 0; i < splitPlatformMaterialCodeTeamAreaList.size(); i++) {
            mapper.batchUpdateCommitStatus(splitPlatformMaterialCodeTeamAreaList.get(i), splitTeamVerifIdList.get(i));
        }

        log.info("回写team审核id到运营申请记录表条数：" + platformMaterialCodeTeamAreaList.size());
        return ResponseData.success("运营申请批量提交成功！");
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData mergeDetail(List<BigDecimal> idList) {
        LambdaQueryWrapper<OperApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OperApplyInfo::getId, idList);
        List<OperApplyInfo> applyInfoList = this.baseMapper.selectList(queryWrapper);
        return ResponseData.success(applyInfoList);
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData updateDetailBatch(List<OperApplyInfo> applyInfoList) {

        //获取当前登录的用户账号
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        List<BigDecimal> idList = applyInfoList.stream().map(a -> a.getId()).collect(Collectors.toList());
        LambdaQueryWrapper<OperApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OperApplyInfo::getId, idList);
        List<OperApplyInfo> applyInfoListDb = this.baseMapper.selectList(queryWrapper);

        Map<BigDecimal, List<OperApplyInfo>> listMapRq = applyInfoList.stream().collect(Collectors.groupingBy(OperApplyInfo::getId));


        for (OperApplyInfo info : applyInfoListDb) {

            OperApplyInfo reqParm = listMapRq.get(info.getId()).get(0);

            info.setOperCurMonthQty(reqParm.getOperCurMonthQty());
            info.setOperCurMonthAdd1Qty(reqParm.getOperCurMonthAdd1Qty());
            info.setOperCurMonthAdd2Qty(reqParm.getOperCurMonthAdd2Qty());
            info.setOperCurMonthAdd3Qty(reqParm.getOperCurMonthAdd3Qty());
            info.setOperCurMonthAdd4Qty(reqParm.getOperCurMonthAdd4Qty());
            info.setOperCurMonthAdd5Qty(reqParm.getOperCurMonthAdd5Qty());
            info.setOperCurMonthAdd6Qty(reqParm.getOperCurMonthAdd6Qty());


            //销售需求备货天数1
            info.setSalesStockDays(reqParm.getSalesStockDays());
            //销售需求覆盖日期1     expectedDeliveryDate
            info.setOperCoversSalesDate(reqParm.getOperCoversSalesDate());
            //额外备货天数
            info.setExtraDays(reqParm.getExtraDays());
            //销售需求1
            info.setSalesDemand(reqParm.getSalesDemand());
            //申请区域备货数量1
            info.setStockQtyArea(reqParm.getStockQtyArea());
            //申请区域备货后周转天数1
            info.setTurnoverDaysArea(reqParm.getTurnoverDaysArea());
            //运营期望交期1
            info.setOperExpectedDate(reqParm.getOperExpectedDate());

            info.setStockReason(reqParm.getStockReason());
            info.setApplyPersonName(userName);
            info.setApplyPersonNo(userAccount);
            info.setUpdateTime(new Date());
        }

      return   this.updateBatch(applyInfoListDb);
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData commitDetailBatch(List<OperApplyInfo> applyInfoList) {

        this.updateDetailBatch(applyInfoList);
        List<BigDecimal> idList = applyInfoList.stream().map(a -> a.getId()).collect(Collectors.toList());
        return this.mergeCommitBatchFast(null, idList);
    }





    @DataSource(name = "stocking")
    @Override
    public ResponseData applyQtyCountMat(String materialCode) {
        LambdaQueryWrapper<OperApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OperApplyInfo::getMaterialCode, materialCode);
        List<OperApplyInfoResultV3> applyQtyCountMatList = this.mapper.applyQtyCountMat(materialCode);
        return ResponseData.success(applyQtyCountMatList);
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
