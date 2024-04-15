package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopifySettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopifySettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtShopifySettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopifySettlementReportParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * Shopify小平台结算报告;(LT_SHOPIFY_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-23
 */
public interface LtShopifySettlementReportService extends IService<LtShopifySettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    LtShopifySettlementReport queryById(String id);
    
    /**
     * 分页查询
     *
     * @param ltShopifySettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtShopifySettlementReportResult> paginQuery(LtShopifySettlementReportParam ltShopifySettlementReport, long current, long size);

     List<LtShopifySettlementReport> paginExport(LtShopifySettlementReportParam param);

     @DataSource(name = "finance")
     List<LtShopifySettlementReportExport> downloadExport(LtShopifySettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltShopifySettlementReport 实例对象
     * @return 实例对象
     */
    LtShopifySettlementReport insert(LtShopifySettlementReport ltShopifySettlementReport);
    /** 
     * 更新数据
     *
     * @param ltShopifySettlementReport 实例对象
     * @return 实例对象
     */
    LtShopifySettlementReport update(LtShopifySettlementReportParam ltShopifySettlementReport);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> idList);


     @DataSource(name = "finance")
     ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList);

     ResponseData autoCalPeopleCost(LtShopifySettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData haveReport(LtShopifySettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData insertStructure(LtShopifySettlementReportParam param);

     @DataSource(name = "finance")
     int updatePeopleCostZero(LtShopifySettlementReportParam param);
 }