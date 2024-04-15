package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockApprovaltimeParameter;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockApprovaltimeParameterMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApprovaltimeParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>
    * 日常备货流程审核节点参数设置 服务实现类
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
@Service
public class StockApprovaltimeParameterServiceImpl extends ServiceImpl<StockApprovaltimeParameterMapper, StockApprovaltimeParameter> implements IStockApprovaltimeParameterService {

    @Resource
    private StockApprovaltimeParameterMapper mapper;



    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StockApprovaltimeParameterResult> findPageBySpec(StockApprovaltimeParameterParam param) {
        List<StockApprovaltimeParameterResult> page = this.baseMapper.customPageList(param);
        return page;
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData update(List<StockApprovaltimeParameterParam> params) {
//        for (StockApprovaltimeParameterParam param : params) {
//            StockApprovaltimeParameter stockApprovaltimeParameter = new StockApprovaltimeParameter();
//            BeanUtils.copyProperties(param, stockApprovaltimeParameter);
//            stockApprovaltimeParameter.setUpdateBy(loginUserInfo.getUserAccount());
//            stockApprovaltimeParameter.setUpdateTime(DateUtil.date());
//            this.baseMapper.updateById(stockApprovaltimeParameter);
//        }

        UpdateWrapper<StockApprovaltimeParameter> updateWrapper = new UpdateWrapper<>();
        for (StockApprovaltimeParameterParam param : params) {
            updateWrapper
                    .eq("auditor_code", param.getAuditorCode())
                    .set("parameter_time", param.getParameterTime())
                    .set("parameter_result", param.getParameterResult())
                    .set("parameter_day", param.getParameterDay())
                    .set("update_by", this.getUserAccount())
                    .set("update_time", DateUtil.date());
            this.baseMapper.update(null, updateWrapper);
            updateWrapper.clear();
        }
        return ResponseData.success();

    }

    @DataSource(name = "stocking")
    @Override
    public StockApprovaltimeParameterResult findByAuditorCode(String auditorCode) {
        List<StockApprovaltimeParameterResult> listParm = this.baseMapper.customPageList(null);
        if (ObjectUtil.isEmpty(listParm)) {
            return null;
        }
        List<StockApprovaltimeParameterResult> auditorCodeResult = listParm.stream().filter(p -> p.getAuditorCode().equals(auditorCode)).collect(Collectors.toList());
        if (ObjectUtil.isEmpty(auditorCodeResult)) {
            return null;
        }
        return auditorCodeResult.get(0);
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
