package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSettingUpRecordParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdTlSettingParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdProposalExtentResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleApproveResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdFeeReimburseReviewMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdTlSettingMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdFeeReimburseReviewService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSettingUpRecordService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdTlSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-设置-提案等级审批设置 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Service
public class RdFeeReimburseReviewServiceImpl extends ServiceImpl<RdFeeReimburseReviewMapper, RdSampleApproveResult> implements IRdFeeReimburseReviewService {

    @Resource
    private RdFeeReimburseReviewMapper mapper;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleApproveResult> listPage() {
        List<RdSampleApproveResult> rdSampleApproveResults = this.mapper.listPageApprove();

        RdProposalParam param = new RdProposalParam();
        param.setSysTaCodeList(rdSampleApproveResults.stream().map(l->l.getSysTaCode()).collect(Collectors.toList()));

        // 打样申请
        List<RdSampleCaResult> rdSampleCaResults = this.mapper.listCaApprove(param);

        // 开模申请
        List<RdSampleCaResult> rdSampleCaMoResults = this.mapper.listCaMoApprove(param);

        // 购样申请
        List<RdSamplePaResult> rdSamplePaResults = this.mapper.listPaApprove(param);

        for (RdSampleApproveResult r: rdSampleApproveResults) {

            r.setRdSampleCaResults(rdSampleCaResults.stream().filter(l-> l.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_PA.getName()) && l.getSysTaCode().equals(r.getSysTaCode()) && l.getSysAppFeeType().equals(r.getSysAppFeeType())).collect(Collectors.toList()));
            r.setRdSampleCaMoResults(rdSampleCaMoResults.stream().filter(l->l.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_PA.getName()) && l.getSysTaCode().equals(r.getSysTaCode()) && l.getSysAppFeeType().equals(r.getSysAppFeeType())).collect(Collectors.toList()));
            r.setRdSamplePaResults(rdSamplePaResults.stream().filter(l->l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APPR.getName()) && l.getSysTaCode().equals(r.getSysTaCode()) && l.getSysAppFeeType().equals(r.getSysAppFeeType())).collect(Collectors.toList()));

            r.setSysAppCaFee(rdSampleCaResults.stream().filter(l-> ObjectUtil.isNotNull(l.getSysSaPayAmount()) && l.getSysTaCode().equals(r.getSysTaCode())).map(n->n.getSysSaPayAmount()).reduce(BigDecimal.ZERO,BigDecimal::add));
            r.setSysAppCaMoFee(rdSampleCaMoResults.stream().filter(l-> ObjectUtil.isNotNull(l.getSysMofPayAmount()) && l.getSysTaCode().equals(r.getSysTaCode())).map(n->n.getSysMofPayAmount()).reduce(BigDecimal.ZERO,BigDecimal::add));
            r.setSysAppPaFee(rdSamplePaResults.stream().filter(l->ObjectUtil.isNotNull(l.getSysFeeAppPayAmount()) && l.getSysTaCode().equals(r.getSysTaCode())).map(n->n.getSysFeeAppPayAmount()).reduce(BigDecimal.ZERO,BigDecimal::add));

            //待补充
            r.setSysAppJcFee(BigDecimal.ZERO);
            r.setSysAppHcFee(BigDecimal.ZERO);
            r.setSysAppWbFee(BigDecimal.ZERO);

            BigDecimal sysAppTotalFee = r.getSysAppCaFee().add(r.getSysAppCaMoFee()).add(r.getSysAppPaFee()).add(r.getSysAppJcFee()).add(r.getSysAppHcFee()).add(r.getSysAppWbFee());
            r.setSysAppTotalFee(sysAppTotalFee);

        }

        return rdSampleApproveResults;
    }
}
