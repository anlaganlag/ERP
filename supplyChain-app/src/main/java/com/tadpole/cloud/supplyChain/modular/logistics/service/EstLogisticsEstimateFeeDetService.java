package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFeeDet;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetUploadParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetParam;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ;(EST_LOGISTICS_ESTIMATE_FEE_DET)表服务接口
 * @author : LSY
 * @date : 2024-3-14
 */
public interface EstLogisticsEstimateFeeDetService extends IService<EstLogisticsEstimateFeeDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EstLogisticsEstimateFeeDet queryById(String id);
    
    /**
     * 分页查询
     *
     * @param estLogisticsEstimateFeeDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<EstLogisticsEstimateFeeDetResult> paginQuery(EstLogisticsEstimateFeeDetParam estLogisticsEstimateFeeDet, long current, long size);


    String generateShipmentId(String estId);

    List<LsLogisticsPriceResult> getLpPrice(EstLogisticsEstimateFeeDetParam param);

     /**
     * 新增数据
     *
     * @param estLogisticsEstimateFeeDet 实例对象
     * @return 实例对象
     */
    EstLogisticsEstimateFeeDet insert(EstLogisticsEstimateFeeDet estLogisticsEstimateFeeDet);
    /** 
     * 更新数据
     *
     * @param estLogisticsEstimateFeeDet 实例对象
     * @return 实例对象
     */
    EstLogisticsEstimateFeeDet update(EstLogisticsEstimateFeeDetParam estLogisticsEstimateFeeDet);
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




     ResponseData singleCostEst(EstLogisticsEstimateFeeDetParam param);

    List<EstLogisticsEstimateFeeDetExportResult>  exportList(EstLogisticsEstimateFeeDetParam param) ;

    ResponseData uploadPackList(EstLogisticsEstimateFeeDetUploadParam param, MultipartFile file) throws IOException, InvalidFormatException;


     ResponseData uploadPackListJson(MultipartFile file) throws IOException, InvalidFormatException;

    ResponseData entrySingleCostEst(EstLogisticsEstimateFeeDetParam param);

    ResponseData allCostEst(List<EstLogisticsEstimateFeeDetParam> param);


     List<String> shipToList();


    List<String> currencyList();

    List<String> logisticsProviderList();

    @DataSource(name = "EBMS")
    List<String> siteList();

    Map<String, String> getPostCodeMap();

    @DataSource(name = "EBMS")
    List<LogisticsInfoResult> getLogisticsInfo();

    @DataSource(name = "EBMS")
    List<String> freightCompanyList();

    @DataSource(name = "EBMS")
    List<String> transportTypeList();

    @DataSource(name = "EBMS")
    List<String> logisticsChannelList();

    @DataSource(name = "EBMS")
    List<String> orderTypeList();

    @DataSource(name = "EBMS")
    List<String> lspNumList(EstLogisticsEstimateFeeDetParam param);

    FixedExchangeRate getFixedRate();



    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    ResponseData delByShipmentId(String estId,String shipmentId);

    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    ResponseData updateByShipmentId(EstLogisticsEstimateFeeDetParam param);
}