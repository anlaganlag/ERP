package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsNewPrice;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsNewPriceParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceExportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceResult;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

 /**
 * 物流商的价格信息;(tb_logistics_new_price)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsNewPriceService extends IService<TbLogisticsNewPrice> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogpId 主键
     * @return 实例对象
     */
    TbLogisticsNewPrice queryById(BigDecimal pkLogpId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsNewPrice 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsNewPriceResult> paginQuery(TbLogisticsNewPriceParam tbLogisticsNewPrice, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsNewPrice 实例对象
     * @return 实例对象
     */
    TbLogisticsNewPrice insert(TbLogisticsNewPrice tbLogisticsNewPrice);
    /** 
     * 更新数据
     *
     * @param tbLogisticsNewPrice 实例对象
     * @return 实例对象
     */
    TbLogisticsNewPrice update(TbLogisticsNewPriceParam tbLogisticsNewPrice);
    /** 
     * 通过主键删除数据
     *
     * @param pkLogpId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkLogpId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param pkLogpIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> pkLogpIdList);

     /**
      * 物流价格导出
      * @param tbLogisticsNewPriceParam
      * @return
      */
     List<TbLogisticsNewPriceExportResult> export(TbLogisticsNewPriceParam tbLogisticsNewPriceParam);

     /**
      * 导入
      * @param file
      * @return
      */
     ResponseData importExcel(MultipartFile file);

     /**
      * 批量添加-物流商的价格信息
      * @param newPriceList
      * @return
      */
     ResponseData batchAdd(List<TbLogisticsNewPriceExportResult> newPriceList);

     /**
      * 查询物流价格
      * @param model
      * @param lhrSendGoodDate  发货日期
      * @param logGoodCharacter  货物特性
      * @param lerPreChargType 计费方式
      * @return
      */
     List<TbLogisticsNewPriceDetResult> getTbLogisticsNewPriceDet(TbLogisticsListToHeadRoute model,  Date lhrSendGoodDate,String logGoodCharacter, String lerPreChargType);


     ResponseData busLspNumList(String busLpCountryCode);
 }