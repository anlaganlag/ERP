package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsPriceCoeffRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsPriceCoeffRuleParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsPriceCoeffRuleMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsPriceCoeffRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 清关价格折算 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgCustomsPriceCoeffRuleServiceImpl extends ServiceImpl<TgCustomsPriceCoeffRuleMapper, TgCustomsPriceCoeffRule> implements ITgCustomsPriceCoeffRuleService {

    @Resource
    private TgCustomsPriceCoeffRuleMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsPriceCoeffRuleParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgCustomsPriceCoeffRuleParam param) {
        if(StringUtils.isBlank(param.getIntervalType())){
            return ResponseData.error("区间类型参数必传，新增失败！");
        }
        if ("区间".equals(param.getIntervalType())){
            if(param.getMaxCustomsRate().compareTo(param.getMinCustomsRate()) < 0){
                return ResponseData.error("关税率最大值必须大于等于关税率最小值，新增失败！");
            }
            if(param.getMaxCustomsRate().compareTo(param.getMinCustomsRate()) == 0
                    && (param.getMinEq().equals("0") || param.getMaxEq().equals("0"))){
                return ResponseData.error("关税率最大值必须大于等于关税率最小值，新增失败！");
            }
        }

        List<TgCustomsPriceCoeffRule> containVal = mapper.selectContainVal(param);
        if(CollectionUtil.isNotEmpty(containVal)){
            return ResponseData.error("已存在此清关价格折算范围值，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsPriceCoeffRule insertEntity = new TgCustomsPriceCoeffRule();
        insertEntity.setCreateUser(name);
        BeanUtils.copyProperties(param, insertEntity);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgCustomsPriceCoeffRuleParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgCustomsPriceCoeffRuleParam param) {
        if(StringUtils.isBlank(param.getIntervalType())){
            return ResponseData.error("区间类型参数必传，编辑失败！");
        }
        if ("区间".equals(param.getIntervalType())){
            if(param.getMaxCustomsRate().compareTo(param.getMinCustomsRate()) < 0){
                return ResponseData.error("关税率最大值必须大于等于关税率最小值，编辑失败！");
            }
            if(param.getMaxCustomsRate().compareTo(param.getMinCustomsRate()) == 0
                    && (param.getMinEq().equals("0") || param.getMaxEq().equals("0"))){
                return ResponseData.error("关税率最大值必须大于等于关税率最小值，编辑失败！");
            }
        }

        List<TgCustomsPriceCoeffRule> containVal = mapper.selectContainVal(param);
        if(CollectionUtil.isNotEmpty(containVal)){
            return ResponseData.error("已存在此清关价格折算范围值，编辑失败！");
        }

        TgCustomsPriceCoeffRule updateEntity = new TgCustomsPriceCoeffRule();
        BeanUtils.copyProperties(param, updateEntity);
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        return this.updateById(updateEntity) ? ResponseData.success() : ResponseData.error("编辑失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public TgCustomsPriceCoeffRule queryPriceCoeff(TgCustomsPriceCoeffRuleParam param) {
        return mapper.queryPriceCoeff(param);
    }
}
