package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsPriceMinRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsPriceMinRuleParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsPriceMinRuleMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsPriceMinRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 最低清关价格 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgCustomsPriceMinRuleServiceImpl extends ServiceImpl<TgCustomsPriceMinRuleMapper, TgCustomsPriceMinRule> implements ITgCustomsPriceMinRuleService {

    @Resource
    private TgCustomsPriceMinRuleMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsPriceMinRuleParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgCustomsPriceMinRuleParam param) {
        LambdaQueryWrapper<TgCustomsPriceMinRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgCustomsPriceMinRule :: getCountryCode, param.getCountryCode());
        if(this.count(queryWrapper) > 0){
            return ResponseData.error("已存在此国家信息，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsPriceMinRule insertEntity = new TgCustomsPriceMinRule();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgCustomsPriceMinRuleParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgCustomsPriceMinRuleParam param) {
        TgCustomsPriceMinRule updateEntity = new TgCustomsPriceMinRule();
        BeanUtils.copyProperties(param, updateEntity);
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        return this.updateById(updateEntity) ? ResponseData.success() : ResponseData.error("编辑失败！");
    }
}
