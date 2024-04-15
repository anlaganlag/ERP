package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageQuery;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.BaseExchangeRateMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseExchangeRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseExchangeRateResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 基础信息-汇率表 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Service
@Slf4j
public class BaseExchangeRateServiceImpl extends ServiceImpl<BaseExchangeRateMapper, BaseExchangeRate> implements IBaseExchangeRateService {

    @Resource
    private BaseExchangeRateMapper mapper;

    @Override
    @DataSource(name = "finance")
    public List<BaseExchangeRateResult> exportExcel(BaseExchangeRateParam param) {
        //设置导出数据为100万；
        PageQuery page = new PageQuery(1_000_000, 1, "ASC", "ID");
        Page pageContext = PageFactory.createPage(page);
        Page<BaseExchangeRateResult> pageResult = this.baseMapper.queryListPage(pageContext, param);
        return pageResult.getRecords();//返回查询结果
    }

    @Override
    @DataSource(name = "finance")
    public Page<BaseExchangeRateResult> queryListPage(BaseExchangeRateParam param) {
        Page pageContext = param.getPageContext();
        return this.baseMapper.queryListPage(pageContext, param);
        //Page<BaseExchangeRateResult> data = this.baseMapper.queryListPage(pageContext, param);
        //return new PageResult<>(data);
    }


//    @Override
//    @DataSource(name = "finance")
//    public Boolean isExistsObj(BaseExchangeRateParam param) {
//        //BeanUtil.copyProperties(param, BaseExchangeRate.class);
//
//        // 方法一
//        //Page pageContext = getPageContext();
//        //IPage<BaseExchangeRateResult> page = this.baseMapper.queryListPage(pageContext, param);
//        //return page.getTotal() > 0;
//
//        //方法二
//        LambdaQueryWrapper<BaseExchangeRate> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtil.isNotEmpty(param.getId())) {
//            wrapper.eq(BaseExchangeRate::getId, param.getId());
//        } else {
//            if (ObjectUtil.isNotEmpty(param.getTargetCurrency())) {
//                wrapper.eq(BaseExchangeRate::getTargetCurrency, param.getTargetCurrency());
//            }
//            if (ObjectUtil.isNotEmpty(param.getOriginalCurrency())) {
//                wrapper.eq(BaseExchangeRate::getOriginalCurrency, param.getOriginalCurrency());
//            }
//        }
//        wrapper.eq(BaseExchangeRate::getIsDelete, 0);
//        return this.baseMapper.selectCount(wrapper) > 0;
//    }


    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData update(BaseExchangeRateParam param, String controllerName) {

        ResponseData responseData = this.validateDataRepeatU(param, controllerName);
        if (!responseData.getSuccess()) {
            return responseData;
        }

        //旧的
        LambdaUpdateWrapper<BaseExchangeRate> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BaseExchangeRate::getId, param.getId());
        updateWrapper.set(BaseExchangeRate::getUpdateTime, new Date());
        updateWrapper.set(BaseExchangeRate::getIsDelete, BigDecimal.valueOf(1));
        updateWrapper.set(BaseExchangeRate::getUpdatePersonNo, LoginContext.me().getLoginUser().getAccount());
        updateWrapper.set(BaseExchangeRate::getUpdatePersonName, LoginContext.me().getLoginUser().getAccount());

        //新的 启用
        BaseExchangeRate oldModel = this.getById(param.getId());
        BaseExchangeRate entity = BeanUtil.copyProperties(param, BaseExchangeRate.class);
        entity.setVersionNumber(oldModel.getVersionNumber().add(BigDecimal.valueOf(1)));
        entity.setCreatePersonNo(LoginContext.me().getLoginUser().getAccount());
        entity.setCreatePersonName(LoginContext.me().getLoginUser().getName());
        entity.setCreateTime(new Date());
        if(this.update(updateWrapper) && this.save(entity)) {
            return ResponseData.success();
        }
        return ResponseData.error(controllerName + "修改失败：数据未变更。");
    }


    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(BaseExchangeRateParam param, String controllerName) {

        ResponseData responseData = this.validateDataRepeat(param, controllerName);
        if (!responseData.getSuccess()) {
            return responseData;
        }

        BaseExchangeRate entity = BeanUtil.copyProperties(param, BaseExchangeRate.class);
        entity.setCreatePersonNo(LoginContext.me().getLoginUser().getAccount());
        entity.setCreatePersonName(LoginContext.me().getLoginUser().getName());
        entity.setVersionNumber(BigDecimal.valueOf(1));
        if (this.save(entity)) {
            return ResponseData.success();
        }
        return ResponseData.error(controllerName + "新增失败，数据未变更。");
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData addBatch(List<BaseExchangeRateParam> params, String controllerName) {

        List<BaseExchangeRate> lists = new ArrayList<>();

        for (BaseExchangeRateParam param : params) {

            ResponseData responseData = this.validateDataRepeat(param, controllerName);

            if (!responseData.getSuccess()) {
                return responseData;
            }

            BaseExchangeRate entity = BeanUtil.copyProperties(param, BaseExchangeRate.class);
            entity.setCreatePersonNo(LoginContext.me().getLoginUser().getAccount());
            entity.setCreatePersonName(LoginContext.me().getLoginUser().getName());
            lists.add(entity);
        }
        if (this.saveBatch(lists)) {
            return ResponseData.success();
        }
        return ResponseData.error(controllerName + "新增失败，数据未变更。");
    }

    @DataSource(name = "finance")
    private ResponseData validateDataRepeat(BaseExchangeRateParam param, String controllerName) {
        //验证 重复   原币币种，目标币种，汇率期间
        LambdaQueryWrapper<BaseExchangeRate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseExchangeRate::getExchangeRatePeriod, param.getExchangeRatePeriod());
        wrapper.eq(BaseExchangeRate::getTargetCurrency, param.getTargetCurrency());
        wrapper.eq(BaseExchangeRate::getOriginalCurrency, param.getOriginalCurrency());
        wrapper.eq(BaseExchangeRate::getIsDelete, 0);
        if (this.baseMapper.selectCount(wrapper) > 0) {
            return ResponseData.error(controllerName + "新增失败，原因：汇率期间[年-月]、原币币种、目标币种 数据存在重复，请确认。");
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    private ResponseData validateDataRepeatU(BaseExchangeRateParam param, String controllerName) {
        //验证 重复   原币币种，目标币种，汇率期间
        LambdaQueryWrapper<BaseExchangeRate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseExchangeRate::getExchangeRatePeriod, param.getExchangeRatePeriod());
        wrapper.eq(BaseExchangeRate::getTargetCurrency, param.getTargetCurrency());
        wrapper.eq(BaseExchangeRate::getOriginalCurrency, param.getOriginalCurrency());
        wrapper.eq(BaseExchangeRate::getIsDelete, 0);
        wrapper.ne(BaseExchangeRate::getId, param.getId());
        if (this.baseMapper.selectCount(wrapper) > 0) {
            return ResponseData.error(controllerName + "修改失败，原因：汇率期间[年-月]、原币币种、目标币种 数据存在重复，请确认。");
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "finance")
    public ResponseData queryOriginalCurrency() {
        return ResponseData.success(this.baseMapper.queryOriginalCurrency());
    }

    @Override
    @DataSource(name = "finance")
    public ResponseData queryTargetCurrency() {
        return ResponseData.success(this.baseMapper.queryTargetCurrency());
    }
}
