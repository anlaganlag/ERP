package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierAccountInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.mapper.SupplierAccountInfoMapper;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierAccountInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierAccountInfoResult;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierAccountInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 供应商-供应商信息 服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Service
public class SupplierAccountInfoServiceImpl extends ServiceImpl<SupplierAccountInfoMapper, SupplierAccountInfo> implements ISupplierAccountInfoService {

    @Resource
    private SupplierAccountInfoMapper mapper;

    @DataSource(name ="product")
    @Override
    public List<SupplierAccountInfoResult> listPage(SupplierAccountInfoParam param) {
        return null;
    }

    @DataSource(name ="product")
    @Override
    public void edit(SupplierAccountInfoParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        SupplierAccountInfo accountInfo = BeanUtil.copyProperties(param,SupplierAccountInfo.class);
        accountInfo.setSysLDate(new Date());
        accountInfo.setSysPerName(loginUser.getName());
        mapper.updateById(accountInfo);
    }
}
