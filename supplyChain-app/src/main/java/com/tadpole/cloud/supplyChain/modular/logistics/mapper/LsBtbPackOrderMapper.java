package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbLogisticsNo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackOrder;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackOrderDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackOrderDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackOrderParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  BTB订单发货Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
public interface LsBtbPackOrderMapper extends BaseMapper<LsBtbPackOrder> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<LsBtbPackOrderResult> queryPage(@Param("page") Page page, @Param("param") LsBtbPackOrderParam param);

    /**
     * 明细查询列表
     * @param param
     * @return
     */
    List<LsBtbPackOrderDetailResult> queryDetail(@Param("param") LsBtbPackOrderDetailParam param);

    /**
     * 根据物料编码获取K3物料信息
     * @param materialCodeSet
     * @return
     */
    List<LsBtbPackBoxDetailResult> getK3MaterialInfo(@Param("materialCodeSet") Set materialCodeSet);

    /**
     * 定时同步马帮BTB订单信息
     */
    void syncBtbOrderInfo();

    /**
     * 获取马帮BTB订单明细信息
     * @return
     */
    List<LsBtbPackOrderDetail> getBtbOrderDetailInfo();

    /**
     * 物流账号下拉
     * @return
     */
    List<LsLogisticAccountResult> logisticsAccountSelect();

    /**
     * 国家分区下拉
     * @param param
     * @return
     */
    List<LsLogisticAccountResult> lCountryPartitionSelect(@Param("param") LsLogisticAccountResult param);

    /**
     * 收货分区下拉
     * @param param
     * @return
     */
    List<LsLogisticAccountResult> lCountryPartitionAreaSelect(@Param("param") LsLogisticAccountResult param);

    /**
     * 货运方式1下拉
     * @return
     */
    List<BaseSelectResult> freightCompanySelect();

    /**
     * 运输方式下拉
     * @return
     */
    List<BaseSelectResult> transportTypeSelect();

    /**
     * 物流渠道下拉
     * @return
     */
    List<BaseSelectResult> logisticsChannelSelect();

    /**
     * 货物特性下拉
     * @return
     */
    List<BaseSelectResult> goodsPropertySelect();

    /**
     * 获取物流商价格
     * @return
     */
    List<LsLogisticsPriceResult> getLpPrice(@Param("param") LsBtbLogisticsNo param);

    /**
     * 物流实际结算平台下拉
     * @return
     */
    List<String> btbPlatformSelect();

    /**
     * 物流实际结算账号下拉
     * @return
     */
    List<String> btbShopNameSelect();

    /**
     * 物流实际结算站点下拉
     * @return
     */
    List<String> btbSiteSelect();

}
