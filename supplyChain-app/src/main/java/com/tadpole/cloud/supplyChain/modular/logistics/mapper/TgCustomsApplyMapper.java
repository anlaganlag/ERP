package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApply;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 报关单 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface TgCustomsApplyMapper extends BaseMapper<TgCustomsApply> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsApplyResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsApplyParam param);

    /**
     * 获取EBMS出货订单数据
     * @param packCodeList
     * @return
     */
    List<TgLogisticsPackingResult> selectLogisticsPacking(@Param("packCodeList") List<String> packCodeList);

    /**
     * 获取EBMS出货订单明细数据
     * @param packCode
     * @return
     */
    List<TgCustomsApplyDetail> selectLogisticsPackingDetail(@Param("packCode") String packCode);

    /**
     * 根据出货清单号获取EBMS出货订单ShipmentID数据
     * @param packCodeList
     * @return
     */
    List<TgLogisticsPackingResult> selectLogisticsPackingShipment(@Param("packCodeList") List<String> packCodeList);

    /**
     * 出货清单号下拉
     * @return
     */
    List<BaseSelectResult> packCodeSelect();

    /**
     * 获取最新的合同协议号
     * @param orderPre
     * @return
     */
    String getNowBgdOrder(@Param("orderPre") String orderPre);

    /**
     * 生成国内报关单sheet0数据
     * @param param
     * @return
     */
    List<TgCustomsApplyExport0ListResult> generateExport0(@Param("param") TgCustomsApplyParam param);

    /**
     * 关联获取产品基本信息的K3价格、是否含税和重量单位
     * @param param
     * @return
     */
    List<TgCustomsApplyExport0ListResult> getBaseProductInfo(@Param("param") TgCustomsApplyParam param);

    /**
     * 生成国内报关单sheet2数据
     * @param param
     * @return
     */
    List<TgCustomsApplyExport2ListResult> generateExport2(@Param("param") TgCustomsApplyParam param);

    /**
     * 生成国内报关单sheet1/sheet3数据
     * @param param
     * @return
     */
    List<TgCustomsApplyExport3ListResult> generateExport3(@Param("param") TgCustomsApplyParam param);

    /**
     * 生成国内报关单sheet4数据
     * @param param
     * @return
     */
    List<TgCustomsApplyExport4ListResult> generateExport4(@Param("param") TgCustomsApplyParam param);

}
