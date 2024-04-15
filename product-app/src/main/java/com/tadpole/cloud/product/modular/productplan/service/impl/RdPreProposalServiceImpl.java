package com.tadpole.cloud.product.modular.productplan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.product.api.productinterpolate.model.params.RdFoReminderParam;
import com.tadpole.cloud.product.api.productplan.entity.RdPreProposal;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalParam;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalUpParam;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalExtentResult;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalUpResult;
import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPssManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;
import com.tadpole.cloud.product.modular.product.model.result.RdPssManageResult;
import com.tadpole.cloud.product.modular.product.service.IRdPlManageService;
import com.tadpole.cloud.product.modular.product.service.IRdPssManageService;
import com.tadpole.cloud.product.modular.productinterpolate.service.IRdFoReminderService;
import com.tadpole.cloud.product.modular.productplan.enums.RdPreProposalEnum;
import com.tadpole.cloud.product.modular.productplan.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productplan.mapper.RdPreProposalMapper;
import com.tadpole.cloud.product.modular.productplan.service.IRdPreProposalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import com.tadpole.cloud.product.modular.productplan.service.IRdPreProposalUpService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdProposalService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdTlSettingService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 预提案 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
@Service
@Log4j
public class RdPreProposalServiceImpl extends ServiceImpl<RdPreProposalMapper, RdPreProposal> implements IRdPreProposalService {

    @Resource
    private RdPreProposalMapper mapper;

    @Resource
    private IRdPreProposalUpService upService;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @Resource
    private IRdPssManageService pssManageService;

    @Resource
    private IRdTlSettingService tlSettingService;

    @Resource
    private IRdProposalService proposalService;

    @Resource
    private IRdPlManageService plManageService;

    @Resource
    private IRdFoReminderService foReminderService;

    @DataSource(name = "product")
    @Override
    @Transactional
    public PageResult<RdPreProposalExtentResult> listPage(RdPreProposalParam param) {
        Page pageContext = param.getPageContext();

        IPage<RdPreProposalExtentResult> page = this.mapper.listPage(pageContext, param);

        return new PageResult<>(page);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData addOrEdit(RdPreProposalParam param) {

        try {
            //log.info("创建/编辑预案 -------开始--------> 传入参数[{"+JSONObject.toJSON(param)+"}]");
            LoginUser loginUser = LoginContext.me().getLoginUser();
            List<HrmresourcetoebmsResult> list = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).collect(Collectors.toList());

            HrmresourcetoebmsResult hrm = null;
            if (list.size() > 0) {
                hrm = list.get(0);
            }
            Date oprDate = new Date();
            String sysYaCode = "";
            RdPreProposal model = BeanUtil.copyProperties(param, RdPreProposal.class);

            QueryWrapper<RdPreProposal> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_YA_CODE", param.getSysYaCode());

            //提交校验
            if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {

                if (param.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName())) {
                    //校验全新同款是否存在
                    RdPlManageParam plParam = new RdPlManageParam();
                    plParam.setSysPlCode(param.getSysPlCode());
                    List<String> proNameList = new ArrayList<>();
                    proNameList.add(param.getSysProName());
                    plParam.setSysProNameList(proNameList);
                    List<String> styleList = new ArrayList<>();
                    styleList.add(param.getSysStyle());
                    plParam.setSysStyleList(styleList);
                    List<String> mainMaterialList = new ArrayList<>();
                    mainMaterialList.add(param.getSysMainMaterial());
                    plParam.setSysMainMaterialList(mainMaterialList);
                    List<String> brandList = new ArrayList<>();
                    brandList.add(param.getSysBrand());
                    plParam.setSysBrandList(brandList);
                    List<String> modelList = new ArrayList<>();
                    modelList.add(param.getSysModel());
                    plParam.setSysModelList(modelList);
                    if ((param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QD.getName()) || param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QX.getName()))) {
                        List<RdPssManageResult> results = pssManageService.listPage(plParam);
                        if (results.size() > 0 && ObjectUtil.isNull(results.get(0).getSysCurAppVersion()) && results.get(0).getSysCurIteVersion().equals("V1.0.0")) {
                            return ResponseData.error("已存在同款,正在迭代中,请重新定义.");
                        }
                    } else {
                        plParam.setSysSpu(param.getSysSpu());
                        List<RdPssManageResult> results = pssManageService.listPage(plParam);
                        RdPssManageResult rdPssManageResult = results.size() > 0 ? results.get(0) : new RdPssManageResult();
                        if (ObjectUtil.isNotNull(rdPssManageResult.getSysCurIteVersion())) {
                            return ResponseData.error("当前款存在正在迭代的版本【" + rdPssManageResult.getSysCurIteVersion() + "】,请在当前迭代版本完成后,重新创建预案.");
                        }
                    }

                }

                //校验必填项是否都填写
                StringBuilder check = new StringBuilder();
                if ((!param.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) && ObjectUtil.isNull(param.getSysProSource())) {
                    check.append("[产品来源]必填; ");
                }
                if (ObjectUtil.isNull(param.getSysDevMethond())) {
                    check.append("[开发方式]必填; ");
                }
                if (ObjectUtil.isNull(param.getSysProName())) {
                    check.append("[产品名称]必填; ");
                }
                if (ObjectUtil.isNull(param.getSysStyle())) {
                    check.append("[款式]必填; ");
                }
                if (ObjectUtil.isNull(param.getSysMainMaterial())) {
                    check.append("[适用品牌或对象]必填; ");
                }
                if (ObjectUtil.isNull(param.getSysBrand())) {
                    check.append("[主材料]必填; ");
                }
                if (ObjectUtil.isNull(param.getSysModel())) {
                    check.append("[型号]必填; ");
                }
                if (ObjectUtil.isNull(param.getSysPlCode())) {
                    check.append("[归属产品线]必填; ");
                }
                if ((param.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName())) && ObjectUtil.isNull(param.getSysFebkProposalLevel())) {
                    check.append("[提案等级]必填; ");
                }
                if (param.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName()) && ObjectUtil.isNull(param.getSysListTimeNode())) {
                    check.append("[上架时间要求]必填; ");
                }

                if (!param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QX.getName()) && !param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QD.getName())) {
                    if (ObjectUtil.isNull(param.getSysOldProDefineNum())) {
                        check.append("[产品定义书]必填; ");
                    }
                }

                if (param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName()) && (ObjectUtil.isNotNull(param.getRdPreProposalUpParams()) && param.getRdPreProposalUpParams().size() == 0)) {
                    check.append("[改良记录]必填; ");
                }

                if (ObjectUtil.isNotNull(check.toString()) && !check.toString().equals("")) {
                    return ResponseData.error(check.toString());
                }
            }

            if (param.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName())) {

                if (ObjectUtil.isNull(param.getSysYaCode())) {
                    sysYaCode = generatorNoUtil.getYaBillNoExtents("000", "TA-KF", 3);
                }

                if (param.getSysPageOpr().equals(RdPreProposalEnum.YA_PAGE_NEW.getName())) {

                    model.setSysYaCode(sysYaCode);
                    model.setSysCDate(oprDate);
                    model.setSysLDate(oprDate);
                    model.setSysPerCode(loginUser.getAccount());
                    model.setSysPerName(loginUser.getName());
                    model.setSysDeptCode(hrm.getIdall());
                    model.setSysDeptName(hrm.getDepartmentname());
                    model.setSysFebkNewDevMethond(param.getSysDevMethond());
                    model.setSysNewProName(param.getSysProName());
                    model.setSysNewStyle(param.getSysStyle());
                    model.setSysNewBrand(param.getSysBrand());
                    model.setSysNewMainMaterial(param.getSysMainMaterial());
                    model.setSysNewModel(param.getSysModel());

                    if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                        model.setSysAssignDate(oprDate);
                        model.setSysAssignPerCode("系统");
                        model.setSysAssignPerName("系统");
                        model.setSysFebkResult(RdPreProposalEnum.YA_FEB_RESULT_YES.getName());
                        model.setSysFebkDate(oprDate);
                        model.setSysFebkPerCode(loginUser.getAccount());
                        model.setSysFebkPerName(loginUser.getName());
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_EXAM.getName());
                        model.setSysSubDate(oprDate);
                        model.setSysSubPerCode(loginUser.getAccount());
                        model.setSysSubPerName(loginUser.getName());
                    } else {
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APP.getName());
                    }

                    String sysDevMethod = ObjectUtil.isNotNull(model.getSysDevMethond()) ? model.getSysDevMethond() : "";
                    switch (sysDevMethod) {

                        case "全新品-定制":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            /*
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);*/

                            break;
                        case "全新品-现货":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-现货":
                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-改良":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-拓新":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;

                        default:

                            break;
                    }

                    //改良信息
                    if (ObjectUtil.isNotNull(param.getSysDevMethond()) && param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {

                        Integer index = 1;
                        for (RdPreProposalUpParam l : param.getRdPreProposalUpParams()) {
                            l.setId(UUID.randomUUID().toString().replace("-", ""));
                            l.setSysGNum(BigDecimal.valueOf(index));
                            index++;
                        }

                        if (param.getRdPreProposalUpParams().size() > 0) {
                            if (upService.add(param.getRdPreProposalUpParams()) > 0) {

                                if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                                    RdPssManageParam pssManageParam = new RdPssManageParam();
                                    pssManageParam.setSysPlCode(model.getSysPlCode());
                                    pssManageParam.setSysCDate(oprDate);
                                    pssManageParam.setSysLDate(oprDate);
                                    pssManageParam.setSysStatus("正常");
                                    pssManageParam.setSysProName(model.getSysNewProName());
                                    pssManageParam.setSysStyle(model.getSysNewStyle());
                                    pssManageParam.setSysMainMaterial(model.getSysNewMainMaterial());
                                    pssManageParam.setSysBrand(model.getSysNewBrand());
                                    pssManageParam.setSysModel(model.getSysNewModel());
                                    pssManageParam.setSysSpu(model.getSysSpu());
                                    pssManageParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                                    pssManageService.addOrEditVersion(pssManageParam);
                                }

                                if (this.mapper.update(model, queryWrapper) > 0) {
                                    return ResponseData.success("预案编辑成功");
                                } else {
                                    return ResponseData.error("预案编辑失败");
                                }
                            } else {
                                return ResponseData.error("保存预案改良信息失败");
                            }
                        } else {
                            if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                                RdPssManageParam pssManageParam = new RdPssManageParam();
                                pssManageParam.setSysPlCode(model.getSysPlCode());
                                pssManageParam.setSysCDate(oprDate);
                                pssManageParam.setSysLDate(oprDate);
                                pssManageParam.setSysStatus("正常");
                                pssManageParam.setSysProName(model.getSysNewProName());
                                pssManageParam.setSysStyle(model.getSysNewStyle());
                                pssManageParam.setSysMainMaterial(model.getSysNewMainMaterial());
                                pssManageParam.setSysBrand(model.getSysNewBrand());
                                pssManageParam.setSysModel(model.getSysNewModel());
                                pssManageParam.setSysSpu(model.getSysSpu());
                                pssManageParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                                pssManageService.addOrEditVersion(pssManageParam);
                            }

                            if (this.mapper.update(model, queryWrapper) > 0) {
                                return ResponseData.success("预案编辑成功");
                            } else {
                                return ResponseData.error("预案编辑失败");
                            }
                        }
                    } else {

                        if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                            RdPssManageParam pssManageParam = new RdPssManageParam();
                            pssManageParam.setSysPlCode(model.getSysPlCode());
                            pssManageParam.setSysCDate(oprDate);
                            pssManageParam.setSysLDate(oprDate);
                            pssManageParam.setSysStatus("正常");
                            pssManageParam.setSysProName(model.getSysNewProName());
                            pssManageParam.setSysStyle(model.getSysNewStyle());
                            pssManageParam.setSysMainMaterial(model.getSysNewMainMaterial());
                            pssManageParam.setSysBrand(model.getSysNewBrand());
                            pssManageParam.setSysModel(model.getSysNewModel());
                            pssManageParam.setSysSpu(model.getSysSpu());
                            pssManageParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                            pssManageService.addOrEditVersion(pssManageParam);
                        }

                        if (this.mapper.insert(model) > 0) {
                            return ResponseData.success("预案保存成功");
                        } else {
                            return ResponseData.error("预案保存失败");
                        }
                    }

                } else {

                    //删除改良信息
                    RdPreProposalUpParam delParam = new RdPreProposalUpParam();
                    delParam.setSysYaCode(param.getSysYaCode());
                    this.upService.delete(delParam);

                    model.setSysLDate(oprDate);
                    model.setSysPerCode(loginUser.getAccount());
                    model.setSysPerName(loginUser.getName());
                    model.setSysDeptCode(hrm.getIdall());
                    model.setSysDeptName(hrm.getDepartmentname());
                    model.setSysFebkNewDevMethond(param.getSysDevMethond());
                    model.setSysNewProName(param.getSysProName());
                    model.setSysNewStyle(param.getSysStyle());
                    model.setSysNewBrand(param.getSysBrand());
                    model.setSysNewMainMaterial(param.getSysMainMaterial());
                    model.setSysNewModel(param.getSysModel());

                    switch (model.getSysDevMethond()) {

                        case "全新品-定制":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            /*
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);*/

                            break;
                        case "全新品-现货":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-现货":
                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-改良":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-拓新":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;

                        default:

                            break;
                    }

                    if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                        model.setSysAssignDate(oprDate);
                        model.setSysAssignPerCode("系统");
                        model.setSysAssignPerName("系统");
                        model.setSysFebkResult(RdPreProposalEnum.YA_FEB_RESULT_YES.getName());
                        model.setSysFebkDate(oprDate);
                        model.setSysFebkPerCode(loginUser.getAccount());
                        model.setSysFebkPerName(loginUser.getName());

                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_EXAM.getName());
                        model.setSysSubDate(oprDate);
                        model.setSysSubPerCode(loginUser.getAccount());
                        model.setSysSubPerName(loginUser.getName());
                    } else {
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APP.getName());
                    }

                    //改良信息
                    if (ObjectUtil.isNotNull(param.getSysDevMethond()) && param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {

                        Integer index = 1;
                        for (RdPreProposalUpParam l : param.getRdPreProposalUpParams()) {
                            l.setId(UUID.randomUUID().toString().replace("-", ""));
                            l.setSysGNum(BigDecimal.valueOf(index));
                            index++;
                        }

                        if (param.getRdPreProposalUpParams().size() > 0 && upService.add(param.getRdPreProposalUpParams()) > 0) {
                            if (this.mapper.update(model, queryWrapper) > 0) {
                                return ResponseData.success("预案编辑成功");
                            } else {
                                return ResponseData.error("预案编辑失败");
                            }
                        } else {
                            return ResponseData.error("保存预案改良信息失败");
                        }

                    } else {

                        if (this.mapper.update(model, queryWrapper) > 0) {
                            return ResponseData.success("预案编辑成功");
                        } else {
                            return ResponseData.error("预案编辑失败");
                        }
                    }
                }
            } else if (param.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) {

                if (ObjectUtil.isNull(param.getSysYaCode())) {
                    sysYaCode = generatorNoUtil.getYaBillNoExtents("000", "TA-XQ", 3);
                }
                if (param.getSysPageOpr().equals(RdPreProposalEnum.YA_PAGE_NEW.getName())) {

                    model.setSysYaCode(sysYaCode);
                    model.setSysCDate(oprDate);
                    model.setSysLDate(oprDate);
                    model.setSysPerCode(loginUser.getAccount());
                    model.setSysPerName(loginUser.getName());
                    model.setSysDeptCode(hrm.getIdall());
                    model.setSysDeptName(hrm.getDepartmentname());
                    model.setSysFebkNewDevMethond(param.getSysDevMethond());
                    model.setSysNewProName(param.getSysProName());
                    model.setSysNewStyle(param.getSysStyle());
                    model.setSysNewBrand(param.getSysBrand());
                    model.setSysNewMainMaterial(param.getSysMainMaterial());
                    model.setSysNewModel(param.getSysModel());

                    String sysDevMethod = ObjectUtil.isNotNull(model.getSysDevMethond()) ? model.getSysDevMethond() : "";
                    switch (sysDevMethod) {

                        case "全新品-定制":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            /*
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);*/

                            break;
                        case "全新品-现货":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-现货":
                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-改良":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-拓新":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;

                        default:

                            break;
                    }

                    if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                        model.setSysAssignDate(oprDate);
                        model.setSysAssignPerCode("系统");
                        model.setSysAssignPerName("系统");
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName());
                        model.setSysSubDate(oprDate);
                        model.setSysSubPerCode(loginUser.getAccount());
                        model.setSysSubPerName(loginUser.getName());
                    } else {
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APP.getName());
                    }

                    //改良信息
                    if (ObjectUtil.isNotNull(param.getSysDevMethond()) && param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {

                        Integer index = 1;
                        for (RdPreProposalUpParam l : param.getRdPreProposalUpParams()) {
                            l.setSysYaCode(sysYaCode);
                            l.setId(UUID.randomUUID().toString().replace("-", ""));
                            l.setSysGNum(BigDecimal.valueOf(index));
                            index++;
                        }

                        if (param.getRdPreProposalUpParams().size() > 0 && upService.add(param.getRdPreProposalUpParams()) > 0) {

                            if (this.mapper.insert(model) > 0) {
                                return ResponseData.success("预案保存成功");
                            } else {
                                return ResponseData.error("预案保存失败");
                            }
                        } else {
                            return ResponseData.error("保存预案改良信息失败");
                        }

                    } else {

                        if (this.mapper.insert(model) > 0) {
                            return ResponseData.success("预案保存成功");
                        } else {
                            return ResponseData.error("预案保存失败");
                        }
                    }

                } else {

                    //删除改良信息
                    RdPreProposalUpParam delParam = new RdPreProposalUpParam();
                    delParam.setSysYaCode(param.getSysYaCode());
                    this.upService.delete(delParam);

                    model.setSysLDate(oprDate);
                    model.setSysPerCode(loginUser.getAccount());
                    model.setSysPerName(loginUser.getName());
                    model.setSysDeptCode(hrm.getIdall());
                    model.setSysDeptName(hrm.getDepartmentname());
                    model.setSysFebkNewDevMethond(param.getSysDevMethond());
                    model.setSysNewProName(param.getSysProName());
                    model.setSysNewStyle(param.getSysStyle());
                    model.setSysNewBrand(param.getSysBrand());
                    model.setSysNewMainMaterial(param.getSysMainMaterial());
                    model.setSysNewModel(param.getSysModel());

                    switch (model.getSysDevMethond()) {

                        case "全新品-定制":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            /*
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);*/

                            break;
                        case "全新品-现货":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-现货":
                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-改良":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-拓新":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;

                        default:

                            break;
                    }

                    if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                        model.setSysAssignDate(oprDate);
                        model.setSysAssignPerCode("系统");
                        model.setSysAssignPerName("系统");
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName());
                        model.setSysSubDate(oprDate);
                        model.setSysSubPerCode(loginUser.getAccount());
                        model.setSysSubPerName(loginUser.getName());
                    } else {
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APP.getName());
                    }

                    //改良信息
                    if (ObjectUtil.isNotNull(param.getSysDevMethond()) && param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {

                        Integer index = 1;
                        for (RdPreProposalUpParam l : param.getRdPreProposalUpParams()) {
                            l.setId(UUID.randomUUID().toString().replace("-", ""));
                            l.setSysGNum(BigDecimal.valueOf(index));
                            index++;
                        }

                        if (param.getRdPreProposalUpParams().size() > 0) {
                            if (upService.add(param.getRdPreProposalUpParams()) > 0) {
                                if (this.mapper.update(model, queryWrapper) > 0) {
                                    return ResponseData.success("预案编辑成功");
                                } else {
                                    return ResponseData.error("预案编辑失败");
                                }
                            } else {
                                return ResponseData.error("保存预案改良信息失败");
                            }
                        } else {
                            if (this.mapper.update(model, queryWrapper) > 0) {
                                return ResponseData.success("预案编辑成功");
                            } else {
                                return ResponseData.error("预案编辑失败");
                            }
                        }

                    } else {

                        if (this.mapper.update(model, queryWrapper) > 0) {
                            return ResponseData.success("预案编辑成功");
                        } else {
                            return ResponseData.error("预案编辑失败");
                        }
                    }
                }
            } else {
                if (ObjectUtil.isNull(param.getSysYaCode())) {
                    sysYaCode = generatorNoUtil.getYaBillNoExtents("000", "TA-TY", 3);
                }
                if (param.getSysPageOpr().equals(RdPreProposalEnum.YA_PAGE_NEW.getName())) {

                    model.setSysYaCode(sysYaCode);
                    model.setSysCDate(oprDate);
                    model.setSysLDate(oprDate);
                    model.setSysPerCode(loginUser.getAccount());
                    model.setSysPerName(loginUser.getName());
                    model.setSysDeptCode(hrm.getIdall());
                    model.setSysDeptName(hrm.getDepartmentname());
                    model.setSysFebkNewDevMethond(param.getSysDevMethond());
                    model.setSysNewProName(param.getSysProName());
                    model.setSysNewStyle(param.getSysStyle());
                    model.setSysNewBrand(param.getSysBrand());
                    model.setSysNewMainMaterial(param.getSysMainMaterial());
                    model.setSysNewModel(param.getSysModel());

                    String sysDevMethod = ObjectUtil.isNotNull(model.getSysDevMethond()) ? model.getSysDevMethond() : "";
                    switch (sysDevMethod) {

                        case "全新品-现货":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-现货":
                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;

                        default:

                            break;
                    }

                    if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                        model.setSysAssignDate(oprDate);
                        model.setSysAssignPerCode("系统");
                        model.setSysAssignPerName("系统");
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName());
                        model.setSysSubDate(oprDate);
                        model.setSysSubPerCode(loginUser.getAccount());
                        model.setSysSubPerName(loginUser.getName());
                    } else {
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APP.getName());
                    }

                    //改良信息
                    if (ObjectUtil.isNotNull(param.getSysDevMethond()) && param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {

                        Integer index = 1;
                        for (RdPreProposalUpParam l : param.getRdPreProposalUpParams()) {
                            l.setSysYaCode(sysYaCode);
                            l.setId(UUID.randomUUID().toString().replace("-", ""));
                            l.setSysGNum(BigDecimal.valueOf(index));
                            index++;
                        }

                        if (param.getRdPreProposalUpParams().size() > 0) {
                            if (upService.add(param.getRdPreProposalUpParams()) > 0) {
                                if (this.mapper.update(model, queryWrapper) > 0) {
                                    return ResponseData.success("预案编辑成功");
                                } else {
                                    return ResponseData.error("预案编辑失败");
                                }
                            } else {
                                return ResponseData.error("保存预案改良信息失败");
                            }
                        } else {
                            if (this.mapper.update(model, queryWrapper) > 0) {
                                return ResponseData.success("预案编辑成功");
                            } else {
                                return ResponseData.error("预案编辑失败");
                            }
                        }
                    } else {

                        if (this.mapper.insert(model) > 0) {
                            return ResponseData.success("预案保存成功");
                        } else {
                            return ResponseData.error("预案保存失败");
                        }
                    }

                } else {

                    //删除改良信息
                    RdPreProposalUpParam delParam = new RdPreProposalUpParam();
                    delParam.setSysYaCode(param.getSysYaCode());
                    this.upService.delete(delParam);

                    model.setSysLDate(oprDate);
                    model.setSysPerCode(loginUser.getAccount());
                    model.setSysPerName(loginUser.getName());
                    model.setSysDeptCode(hrm.getIdall());
                    model.setSysDeptName(hrm.getDepartmentname());
                    model.setSysFebkNewDevMethond(param.getSysDevMethond());
                    model.setSysNewProName(param.getSysProName());
                    model.setSysNewStyle(param.getSysStyle());
                    model.setSysNewBrand(param.getSysBrand());
                    model.setSysNewMainMaterial(param.getSysMainMaterial());
                    model.setSysNewModel(param.getSysModel());

                    switch (model.getSysDevMethond()) {

                        case "全新品-定制":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "全新品-现货":
                            model.setSysSpu("");
                            model.setSysOldProDefineVersion("");
                            model.setSysOldProDefineNum("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-现货":
                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-改良":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPatternNewDemand("");
                            model.setSysPatternNewDemandPic("");
                            model.setSysColorNewDemand("");
                            model.setSysColorNewDemandPic("");
                            model.setSysNormsNewDemand("");
                            model.setSysPackageQtyNewDemand("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;
                        case "派生品-拓新":
                            model.setSysComReferPic("");
                            model.setSysComReferLink("");
                            model.setSysAnnex("");
                            model.setSysPushSampQty(null);

                            model.setSysYaSellPointDesc("");
                            model.setSysYaMarketAsse("");
                            model.setSysMainMarket("");
                            model.setSysSeasonLabel("");
                            model.setSysFestivalLabel("");

                            model.setSysYaFuncRequire("");
                            model.setSysYaSizeRequire("");
                            model.setSysYaWeightRequire("");
                            model.setSysYaMaterialRequire("");
                            model.setSysYaPatternRequire("");
                            model.setSysYaPartsRequire("");
                            model.setSysYaPackageRequire("");
                            model.setSysYaComplianceRequire("");
                            model.setSysYaCertificationRequirex("");
                            model.setSysYaUsageScenario("");
                            model.setSysYaUserGroup("");

                            model.setSysPurCostRangeReq("");
                            model.setSysBotLinePurPrice(null);
                            model.setSysEstSellPrice(null);
                            model.setSysBotLineSellProfit(null);
                            model.setSysEstOrderQty(null);

                            break;

                        default:

                            break;
                    }

                    if (param.getSysFuncOpr().equals(RdPreProposalEnum.YA_FUNC_SUBMIT.getName())) {
                        model.setSysAssignDate(oprDate);
                        model.setSysAssignPerCode("系统");
                        model.setSysAssignPerName("系统");
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName());
                        model.setSysSubDate(oprDate);
                        model.setSysSubPerCode(loginUser.getAccount());
                        model.setSysSubPerName(loginUser.getName());
                    } else {
                        model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APP.getName());
                    }

                    //改良信息
                    if (ObjectUtil.isNotNull(param.getSysDevMethond()) && param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {

                        Integer index = 1;
                        for (RdPreProposalUpParam l : param.getRdPreProposalUpParams()) {
                            l.setId(UUID.randomUUID().toString().replace("-", ""));
                            l.setSysGNum(BigDecimal.valueOf(index));
                            index++;
                        }

                        if (param.getRdPreProposalUpParams().size() > 0 && upService.add(param.getRdPreProposalUpParams()) > 0) {
                            if (this.mapper.update(model, queryWrapper) > 0) {
                                return ResponseData.success("预案编辑成功");
                            } else {
                                return ResponseData.error("预案编辑失败");
                            }
                        } else {
                            return ResponseData.error("保存预案改良信息失败");
                        }

                    } else {

                        if (this.mapper.update(model, queryWrapper) > 0) {
                            return ResponseData.success("预案编辑成功");
                        } else {
                            return ResponseData.error("预案编辑失败");
                        }
                    }
                }
            }

        } catch (Exception exception) {
            //log.info("创建/编辑预案 -------系统异常--------> 异常[{"+JSONObject.toJSON(exception.toString())+"}]");
            return ResponseData.error("系统处理异常");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdPreProposalExtentResult detail(RdPreProposalParam param) {

        List<String> listSysYaCode = new ArrayList<>();
        listSysYaCode.add(param.getSysYaCode());
        param.setSysYaCodeList(listSysYaCode);

        RdPreProposalUpParam upParam = new RdPreProposalUpParam();
        upParam.setSysYaCode(param.getSysYaCode());
        List<RdPreProposalUpResult> upResults = this.upService.detail(upParam);

        RdPreProposalExtentResult extentResult = this.mapper.detail(param);
        extentResult.setRdPreProposalUpResultList(upResults);

        return extentResult;
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData archive(RdPreProposalParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            UpdateWrapper<RdPreProposal> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("SYS_ARCH_TYPE", RdPreProposalEnum.YA_ARCH_TYPE_CXGD.getName()).set("SYS_ARCH_REMARKS", param.getSysArchRemarks())
                    .set("SYS_ARCH_DATE", new Date()).set("SYS_ARCH_PER_NAME", loginUser.getName()).set("SYS_ARCH_PER_CODE", loginUser.getAccount())
                    .set("SYS_YA_STATUS", RdPreProposalEnum.YA_STATE_WAIT_ARCH.getName()).eq("SYS_YA_CODE", param.getSysYaCode());

            int result = this.mapper.update(null, updateWrapper);
            if (result > 0) {
                return ResponseData.success("预案归档成功");
            } else {
                return ResponseData.error("预案归档失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统异常");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData revoke(RdPreProposalParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            //SPU 删掉/迭代版本置空
            RdPreProposal model = this.mapper.selectById(param.getSysYaCode());
            RdPssManageParam pssManageParam = new RdPssManageParam();
            pssManageParam.setSysPlCode(model.getSysPlCode());
            pssManageParam.setSysProName(param.getSysNewProName());
            pssManageParam.setSysStyle(param.getSysNewStyle());
            pssManageParam.setSysMainMaterial(param.getSysNewMainMaterial());
            pssManageParam.setSysBrand(param.getSysNewBrand());
            pssManageParam.setSysModel(param.getSysNewModel());
            pssManageParam.setSysSpu(model.getSysSpu());
            pssManageService.revokePssVersion(pssManageParam);

            UpdateWrapper<RdPreProposal> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_YA_CODE", param.getSysYaCode())
                    .set("SYS_YA_STATUS", RdPreProposalEnum.YA_STATE_WAIT_APP.getName()).set("SYS_SUB_DATE", "").set("SYS_SUB_PER_CODE", "")
                    .set("SYS_SUB_PER_NAME", "").set("SYS_FEBK_RESULT", "").set("SYS_FEBK_CONTENT", "").set("SYS_FEBK_DATE", "")
                    .set("SYS_FEBK_PER_NAME", "").set("SYS_FEBK_PER_CODE", "").set("SYS_EXAM_RESULT", "").set("SYS_EXAM_INSTRUCTE", "")
                    .set("SYS_EXAM_PER_CODE", "").set("SYS_EXAM_PER_NAME", "").set("SYS_EXAM_DATE", "")
                    .set("SYS_ASSIGN_DATE", "").set("SYS_ASSIGN_PER_NAME", "").set("SYS_ASSIGN_PER_CODE", "");

            int result = this.mapper.update(null, updateWrapper);
            if (result > 0) {
                return ResponseData.success("预案撤销成功");
            } else {
                return ResponseData.error("预案撤销失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统异常");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData apply(RdPreProposalParam param) {

        try {

            RdPreProposal model = this.mapper.selectById(param.getSysYaCode());

            //校验必填项是否都填写
            StringBuilder check = new StringBuilder();
            if ((!model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) && ObjectUtil.isNull(model.getSysProSource())) {
                check.append("[产品来源]必填; ");
            }
            if (ObjectUtil.isNull(model.getSysDevMethond())) {
                check.append("[开发方式]必填; ");
            }
            if (ObjectUtil.isNull(model.getSysProName())) {
                check.append("[产品名称]必填; ");
            }
            if (ObjectUtil.isNull(model.getSysStyle())) {
                check.append("[款式]必填; ");
            }
            if (ObjectUtil.isNull(model.getSysMainMaterial())) {
                check.append("[适用品牌或对象]必填; ");
            }
            if (ObjectUtil.isNull(model.getSysBrand())) {
                check.append("[主材料]必填; ");
            }
            if (ObjectUtil.isNull(model.getSysModel())) {
                check.append("[型号]必填; ");
            }
            if (ObjectUtil.isNull(model.getSysPlCode())) {
                check.append("[归属产品线]必填; ");
            }
            if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName()) && ObjectUtil.isNull(model.getSysFebkProposalLevel())) {
                check.append("[提案等级]必填; ");
            }
            if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName()) && ObjectUtil.isNull(model.getSysListTimeNode())) {
                check.append("[上架时间要求]必填; ");
            }

            if (ObjectUtil.isNotNull(model.getSysDevMethond())) {
                if (!model.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QX.getName()) && !model.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QD.getName())) {
                    if (ObjectUtil.isNull(model.getSysOldProDefineNum())) {
                        check.append("[产品定义书]必填; ");
                    }
                }

                if (model.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {

                    RdPreProposalUpParam upParam = new RdPreProposalUpParam();
                    upParam.setSysYaCode(model.getSysYaCode());
                    List<RdPreProposalUpResult> upResults = this.upService.detail(upParam);
                    if (upResults.size() == 0) {
                        check.append("[改良记录]必填; ");
                    }
                    for (RdPreProposalUpResult up : upResults) {
                        String str = "";
                        if (ObjectUtil.isNull(up.getSysImprovePoint()) || ObjectUtil.isNull(up.getSysImprovePointPic()) || ObjectUtil.isNull(up.getSysSeverity())) {
                            str = "组序号[" + up.getSysGNum() + "]";
                        }

                        if (ObjectUtil.isNull(up.getSysImprovePoint())) {
                            str = str + " [改良点]必填 ";
                        }
                        if (ObjectUtil.isNull(up.getSysImprovePointPic())) {
                            str = str + " [改良点图示]必填 ";
                        }
                        if (ObjectUtil.isNull(up.getSysSeverity())) {
                            str = str + " [严重程度]必填 ";
                        }
                        check.append(str);
                    }
                }

                //校验全新同款是否存在
                if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName())) {
                    RdPlManageParam plParam = new RdPlManageParam();
                    plParam.setSysPlCode(param.getSysPlCode());
                    List<String> proNameList = new ArrayList<>();
                    proNameList.add(param.getSysProName());
                    plParam.setSysProNameList(proNameList);
                    List<String> styleList = new ArrayList<>();
                    styleList.add(param.getSysStyle());
                    plParam.setSysStyleList(styleList);
                    List<String> mainMaterialList = new ArrayList<>();
                    mainMaterialList.add(param.getSysMainMaterial());
                    plParam.setSysMainMaterialList(mainMaterialList);
                    List<String> brandList = new ArrayList<>();
                    brandList.add(param.getSysBrand());
                    plParam.setSysBrandList(brandList);
                    List<String> modelList = new ArrayList<>();
                    modelList.add(param.getSysModel());
                    plParam.setSysModelList(modelList);
                    if (model.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QD.getName()) || model.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QX.getName())) {

                        List<RdPssManageResult> results = pssManageService.listPage(plParam);
                        if (results.size() > 0 && ObjectUtil.isNull(results.get(0).getSysCurAppVersion()) && results.get(0).getSysCurIteVersion().equals("V1.0.0")) {
                            check.append("已存在同款,正在迭代中,请重新定义.");
                        }
                    } else {
                        plParam.setSysSpu(model.getSysSpu());
                        List<RdPssManageResult> results = pssManageService.listPage(plParam);
                        RdPssManageResult rdPssManageResult = results.size() > 0 ? results.get(0) : new RdPssManageResult();
                        if (ObjectUtil.isNotNull(rdPssManageResult.getSysCurIteVersion())) {
                            check.append("当前款存在正在迭代的版本【" + rdPssManageResult.getSysCurIteVersion() + "】,请在当前迭代版本完成后,重新创建预案.");
                        }
                        if (ObjectUtil.isNotNull(rdPssManageResult.getSysStatus()) && rdPssManageResult.getSysStatus().equals("关闭")) {
                            check.append("当前SPU已关闭,不能进行版本迭代,请确认.");
                        }
                    }
                }

            }

            if (ObjectUtil.isNotNull(check.toString()) && !check.toString().equals("")) {
                return ResponseData.error(check.toString());
            }

            LoginUser loginUser = LoginContext.me().getLoginUser(); //登陆信息
            Date oprDate = new Date(); //操作时间

            UpdateWrapper<RdPreProposal> updateWrapper = new UpdateWrapper<>();
            if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName())) {
                updateWrapper.eq("SYS_YA_CODE", param.getSysYaCode())
                        .set("SYS_YA_STATUS", RdPreProposalEnum.YA_STATE_WAIT_EXAM.getName()).set("SYS_SUB_DATE", oprDate).set("SYS_SUB_PER_CODE", loginUser.getAccount())
                        .set("SYS_SUB_PER_NAME", loginUser.getName()).set("SYS_FEBK_RESULT", RdPreProposalEnum.YA_FEB_RESULT_YES.getName()).set("SYS_FEBK_CONTENT", "").set("SYS_FEBK_DATE", oprDate)
                        .set("SYS_FEBK_PER_NAME", loginUser.getName()).set("SYS_FEBK_PER_CODE", loginUser.getAccount())
                        .set("SYS_ASSIGN_DATE", oprDate).set("SYS_ASSIGN_PER_NAME", loginUser.getName()).set("SYS_ASSIGN_PER_CODE", loginUser.getAccount());
            } else if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) {
                updateWrapper.eq("SYS_YA_CODE", param.getSysYaCode())
                        .set("SYS_YA_STATUS", RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName()).set("SYS_SUB_DATE", oprDate).set("SYS_SUB_PER_CODE", loginUser.getAccount())
                        .set("SYS_SUB_PER_NAME", loginUser.getName()).set("SYS_ASSIGN_DATE", oprDate)
                        .set("SYS_ASSIGN_PER_NAME", loginUser.getName()).set("SYS_ASSIGN_PER_CODE", loginUser.getAccount());
            } else {
                updateWrapper.eq("SYS_YA_CODE", param.getSysYaCode())
                        .set("SYS_YA_STATUS", RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName()).set("SYS_SUB_DATE", oprDate).set("SYS_SUB_PER_CODE", loginUser.getAccount())
                        .set("SYS_SUB_PER_NAME", loginUser.getName()).set("SYS_ASSIGN_DATE", oprDate)
                        .set("SYS_ASSIGN_PER_NAME", loginUser.getName()).set("SYS_ASSIGN_PER_CODE", loginUser.getAccount());
            }

            if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName())) {
                RdPssManageParam pssManageParam = new RdPssManageParam();
                pssManageParam.setSysPlCode(model.getSysPlCode());
                pssManageParam.setSysCDate(oprDate);
                pssManageParam.setSysLDate(oprDate);
                pssManageParam.setSysStatus("正常");
                pssManageParam.setSysProName(model.getSysNewProName());
                pssManageParam.setSysStyle(model.getSysNewStyle());
                pssManageParam.setSysMainMaterial(model.getSysNewMainMaterial());
                pssManageParam.setSysBrand(model.getSysNewBrand());
                pssManageParam.setSysModel(model.getSysNewModel());
                pssManageParam.setSysSpu(model.getSysSpu());
                pssManageParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                pssManageService.addOrEditVersion(pssManageParam);
            }

            int result = this.mapper.update(null, updateWrapper);
            if (result > 0) {
                return ResponseData.success("预案申请成功");
            } else {
                return ResponseData.error("预案申请失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统异常");
        }

    }

    @DataSource(name = "product")
    @Override
    public List<RdPreProposalExtentResult> listPageFebk(RdPreProposalParam param) {
        return this.mapper.listPageFebk(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData feedBack(RdPreProposalParam param) {
        try {

            LoginUser loginUser = LoginContext.me().getLoginUser();

            QueryWrapper<RdPreProposal> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_YA_CODE", param.getSysYaCode());

            Date oprDate = new Date();

            RdPreProposal model = this.baseMapper.selectOne(queryWrapper);
            model.setSysFebkPerName(loginUser.getName());
            model.setSysFebkPerCode(loginUser.getAccount());
            model.setSysFebkDate(oprDate);
            model.setSysFebkResult(param.getSysFebkResult());
            model.setSysFebkContent(param.getSysFebkContent());
            model.setSysFebkChangeDesc(param.getSysFebkChangeDesc());
            model.setSysFebkFirstOrderAmounEva(param.getSysFebkFirstOrderAmounEva());
            model.setSysFebkFirstOrderQtyEva(param.getSysFebkFirstOrderQtyEva());
            model.setSysFebkProposalLevel(param.getSysFebkProposalLevel());

            if (param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QX.getName())) {
                model.setSysFebkNewDevMethond(param.getSysFebkNewDevMethond());
            }

            if (param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QD.getName()) || param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QX.getName())) {
                model.setSysNewModel(param.getSysNewModel());
                model.setSysNewBrand(param.getSysNewBrand());
                model.setSysNewMainMaterial(param.getSysNewMainMaterial());
                model.setSysNewStyle(param.getSysNewStyle());
                model.setSysNewProName(param.getSysNewProName());
            }

            //校验全新同款是否存在
            if (model.getSysFebkResult().equals(RdPreProposalEnum.YA_FEB_RESULT_YES.getName())) {
                RdPlManageParam plParam = new RdPlManageParam();
                if (param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QD.getName()) || param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_QX.getName())) {
                    plParam.setSysPlCode(model.getSysPlCode());
                    List<String> proNameList = new ArrayList<>();
                    proNameList.add(param.getSysNewProName());
                    plParam.setSysProNameList(proNameList);
                    List<String> styleList = new ArrayList<>();
                    styleList.add(param.getSysNewStyle());
                    plParam.setSysStyleList(styleList);
                    List<String> mainMaterialList = new ArrayList<>();
                    mainMaterialList.add(param.getSysNewMainMaterial());
                    plParam.setSysMainMaterialList(mainMaterialList);
                    List<String> brandList = new ArrayList<>();
                    brandList.add(param.getSysNewBrand());
                    plParam.setSysBrandList(brandList);
                    List<String> modelList = new ArrayList<>();
                    modelList.add(param.getSysNewModel());
                    plParam.setSysModelList(modelList);
                    List<RdPssManageResult> results = pssManageService.listPage(plParam);
                    if (results.size() > 0 && ObjectUtil.isNull(results.get(0).getSysCurAppVersion()) && results.get(0).getSysCurIteVersion().equals("V1.0.0")) {
                        return ResponseData.error("已存在同款,正在迭代中,请重新定义.");
                    }
                } else {
                    plParam.setSysPlCode(model.getSysPlCode());
                    plParam.setSysSpu(param.getSysSpu());
                    List<RdPssManageResult> results = pssManageService.listPage(plParam);
                    RdPssManageResult rdPssManageResult = results.size() > 0 ? results.get(0) : new RdPssManageResult();
                    if (ObjectUtil.isNotNull(rdPssManageResult.getSysCurIteVersion())) {
                        return ResponseData.error("当前款存在正在迭代的版本【" + rdPssManageResult.getSysCurIteVersion() + "】,请在当前迭代版本完成后,重新创建预案.");
                    }
                    if (ObjectUtil.isNotNull(rdPssManageResult.getSysStatus()) && rdPssManageResult.getSysStatus().equals("关闭")) {
                        return ResponseData.error("当前SPU已关闭,不能进行版本迭代,请确认.");
                    }
                }
            }
            if (model.getSysFebkResult().equals(RdPreProposalEnum.YA_FEB_RESULT_NO.getName())) {
                model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_ARCH.getName());
                model.setSysArchDate(oprDate);
                model.setSysArchPerCode(loginUser.getAccount());
                model.setSysArchPerName(loginUser.getName());
                model.setSysArchType(RdPreProposalEnum.YA_ARCH_TYPE_FKGD.getName());
            } else {
                model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_EXAM.getName());
            }

            if (ObjectUtil.isNotNull(param.getSysDevMethond()) && param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())) {
                //删除改良信息
                RdPreProposalUpParam delParam = new RdPreProposalUpParam();
                delParam.setSysYaCode(param.getSysYaCode());
                this.upService.delete(delParam);

                //新增改良信息
                if (param.getRdPreProposalUpParams().size() > 0 && upService.add(param.getRdPreProposalUpParams()) > 0) {

                    if (!model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName()) && model.getSysFebkResult().equals(RdPreProposalEnum.YA_FEB_RESULT_YES.getName())) {
                        RdPssManageParam pssManageParam = new RdPssManageParam();
                        pssManageParam.setSysPlCode(model.getSysPlCode());
                        pssManageParam.setSysCDate(oprDate);
                        pssManageParam.setSysLDate(oprDate);
                        pssManageParam.setSysStatus("正常");
                        pssManageParam.setSysProName(model.getSysNewProName());
                        pssManageParam.setSysStyle(model.getSysNewStyle());
                        pssManageParam.setSysMainMaterial(model.getSysNewMainMaterial());
                        pssManageParam.setSysBrand(model.getSysNewBrand());
                        pssManageParam.setSysModel(model.getSysNewModel());
                        pssManageParam.setSysSpu(model.getSysSpu());
                        pssManageParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                        pssManageService.addOrEditVersion(pssManageParam);
                    }

                    if (this.mapper.update(model, queryWrapper) > 0) {
                        return ResponseData.success("反馈提交成功");
                    } else {
                        return ResponseData.error("反馈提交失败");
                    }
                } else {
                    return ResponseData.error("反馈提交修改改良信息失败");
                }
            } else {

                if (!model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_DEV.getName()) && model.getSysFebkResult().equals(RdPreProposalEnum.YA_FEB_RESULT_YES.getName())) {
                    RdPssManageParam pssManageParam = new RdPssManageParam();
                    pssManageParam.setSysPlCode(model.getSysPlCode());
                    pssManageParam.setSysCDate(oprDate);
                    pssManageParam.setSysLDate(oprDate);
                    pssManageParam.setSysStatus("正常");
                    pssManageParam.setSysProName(model.getSysNewProName());
                    pssManageParam.setSysStyle(model.getSysNewStyle());
                    pssManageParam.setSysMainMaterial(model.getSysNewMainMaterial());
                    pssManageParam.setSysBrand(model.getSysNewBrand());
                    pssManageParam.setSysModel(model.getSysNewModel());
                    pssManageParam.setSysSpu(model.getSysSpu());
                    pssManageParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                    pssManageService.addOrEditVersion(pssManageParam);
                }

                int result = this.mapper.update(model, queryWrapper);
                if (result > 0) {
                    return ResponseData.success("反馈提交成功");
                } else {
                    return ResponseData.error("反馈提交失败");
                }
            }

        } catch (Exception exception) {
            return ResponseData.error("系统异常");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData examine(RdPreProposalParam param) {
        try {
            List<RdTlSetting> rdTlSettings = tlSettingService.listPage(); //提案等级设置信息
            LoginUser loginUser = LoginContext.me().getLoginUser();

            QueryWrapper<RdPreProposal> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_YA_CODE", param.getSysYaCode());

            boolean sysYaIsAutoAppr = false; //预案是否自动审批
            Date oprDate = new Date();
            RdPreProposal model = this.baseMapper.selectOne(queryWrapper);
            model.setSysExamDate(oprDate);
            model.setSysExamInstructe(param.getSysExamInstructe());
            model.setSysExamResult(param.getSysExamResult());
            model.setSysExamPerCode(loginUser.getAccount());
            model.setSysExamPerName(loginUser.getName());
            if (param.getSysExamResult().equals(RdPreProposalEnum.YA_EXAM_RESULT_NO.getName())) {
                model.setSysArchType(RdPreProposalEnum.YA_ARCH_TYPE_SHGD.getName());
                model.setSysArchDate(oprDate);
                model.setSysArchPerName(RdPreProposalEnum.YA_SYS_DEFAULT_NAME.getName());
                model.setSysArchPerCode(RdPreProposalEnum.YA_SYS_DEFAULT_CODE.getName());
                model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_ARCH.getName());
            } else if (param.getSysExamResult().equals(RdPreProposalEnum.YA_EXAM_RESULT_FEBK.getName())) {
                model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName());
            } else {
                RdTlSetting rdTlSetting = rdTlSettings.stream().filter(l -> l.getSysTaLevel().equals(model.getSysFebkProposalLevel())).findFirst().get();
                if (ObjectUtil.isNotNull(rdTlSetting) && rdTlSetting.getSysIsAutoApprove().equals(RdPreProposalEnum.YA_IS_AUTO_APPR_YES.getName())) {
                    model.setSysApprDate(oprDate);
                    model.setSysApprPerCode(RdPreProposalEnum.YA_SYS_DEFAULT_CODE.getName());
                    model.setSysApprPerName(RdPreProposalEnum.YA_SYS_DEFAULT_NAME.getName());
                    model.setSysApprResult(RdPreProposalEnum.YA_APPR_RESULT_YES.getName());
                    model.setSysArchType(RdPreProposalEnum.YA_ARCH_TYPE_SHGD.getName());
                    model.setSysArchPerName(RdPreProposalEnum.YA_SYS_DEFAULT_NAME.getName());
                    model.setSysArchPerCode(RdPreProposalEnum.YA_SYS_DEFAULT_CODE.getName());
                    model.setSysArchDate(oprDate);
                    model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_ARCH.getName());
                    sysYaIsAutoAppr = true;
                } else {
                    model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APPR.getName());
                }
            }

            if (sysYaIsAutoAppr) {
                //SPU信息
                RdPlManageParam pssManageParam = new RdPlManageParam();
                pssManageParam.setSysPlCode(param.getSysPlCode());
                List<String> proNameList = new ArrayList<>();
                proNameList.add(param.getSysNewProName());
                pssManageParam.setSysProNameList(proNameList);
                List<String> styleList = new ArrayList<>();
                styleList.add(param.getSysNewStyle());
                pssManageParam.setSysStyleList(styleList);
                List<String> mainMaterialList = new ArrayList<>();
                mainMaterialList.add(param.getSysNewMainMaterial());
                pssManageParam.setSysMainMaterialList(mainMaterialList);
                List<String> brandList = new ArrayList<>();
                brandList.add(param.getSysNewBrand());
                pssManageParam.setSysBrandList(brandList);
                List<String> modelList = new ArrayList<>();
                modelList.add(param.getSysNewModel());
                pssManageParam.setSysModelList(modelList);
                pssManageParam.setSysSpu(model.getSysSpu());
                RdPssManageResult rdPssManageResult = null;
                List<RdPssManageResult> results = pssManageService.listPage(pssManageParam);
                if (results.size() > 0)
                    rdPssManageResult = results.get(0);

                //新增提案信息
                RdPlManageParam plManageParam = new RdPlManageParam();
                plManageParam.setSysPlCode(model.getSysPlCode());
                RdPlManageResult plManageResult = plManageService.list(plManageParam).get(0);

                HrmresourcetoebmsResult hrm = null;
                List<HrmresourcetoebmsResult> list = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(plManageResult.getSysPerCode())).collect(Collectors.toList());
                if (list.size() > 0)
                    hrm = list.get(0);

                RdProposalParam proposalParam = new RdProposalParam();
                proposalParam.setSysCDate(oprDate);
                proposalParam.setSysLDate(oprDate);
                proposalParam.setSysPerCode(plManageResult.getSysPerCode());
                proposalParam.setSysPerName(plManageResult.getSysPerName());
                proposalParam.setSysDeptCode(hrm.getIdall());
                proposalParam.setSysDeptName(hrm.getDepartmentname());
                proposalParam.setSysTaStatus(RdProposalEnum.TA_STATE_NEW.getName());
                proposalParam.setSysTaProcess(RdProposalEnum.TA_PROCESS_DESIGN.getName());
                proposalParam.setSysPlCode(model.getSysPlCode());
                proposalParam.setSysSpu(rdPssManageResult.getSysSpu());
                proposalParam.setSysTaSource(model.getSysYaScene());
                proposalParam.setSysTaSourceId(model.getSysYaCode());
                proposalParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                proposalParam.setSysVersion(rdPssManageResult.getSysCurIteVersion());

                if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) {
                    proposalParam.setSysTaLtrDate(new SimpleDateFormat("yyyy-MM").format(model.getSysListTimeNode()));
                }
                proposalParam.setSysTaLevel(model.getSysFebkProposalLevel());
                proposalParam.setSysTaPaDate(oprDate);
                proposalParam.setSysTaSeasonLabel(model.getSysSeasonLabel());
                proposalParam.setSysTaFestivalLabel(model.getSysFestivalLabel());
                proposalParam.setSysTaMainMarket(model.getSysMainMarket());
                proposalParam.setSysTaUsageScenario(model.getSysYaUsageScenario());
                proposalParam.setSysTaUserGroup(model.getSysYaUserGroup());
                proposalParam.setSysTaSellPointDesc(model.getSysYaSellPointDesc());
                proposalParam.setSysPageOpr(RdProposalEnum.TA_PAGE_NEW.getName());
                proposalService.addOrEdit(proposalParam);

                //下单提醒信息
                if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) {
                    RdFoReminderParam foReminderParam = new RdFoReminderParam();
                    foReminderParam.setSysCDate(oprDate);
                    foReminderParam.setSysLDate(oprDate);
                    foReminderParam.setSysPerCode(loginUser.getAccount());
                    foReminderParam.setSysPerName(loginUser.getName());
                    foReminderParam.setSysForStatus("待开放");
                    foReminderParam.setSysYaCode(model.getSysYaCode());
                    foReminderParam.setSysForTitle("您提的产品开发需求[" + model.getSysYaCode() + "]可以下单了,请尽快反馈首单采购数量！");

                    foReminderService.add(foReminderParam);
                }

                //修改预案信息
                this.update(model, queryWrapper);

                return ResponseData.success("审核提交成功");
            } else {

                if (param.getSysExamResult().equals(RdPreProposalEnum.YA_EXAM_RESULT_NO.getName()) || param.getSysExamResult().equals(RdPreProposalEnum.YA_EXAM_RESULT_FEBK.getName())) {
                    RdPssManageParam pssManageParam = new RdPssManageParam();
                    pssManageParam.setSysPlCode(model.getSysPlCode());
                    pssManageParam.setSysProName(param.getSysNewProName());
                    pssManageParam.setSysStyle(param.getSysNewStyle());
                    pssManageParam.setSysMainMaterial(param.getSysNewMainMaterial());
                    pssManageParam.setSysBrand(param.getSysNewBrand());
                    pssManageParam.setSysModel(param.getSysNewModel());
                    pssManageParam.setSysSpu(model.getSysSpu());

                    pssManageService.revokePssVersion(pssManageParam);
                }

                //修改预案信息
                this.update(model, queryWrapper);
                return ResponseData.success("审核提交成功");
            }

        } catch (Exception exception) {
            return ResponseData.error("系统异常");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData approve(RdPreProposalParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            QueryWrapper<RdPreProposal> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_YA_CODE", param.getSysYaCode());

            Date oprDate = new Date();
            RdPreProposal model = this.baseMapper.selectOne(queryWrapper);
            model.setSysApprDate(oprDate);
            model.setSysApprRemarks(param.getSysApprRemarks());
            model.setSysApprResult(param.getSysApprResult());
            model.setSysApprPerCode(loginUser.getAccount());
            model.setSysApprPerName(loginUser.getName());
            model.setSysArchType(RdPreProposalEnum.YA_ARCH_TYPE_SPGD.getName());
            model.setSysArchDate(oprDate);
            model.setSysArchPerName(RdPreProposalEnum.YA_SYS_DEFAULT_NAME.getName());
            model.setSysArchPerCode(RdPreProposalEnum.YA_SYS_DEFAULT_CODE.getName());
            model.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_ARCH.getName());

            if (param.getSysApprResult().equals(RdPreProposalEnum.YA_APPR_RESULT_YES.getName())) {
                //SPU信息
                RdPlManageParam pssManageParam = new RdPlManageParam();
                pssManageParam.setSysPlCode(model.getSysPlCode());
                List<String> proNameList = new ArrayList<>();
                proNameList.add(param.getSysNewProName());
                pssManageParam.setSysProNameList(proNameList);
                List<String> styleList = new ArrayList<>();
                styleList.add(param.getSysNewStyle());
                pssManageParam.setSysStyleList(styleList);
                List<String> mainMaterialList = new ArrayList<>();
                mainMaterialList.add(param.getSysNewMainMaterial());
                pssManageParam.setSysMainMaterialList(mainMaterialList);
                List<String> brandList = new ArrayList<>();
                brandList.add(param.getSysNewBrand());
                pssManageParam.setSysBrandList(brandList);
                List<String> modelList = new ArrayList<>();
                modelList.add(param.getSysNewModel());
                pssManageParam.setSysModelList(modelList);
                pssManageParam.setSysSpu(model.getSysSpu());
                RdPssManageResult rdPssManageResult = null;
                List<RdPssManageResult> results = pssManageService.listPage(pssManageParam);
                if (results.size() > 0)
                    rdPssManageResult = results.get(0);

                RdPlManageParam plManageParam = new RdPlManageParam();
                plManageParam.setSysPlCode(model.getSysPlCode());
                RdPlManageResult plManageResult = plManageService.list(plManageParam).get(0);

                HrmresourcetoebmsResult hrm = null;
                List<HrmresourcetoebmsResult> list = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(plManageResult.getSysPerCode())).collect(Collectors.toList());
                if (list.size() > 0)
                    hrm = list.get(0);

                //新增提案信息
                RdProposalParam proposalParam = new RdProposalParam();
                proposalParam.setSysCDate(oprDate);
                proposalParam.setSysLDate(oprDate);
                proposalParam.setSysPerCode(plManageResult.getSysPerCode());
                proposalParam.setSysPerName(plManageResult.getSysPerName());
                proposalParam.setSysDeptCode(hrm.getIdall());
                proposalParam.setSysDeptName(hrm.getDepartmentname());
                proposalParam.setSysTaStatus(RdProposalEnum.TA_STATE_NEW.getName());
                proposalParam.setSysTaProcess(RdProposalEnum.TA_PROCESS_DESIGN.getName());
                proposalParam.setSysPlCode(model.getSysPlCode());
                proposalParam.setSysSpu(rdPssManageResult.getSysSpu());
                proposalParam.setSysTaSource(model.getSysYaScene());
                proposalParam.setSysTaSourceId(model.getSysYaCode());
                proposalParam.setSysDevMethond(model.getSysFebkNewDevMethond());
                proposalParam.setSysVersion(rdPssManageResult.getSysCurIteVersion());

                if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) {
                    proposalParam.setSysTaLtrDate(new SimpleDateFormat("yyyy-MM").format(model.getSysListTimeNode()));
                }
                proposalParam.setSysTaLevel(model.getSysFebkProposalLevel());
                proposalParam.setSysTaPaDate(oprDate);
                proposalParam.setSysTaSeasonLabel(model.getSysSeasonLabel());
                proposalParam.setSysTaFestivalLabel(model.getSysFestivalLabel());
                proposalParam.setSysTaMainMarket(model.getSysMainMarket());
                proposalParam.setSysTaUsageScenario(model.getSysYaUsageScenario());
                proposalParam.setSysTaUserGroup(model.getSysYaUserGroup());
                proposalParam.setSysTaSellPointDesc(model.getSysYaSellPointDesc());
                proposalParam.setSysPageOpr(RdProposalEnum.TA_PAGE_NEW.getName());
                proposalService.addOrEdit(proposalParam);

                //下单提醒信息
                if (model.getSysYaScene().equals(RdPreProposalEnum.YA_SCENE_OPR.getName())) {
                    RdFoReminderParam foReminderParam = new RdFoReminderParam();
                    foReminderParam.setSysCDate(oprDate);
                    foReminderParam.setSysLDate(oprDate);
                    foReminderParam.setSysPerCode(loginUser.getAccount());
                    foReminderParam.setSysPerName(loginUser.getName());
                    foReminderParam.setSysForStatus("待开放");
                    foReminderParam.setSysYaCode(model.getSysYaCode());
                    foReminderParam.setSysForTitle("您提的产品开发需求[" + model.getSysYaCode() + "]可以下单了,请尽快反馈首单采购数量！");

                    foReminderService.add(foReminderParam);
                }

                //修改预案信息
                this.update(model, queryWrapper);

                return ResponseData.success("审批提交成功");
            } else {
                if (param.getSysApprResult().equals(RdPreProposalEnum.YA_APPR_RESULT_NO.getName())) {
                    RdPssManageParam pssManageParam = new RdPssManageParam();
                    pssManageParam.setSysPlCode(model.getSysPlCode());
                    pssManageParam.setSysProName(param.getSysNewProName());
                    pssManageParam.setSysStyle(param.getSysNewStyle());
                    pssManageParam.setSysMainMaterial(param.getSysNewMainMaterial());
                    pssManageParam.setSysBrand(param.getSysNewBrand());
                    pssManageParam.setSysModel(param.getSysNewModel());
                    pssManageParam.setSysSpu(model.getSysSpu());

                    pssManageService.revokePssVersion(pssManageParam);
                }

                //修改预案信息
                this.update(model, queryWrapper);

                return ResponseData.success("审批提交成功");
            }

        } catch (Exception exception) {
            return ResponseData.error("系统异常");
        }

    }

}
