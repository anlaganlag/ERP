package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgImportCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgImportCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgEbmsCompanyResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgImportCompanyResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgIncludeTaxEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgImportCompanyMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgImportCompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 进口商 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgImportCompanyServiceImpl extends ServiceImpl<TgImportCompanyMapper, TgImportCompany> implements ITgImportCompanyService {

    @Resource
    private TgImportCompanyMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgImportCompanyParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgImportCompanyParam param) {
        LambdaQueryWrapper<TgImportCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgImportCompany :: getCompanyNameEn, param.getCompanyNameEn())
            .eq(TgImportCompany :: getArrivalCountryCode, param.getArrivalCountryCode());
        if(this.count(queryWrapper) > 0){
            return ResponseData.error("已存在此公司信息，新增失败！");
        }
        if(TgIncludeTaxEnum.NO.getCode().equals(param.getIncludeTax())){
            if(StringUtils.isBlank(param.getTaxNum())){
                return ResponseData.error("税号不能为空，新增失败！");
            }
            if(StringUtils.isBlank(param.getCustomsNum())){
                return ResponseData.error("清关号不能为空，新增失败！");
            }
        }
        String name = LoginContext.me().getLoginUser().getName();
        TgImportCompany insertEntity = new TgImportCompany();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgImportCompanyParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgImportCompanyParam param) {
        if(TgIncludeTaxEnum.NO.getCode().equals(param.getIncludeTax())){
            return ResponseData.error("税号不能为空，编辑失败！");
        }
        if(TgIncludeTaxEnum.NO.getCode().equals(param.getIncludeTax())){
            if(StringUtils.isBlank(param.getTaxNum())){
                return ResponseData.error("税号不能为空，新增失败！");
            }
            if(StringUtils.isBlank(param.getCustomsNum())){
                return ResponseData.error("清关号不能为空，新增失败！");
            }
        }
        LambdaQueryWrapper<TgImportCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgImportCompany :: getCompanyNameEn, param.getCompanyNameEn())
                .eq(TgImportCompany :: getArrivalCountryCode, param.getArrivalCountryCode());
        TgImportCompany importCompany = this.getOne(queryWrapper);
        if(importCompany != null && !param.getId().equals(importCompany.getId())){
            return ResponseData.error("已存在此公司信息，新增失败！");
        }

        TgImportCompany updateEntity = new TgImportCompany();
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setId(param.getId());
        updateEntity.setIncludeTax(param.getIncludeTax());
        updateEntity.setArrivalCountryCode(param.getArrivalCountryCode());
        updateEntity.setArrivalCountryName(param.getArrivalCountryName());
        updateEntity.setTaxNum(param.getTaxNum());
        updateEntity.setCustomsNum(param.getCustomsNum());
        updateEntity.setContactInfo(param.getContactInfo());
        updateEntity.setContractNo(param.getContractNo());
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        return this.updateById(updateEntity) ? ResponseData.success() : ResponseData.error("编辑失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgImportCompanyResult> export(TgImportCompanyParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> inCompanyNameSelect() {
        return mapper.inCompanyNameSelect();
    }

    @Override
    @DataSource(name = "stocking")
    public List<TgEbmsCompanyResult> companySelect(String comTaxType) {
        return mapper.companySelect(comTaxType);
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgImportCompany> importCompanySelect() {
        return this.list();
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgImportCompany> importCompanyCountrySelect(TgImportCompanyParam param) {
        LambdaQueryWrapper<TgImportCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgImportCompany :: getCompanyNameEn, param.getCompanyNameEn());
        return this.list(queryWrapper);
    }
}
