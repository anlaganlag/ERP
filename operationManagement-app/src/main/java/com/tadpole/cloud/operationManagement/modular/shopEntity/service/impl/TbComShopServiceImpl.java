package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopRecBrankChangeAcc;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopShroffAccount;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNum;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopLogisticsReportResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopReportResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资源-店铺;(Tb_Com_Shop)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopServiceImpl extends ServiceImpl<TbComShopMapper, TbComShop>  implements TbComShopService{
    @Resource
    private TbComShopMapper tbComShopMapper;

     @Autowired
     SyncToErpConsumer syncToErpConsumer;

    /**
     * 通过ID查询单条数据
     *
     * @param shopName 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShop queryById(String shopName){
        return tbComShopMapper.selectById(shopName);
    }

    /**
     * 分页查询
     *
     * @param tbComShop 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopResult> paginQuery(TbComShopParam tbComShop, long current, long size){
        //1. 构建动态查询条件
        MPJLambdaWrapper<TbComShop> queryWrapper = MPJWrappers.<TbComShop>lambdaJoin();
        queryWrapper
                .selectAll(TbComShop.class)
                .select(TbComTaxNum::getTaxnOverseas)
                .eq(ObjectUtil.isNotEmpty(tbComShop.getShopState()), TbComShop::getShopState, tbComShop.getShopState())
                .leftJoin(TbComTaxNum.class, TbComTaxNum::getShopName, TbComShop::getShopName)
                .orderByAsc(TbComShop::getElePlatformName,TbComShop::getShopName,TbComShop::getCountryCode);


        //补充店铺名称申请
        if(StrUtil.isNotBlank(tbComShop.getShopName())){
            queryWrapper.like(TbComShop::getShopName, tbComShop.getShopName());
        }


        if(StrUtil.isNotBlank(tbComShop.getShopIsExtPay())){
            queryWrapper.eq(TbComShop::getShopIsExtPay, tbComShop.getShopIsExtPay());
        }
        if(StrUtil.isNotBlank(tbComShop.getElePlatformName())){
            queryWrapper.eq(TbComShop::getElePlatformName, tbComShop.getElePlatformName());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopNameSimple())){
            queryWrapper.eq(TbComShop::getShopNameSimple, tbComShop.getShopNameSimple());
        }
        if(StrUtil.isNotBlank(tbComShop.getCountryCode())){
            queryWrapper.eq(TbComShop::getCountryCode, tbComShop.getCountryCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopNamePlat())){
            queryWrapper.eq(TbComShop::getShopNamePlat, tbComShop.getShopNamePlat());
        }
        if(StrUtil.isNotBlank(tbComShop.getSellerId())){
            queryWrapper.eq(TbComShop::getSellerId, tbComShop.getSellerId());
        }
        if(StrUtil.isNotBlank(tbComShop.getMarketplaceId())){
            queryWrapper.eq(TbComShop::getMarketplaceId, tbComShop.getMarketplaceId());
        }
        if(StrUtil.isNotBlank(tbComShop.getAwsAccessKeyId())){
            queryWrapper.eq(TbComShop::getAwsAccessKeyId, tbComShop.getAwsAccessKeyId());
        }
        if(StrUtil.isNotBlank(tbComShop.getCId())){
            queryWrapper.eq(TbComShop::getCId, tbComShop.getCId());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMail())){
            queryWrapper.eq(TbComShop::getShopMail, tbComShop.getShopMail());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMailType())){
            queryWrapper.eq(TbComShop::getShopMailType, tbComShop.getShopMailType());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopTelephone())){
            queryWrapper.eq(TbComShop::getShopTelephone, tbComShop.getShopTelephone());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComNameEn())){
            queryWrapper.eq(TbComShop::getShopComNameEn, tbComShop.getShopComNameEn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrEn1())){
            queryWrapper.eq(TbComShop::getShopComAddrEn1, tbComShop.getShopComAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrEn2())){
            queryWrapper.eq(TbComShop::getShopComAddrEn2, tbComShop.getShopComAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComCity())){
            queryWrapper.eq(TbComShop::getShopComCity, tbComShop.getShopComCity());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComCountry())){
            queryWrapper.eq(TbComShop::getShopComCountry, tbComShop.getShopComCountry());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComState())){
            queryWrapper.eq(TbComShop::getShopComState, tbComShop.getShopComState());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComDistrict())){
            queryWrapper.eq(TbComShop::getShopComDistrict, tbComShop.getShopComDistrict());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComPosCode())){
            queryWrapper.eq(TbComShop::getShopComPosCode, tbComShop.getShopComPosCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopLegPersonEn())){
            queryWrapper.eq(TbComShop::getShopLegPersonEn, tbComShop.getShopLegPersonEn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBenMail1())){
            queryWrapper.eq(TbComShop::getShopBenMail1, tbComShop.getShopBenMail1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBenMail2())){
            queryWrapper.eq(TbComShop::getShopBenMail2, tbComShop.getShopBenMail2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopRemLogAddress())){
            queryWrapper.eq(TbComShop::getShopRemLogAddress, tbComShop.getShopRemLogAddress());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopState())){
            queryWrapper.eq(TbComShop::getShopState, tbComShop.getShopState());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMainBody())){
            queryWrapper.eq(TbComShop::getShopMainBody, tbComShop.getShopMainBody());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComNameCn())){
            queryWrapper.eq(TbComShop::getShopComNameCn, tbComShop.getShopComNameCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrCn())){
            queryWrapper.eq(TbComShop::getShopComAddrCn, tbComShop.getShopComAddrCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopLegPersonCn())){
            queryWrapper.eq(TbComShop::getShopLegPersonCn, tbComShop.getShopLegPersonCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComRegCountry())){
            queryWrapper.eq(TbComShop::getShopComRegCountry, tbComShop.getShopComRegCountry());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBank())){
            queryWrapper.eq(TbComShop::getShopColAccBank, tbComShop.getShopColAccBank());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccNo())){
            queryWrapper.eq(TbComShop::getShopColAccNo, tbComShop.getShopColAccNo());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColCurrency())){
            queryWrapper.eq(TbComShop::getShopColCurrency, tbComShop.getShopColCurrency());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccEmail())){
            queryWrapper.eq(TbComShop::getShopColAccEmail, tbComShop.getShopColAccEmail());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBic())){
            queryWrapper.eq(TbComShop::getShopBic, tbComShop.getShopBic());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopRoutingNumber())){
            queryWrapper.eq(TbComShop::getShopRoutingNumber, tbComShop.getShopRoutingNumber());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBsb())){
            queryWrapper.eq(TbComShop::getShopBsb, tbComShop.getShopBsb());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBanKCode())){
            queryWrapper.eq(TbComShop::getShopBanKCode, tbComShop.getShopBanKCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBranchCode())){
            queryWrapper.eq(TbComShop::getShopBranchCode, tbComShop.getShopBranchCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountType())){
            queryWrapper.eq(TbComShop::getShopAccountType, tbComShop.getShopAccountType());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopSortCode())){
            queryWrapper.eq(TbComShop::getShopSortCode, tbComShop.getShopSortCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopIban())){
            queryWrapper.eq(TbComShop::getShopIban, tbComShop.getShopIban());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopShipAddrEn1())){
            queryWrapper.eq(TbComShop::getShopShipAddrEn1, tbComShop.getShopShipAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopShipAddrEn2())){
            queryWrapper.eq(TbComShop::getShopShipAddrEn2, tbComShop.getShopShipAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBankMainBody())){
            queryWrapper.eq(TbComShop::getShopColAccBankMainBody, tbComShop.getShopColAccBankMainBody());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBankRemark())){
            queryWrapper.eq(TbComShop::getShopColAccBankRemark, tbComShop.getShopColAccBankRemark());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountHoldName())){
            queryWrapper.eq(TbComShop::getShopAccountHoldName, tbComShop.getShopAccountHoldName());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountNo())){
            queryWrapper.eq(TbComShop::getShopAccountNo, tbComShop.getShopAccountNo());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBankName())){
            queryWrapper.eq(TbComShop::getShopBankName, tbComShop.getShopBankName());
        }
        if(StrUtil.isNotBlank(tbComShop.getIdentification())){
            queryWrapper.eq(TbComShop::getIdentification, tbComShop.getIdentification());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopType())){
            queryWrapper.eq(TbComShop::getShopType, tbComShop.getShopType());
        }
        if(ObjectUtil.isNotEmpty(tbComShop.getElePlatformNameList())){
            queryWrapper.in(TbComShop::getElePlatformName, tbComShop.getElePlatformNameList());
        }
        //2. 执行分页查询
        Page<TbComShopResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopResult> selectResult = tbComShopMapper.selectJoinPage(pagin ,TbComShopResult.class, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }



    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopReportResult> paginQueryShopReport(TbComShopParam tbComShop, long current, long size){
        //1. 构建动态查询条件
        MPJLambdaWrapper<TbComShop> queryWrapper = MPJWrappers.<TbComShop>lambdaJoin();
        queryWrapper
                .selectAll(TbComShop.class)
                .select(TbComTaxNum::getTaxnOverseas)
                .eq(ObjectUtil.isNotEmpty(tbComShop.getShopState()), TbComShop::getShopState, tbComShop.getShopState())
                .leftJoin(TbComTaxNum.class, TbComTaxNum::getShopName, TbComShop::getShopName);

        //补充店铺名称申请
        if(StrUtil.isNotBlank(tbComShop.getShopName())){
            queryWrapper.like(TbComShop::getShopName, tbComShop.getShopName());
        }

        if(StrUtil.isNotBlank(tbComShop.getTaxnOverseas())){
            queryWrapper.eq(TbComTaxNum::getTaxnOverseas, tbComShop.getTaxnOverseas());
        }


        if(StrUtil.isNotBlank(tbComShop.getTaxnAgency())){
            queryWrapper.eq(TbComTaxNum::getTaxnAgency, tbComShop.getTaxnAgency());
        }



        if(StrUtil.isNotBlank(tbComShop.getShopIsExtPay())){
            queryWrapper.eq(TbComShop::getShopIsExtPay, tbComShop.getShopIsExtPay());
        }
        if(StrUtil.isNotBlank(tbComShop.getElePlatformName())){
            queryWrapper.eq(TbComShop::getElePlatformName, tbComShop.getElePlatformName());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopNameSimple())){
            queryWrapper.eq(TbComShop::getShopNameSimple, tbComShop.getShopNameSimple());
        }
        if(StrUtil.isNotBlank(tbComShop.getCountryCode())){
            queryWrapper.eq(TbComShop::getCountryCode, tbComShop.getCountryCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopNamePlat())){
            queryWrapper.eq(TbComShop::getShopNamePlat, tbComShop.getShopNamePlat());
        }
        if(StrUtil.isNotBlank(tbComShop.getSellerId())){
            queryWrapper.eq(TbComShop::getSellerId, tbComShop.getSellerId());
        }
        if(StrUtil.isNotBlank(tbComShop.getMarketplaceId())){
            queryWrapper.eq(TbComShop::getMarketplaceId, tbComShop.getMarketplaceId());
        }
        if(StrUtil.isNotBlank(tbComShop.getAwsAccessKeyId())){
            queryWrapper.eq(TbComShop::getAwsAccessKeyId, tbComShop.getAwsAccessKeyId());
        }
        if(StrUtil.isNotBlank(tbComShop.getCId())){
            queryWrapper.eq(TbComShop::getCId, tbComShop.getCId());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMail())){
            queryWrapper.eq(TbComShop::getShopMail, tbComShop.getShopMail());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMailType())){
            queryWrapper.eq(TbComShop::getShopMailType, tbComShop.getShopMailType());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopTelephone())){
            queryWrapper.eq(TbComShop::getShopTelephone, tbComShop.getShopTelephone());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComNameEn())){
            queryWrapper.eq(TbComShop::getShopComNameEn, tbComShop.getShopComNameEn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrEn1())){
            queryWrapper.eq(TbComShop::getShopComAddrEn1, tbComShop.getShopComAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrEn2())){
            queryWrapper.eq(TbComShop::getShopComAddrEn2, tbComShop.getShopComAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComCity())){
            queryWrapper.eq(TbComShop::getShopComCity, tbComShop.getShopComCity());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComCountry())){
            queryWrapper.eq(TbComShop::getShopComCountry, tbComShop.getShopComCountry());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComState())){
            queryWrapper.eq(TbComShop::getShopComState, tbComShop.getShopComState());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComDistrict())){
            queryWrapper.eq(TbComShop::getShopComDistrict, tbComShop.getShopComDistrict());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComPosCode())){
            queryWrapper.eq(TbComShop::getShopComPosCode, tbComShop.getShopComPosCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopLegPersonEn())){
            queryWrapper.eq(TbComShop::getShopLegPersonEn, tbComShop.getShopLegPersonEn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBenMail1())){
            queryWrapper.eq(TbComShop::getShopBenMail1, tbComShop.getShopBenMail1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBenMail2())){
            queryWrapper.eq(TbComShop::getShopBenMail2, tbComShop.getShopBenMail2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopRemLogAddress())){
            queryWrapper.eq(TbComShop::getShopRemLogAddress, tbComShop.getShopRemLogAddress());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopState())){
            queryWrapper.eq(TbComShop::getShopState, tbComShop.getShopState());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMainBody())){
            queryWrapper.eq(TbComShop::getShopMainBody, tbComShop.getShopMainBody());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComNameCn())){
            queryWrapper.eq(TbComShop::getShopComNameCn, tbComShop.getShopComNameCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrCn())){
            queryWrapper.eq(TbComShop::getShopComAddrCn, tbComShop.getShopComAddrCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopLegPersonCn())){
            queryWrapper.eq(TbComShop::getShopLegPersonCn, tbComShop.getShopLegPersonCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComRegCountry())){
            queryWrapper.eq(TbComShop::getShopComRegCountry, tbComShop.getShopComRegCountry());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBank())){
            queryWrapper.like(TbComShop::getShopColAccBank, tbComShop.getShopColAccBank());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccNo())){
            queryWrapper.eq(TbComShop::getShopColAccNo, tbComShop.getShopColAccNo());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColCurrency())){
            queryWrapper.eq(TbComShop::getShopColCurrency, tbComShop.getShopColCurrency());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccEmail())){
            queryWrapper.eq(TbComShop::getShopColAccEmail, tbComShop.getShopColAccEmail());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBic())){
            queryWrapper.eq(TbComShop::getShopBic, tbComShop.getShopBic());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopRoutingNumber())){
            queryWrapper.eq(TbComShop::getShopRoutingNumber, tbComShop.getShopRoutingNumber());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBsb())){
            queryWrapper.eq(TbComShop::getShopBsb, tbComShop.getShopBsb());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBanKCode())){
            queryWrapper.eq(TbComShop::getShopBanKCode, tbComShop.getShopBanKCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBranchCode())){
            queryWrapper.eq(TbComShop::getShopBranchCode, tbComShop.getShopBranchCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountType())){
            queryWrapper.eq(TbComShop::getShopAccountType, tbComShop.getShopAccountType());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopSortCode())){
            queryWrapper.eq(TbComShop::getShopSortCode, tbComShop.getShopSortCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopIban())){
            queryWrapper.eq(TbComShop::getShopIban, tbComShop.getShopIban());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopShipAddrEn1())){
            queryWrapper.eq(TbComShop::getShopShipAddrEn1, tbComShop.getShopShipAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopShipAddrEn2())){
            queryWrapper.eq(TbComShop::getShopShipAddrEn2, tbComShop.getShopShipAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBankMainBody())){
            queryWrapper.eq(TbComShop::getShopColAccBankMainBody, tbComShop.getShopColAccBankMainBody());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBankRemark())){
            queryWrapper.eq(TbComShop::getShopColAccBankRemark, tbComShop.getShopColAccBankRemark());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountHoldName())){
            queryWrapper.eq(TbComShop::getShopAccountHoldName, tbComShop.getShopAccountHoldName());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountNo())){
            queryWrapper.eq(TbComShop::getShopAccountNo, tbComShop.getShopAccountNo());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBankName())){
            queryWrapper.eq(TbComShop::getShopBankName, tbComShop.getShopBankName());
        }
        if(StrUtil.isNotBlank(tbComShop.getIdentification())){
            queryWrapper.eq(TbComShop::getIdentification, tbComShop.getIdentification());
        }
        //2. 执行分页查询
        Page<TbComShopReportResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopReportResult> selectResult = tbComShopMapper.selectJoinPage(pagin ,TbComShopReportResult.class, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }


    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopLogisticsReportResult> TbComShopLogisticsReportResult(TbComShopParam tbComShop, long current, long size){
        //1. 构建动态查询条件
        MPJLambdaWrapper<TbComShop> queryWrapper = MPJWrappers.<TbComShop>lambdaJoin();
        queryWrapper
                .selectAll(TbComShop.class)
                .select(TbComTaxNum::getTaxnOverseas)
                .select(TbComTaxNum::getTaxnLocal)
                .select(TbComTaxNum::getTaxnEori)
                .select(TbComTaxNum::getTaxnBelongToCom)
                .select(TbComTaxNum::getTaxnRegAddress)
                .select(TbComTaxNum::getTaxnAddrOfAccountant)
                .select(TbComTaxNum::getTaxnEffDateOfVat)
                .select(TbComTaxNum::getTaxnAgency)
                .select(TbComTaxNum::getTaxnAgencyTel)
                .select(TbComTaxNum::getTaxnAgencyAddress)
                .select(TbComTaxNum::getTaxnRate)
                .select(TbComTaxNum::getTaxnState)
                .selectAs(TbComTaxNum::getShopName,"taxShopName")
                .eq(ObjectUtil.isNotEmpty(tbComShop.getShopState()), TbComShop::getShopState, tbComShop.getShopState())
                .leftJoin(TbComTaxNum.class, TbComTaxNum::getShopName, TbComShop::getShopName);

        //补充店铺名称申请
        if(StrUtil.isNotBlank(tbComShop.getShopName())){
            queryWrapper.like(TbComShop::getShopName, tbComShop.getShopName());
        }

        if(StrUtil.isNotBlank(tbComShop.getTaxnOverseas())){
            queryWrapper.eq(TbComTaxNum::getTaxnOverseas, tbComShop.getTaxnOverseas());
        }


        if(StrUtil.isNotBlank(tbComShop.getTaxnAgency())){
            queryWrapper.eq(TbComTaxNum::getTaxnAgency, tbComShop.getTaxnAgency());
        }



        if(StrUtil.isNotBlank(tbComShop.getShopIsExtPay())){
            queryWrapper.eq(TbComShop::getShopIsExtPay, tbComShop.getShopIsExtPay());
        }
        if(StrUtil.isNotBlank(tbComShop.getElePlatformName())){
            queryWrapper.eq(TbComShop::getElePlatformName, tbComShop.getElePlatformName());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopNameSimple())){
            queryWrapper.eq(TbComShop::getShopNameSimple, tbComShop.getShopNameSimple());
        }
        if(StrUtil.isNotBlank(tbComShop.getCountryCode())){
            queryWrapper.eq(TbComShop::getCountryCode, tbComShop.getCountryCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopNamePlat())){
            queryWrapper.eq(TbComShop::getShopNamePlat, tbComShop.getShopNamePlat());
        }
        if(StrUtil.isNotBlank(tbComShop.getSellerId())){
            queryWrapper.eq(TbComShop::getSellerId, tbComShop.getSellerId());
        }
        if(StrUtil.isNotBlank(tbComShop.getMarketplaceId())){
            queryWrapper.eq(TbComShop::getMarketplaceId, tbComShop.getMarketplaceId());
        }
        if(StrUtil.isNotBlank(tbComShop.getAwsAccessKeyId())){
            queryWrapper.eq(TbComShop::getAwsAccessKeyId, tbComShop.getAwsAccessKeyId());
        }
        if(StrUtil.isNotBlank(tbComShop.getCId())){
            queryWrapper.eq(TbComShop::getCId, tbComShop.getCId());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMail())){
            queryWrapper.eq(TbComShop::getShopMail, tbComShop.getShopMail());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMailType())){
            queryWrapper.eq(TbComShop::getShopMailType, tbComShop.getShopMailType());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopTelephone())){
            queryWrapper.eq(TbComShop::getShopTelephone, tbComShop.getShopTelephone());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComNameEn())){
            queryWrapper.eq(TbComShop::getShopComNameEn, tbComShop.getShopComNameEn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrEn1())){
            queryWrapper.eq(TbComShop::getShopComAddrEn1, tbComShop.getShopComAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrEn2())){
            queryWrapper.eq(TbComShop::getShopComAddrEn2, tbComShop.getShopComAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComCity())){
            queryWrapper.eq(TbComShop::getShopComCity, tbComShop.getShopComCity());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComCountry())){
            queryWrapper.eq(TbComShop::getShopComCountry, tbComShop.getShopComCountry());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComState())){
            queryWrapper.eq(TbComShop::getShopComState, tbComShop.getShopComState());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComDistrict())){
            queryWrapper.eq(TbComShop::getShopComDistrict, tbComShop.getShopComDistrict());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComPosCode())){
            queryWrapper.eq(TbComShop::getShopComPosCode, tbComShop.getShopComPosCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopLegPersonEn())){
            queryWrapper.eq(TbComShop::getShopLegPersonEn, tbComShop.getShopLegPersonEn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBenMail1())){
            queryWrapper.eq(TbComShop::getShopBenMail1, tbComShop.getShopBenMail1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBenMail2())){
            queryWrapper.eq(TbComShop::getShopBenMail2, tbComShop.getShopBenMail2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopRemLogAddress())){
            queryWrapper.eq(TbComShop::getShopRemLogAddress, tbComShop.getShopRemLogAddress());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopState())){
            queryWrapper.eq(TbComShop::getShopState, tbComShop.getShopState());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopMainBody())){
            queryWrapper.eq(TbComShop::getShopMainBody, tbComShop.getShopMainBody());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComNameCn())){
            queryWrapper.eq(TbComShop::getShopComNameCn, tbComShop.getShopComNameCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComAddrCn())){
            queryWrapper.eq(TbComShop::getShopComAddrCn, tbComShop.getShopComAddrCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopLegPersonCn())){
            queryWrapper.eq(TbComShop::getShopLegPersonCn, tbComShop.getShopLegPersonCn());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopComRegCountry())){
            queryWrapper.eq(TbComShop::getShopComRegCountry, tbComShop.getShopComRegCountry());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBank())){
            queryWrapper.like(TbComShop::getShopColAccBank, tbComShop.getShopColAccBank());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccNo())){
            queryWrapper.eq(TbComShop::getShopColAccNo, tbComShop.getShopColAccNo());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColCurrency())){
            queryWrapper.eq(TbComShop::getShopColCurrency, tbComShop.getShopColCurrency());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccEmail())){
            queryWrapper.eq(TbComShop::getShopColAccEmail, tbComShop.getShopColAccEmail());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBic())){
            queryWrapper.eq(TbComShop::getShopBic, tbComShop.getShopBic());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopRoutingNumber())){
            queryWrapper.eq(TbComShop::getShopRoutingNumber, tbComShop.getShopRoutingNumber());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBsb())){
            queryWrapper.eq(TbComShop::getShopBsb, tbComShop.getShopBsb());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBanKCode())){
            queryWrapper.eq(TbComShop::getShopBanKCode, tbComShop.getShopBanKCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBranchCode())){
            queryWrapper.eq(TbComShop::getShopBranchCode, tbComShop.getShopBranchCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountType())){
            queryWrapper.eq(TbComShop::getShopAccountType, tbComShop.getShopAccountType());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopSortCode())){
            queryWrapper.eq(TbComShop::getShopSortCode, tbComShop.getShopSortCode());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopIban())){
            queryWrapper.eq(TbComShop::getShopIban, tbComShop.getShopIban());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopShipAddrEn1())){
            queryWrapper.eq(TbComShop::getShopShipAddrEn1, tbComShop.getShopShipAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopShipAddrEn2())){
            queryWrapper.eq(TbComShop::getShopShipAddrEn2, tbComShop.getShopShipAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBankMainBody())){
            queryWrapper.eq(TbComShop::getShopColAccBankMainBody, tbComShop.getShopColAccBankMainBody());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopColAccBankRemark())){
            queryWrapper.eq(TbComShop::getShopColAccBankRemark, tbComShop.getShopColAccBankRemark());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountHoldName())){
            queryWrapper.eq(TbComShop::getShopAccountHoldName, tbComShop.getShopAccountHoldName());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopAccountNo())){
            queryWrapper.eq(TbComShop::getShopAccountNo, tbComShop.getShopAccountNo());
        }
        if(StrUtil.isNotBlank(tbComShop.getShopBankName())){
            queryWrapper.eq(TbComShop::getShopBankName, tbComShop.getShopBankName());
        }
        if(StrUtil.isNotBlank(tbComShop.getIdentification())){
            queryWrapper.eq(TbComShop::getIdentification, tbComShop.getIdentification());
        }

        if(ObjectUtil.isNotEmpty(tbComShop.getElePlatformNameList())){
            queryWrapper.in(TbComShop::getElePlatformName, tbComShop.getElePlatformNameList());
        }
        //2. 执行分页查询
        Page<TbComShopLogisticsReportResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopLogisticsReportResult> selectResult = tbComShopMapper.selectJoinPage(pagin ,TbComShopLogisticsReportResult.class, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }


    /**
      * 分页异常查询
      *
      * @param tbComShop 筛选条件
      * @param current 当前页码
      * @param size  每页大小
      * @return Page 分页查询结果
      */
     @DataSource(name = "stocking")
     @Override
     public Page<TbComShopResult> paginErrorQuery(TbComShopParam tbComShop, long current, long size){
         //1. 构建动态查询条件
         LambdaQueryWrapper<TbComShop> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbComShop::getShopSyncState,"未同步")
                     .or().isNull(TbComShop::getShopOrgCode);
         //2. 执行分页查询
         Page<TbComShopResult> pagin = new Page<>(current , size , true);
         IPage<TbComShopResult> selectResult = tbComShopMapper.selectByPage(pagin , queryWrapper);
         pagin.setPages(selectResult.getPages());
         pagin.setTotal(selectResult.getTotal());
         pagin.setRecords(selectResult.getRecords());
         //3. 返回结果
         return pagin;
     }

    /**
     * 分页异常查询
     *
     * @param param 筛选条件
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShop queryShopInfo(TbComShopParam param){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShop> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbComShop::getElePlatformName,param.getElePlatformName())
                .eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbComShop::getShopNameSimple,param.getShopNameSimple())
                .eq(ObjectUtil.isNotEmpty(param.getShopComNameCn()),TbComShop::getShopComNameCn,param.getShopComNameCn())
                ;
        //2. 执行分页查询
        List<TbComShop> tbComShopList = tbComShopMapper.selectList( queryWrapper);
        if (ObjectUtil.isNotEmpty(tbComShopList)) {
            return tbComShopList.get(0);
        }
        return null;
    }

    /**
     * 新增数据
     *
     * @param tbComShop 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShop insert(TbComShop tbComShop){
        tbComShopMapper.insert(tbComShop);
        return tbComShop;
    }

     /**
      * 生成源组织编码
      *
      * @param
      * @return 实例对象
      */
     @DataSource(name = "stocking")
     @Override
     public void createOrgCode(List<String> shopNameList){
         tbComShopMapper.createOrgCode(shopNameList);
     }


     /**
      * 同步ERP(店铺信息)
      *
      * @param "List<String> shopNameList 实例对象
      * @return void
      */
     @DataSource(name = "stocking")
     @Override
     public ResponseData syncShopInfo(List<String> shopNameList){
         if (ObjectUtil.isEmpty(shopNameList)) {
             return  ResponseData.error("请选择同步数据");
         }
         //创建组织编码
         tbComShopMapper.createOrgCode(shopNameList);

        //取出对应的店铺信息
         List<TbComShop> tbComShopList = new LambdaQueryChainWrapper<>(tbComShopMapper).in(TbComShop::getShopName, shopNameList)
                 .and(i->i.isNull(TbComShop::getShopSyncState).or().eq(TbComShop::getShopSyncState,"未同步")).list();
         if (ObjectUtil.isEmpty(tbComShopList)) {
             return  ResponseData.error("无可同步的数据");
         }
         List<String> shopNames = tbComShopList.stream().map(TbComShop::getShopName).collect(Collectors.toList());

         //组装JSONArray
         JSONArray Jarr = new JSONArray();
         tbComShopList.forEach(i->{
             JSONObject object = new JSONObject();
             object.put("FNumber",i.getShopOrgCode());
             object.put("FName",i.getShopName());
             object.put("fOrgFormID",3);
             object.put("FIsAccountOrg",true);
             object.put("FIsBusinessOrg",true);
             object.put("FParentID","003");
             object.put("FOrgFunctions","销售职能,采购职能,库存职能,结算职能,收付职能");
             object.put("FDevelopmentBox",false);
             object.put("FAcctOrgType","利润中心");
             object.put("F_UNW_ECOMMERCEPLATFORM",i.getElePlatformName());
             object.put("F_BSC_B2CPort",i.getCountryCode());
             object.put("F_BSC_Remark1",i.getRemark());
             object.put("F_BSC_Remark2",i.getRemark());
             object.put("F_BSC_Remark3",i.getRemark());
             Jarr.add(object);
         });
;
         JSONObject obj = syncToErpConsumer.createOrg(Jarr);
         if (obj.getString("Code") != null && obj.getString("Code").equals("0")) {
             new LambdaUpdateChainWrapper<>(tbComShopMapper).in(TbComShop::getShopName,shopNames)
                     .set(TbComShop::getShopSyncState,"已同步")
                     .set(TbComShop::getShopSyncTime,new Date())
                     .update();
             return ResponseData.success();
         } else {
             String responseMsg = null;
             JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
             if (CollectionUtil.isNotEmpty(responseResult)) {
                 responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
             }
             new LambdaUpdateChainWrapper<>(tbComShopMapper).in(TbComShop::getShopName,shopNames)
                     .set(TbComShop::getShopSyncState,"未同步")
                     .set(TbComShop::getShopSyncTime,new Date())
                     .update();
             return ResponseData.error("同步erp盘点接口失败！" + obj.getString("Message") + responseMsg);
         }
     }


     /**
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShop update(TbComShop entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShop> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopMapper);
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopIsExtPay()),TbComShop::getShopIsExtPay, entityParam.getShopIsExtPay());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getElePlatformName()),TbComShop::getElePlatformName, entityParam.getElePlatformName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopNameSimple()),TbComShop::getShopNameSimple, entityParam.getShopNameSimple());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCountryCode()),TbComShop::getCountryCode, entityParam.getCountryCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopNamePlat()),TbComShop::getShopNamePlat, entityParam.getShopNamePlat());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSellerId()),TbComShop::getSellerId, entityParam.getSellerId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getMarketplaceId()),TbComShop::getMarketplaceId, entityParam.getMarketplaceId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getAwsAccessKeyId()),TbComShop::getAwsAccessKeyId, entityParam.getAwsAccessKeyId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCId()),TbComShop::getCId, entityParam.getCId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopMail()),TbComShop::getShopMail, entityParam.getShopMail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopMailType()),TbComShop::getShopMailType, entityParam.getShopMailType());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopTelephone()),TbComShop::getShopTelephone, entityParam.getShopTelephone());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComNameEn()),TbComShop::getShopComNameEn, entityParam.getShopComNameEn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComAddrEn1()),TbComShop::getShopComAddrEn1, entityParam.getShopComAddrEn1());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComAddrEn2()),TbComShop::getShopComAddrEn2, entityParam.getShopComAddrEn2());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComCity()),TbComShop::getShopComCity, entityParam.getShopComCity());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComCountry()),TbComShop::getShopComCountry, entityParam.getShopComCountry());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComState()),TbComShop::getShopComState, entityParam.getShopComState());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComDistrict()),TbComShop::getShopComDistrict, entityParam.getShopComDistrict());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComPosCode()),TbComShop::getShopComPosCode, entityParam.getShopComPosCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopLegPersonEn()),TbComShop::getShopLegPersonEn, entityParam.getShopLegPersonEn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBenMail1()),TbComShop::getShopBenMail1, entityParam.getShopBenMail1());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBenMail2()),TbComShop::getShopBenMail2, entityParam.getShopBenMail2());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopRemLogAddress()),TbComShop::getShopRemLogAddress, entityParam.getShopRemLogAddress());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopState()),TbComShop::getShopState, entityParam.getShopState());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopMainBody()),TbComShop::getShopMainBody, entityParam.getShopMainBody());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComNameCn()),TbComShop::getShopComNameCn, entityParam.getShopComNameCn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComAddrCn()),TbComShop::getShopComAddrCn, entityParam.getShopComAddrCn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopLegPersonCn()),TbComShop::getShopLegPersonCn, entityParam.getShopLegPersonCn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComRegCountry()),TbComShop::getShopComRegCountry, entityParam.getShopComRegCountry());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccBank()),TbComShop::getShopColAccBank, entityParam.getShopColAccBank());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccNo()),TbComShop::getShopColAccNo, entityParam.getShopColAccNo());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColCurrency()),TbComShop::getShopColCurrency, entityParam.getShopColCurrency());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccEmail()),TbComShop::getShopColAccEmail, entityParam.getShopColAccEmail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBic()),TbComShop::getShopBic, entityParam.getShopBic());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopRoutingNumber()),TbComShop::getShopRoutingNumber, entityParam.getShopRoutingNumber());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBsb()),TbComShop::getShopBsb, entityParam.getShopBsb());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBanKCode()),TbComShop::getShopBanKCode, entityParam.getShopBanKCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBranchCode()),TbComShop::getShopBranchCode, entityParam.getShopBranchCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopAccountType()),TbComShop::getShopAccountType, entityParam.getShopAccountType());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopSortCode()),TbComShop::getShopSortCode, entityParam.getShopSortCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopIban()),TbComShop::getShopIban, entityParam.getShopIban());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopShipAddrEn1()),TbComShop::getShopShipAddrEn1, entityParam.getShopShipAddrEn1());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopShipAddrEn2()),TbComShop::getShopShipAddrEn2, entityParam.getShopShipAddrEn2());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccBankMainBody()),TbComShop::getShopColAccBankMainBody, entityParam.getShopColAccBankMainBody());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccBankRemark()),TbComShop::getShopColAccBankRemark, entityParam.getShopColAccBankRemark());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopAccountHoldName()),TbComShop::getShopAccountHoldName, entityParam.getShopAccountHoldName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopAccountNo()),TbComShop::getShopAccountNo, entityParam.getShopAccountNo());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBankName()),TbComShop::getShopBankName, entityParam.getShopBankName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getIdentification()),TbComShop::getIdentification, entityParam.getIdentification());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopDataState()),TbComShop::getShopDataState, entityParam.getShopDataState());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopOrgCode()),TbComShop::getShopOrgCode, entityParam.getShopOrgCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopSyncState()),TbComShop::getShopSyncState, entityParam.getShopSyncState());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopStateUpdatePersonCode()),TbComShop::getShopStateUpdatePersonCode, entityParam.getShopStateUpdatePersonCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopStateUpdatePersonName()),TbComShop::getShopStateUpdatePersonName, entityParam.getShopStateUpdatePersonName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getRemark()),TbComShop::getRemark, entityParam.getRemark());

        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopEffeRangeStart()),TbComShop::getShopEffeRangeStart, entityParam.getShopEffeRangeStart());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopEffeRangeEnd()),TbComShop::getShopEffeRangeEnd, entityParam.getShopEffeRangeEnd());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopSyncTime()),TbComShop::getShopSyncTime, entityParam.getShopSyncTime());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopCreateTime()),TbComShop::getShopCreateTime, entityParam.getShopCreateTime());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopStateUpdateTime()),TbComShop::getShopStateUpdateTime, entityParam.getShopStateUpdateTime());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getAuthStatus()),TbComShop::getAuthStatus, entityParam.getAuthStatus());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopDataDownTask()),TbComShop::getShopDataDownTask, entityParam.getShopDataDownTask());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getPurchaseMode()),TbComShop::getPurchaseMode, entityParam.getPurchaseMode());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getVcShopCode()),TbComShop::getVcShopCode, entityParam.getVcShopCode());
        //2. 设置主键，并更新
        chainWrapper
                .eq(TbComShop::getShopName, entityParam.getShopName())
                .and(wrapper->wrapper.isNull(TbComShop::getShopState).or().ne(TbComShop::getShopState,"关闭"));
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getShopName());
        }else{
            return entityParam;
        }
    }



     @DataSource(name = "stocking")
     @Override
     public ResponseData applyShutShop(TbComShop tbComShop){
         LoginUser loginUser = LoginContext.me().getLoginUser();
         if (ObjectUtil.isNull(loginUser)) {
             return ResponseData.error("无登陆人信息");
         }
         String account = loginUser.getAccount();
         String name = loginUser.getName();


         List<TbComShop> list = new LambdaQueryChainWrapper<>(tbComShopMapper)
                 .eq(TbComShop::getShopName, tbComShop.getShopName()).list();
         if (list.size()>1) {
             return ResponseData.error("找到多条对应店铺");
         }
         TbComShop tbComShopEnt = list.get(0);
         if (ObjectUtil.isNull(tbComShopEnt)) {
             return ResponseData.error(StrUtil.format("没有找到对应店铺名:[{}]",tbComShop.getShopName()));
         }
         String shopState = tbComShopEnt.getShopState();
         if ("申请关店中".equals(shopState) || "已关闭".equals(shopState)) {
             return ResponseData.error(StrUtil.format("当前状态不可申请关闭:[{}]",shopState));
         }

         //1. 根据条件动态更新
         LambdaUpdateChainWrapper<TbComShop> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopMapper);
         chainWrapper.set(TbComShop::getShopStateUpdatePersonName, name);
         chainWrapper.set(TbComShop::getShopStateUpdatePersonCode, account);
         chainWrapper.set(TbComShop::getShopStateUpdateTime, new Date());
         chainWrapper.set(TbComShop::getShopState, "申请关店中");
         //2. 设置主键，并更新
         chainWrapper.eq(TbComShop::getShopName, tbComShop.getShopName());
         boolean ret = chainWrapper.update();
         //3. 更新成功了，查询最最对象返回
         if(ret) {
             return ResponseData.success();
         } else {
             return ResponseData.error("申请关闭店铺失败");
         }

     }


    @DataSource(name = "stocking")
    @Override
    public ResponseData shutShop(TbComShop tbComShop){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        if (ObjectUtil.isNull(loginUser)) {
            return ResponseData.error("无登陆人信息");
        }
        List<TbComShop> list = new LambdaQueryChainWrapper<>(tbComShopMapper)
                .eq(TbComShop::getShopName, tbComShop.getShopName()).list();
        if (list.size()>1) {
            return ResponseData.error("找到多条对应店铺");
        }
        TbComShop tbComShopEnt = list.get(0);
        if (ObjectUtil.isNull(tbComShopEnt)) {
            return ResponseData.error(StrUtil.format("没有找到对应店铺名:[{}]",tbComShop.getShopName()));
        }
        String shopState = tbComShopEnt.getShopState();
        if ("已关闭".equals(shopState)) {
            return ResponseData.error(StrUtil.format("当前状态不可关闭:[{}]",shopState));
        }

        //1. 根据条件动态更新
        String account = loginUser.getAccount();
        String name = loginUser.getName();
        LambdaUpdateChainWrapper<TbComShop> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopMapper);
        chainWrapper.set(TbComShop::getShopStateUpdatePersonName, name);
        chainWrapper.set(TbComShop::getShopStateUpdatePersonCode, account);
        chainWrapper.set(TbComShop::getShopStateUpdateTime, new Date());
        chainWrapper.set(TbComShop::getShopState, "已关闭");
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShop::getShopName, tbComShop.getShopName());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret) {
            return ResponseData.success();
        } else {
            return ResponseData.error("关闭店铺失败");
        }

    }




     /**
     * 通过主键删除数据
     *
     * @param shopName 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String shopName){
        int total = tbComShopMapper.deleteById(shopName);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param shopNameList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> shopNameList){
         int delCount = tbComShopMapper.deleteBatchIds(shopNameList);
         if (shopNameList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 店铺名称下拉
      * @param filter 是否过滤
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public List<String> shopNameList(boolean filter) {
         MPJLambdaWrapper<TbComShop> queryWrapper = MPJWrappers.<TbComShop>lambdaJoin()
                 .select(TbComShop::getShopName)
                 .notInSql(filter,TbComShop::getShopName, "SELECT shop_Name FROM Tb_Com_Shop_Rec_Brank_Change_Acc WHERE sys_Change_Status = '已申请' OR sys_Change_Status = '已接收'");

         List<String> nameList = tbComShopMapper.selectJoinList(String.class, queryWrapper);
         return nameList;
     }

     /**
      * 收款银行账号变更
      * @param changeAcc 审核通过的变更记录
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public boolean recBrankChangeAcc(TbComShopRecBrankChangeAcc changeAcc) {
         LambdaUpdateWrapper<TbComShop> updateWrapper = new LambdaUpdateWrapper<>();
         updateWrapper
                 .eq(TbComShop::getShopName, changeAcc.getShopName())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopColAccBank()), TbComShop::getShopColAccBank, changeAcc.getShopColAccBank())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopColAccBankMainBody()), TbComShop::getShopColAccBankMainBody, changeAcc.getShopColAccBankMainBody())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopColAccNo()), TbComShop::getShopColAccNo, changeAcc.getShopColAccNo())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopColCurrency()), TbComShop::getShopColCurrency, changeAcc.getShopColCurrency())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopBic()), TbComShop::getShopBic, changeAcc.getShopBic())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopRoutingNumber()), TbComShop::getShopRoutingNumber, changeAcc.getShopRoutingNumber())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopBsb()), TbComShop::getShopBsb, changeAcc.getShopBsb())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopBanKCode()), TbComShop::getShopBanKCode, changeAcc.getShopBanKCode())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopBranchCode()), TbComShop::getShopBranchCode, changeAcc.getShopBranchCode())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopAccountType()), TbComShop::getShopAccountType, changeAcc.getShopAccountType())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopSortCode()), TbComShop::getShopSortCode, changeAcc.getShopSortCode())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopIban()), TbComShop::getShopIban, changeAcc.getShopIban())
                 .set(ObjectUtil.isNotEmpty(changeAcc.getShopColAccEmail()), TbComShop::getShopColAccEmail, changeAcc.getShopColAccEmail());
         return  tbComShopMapper.update(null, updateWrapper) >0;
     }

     /**
      * 更新信用卡账号信息
      * @param shopShroffAccount
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public boolean updateShroffAccount(TbComShopShroffAccount shopShroffAccount) {
         LambdaUpdateWrapper<TbComShop> wrapper = new LambdaUpdateWrapper<>();
         wrapper.eq(TbComShop::getShopName, shopShroffAccount.getShopName())
                 .set(ObjectUtil.isNotEmpty(shopShroffAccount.getShopAccountNo()),TbComShop::getShopAccountNo,shopShroffAccount.getShopAccountNo())
                 .set(ObjectUtil.isNotEmpty(shopShroffAccount.getShopBankName()),TbComShop::getShopBankName,shopShroffAccount.getShopBankName())
                 .set(ObjectUtil.isNotNull(shopShroffAccount.getShopEffeRangeStart()),TbComShop::getShopEffeRangeStart,shopShroffAccount.getShopEffeRangeStart())
                 .set(ObjectUtil.isNotNull(shopShroffAccount.getShopEffeRangeEnd()),TbComShop::getShopEffeRangeEnd,shopShroffAccount.getShopEffeRangeEnd());
         int count = tbComShopMapper.update(null, wrapper);
         return count>0;
     }

    @DataSource(name = "stocking")
    @Override
    public  List<Map<String, Object>> getAllShopName(){
        //1. 构建动态查询条件
        QueryWrapper<TbComShop> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ele_platform_name as elePlatformName","shop_name as shopName")
        .orderByAsc("ele_platform_name")
        ;
        List<Map<String, Object>> maps = this.baseMapper.selectMaps(queryWrapper);

        return maps;
    }

    /**
     * 账号下拉
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    public List<String> shopList() {
        LambdaQueryWrapper<TbComShop> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TbComShop :: getShopNameSimple)
                .in(TbComShop :: getShopState, "正常", "开通中")
                .isNotNull(TbComShop :: getShopNameSimple)
                .groupBy(TbComShop :: getShopNameSimple);
        List<TbComShop> TbComShopList = tbComShopMapper.selectList(queryWrapper);
        List<String> nameList = TbComShopList.stream().map(i -> i.getShopNameSimple()).collect(Collectors.toList());
        return nameList;
    }


    @DataSource(name = "stocking")
    @Override
    public List<String> siteList() {
        LambdaQueryWrapper<TbComShop> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TbComShop :: getCountryCode)
                .isNotNull(TbComShop :: getCountryCode)
                .groupBy(TbComShop :: getCountryCode);
        List<TbComShop> TbComShopList = tbComShopMapper.selectList(queryWrapper);
        List<String> nameList = TbComShopList.stream().map(TbComShop::getCountryCode).distinct().collect(Collectors.toList());
        //小平台校验ALL站点下站点
        nameList.add("PH");
        return nameList;
    }



    @DataSource(name = "stocking")
    @Override
    public List<String> platformList() {
        LambdaQueryWrapper<TbComShop> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TbComShop :: getElePlatformName)
                .isNotNull(TbComShop :: getElePlatformName)
                .groupBy(TbComShop :: getElePlatformName);
        List<TbComShop> TbComShopList = tbComShopMapper.selectList(queryWrapper);
        List<String> nameList = TbComShopList.stream().map(TbComShop::getElePlatformName).distinct().collect(Collectors.toList());
        return nameList;
    }


    @DataSource(name = "stocking")
    @Override
    public List<String> shopNameList() {
        LambdaQueryWrapper<TbComShop> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TbComShop :: getShopName)
                .isNotNull(TbComShop :: getShopName)
                .groupBy(TbComShop :: getShopName);
        List<TbComShop> TbComShopList = tbComShopMapper.selectList(queryWrapper);
        List<String> nameList = TbComShopList.stream().map(TbComShop::getShopName).distinct().collect(Collectors.toList());
        return nameList;
    }
 }
