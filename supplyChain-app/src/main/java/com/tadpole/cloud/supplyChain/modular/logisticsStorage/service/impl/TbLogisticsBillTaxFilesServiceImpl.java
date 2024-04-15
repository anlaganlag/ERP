package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBillTaxFiles;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsBillTaxFilesMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsBillTaxFilesService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillTaxFilesResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillTaxFilesParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流账单税务文件;(tb_logistics_bill_tax_files)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsBillTaxFilesServiceImpl  extends ServiceImpl<TbLogisticsBillTaxFilesMapper, TbLogisticsBillTaxFiles> implements TbLogisticsBillTaxFilesService{
    @Resource
    private TbLogisticsBillTaxFilesMapper tbLogisticsBillTaxFilesMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillTaxFiles queryById(BigDecimal id){
        return tbLogisticsBillTaxFilesMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsBillTaxFilesResult> paginQuery(TbLogisticsBillTaxFilesParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsBillTaxFiles> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillTaxFiles::getBillNum, param.getBillNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillTaxFiles::getLhrOddNum, param.getLhrOddNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTaxNum()),TbLogisticsBillTaxFiles::getTaxNum, param.getTaxNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTaxType()),TbLogisticsBillTaxFiles::getTaxType, param.getTaxType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTaxOriginFileName()),TbLogisticsBillTaxFiles::getTaxOriginFileName, param.getTaxOriginFileName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTaxFileName()),TbLogisticsBillTaxFiles::getTaxFileName, param.getTaxFileName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOperator()),TbLogisticsBillTaxFiles::getOperator, param.getOperator());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOperatorCode()),TbLogisticsBillTaxFiles::getOperatorCode, param.getOperatorCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFilePathFull()),TbLogisticsBillTaxFiles::getFilePathFull, param.getFilePathFull());
        //2. 执行分页查询
        Page<TbLogisticsBillTaxFilesResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsBillTaxFilesResult> selectResult = tbLogisticsBillTaxFilesMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsBillTaxFiles 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillTaxFiles insert(TbLogisticsBillTaxFiles tbLogisticsBillTaxFiles){
        tbLogisticsBillTaxFilesMapper.insert(tbLogisticsBillTaxFiles);
        return tbLogisticsBillTaxFiles;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillTaxFiles update(TbLogisticsBillTaxFilesParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsBillTaxFiles> wrapper = new LambdaUpdateChainWrapper<TbLogisticsBillTaxFiles>(tbLogisticsBillTaxFilesMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillTaxFiles::getBillNum, param.getBillNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillTaxFiles::getLhrOddNum, param.getLhrOddNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTaxNum()),TbLogisticsBillTaxFiles::getTaxNum, param.getTaxNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTaxType()),TbLogisticsBillTaxFiles::getTaxType, param.getTaxType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTaxOriginFileName()),TbLogisticsBillTaxFiles::getTaxOriginFileName, param.getTaxOriginFileName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTaxFileName()),TbLogisticsBillTaxFiles::getTaxFileName, param.getTaxFileName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOperator()),TbLogisticsBillTaxFiles::getOperator, param.getOperator());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOperatorCode()),TbLogisticsBillTaxFiles::getOperatorCode, param.getOperatorCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getFilePathFull()),TbLogisticsBillTaxFiles::getFilePathFull, param.getFilePathFull());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsBillTaxFiles::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal id){
        int total = tbLogisticsBillTaxFilesMapper.deleteById(id);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = tbLogisticsBillTaxFilesMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}