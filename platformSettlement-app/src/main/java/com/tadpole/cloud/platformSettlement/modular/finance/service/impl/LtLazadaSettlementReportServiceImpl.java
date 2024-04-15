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
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtLazadaSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtLazadaSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtLazadaSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LtLazadaSettlementReportMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtLazadaSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtLazadaSettlementReportService;
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
 * ;(LT_LAZADA_SETTLEMENT_REPORT)表服务实现类
 * @author : LSY
 * @date : 2023-12-22
 */
@Service
@Transactional
@Slf4j
public class LtLazadaSettlementReportServiceImpl  extends ServiceImpl<LtLazadaSettlementReportMapper, LtLazadaSettlementReport> implements LtLazadaSettlementReportService {
     @Resource
     private LtLazadaSettlementReportMapper ltLazadaSettlementReportMapper;

     @Resource
     private AllocStructureMapper allocStructureMapper;

     @Resource
     AllocStructureService allocStructureService;


     /**
      * 通过ID查询单条数据
      *
      * @param undefinedId 主键
      * @return 实例对象
      */
     @DataSource(name = "finance")
     @Override
     public LtLazadaSettlementReport queryById(String undefinedId) {
         return ltLazadaSettlementReportMapper.selectById(undefinedId);
     }

     /**
      * 分页查询
      *
      * @param param   筛选条件
      * @param current 当前页码
      * @param size    每页大小
      * @return
      */
     @DataSource(name = "finance")
     @Override
     public Page<LtLazadaSettlementReportResult> paginQuery(LtLazadaSettlementReportParam param, long current, long size) {
         //1. 构建动态查询条件
         LambdaQueryWrapper<LtLazadaSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPeriod()), LtLazadaSettlementReport::getPeriod, param.getPeriod());
         //2. 执行分页查询
         Page<LtLazadaSettlementReportResult> pagin = new Page<>(current, size, true);
         IPage<LtLazadaSettlementReportResult> selectResult = ltLazadaSettlementReportMapper.selectByPage(pagin, queryWrapper);
         pagin.setPages(selectResult.getPages());
         pagin.setTotal(selectResult.getTotal());
         pagin.setRecords(selectResult.getRecords());
         //3. 返回结果
         return pagin;
     }

     @DataSource(name = "finance")
     @Override
     public List<LtLazadaSettlementReport> paginExport(LtLazadaSettlementReportParam param) {
         //1. 构建动态查询条件
         LambdaQueryWrapper<LtLazadaSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()), LtLazadaSettlementReport::getId, param.getId());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()), LtLazadaSettlementReport::getCategory, param.getCategory());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()), LtLazadaSettlementReport::getProductType, param.getProductType());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()), LtLazadaSettlementReport::getProductName, param.getProductName());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()), LtLazadaSettlementReport::getStyle, param.getStyle());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()), LtLazadaSettlementReport::getMainMaterial, param.getMainMaterial());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()), LtLazadaSettlementReport::getDesign, param.getDesign());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()), LtLazadaSettlementReport::getCompanyBrand, param.getCompanyBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()), LtLazadaSettlementReport::getFitBrand, param.getFitBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()), LtLazadaSettlementReport::getModel, param.getModel());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()), LtLazadaSettlementReport::getColor, param.getColor());
         //2. 执行分页查询
         List<LtLazadaSettlementReport> selectResult = ltLazadaSettlementReportMapper.exportByPage(queryWrapper);
         //3. 返回结果
         return selectResult;
     }


     @DataSource(name = "finance")
     @Override
     public List<LtLazadaSettlementReportExport> downloadExport(LtLazadaSettlementReportParam param){
         //1. 构建动态查询条件
         LambdaQueryWrapper<LtLazadaSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(LtLazadaSettlementReport::getId, "-1");

         //2. 执行分页查询
         List<LtLazadaSettlementReportExport> selectResult = ltLazadaSettlementReportMapper.downloadExport( queryWrapper);
         //3. 返回结果
         return selectResult;
     }

     /**
      * 新增数据
      *
      * @param ltLazadaSettlementReport 实例对象
      * @return 实例对象
      */
     @DataSource(name = "finance")
     @Override
     public LtLazadaSettlementReport insert(LtLazadaSettlementReport ltLazadaSettlementReport) {
         ltLazadaSettlementReportMapper.insert(ltLazadaSettlementReport);
         return ltLazadaSettlementReport;
     }

     /**
      * 更新数据
      *
      * @param param 实例对象
      * @return 实例对象
      */
     @DataSource(name = "finance")
     @Override
     public LtLazadaSettlementReport update(LtLazadaSettlementReportParam param) {
         //1. 根据条件动态更新
         LambdaUpdateChainWrapper<LtLazadaSettlementReport> wrapper = new LambdaUpdateChainWrapper<LtLazadaSettlementReport>(ltLazadaSettlementReportMapper);
         //2. 设置主键，并更新
         wrapper.eq(LtLazadaSettlementReport::getId, param.getId());
         LoginUser loginUser = LoginContext.me().getLoginUser();
         wrapper.set(LtLazadaSettlementReport::getUpdateTime, new Date());
         wrapper.set(LtLazadaSettlementReport::getUpdateBy, loginUser.getName());
         wrapper.set(LtLazadaSettlementReport::getProductType, param.getProductType());

         boolean ret = wrapper.update();
         //3. 更新成功了，查询最最对象返回
         if (ret) {
             return queryById(param.getId());
         } else {
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
     public boolean deleteById(String undefinedId) {
         int total = ltLazadaSettlementReportMapper.deleteById(undefinedId);
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
         int delCount = ltLazadaSettlementReportMapper.deleteBatchIds(undefinedIdList);
         if (undefinedIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     @DataSource(name = "finance")
     @Override
     public ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList) {
         log.info("小平台结算报告Lazada-导入:Excel处理开始");
         BufferedInputStream buffer = null;

         try {
             buffer = new BufferedInputStream(file.getInputStream());
             BaseExcelListener listener = new BaseExcelListener<LtLazadaSettlementReport>();
             EasyExcel.read(buffer, LtLazadaSettlementReport.class, listener).sheet()
                     .doRead();

             List<LtLazadaSettlementReport> dataList = listener.getDataList();
             if (CollectionUtil.isEmpty(dataList)) {
                 return ResponseData.error("小平台结算报告Lazada-导入为空！");
             }

             //异常数据集合
             List<LtLazadaSettlementReport> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
             String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getShopName())||!shopList.contains(i.getShopName()))) {
                 log.error(StrUtil.format("存在异常账号:可选账号[{}]",String.join("、", shopList)));
                 return ResponseData.error(StrUtil.format("存在异常账号:可选账号[{}]",String.join("、", shopList)));
             }

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getSite())||!siteList.contains(i.getSite()))) {
                 log.error(StrUtil.format("存在异常站点:可选站点[{}]",String.join("、", siteList)));
                 return ResponseData.error(StrUtil.format("存在异常站点:可选站点[{}]",String.join("、", siteList)));
             }

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



             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getSellerSku()))) {
                 return ResponseData.error(StrUtil.format("导入失败:存在物料编码为空"));
             }
             dataList.forEach(
                     i -> {
                         if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                             i.setUpdateBy(operator);
                             i.setUpdateTime(date);
                         }else{
                             i.setCreateBy(operator);
                             i.setCreateTime(date);
                         };
                         i.setPlatform("Lazada");
                     }

             );
             //批量保存更新表数据
             if (CollectionUtil.isNotEmpty(dataList)) {
                 String period = dataList.get(0).getPeriod();
                 new LambdaUpdateChainWrapper<>(ltLazadaSettlementReportMapper)
                         .eq(ObjectUtil.isNotEmpty(period), LtLazadaSettlementReport::getPeriod, period)
                         .ne(LtLazadaSettlementReport::getConfirmStatus,1)
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
         } catch (Exception e) {
             log.error("导入Excel处理异常，导入失败！", e);
             return ResponseData.error("导入Excel处理异常，导入失败！"+ e.getMessage());
         }
     }

     @DataSource(name = "finance")
     @Override
     public ResponseData autoCalPeopleCost(LtLazadaSettlementReportParam param) {
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
     public ResponseData haveReport(LtLazadaSettlementReportParam param) {
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

         LambdaQueryWrapper<LtLazadaSettlementReport> shopifyQw = new LambdaQueryWrapper<>();
         shopifyQw.eq(LtLazadaSettlementReport::getPeriod, param.getPeriod());
         if (ltLazadaSettlementReportMapper.selectCount(shopifyQw) == 0) {
             log.error("报告当前会计期间[{}]无数据", param.getPeriod());
             return ResponseData.error("数据为空");
         }
         return ResponseData.success();
     }
     @DataSource(name = "finance")
     @Override
     @Transactional(rollbackFor = Exception.class)
     public ResponseData insertStructure(LtLazadaSettlementReportParam param) {
         if (ObjectUtil.isEmpty(param.getPeriod())) {
             return  ResponseData.error("未指定会计区间");
         }

         String createBy = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "系统生成架构";
         param.setCreateBy(createBy);
         ltLazadaSettlementReportMapper.insertStructure(param);

         return ResponseData.success();

     }
 }