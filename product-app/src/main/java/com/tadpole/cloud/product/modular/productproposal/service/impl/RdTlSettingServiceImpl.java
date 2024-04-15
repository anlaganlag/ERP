package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSettingUpRecordParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdTlSettingParam;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdTlSettingMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSettingUpRecordService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdTlSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 提案-设置-提案等级审批设置 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Service
public class RdTlSettingServiceImpl extends ServiceImpl<RdTlSettingMapper, RdTlSetting> implements IRdTlSettingService {

    @Resource
    private RdTlSettingMapper mapper;

    @Resource
    private IRdSettingUpRecordService upRecordService;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdTlSetting> listPage(){

        Wrapper<RdTlSetting> queryWrapper = new QueryWrapper<>();

        return this.mapper.selectList(null);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public void save(List<RdTlSettingParam> params) {


        DateTime oprDate = DateUtil.date();

        params.forEach(l -> {
            List<RdSettingUpRecordParam> listParams = new ArrayList<>();
            boolean isChange = false;
            RdTlSetting tlSetting = this.mapper.selectById(l.getId());
            RdSettingUpRecordParam param = null;
            if (tlSetting.getSysFirstOrderPurQty().compareTo(l.getSysFirstOrderPurQty()) != 0){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("提案等级设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("首单采购数量(>= PCS)");
                param.setSysModifyRecordOrgValue(tlSetting.getSysFirstOrderPurQty().toString());
                param.setSysModifyRecordNewValue(l.getSysFirstOrderPurQty().toString());

                listParams.add(param);

                isChange = true;
            }

            if (tlSetting.getSysFirstOrderPurAmount().compareTo(l.getSysFirstOrderPurAmount()) != 0){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("提案等级设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("首单采购金额(>= CNY)");
                param.setSysModifyRecordOrgValue(tlSetting.getSysFirstOrderPurAmount().toString());
                param.setSysModifyRecordNewValue(l.getSysFirstOrderPurAmount().toString());

                listParams.add(param);

                isChange = true;
            }

            if (!tlSetting.getSysIsAutoApprove().equals(l.getSysIsAutoApprove())){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("提案等级设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("自动审批");
                param.setSysModifyRecordOrgValue(tlSetting.getSysIsAutoApprove());
                param.setSysModifyRecordNewValue(l.getSysIsAutoApprove());

                listParams.add(param);

                isChange = true;
            }

            if(listParams.size() > 0){
                upRecordService.batchAdd(listParams);
            }

            if(isChange){
                RdTlSetting model = new RdTlSetting();
                BeanUtils.copyProperties(l, model);
                model.setSysLDate(oprDate);
                model.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                model.setSysPerName(LoginContext.me().getLoginUser().getName());
                this.mapper.updateById(model);
            }

        });
    }
}
