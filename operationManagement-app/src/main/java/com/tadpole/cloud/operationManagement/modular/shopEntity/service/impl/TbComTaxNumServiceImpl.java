package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.*;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditDetParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxNumParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumDTOResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComTaxAuditDetMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComTaxAuditMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComTaxNumMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopTaxnCatManageService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxAuditDetService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxAuditService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 资源-税号;(Tb_Com_Tax_Num)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComTaxNumServiceImpl extends ServiceImpl<TbComTaxNumMapper, TbComTaxNum>  implements TbComTaxNumService{
    @Resource
    private TbComTaxNumMapper tbComTaxNumMapper;

    @Resource
    private TbComTaxAuditMapper tbComTaxAuditMapper;

    @Resource
    private TbComTaxAuditDetMapper tbComTaxAuditDetMapper;

    @Resource
    private TbComTaxAuditService tbComTaxAuditService;


    @Resource
    private TbComTaxAuditDetService tbComTaxAuditDetService;
    @Resource
    private TbComShopTaxnCatManageService tbComShopTaxnCatManageService;

    /** 
     * 通过ID查询单条数据 
     *
     * @param "taxnOverseas 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public ResponseData queryById(String shopName){
        if (StrUtil.isEmpty(shopName)) {
            return ResponseData.error("店铺名为空");
        }
        MPJLambdaWrapper<TbComTaxNum> queryWrapper = MPJWrappers.<TbComTaxNum>lambdaJoin();
        queryWrapper
                .selectAll(TbComTaxNum.class)
                .select(TbComShop::getShopMainBody,TbComShop::getShopNamePlat)
                .selectAll(TbComTaxAudit.class)
                .selectAll(TbComTaxAuditDet.class)
                .leftJoin(TbComShop.class, TbComShop::getShopName, TbComTaxNum::getShopName)
                .leftJoin(TbComTaxAudit.class, TbComTaxAudit::getTaxnOverseas, TbComTaxNum::getTaxnOverseas)
                .leftJoin(TbComTaxAuditDet.class,TbComTaxAuditDet::getCaseNo,TbComTaxAudit::getCaseNo)
                .selectCollection(TbComTaxAudit.class, TbComTaxNumDTOResult::getComTaxAudit)
                .selectCollection(TbComTaxAuditDet.class, TbComTaxNumDTOResult::getTbComTaxAuditDets)
                .selectAssociation(TbComTaxNum.class, TbComTaxNumDTOResult::getComTaxNum)
                .eq(TbComTaxNum::getShopName, shopName);

        List<TbComTaxNumDTOResult> tbComTaxNumResults = tbComTaxNumMapper.selectJoinList(TbComTaxNumDTOResult.class, queryWrapper);

        if (ObjectUtil.isEmpty(tbComTaxNumResults)) {
            return ResponseData.success("无税号数据");
        }
        if (tbComTaxNumResults.size()>1) {
            return ResponseData.error("多个税号数据");
        }
        TbComTaxNumDTOResult tbComTaxNumResult = tbComTaxNumResults.get(0);
        return ResponseData.success(tbComTaxNumResult);
        //#TODO 将查账明细放入明细主表
//        List<TbComTaxAuditResult> mains = tbComTaxNumResult.getComTaxAudit();
//        List<TbComTaxAuditDetResult> details = tbComTaxNumResult.getTbComTaxAuditDets();
//        if (details.size()>0) {
//            for (TbComTaxAuditResult main : mains) {
//                List<TbComTaxAuditDetResult> targetDetails = main.getComTaxAuditDetList();
//                String caseNo = main.getCaseNo();
//                for (TbComTaxAuditDetResult detail : details) {
//                    String caseNoDet = detail.getCaseNo();
//                    if (caseNo.equals(caseNoDet)) {
//                        targetDetails.add(detail);
//                    }
//                }
//                main.setComTaxAuditDetList(targetDetails);
//            }
//        }
    }
    
    /**
     * 分页查询
     *
     * @param tbComTaxNum 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComTaxNumResult> paginQuery(TbComTaxNumParam tbComTaxNum, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComTaxNum> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComTaxNum.getShopName())){
            queryWrapper.eq(TbComTaxNum::getShopName, tbComTaxNum.getShopName());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnLocal())){
            queryWrapper.eq(TbComTaxNum::getTaxnLocal, tbComTaxNum.getTaxnLocal());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnEori())){
            queryWrapper.eq(TbComTaxNum::getTaxnEori, tbComTaxNum.getTaxnEori());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnBelongToCom())){
            queryWrapper.eq(TbComTaxNum::getTaxnBelongToCom, tbComTaxNum.getTaxnBelongToCom());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnRegAddress())){
            queryWrapper.eq(TbComTaxNum::getTaxnRegAddress, tbComTaxNum.getTaxnRegAddress());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnAddrOfAccountant())){
            queryWrapper.eq(TbComTaxNum::getTaxnAddrOfAccountant, tbComTaxNum.getTaxnAddrOfAccountant());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnDecMethod())){
            queryWrapper.eq(TbComTaxNum::getTaxnDecMethod, tbComTaxNum.getTaxnDecMethod());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnDecTime())){
            queryWrapper.eq(TbComTaxNum::getTaxnDecTime, tbComTaxNum.getTaxnDecTime());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnRegEmail())){
            queryWrapper.eq(TbComTaxNum::getTaxnRegEmail, tbComTaxNum.getTaxnRegEmail());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnRegTel())){
            queryWrapper.eq(TbComTaxNum::getTaxnRegTel, tbComTaxNum.getTaxnRegTel());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnAudEmail())){
            queryWrapper.eq(TbComTaxNum::getTaxnAudEmail, tbComTaxNum.getTaxnAudEmail());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnAltEmail1())){
            queryWrapper.eq(TbComTaxNum::getTaxnAltEmail1, tbComTaxNum.getTaxnAltEmail1());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnAltEmail2())){
            queryWrapper.eq(TbComTaxNum::getTaxnAltEmail2, tbComTaxNum.getTaxnAltEmail2());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnAgency())){
            queryWrapper.eq(TbComTaxNum::getTaxnAgency, tbComTaxNum.getTaxnAgency());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnAgencyTel())){
            queryWrapper.eq(TbComTaxNum::getTaxnAgencyTel, tbComTaxNum.getTaxnAgencyTel());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnAgencyAddress())){
            queryWrapper.eq(TbComTaxNum::getTaxnAgencyAddress, tbComTaxNum.getTaxnAgencyAddress());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnState())){
            queryWrapper.eq(TbComTaxNum::getTaxnState, tbComTaxNum.getTaxnState());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnFiles())){
            queryWrapper.eq(TbComTaxNum::getTaxnFiles, tbComTaxNum.getTaxnFiles());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnCate())){
            queryWrapper.eq(TbComTaxNum::getTaxnCate, tbComTaxNum.getTaxnCate());
        }
        if(StrUtil.isNotBlank(tbComTaxNum.getTaxnFilesName())){
            queryWrapper.eq(TbComTaxNum::getTaxnFilesName, tbComTaxNum.getTaxnFilesName());
        }
        queryWrapper.orderByAsc(TbComTaxNum::getShopName);
        //2. 执行分页查询
        Page<TbComTaxNumResult> pagin = new Page<>(current , size , true);
        IPage<TbComTaxNumResult> selectResult = tbComTaxNumMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComTaxNum 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxNum insert(TbComTaxNum tbComTaxNum){
        tbComTaxNumMapper.insert(tbComTaxNum);
        return tbComTaxNum;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxNum update(TbComTaxNum entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComTaxNum> chainWrapper = new LambdaUpdateChainWrapper<>(tbComTaxNumMapper);
//        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopName()),TbComTaxNum::getShopName, entityParam.getShopName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnLocal()),TbComTaxNum::getTaxnLocal, entityParam.getTaxnLocal());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnEori()),TbComTaxNum::getTaxnEori, entityParam.getTaxnEori());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnBelongToCom()),TbComTaxNum::getTaxnBelongToCom, entityParam.getTaxnBelongToCom());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnRegAddress()),TbComTaxNum::getTaxnRegAddress, entityParam.getTaxnRegAddress());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAddrOfAccountant()),TbComTaxNum::getTaxnAddrOfAccountant, entityParam.getTaxnAddrOfAccountant());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnDecMethod()),TbComTaxNum::getTaxnDecMethod, entityParam.getTaxnDecMethod());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnDecTime()),TbComTaxNum::getTaxnDecTime, entityParam.getTaxnDecTime());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnRegEmail()),TbComTaxNum::getTaxnRegEmail, entityParam.getTaxnRegEmail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnRegTel()),TbComTaxNum::getTaxnRegTel, entityParam.getTaxnRegTel());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAudEmail()),TbComTaxNum::getTaxnAudEmail, entityParam.getTaxnAudEmail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAltEmail1()),TbComTaxNum::getTaxnAltEmail1, entityParam.getTaxnAltEmail1());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAltEmail2()),TbComTaxNum::getTaxnAltEmail2, entityParam.getTaxnAltEmail2());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAgency()),TbComTaxNum::getTaxnAgency, entityParam.getTaxnAgency());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAgencyTel()),TbComTaxNum::getTaxnAgencyTel, entityParam.getTaxnAgencyTel());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAgencyAddress()),TbComTaxNum::getTaxnAgencyAddress, entityParam.getTaxnAgencyAddress());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnState()),TbComTaxNum::getTaxnState, entityParam.getTaxnState());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnFiles()),TbComTaxNum::getTaxnFiles, entityParam.getTaxnFiles());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnCate()),TbComTaxNum::getTaxnCate, entityParam.getTaxnCate());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnFilesName()),TbComTaxNum::getTaxnFilesName, entityParam.getTaxnFilesName());

        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnRate()),TbComTaxNum::getTaxnRate, entityParam.getTaxnRate());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnEffDateOfVat()),TbComTaxNum::getTaxnEffDateOfVat, entityParam.getTaxnEffDateOfVat());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnEffeRangeStart()),TbComTaxNum::getTaxnEffeRangeStart, entityParam.getTaxnEffeRangeStart());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnEffeRangeEnd()),TbComTaxNum::getTaxnEffeRangeEnd, entityParam.getTaxnEffeRangeEnd());
        //2. 设置主键，并更新
        chainWrapper.eq(TbComTaxNum::getTaxnOverseas, entityParam.getTaxnOverseas());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        LoginUser loginUser = LoginContext.me().getLoginUser();
        TbComShopTaxnCatManage catManage = new TbComShopTaxnCatManage();
        catManage.setSysId(BigDecimal.valueOf(IdWorker.getId()));
        catManage.setShopName(entityParam.getShopName());
        catManage.setTaxnType(entityParam.getTaxnCate());
        catManage.setTaxnEffectRangeEnd(entityParam.getTaxnEffeRangeEnd());
        catManage.setTaxnEffectRangeStart(entityParam.getTaxnEffeRangeStart());
        tbComShopTaxnCatManageService.insert(catManage);
        return entityParam;
    }


     @DataSource(name = "stocking")
     @Override
     public ResponseData  updateByShopName(TbComTaxNum tbComTaxNum) throws Exception {
         try {
             if (ObjectUtil.isNull(tbComTaxNum) || ObjectUtil.isEmpty(tbComTaxNum.getShopName())) {
                 return ResponseData.error("店铺名称为空");
             }

             LambdaQueryWrapper<TbComTaxNum> wrapper = new LambdaQueryWrapper<>();
             wrapper.eq(TbComTaxNum::getShopName, tbComTaxNum.getShopName());
             List<TbComTaxNum> taxNumList = this.tbComTaxNumMapper.selectList(wrapper);

             if (ObjectUtil.isEmpty(taxNumList)) {
                 tbComTaxNumMapper.insert(tbComTaxNum);
                 return ResponseData.success();
             }

             //1. 根据条件动态更新
             LambdaUpdateChainWrapper<TbComTaxNum> chainWrapper = new LambdaUpdateChainWrapper<>(tbComTaxNumMapper);


             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnLocal())) {
                 chainWrapper.set(TbComTaxNum::getTaxnLocal, tbComTaxNum.getTaxnLocal());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnEori())) {
                 chainWrapper.set(TbComTaxNum::getTaxnEori, tbComTaxNum.getTaxnEori());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnBelongToCom())) {
                 chainWrapper.set(TbComTaxNum::getTaxnBelongToCom, tbComTaxNum.getTaxnBelongToCom());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnRegAddress())) {
                 chainWrapper.set(TbComTaxNum::getTaxnRegAddress, tbComTaxNum.getTaxnRegAddress());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnAddrOfAccountant())) {
                 chainWrapper.set(TbComTaxNum::getTaxnAddrOfAccountant, tbComTaxNum.getTaxnAddrOfAccountant());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnDecMethod())) {
                 chainWrapper.set(TbComTaxNum::getTaxnDecMethod, tbComTaxNum.getTaxnDecMethod());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnDecTime())) {
                 chainWrapper.set(TbComTaxNum::getTaxnDecTime, tbComTaxNum.getTaxnDecTime());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnRegEmail())) {
                 chainWrapper.set(TbComTaxNum::getTaxnRegEmail, tbComTaxNum.getTaxnRegEmail());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnRegTel())) {
                 chainWrapper.set(TbComTaxNum::getTaxnRegTel, tbComTaxNum.getTaxnRegTel());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnAudEmail())) {
                 chainWrapper.set(TbComTaxNum::getTaxnAudEmail, tbComTaxNum.getTaxnAudEmail());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnAltEmail1())) {
                 chainWrapper.set(TbComTaxNum::getTaxnAltEmail1, tbComTaxNum.getTaxnAltEmail1());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnAltEmail2())) {
                 chainWrapper.set(TbComTaxNum::getTaxnAltEmail2, tbComTaxNum.getTaxnAltEmail2());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnAgency())) {
                 chainWrapper.set(TbComTaxNum::getTaxnAgency, tbComTaxNum.getTaxnAgency());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnAgencyTel())) {
                 chainWrapper.set(TbComTaxNum::getTaxnAgencyTel, tbComTaxNum.getTaxnAgencyTel());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnAgencyAddress())) {
                 chainWrapper.set(TbComTaxNum::getTaxnAgencyAddress, tbComTaxNum.getTaxnAgencyAddress());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnState())) {
                 chainWrapper.set(TbComTaxNum::getTaxnState, tbComTaxNum.getTaxnState());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnFiles())) {
                 chainWrapper.set(TbComTaxNum::getTaxnFiles, tbComTaxNum.getTaxnFiles());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnCate())) {
                 chainWrapper.set(TbComTaxNum::getTaxnCate, tbComTaxNum.getTaxnCate());
             }
             if (StrUtil.isNotBlank(tbComTaxNum.getTaxnFilesName())) {
                 chainWrapper.set(TbComTaxNum::getTaxnFilesName, tbComTaxNum.getTaxnFilesName());
             }

             if (ObjectUtil.isNotNull(tbComTaxNum.getTaxnEffeRangeStart())) {
                 chainWrapper.set(TbComTaxNum::getTaxnEffeRangeStart, tbComTaxNum.getTaxnEffeRangeStart());
             }

             if (ObjectUtil.isNotNull(tbComTaxNum.getTaxnEffeRangeEnd())) {
                 chainWrapper.set(TbComTaxNum::getTaxnEffeRangeEnd, tbComTaxNum.getTaxnEffeRangeEnd());
             }

             if (ObjectUtil.isNotNull(tbComTaxNum.getTaxnOverseas())) {
                 chainWrapper.set(TbComTaxNum::getTaxnOverseas, tbComTaxNum.getTaxnOverseas());
             }

             chainWrapper
                     .eq(TbComTaxNum::getShopName, tbComTaxNum.getShopName());

             //2. 设置主键，并更新
             boolean ret = chainWrapper.update();
             //3. 更新成功了，查询最最对象返回
         } catch (Exception e) {
             log.error("按店铺修改请求参数【{}】执行异常：{}", JSONUtil.toJsonStr(tbComTaxNum), JSONUtil.toJsonStr(e));
             String erMsg = "按店铺修改异常" + JSONUtil.toJsonStr(e);
             return ResponseData.error(500, erMsg);
         }
         return ResponseData.success();
     }
    
    /** 
     * 通过主键删除数据
     *
     * @param "taxnOverseas 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxNum cancelTaxCode(TbComTaxNum tbComTaxNum) throws Exception {
        if (ObjectUtil.isNull(tbComTaxNum)||ObjectUtil.isEmpty(tbComTaxNum.getShopName())) {
            throw new Exception("店铺名称为空");
        }

        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComTaxNum> chainWrapper = new LambdaUpdateChainWrapper<>(tbComTaxNumMapper);
        chainWrapper
                .eq(TbComTaxNum::getShopName, tbComTaxNum.getShopName())
                .set(TbComTaxNum::getTaxnState, "注销");

        //2. 设置主键，并更新
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回

        return tbComTaxNum;
    }
    @DataSource(name = "stocking")
    @Override
    public ResponseData updateTaxNumState(String shopName,String taxnState) {
        if (ObjectUtil.isEmpty(shopName)) {
            return ResponseData.error("店铺为空");
        }
        if (ObjectUtil.isEmpty(taxnState)) {
            return ResponseData.error("状态为空");
        }
        if (!"正常".equals(taxnState) && !"查账".equals(taxnState)) {
            return ResponseData.error(StrUtil.format("状态:[{}]不在[正常,查账]值域",taxnState));
        }

        TbComTaxNum tbComTaxNum = new LambdaQueryChainWrapper<>(tbComTaxNumMapper)
                .eq(TbComTaxNum::getShopName, shopName).one();
        String preTaxnState = tbComTaxNum.getTaxnState();
        if (preTaxnState.equals(taxnState)) {
            return ResponseData.error(StrUtil.format("无需修改[{}]",taxnState));
        }
        //更新税号状态
        LambdaUpdateChainWrapper<TbComTaxNum> chainWrapper = new LambdaUpdateChainWrapper<>(tbComTaxNumMapper);
        chainWrapper
                .eq(TbComTaxNum::getShopName,shopName )
                .set(TbComTaxNum::getTaxnState, taxnState).update();

        //更新税号为正常即结束查账时,则查账主表记录结束日期
        if ("正常".equals(taxnState)) {
            String taxnOverseas = tbComTaxNum.getTaxnOverseas();
            LambdaUpdateChainWrapper<TbComTaxAudit> taxAuditDateEWrapper = new LambdaUpdateChainWrapper<>(tbComTaxAuditMapper);
            taxAuditDateEWrapper
                    .eq(TbComTaxAudit::getTaxnOverseas,taxnOverseas )
                    .set(TbComTaxAudit::getTaxAuditDateE, new Date()).update();
        }

        return ResponseData.success(StrUtil.format("店铺:[{}],状态[{}]修改为[{}]",shopName,preTaxnState,taxnState));

    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData addTaxAudit(TbComTaxAuditParam param) throws Exception {
        try {
            List<TbComTaxAuditDetParam> comTaxAuditDetList = param.getComTaxAuditDetList();
            if (ObjectUtil.isEmpty(comTaxAuditDetList)) {
                throw new IllegalArgumentException("查账明细为空");
            }
            String caseNo = param.getCaseNo();
            TbComTaxAudit tbComTaxAudit = new TbComTaxAudit();
            BeanUtil.copyProperties(param, tbComTaxAudit);

            LambdaQueryWrapper<TbComTaxAudit> taxAuditQueryWrapper = new LambdaQueryWrapper<>();
            taxAuditQueryWrapper.eq(TbComTaxAudit::getCaseNo, caseNo);
            List<TbComTaxAudit> existingAudits = tbComTaxAuditMapper.selectList(taxAuditQueryWrapper);


            if (ObjectUtil.isEmpty(existingAudits)) {
                TbComTaxAudit tbComTaxAuditMain = tbComTaxAuditService.insert(tbComTaxAudit);
                caseNo = tbComTaxAuditMain.getCaseNo();
            }

            LambdaQueryWrapper<TbComTaxAuditDet> taxAuditDetQueryWrapper = new LambdaQueryWrapper<>();
            taxAuditDetQueryWrapper.eq(TbComTaxAuditDet::getCaseNo, caseNo).orderByDesc(TbComTaxAuditDet::getCaseNoDetId);
            List<TbComTaxAuditDet> curDetList = tbComTaxAuditDetMapper.selectList(taxAuditDetQueryWrapper);

            if (ObjectUtil.isNotEmpty(curDetList) && param.getIsUpdate()) {
                tbComTaxAuditDetMapper.deleteById(curDetList.get(0).getCaseNoDetId());
            }

            ArrayList<TbComTaxAuditDet> detList = new ArrayList<>();
            for (TbComTaxAuditDetParam tbComTaxAuditDetParam : comTaxAuditDetList) {
                TbComTaxAuditDet det = new TbComTaxAuditDet();
                BeanUtil.copyProperties(tbComTaxAuditDetParam, det);
                det.setCaseNo(caseNo);
                det.setCaseNoDetId(new BigDecimal(IdWorker.getId()));
                detList.add(det);
            }
            tbComTaxAuditDetService.saveBatch(detList);
        } catch (Exception e) {
            log.error("税号查账新增异常:{}", JSON.toJSONString(e.getMessage()));
            log.error("税号查账新增异常stackTrace:\n{}", ExceptionUtils.getStackTrace(e));
        return ResponseData.error(StrUtil.format("税号查账新增异常:{}", JSON.toJSONString(e.getMessage())));
    }
        return ResponseData.success();
    }




    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String taxnOverseas){
        int total = tbComTaxNumMapper.deleteById(taxnOverseas);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param taxnOverseasList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> taxnOverseasList){
         int delCount = tbComTaxNumMapper.deleteBatchIds(taxnOverseasList);
         if (taxnOverseasList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 更新税号信息
      * @param tbComShopTaxnCatManage
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public boolean updateTaxNum(TbComShopTaxnCatManage tbComShopTaxnCatManage) {
         LambdaUpdateWrapper<TbComTaxNum> updateWrapper = new LambdaUpdateWrapper<>();
         updateWrapper.set(ObjectUtil.isNotEmpty(tbComShopTaxnCatManage.getTaxnType()), TbComTaxNum::getTaxnCate, tbComShopTaxnCatManage.getTaxnType())
                 .set(ObjectUtil.isNotNull(tbComShopTaxnCatManage.getTaxnEffectRangeStart()), TbComTaxNum::getTaxnEffeRangeStart, tbComShopTaxnCatManage.getTaxnEffectRangeStart())
                 .set(ObjectUtil.isNotNull(tbComShopTaxnCatManage.getTaxnEffectRangeEnd()), TbComTaxNum::getTaxnEffeRangeEnd, tbComShopTaxnCatManage.getTaxnEffectRangeEnd())
                 .eq(TbComTaxNum::getShopName,tbComShopTaxnCatManage.getShopName());
         int count = tbComTaxNumMapper.update(null, updateWrapper);
         return count>0;
     }
 }