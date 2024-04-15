package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdSdSetting;
import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSdSettingParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSettingUpRecordParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdTlSettingParam;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSdSettingMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSdSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import com.tadpole.cloud.product.modular.productproposal.service.IRdSettingUpRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 提案-设置-拿样任务时长设置 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Service
public class RdSdSettingServiceImpl extends ServiceImpl<RdSdSettingMapper, RdSdSetting> implements IRdSdSettingService {

    @Resource
    private RdSdSettingMapper mapper;

    @Resource
    private IRdSettingUpRecordService upRecordService;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdSdSetting> listPage(){
        Wrapper<RdSdSetting> queryWrapper =new QueryWrapper<>();
        return this.mapper.selectList(null);
    };

    @DataSource(name = "product")
    @Override
    @Transactional
    public void save(List<RdSdSettingParam> params) {

        DateTime oprDate = DateUtil.date();

        params.forEach(l -> {
            List<RdSettingUpRecordParam> listParams = new ArrayList<>();

            boolean isChange = false;
            RdSdSetting rdSdSetting = this.mapper.selectById(l.getId());
            RdSettingUpRecordParam param = null;
            if (rdSdSetting.getSysFebkDuration().compareTo(l.getSysFebkDuration()) != 0){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("拿样任务时长设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("反馈时长(天)");
                param.setSysModifyRecordOrgValue(rdSdSetting.getSysFebkDuration().toString());
                param.setSysModifyRecordNewValue(l.getSysFebkDuration().toString());

                listParams.add(param);

                isChange = true;
            }

            if (rdSdSetting.getSysSamplingDuration().compareTo(l.getSysSamplingDuration()) != 0){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("拿样任务时长设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("拿样时长(天)");
                param.setSysModifyRecordOrgValue(rdSdSetting.getSysSamplingDuration().toString());
                param.setSysModifyRecordNewValue(l.getSysSamplingDuration().toString());

                listParams.add(param);

                isChange = true;
            }

            if(listParams.size() > 0){
                upRecordService.batchAdd(listParams);
            }

            if(isChange){
                RdSdSetting model = new RdSdSetting();
                BeanUtils.copyProperties(l, model);
                model.setSysLDate(oprDate);
                model.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                model.setSysPerName(LoginContext.me().getLoginUser().getName());
                this.baseMapper.updateById(model);
            }

        });
    }
}
