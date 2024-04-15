package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgPostCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgPostCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgPostCompanyResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgPostCompanyMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgPostCompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 发货公司 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgPostCompanyServiceImpl extends ServiceImpl<TgPostCompanyMapper, TgPostCompany> implements ITgPostCompanyService {

    @Resource
    private TgPostCompanyMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgPostCompanyParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgPostCompanyParam param) {
        LambdaQueryWrapper<TgPostCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgPostCompany :: getCompanyNameCn, param.getCompanyNameCn());
        if(this.count(queryWrapper) > 0){
            return ResponseData.error("已存在此公司信息，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgPostCompany insertEntity = new TgPostCompany();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgPostCompanyParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgPostCompanyParam param) {
        TgPostCompany updateEntity = new TgPostCompany();
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setId(param.getId());
        updateEntity.setContactInfo(param.getContactInfo());
        updateEntity.setFax(param.getFax());
        updateEntity.setContactUser(param.getContactUser());
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        return this.updateById(updateEntity) ? ResponseData.success() : ResponseData.error("编辑失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgPostCompanyResult> export(TgPostCompanyParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> postCompanyNameSelect() {
        return mapper.postCompanyNameSelect();
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgPostCompany> postCompanySelect() {
        return this.list();
    }
}
