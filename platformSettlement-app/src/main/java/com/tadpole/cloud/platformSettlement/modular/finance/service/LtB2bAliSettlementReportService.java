package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bAliSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtB2bAliSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtB2bAliSettlementReportParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(LT_B2B_ALI_SETTLEMENT_REPORT)表服务接口
 * @author : LSY
 * @date : 2023-12-22
 */
public interface LtB2bAliSettlementReportService extends IService<LtB2bAliSettlementReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    LtB2bAliSettlementReport queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param ltB2bAliSettlementReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<LtB2bAliSettlementReportResult> paginQuery(LtB2bAliSettlementReportParam ltB2bAliSettlementReport, long current, long size);

     List<LtB2bAliSettlementReport> paginExport(LtB2bAliSettlementReportParam param);

     /**
     * 新增数据
     *
     * @param ltB2bAliSettlementReport 实例对象
     * @return 实例对象
     */
    LtB2bAliSettlementReport insert(LtB2bAliSettlementReport ltB2bAliSettlementReport);
    /** 
     * 更新数据
     *
     * @param ltB2bAliSettlementReport 实例对象
     * @return 实例对象
     */
    LtB2bAliSettlementReport update(LtB2bAliSettlementReportParam ltB2bAliSettlementReport);
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
     ResponseData importSettlementReport(MultipartFile file);
 }