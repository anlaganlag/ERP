package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bAliSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtB2bAliSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LtB2bAliSettlementReportMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtB2bAliSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtB2bAliSettlementReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 /**
 * ;(LT_B2B_ALI_SETTLEMENT_REPORT)表服务实现类
 * @author : LSY
 * @date : 2023-12-22
 */
@Service
@Transactional
@Slf4j
public class LtB2bAliSettlementReportServiceImpl  extends ServiceImpl<LtB2bAliSettlementReportMapper, LtB2bAliSettlementReport> implements LtB2bAliSettlementReportService{
    @Resource
    private LtB2bAliSettlementReportMapper ltB2bAliSettlementReportMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtB2bAliSettlementReport queryById(String undefinedId){
        return ltB2bAliSettlementReportMapper.selectById(undefinedId);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "finance")
    @Override
    public Page<LtB2bAliSettlementReportResult> paginQuery(LtB2bAliSettlementReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtB2bAliSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        //2. 执行分页查询
        Page<LtB2bAliSettlementReportResult> pagin = new Page<>(current , size , true);
        IPage<LtB2bAliSettlementReportResult> selectResult = ltB2bAliSettlementReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

     @DataSource(name = "finance")
     @Override
     public List<LtB2bAliSettlementReport> paginExport(LtB2bAliSettlementReportParam param){
         //1. 构建动态查询条件
         LambdaQueryWrapper<LtB2bAliSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()),LtB2bAliSettlementReport::getId, param.getId());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillCode()),LtB2bAliSettlementReport::getBillCode, param.getBillCode());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getNewMatCode()),LtB2bAliSettlementReport::getNewMatCode, param.getNewMatCode());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()),LtB2bAliSettlementReport::getCategory, param.getCategory());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()),LtB2bAliSettlementReport::getProductType, param.getProductType());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()),LtB2bAliSettlementReport::getProductName, param.getProductName());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()),LtB2bAliSettlementReport::getStyle, param.getStyle());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()),LtB2bAliSettlementReport::getMainMaterial, param.getMainMaterial());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()),LtB2bAliSettlementReport::getDesign, param.getDesign());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()),LtB2bAliSettlementReport::getCompanyBrand, param.getCompanyBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()),LtB2bAliSettlementReport::getFitBrand, param.getFitBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()),LtB2bAliSettlementReport::getModel, param.getModel());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()),LtB2bAliSettlementReport::getColor, param.getColor());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOrderNo()),LtB2bAliSettlementReport::getOrderNo, param.getOrderNo());
         //2. 执行分页查询
         List<LtB2bAliSettlementReport> selectResult = ltB2bAliSettlementReportMapper.exportByPage( queryWrapper);
         //3. 返回结果
         return selectResult;
     }

    
    /** 
     * 新增数据
     *
     * @param ltB2bAliSettlementReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtB2bAliSettlementReport insert(LtB2bAliSettlementReport ltB2bAliSettlementReport){
        ltB2bAliSettlementReportMapper.insert(ltB2bAliSettlementReport);
        return ltB2bAliSettlementReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtB2bAliSettlementReport update(LtB2bAliSettlementReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LtB2bAliSettlementReport> wrapper = new LambdaUpdateChainWrapper<LtB2bAliSettlementReport>(ltB2bAliSettlementReportMapper);
        //2. 设置主键，并更新
        wrapper.eq(LtB2bAliSettlementReport::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(LtB2bAliSettlementReport::getUpdateTime, new Date());
        wrapper.set(LtB2bAliSettlementReport::getUpdateBy, loginUser.getName());
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
     * @param undefinedId 主键
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteById(String undefinedId){
        int total = ltB2bAliSettlementReportMapper.deleteById(undefinedId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteBatchIds(List<String> undefinedIdList) {
        int delCount = ltB2bAliSettlementReportMapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
     @DataSource(name = "finance")
     @Override
     public ResponseData importSettlementReport(MultipartFile file) {
         log.info("小平台结算报告B2B阿里巴巴-导入:Excel处理开始");
         BufferedInputStream buffer = null;

         try {
             buffer = new BufferedInputStream(file.getInputStream());
             BaseExcelListener listener = new BaseExcelListener<LtB2bAliSettlementReport>();
             EasyExcel.read(buffer, LtB2bAliSettlementReport.class, listener).sheet()
                     .doRead();

             List<LtB2bAliSettlementReport> dataList = listener.getDataList();
             if (CollectionUtil.isEmpty(dataList)) {
                 return ResponseData.error("小平台结算报告B2B阿里巴巴-导入为空！");
             }

             //异常数据集合
             List<LtB2bAliSettlementReport> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
             String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";
             DateTime date = DateUtil.date();
             dataList.forEach(
                     i -> {
                         if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                             i.setUpdateBy(operator);
                             i.setUpdateTime(date);
                         }else{
                             i.setCreateBy(operator);
                             i.setCreateTime(date);
                         };
                     }

             );
             //批量保存更新表数据
             if (CollectionUtil.isNotEmpty(dataList)) {
                 this.saveOrUpdateBatch(dataList);
                 if (CollectionUtil.isNotEmpty(errorList)) {
                     String fileName = "异常数据";
                     //部分导入成功
                     return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                 }
                 return ResponseData.success("导入成功！");
             }
             if (CollectionUtil.isNotEmpty(errorList)) {
                 String fileName = "异常数据";
                 //导入失败
                 return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
             }
             return ResponseData.error("导入失败！导入数据为空！");
         } catch (Exception e) {log.error("导入Excel处理异常，导入失败！", e);
             return ResponseData.error("导入Excel处理异常，导入失败！"+ e.getMessage());
         }
     }
}