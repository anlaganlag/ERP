package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.exp.RequestEmptyException;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import cn.stylefeng.guns.cloud.system.core.dbs.context.CurrentDataSourceContext;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandCommunal;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademark;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandCommunalParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkReportPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkReportResult;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkResult;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandTrademarkMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandCommunalService;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandTrademarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* <p>
* 品牌商标表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-19
*/
@Service
public class TbBrandTrademarkServiceImpl extends ServiceImpl<TbBrandTrademarkMapper, TbBrandTrademark> implements TbBrandTrademarkService {

    @Autowired
    TbBrandCommunalService brandCommunalService;
    @DataSource(name="stocking")
    @Override
    public List<String> getTradeName() {
        QueryWrapper query=new QueryWrapper();
        query.select("distinct trade_name")
                .orderByAsc("trade_name");
        List list = this.baseMapper.selectObjs(query);
        return list;
    }

    @DataSource(name="stocking")
    @Override
    public Page<TbBrandTrademarkResult> getPage(TbBrandTrademarkPageParam param) {
        Page page = param.getPageContext();
        return    this.baseMapper.getPage(page,param);
    }

    //@Transactional
    @DataSource(name="stocking")
    @Override
    public void save(TbBrandTrademarkParam param) {

        QueryWrapper<TbBrandTrademark> query=new QueryWrapper();
        query.eq("upper(trade_name)",param.getTradeName().toUpperCase())
                .eq("type",param.getType())
        ;
        if (this.baseMapper.selectCount(query)>0){
            throw new RequestEmptyException("已存在品牌名称:"+param.getTradeName());
        }

        TbBrandTrademark brand=new TbBrandTrademark();
        BeanUtils.copyProperties(param,brand);
        brand.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
        brand.setCreateName(LoginContext.me().getLoginUser().getName());
        this.baseMapper.insert(brand);
        if (param.getType()==0){
            TbBrandCommunal communal=new TbBrandCommunal();
            communal.setSalesBrand(param.getTradeName());
            communal.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
            communal.setSysPerName(LoginContext.me().getLoginUser().getName());
            brandCommunalService.save(communal);
        }

        saveDictionary(param.getTradeName());
    }

    //字典存回EBMS
    public void saveDictionary(String tradeName){
        // 切换到指定数据源
        CurrentDataSourceContext.setDataSourceType("EBMS");
        this.baseMapper.saveDictionary("p53","公司品牌",tradeName);
        CurrentDataSourceContext.clearDataSourceType();
    }

    @DataSource(name="EBMS")
    @Override
    public List<Map<String, Object>> getDictionarys(String code) {
        return this.baseMapper.getDictionarys(code);
    }

    @DataSource(name="stocking")
    @Override
    public Page<TbBrandTrademarkReportResult> getTradeReportPage(TbBrandTrademarkReportPageParam param) {
        Page page = param.getPageContext();
        return    this.baseMapper.getTradeReportPage(page,param);
    }

    @DataSource(name="stocking")
    @Override
    public List<TbBrandTrademarkReportResult> getTradeReport(TbBrandTrademarkReportPageParam param) {
        return this.baseMapper.getTradeReport(param);
    }
}
