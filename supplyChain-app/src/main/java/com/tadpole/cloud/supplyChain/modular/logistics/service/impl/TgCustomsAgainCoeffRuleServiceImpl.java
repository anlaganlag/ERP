package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsAgainCoeffRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsAgainCoeffRuleParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsAgainCoeffRuleMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsAgainCoeffRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 清关二次折算 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgCustomsAgainCoeffRuleServiceImpl extends ServiceImpl<TgCustomsAgainCoeffRuleMapper, TgCustomsAgainCoeffRule> implements ITgCustomsAgainCoeffRuleService {

    @Resource
    private TgCustomsAgainCoeffRuleMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsAgainCoeffRuleParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgCustomsAgainCoeffRuleParam param) {
        if(StringUtils.isBlank(param.getIntervalType())){
            return ResponseData.error("区间类型参数必传，新增失败！");
        }
        if ("区间".equals(param.getIntervalType())){
            if(param.getMaxCustomsPrice().compareTo(param.getMinCustomsPrice()) < 0){
                return ResponseData.error("清关单价最大值必须大于等于清关单价最小值，新增失败！");
            }
            if(param.getMaxCustomsPrice().compareTo(param.getMinCustomsPrice()) == 0
                    && (param.getMinEq().equals("0") || param.getMaxEq().equals("0"))){
                return ResponseData.error("清关单价最大值必须大于等于清关单价最小值，新增失败！");
            }
        }

        List<TgCustomsAgainCoeffRule> containVal = mapper.selectContainVal(param);
        if(CollectionUtil.isNotEmpty(containVal)){
            return ResponseData.error("已存在此清关二次折算范围值，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsAgainCoeffRule insertEntity = new TgCustomsAgainCoeffRule();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgCustomsAgainCoeffRuleParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgCustomsAgainCoeffRuleParam param) {
        if(StringUtils.isBlank(param.getIntervalType())){
            return ResponseData.error("区间类型参数必传，编辑失败！");
        }
        if ("区间".equals(param.getIntervalType())){
            if(param.getMaxCustomsPrice().compareTo(param.getMinCustomsPrice()) < 0){
                return ResponseData.error("清关单价最大值必须大于等于清关单价最小值，编辑失败！");
            }
            if(param.getMaxCustomsPrice().compareTo(param.getMinCustomsPrice()) == 0
                    && (param.getMinEq().equals("0") || param.getMaxEq().equals("0"))){
                return ResponseData.error("清关单价最大值必须大于等于清关单价最小值，编辑失败！");
            }
        }

        List<TgCustomsAgainCoeffRule> containVal = mapper.selectContainVal(param);
        if(CollectionUtil.isNotEmpty(containVal)){
            return ResponseData.error("已存在此清关二次折算范围值，编辑失败！");
        }

        TgCustomsAgainCoeffRule updateEntity = new TgCustomsAgainCoeffRule();
        BeanUtils.copyProperties(param, updateEntity);
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        return this.updateById(updateEntity) ? ResponseData.success() : ResponseData.error("编辑失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public TgCustomsAgainCoeffRule queryAgainCoeff(TgCustomsAgainCoeffRuleParam param) {
        return mapper.queryAgainCoeff(param);
    }
}
