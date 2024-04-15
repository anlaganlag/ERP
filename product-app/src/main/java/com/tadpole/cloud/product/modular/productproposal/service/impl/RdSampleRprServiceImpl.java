package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleRpr;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleRprParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleRprResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleRprMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleRprService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提案-退货款记录 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
public class RdSampleRprServiceImpl extends ServiceImpl<RdSampleRprMapper, RdSampleRpr> implements IRdSampleRprService {

    @Resource
    private RdSampleRprMapper mapper;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdSampleRprResult> listSampleRpr(RdSampleRprParam param){
        return this.mapper.listSampleRpr(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData add(List<RdSampleRprParam> params){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();
        HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();
        List<RdSampleRpr> rdSampleRprs = new ArrayList<>();

        for (RdSampleRprParam param: params) {
            RdSampleRpr rdSampleRpr = BeanUtil.copyProperties(param,RdSampleRpr.class);
            rdSampleRpr.setSysRefCode(generatorNoUtil.getRprBillNoExtents("0000","Ref-TH",4));
            rdSampleRpr.setSysCDate(oprDate);
            rdSampleRpr.setSysLDate(oprDate);
            rdSampleRpr.setSysPerCode(loginUser.getAccount());
            rdSampleRpr.setSysPerName(loginUser.getName());
            rdSampleRpr.setSysDeptCode(hrm.getIdall());
            rdSampleRpr.setSysDeptName(hrm.getDepartmentname());

            rdSampleRprs.add(rdSampleRpr);
        }

        if (this.saveBatch(rdSampleRprs)){
            return ResponseData.success();
        }else {
            return ResponseData.error("");
        }
    }

}
