package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.*;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.BackPrepareRecomBiMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SysBizConfigMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.TeamVerifMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.BackPrepareRecomBiParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.BackPrepareRecomBiExcel;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.BackPrepareRecomBiResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.*;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
* <p>
    * 每日备货推荐表-BI 服务实现类
    * </p>
*
* @author cyt
* @since 2022-08-19
*/
@Service
@Slf4j
public class BackPrepareRecomBiServiceImpl extends ServiceImpl<BackPrepareRecomBiMapper, BackPrepareRecomBi> implements IBackPrepareRecomBiService {
    @Autowired
    ResourceLoader resourceLoader;

    @Resource
    private BackPrepareRecomBiMapper mapper;

    @Resource
    private SysBizConfigMapper sysBizConfigMapper;

    @Resource
    private ISysBizConfigService sysBizConfigService;


    @Resource
    private TeamVerifMapper teamVerifMapper;

    @Resource
    private ITeamVerifService teamVerifService;

    @Resource
    private IPurchaseOrdersService purchaseOrdersService;

    @Resource
    private ISpecialApplyInfoV3Service specialApplyInfoV3Service;

    @Resource
    private IVerifInfoRecordService verifInfoRecordService;

    @Resource
    private RpMaterialService rpMaterialService;



    @DataSource(name = "stocking")
    @Override
    public PageResult<BackPrepareRecomBiResult> queryPage(BackPrepareRecomBiParam reqVO) {


        reqVO.setOperator(this.getUserAccount());

        log.info("每天备货推荐查询开始");
        long start = System.currentTimeMillis();

        IPage<BackPrepareRecomBiResult> page = this.baseMapper.queryPage(reqVO.getPageContext(), reqVO);
        log.info("每天备货推荐查询结束，耗时---------->" + (System.currentTimeMillis() - start) + "ms");
        return new PageResult<>(page);
    }


    @DataSource(name = "stocking")
    @Override
    public List<BackPrepareRecomBiResult> export(BackPrepareRecomBiParam reqVO) {

        reqVO.setOperator(this.getUserAccount());
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return this.baseMapper.queryPage(pageContext, reqVO).getRecords();
    }

    @Override
    @DataSource(name = "stocking")
    public void exportExcel(HttpServletResponse response, BackPrepareRecomBiParam param) throws Exception {


        param.setOperator(this.getUserAccount());
        List<BackPrepareRecomBiExcel> infoList = this.baseMapper.excelQueryPage(param);

        if (ObjectUtil.isEmpty(infoList)) {
            throw new Exception("每日备货推荐导出失败，原因：未找到该对应的数据");
        }

        String filename = "每日备货推荐";
        String modelPath = "classpath:template/backPrepareRecomBi.xlsx";//模板所在路径
        org.springframework.core.io.Resource resource = resourceLoader.getResource(modelPath);

        InputStream inputStream = resource.getInputStream();

        BackPrepareRecomBiExcel applyInfo = infoList.get(0);


        Date bizdate = applyInfo.getBizdate();//推荐日期
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//推荐日期月份

        //填充单个的值,当前月往后计算7个月 的数字
        int monthValue = applyInfo.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                //map.put("curMonth", monthValue);
                map.put("curMonth", bizLocalDate.getMonthValue());
                continue;
            }
            if (i < 4) {
                map.put("curMonthMinus" + i, bizLocalDate.minusMonths(Long.valueOf(i)).getMonthValue());
            }
            //int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            //map.put("curMonthAdd" + i, mothInt);
            map.put("curMonthAdd" + i, bizLocalDate.plusMonths(Long.valueOf(i)).getMonthValue());
        }

        List<BackPrepareRecomBiExcel> resultList = new ArrayList<>();

        for (BackPrepareRecomBiExcel result : infoList) {
            resultList.add(result);
        }

        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(map, writeSheet);
            excelWriter.fill(resultList, fillConfig, writeSheet);
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            log.error("事业部审核导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }
    }


    @DataSource(name = "master")
    @Override
    public ResponseData getDepartment(String accountCode) {
    /*    LambdaQueryWrapper<EntUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EntUser::getUserCode, accountCode);
        List<EntUser> userList = entUserMapper.queryInfoByDictType(wrapper);
        if (ObjectUtil.isNotEmpty(userList)) {
            return ResponseData.success(userList.get(0).getDepartment());
        }
        return ResponseData.error("输入的工号有误，没有找到对应的账户信息");*/
        return ResponseData.error("根据工号获取部门信息-新平台使用登录时缓存的信息获取");
    }



    @DataSource(name = "stocking")
    @Override
//    @Transactional(rollbackFor = Exception.class)
        @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public TeamVerif creatTeamVerif(SpecialApplyInfoV3 applyInfo) {

        TeamVerif teamVerif = this.initTeamVerif(applyInfo);
      /*  if (ObjectUtil.isNotNull(teamVerif)) {
            this.saveTeamverif(teamVerif);
//            teamVerifMapper.insert(teamVerif);
            applyInfo.setTeamVerifNo(teamVerif.getId().intValue());
        }*/
        return teamVerif;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTeamverif(TeamVerif teamVerif) {
        teamVerifService.save(teamVerif);
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public TeamVerif initTeamVerif(SpecialApplyInfoV3 applyInfo) {

//        groupFiled=t.platform|| t.area ||t.department|| t.team|| t.material_code

        StringBuffer stringBuffer = new StringBuffer();
        String groupFiled = stringBuffer.append(applyInfo.getPlatform()).append(applyInfo.getArea()).append(applyInfo.getDepartment())
                .append(applyInfo.getTeam()).append(applyInfo.getMaterialCode()).toString();
        TeamVerif teamVerif=null;
        teamVerif=  mapper.creatTeamVerif(groupFiled);

        boolean noHaveBiDate = ObjectUtil.isNull(teamVerif) ? true : false;
        if (noHaveBiDate) { // 没有BI的推荐数据，需要关联物料信息初始化物料信息数据
            teamVerif = new TeamVerif();
//            Material materialInfo = this.getMaterial(applyInfo.getMaterialCode());
            Material materialInfo = rpMaterialService.getMaterialInfo(applyInfo.getMaterialCode());
            BeanUtil.copyProperties(materialInfo, teamVerif, CopyOptions.create().ignoreNullValue().setIgnoreProperties("createTime"));
            teamVerif.setBrand(materialInfo.getFitBrand());
            teamVerif.setPattern(materialInfo.getDesign());
        }

        //初始化 特殊备货申请填写的信息
        teamVerif.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_APPLY);
        teamVerif.setVerifPersonName(applyInfo.getApplyPersonName());
        teamVerif.setVerifPersonNo(applyInfo.getApplyPersonNo());
        teamVerif.setVerifDate(applyInfo.getApplyDate());
        teamVerif.setBillType(applyInfo.getBillType());//备货类型
        teamVerif.setSalesStockDays(applyInfo.getSalesStockDays());
        teamVerif.setSalesDemand(applyInfo.getSalesDemand());
        teamVerif.setOperExpectedDate(applyInfo.getOperExpectedDate());
        teamVerif.setStockReason(applyInfo.getStockReason());
//        teamVerif.setRecomDeliveryType(applyInfo.getDeliveryType());
        teamVerif.setDeliveryType(applyInfo.getDeliveryType());
        teamVerif.setAllComit(applyInfo.getAllComit());
        teamVerif.setStockQtyArea(applyInfo.getApplyQty());//采购申请数量
        teamVerif.setCreateTime(new Date());
        teamVerif.setPlatform(applyInfo.getPlatform());
        teamVerif.setArea(applyInfo.getArea());
        teamVerif.setDepartment(applyInfo.getDepartment());
        teamVerif.setTeam(applyInfo.getTeam());


        //

        if ( ! noHaveBiDate) { //BI有推荐值 需要初始字段值

            //申请区域备货后周转天数   '界面实时计算：MAX(每日备货推荐.销售需求2,每日备货推荐.AZ海外总库存D5)/日均销量D5
            if (teamVerif.getDayavgqty().compareTo(BigDecimal.ZERO) > 0) {
                teamVerif.setTurnoverDaysArea(applyInfo.getSalesDemand().max(teamVerif.getTotalVolume()).divide(teamVerif.getDayavgqty(), 0, BigDecimal.ROUND_DOWN));
            } else {
                teamVerif.setTurnoverDaysArea(BigDecimal.valueOf(99999));
            }

            //今年上月/去年上月

            if (teamVerif.getLastPreMonthQty().compareTo(BigDecimal.ZERO) > 0) {
                teamVerif.setCurPreMonthLastPreMonth(teamVerif.getCurPreMonthQty().divide(teamVerif.getLastPreMonthQty(), 4, BigDecimal.ROUND_DOWN));
            } else {
                teamVerif.setCurPreMonthLastPreMonth(BigDecimal.valueOf(99999));
            }

            // -- AZ海外总库存供货天数  修改  向下取整 SUM(每日备货推荐.AZ海外总库存D6  TOTAL_VOLUME)/(SUM(每日备货推荐.预估销售数量D6  PRE_SALE_QTY)/DISTINCT(总备货天数D6  total_back_days))

            if (teamVerif.getPreSaleQty().compareTo(BigDecimal.ZERO) <= 0 || teamVerif.getTotalBackDays().compareTo(BigDecimal.ZERO) <= 0) {
                teamVerif.setPrepareDays(BigDecimal.valueOf(99999));
            } else {
                teamVerif.setPrepareDays(
                        teamVerif.getTotalVolume().divide(
                                teamVerif.getPreSaleQty().divide(teamVerif.getTotalBackDays(),8, BigDecimal.ROUND_DOWN)
                                ,0
                                ,BigDecimal.ROUND_DOWN)
                );
            }

        }
        return teamVerif;
    }

    @DataSource(name = "basicdata")
    @Override
    @Transactional(rollbackFor = Exception.class)
//    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Material getMaterial(String materialCode) {
        Material materialInfo = rpMaterialService.getMaterialInfo(materialCode);
        return materialInfo;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOrders createPurchaseOrder(List<TeamVerif> teamVerifList, List<SpecialApplyInfoV3> infoList) {

//        PurchaseOrders orders = purchaseOrdersService.initPurchaseOrders(teamVerifList);
        PurchaseOrders orders = this.initPurchaseOrders(teamVerifList);

        if (ObjectUtil.isNull(orders)) {
            return null;
        }
        orders.setId(IdWorker.getIdStr());
        purchaseOrdersService.save(orders);
        String purchaseApplyNo = orders.getId();
        String billType = orders.getBillType();
        //修改状态为已申请  反写采购申请单id
        String groupFiled = getPurchaseOrderGroupFiled(teamVerifList.get(0));
        this.baseMapper.updatePurchaseApplyNo(groupFiled, purchaseApplyNo, billType, teamVerifList);

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        //补冲一条事业部审核记录
        VerifInfoRecord verifInfoRecord = this.creatVerifInfo("特殊备货已线下审核，事业部审批系统自动通过", orders.getPurchaseApplyQty(), orders.getId(), currentUser, 10);
        verifInfoRecordService.save(verifInfoRecord);


        if (ObjectUtil.isNull(orders)) {
           return orders;
        }

        for (SpecialApplyInfoV3 applyInfo : infoList) {
            applyInfo.setPurchaseApplyNo(orders.getId());
            applyInfo.setStatus(BigDecimal.valueOf(2));
            applyInfo.setUpdateTime(new Date());
            applyInfo.setApplyDate(new Date());
//            this.baseMapper.updateById(applyInfo);
        }
        specialApplyInfoV3Service.updateBatchByIdList(infoList);

        return orders;
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
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VerifInfoRecord creatVerifInfo(String reason, BigDecimal qty, String id, LoginUser currentUser, int verifBizType) {
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

    @DataSource(name = "stocking")
    @Override
//    @Transactional(rollbackFor = Exception.class)
        @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PurchaseOrders initPurchaseOrders(List<TeamVerif> teamVerifList) {

        PurchaseOrders purchaseOrders=null;

        TeamVerif teamVerif = teamVerifList.get(0);
        String groupFiled = getPurchaseOrderGroupFiled(teamVerif);

        purchaseOrders=  mapper.createPurchaseOrder(groupFiled,teamVerifList);

        if (ObjectUtil.isNull(purchaseOrders)) {
            return null;
        }

        PurchaseOrders orders = BeanUtil.copyProperties(purchaseOrders, PurchaseOrders.class);
        orders.setOrderStatus(StockConstant.ORDER_STATUS_PLAN_WAIT);

        if (ObjectUtil.isNotNull(orders.getBizdate())) {

            // 申请备货数量计算逻辑错误，需要修改成采购申请数量=SUM(特殊备货申请.申请区域备货数量D6）-国内可用库存-采购未完成数量-申请审核中数量
//        BigDecimal applyQty = orders.getPurchaseApplyQty().subtract(orders.getCanuseqty()).subtract(orders.getUnpurchase()).subtract(orders.getApproveQty());
//        orders.setAdviceApplyQty(applyQty);
//        orders.setPurchaseApplyQty(applyQty);

            //补充销售需求覆盖日期
            if (ObjectUtil.isNull(purchaseOrders.getSalesStockDays())) {
                orders.setRecomBackOverDays(DateUtil.date());
            } else {
                orders.setRecomBackOverDays(DateUtil.offsetMonth(DateUtil.date(), purchaseOrders.getSalesStockDays().intValue()));
            }
            //补充 申请区域备货后周转天数
            if (orders.getTotalVolume() == null) {
                orders.setTotalVolume(new BigDecimal(0));
            }
//                    BigDecimal salesDemand = orders.getSalesDemand().max(orders.getTotalVolume());
//       界面实时计算：（AZ海外总库存D4+国内可用库存+采购未完成数量+申请审核中数量+采购申请数量D4)/日均销量D4
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
        }

        orders.setOverTimeNot(1L);
        orders.setOrderLastTime(purchaseOrdersService.lastOrderTime(orders.getPlatform(),orders.getTeam(),orders.getMaterialCode()));

     /*   orders.setId(IdWorker.getIdStr());
        purchaseOrdersService.save(orders);
        String purchaseApplyNo = orders.getId();
        String billType = orders.getBillType();
        //修改状态为已申请  反写id
        this.baseMapper.updatePurchaseApplyNo(groupFiled, purchaseApplyNo, billType, teamVerifList);*/
        return orders;

    }


    @DataSource(name = "stocking")
    @Override
//    @Transactional(rollbackFor = Exception.class)
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public TeamVerif updateTeamVerif(SpecialApplyInfoV3 applyInfo,TeamVerif teamVerif) {

        TeamVerif initTeamVerif = this.initTeamVerif(applyInfo);

        BeanUtil.copyProperties(initTeamVerif, teamVerif, CopyOptions.create().setIgnoreNullValue(true).setIgnoreProperties("createTime"));

        teamVerif.setId(BigDecimal.valueOf(applyInfo.getTeamVerifNo()));

        teamVerifMapper.updateById(teamVerif);
        return teamVerif;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOrders updatePurchaseOrder(PurchaseOrders order,List<TeamVerif> teamVerifList) {

        PurchaseOrders initOrder = this.initPurchaseOrders(teamVerifList);
//        String id = order.getId();

        BeanUtil.copyProperties(initOrder, order, CopyOptions.create().setIgnoreNullValue(true));
        order.setUpdateTime(new Date());
//        order.setId(id);

            purchaseOrdersService.updateById(order);

        return order;
    }


    private String getPurchaseOrderGroupFiled(TeamVerif teamVerif) {
        String groupFiled;
        StringBuffer stringBuffer = new StringBuffer();
        //t.platform||p.department||t.team||t.material_code||t.bill_type
        groupFiled = stringBuffer.append(teamVerif.getPlatform()).append(teamVerif.getDepartment())
                .append(teamVerif.getTeam()).append(teamVerif.getMaterialCode()).append(teamVerif.getBillType()).toString();
        return groupFiled;
    }


    @DataSource(name = "stocking")
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResponseData flashSpecialApplyData() {

        LambdaQueryWrapper<SysBizConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysBizConfig::getAppCode, "MCMS")
                .eq(SysBizConfig::getBizCode,"SPECIAL_APPLY_INFO_UPDATE");

        List<SysBizConfig> sysBizConfigList = sysBizConfigMapper.selectList(wrapper);
        if (ObjectUtil.isEmpty(sysBizConfigList)) {
          return   ResponseData.error("未找到对应的业务配置信息");
        }

        SysBizConfig bizConfig = sysBizConfigList.get(0);

        if ( ! bizConfig.getActionResult().equals("0")) { //为0的时候需要更新
            return ResponseData.success("特殊备货申请每日推荐数据不需要更新");
        }
        ResponseData responseData = specialApplyInfoV3Service.flashSpecialApplyData(bizConfig);
//        sysBizConfigService.updateActionResult(bizConfig.getId(),"1");
//        return  this.updateSysBizConfig(bizConfig);
        return  responseData;
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateSysBizConfig(SysBizConfig bizConfig) {
        ResponseData responseData = specialApplyInfoV3Service.flashSpecialApplyData(bizConfig);
        if (responseData.getSuccess()) {
            sysBizConfigService.updateActionResult(bizConfig.getId(),"1");
        }
        return responseData;
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
