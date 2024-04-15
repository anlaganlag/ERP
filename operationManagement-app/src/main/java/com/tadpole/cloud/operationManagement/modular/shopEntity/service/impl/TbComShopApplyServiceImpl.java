package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.*;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyDetService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbPlatformAccoSiteCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源-店铺申请;(Tb_Com_Shop_Apply)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopApplyServiceImpl extends ServiceImpl<TbComShopApplyMapper, TbComShopApply>  implements TbComShopApplyService{
    @Resource
    private TbComShopApplyMapper tbComShopApplyMapper;
    @Resource
    private TbComShopApplyDetMapper tbComShopApplyDetMapper;
    @Resource
    private TbComShopApplyDetService tbComShopApplyDetService;

     @Resource
     private TbComShopApplyBankCollectTaskMapper bankCollectTaskMapper;
     @Resource
     private TbComShopApplyTaxnTaskMapper taxnTaskMapper;

    @Resource
    private TbPlatformAccoSiteCodeMapper tbPlatformAccoSiteCodeMapper;


    @Resource
    private TbPlatformAccoSiteCodeService tbPlatformAccoSiteCodeService;



    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApply queryById(BigDecimal sysApplyId){
        return tbComShopApplyMapper.selectById(sysApplyId);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopApply 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopApplyResult> paginQuery(TbComShopApplyParam tbComShopApply, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopApply> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopApply.getSysApplyPerCode())){
            queryWrapper.eq(TbComShopApply::getSysApplyPerCode, tbComShopApply.getSysApplyPerCode());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getSysApplyPerName())){
            queryWrapper.eq(TbComShopApply::getSysApplyPerName, tbComShopApply.getSysApplyPerName());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getComNameCn())){
            queryWrapper.eq(TbComShopApply::getComNameCn, tbComShopApply.getComNameCn());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getElePlatformName())){
            queryWrapper.eq(TbComShopApply::getElePlatformName, tbComShopApply.getElePlatformName());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getAttachName())){
            queryWrapper.eq(TbComShopApply::getAttachName, tbComShopApply.getAttachName());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getSysApplyDesc())){
            queryWrapper.eq(TbComShopApply::getSysApplyDesc, tbComShopApply.getSysApplyDesc());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopNameSimple())){
            queryWrapper.eq(TbComShopApply::getShopNameSimple, tbComShopApply.getShopNameSimple());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopLegPersonEn())){
            queryWrapper.eq(TbComShopApply::getShopLegPersonEn, tbComShopApply.getShopLegPersonEn());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopTelephone())){
            queryWrapper.eq(TbComShopApply::getShopTelephone, tbComShopApply.getShopTelephone());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComNameEn())){
            queryWrapper.eq(TbComShopApply::getShopComNameEn, tbComShopApply.getShopComNameEn());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComAddrEn1())){
            queryWrapper.eq(TbComShopApply::getShopComAddrEn1, tbComShopApply.getShopComAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComAddrEn2())){
            queryWrapper.eq(TbComShopApply::getShopComAddrEn2, tbComShopApply.getShopComAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopShipAddrEn1())){
            queryWrapper.eq(TbComShopApply::getShopShipAddrEn1, tbComShopApply.getShopShipAddrEn1());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopShipAddrEn2())){
            queryWrapper.eq(TbComShopApply::getShopShipAddrEn2, tbComShopApply.getShopShipAddrEn2());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComCity())){
            queryWrapper.eq(TbComShopApply::getShopComCity, tbComShopApply.getShopComCity());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComCountry())){
            queryWrapper.eq(TbComShopApply::getShopComCountry, tbComShopApply.getShopComCountry());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComState())){
            queryWrapper.eq(TbComShopApply::getShopComState, tbComShopApply.getShopComState());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComDistrict())){
            queryWrapper.eq(TbComShopApply::getShopComDistrict, tbComShopApply.getShopComDistrict());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getAttachAddrr())){
            queryWrapper.eq(TbComShopApply::getAttachAddrr, tbComShopApply.getAttachAddrr());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getIdentification())){
            queryWrapper.eq(TbComShopApply::getIdentification, tbComShopApply.getIdentification());
        }
        if(StrUtil.isNotBlank(tbComShopApply.getShopComPosCode())){
            queryWrapper.eq(TbComShopApply::getShopComPosCode, tbComShopApply.getShopComPosCode());
        }
        //2. 执行分页查询
        Page<TbComShopApplyResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopApplyResult> selectResult = tbComShopApplyMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopApply 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApply insert(TbComShopApply tbComShopApply){
        tbComShopApply.setSysApplyId(BigDecimal.valueOf(IdWorker.getId()));
        LoginUser loginUser = LoginContext.me().getLoginUser();
        tbComShopApply.setSysApplyPerCode(loginUser.getAccount());
        tbComShopApply.setSysApplyPerName(loginUser.getName());
        tbComShopApply.setSysApplyDate(new Date());
        tbComShopApply.setSysApplyState(0);
        tbComShopApplyMapper.insert(tbComShopApply);
        return tbComShopApply;
    }


    /**
     * 拷贝数据
     *
     * @param "tbComShopApply 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApply copy(TbComShopApply tbComShopApplyParam){
        BigDecimal sysApplyId = tbComShopApplyParam.getSysApplyId();
        if (ObjectUtil.isNull(sysApplyId)) {
            log.error("无申请店铺ID");
            return null;
        }
        TbComShopApply srcComShopApply = new LambdaQueryChainWrapper<>(tbComShopApplyMapper)
                .eq(TbComShopApply::getSysApplyId, sysApplyId).one();
        if (ObjectUtil.isNull(srcComShopApply)) {
            log.error("找不到申请店铺");
            return null;
        }
        //非已完成的不能复制
        if (srcComShopApply.getSysApplyState()!=3) {
            log.error("非已完成申请店铺不能复制");
            return null;
        }

        TbComShopApply tbComShopApply = new TbComShopApply();
        BeanUtil.copyProperties(srcComShopApply,tbComShopApply);

        tbComShopApply.setSysApplyId(BigDecimal.valueOf(IdWorker.getId()));
        LoginUser loginUser = LoginContext.me().getLoginUser();
        tbComShopApply.setSysApplyPerCode(loginUser.getAccount());
        tbComShopApply.setSysApplyPerName(loginUser.getName());
        tbComShopApply.setSysApplyDate(new Date());
        tbComShopApply.setSysApplyState(1);
        tbComShopApplyMapper.insert(tbComShopApply);
        return tbComShopApply;
    }


    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyParam update(TbComShopApplyParam entityParam){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean isUploadFile = (ObjectUtil.isNotEmpty(entityParam.getCreateShopSaveOrSubmit()) && "资料上传".equals(entityParam.getCreateShopSaveOrSubmit()));

        if (isUploadFile) {//资料上传时更新
            LambdaUpdateWrapper<TbComShopApply> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TbComShopApply::getSysApplyId, entityParam.getSysApplyId())
                    .set(TbComShopApply::getAttachAddrr, entityParam.getAttachAddrr())
                    .set(TbComShopApply::getSysApplyState, BigDecimal.ONE);
            int update = tbComShopApplyMapper.update(null, wrapper);
            return entityParam;
        }
        //在配置表新增或更新账号
        TbPlatformAccoSiteCode existingTbPlatformAccoSiteCode = new LambdaQueryChainWrapper<>(tbPlatformAccoSiteCodeMapper)
                .eq(TbPlatformAccoSiteCode::getName, entityParam.getShopNameSimple())
                .eq(TbPlatformAccoSiteCode::getType, "账号")
                .one();
        if (ObjectUtil.isEmpty(existingTbPlatformAccoSiteCode)) {
            TbPlatformAccoSiteCode tbPlatformAccoSiteCode = new TbPlatformAccoSiteCode();
            tbPlatformAccoSiteCode.setName(entityParam.getShopNameSimple());
            tbPlatformAccoSiteCode.setType("账号");
            tbPlatformAccoSiteCodeService.insert(tbPlatformAccoSiteCode);
        }



        //根据申请编号删除申请明细
        if (ObjectUtil.isNotEmpty(entityParam.getSysApplyId())) {
            LambdaQueryWrapper<TbComShopApplyDet> detWrapper = new LambdaQueryWrapper<>();
            detWrapper.eq(TbComShopApplyDet::getSysApplyId, entityParam.getSysApplyId());
            tbComShopApplyDetMapper.delete(detWrapper);
        } else{
            TbComShopApply tbComShopApply = new TbComShopApply();
            BeanUtil.copyProperties(entityParam,tbComShopApply);
            BigDecimal applyId = BigDecimal.valueOf(IdWorker.getId());
            entityParam.setSysApplyId(applyId);
            tbComShopApply.setSysApplyId(applyId);
            //店铺创建状态
            tbComShopApply.setSysApplyState(1);
            tbComShopApplyMapper.insert(tbComShopApply);
        }
        List<TbComShopApplyDet> applyDetList = entityParam.getApplyDetList();
        List<BigDecimal> sysApplyDetIdList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(applyDetList)) {
            applyDetList.forEach(c->{
                c.setSysApplyId(entityParam.getSysApplyId());
                BigDecimal detId = BigDecimal.valueOf(IdWorker.getId());
                c.setSysApplyDetId(detId);
                c.setSysCreateDate(new Date());
                c.setSysCreatePerCode(loginUser.getAccount());
                c.setSysCreatePerName(loginUser.getName());
                tbComShopApplyDetMapper.insert(c);
                sysApplyDetIdList.add(detId);
            });
        }

        Integer applyState = 2; //提交是才更新状态

        boolean isSubcomit = (ObjectUtil.isNotEmpty(entityParam.getCreateShopSaveOrSubmit()) && "提交".equals(entityParam.getCreateShopSaveOrSubmit()));
        if (isSubcomit) {
            //大所有申请明细都没有需要创建的是收款账号和税号任务时，直接变成已完成
//            applyDetList.forEach(a->a.setSysApplyDetId(BigDecimal.valueOf(IdWorker.getId())));

            //创建银行收款账号 taxnTaskMapper
            List<TbComShopApplyDet> bankColleAccList = applyDetList.stream().filter(a -> BigDecimal.ONE.equals(a.getShopBankColleAcc())).collect(Collectors.toList());
            List<TbComShopApplyBankCollectTask> needSaveBankTaskList = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(bankColleAccList)) {
                for (TbComShopApplyDet tbComShopApplyDet : bankColleAccList) {
                    TbComShopApplyBankCollectTask bankCollectTask = BeanUtil.copyProperties(tbComShopApplyDet, TbComShopApplyBankCollectTask.class);
                    bankCollectTask.setSysApplyDetBctId(BigDecimal.valueOf(IdWorker.getId()));
                    bankCollectTask.setSysApplyDetBctState("未完成");
                    bankCollectTaskMapper.insert(bankCollectTask);
                    needSaveBankTaskList.add(bankCollectTask);
                }

            }

            //创建税号 taxnTaskMapper
            List<TbComShopApplyDet> shopTaxnList = applyDetList.stream().filter(a -> BigDecimal.ONE.equals(a.getShopTaxn())).collect(Collectors.toList());
            List<TbComShopApplyTaxnTask> needShopTaxnList = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(shopTaxnList)) {
                for (TbComShopApplyDet tbComShopApplyDet : shopTaxnList) {
                    TbComShopApplyTaxnTask taxnTask = BeanUtil.copyProperties(tbComShopApplyDet, TbComShopApplyTaxnTask.class);
                    taxnTask.setTaxnId(BigDecimal.valueOf(IdWorker.getId()));
                    taxnTask.setTaxnTaskState("未完成");
                    taxnTaskMapper.insert(taxnTask);
                    needShopTaxnList.add(taxnTask);
                }
            }
            if (ObjectUtil.isEmpty(needSaveBankTaskList) &&  ObjectUtil.isEmpty(needShopTaxnList)) {
                applyState=3;
            }
        }


        //根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopApply> chainWrapper = getWrapper(entityParam);
        chainWrapper.set(TbComShopApply::getSysApplyPerName, loginUser.getName());
        chainWrapper.set(TbComShopApply::getSysApplyPerCode, loginUser.getAccount());
        chainWrapper.set(TbComShopApply::getSysApplyDate, new Date());
        chainWrapper.set(isSubcomit, TbComShopApply::getSysApplyState, applyState);
        //设置主键，并更新
        chainWrapper.eq(TbComShopApply::getSysApplyId, entityParam.getSysApplyId());
        boolean ret = chainWrapper.update();

        //如果沒有财务任务需要处理，直接完成了，则同步数据
//        if (applyState==3 && sysApplyDetIdList.size()>0) {
            for (BigDecimal detId : sysApplyDetIdList) {
                tbComShopApplyDetService.syncData(detId);
            }
//        }

        //3. 更新成功了，是否有具体的申请明细项
            return entityParam;

    }

     private LambdaUpdateChainWrapper<TbComShopApply> getWrapper(TbComShopApplyParam entityParam) {
         LambdaUpdateChainWrapper<TbComShopApply> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopApplyMapper);
//         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSysApplyPerCode()),TbComShopApply::getSysApplyPerCode, entityParam.getSysApplyPerCode());
//         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSysApplyPerName()),TbComShopApply::getSysApplyPerName, entityParam.getSysApplyPerName());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getComNameCn()),TbComShopApply::getComNameCn, entityParam.getComNameCn());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getElePlatformName()),TbComShopApply::getElePlatformName, entityParam.getElePlatformName());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getAttachName()),TbComShopApply::getAttachName, entityParam.getAttachName());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSysApplyDesc()),TbComShopApply::getSysApplyDesc, entityParam.getSysApplyDesc());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopNameSimple()),TbComShopApply::getShopNameSimple, entityParam.getShopNameSimple());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopLegPersonEn()),TbComShopApply::getShopLegPersonEn, entityParam.getShopLegPersonEn());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopTelephone()),TbComShopApply::getShopTelephone, entityParam.getShopTelephone());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComNameEn()),TbComShopApply::getShopComNameEn, entityParam.getShopComNameEn());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComAddrEn1()),TbComShopApply::getShopComAddrEn1, entityParam.getShopComAddrEn1());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComAddrEn2()),TbComShopApply::getShopComAddrEn2, entityParam.getShopComAddrEn2());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopShipAddrEn1()),TbComShopApply::getShopShipAddrEn1, entityParam.getShopShipAddrEn1());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopShipAddrEn2()),TbComShopApply::getShopShipAddrEn2, entityParam.getShopShipAddrEn2());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComCity()),TbComShopApply::getShopComCity, entityParam.getShopComCity());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComCountry()),TbComShopApply::getShopComCountry, entityParam.getShopComCountry());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComState()),TbComShopApply::getShopComState, entityParam.getShopComState());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComDistrict()),TbComShopApply::getShopComDistrict, entityParam.getShopComDistrict());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getAttachAddrr()),TbComShopApply::getAttachAddrr, entityParam.getAttachAddrr());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getIdentification()),TbComShopApply::getIdentification, entityParam.getIdentification());
         chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComPosCode()),TbComShopApply::getShopComPosCode, entityParam.getShopComPosCode());

//         chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSysApplyDate()),TbComShopApply::getSysApplyDate, entityParam.getSysApplyDate());
//         chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSysApplyState()),TbComShopApply::getSysApplyState, entityParam.getSysApplyState());

//         if (ObjectUtil.isNotEmpty(entityParam.getCreateShopSaveOrSubmit()) && "提交".equals(entityParam.getCreateShopSaveOrSubmit())) {
//             chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCreateShopSaveOrSubmit()),TbComShopApply::getSysApplyState, entityParam.getSysApplyState());
//         }


         return chainWrapper;
     }

     /**
     * 通过主键删除数据
     *
     * @param sysApplyId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal sysApplyId){
        //根据申请编号删除申请明细
        LambdaQueryWrapper<TbComShopApplyDet> detWrapper = new LambdaQueryWrapper<>();
        detWrapper.eq(TbComShopApplyDet::getSysApplyId, sysApplyId);
        tbComShopApplyDetMapper.delete(detWrapper);

        int total = tbComShopApplyMapper.deleteById(sysApplyId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param sysApplyIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysApplyIdList){
         int delCount = tbComShopApplyMapper.deleteBatchIds(sysApplyIdList);
         if (sysApplyIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }



     /**
      * 分页连表查询
      * @param shopApplyParam
      * @param current
      * @param size
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public Page<TbComShopApplyResult> joinPaginQuery(TbComShopApplyParam shopApplyParam, long current, long size) {
         //1. 构建动态查询条件
         MPJLambdaWrapper<TbComShopApply> queryWrapper = MPJWrappers.<TbComShopApply>lambdaJoin()
                 .selectAll(TbComShopApply.class)
                 .selectCollection(TbComShopApplyDet.class,TbComShopApplyResult::getApplyDetList)
                 .selectCollection(TbComShopApplyBankCollectTask.class,TbComShopApplyResult::getBankCollectTaskList)
                 .selectCollection(TbComShopApplyTaxnTask.class,TbComShopApplyResult::getTaxnTaskList)
                 .leftJoin(TbComShopApplyDet.class,TbComShopApplyDet::getSysApplyId,TbComShopApply::getSysApplyId)
                 .leftJoin(TbComShopApplyBankCollectTask.class,TbComShopApplyBankCollectTask::getSysApplyDetId,TbComShopApplyDet::getSysApplyDetId)
                 .leftJoin(TbComShopApplyTaxnTask.class,TbComShopApplyTaxnTask::getSysApplyDetId,TbComShopApplyDet::getSysApplyDetId)
                 .like(ObjectUtil.isNotEmpty(shopApplyParam.getElePlatformName()),TbComShopApply::getElePlatformName, shopApplyParam.getElePlatformName())
                 .like(ObjectUtil.isNotEmpty(shopApplyParam.getShopNameSimple()),TbComShopApply::getShopNameSimple, shopApplyParam.getShopNameSimple())
                 .eq(ObjectUtil.isNotEmpty(shopApplyParam.getSysApplyState()),TbComShopApply::getSysApplyState, shopApplyParam.getSysApplyState())
                 .in(CollUtil.isNotEmpty(shopApplyParam.getComNameCnList()),TbComShopApply::getComNameCn,shopApplyParam.getComNameCnList())
                 .like(ObjectUtil.isNotEmpty(shopApplyParam.getComNameCn()),TbComShopApply::getComNameCn, shopApplyParam.getComNameCn())
                 .inSql(ObjectUtil.isNotNull(shopApplyParam.getShopTaxnState()) && shopApplyParam.getShopTaxnState()>0,TbComShopApply::getSysApplyId,
                         "SELECT DISTINCT D.SYS_APPLY_ID  FROM TB_COM_SHOP_APPLY_TAXN_TASK TT " +
                                 "LEFT  JOIN  TB_COM_SHOP_APPLY_DET D ON TT.SYS_APPLY_DET_ID=D.SYS_APPLY_DET_ID  " +
                                 "WHERE TT.TAXN_TASK_STATE='未完成' ")
                 .inSql(ObjectUtil.isNotNull(shopApplyParam.getShopBankColleAccState()) && shopApplyParam.getShopBankColleAccState()>0,TbComShopApply::getSysApplyId,
                         "SELECT DISTINCT d.SYS_APPLY_ID  FROM Tb_Com_Shop_Apply_Bank_Collect_Task tk " +
                                 "LEFT  JOIN  Tb_Com_Shop_Apply_Det d ON tk.sys_Apply_Det_Id=d.sys_Apply_Det_Id  " +
                                 "WHERE tk.sys_Apply_Det_Bct_State='未完成' ")
                 .orderBy(Boolean.TRUE,Boolean.FALSE,TbComShopApply::getSysApplyDate);

         //2. 执行分页查询
         Page<TbComShopApplyResult> pagin = new Page<>(current , size , true);
         IPage<TbComShopApplyResult> selectResult = tbComShopApplyMapper.selectJoinPage(pagin , TbComShopApplyResult.class,queryWrapper);
         pagin.setPages(selectResult.getPages());
         pagin.setTotal(selectResult.getTotal());
         pagin.setRecords(selectResult.getRecords());
         //3. 返回结果
         return pagin;
     }

 }