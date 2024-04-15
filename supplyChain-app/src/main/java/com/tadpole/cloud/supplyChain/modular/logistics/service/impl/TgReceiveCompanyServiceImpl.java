package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgReceiveCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgReceiveCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgReceiveCompanyResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgReceiveCompanyMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgReceiveCompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收货公司 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgReceiveCompanyServiceImpl extends ServiceImpl<TgReceiveCompanyMapper, TgReceiveCompany> implements ITgReceiveCompanyService {

    @Resource
    private TgReceiveCompanyMapper mapper;
    @Autowired
    private ITgReceiveCompanyService tgReceiveCompanyService;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgReceiveCompanyParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgReceiveCompanyParam param) {
        LambdaQueryWrapper<TgReceiveCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgReceiveCompany :: getCompanyNameCn, param.getCompanyNameCn());
        if(this.count(queryWrapper) > 0){
            return ResponseData.error("已存在此公司信息，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgReceiveCompany insertEntity = new TgReceiveCompany();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgReceiveCompanyParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgReceiveCompanyParam param) {
        LambdaQueryWrapper<TgReceiveCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgReceiveCompany :: getCompanyNameCn, param.getCompanyNameCn());
        TgReceiveCompany receiveCompany = this.getOne(queryWrapper);
        if(receiveCompany != null && !param.getId().equals(receiveCompany.getId())){
            return ResponseData.error("已存在此公司信息，新增失败！");
        }

        TgReceiveCompany updateEntity = new TgReceiveCompany();
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setId(param.getId());
        updateEntity.setAddrCn(param.getAddrCn());
        updateEntity.setContactUser(param.getContactUser());
        updateEntity.setEmail(param.getEmail());
        updateEntity.setPhone(param.getPhone());
        updateEntity.setCountryCode(param.getCountryCode());
        updateEntity.setState(param.getState());
        updateEntity.setCity(param.getCity());
        updateEntity.setLogRecZip(param.getLogRecZip());
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        return this.updateById(updateEntity) ? ResponseData.success() : ResponseData.error("编辑失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData getReceiveCompany() {
        try {
            String name = LoginContext.me().getLoginUser().getName();
            List<TgReceiveCompany> ebmsReceiveCompanyList = tgReceiveCompanyService.getEbmsReceiveCompany();
            for (TgReceiveCompany ebmsReceiveCompany : ebmsReceiveCompanyList) {
                LambdaQueryWrapper<TgReceiveCompany> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(TgReceiveCompany :: getCompanyNameCn, ebmsReceiveCompany.getCompanyNameCn());
                TgReceiveCompany receiveCompany = tgReceiveCompanyService.getOne(queryWrapper);
                if(receiveCompany != null){
                    LambdaUpdateWrapper<TgReceiveCompany> upperWrapper = new LambdaUpdateWrapper<>();
                    upperWrapper.eq(TgReceiveCompany :: getId, receiveCompany.getId())
                            .set(StringUtils.isNotBlank(ebmsReceiveCompany.getAddrCn()), TgReceiveCompany :: getAddrCn, ebmsReceiveCompany.getAddrCn())
                            .set(StringUtils.isNotBlank(ebmsReceiveCompany.getContactUser()), TgReceiveCompany :: getContactUser, ebmsReceiveCompany.getContactUser())
                            .set(StringUtils.isNotBlank(ebmsReceiveCompany.getPhone()), TgReceiveCompany :: getPhone, ebmsReceiveCompany.getPhone())
                            .set(StringUtils.isNotBlank(ebmsReceiveCompany.getCountryCode()), TgReceiveCompany :: getCountryCode, ebmsReceiveCompany.getCountryCode())
                            .set(StringUtils.isNotBlank(ebmsReceiveCompany.getState()), TgReceiveCompany :: getState, ebmsReceiveCompany.getState())
                            .set(StringUtils.isNotBlank(ebmsReceiveCompany.getCity()), TgReceiveCompany :: getCity, ebmsReceiveCompany.getCity())
                            .set(StringUtils.isNotBlank(ebmsReceiveCompany.getLogRecZip()), TgReceiveCompany :: getLogRecZip, ebmsReceiveCompany.getLogRecZip())
                            .set(TgReceiveCompany :: getUpdateUser, name)
                            .set(TgReceiveCompany :: getUpdateTime, DateUtil.date());
                    tgReceiveCompanyService.update(upperWrapper);
                } else {
                    ebmsReceiveCompany.setCreateUser(name);
                    tgReceiveCompanyService.save(ebmsReceiveCompany);
                }
            }
            return ResponseData.success();
        } catch (Exception e){
            log.error("获取收货公司异常:[{}]", e);
            return ResponseData.error("获取收货公司异常");
        }
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public TgReceiveCompany getOne(Wrapper<TgReceiveCompany> queryWrapper) {
        return this.getOne(queryWrapper, true);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public boolean save(TgReceiveCompany entity) {
        return SqlHelper.retBool(this.getBaseMapper().insert(entity));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public boolean update(Wrapper<TgReceiveCompany> updateWrapper) {
        return this.update((TgReceiveCompany)null, updateWrapper);
    }

    @Override
    @DataSource(name = "EBMS")
    public List<TgReceiveCompany> getEbmsReceiveCompany() {
        return mapper.getEbmsReceiveCompany();
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgReceiveCompanyResult> export(TgReceiveCompanyParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> receiveCompanyNameSelect() {
        return mapper.receiveCompanyNameSelect();
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgReceiveCompany> receiveCompanySelect() {
        return this.list();
    }
}
