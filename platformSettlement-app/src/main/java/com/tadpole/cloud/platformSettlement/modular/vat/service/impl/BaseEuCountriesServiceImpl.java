package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseEuCountries;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.BaseEuCountriesMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseEuCountriesParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseEuCountriesResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseEuCountriesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 基础信息-欧盟国家参数列表 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Service
public class BaseEuCountriesServiceImpl extends ServiceImpl<BaseEuCountriesMapper, BaseEuCountries> implements IBaseEuCountriesService {

    @Resource
    private BaseEuCountriesMapper mapper;

    @Override
    @DataSource(name = "finance")
    public Page<BaseEuCountriesResult> queryListPage(BaseEuCountriesParam param) {
        Page pageContext = param.getPageContext();
        return this.baseMapper.queryListPage(pageContext, param);
    }

    private Boolean isExistsObj(BaseEuCountriesParam param) {
        LambdaQueryWrapper<BaseEuCountries> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotEmpty(param.getCountry())) {
            wrapper.eq(BaseEuCountries::getCountry, param.getCountry());
        }
        if (ObjectUtil.isNotEmpty(param.getCountryEn())) {
            wrapper.eq(BaseEuCountries::getCountryEn, param.getCountryEn());
        }
        if (ObjectUtil.isNotEmpty(param.getCountryCn())) {
            wrapper.eq(BaseEuCountries::getCountryCn, param.getCountryCn());
        }
        if (ObjectUtil.isNotEmpty(param.getArea())) {
            wrapper.eq(BaseEuCountries::getArea, param.getArea());
        }
        if(ObjectUtil.isNotEmpty(param.getId())){
            wrapper.ne(BaseEuCountries::getId,param.getId());
        }
        wrapper.eq(BaseEuCountries::getIsDelete, 0);
        return this.baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(BaseEuCountriesParam param) {
        if(isExistsObj(param)) {
            return ResponseData.error("修改失败，原因：待修改的数据已存在,请检查。");
        }

        LambdaUpdateWrapper<BaseEuCountries> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BaseEuCountries::getId, param.getId());
        updateWrapper.set(BaseEuCountries::getArea, param.getArea());
        updateWrapper.set(BaseEuCountries::getCountry, param.getCountry());
        updateWrapper.set(BaseEuCountries::getCountryEn, param.getCountryEn());
        updateWrapper.set(BaseEuCountries::getCountryCn, param.getCountryCn());
        updateWrapper.set(BaseEuCountries::getIsDelete, param.getStatus());
        updateWrapper.set(BaseEuCountries::getUpdateTime, new Date());
        updateWrapper.set(BaseEuCountries::getUpdatePersonNo, LoginContext.me().getLoginUser().getAccount());
        updateWrapper.set(BaseEuCountries::getUpdatePersonName, LoginContext.me().getLoginUser().getAccount());
        if(!this.update(updateWrapper)) {
            return ResponseData.error("修改失败，原因：数据未变更！");
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData save(BaseEuCountriesParam param) {
        if (isExistsObj(param)) {
            return ResponseData.error("新增失败，原因：待新增的数据已存在,请检查。");
        }

        BaseEuCountries entity = BeanUtil.copyProperties(param, BaseEuCountries.class);
        entity.setIsDelete(param.getStatus());
        entity.setCreatePersonNo(LoginContext.me().getLoginUser().getAccount());
        entity.setCreatePersonName(LoginContext.me().getLoginUser().getName());

        if (!this.save(entity)) {
            return ResponseData.error("新增失败，原因：数据未变更。");
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData euCountry() {
        return ResponseData.success(this.baseMapper.euCountry());
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData cnCountry() {
        return ResponseData.success(this.baseMapper.cnCountry());
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData enCountry() {
        return ResponseData.success(this.baseMapper.enCountry());
    }
}
