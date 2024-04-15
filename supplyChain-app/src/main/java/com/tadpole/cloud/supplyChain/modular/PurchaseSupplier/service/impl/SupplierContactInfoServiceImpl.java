package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierAccountInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierContactInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.mapper.SupplierContactInfoMapper;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierAccountInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierContactInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierContactInfoResult;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierContactInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 供应商-联系人信息 服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Service
public class SupplierContactInfoServiceImpl extends ServiceImpl<SupplierContactInfoMapper, SupplierContactInfo> implements ISupplierContactInfoService {

    @Resource
    private SupplierContactInfoMapper mapper;

    @DataSource(name ="product")
    @Override
    public List<SupplierContactInfo> listPage(SupplierContactInfoParam param) {

        QueryWrapper<SupplierContactInfo>  queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("SYS_SUP_CODE",param.getSysSupCode());

        return this.list(queryWrapper);
    }

    @DataSource(name ="product")
    @Override
    public void edit(SupplierContactInfoParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        SupplierContactInfo contactInfo = BeanUtil.copyProperties(param,SupplierContactInfo.class);
        contactInfo.setSysLDate(new Date());
        contactInfo.setSysPerCode(loginUser.getAccount());
        contactInfo.setSysPerName(loginUser.getName());
        contactInfo.setSysDeptCode(loginUser.getDepartment());
        contactInfo.setSysDeptName(loginUser.getTeam());
        mapper.updateById(contactInfo);
    }

    @DataSource(name ="product")
    @Override
    public void add(SupplierContactInfoParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        SupplierContactInfo contactInfo = BeanUtil.copyProperties(param,SupplierContactInfo.class);
        contactInfo.setSysCDate(new Date());
        contactInfo.setSysLDate(new Date());
        contactInfo.setSysPerCode(loginUser.getAccount());
        contactInfo.setSysPerName(loginUser.getName());
        contactInfo.setSysDeptCode(loginUser.getDepartment());
        contactInfo.setSysDeptName(loginUser.getTeam());
        this.save(contactInfo);
    }
}
