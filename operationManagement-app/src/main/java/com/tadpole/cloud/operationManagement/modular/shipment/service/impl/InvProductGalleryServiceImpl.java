package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.InvProductGallery;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.InvProductGalleryMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.InvProductGalleryParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ListingSelectParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ListingSelectResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.InvProductGalleryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* <p>
    *  AmazSKU服务实现类
    * </p>
*
* @author lsy
* @since 2023-02-03
*/
@Service
@Slf4j
public class InvProductGalleryServiceImpl extends ServiceImpl<InvProductGalleryMapper, InvProductGallery> implements InvProductGalleryService {

    @Resource
    private InvProductGalleryMapper mapper;

    @Override
    @DataSource(name = "basicdata")
    public List<InvProductGallery> querySku(InvProductGalleryParam param) {

        LambdaQueryWrapper<InvProductGallery> queryWrapper = new LambdaQueryWrapper<>();
        //部门
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDepartment()),InvProductGallery::getDepartment,param.getDepartment());
        //team
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTeam()),InvProductGallery::getTeam,param.getTeam());
        //区域
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getArea()),InvProductGallery::getArea,param.getArea());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getAreaList()),InvProductGallery::getArea,param.getAreaList());
        //账号
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysShopsName()),InvProductGallery::getSysShopsName,param.getSysShopsName());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getSysShopsNameList()),InvProductGallery::getSysShopsName,param.getSysShopsNameList());
        //站点
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysSite()),InvProductGallery::getSysSite,param.getSysSite());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getSysSiteList()),InvProductGallery::getSysSite,param.getSysSiteList());
        //ASIN
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAsin()),InvProductGallery::getAsin,param.getAsin());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getAsinList()),InvProductGallery::getAsin,param.getAsinList());
        //物料编码
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMaterialCode()),InvProductGallery::getMaterialCode,param.getMaterialCode());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getMaterialCodeList()),InvProductGallery::getMaterialCode,param.getMaterialCodeList());
        //SKU
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSku()),InvProductGallery::getSku,param.getSku());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getSkuList()),InvProductGallery::getSku,param.getSkuList());

        //默认情况下支持FA模式，且状态为 ‘正常销售’,‘清货物料’,'清货SKU'
        queryWrapper.eq(InvProductGallery::getSysLogiMode, "FA");
        queryWrapper.in(InvProductGallery::getSysLabelType, "正常销售","清货物料","清货SKU");

        //默认情况下不加载AU,SG,NL,PL,SE,TR,BE站点的SKU
        queryWrapper.notIn(InvProductGallery::getSysSite, "AU", "SG", "NL", "PL", "SE", "TR", "BE");

        return  mapper.selectList(queryWrapper);


    }

    @Override
    @DataSource(name = "basicdata")
    public List<InvProductGallery> querySkuDatalimit(InvProductGalleryParam param) {
        return  mapper.querySkuDatalimit(param);
    }

    @Override
    @DataSource(name = "basicdata")
    public ListingSelectResult listingSelect(ListingSelectParam param) {

        InvProductGalleryParam productGalleryParam = new InvProductGalleryParam();
        BeanUtil.copyProperties(param,productGalleryParam);

        List<InvProductGallery> productGalleryList = this.querySkuDatalimit(productGalleryParam);
        if (ObjectUtil.isEmpty(productGalleryList)) {
            return null;
        }

        ListingSelectResult selectResult = new ListingSelectResult();

        Set<String> skuSet = productGalleryList.stream().map(p -> p.getSku()).collect(Collectors.toSet());
        Set<String> asinSet = productGalleryList.stream().map(p -> p.getAsin()).collect(Collectors.toSet());
        Set<String> materialCodeSet = productGalleryList.stream().map(p -> p.getMaterialCode()).collect(Collectors.toSet());
        Set<String> siteSet = productGalleryList.stream().map(p -> p.getSysSite()).collect(Collectors.toSet());
        Set<String> sysShopsNameSet = productGalleryList.stream().map(p -> p.getSysShopsName()).collect(Collectors.toSet());

        selectResult.setSkuList(skuSet);
        selectResult.setAsinList(asinSet);
        selectResult.setMaterialCodeList(materialCodeSet);
        selectResult.setSysSiteList(siteSet);
        selectResult.setSysShopsNameList(sysShopsNameSet);
        return selectResult;
    }

    @Override
    public LoginUser getLoginUserInfo() {
        //查询当前登录人，所申请的
        LoginContext current = SpringContext.getBean(LoginContext.class);
        if (ObjectUtil.isNull(current)) {
            return null;
        }
        LoginUser currentUser = current.getLoginUser();
        return currentUser;
    }
}
