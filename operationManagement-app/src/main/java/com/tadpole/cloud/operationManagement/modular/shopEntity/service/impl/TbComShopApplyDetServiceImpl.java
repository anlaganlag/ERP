package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.*;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyDetParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyDetResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopApplyDetMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopApplyMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 /**
 * 资源-店铺申请详情表;(Tb_Com_Shop_Apply_Det)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopApplyDetServiceImpl extends ServiceImpl<TbComShopApplyDetMapper, TbComShopApplyDet>  implements TbComShopApplyDetService{
    @Resource
    private TbComShopApplyDetMapper tbComShopApplyDetMapper;
    @Resource
    private TbComShopApplyMapper tbComShopApplyMapper;

     @Resource
     private TbComShopApplyBankCollectTaskService bankCollectTaskService;

     @Resource
     private TbComShopApplyTaxnTaskService taxnTaskService;

     @Resource
     private TbComShopService tbComShopService;

     @Resource
     private TbComTaxNumService tbComTaxNumService;
     @Resource
     private TbComShopDataDownService tbComShopDataDownService;

    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyDet queryById(BigDecimal sysApplyDetId){
        return tbComShopApplyDetMapper.selectById(sysApplyDetId);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopApplyDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopApplyDetResult> paginQuery(TbComShopApplyDetParam tbComShopApplyDet, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopApplyDet> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopApplyDet.getSysCreatePerCode())){
            queryWrapper.eq(TbComShopApplyDet::getSysCreatePerCode, tbComShopApplyDet.getSysCreatePerCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getSysCreatePerName())){
            queryWrapper.eq(TbComShopApplyDet::getSysCreatePerName, tbComShopApplyDet.getSysCreatePerName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopName())){
            queryWrapper.eq(TbComShopApplyDet::getShopName, tbComShopApplyDet.getShopName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopIsExtPay())){
            queryWrapper.eq(TbComShopApplyDet::getShopIsExtPay, tbComShopApplyDet.getShopIsExtPay());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getCountryCode())){
            queryWrapper.eq(TbComShopApplyDet::getCountryCode, tbComShopApplyDet.getCountryCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopNamePlat())){
            queryWrapper.eq(TbComShopApplyDet::getShopNamePlat, tbComShopApplyDet.getShopNamePlat());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getSellerId())){
            queryWrapper.eq(TbComShopApplyDet::getSellerId, tbComShopApplyDet.getSellerId());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getMarketplaceId())){
            queryWrapper.eq(TbComShopApplyDet::getMarketplaceId, tbComShopApplyDet.getMarketplaceId());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getAwsAccessKeyId())){
            queryWrapper.eq(TbComShopApplyDet::getAwsAccessKeyId, tbComShopApplyDet.getAwsAccessKeyId());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getCId())){
            queryWrapper.eq(TbComShopApplyDet::getCId, tbComShopApplyDet.getCId());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopRemLogAddress())){
            queryWrapper.eq(TbComShopApplyDet::getShopRemLogAddress, tbComShopApplyDet.getShopRemLogAddress());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopMail())){
            queryWrapper.eq(TbComShopApplyDet::getShopMail, tbComShopApplyDet.getShopMail());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopMailType())){
            queryWrapper.eq(TbComShopApplyDet::getShopMailType, tbComShopApplyDet.getShopMailType());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopComPosCode())){
            queryWrapper.eq(TbComShopApplyDet::getShopComPosCode, tbComShopApplyDet.getShopComPosCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopBenMail1())){
            queryWrapper.eq(TbComShopApplyDet::getShopBenMail1, tbComShopApplyDet.getShopBenMail1());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopBenMail2())){
            queryWrapper.eq(TbComShopApplyDet::getShopBenMail2, tbComShopApplyDet.getShopBenMail2());
        }
        if(StrUtil.isNotBlank(tbComShopApplyDet.getShopColAccBankRemark())){
            queryWrapper.eq(TbComShopApplyDet::getShopColAccBankRemark, tbComShopApplyDet.getShopColAccBankRemark());
        }
        //2. 执行分页查询
        Page<TbComShopApplyDetResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopApplyDetResult> selectResult = tbComShopApplyDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopApplyDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyDet insert(TbComShopApplyDet tbComShopApplyDet){
        tbComShopApplyDetMapper.insert(tbComShopApplyDet);
        return tbComShopApplyDet;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyDet update(TbComShopApplyDet entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopApplyDet> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopApplyDetMapper);
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSysCreatePerCode()),TbComShopApplyDet::getSysCreatePerCode, entityParam.getSysCreatePerCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSysCreatePerName()),TbComShopApplyDet::getSysCreatePerName, entityParam.getSysCreatePerName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopName()),TbComShopApplyDet::getShopName, entityParam.getShopName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopIsExtPay()),TbComShopApplyDet::getShopIsExtPay, entityParam.getShopIsExtPay());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCountryCode()),TbComShopApplyDet::getCountryCode, entityParam.getCountryCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopNamePlat()),TbComShopApplyDet::getShopNamePlat, entityParam.getShopNamePlat());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSellerId()),TbComShopApplyDet::getSellerId, entityParam.getSellerId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getMarketplaceId()),TbComShopApplyDet::getMarketplaceId, entityParam.getMarketplaceId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getAwsAccessKeyId()),TbComShopApplyDet::getAwsAccessKeyId, entityParam.getAwsAccessKeyId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCId()),TbComShopApplyDet::getCId, entityParam.getCId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopRemLogAddress()),TbComShopApplyDet::getShopRemLogAddress, entityParam.getShopRemLogAddress());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopMail()),TbComShopApplyDet::getShopMail, entityParam.getShopMail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopMailType()),TbComShopApplyDet::getShopMailType, entityParam.getShopMailType());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComPosCode()),TbComShopApplyDet::getShopComPosCode, entityParam.getShopComPosCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBenMail1()),TbComShopApplyDet::getShopBenMail1, entityParam.getShopBenMail1());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBenMail2()),TbComShopApplyDet::getShopBenMail2, entityParam.getShopBenMail2());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccBankRemark()),TbComShopApplyDet::getShopColAccBankRemark, entityParam.getShopColAccBankRemark());

        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSysApplyId()),TbComShopApplyDet::getSysApplyId, entityParam.getSysApplyId());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSysCreateDate()),TbComShopApplyDet::getSysCreateDate, entityParam.getSysCreateDate());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getIsVirtualShop()),TbComShopApplyDet::getIsVirtualShop, entityParam.getIsVirtualShop());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopBankColleAcc()),TbComShopApplyDet::getShopBankColleAcc, entityParam.getShopBankColleAcc());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopTaxn()),TbComShopApplyDet::getShopTaxn, entityParam.getShopTaxn());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopDataDownTask()),TbComShopApplyDet::getShopDataDownTask, entityParam.getShopDataDownTask());
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopApplyDet::getSysApplyDetId, entityParam.getSysApplyDetId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getSysApplyDetId());
        }else{
            return entityParam;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysApplyDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal sysApplyDetId){
        int total = tbComShopApplyDetMapper.deleteById(sysApplyDetId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param sysApplyDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysApplyDetIdList){
         int delCount = tbComShopApplyDetMapper.deleteBatchIds(sysApplyDetIdList);
         if (sysApplyDetIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 店铺财务任务处理完后，同步数据
      * @param sysApplyDetId
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public ResponseData syncData(BigDecimal sysApplyDetId) {
         MPJLambdaWrapper<TbComShopApplyDet> queryWrapper = MPJWrappers.<TbComShopApplyDet>lambdaJoin()
                 .selectAll(TbComShopApplyDet.class)
                 .eq(TbComShopApplyDet::getSysApplyDetId,sysApplyDetId)
                 .notInSql(TbComShopApplyDet::getSysApplyDetId
                         ,"SELECT DISTINCT sys_Apply_Det_Id FROM ( " +
                         "     SELECT sys_Apply_Det_Id  FROM Tb_Com_Shop_Apply_Bank_Collect_Task b WHERE b.SYS_APPLY_DET_BCT_STATE  ='未完成' " +
                         "     UNION ALL  " +
                         "     SELECT sys_Apply_Det_Id FROM Tb_Com_Shop_Apply_Taxn_Task t WHERE t.TAXN_TASK_STATE  ='未完成' " +
                         "    ) ");
         TbComShopApplyDet applyDet = tbComShopApplyDetMapper.selectOne(queryWrapper);
         if (ObjectUtil.isNull(applyDet)) {
             return ResponseData.success();
         }
         TbComShopApply shopApply = tbComShopApplyMapper.selectById(applyDet.getSysApplyId());

         TbComShop comShop = BeanUtil.copyProperties(shopApply, TbComShop.class);
         comShop.setShopComNameCn(shopApply.getComNameCn());
         comShop.setShopMainBody(shopApply.getComNameCn());
         BeanUtil.copyProperties(applyDet,comShop);

         TbComShopApplyBankCollectTask bankCollectTask = bankCollectTaskService.getBankCollectTaskByDetId(sysApplyDetId);
         if (ObjectUtil.isNotNull(bankCollectTask)) {
             BeanUtil.copyProperties(bankCollectTask,comShop);
         }


         TbComShopApplyTaxnTask taxnTask=taxnTaskService.getTaxnTaskByDetId(sysApplyDetId);
         if (ObjectUtil.isNotNull(taxnTask)) {
             BeanUtil.copyProperties(taxnTask,comShop);
             TbComTaxNum tbComTaxNum = new TbComTaxNum();
             BeanUtil.copyProperties(taxnTask, tbComTaxNum);
             tbComTaxNumService.saveOrUpdate(tbComTaxNum);
         }
         comShop.setShopState("正常");
         comShop.setShopCreateTime(new Date());
         tbComShopService.saveOrUpdate(comShop);
         //是否创建店铺数据下载任务 0：不创建店铺数据下载任务，1：创建下载任务
         if (ObjectUtil.isNotEmpty(comShop.getElePlatformName()) &&
                 ("Amazon".equals(comShop.getElePlatformName()) || "Walmart".equals(comShop.getElePlatformName()))) {
             tbComShopDataDownService.createTask(comShop);
             log.info("店铺【{}】数据下载新增任务成功", comShop.getShopName());
         } else {
             log.info("店铺【{}】不是Amazon，Walmart平台不需要下载店铺数据",comShop.getShopName());
         }
         //同步到k3
         List<String> shopNameList = new ArrayList<>();
         shopNameList.add(comShop.getShopName());
         return tbComShopService.syncShopInfo(shopNameList);
     }
 }