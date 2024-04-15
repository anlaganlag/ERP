package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAsinBlacklist;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockAsinBlacklistMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockAsinBlacklistParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockAsinBlacklistService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* <p>
    * ASIN黑名单 服务实现类
    * </p>
*
* @author lsy
* @since 2022-12-19
*/
@Service
public class StockAsinBlacklistServiceImpl extends ServiceImpl<StockAsinBlacklistMapper, StockAsinBlacklist> implements IStockAsinBlacklistService {

    @Resource
    private StockAsinBlacklistMapper stockAsinBlacklistMapper;


    @DataSource(name = "stocking")
    @Override
    public PageResult<StockAsinBlacklist> asinBlacklist(StockAsinBlacklistParam param) {
        LambdaQueryWrapper<StockAsinBlacklist> queryWrapper = this.getWrapper(param);
        IPage<StockAsinBlacklist> blacklistIPage = stockAsinBlacklistMapper.selectPage(param.getPageContext(), queryWrapper);
        PageResult<StockAsinBlacklist> pageResult = new PageResult<>(blacklistIPage);
        return pageResult;
    }


    @DataSource(name = "stocking")
    @Override
    public List<StockAsinBlacklist> export(StockAsinBlacklistParam param) {
        LambdaQueryWrapper<StockAsinBlacklist> queryWrapper = this.getWrapper(param);
        List<StockAsinBlacklist> blacklists = stockAsinBlacklistMapper.selectList(queryWrapper);
        return blacklists;
    }


    private  LambdaQueryWrapper<StockAsinBlacklist> getWrapper(StockAsinBlacklistParam param) {
        LambdaQueryWrapper<StockAsinBlacklist> queryWrapper = new LambdaQueryWrapper<>();
        if ( ! Objects.isNull(param)) {
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getPlatformList()), StockAsinBlacklist::getPlatform, param.getPlatformList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getAreaList()), StockAsinBlacklist::getArea, param.getAreaList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getProductTypeList()), StockAsinBlacklist::getProductType, param.getProductTypeList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getDepartmentList()), StockAsinBlacklist::getDepartment, param.getDepartmentList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getTeamList()), StockAsinBlacklist::getTeam, param.getTeamList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getMaterialCodeList()), StockAsinBlacklist::getMaterialCode, param.getMaterialCodeList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getAsinList()), StockAsinBlacklist::getAsin, param.getAsinList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getCountryCodeList()), StockAsinBlacklist::getCountryCode, param.getCountryCodeList());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMaterialCode()), StockAsinBlacklist::getMaterialCode, param.getMaterialCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAsin()), StockAsinBlacklist::getAsin, param.getAsin());
            queryWrapper.eq(StringUtils.isNotEmpty(param.getStatus()), StockAsinBlacklist::getStatus, param.getStatus());
            queryWrapper.orderByDesc(StockAsinBlacklist::getId);
        }
        return queryWrapper;
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
