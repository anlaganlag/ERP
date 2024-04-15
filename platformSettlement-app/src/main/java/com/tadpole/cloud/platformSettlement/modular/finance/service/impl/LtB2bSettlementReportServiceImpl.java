package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import com.tadpole.cloud.platformSettlement.api.finance.entity.AllocStructure;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtB2bSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LtB2bSettlementReportMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtB2bSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtB2bSettlementReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ;(LT_B2B_SETTLEMENT_REPORT)表服务实现类
 * @author : LSY
 * @date : 2023-12-25
 */
@Service
@Transactional
@Slf4j
public class LtB2bSettlementReportServiceImpl  extends ServiceImpl<LtB2bSettlementReportMapper, LtB2bSettlementReport> implements LtB2bSettlementReportService{
    @Resource
    private LtB2bSettlementReportMapper ltB2bSettlementReportMapper;

    @Resource
    AllocStructureService allocStructureService;

    @Resource
    private AllocStructureMapper allocStructureMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtB2bSettlementReport queryById(String undefinedId){
        return ltB2bSettlementReportMapper.selectById(undefinedId);
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
    public Page<LtB2bSettlementReportResult> paginQuery(LtB2bSettlementReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtB2bSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPeriod()), LtB2bSettlementReport::getPeriod, param.getPeriod());
        //2. 执行分页查询
        Page<LtB2bSettlementReportResult> pagin = new Page<>(current , size , true);
        IPage<LtB2bSettlementReportResult> selectResult = ltB2bSettlementReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

     @DataSource(name = "finance")
     @Override
     public List<LtB2bSettlementReport> paginExport(LtB2bSettlementReportParam param){
         //1. 构建动态查询条件
         LambdaQueryWrapper<LtB2bSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()),LtB2bSettlementReport::getId, param.getId());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillCode()),LtB2bSettlementReport::getBillCode, param.getBillCode());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getNewMatCode()),LtB2bSettlementReport::getNewMatCode, param.getNewMatCode());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()),LtB2bSettlementReport::getCategory, param.getCategory());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()),LtB2bSettlementReport::getProductType, param.getProductType());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()),LtB2bSettlementReport::getProductName, param.getProductName());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()),LtB2bSettlementReport::getStyle, param.getStyle());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()),LtB2bSettlementReport::getMainMaterial, param.getMainMaterial());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()),LtB2bSettlementReport::getDesign, param.getDesign());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()),LtB2bSettlementReport::getCompanyBrand, param.getCompanyBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()),LtB2bSettlementReport::getFitBrand, param.getFitBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()),LtB2bSettlementReport::getModel, param.getModel());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()),LtB2bSettlementReport::getColor, param.getColor());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOrderNo()),LtB2bSettlementReport::getOrderNo, param.getOrderNo());
         //2. 执行分页查询
         List<LtB2bSettlementReport> selectResult = ltB2bSettlementReportMapper.exportByPage( queryWrapper);
         //3. 返回结果
         return selectResult;
     }

    @DataSource(name = "finance")
    @Override
    public List<LtB2bSettlementReportExport> downloadExport(LtB2bSettlementReportParam param){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtB2bSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LtB2bSettlementReport::getId, "-1");

        //2. 执行分页查询
        List<LtB2bSettlementReportExport> selectResult = ltB2bSettlementReportMapper.downloadExport( queryWrapper);
        //3. 返回结果
        return selectResult;
    }



     /**
     * 新增数据
     *
     * @param ltB2bSettlementReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtB2bSettlementReport insert(LtB2bSettlementReport ltB2bSettlementReport){
        ltB2bSettlementReportMapper.insert(ltB2bSettlementReport);
        return ltB2bSettlementReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtB2bSettlementReport update(LtB2bSettlementReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LtB2bSettlementReport> wrapper = new LambdaUpdateChainWrapper<LtB2bSettlementReport>(ltB2bSettlementReportMapper);
        //2. 设置主键，并更新
        wrapper.eq(LtB2bSettlementReport::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(LtB2bSettlementReport::getUpdateTime, new Date());
        wrapper.set(LtB2bSettlementReport::getUpdateBy, loginUser.getName());
        wrapper.set(LtB2bSettlementReport::getProductType, param.getProductType());
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
        int total = ltB2bSettlementReportMapper.deleteById(undefinedId);
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
        int delCount = ltB2bSettlementReportMapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


     @DataSource(name = "finance")
     @Override
     public ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList) {
         log.info("小平台结算报告B2B-导入:Excel处理开始");
         BufferedInputStream buffer = null;

         try {
             buffer = new BufferedInputStream(file.getInputStream());
             BaseExcelListener listener = new BaseExcelListener<LtB2bSettlementReport>();
             EasyExcel.read(buffer, LtB2bSettlementReport.class, listener).sheet()
                     .doRead();

             List<LtB2bSettlementReport> dataList = listener.getDataList();
             if (dataList.stream().anyMatch(i -> ObjectUtil.isNotEmpty(i.getPlatform()) && !"B2B".equals(i.getPlatform()) && !"Alibaba".equals(i.getPlatform()) )) {
                 log.error("平台有误:可选平台有误[Alibaba、B2B]");
                 return ResponseData.error("平台有误有误:可选平台有误[Alibaba、B2B]");
             }

             dataList = dataList.stream().filter(i -> "B2B".equals(i.getPlatform())).collect(Collectors.toList());
             if (CollectionUtil.isEmpty(dataList)) {
                 return ResponseData.success("小平台结算报告B2B-导入为空！");
             }

             //异常数据集合
             List<LtB2bSettlementReport> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
             String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";


             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getDepartment())||!departmentList.contains(i.getDepartment()))) {
                 log.error(StrUtil.format("存在异常事业部:可选事业部[{}]",String.join("、", departmentList)));
                 return ResponseData.error(StrUtil.format("存在异常事业部:可选事业部[{}]",String.join("、", departmentList)));
             }

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getTeam())||!teamList.contains(i.getTeam()))) {
                 log.error(StrUtil.format("存在异常Team:可选Team[{}]",String.join("、", teamList)));
                 return ResponseData.error(StrUtil.format("存在异常Team:可选Team[{}]",String.join("、", teamList)));
             }


             DateTime date = DateUtil.date();
             String pattern = "\\d{4}-\\d{2}";
             if (dataList.stream().anyMatch(i-> ObjectUtil.isEmpty(i.getPeriod()) ||!i.getPeriod().matches(pattern))) {
                 return ResponseData.error(StrUtil.format("导入失败:期间正确格式yyyy-MM"));
             }



             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getNewMatCode()))) {
                 return ResponseData.error(StrUtil.format("导入失败:存在物料编码为空"));
             }


             for (LtB2bSettlementReport i : dataList) {
                         if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                             i.setUpdateBy(operator);
                             i.setUpdateTime(date);
                         }else{
                             i.setCreateBy(operator);
                             i.setCreateTime(date);
                         };
                         i.setPlatform("B2B");
                 try {
                     if (ObjectUtil.isNotEmpty(i.getCollectionDate())) {
                     DateTime parse = DateUtil.parse(i.getCollectionDate(), "yyyy-MM-dd");
                     i.setCollectionDate(DateUtil.format(parse,"yyyy-MM-dd hh:mm:ss"));}
                 } catch (Exception e){
                     log.error(StrUtil.format("导入失败:收款日期日期转换异常[{}]",i.getCollectionDate()));
                     return ResponseData.error(StrUtil.format("导入失败:收款日期:[{}]转换异常,正确格式yyyy-MM-dd hh:mm:ss",i.getCollectionDate()));
                 }
             }

             //批量保存更新表数据
             if (CollectionUtil.isNotEmpty(dataList)) {
                 String period = dataList.get(0).getPeriod();
                 new LambdaUpdateChainWrapper<>(ltB2bSettlementReportMapper)
                         .eq(ObjectUtil.isNotEmpty(period), LtB2bSettlementReport::getPeriod, period)
                         .ne(LtB2bSettlementReport::getConfirmStatus,1)
                         .remove();
                 this.saveOrUpdateBatch(dataList);
                 if (CollectionUtil.isNotEmpty(errorList)) {
                     String fileName = "异常数据";
                     //部分导入成功
                     return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                 }
                 this.baseMapper.mergePeople(period);
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

    @DataSource(name = "finance")
    @Override
    public ResponseData autoCalPeopleCost(LtB2bSettlementReportParam param) {
        try {
            if (ObjectUtil.isEmpty(param.getPeriod())) {
                return ResponseData.error("导入失败:期间不能为空");
            }
            this.baseMapper.mergePeople(param.getPeriod());
            return ResponseData.success();
        }catch (Exception e){
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！" + e.getMessage());
        }
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData haveReport(LtB2bSettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getPeriod())) {
            return ResponseData.error("未指定会计区间");
        }

        LambdaQueryWrapper<AllocStructure> qw = new LambdaQueryWrapper<>();
        qw.eq(ObjectUtil.isNotEmpty(param.getPeriod()), AllocStructure::getPeriod, param.getPeriod());

        if (allocStructureMapper.selectCount(qw) == 0) {
            return ResponseData.error(StrUtil.format("当前会计区间{}无分摊分摊架构", param.getPeriod()));
        }

        //0.当前会计架构是否确认
        AllocStructureParam allocStructureParam = AllocStructureParam.builder().period(param.getPeriod()).build();
        if (allocStructureService.isNoComfirm(allocStructureParam)) {
            return ResponseData.error(StrUtil.format("当前会计区间{}存在未未确认分摊架构", param.getPeriod()));
        }

        LambdaQueryWrapper<LtB2bSettlementReport> shopifyQw = new LambdaQueryWrapper<>();
        shopifyQw.eq(LtB2bSettlementReport::getPeriod, param.getPeriod());
        if (ltB2bSettlementReportMapper.selectCount(shopifyQw) == 0) {
            log.error("报告当前会计期间[{}]无数据", param.getPeriod());
            return ResponseData.error("数据为空");
        }
        return ResponseData.success();
    }
    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insertStructure(LtB2bSettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getPeriod())) {
            return  ResponseData.error("未指定会计区间");
        }

        String createBy = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "系统生成架构";
        param.setCreateBy(createBy);
        ltB2bSettlementReportMapper.insertStructure(param);

        return ResponseData.success();

    }

 }