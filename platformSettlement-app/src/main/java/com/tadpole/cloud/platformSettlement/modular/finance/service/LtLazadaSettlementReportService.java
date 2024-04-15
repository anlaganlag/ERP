package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtLazadaSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtLazadaSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtLazadaSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtLazadaSettlementReportParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(LT_LAZADA_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-22
 */
public interface LtLazadaSettlementReportService extends IService<LtLazadaSettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtLazadaSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltLazadaSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtLazadaSettlementReportResult> paginQuery(LtLazadaSettlementReportParam ltLazadaSettlementReport, long current, long size);

     List<LtLazadaSettlementReport> paginExport(LtLazadaSettlementReportParam param);

     @DataSource(name = "finance")
     List<LtLazadaSettlementReportExport> downloadExport(LtLazadaSettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltLazadaSettlementReport 实例对象
     * @return 实例对象
     */
    LtLazadaSettlementReport insert(LtLazadaSettlementReport ltLazadaSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltLazadaSettlementReport 实例对象
     * @return 实例对象
     */
    LtLazadaSettlementReport update(LtLazadaSettlementReportParam ltLazadaSettlementReport);
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


     ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList);

     @DataSource(name = "finance")
     ResponseData autoCalPeopleCost(LtLazadaSettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData haveReport(LtLazadaSettlementReportParam param);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class)
     ResponseData insertStructure(LtLazadaSettlementReportParam param);
 }