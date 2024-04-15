package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAlibabaSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAlibabaSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtAlibabaSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtAlibabaSettlementReportParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(LT_ALIBABA_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-25
 */
public interface LtAlibabaSettlementReportService extends IService<LtAlibabaSettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtAlibabaSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltAlibabaSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtAlibabaSettlementReportResult> paginQuery(LtAlibabaSettlementReportParam ltAlibabaSettlementReport, long current, long size);

     @DataSource(name = "finance")
     List<LtAlibabaSettlementReport> paginExport(LtAlibabaSettlementReportParam param);

     @DataSource(name = "finance")
     List<LtAlibabaSettlementReportExport> downloadExport(LtAlibabaSettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltAlibabaSettlementReport 实例对象
     * @return 实例对象
     */
    LtAlibabaSettlementReport insert(LtAlibabaSettlementReport ltAlibabaSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltAlibabaSettlementReport 实例对象
     * @return 实例对象
     */
    LtAlibabaSettlementReport update(LtAlibabaSettlementReportParam ltAlibabaSettlementReport);
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
     ResponseData autoCalPeopleCost(LtAlibabaSettlementReportParam param);


     ResponseData haveReport(LtAlibabaSettlementReportParam param);

     ResponseData insertStructure(LtAlibabaSettlementReportParam param);
 }