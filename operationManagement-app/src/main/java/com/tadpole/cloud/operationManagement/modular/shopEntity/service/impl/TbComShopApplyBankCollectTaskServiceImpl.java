package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyBankCollectTask;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopApplyBankCollectTaskMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopApplyMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyBankCollectTaskParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyBankCollectTaskResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyBankCollectTaskService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyDetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
 /**
 * 资源-店铺申请银行收款任务;(Tb_Com_Shop_Apply_Bank_Collect_Task)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopApplyBankCollectTaskServiceImpl extends ServiceImpl<TbComShopApplyBankCollectTaskMapper, TbComShopApplyBankCollectTask>  implements TbComShopApplyBankCollectTaskService{
    @Resource
    private TbComShopApplyBankCollectTaskMapper tbComShopApplyBankCollectTaskMapper;

     @Resource
     private TbComShopApplyMapper tbComShopApplyMapper;

     @Resource
     private TbComShopApplyDetService tbComShopApplyDetService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyDetBctId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyBankCollectTask queryById(BigDecimal sysApplyDetBctId){
        return tbComShopApplyBankCollectTaskMapper.selectById(sysApplyDetBctId);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopApplyBankCollectTask 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopApplyBankCollectTaskResult> paginQuery(TbComShopApplyBankCollectTaskParam tbComShopApplyBankCollectTask, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopApplyBankCollectTask> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getSysLastUpdPerName())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getSysLastUpdPerName, tbComShopApplyBankCollectTask.getSysLastUpdPerName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getSysLastUpdPerCode())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getSysLastUpdPerCode, tbComShopApplyBankCollectTask.getSysLastUpdPerCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopName())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopName, tbComShopApplyBankCollectTask.getShopName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopColAccBank())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopColAccBank, tbComShopApplyBankCollectTask.getShopColAccBank());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopColAccBankMainBody())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopColAccBankMainBody, tbComShopApplyBankCollectTask.getShopColAccBankMainBody());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopColAccNo())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopColAccNo, tbComShopApplyBankCollectTask.getShopColAccNo());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopColCurrency())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopColCurrency, tbComShopApplyBankCollectTask.getShopColCurrency());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopColAccEmail())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopColAccEmail, tbComShopApplyBankCollectTask.getShopColAccEmail());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopBic())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopBic, tbComShopApplyBankCollectTask.getShopBic());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopRoutingNumber())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopRoutingNumber, tbComShopApplyBankCollectTask.getShopRoutingNumber());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopBsb())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopBsb, tbComShopApplyBankCollectTask.getShopBsb());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopBankCode())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopBankCode, tbComShopApplyBankCollectTask.getShopBankCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopBranchCode())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopBranchCode, tbComShopApplyBankCollectTask.getShopBranchCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopAccountType())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopAccountType, tbComShopApplyBankCollectTask.getShopAccountType());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopSortCode())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopSortCode, tbComShopApplyBankCollectTask.getShopSortCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopIban())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopIban, tbComShopApplyBankCollectTask.getShopIban());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getSysApplyDetBctState())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getSysApplyDetBctState, tbComShopApplyBankCollectTask.getSysApplyDetBctState());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopAccountHoldName())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopAccountHoldName, tbComShopApplyBankCollectTask.getShopAccountHoldName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopAccountNo())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopAccountNo, tbComShopApplyBankCollectTask.getShopAccountNo());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopBankName())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopBankName, tbComShopApplyBankCollectTask.getShopBankName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopComAddrCn())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopComAddrCn, tbComShopApplyBankCollectTask.getShopComAddrCn());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopLegPersonCn())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopLegPersonCn, tbComShopApplyBankCollectTask.getShopLegPersonCn());
        }
        if(StrUtil.isNotBlank(tbComShopApplyBankCollectTask.getShopComRegCountry())){
            queryWrapper.eq(TbComShopApplyBankCollectTask::getShopComRegCountry, tbComShopApplyBankCollectTask.getShopComRegCountry());
        }
        //2. 执行分页查询
        Page<TbComShopApplyBankCollectTaskResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopApplyBankCollectTaskResult> selectResult = tbComShopApplyBankCollectTaskMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopApplyBankCollectTask 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyBankCollectTask insert(TbComShopApplyBankCollectTask tbComShopApplyBankCollectTask){
        tbComShopApplyBankCollectTaskMapper.insert(tbComShopApplyBankCollectTask);
        return tbComShopApplyBankCollectTask;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyBankCollectTask update(TbComShopApplyBankCollectTaskParam entityParam){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopApplyBankCollectTask> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopApplyBankCollectTaskMapper);

        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopName()),TbComShopApplyBankCollectTask::getShopName, entityParam.getShopName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccBank()),TbComShopApplyBankCollectTask::getShopColAccBank, entityParam.getShopColAccBank());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccBankMainBody()),TbComShopApplyBankCollectTask::getShopColAccBankMainBody, entityParam.getShopColAccBankMainBody());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccNo()),TbComShopApplyBankCollectTask::getShopColAccNo, entityParam.getShopColAccNo());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColCurrency()),TbComShopApplyBankCollectTask::getShopColCurrency, entityParam.getShopColCurrency());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopColAccEmail()),TbComShopApplyBankCollectTask::getShopColAccEmail, entityParam.getShopColAccEmail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBic()),TbComShopApplyBankCollectTask::getShopBic, entityParam.getShopBic());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopRoutingNumber()),TbComShopApplyBankCollectTask::getShopRoutingNumber, entityParam.getShopRoutingNumber());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBsb()),TbComShopApplyBankCollectTask::getShopBsb, entityParam.getShopBsb());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBankCode()),TbComShopApplyBankCollectTask::getShopBankCode, entityParam.getShopBankCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBranchCode()),TbComShopApplyBankCollectTask::getShopBranchCode, entityParam.getShopBranchCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopAccountType()),TbComShopApplyBankCollectTask::getShopAccountType, entityParam.getShopAccountType());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopSortCode()),TbComShopApplyBankCollectTask::getShopSortCode, entityParam.getShopSortCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopIban()),TbComShopApplyBankCollectTask::getShopIban, entityParam.getShopIban());

        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopAccountHoldName()),TbComShopApplyBankCollectTask::getShopAccountHoldName, entityParam.getShopAccountHoldName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopAccountNo()),TbComShopApplyBankCollectTask::getShopAccountNo, entityParam.getShopAccountNo());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBankName()),TbComShopApplyBankCollectTask::getShopBankName, entityParam.getShopBankName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComAddrCn()),TbComShopApplyBankCollectTask::getShopComAddrCn, entityParam.getShopComAddrCn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopLegPersonCn()),TbComShopApplyBankCollectTask::getShopLegPersonCn, entityParam.getShopLegPersonCn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopComRegCountry()),TbComShopApplyBankCollectTask::getShopComRegCountry, entityParam.getShopComRegCountry());

        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSysApplyDetId()),TbComShopApplyBankCollectTask::getSysApplyDetId, entityParam.getSysApplyDetId());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopEffeRangeStart()),TbComShopApplyBankCollectTask::getShopEffeRangeStart, entityParam.getShopEffeRangeStart());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopEffeRangeEnd()),TbComShopApplyBankCollectTask::getShopEffeRangeEnd, entityParam.getShopEffeRangeEnd());
        String sysApplyDetBctState = "未完成";
        if ("提交".equals(entityParam.getSaveOrSubmit())) {
            sysApplyDetBctState = "完成";
        }
        chainWrapper.set(TbComShopApplyBankCollectTask::getSysLastUpdPerName, loginUser.getName());
        chainWrapper.set(TbComShopApplyBankCollectTask::getSysLastUpdPerCode, loginUser.getAccount());
        chainWrapper.set(TbComShopApplyBankCollectTask::getSysApplyDetBctState, sysApplyDetBctState);
        chainWrapper.set(TbComShopApplyBankCollectTask::getSysLastUpdDate,new Date());

        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopApplyBankCollectTask::getSysApplyDetBctId, entityParam.getSysApplyDetBctId());
        boolean ret = chainWrapper.update();

        //店铺创建是否完成，同步数据
        ResponseData result= tbComShopApplyDetService.syncData(entityParam.getSysApplyDetId());
        //所有财务任务是否完成，更新申请状态
        tbComShopApplyMapper.updateShopApplyState();

        //3. 更新成功了，查询最最对象返回
            return queryById(entityParam.getSysApplyDetBctId());

    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysApplyDetBctId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal sysApplyDetBctId){
        int total = tbComShopApplyBankCollectTaskMapper.deleteById(sysApplyDetBctId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param sysApplyDetBctIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysApplyDetBctIdList){
         int delCount = tbComShopApplyBankCollectTaskMapper.deleteBatchIds(sysApplyDetBctIdList);
         if (sysApplyDetBctIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 根据申请明细ID 查找银行任务信息
      * @param sysApplyDetId
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public TbComShopApplyBankCollectTask getBankCollectTaskByDetId(BigDecimal sysApplyDetId) {
         LambdaQueryWrapper<TbComShopApplyBankCollectTask> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbComShopApplyBankCollectTask::getSysApplyDetId, sysApplyDetId);
         List<TbComShopApplyBankCollectTask> taskList = tbComShopApplyBankCollectTaskMapper.selectList(queryWrapper);
         if (ObjectUtil.isNotEmpty(taskList)) {
             return taskList.get(0);
         }
         return null;
     }
 }