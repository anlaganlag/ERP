package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdFarSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdFarSettingParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSettingUpRecordParam;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdFarSettingMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdFarSettingService;
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
 * 提案-设置-研发费用自动过审设置 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Service
public class RdFarSettingServiceImpl extends ServiceImpl<RdFarSettingMapper, RdFarSetting> implements IRdFarSettingService {

    @Resource
    private RdFarSettingMapper mapper;

    @Resource
    private IRdSettingUpRecordService upRecordService;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdFarSetting> listPage(){
        Wrapper<RdFarSetting> queryWrapper = new QueryWrapper<>();

        return this.mapper.selectList(null);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public void save(List<RdFarSettingParam> params) {

        DateTime oprDate = DateUtil.date();

        params.forEach(l -> {
            List<RdSettingUpRecordParam> listParams = new ArrayList<>();

            boolean isChange = false;
            RdFarSetting rdFarSetting = this.mapper.selectById(l.getId());
            RdSettingUpRecordParam param = null;
            if (rdFarSetting.getSysSingleAppAmount().compareTo(l.getSysSingleAppAmount()) != 0){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("研发费用自动过审设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("单次申请金额(<=元)");
                param.setSysModifyRecordOrgValue(rdFarSetting.getSysSingleAppAmount().toString());
                param.setSysModifyRecordNewValue(l.getSysSingleAppAmount().toString());

                listParams.add(param);

                isChange = true;
            }

            if (rdFarSetting.getSysAppTotalAmount().compareTo(l.getSysAppTotalAmount()) != 0){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("研发费用自动过审设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("累积金额(<=元)");
                param.setSysModifyRecordOrgValue(rdFarSetting.getSysAppTotalAmount().toString());
                param.setSysModifyRecordNewValue(l.getSysAppTotalAmount().toString());

                listParams.add(param);

                isChange = true;
            }

            if (rdFarSetting.getSysTaskQty().compareTo(l.getSysTaskQty()) != 0){
                param = new RdSettingUpRecordParam();
                param.setSysLDate(oprDate);
                param.setSysPerName(LoginContext.me().getLoginUser().getName());
                param.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                param.setSysModifySettingType("研发费用自动过审设置");
                param.setSysModifyRecordNum(l.getId());
                param.setSysModifyRecordParam("任务次数小于");
                param.setSysModifyRecordOrgValue(rdFarSetting.getSysTaskQty().toString());
                param.setSysModifyRecordNewValue(l.getSysTaskQty().toString());

                listParams.add(param);

                isChange = true;
            }

            if(listParams.size() > 0){
                upRecordService.batchAdd(listParams);
            }

            if(isChange){
                RdFarSetting model = new RdFarSetting();
                BeanUtils.copyProperties(l, model);
                model.setSysLDate(oprDate);
                model.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
                model.setSysPerName(LoginContext.me().getLoginUser().getName());
                this.baseMapper.updateById(model);
            }

        });
    }
}
