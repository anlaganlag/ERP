package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.CloseLogisticsInGoodsParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.CreateBindPackListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsSingleBoxRecModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListResultModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel.ShipmentUploadExcelDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 亚马逊货件;(tb_logistics_pack_list)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackListService extends IService<TbLogisticsPackList> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsPackList queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackList 筛选条件
     * @return
     */
    PageResult<TbLogisticsPackListResultModel> pageQuery(TbLogisticsPackListParam tbLogisticsPackList);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackList 实例对象
     * @return 实例对象
     */
    TbLogisticsPackList insert(TbLogisticsPackList tbLogisticsPackList);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackList 实例对象
     * @return 实例对象
     */
    TbLogisticsPackList update(TbLogisticsPackListParam tbLogisticsPackList);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> idList);

    /**
     * 通过ShipmentID查询亚马逊货件明细数据
     * @param shipmentId
     * @return
     */
    Map<String, Object> getLogisticsPackListDetail(String shipmentId);

    List<LogisticsSingleBoxRecModel> getSingleBoxRec(String shipmentId, String sku);

    Map<String, Object> getLogisticsPackListDetailNew(String packListCode);

    List<LogisticsSingleBoxRecModel> getSingleBoxRecNew(String packListCode, String sku);

    /**
     * 导入PackList
     * @param param
     * @param file
     * @return
     */
    ResponseData packListImportNew(TbLogisticsPackListParam param, MultipartFile file) throws Exception;

    /**
     * PackList绑定出货清单(国内仓)（新版绑定）
     * @param createBindPackListParam
     * @return
     */
    ResponseData createBindPackListNew(CreateBindPackListParam createBindPackListParam);


    ResponseData newCancelPackList(String packListCode);

    ResponseData editShipmentInfo(TbLogisticsPackListParam param);

    ResponseData shipmentRealStatusChange(TbLogisticsPackListParam param);

    /**
     * 通过ShipmentID查询来货信息 sku维度
     * @param shipmentId
     * @return
     */
    ResponseData queryLogisticsInGoodsList(String shipmentId);

    ShipmentUploadExcelDTO getshipmentUploadExcelData(String packListCode);

    Workbook getWorkbook(String packListCode, String shopNameSimple,String packTempName);

    Workbook fillData(Workbook workbook, String packListCode);

    int deleteByPackCode(String packCode);

    /**
     * 查询标签信息  根据countryCode + Sku + FnSKU + MateCode
     * @param csfm
     * @return
     */
    List<String> getTbComShippingLableFromEbms(List<String> csfm);

    /**
     * 关闭在途商品
     * @param model
     * @return
     */
    ResponseData closeLogisticsInGoods(CloseLogisticsInGoodsParam model);
}