package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LtAliexpressSettlementReportParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtAliexpressSettlementReportResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(LT_ALIEXPRESS_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-22
 */
public interface LtAliexpressSettlementReportService extends IService<LtAliexpressSettlementReport>  {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtAliexpressSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltAliexpressSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtAliexpressSettlementReportResult> paginQuery(LtAliexpressSettlementReportParam ltAliexpressSettlementReport, long current, long size);

     List<LtAliexpressSettlementReportExport> downloadExport(LtAliexpressSettlementReportParam param);


     List<LtAliexpressSettlementReport> paginExport(LtAliexpressSettlementReportParam ltAliexpressSettlementReport);
    /**
     * 新增数据
     *
     * @param ltAliexpressSettlementReport 实例对象
     * @return 实例对象
     */
    LtAliexpressSettlementReport insert(LtAliexpressSettlementReport ltAliexpressSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltAliexpressSettlementReport 实例对象
     * @return 实例对象
     */
    LtAliexpressSettlementReport update(LtAliexpressSettlementReportParam ltAliexpressSettlementReport);
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


     @DataSource(name = "finance")
     ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList);

     @DataSource(name = "finance")
     ResponseData autoCalPeopleCost(LtAliexpressSettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData haveReport(LtAliexpressSettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData insertStructure(LtAliexpressSettlementReportParam param);
//     ResponseData getPeopleCost(LtAliexpressSettlementReportParam param);

 }