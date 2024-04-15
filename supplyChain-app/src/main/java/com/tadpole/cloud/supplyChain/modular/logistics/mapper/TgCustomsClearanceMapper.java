package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.api.saihu.entity.SaihuShop;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearance;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearanceDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 清关单 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface TgCustomsClearanceMapper extends BaseMapper<TgCustomsClearance> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsClearanceResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsClearanceParam param);

    /**
     * 获取EBMS出货订单明细数据
     * @param packCode
     * @return
     */
    List<TgCustomsClearanceDetail> selectLogisticsPackingDetail(@Param("packCode") String packCode);

    /**
     * 获取最新的发票号
     * @param orderPre
     * @return
     */
    String getNowQgdOrder(@Param("orderPre") String orderPre);

    /**
     * 获取清关发票sheet0箱子列表
     * @param param
     * @return
     */
    List<TgCustomsClearanceExport0ListResult> generateExportBox0(@Param("param") TgCustomsClearanceParam param);

    /**
     * 获取清关发票基本信息
     * @param param
     * @return
     */
    List<TgCustomsClearanceExport0ListResult> getClearanceInfo(@Param("param") TgCustomsClearanceParam param);

    /**
     * 获取人工合并单个品名毛重
     * @param param
     * @return
     */
    TgCustomsClearanceExport0ListResult getHandAvgWeight(@Param("param") TgCustomsClearanceParam param);

    /**
     * 获取系统合并单个品名毛重
     * @param param
     * @return
     */
    TgCustomsClearanceExport0ListResult getSysAvgWeight(@Param("param") TgCustomsClearanceParam param);

    /**
     * 生成清关发票sheet0列表
     * @param param
     * @return
     */
    List<TgCustomsClearanceExport0ListResult> generateExport0(@Param("param") TgCustomsClearanceParam param);

    /**
     * 生成清关发票快递sheet0列表
     * @param param
     * @return
     */
    List<TgCustomsClearanceExport0ListResult> generateExportKD0(@Param("param") TgCustomsClearanceParam param);

    /**
     * 判断是否存在1个箱子有多个重量的情况
     * @param param
     * @return
     */
    Integer haveMoreWeight(@Param("param") TgCustomsClearanceParam param);

    /**
     * 根据单号和分单号获取清关发票总重量
     * @param param
     * @return
     */
    TgCustomsClearanceExport0Result getClearanceSumWeight(@Param("param") TgCustomsClearanceParam param);

    /**
     * 根据清关品名获取清关产品信息重量
     * @param invoiceProNameCn
     * @return
     */
    List<BigDecimal>getMergeProductWeight(@Param("invoiceProNameCn") String invoiceProNameCn);

    /**
     * 根据清关品名获取产品基本信息重量
     * @param invoiceProNameCn
     * @return
     */
    List<BigDecimal> getProductWeight(@Param("invoiceProNameCn") String invoiceProNameCn);

    /**
     * 生成清关发票sheet1
     * @param param
     * @return
     */
    List<TgCustomsClearanceExport1Result> generateExport1(@Param("param") TgCustomsClearanceParam param);

    /**
     * 生成清关发票快递sheet1
     * @param param
     * @return
     */
    List<TgCustomsClearanceExport1Result> generateExportKD1(@Param("param") TgCustomsClearanceParam param);

    /**
     * 清关合并预览
     * @param param
     * @return
     */
    List<TgCustomsClearanceDetailResult> clearMergePreview(@Param("param") TgCustomsClearanceDetailParam param);

    /**
     * 查看已合并清关产品
     * @param param
     * @return
     */
    List<TgCustomsClearanceDetailResult> selectClearMerge(@Param("param") TgCustomsClearanceDetailParam param);

    /**
     * 获取赛狐店铺
     * @return
     */
    List<SaihuShop> getSaihuShop();

}
