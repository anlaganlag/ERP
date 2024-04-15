package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopRecBrankChangeAcc;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopRecBrankChangeAccParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopRecBrankChangeAccResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopRecBrankChangeAccMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopRecBrankChangeAccService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
 /**
 * 资源-店铺收款银行账号变更;(Tb_Com_Shop_Rec_Brank_Change_Acc)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopRecBrankChangeAccServiceImpl extends ServiceImpl<TbComShopRecBrankChangeAccMapper, TbComShopRecBrankChangeAcc>  implements TbComShopRecBrankChangeAccService{
    @Resource
    private TbComShopRecBrankChangeAccMapper tbComShopRecBrankChangeAccMapper;
    @Resource
    private TbComShopService tbComShopService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopRecBrankChangeAcc queryById(BigDecimal sysId){
        return tbComShopRecBrankChangeAccMapper.selectById(sysId);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopRecBrankChangeAccResult> paginQuery(TbComShopRecBrankChangeAccParam queryParam, long current, long size){

        MPJLambdaWrapper<TbComShopRecBrankChangeAcc> queryWrapper = MPJWrappers.<TbComShopRecBrankChangeAcc>lambdaJoin()
                .selectAll(TbComShopRecBrankChangeAcc.class)
                .select(TbComShop::getElePlatformName,TbComShop::getShopNameSimple,TbComShop::getCountryCode,TbComShop::getShopMainBody)
                .leftJoin(TbComShop.class,TbComShop::getShopName,TbComShopRecBrankChangeAcc::getShopName)
                .eq(ObjectUtil.isNotEmpty(queryParam.getElePlatformName()),TbComShop::getElePlatformName, queryParam.getElePlatformName())
                .eq(ObjectUtil.isNotEmpty(queryParam.getShopNameSimple()),TbComShop::getShopNameSimple, queryParam.getShopNameSimple())
                .eq(ObjectUtil.isNotEmpty(queryParam.getCountryCode()),TbComShop::getCountryCode, queryParam.getCountryCode())
                .eq(ObjectUtil.isNotEmpty(queryParam.getSysChangeStatus()),TbComShopRecBrankChangeAcc::getSysChangeStatus, queryParam.getSysChangeStatus())
                .orderByDesc(TbComShopRecBrankChangeAcc::getSysApplyDate);

        //2. 执行分页查询
        Page<TbComShopRecBrankChangeAccResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopRecBrankChangeAccResult> selectResult = tbComShopRecBrankChangeAccMapper.selectJoinPage(pagin , TbComShopRecBrankChangeAccResult.class,queryWrapper);

        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

     /**
      * 新增数据
      *
      * @param tbComShopRecBrankChangeAcc 实例对象
      * @return 实例对象
      */
     @DataSource(name = "stocking")
     @Override
     public TbComShopRecBrankChangeAcc insert(TbComShopRecBrankChangeAcc tbComShopRecBrankChangeAcc) {
         LoginUser loginUser = LoginContext.me().getLoginUser();
         tbComShopRecBrankChangeAcc.setSysId(BigDecimal.valueOf(IdWorker.getId()));
         tbComShopRecBrankChangeAcc.setSysAppChangeStatus(BigDecimal.ZERO);
         tbComShopRecBrankChangeAcc.setSysChangeStatus("已申请");
         tbComShopRecBrankChangeAcc.setSysApplyDate(new Date());
         tbComShopRecBrankChangeAcc.setSyApplyPerName2(loginUser.getName());
         tbComShopRecBrankChangeAcc.setSysApplyPerCode2(loginUser.getAccount());
         tbComShopRecBrankChangeAccMapper.insert(tbComShopRecBrankChangeAcc);
         return tbComShopRecBrankChangeAcc;
     }
    
    /** 
     * 更新数据
     *
     * @param tbComShopRecBrankChangeAcc 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopRecBrankChangeAcc update(TbComShopRecBrankChangeAcc tbComShopRecBrankChangeAcc){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopRecBrankChangeAcc> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopRecBrankChangeAccMapper);
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopName())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopName, tbComShopRecBrankChangeAcc.getShopName());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccBank())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccBank, tbComShopRecBrankChangeAcc.getShopColAccBank());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccBankMainBody())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccBankMainBody, tbComShopRecBrankChangeAcc.getShopColAccBankMainBody());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccNo())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccNo, tbComShopRecBrankChangeAcc.getShopColAccNo());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColCurrency())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColCurrency, tbComShopRecBrankChangeAcc.getShopColCurrency());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccEmail())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccEmail, tbComShopRecBrankChangeAcc.getShopColAccEmail());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBic())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBic, tbComShopRecBrankChangeAcc.getShopBic());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopRoutingNumber())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopRoutingNumber, tbComShopRecBrankChangeAcc.getShopRoutingNumber());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBsb())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBsb, tbComShopRecBrankChangeAcc.getShopBsb());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBanKCode())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBanKCode, tbComShopRecBrankChangeAcc.getShopBanKCode());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBranchCode())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBranchCode, tbComShopRecBrankChangeAcc.getShopBranchCode());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopAccountType())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopAccountType, tbComShopRecBrankChangeAcc.getShopAccountType());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopSortCode())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopSortCode, tbComShopRecBrankChangeAcc.getShopSortCode());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopIban())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopIban, tbComShopRecBrankChangeAcc.getShopIban());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccBankOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccBankOrig, tbComShopRecBrankChangeAcc.getShopColAccBankOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccBankMainBodyOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccBankMainBodyOrig, tbComShopRecBrankChangeAcc.getShopColAccBankMainBodyOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccNoOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccNoOrig, tbComShopRecBrankChangeAcc.getShopColAccNoOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColCurrencyOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColCurrencyOrig, tbComShopRecBrankChangeAcc.getShopColCurrencyOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopColAccEmailOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopColAccEmailOrig, tbComShopRecBrankChangeAcc.getShopColAccEmailOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBicOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBicOrig, tbComShopRecBrankChangeAcc.getShopBicOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopRoutingNumberOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopRoutingNumberOrig, tbComShopRecBrankChangeAcc.getShopRoutingNumberOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBsbOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBsbOrig, tbComShopRecBrankChangeAcc.getShopBsbOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBanKCodeOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBanKCodeOrig, tbComShopRecBrankChangeAcc.getShopBanKCodeOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopBranchCodeOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopBranchCodeOrig, tbComShopRecBrankChangeAcc.getShopBranchCodeOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopAccountTypeOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopAccountTypeOrig, tbComShopRecBrankChangeAcc.getShopAccountTypeOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopSortCodeOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopSortCodeOrig, tbComShopRecBrankChangeAcc.getShopSortCodeOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getShopIbanOrig())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getShopIbanOrig, tbComShopRecBrankChangeAcc.getShopIbanOrig());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getSysFinishPerName())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getSysFinishPerName, tbComShopRecBrankChangeAcc.getSysFinishPerName());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getSysFinishPerCode())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getSysFinishPerCode, tbComShopRecBrankChangeAcc.getSysFinishPerCode());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getSyApplyPerName2())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getSyApplyPerName2, tbComShopRecBrankChangeAcc.getSyApplyPerName2());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getSysApplyPerCode2())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getSysApplyPerCode2, tbComShopRecBrankChangeAcc.getSysApplyPerCode2());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getSysRecPerName())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getSysRecPerName, tbComShopRecBrankChangeAcc.getSysRecPerName());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getSysRecPerCode())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getSysRecPerCode, tbComShopRecBrankChangeAcc.getSysRecPerCode());
        }
        if(StrUtil.isNotBlank(tbComShopRecBrankChangeAcc.getSysChangeStatus())){
            chainWrapper.set(TbComShopRecBrankChangeAcc::getSysChangeStatus, tbComShopRecBrankChangeAcc.getSysChangeStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopRecBrankChangeAcc::getSysId, tbComShopRecBrankChangeAcc.getSysId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComShopRecBrankChangeAcc.getSysId());
        }else{
            return tbComShopRecBrankChangeAcc;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal sysId){
        int total = tbComShopRecBrankChangeAccMapper.deleteById(sysId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param sysIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysIdList){
         int delCount = tbComShopRecBrankChangeAccMapper.deleteBatchIds(sysIdList);
         if (sysIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 根据收款账号变更申请记录ID 更新状态为已接受
      * @param sysId
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public ResponseData updateReceive(BigDecimal sysId) {
         LoginUser loginUser = LoginContext.me().getLoginUser();

         TbComShopRecBrankChangeAcc brankChangeAcc = tbComShopRecBrankChangeAccMapper.selectById(sysId);
         if (! brankChangeAcc.getSysAppChangeStatus().equals(BigDecimal.ZERO)) {//申请变更状态(0:变更申请、1:接收确认、2:完成确认)
             return ResponseData.error("变更申请记录的状态已经发生变化，请刷新页面！请勿重复操作！");
         }
         if (! "已申请".equals(brankChangeAcc.getSysChangeStatus())) {//变更状态(已申请、已接收,已完成)
             return ResponseData.error("变更申请状态已经变更了，请刷新页面！请勿重复操作！");
         }

         brankChangeAcc.setSysAppChangeStatus(BigDecimal.valueOf(1L));
         brankChangeAcc.setSysChangeStatus("已接收");
         brankChangeAcc.setSysRecDate(new Date());
         brankChangeAcc.setSysRecPerName(loginUser.getName());
         brankChangeAcc.setSysRecPerCode(loginUser.getAccount());
         tbComShopRecBrankChangeAccMapper.updateById(brankChangeAcc);
         return ResponseData.success(brankChangeAcc);
     }

     /**
      * 根据收款账号变更申请记录ID 更新状态为完成
      * @param sysId
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public ResponseData updateFinish(BigDecimal sysId) {
         LoginUser loginUser = LoginContext.me().getLoginUser();

         TbComShopRecBrankChangeAcc brankChangeAcc = tbComShopRecBrankChangeAccMapper.selectById(sysId);
         if (! brankChangeAcc.getSysAppChangeStatus().equals(BigDecimal.ONE)) {//申请变更状态(0:变更申请、1:接收确认、2:完成确认)
             return ResponseData.error("变更申请记录的状态已经发生变化，请刷新页面！请勿重复操作！");
         }
         if (! "已接收".equals(brankChangeAcc.getSysChangeStatus())) {//变更状态(已申请、已接收,已完成)
             return ResponseData.error("变更申请状态已经变更了，请刷新页面！请勿重复操作！");
         }

         brankChangeAcc.setSysAppChangeStatus(BigDecimal.valueOf(2L));
         brankChangeAcc.setSysChangeStatus("已完成");
         brankChangeAcc.setSysFinishDate(new Date());
         brankChangeAcc.setSysFinishPerName(loginUser.getName());
         brankChangeAcc.setSysFinishPerCode(loginUser.getAccount());
         tbComShopRecBrankChangeAccMapper.updateById(brankChangeAcc);
         //更新tbComShop表数据
         tbComShopService.recBrankChangeAcc(brankChangeAcc);
         return ResponseData.success();
     }
 }