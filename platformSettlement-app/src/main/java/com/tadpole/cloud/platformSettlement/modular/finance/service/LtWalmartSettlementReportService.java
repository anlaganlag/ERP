package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtWalmartSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtWalmartSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtWalmartSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtWalmartSettlementReportParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(LT_WALMART_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-22
 */
public interface LtWalmartSettlementReportService extends IService<LtWalmartSettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtWalmartSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltWalmartSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtWalmartSettlementReportResult> paginQuery(LtWalmartSettlementReportParam ltWalmartSettlementReport, long current, long size);

     List<LtWalmartSettlementReport> paginExport(LtWalmartSettlementReportParam param);

     List<LtWalmartSettlementReportExport> downloadExport(LtWalmartSettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltWalmartSettlementReport 实例对象
     * @return 实例对象
     */
    LtWalmartSettlementReport insert(LtWalmartSettlementReport ltWalmartSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltWalmartSettlementReport 实例对象
     * @return 实例对象
     */
    LtWalmartSettlementReport update(LtWalmartSettlementReportParam ltWalmartSettlementReport);
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    boolean deleteById(String undefinedId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> undefinedIdList);


     ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList, List<String> walShopList);

     ResponseData autoCalPeopleCost(LtWalmartSettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData haveReport(LtWalmartSettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData insertStructure(LtWalmartSettlementReportParam param);

     @DataSource(name = "finance")
     int updatePeopleCostZero(LtWalmartSettlementReportParam param);
 }