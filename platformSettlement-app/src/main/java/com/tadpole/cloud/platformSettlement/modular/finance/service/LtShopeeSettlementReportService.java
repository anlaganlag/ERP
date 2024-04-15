package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopeeSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopeeSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtShopeeSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopeeSettlementReportParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(LT_SHOPEE_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-22
 */
public interface LtShopeeSettlementReportService extends IService<LtShopeeSettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtShopeeSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltShopeeSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtShopeeSettlementReportResult> paginQuery(LtShopeeSettlementReportParam ltShopeeSettlementReport, long current, long size);

     @DataSource(name = "finance")
     List<LtShopeeSettlementReportResult> paginExport(LtShopeeSettlementReportParam param);

     @DataSource(name = "finance")
     List<LtShopeeSettlementReportExport> downloadExport(LtShopeeSettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltShopeeSettlementReport 实例对象
     * @return 实例对象
     */
    LtShopeeSettlementReport insert(LtShopeeSettlementReport ltShopeeSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltShopeeSettlementReport 实例对象
     * @return 实例对象
     */
    LtShopeeSettlementReport update(LtShopeeSettlementReportParam ltShopeeSettlementReport);
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
     ResponseData autoCalPeopleCost(LtShopeeSettlementReportParam param);



     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData haveReport(LtShopeeSettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData insertStructure(LtShopeeSettlementReportParam param);
 }