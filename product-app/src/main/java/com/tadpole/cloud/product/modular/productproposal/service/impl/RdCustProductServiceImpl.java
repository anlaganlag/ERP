package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.product.api.productproposal.entity.*;
import com.tadpole.cloud.product.api.productproposal.model.params.RdCustProductParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdCustProductDet2Result;
import com.tadpole.cloud.product.api.productproposal.model.result.RdCustProductDetResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdCustProductResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.api.productproposal.entity.RdCustProductDet2;
import com.tadpole.cloud.product.modular.product.entity.RdPssManage;
import com.tadpole.cloud.product.modular.product.entity.RdPssManageVersion;
import com.tadpole.cloud.product.modular.product.mapper.RdPssManageVersionMapper;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.*;
import com.tadpole.cloud.product.modular.productproposal.service.IRdCustProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-定品 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
@Service
public class RdCustProductServiceImpl extends ServiceImpl<RdCustProductMapper, RdCustProduct> implements IRdCustProductService {

    @Resource
    private RdCustProductMapper mapper;

    @Resource
    private RdCustProductDetMapper rdCustProductDetMapper;

    @Resource
    private RdCustProductDet2Mapper rdCustProductDet2Mapper;

    @Resource
    private RdSampleTaskMapper rdSampleTaskMapper;

    @Resource
    private RdSamplePaMapper rdSamplePaMapper;

    @Resource
    private RdSampleCaMapper rdSampleCaMapper;

    @Resource
    private RdMoldOpenPfaMapper rdMoldOpenPfaMapper;

    @Resource
    private RdSampleManageMapper rdSampleManageMapper;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @Resource
    private RdProposalMapper rdProposalMapper;

    @Resource
    private RdPssManageVersionMapper rdPssManageVersionMapper;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdCustProductResult> listPage(RdCustProductParam param) {
        return this.mapper.listPage(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData checkIsCanCreate(RdCustProductParam param) {

        //校验定品申请
        QueryWrapper<RdCustProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());

        List<RdCustProduct> rdCustProducts = this.mapper.selectList(queryWrapper);
        List<RdCustProduct> rdCustProductWaitPub = rdCustProducts.stream().filter(l -> l.getSysCpStatus().equals(RdProposalEnum.CP_STATUS_WAIT_SUBMIT.getName())).collect(Collectors.toList());
        List<RdCustProduct> rdCustProductIng = rdCustProducts.stream().filter(l -> l.getSysCpStatus().equals(RdProposalEnum.CP_STATUS_WAIT_APPR1.getName())).collect(Collectors.toList());
        if (rdCustProductWaitPub.size() > 0) {
            return ResponseData.error("系统已存在待提交的申请,申请编号{" + rdCustProductWaitPub.get(0).getSysCpCode() + "},不能再申请。");
        }

        if (rdCustProductIng.size() > 0) {
            return ResponseData.error("系统存在进行中的申请,申请编号{" + rdCustProductIng.get(0).getSysCpCode() + "},不能再申请。");
        }


        //校验拿样任务
        QueryWrapper<RdSampleTask> rdSampleTaskQueryWrapper = new QueryWrapper<>();
        rdSampleTaskQueryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());

        List<RdSampleTask> rdSampleTasks = this.rdSampleTaskMapper.selectList(rdSampleTaskQueryWrapper);

        List<RdSampleTask> rdSampleTasksWaitPub = rdSampleTasks.stream().filter(l -> l.getSysTsTaskStatus().equals(RdProposalEnum.SAMPLE_STATE_WAIT_PUBLIC.getName())).collect(Collectors.toList());
        List<RdSampleTask> rdSampleTasksIng = rdSampleTasks.stream().filter(l -> l.getSysTsTaskStatus().equals(RdProposalEnum.SAMPLE_STATE_SAMPLE.getName())).collect(Collectors.toList());
        if (rdSampleTasksWaitPub.size() > 0) {
            return ResponseData.error("系统已存在待发布的任务,任务编号{" + rdSampleTasksWaitPub.get(0).getSysTsTaskCode() + "},不进行定品申请。");
        }

        if (rdSampleTasksIng.size() > 0) {
            return ResponseData.error("系统存在进行中的任务,任务编号{" + rdSampleTasksIng.get(0).getSysTsTaskCode() + "},不进行定品申请。");
        }

        return ResponseData.success();
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdCustProductResult detail(RdCustProductParam param) {
        RdCustProductResult result = new RdCustProductResult();
        if (ObjectUtil.isNotNull(param.getSysCpCode())) {

            if (!param.getSysPageOpr().equals(RdProposalEnum.CP_PAGE_APPR.getName())) {
                QueryWrapper<RdSampleTask> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
                List<RdSampleTask> rdSampleTasks = this.rdSampleTaskMapper.selectList(queryWrapper);
                List<RdSampleTask> rdSampleTasksC = rdSampleTasks.stream().filter(l -> l.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName())).collect(Collectors.toList());
                if (rdSampleTasksC.size() > 0) {
                    param.setSysTsTaskCodeList(rdSampleTasksC.stream().map(l -> l.getSysTsTaskCode()).collect(Collectors.toList()));
                }
            }

            List<RdCustProductResult> results = this.mapper.detail(param);

            if (results.size() > 0) {
                result = results.get(0);
            }

            List<RdCustProductDetResult> detResults = this.rdCustProductDetMapper.listCustProductDet(param);
            if (detResults.size() > 0 && param.getSysPageOpr().equals(RdProposalEnum.CP_PAGE_APPR.getName())) {
                result.setRdCustProductDetResultList(detResults);
            } else {
                List<RdCustProductDetResult> rdCustProductDetResults = this.rdCustProductDetMapper.listCustSample(param);
                if (rdCustProductDetResults.size() > 0) {
                    result.setRdCustProductDetResultList(rdCustProductDetResults);
                }
            }

            List<RdCustProductDet2Result> rdCustProductDet2Results = this.rdCustProductDet2Mapper.listCustSample(param);
            if (rdCustProductDet2Results.size() > 0) {
                result.setRdCustProductDetResult2List(rdCustProductDet2Results);
            }

        } else {
            //找到最近一次的定品申请编号
            QueryWrapper<RdCustProduct> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq("SYS_TA_CODE", param.getSysTaCode()).orderByDesc("SYS_C_DATE");
            List<RdCustProduct> rdCustProductList = this.mapper.selectList(productQueryWrapper);

            if(rdCustProductList.size()>0){
                param.setSysCpCode(rdCustProductList.get(0).getSysCpCode());
            }

            QueryWrapper<RdSampleTask> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
            List<RdSampleTask> rdSampleTasks = this.rdSampleTaskMapper.selectList(queryWrapper);
            List<RdSampleTask> rdSampleTasksC = rdSampleTasks.stream().filter(l -> l.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName())).collect(Collectors.toList());
            if (rdSampleTasksC.size() > 0) {
                param.setSysTsTaskCodeList(rdSampleTasksC.stream().map(l -> l.getSysTsTaskCode()).collect(Collectors.toList()));
            }

            List<RdCustProductResult> results = this.mapper.detail(param);
            if (results.size() > 0) {
                result = results.get(0);
            }

            List<RdCustProductDetResult> rdCustProductDetResults = this.rdCustProductDetMapper.listCustSample(param);
            if (rdCustProductDetResults.size() > 0) {
                result.setRdCustProductDetResultList(rdCustProductDetResults);
            }
            List<RdCustProductDet2Result> rdCustProductDet2Results = this.rdCustProductDet2Mapper.listCustSample(param);
            if (rdCustProductDet2Results.size() > 0) {
                result.setRdCustProductDetResult2List(rdCustProductDet2Results);
            }
        }
        return result;
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData addOrEdit(RdCustProductParam param) {
        try {
            RdCustProduct rdCustProduct = BeanUtil.copyProperties(param, RdCustProduct.class);
            Date oprDate = new Date();
            LoginUser loginUser = LoginContext.me().getLoginUser();
            HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();
            if (param.getSysPageOpr().equals(RdProposalEnum.CP_PAGE_NEW.getName())) {
                rdCustProduct.setSysCpCode(generatorNoUtil.getCpBillNoExtents("00", param.getSysTaCode() + "-DP", 2));
                QueryWrapper<RdCustProduct> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
                Integer count = this.mapper.selectCount(queryWrapper);
                rdCustProduct.setSysCpName("第【" + (count + 1) + "】次定品");

                rdCustProduct.setSysCDate(oprDate);
                rdCustProduct.setSysLDate(oprDate);
                rdCustProduct.setSysDeptCode(hrm.getIdall());
                rdCustProduct.setSysDeptName(hrm.getDepartmentname());
                rdCustProduct.setSysPerCode(loginUser.getAccount());
                rdCustProduct.setSysPerName(loginUser.getName());

                if (param.getSysFuncOpr().equals(RdProposalEnum.CP_FUNC_SUBMIT.getName())) {
                    rdCustProduct.setSysCpStatus(RdProposalEnum.CP_STATUS_WAIT_APPR1.getName());
                    rdCustProduct.setSysCpAppDate(oprDate);
                    rdCustProduct.setSysCpStatus(RdProposalEnum.CP_STATUS_WAIT_APPR1.getName());
                    rdCustProduct.setSysCpAppPerCode(loginUser.getAccount());
                    rdCustProduct.setSysCpAppPerName(loginUser.getName());

                    QueryWrapper<RdSampleTask> queryTask = new QueryWrapper<>();
                    queryTask.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSampleTask> rdSampleTasks = this.rdSampleTaskMapper.selectList(queryTask);

                    QueryWrapper<RdSamplePa> queryPa = new QueryWrapper<>();
                    queryPa.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSamplePa> rdSamplePas = this.rdSamplePaMapper.selectList(queryPa);

                    QueryWrapper<RdSampleCa> queryCa = new QueryWrapper<>();
                    queryCa.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSampleCa> rdSampleCas = this.rdSampleCaMapper.selectList(queryCa);

                    QueryWrapper<RdMoldOpenPfa> queryPfa = new QueryWrapper<>();
                    queryPfa.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdMoldOpenPfa> rdMoldOpenPfas = this.rdMoldOpenPfaMapper.selectList(queryPfa);

                    QueryWrapper<RdSampleManage> querySm = new QueryWrapper<>();
                    querySm.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSampleManage> rdSampleManages = this.rdSampleManageMapper.selectList(querySm).stream().filter(l -> ObjectUtil.isNotNull(l.getSysKfyFebkIsValid())).collect(Collectors.toList());

                    Optional<RdSampleTask> minTask = rdSampleTasks.stream().min(Comparator.comparing(RdSampleTask::getSysTsRelieaseDate));
                    Optional<RdSampleManage> maxSm = rdSampleManages.stream().max(Comparator.comparing(RdSampleManage::getSysKfySubDate));

                    rdCustProduct.setSysCpStTimes(BigDecimal.valueOf(rdSampleTasks.stream().filter(l -> l.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName())).count()));
                    rdCustProduct.setSysCpCtTimes(BigDecimal.valueOf(rdSampleTasks.stream().filter(l -> l.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName())).count()));
                    rdCustProduct.setSysCpTTotalTimes(BigDecimal.valueOf(rdSampleTasks.size()));

                    BigDecimal sysCpStFeeAmount = BigDecimal.ZERO;
                    BigDecimal sysCpCtFeeAmountCa = BigDecimal.ZERO;
                    BigDecimal sysCpCtFeeAmountPfa = BigDecimal.ZERO;
                    BigDecimal sysCpCtFeeAmount = BigDecimal.ZERO;
                    sysCpStFeeAmount = rdSamplePas.stream().filter(l->ObjectUtil.isNotNull(l.getSysFeeAppPayAmount())).map(l -> l.getSysFeeAppPayAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    sysCpCtFeeAmountCa = rdSampleCas.stream().filter(l->ObjectUtil.isNotNull(l.getSysSaPayAmount())).map(l -> l.getSysSaPayAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    sysCpCtFeeAmountPfa = rdMoldOpenPfas.stream().filter(l->ObjectUtil.isNotNull(l.getSysMofPayAmount())).map(l -> l.getSysMofPayAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    sysCpCtFeeAmount = sysCpCtFeeAmountCa.add(sysCpCtFeeAmountPfa);
                    rdCustProduct.setSysCpStFeeAmount(sysCpStFeeAmount);
                    rdCustProduct.setSysCpCtFeeAmount(sysCpCtFeeAmount);
                    rdCustProduct.setSysCpTotalFeeAmount(sysCpStFeeAmount.add(sysCpCtFeeAmount));

                    rdCustProduct.setSysCpStQty(BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfySource().equals(RdProposalEnum.KFY_SOURCE_XH.getName())).count()));
                    rdCustProduct.setSysCpCtQty(BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfySource().equals(RdProposalEnum.KFY_SOURCE_DZ.getName())).count()));
                    rdCustProduct.setSysCpSTotalQty(BigDecimal.valueOf(rdSampleManages.size()));

                    rdCustProduct.setSysCpIsQty(BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfyFebkIsValid().equals(RdProposalEnum.KFY_FEBK_IS_VALID_NO.getName())).count()));
                    rdCustProduct.setSysCpVsQty(BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfyFebkIsValid().equals(RdProposalEnum.KFY_FEBK_IS_VALID_YES.getName())).count()));

                    rdCustProduct.setSysCpAppQty(BigDecimal.valueOf(param.getRdCustProductDetParams().stream().filter(l -> l.getSysCpIsSelect().equals(RdProposalEnum.CP_IS_SELECT_YES.getName())).count()));

                    int sysCpPuTimes = 0; //提案已用时
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(minTask.get().getSysTsRelieaseDate());
                    startCal.set(Calendar.HOUR_OF_DAY, 0);
                    startCal.set(Calendar.MINUTE, 0);
                    startCal.set(Calendar.SECOND, 0);
                    startCal.set(Calendar.MILLISECOND, 0);
                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(maxSm.isPresent() ? maxSm.get().getSysKfySubDate() : oprDate);
                    endCal.set(Calendar.HOUR_OF_DAY, 0);
                    endCal.set(Calendar.MINUTE, 0);
                    endCal.set(Calendar.SECOND, 0);
                    endCal.set(Calendar.MILLISECOND, 0);
                    sysCpPuTimes = (int) (endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                    rdCustProduct.setSysCpPuTimes(BigDecimal.valueOf(sysCpPuTimes));
                }else {
                    rdCustProduct.setSysCpStatus(RdProposalEnum.CP_STATUS_WAIT_SUBMIT.getName());
                }

                this.mapper.insert(rdCustProduct);

                if (param.getSysFuncOpr().equals(RdProposalEnum.CP_FUNC_SUBMIT.getName())){
                    UpdateWrapper<RdProposal> rdProposalUpdateWrapper = new UpdateWrapper<>();
                    rdProposalUpdateWrapper.eq("SYS_TA_CODE",rdCustProduct.getSysTaCode());
                    rdProposalUpdateWrapper.set("SYS_TA_PROCESS",RdProposalEnum.TA_PROCESS_ORDER.getName());
                    rdProposalUpdateWrapper.set("SYS_TA_SP_DOC",rdCustProduct.getSysCpSpDoc());
                    this.rdProposalMapper.update(null,rdProposalUpdateWrapper);
                }

                param.getRdCustProductDetParams().stream().filter(l -> ObjectUtil.isNotNull(l.getSysCpProductClass())).collect(Collectors.toList()).forEach(l -> {
                    UpdateWrapper<RdSampleManage> rdSampleManageUpdateWrapper = new UpdateWrapper<>();
                    rdSampleManageUpdateWrapper.eq("SYS_KFY_CODE", l.getSysKfyCode());
                    rdSampleManageUpdateWrapper.set("SYS_KFY_PRODUCT_CLASSIFY", l.getSysCpProductClass());
                    this.rdSampleManageMapper.update(null, rdSampleManageUpdateWrapper);

                    if (param.getSysFuncOpr().equals(RdProposalEnum.CP_FUNC_SUBMIT.getName())) {
                            RdCustProductDet rdCustProductDet = BeanUtil.copyProperties(l, RdCustProductDet.class);
                            rdCustProductDet.setId(UUID.randomUUID().toString().replace("-", ""));
                            rdCustProductDet.setSysCpCode(rdCustProduct.getSysCpCode());
                            rdCustProductDet.setSysCpIsCustProduct(RdProposalEnum.CP_IS_CUST_PRODUCT_NO.getName());
                            this.rdCustProductDetMapper.insert(rdCustProductDet);
                    } else {
                        RdCustProductDet rdCustProductDet = BeanUtil.copyProperties(l, RdCustProductDet.class);
                        rdCustProductDet.setId(UUID.randomUUID().toString().replace("-", ""));
                        rdCustProductDet.setSysCpCode(rdCustProduct.getSysCpCode());
                        this.rdCustProductDetMapper.insert(rdCustProductDet);
                    }
                });

                //dyl  detail2
                param.getRdCustProductDet2Params().forEach(l -> {

                        RdCustProductDet2 rdCustProductDet2 = BeanUtil.copyProperties(l, RdCustProductDet2.class);
                        rdCustProductDet2.setId(UUID.randomUUID().toString().replace("-", ""));
                        rdCustProductDet2.setSysCpCode(rdCustProduct.getSysCpCode());
                        this.rdCustProductDet2Mapper.insert(rdCustProductDet2);
                });

                return ResponseData.success("定品申请成功.");
            } else {

                UpdateWrapper<RdCustProduct> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("SYS_CP_CODE", rdCustProduct.getSysCpCode());
                updateWrapper.set("SYS_CP_SP_DOC", ObjectUtil.isNotNull(param.getSysCpSpDoc()) ? param.getSysCpSpDoc() : "");

                if (param.getSysFuncOpr().equals(RdProposalEnum.CP_FUNC_SUBMIT.getName())) {
                    updateWrapper.set("SYS_CP_APP_DATE", oprDate).set("SYS_CP_STATUS", RdProposalEnum.CP_STATUS_WAIT_APPR1.getName())
                            .set("SYS_CP_APP_PER_CODE", loginUser.getAccount()).set("SYS_CP_APP_PER_NAME", loginUser.getName());

                    QueryWrapper<RdSampleTask> queryTask = new QueryWrapper<>();
                    queryTask.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSampleTask> rdSampleTasks = this.rdSampleTaskMapper.selectList(queryTask);

                    QueryWrapper<RdSamplePa> queryPa = new QueryWrapper<>();
                    queryPa.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSamplePa> rdSamplePas = this.rdSamplePaMapper.selectList(queryPa);

                    QueryWrapper<RdSampleCa> queryCa = new QueryWrapper<>();
                    queryCa.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSampleCa> rdSampleCas = this.rdSampleCaMapper.selectList(queryCa);

                    QueryWrapper<RdMoldOpenPfa> queryPfa = new QueryWrapper<>();
                    queryPfa.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdMoldOpenPfa> rdMoldOpenPfas = this.rdMoldOpenPfaMapper.selectList(queryPfa);

                    QueryWrapper<RdSampleManage> querySm = new QueryWrapper<>();
                    querySm.eq("SYS_TA_CODE", rdCustProduct.getSysTaCode());
                    List<RdSampleManage> rdSampleManages = this.rdSampleManageMapper.selectList(querySm).stream().filter(l -> ObjectUtil.isNotNull(l.getSysKfyFebkIsValid())).collect(Collectors.toList());

                    Optional<RdSampleTask> minTask = rdSampleTasks.stream().min(Comparator.comparing(RdSampleTask::getSysTsRelieaseDate));
                    Optional<RdSampleManage> maxSm = rdSampleManages.stream().max(Comparator.comparing(RdSampleManage::getSysKfySubDate));

                    updateWrapper.set("SYS_CP_ST_TIMES", BigDecimal.valueOf(rdSampleTasks.stream().filter(l -> l.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName())).count()))
                            .set("SYS_CP_CT_TIMES", BigDecimal.valueOf(rdSampleTasks.stream().filter(l -> l.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName())).count()))
                            .set("SYS_CP_T_TOTAL_TIMES", BigDecimal.valueOf(rdSampleTasks.size()));

                    BigDecimal sysCpStFeeAmount = BigDecimal.ZERO;
                    BigDecimal sysCpCtFeeAmountCa = BigDecimal.ZERO;
                    BigDecimal sysCpCtFeeAmountPfa = BigDecimal.ZERO;
                    BigDecimal sysCpCtFeeAmount = BigDecimal.ZERO;
                    sysCpStFeeAmount = rdSamplePas.stream().filter(l->ObjectUtil.isNotNull(l.getSysFeeAppPayAmount())).map(l -> l.getSysFeeAppPayAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    sysCpCtFeeAmountCa = rdSampleCas.stream().filter(l->ObjectUtil.isNotNull(l.getSysSaPayAmount())).map(l -> l.getSysSaPayAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    sysCpCtFeeAmountPfa = rdMoldOpenPfas.stream().filter(l->ObjectUtil.isNotNull(l.getSysMofPayAmount())).map(l -> l.getSysMofPayAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    sysCpCtFeeAmount = sysCpCtFeeAmountCa.add(sysCpCtFeeAmountPfa);

                    updateWrapper.set("SYS_CP_ST_FEE_AMOUNT", sysCpStFeeAmount).set("SYS_CP_CT_FEE_AMOUNT", sysCpCtFeeAmount)
                            .set("SYS_CP_TOTAL_FEE_AMOUNT", sysCpStFeeAmount.add(sysCpCtFeeAmount))
                            .set("SYS_CP_ST_QTY", BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfySource().equals(RdProposalEnum.KFY_SOURCE_XH.getName())).count()))
                            .set("SYS_CP_CT_QTY", BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfySource().equals(RdProposalEnum.KFY_SOURCE_DZ.getName())).count()))
                            .set("SYS_CP_S_TOTAL_QTY", BigDecimal.valueOf(rdSampleManages.size()))
                            .set("SYS_CP_IS_QTY", BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfyFebkIsValid().equals(RdProposalEnum.KFY_FEBK_IS_VALID_NO.getName())).count()))
                            .set("SYS_CP_VS_QTY", BigDecimal.valueOf(rdSampleManages.stream().filter(l -> l.getSysKfyFebkIsValid().equals(RdProposalEnum.KFY_FEBK_IS_VALID_YES.getName())).count()))
                            .set("SYS_CP_APP_QTY", BigDecimal.valueOf(param.getRdCustProductDetParams().stream().filter(l -> l.getSysCpIsSelect().equals(RdProposalEnum.CP_IS_SELECT_YES.getName())).count()));

                    int sysCpPuTimes = 0; //提案已用时
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(minTask.get().getSysTsRelieaseDate());
                    startCal.set(Calendar.HOUR_OF_DAY, 0);
                    startCal.set(Calendar.MINUTE, 0);
                    startCal.set(Calendar.SECOND, 0);
                    startCal.set(Calendar.MILLISECOND, 0);
                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(maxSm.get().getSysKfySubDate());
                    endCal.set(Calendar.HOUR_OF_DAY, 0);
                    endCal.set(Calendar.MINUTE, 0);
                    endCal.set(Calendar.SECOND, 0);
                    endCal.set(Calendar.MILLISECOND, 0);
                    sysCpPuTimes = (int) (endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                    updateWrapper.set("SYS_CP_PU_TIMES", BigDecimal.valueOf(sysCpPuTimes));
                } else {
                    updateWrapper.set("SYS_CP_STATUS", RdProposalEnum.CP_STATUS_WAIT_SUBMIT.getName());
                }

                this.mapper.update(null, updateWrapper);

                if (param.getSysFuncOpr().equals(RdProposalEnum.CP_FUNC_SUBMIT.getName())){
                    UpdateWrapper<RdProposal> rdProposalUpdateWrapper = new UpdateWrapper<>();
                    rdProposalUpdateWrapper.eq("SYS_TA_CODE",rdCustProduct.getSysTaCode());
                    rdProposalUpdateWrapper.set("SYS_TA_PROCESS",RdProposalEnum.TA_PROCESS_ORDER.getName());
                    rdProposalUpdateWrapper.set("SYS_TA_SP_DOC",rdCustProduct.getSysCpSpDoc());
                    this.rdProposalMapper.update(null,rdProposalUpdateWrapper);
                }

                QueryWrapper<RdCustProductDet> rdCustProductDetQueryWrapper = new QueryWrapper<>();
                rdCustProductDetQueryWrapper.eq("SYS_CP_CODE", rdCustProduct.getSysCpCode());
                this.rdCustProductDetMapper.delete(rdCustProductDetQueryWrapper);

                param.getRdCustProductDetParams().stream().filter(l -> ObjectUtil.isNotNull(l.getSysCpProductClass())).collect(Collectors.toList()).forEach(l -> {
                    UpdateWrapper<RdSampleManage> rdSampleManageUpdateWrapper = new UpdateWrapper<>();
                    rdSampleManageUpdateWrapper.eq("SYS_KFY_CODE", l.getSysKfyCode());
                    rdSampleManageUpdateWrapper.set("SYS_KFY_PRODUCT_CLASSIFY", l.getSysCpProductClass());
                    this.rdSampleManageMapper.update(null, rdSampleManageUpdateWrapper);

                    if (param.getSysFuncOpr().equals(RdProposalEnum.CP_FUNC_SUBMIT.getName())) {
                            RdCustProductDet rdCustProductDet = BeanUtil.copyProperties(l, RdCustProductDet.class);
                            rdCustProductDet.setId(UUID.randomUUID().toString().replace("-", ""));
                            rdCustProductDet.setSysCpCode(rdCustProduct.getSysCpCode());
                            rdCustProductDet.setSysCpIsCustProduct(RdProposalEnum.CP_IS_CUST_PRODUCT_NO.getName());
                            this.rdCustProductDetMapper.insert(rdCustProductDet);
                    } else {
                        RdCustProductDet rdCustProductDet = BeanUtil.copyProperties(l, RdCustProductDet.class);
                        rdCustProductDet.setId(UUID.randomUUID().toString().replace("-", ""));
                        rdCustProductDet.setSysCpCode(rdCustProduct.getSysCpCode());
                        this.rdCustProductDetMapper.insert(rdCustProductDet);
                    }
                });

                //dyl  detail2
                QueryWrapper<RdCustProductDet2> rdCustProductDet2QueryWrapper = new QueryWrapper<>();
                rdCustProductDet2QueryWrapper.eq("SYS_CP_CODE", rdCustProduct.getSysCpCode());
                this.rdCustProductDet2Mapper.delete(rdCustProductDet2QueryWrapper);

                param.getRdCustProductDet2Params().forEach(l -> {

                    RdCustProductDet2 rdCustProductDet2 = BeanUtil.copyProperties(l, RdCustProductDet2.class);
                    rdCustProductDet2.setId(UUID.randomUUID().toString().replace("-", ""));
                    rdCustProductDet2.setSysCpCode(rdCustProduct.getSysCpCode());
                    this.rdCustProductDet2Mapper.insert(rdCustProductDet2);
                });

                return ResponseData.success("定品申请成功.");

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData custProductAppr(RdCustProductParam param) {
        try {
            Date oprDate = new Date();
            LoginUser loginUser = LoginContext.me().getLoginUser();

            UpdateWrapper<RdCustProduct> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_CP_CODE", param.getSysCpCode());

            updateWrapper.set("SYS_CP_APPR_DATE", oprDate).set("SYS_CP_APPR_EXPLAIN", ObjectUtil.isNotNull(param.getSysCpApprExplain()) ? param.getSysCpApprExplain() : "")
                    .set("SYS_CP_APPR_PER_CODE", loginUser.getAccount()).set("SYS_CP_APPR_PER_NAME", loginUser.getName())
                    .set("SYS_CP_APPR_RESULT", param.getSysCpApprResult());

            param.getRdCustProductDetParams().forEach(l->{
                UpdateWrapper<RdCustProductDet> rdCustProductDetUpdateWrapper = new UpdateWrapper<>();
                rdCustProductDetUpdateWrapper.eq("ID",l.getId());
                rdCustProductDetUpdateWrapper.set("SYS_CP_IS_CUST_PRODUCT",ObjectUtil.isNotNull(l.getSysCpIsCustProduct())?l.getSysCpIsCustProduct():RdProposalEnum.CP_IS_CUST_PRODUCT_YES.getName());

                this.rdCustProductDetMapper.update(null,rdCustProductDetUpdateWrapper);
            });

            updateWrapper.set("SYS_CP_APPR_QTY", BigDecimal.valueOf(param.getRdCustProductDetParams().stream().filter(l->ObjectUtil.isNotNull(l.getSysCpIsCustProduct()) && l.getSysCpIsCustProduct().equals(RdProposalEnum.CP_IS_CUST_PRODUCT_YES.getName())).count()));

            if (param.getSysCpApprResult().equals(RdProposalEnum.CP_APPR_RESULT_ZZKF.getName())) {
                UpdateWrapper<RdProposal> rdProposalUpdateWrapper = new UpdateWrapper<>();
                rdProposalUpdateWrapper.eq("SYS_TA_CODE", param.getSysTaCode());

                rdProposalUpdateWrapper.set("SYS_TA_STATUS", RdProposalEnum.TA_STATE_ARCH.getName()).set("SYS_TA_PROCESS", RdProposalEnum.TA_PROCESS_ARCHIVE.getName())
                        .set("SYS_TA_ARCH_TYPE", RdProposalEnum.TA_ARCH_TYPE_APPR.getName()).set("SYS_TA_ARCH_DATE", oprDate)
                        .set("SYS_TA_ARCH_PER_CODE", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName()).set("SYS_TA_ARCH_PER_NAME", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName());

                if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null, rdProposalUpdateWrapper) > 0) {
                    return ResponseData.success("定品审批成功.");
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("定品审批失败.");
                }
            } else if (param.getSysCpApprResult().equals(RdProposalEnum.CP_APPR_RESULT_TYLD.getName())) {

                updateWrapper.set("SYS_CP_STATUS",RdProposalEnum.CP_STATUS_WAIT_APPR2.getName());

                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("定品审批成功.");
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("定品审批失败.");
                }
            } else {

                UpdateWrapper<RdProposal> rdProposalUpdateWrapper = new UpdateWrapper<>();
                rdProposalUpdateWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
                rdProposalUpdateWrapper.set("SYS_TA_PROCESS", RdProposalEnum.TA_PROCESS_DESIGN.getName());

                updateWrapper.set("SYS_CP_STATUS", RdProposalEnum.CP_STATUS_APPR.getName());

                if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null, rdProposalUpdateWrapper) > 0) {
                    return ResponseData.success("定品审批成功.");
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("定品审批失败.");
                }
            }
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData custProductAppr2(RdCustProductParam param) {

        Date oprDate = new Date();
        LoginUser loginUser = LoginContext.me().getLoginUser();

        UpdateWrapper<RdCustProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SYS_CP_CODE", param.getSysCpCode());

        updateWrapper.set("SYS_CP_APPR_DATE2", oprDate).set("SYS_CP_APPR_EXPLAIN2", ObjectUtil.isNotNull(param.getSysCpApprExplain2()) ? param.getSysCpApprExplain2() : "")
                .set("SYS_CP_APPR_PER_CODE2", loginUser.getAccount()).set("SYS_CP_APPR_PER_NAME2", loginUser.getName())
                .set("SYS_CP_APPR_RESULT2", param.getSysCpApprResult2());

        if (param.getSysCpApprResult2().equals(RdProposalEnum.CP_APPR_RESULT_ZZKF.getName())) {
            UpdateWrapper<RdProposal> rdProposalUpdateWrapper = new UpdateWrapper<>();
            rdProposalUpdateWrapper.eq("SYS_TA_CODE", param.getSysTaCode());

            rdProposalUpdateWrapper.set("SYS_TA_STATUS", RdProposalEnum.TA_STATE_ARCH.getName()).set("SYS_TA_PROCESS", RdProposalEnum.TA_PROCESS_ARCHIVE.getName())
                    .set("SYS_TA_ARCH_TYPE", RdProposalEnum.TA_ARCH_TYPE_APPR.getName()).set("SYS_TA_ARCH_DATE", oprDate)
                    .set("SYS_TA_ARCH_PER_CODE", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName()).set("SYS_TA_ARCH_PER_NAME", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName());

            if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null, rdProposalUpdateWrapper) > 0) {
                return ResponseData.success("定品审批成功.");
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("定品审批失败.");
            }
        } else if (param.getSysCpApprResult2().equals(RdProposalEnum.CP_APPR_RESULT_TYLD.getName())) {

            updateWrapper.set("SYS_CP_STATUS", RdProposalEnum.CP_STATUS_APPR.getName());

            UpdateWrapper<RdProposal> rdProposalUpdateWrapper = new UpdateWrapper<>();
            rdProposalUpdateWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
            rdProposalUpdateWrapper.set("SYS_TA_STATUS", RdProposalEnum.TA_STATE_ARCH.getName()).set("SYS_TA_PROCESS", RdProposalEnum.TA_PROCESS_ARCHIVE.getName())
                    .set("SYS_TA_ARCH_TYPE", RdProposalEnum.TA_ARCH_TYPE_APPR.getName()).set("SYS_TA_ARCH_DATE", oprDate)
                    .set("SYS_TA_ARCH_PER_CODE", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName()).set("SYS_TA_ARCH_PER_NAME", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName());

            //更新spu版本状态
            QueryWrapper<RdProposal>   proposalQueryWrapper = new QueryWrapper<>();
            rdProposalUpdateWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
            List<RdProposal>  proposalList= this.rdProposalMapper.selectList(proposalQueryWrapper);

            if(proposalList.size()>0){
                UpdateWrapper<RdPssManageVersion> rdPssManageVersionUpdateWrapper = new UpdateWrapper<>();
                rdPssManageVersionUpdateWrapper.eq("SYS_SPU", proposalList.get(0).getSysSpu()).eq("SYS_VERSION_STATUS","进行中")
                        .set("SYS_VERSION_STATUS","已落地");

                this.rdPssManageVersionMapper.update(null,rdPssManageVersionUpdateWrapper);
            }

            // todo 待补充 落地阶段 数据

            if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null, rdProposalUpdateWrapper) > 0) {
                return ResponseData.success("定品审批成功.");
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("定品审批失败.");
            }

        } else {

            //提案修改为设计中
            UpdateWrapper<RdProposal> rdProposalUpdateWrapper = new UpdateWrapper<>();
            rdProposalUpdateWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
            rdProposalUpdateWrapper.set("SYS_TA_PROCESS", RdProposalEnum.TA_PROCESS_DESIGN.getName());

            updateWrapper.set("SYS_CP_STATUS", RdProposalEnum.CP_STATUS_APPR.getName());

            if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null, rdProposalUpdateWrapper) > 0) {
                return ResponseData.success("定品审批成功.");
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("定品审批失败.");
            }
        }
    }
}
