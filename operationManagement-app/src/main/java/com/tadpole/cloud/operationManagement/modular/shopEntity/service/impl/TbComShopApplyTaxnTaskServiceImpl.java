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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyTaxnTask;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopApplyMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopApplyTaxnTaskMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyTaxnTaskParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyTaxnTaskResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyDetService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyTaxnTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
 /**
 * 资源-店铺申请税务任务;(Tb_Com_Shop_Apply_Taxn_Task)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopApplyTaxnTaskServiceImpl extends ServiceImpl<TbComShopApplyTaxnTaskMapper, TbComShopApplyTaxnTask>  implements TbComShopApplyTaxnTaskService{
    @Resource
    private TbComShopApplyTaxnTaskMapper tbComShopApplyTaxnTaskMapper;
    @Resource
    private TbComShopApplyMapper tbComShopApplyMapper;

    @Resource
    private TbComShopApplyDetService tbComShopApplyDetService;

    /** 
     * 通过ID查询单条数据 
     *
     * @param taxnId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyTaxnTask queryById(BigDecimal taxnId){
        return tbComShopApplyTaxnTaskMapper.selectById(taxnId);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopApplyTaxnTask 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopApplyTaxnTaskResult> paginQuery(TbComShopApplyTaxnTaskParam tbComShopApplyTaxnTask, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopApplyTaxnTask> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getSysLastUpdPerName())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getSysLastUpdPerName, tbComShopApplyTaxnTask.getSysLastUpdPerName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getSysLastUpdPerCode())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getSysLastUpdPerCode, tbComShopApplyTaxnTask.getSysLastUpdPerCode());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getShopName())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getShopName, tbComShopApplyTaxnTask.getShopName());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnOverseas())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnOverseas, tbComShopApplyTaxnTask.getTaxnOverseas());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnLocal())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnLocal, tbComShopApplyTaxnTask.getTaxnLocal());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnEori())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnEori, tbComShopApplyTaxnTask.getTaxnEori());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnBelongToCom())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnBelongToCom, tbComShopApplyTaxnTask.getTaxnBelongToCom());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnRegAddress())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnRegAddress, tbComShopApplyTaxnTask.getTaxnRegAddress());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnAddrOfAccountant())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnAddrOfAccountant, tbComShopApplyTaxnTask.getTaxnAddrOfAccountant());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnDecMethod())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnDecMethod, tbComShopApplyTaxnTask.getTaxnDecMethod());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnDecTime())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnDecTime, tbComShopApplyTaxnTask.getTaxnDecTime());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnFiles())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnFiles, tbComShopApplyTaxnTask.getTaxnFiles());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnRegEmail())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnRegEmail, tbComShopApplyTaxnTask.getTaxnRegEmail());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnRegTel())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnRegTel, tbComShopApplyTaxnTask.getTaxnRegTel());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnAudEmail())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnAudEmail, tbComShopApplyTaxnTask.getTaxnAudEmail());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnAltEmail1())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnAltEmail1, tbComShopApplyTaxnTask.getTaxnAltEmail1());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnAltEmail2())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnAltEmail2, tbComShopApplyTaxnTask.getTaxnAltEmail2());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnAgency())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnAgency, tbComShopApplyTaxnTask.getTaxnAgency());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnAgencyTel())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnAgencyTel, tbComShopApplyTaxnTask.getTaxnAgencyTel());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnAgencyAddress())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnAgencyAddress, tbComShopApplyTaxnTask.getTaxnAgencyAddress());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnState())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnState, tbComShopApplyTaxnTask.getTaxnState());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnCate())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnCate, tbComShopApplyTaxnTask.getTaxnCate());
        }
        if(StrUtil.isNotBlank(tbComShopApplyTaxnTask.getTaxnTaskState())){
            queryWrapper.eq(TbComShopApplyTaxnTask::getTaxnTaskState, tbComShopApplyTaxnTask.getTaxnTaskState());
        }
        //2. 执行分页查询
        Page<TbComShopApplyTaxnTaskResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopApplyTaxnTaskResult> selectResult = tbComShopApplyTaxnTaskMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopApplyTaxnTask 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyTaxnTask insert(TbComShopApplyTaxnTask tbComShopApplyTaxnTask){
        tbComShopApplyTaxnTaskMapper.insert(tbComShopApplyTaxnTask);
        return tbComShopApplyTaxnTask;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopApplyTaxnTask update(TbComShopApplyTaxnTaskParam entityParam){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopApplyTaxnTask> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopApplyTaxnTaskMapper);

        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopName()),TbComShopApplyTaxnTask::getShopName, entityParam.getShopName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnOverseas()),TbComShopApplyTaxnTask::getTaxnOverseas, entityParam.getTaxnOverseas());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnLocal()),TbComShopApplyTaxnTask::getTaxnLocal, entityParam.getTaxnLocal());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnEori()),TbComShopApplyTaxnTask::getTaxnEori, entityParam.getTaxnEori());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnBelongToCom()),TbComShopApplyTaxnTask::getTaxnBelongToCom, entityParam.getTaxnBelongToCom());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnRegAddress()),TbComShopApplyTaxnTask::getTaxnRegAddress, entityParam.getTaxnRegAddress());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAddrOfAccountant()),TbComShopApplyTaxnTask::getTaxnAddrOfAccountant, entityParam.getTaxnAddrOfAccountant());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnDecMethod()),TbComShopApplyTaxnTask::getTaxnDecMethod, entityParam.getTaxnDecMethod());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnDecTime()),TbComShopApplyTaxnTask::getTaxnDecTime, entityParam.getTaxnDecTime());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnFiles()),TbComShopApplyTaxnTask::getTaxnFiles, entityParam.getTaxnFiles());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnRegEmail()),TbComShopApplyTaxnTask::getTaxnRegEmail, entityParam.getTaxnRegEmail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnRegTel()),TbComShopApplyTaxnTask::getTaxnRegTel, entityParam.getTaxnRegTel());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAudEmail()),TbComShopApplyTaxnTask::getTaxnAudEmail, entityParam.getTaxnAudEmail());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAltEmail1()),TbComShopApplyTaxnTask::getTaxnAltEmail1, entityParam.getTaxnAltEmail1());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAltEmail2()),TbComShopApplyTaxnTask::getTaxnAltEmail2, entityParam.getTaxnAltEmail2());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAgency()),TbComShopApplyTaxnTask::getTaxnAgency, entityParam.getTaxnAgency());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAgencyTel()),TbComShopApplyTaxnTask::getTaxnAgencyTel, entityParam.getTaxnAgencyTel());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnAgencyAddress()),TbComShopApplyTaxnTask::getTaxnAgencyAddress, entityParam.getTaxnAgencyAddress());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnState()),TbComShopApplyTaxnTask::getTaxnState, entityParam.getTaxnState());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaxnCate()),TbComShopApplyTaxnTask::getTaxnCate, entityParam.getTaxnCate());

        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSysApplyDetId()),TbComShopApplyTaxnTask::getSysApplyDetId, entityParam.getSysApplyDetId());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnEffDateOfVat()),TbComShopApplyTaxnTask::getTaxnEffDateOfVat, entityParam.getTaxnEffDateOfVat());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnRate()),TbComShopApplyTaxnTask::getTaxnRate, entityParam.getTaxnRate());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnEffeRangeStart()),TbComShopApplyTaxnTask::getTaxnEffeRangeStart, entityParam.getTaxnEffeRangeStart());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaxnEffeRangeEnd()),TbComShopApplyTaxnTask::getTaxnEffeRangeEnd, entityParam.getTaxnEffeRangeEnd());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getIsVirtualShop()),TbComShopApplyTaxnTask::getIsVirtualShop, entityParam.getIsVirtualShop());
        String taxnTaskState = "未完成";
        if ("提交".equals(entityParam.getSaveOrSubmit())) {
            taxnTaskState = "完成";
        }
        chainWrapper.set(TbComShopApplyTaxnTask::getTaxnTaskState, taxnTaskState);
        chainWrapper.set(TbComShopApplyTaxnTask::getSysLastUpdPerName, loginUser.getName());
        chainWrapper.set(TbComShopApplyTaxnTask::getSysLastUpdPerCode, loginUser.getAccount());
        chainWrapper.set(TbComShopApplyTaxnTask::getSysLastUpdDate,new Date());

        if (ObjectUtil.isEmpty(entityParam.getTaxnFiles())) {
            chainWrapper.set(TbComShopApplyTaxnTask::getTaxnFiles,"");
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopApplyTaxnTask::getTaxnId, entityParam.getTaxnId());
        boolean ret = chainWrapper.update();
        //店铺创建是否完成，同步数据
        ResponseData result= tbComShopApplyDetService.syncData(entityParam.getSysApplyDetId());
        //所有财务任务是否完成，更新申请状态
        tbComShopApplyMapper.updateShopApplyState();
        //3. 更新成功了，查询最最对象返回
        return queryById(entityParam.getTaxnId());
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param taxnId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal taxnId){
        int total = tbComShopApplyTaxnTaskMapper.deleteById(taxnId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param taxnIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> taxnIdList){
         int delCount = tbComShopApplyTaxnTaskMapper.deleteBatchIds(taxnIdList);
         if (taxnIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 根据店铺申请明细ID查找银行税号任务信息
      * @param sysApplyDetId
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public TbComShopApplyTaxnTask getTaxnTaskByDetId(BigDecimal sysApplyDetId) {
         LambdaQueryWrapper<TbComShopApplyTaxnTask> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbComShopApplyTaxnTask::getSysApplyDetId, sysApplyDetId);
         List<TbComShopApplyTaxnTask> taskList = tbComShopApplyTaxnTaskMapper.selectList(queryWrapper);
         if (ObjectUtil.isNotEmpty(taskList)) {
             return taskList.get(0);
         }
         return null;
     }
 }