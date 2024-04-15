package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtB2bSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtB2bSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopeeSettlementReportParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(LT_B2B_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-25
 */
public interface LtB2bSettlementReportService extends IService<LtB2bSettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtB2bSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltB2bSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtB2bSettlementReportResult> paginQuery(LtB2bSettlementReportParam ltB2bSettlementReport, long current, long size);

     @DataSource(name = "finance")
     List<LtB2bSettlementReport> paginExport(LtB2bSettlementReportParam param);

     @DataSource(name = "finance")
     List<LtB2bSettlementReportExport> downloadExport(LtB2bSettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltB2bSettlementReport 实例对象
     * @return 实例对象
     */
    LtB2bSettlementReport insert(LtB2bSettlementReport ltB2bSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltB2bSettlementReport 实例对象
     * @return 实例对象
     */
    LtB2bSettlementReport update(LtB2bSettlementReportParam ltB2bSettlementReport);
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
     ResponseData autoCalPeopleCost(LtB2bSettlementReportParam param);



     ResponseData haveReport(LtB2bSettlementReportParam param);

     ResponseData insertStructure(LtB2bSettlementReportParam param);
 }