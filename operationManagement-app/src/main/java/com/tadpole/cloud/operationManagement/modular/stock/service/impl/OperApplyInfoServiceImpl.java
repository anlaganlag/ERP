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
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.OperApplyInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifService;
import com.tadpole.cloud.operationManagement.modular.stock.utils.StockCalcRules;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class OperApplyInfoServiceImpl extends ServiceImpl<OperApplyInfoMapper, OperApplyInfo> implements IOperApplyInfoService {

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

        LambdaQueryWrapper<OperApplyInfo> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(OperApplyInfo::getAllComit, StockConstant.ALL_COMIT_NO.intValue())//还未合并生成5维度的数据
                .in(OperApplyInfo::getStockStatus, StockConstant.OPER_STOCK_STATUS_WAIT, StockConstant.OPER_STOCK_STATUS_COMIT)//
                .isNull(OperApplyInfo::getNote);//非黑名单的asin

        List<OperApplyInfo> dataList = mapper.selectList(wrapper);

        if (ObjectUtil.isEmpty(dataList)) {
            return ResponseData.success("人工全部已经提交，无需系统自动提交");
        }


        //        OverTimeNot  0 备货   1 不备货
        dataList.forEach(d -> {
            if (StockConstant.OVERTIME_ACTION_YES.equals(parameter.getParameterResult())) {//超时备货
                if (StockConstant.OPER_STOCK_STATUS_WAIT == d.getStockStatus()) {
                    d.setOverTimeNot(BigDecimal.ZERO);
                    d.setStockStatus(StockConstant.OPER_STOCK_STATUS_COMIT);
                }
                d.setAllComit(BigDecimal.ONE);

            } else {//超时不备货
                if (StockConstant.OPER_STOCK_STATUS_WAIT == d.getStockStatus()) {
                    d.setOverTimeNot(BigDecimal.ONE);
                    d.setStockStatus(StockConstant.OPER_STOCK_STATUS_NO);
                } else {
                    d.setAllComit(BigDecimal.ONE);
                }
            }
            d.setApplyDate(new Date());
            d.setUpdateTime(new Date());
            d.setApplyPersonNo("System");
            d.setApplyPersonName("System");
            d.setStockReason("系统自动处理自动备货类型为[" + parameter.getParameterResult() + "],Y代表超时自动备货，N代表超时不备货");
        });

        this.updateBatchById(dataList);

        //超时批量操作 OverTimeNot  0 备货   1 不备货


        //提交下一节点集合
        List<TeamVerif> TeamVerifLists = new ArrayList<>();


        TreeSet<String> platformMaterialCodeTeamAreaSet = new TreeSet<>();

        dataList.stream().forEach(i -> {
            if (BigDecimal.ONE.equals(i.getAllComit())) {
                platformMaterialCodeTeamAreaSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam() + i.getArea());
            }
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

        return ResponseData.success();
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

    @Override
    public List<OperApplyInfoResult> queryList(OperApplyInfoReqVO reqVO) {
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


        List<OperApplyInfoResult> resultList = this.baseMapper.queryList(reqVO);
        log.info("运营申请列表查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");

        return resultList;
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateBatchOperApplyInfo(List<OperApplyInfo> updateOperInofList) {
        List<List<OperApplyInfo>> splitData = CollectionUtil.split(updateOperInofList, 500);
        for (List<OperApplyInfo> infoList : splitData) {
            mapper.updateBatchOperApplyInfo(infoList);
        }
        mapper.updateBatchOperApplyInfo(updateOperInofList);
        return ResponseData.success();
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
