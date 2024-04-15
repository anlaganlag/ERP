package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNum;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopReportResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopReportService;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源-店铺;(Tb_Com_Shop)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopReportServiceImpl extends ServiceImpl<TbComShopMapper, TbComShop>  implements TbComShopReportService {
    @Resource
    private TbComShopMapper tbComShopMapper;

     @Autowired
     SyncToErpConsumer syncToErpConsumer;
    




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
        //2. 执行分页查询
        Page<TbComShopReportResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopReportResult> selectResult = tbComShopMapper.selectJoinPage(pagin ,TbComShopReportResult.class, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
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


 }