package com.tadpole.cloud.operationManagement.modular.brand.service.impl;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademarkRegis;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademarkRegisProgress;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandTrademarkRegisProgressMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandTrademarkRegisProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbTrademarkCertificateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.*;
import org.springframework.beans.BeanUtils;
/**
* <p>
* 品牌商标注册进度表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-24
*/
@Service
public class TbBrandTrademarkRegisProgressServiceImpl extends ServiceImpl<TbBrandTrademarkRegisProgressMapper, TbBrandTrademarkRegisProgress> implements TbBrandTrademarkRegisProgressService {

    @Autowired
    TbTrademarkCertificateService trademarkCertificateService;

    @DataSource(name = "stocking")
    @Override
    public TbBrandTrademarkRegisProgressResult queryById(Long id) {
        TbBrandTrademarkRegisProgress entity = this.baseMapper.selectById(id);
        TbBrandTrademarkRegisProgressResult result = new TbBrandTrademarkRegisProgressResult();
        if (entity != null) {
            BeanUtils.copyProperties(entity,result);
            if (StringUtils.isNotEmpty(entity.getRemark())){
                result.setRemarks(JSONUtil.toList(entity.getRemark(),ProgressRemarkResult.class));
            }

        }
        return result;
    }

    @DataSource(name = "stocking")
    @Override
    public void save(TbBrandTrademarkRegisProgressParam param) {
        TbBrandTrademarkRegisProgress entity = new TbBrandTrademarkRegisProgress();
        BeanUtils.copyProperties(param, entity);
        if (param.getRemarks()!=null &&param.getRemarks().size()>0){
            entity.setRemark(JSONUtil.toJsonStr(param.getRemarks()));
        }
        //edit
        if (entity.getId() != null) {
            entity.setUpdateName(LoginContext.me().getLoginUser().getName());
            entity.setUpdateTime(DateUtil.date());
            this.baseMapper.updateById(entity);

        } else {
            entity.setCreateName(LoginContext.me().getLoginUser().getName());
            this.baseMapper.insert(entity);

        }
        //已下证
        if (param.getTrademarkStatus()==4){
            TbTrademarkCertificateParam tc =new TbTrademarkCertificateParam();
            //tc.setId(param.getId());
            tc.setTradeName(param.getTradeName());
            tc.setTrademarkType(param.getTrademarkType());
            tc.setRegisterCountry(param.getRegisterCountry());
            tc.setTrademarkCategory(param.getTrademarkCategory());
            tc.setTrademarkSubClass(param.getTrademarkSubClass());
            tc.setTrademarkOwner(param.getTrademarkOwner());
            tc.setCustodyDepartment(param.getCustodyDepartment());
            tc.setRegisterNumber(param.getRegisterNumber());
            tc.setTrademarkValidityTermEnd(param.getTrademarkValidityTermStart());
            tc.setTrademarkValidityTermStart(param.getTrademarkValidityTermEnd());
            tc.setRenewalValidityTrademarkStart(param.getTrademarkValidityTermStart());
            tc.setRenewalValidityTrademarkEnd(param.getTrademarkValidityTermEnd());
            trademarkCertificateService.save(tc);
        }
    }
}

