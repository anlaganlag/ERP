package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtRakutenSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtRakutenSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtRakutenSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.DepartTeamProductTypeParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtRakutenSettlementReportParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

 /**
 * ;(LT_RAKUTEN_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-22
 */
public interface LtRakutenSettlementReportService extends IService<LtRakutenSettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtRakutenSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltRakutenSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtRakutenSettlementReportResult> paginQuery(LtRakutenSettlementReportParam ltRakutenSettlementReport, long current, long size);

     List<LtRakutenSettlementReport> paginExport(LtRakutenSettlementReportParam param);

     @DataSource(name = "finance")
     List<LtRakutenSettlementReportExport> downloadExport(LtRakutenSettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltRakutenSettlementReport 实例对象
     * @return 实例对象
     */
    LtRakutenSettlementReport insert(LtRakutenSettlementReport ltRakutenSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltRakutenSettlementReport 实例对象
     * @return 实例对象
     */
    LtRakutenSettlementReport update(LtRakutenSettlementReportParam ltRakutenSettlementReport);

     @DataSource(name = "finance")
     ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList);

     /**
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    boolean deleteById(String undefinedId);

     ResponseData mergeRakutenSettleNallocStruct(LtRakutenSettlementReportParam param);

     /**
     * 通过主键删除数据--批量删除
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> undefinedIdList);


     @DataSource(name = "finance")
     int updatePeopleCostZero(LtRakutenSettlementReportParam param);


     ResponseData autoCalPeopleCost(LtRakutenSettlementReportParam param);

     ResponseData haveReport(LtRakutenSettlementReportParam param);

     ResponseData insertStructure(LtRakutenSettlementReportParam param);



     List<String> getProductType(DepartTeamProductTypeParam param);
 }