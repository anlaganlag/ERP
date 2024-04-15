package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbLogisticsNo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackOrder;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackOrderDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.BtbPackApplyShipmentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackOrderDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackOrderParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbPackBoxDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbPackOrderResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticAccountResult;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  BTB订单发货服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
public interface ILsBtbPackOrderService extends IService<LsBtbPackOrder> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(LsBtbPackOrderParam param);

    /**
     * 明细查询列表
     * @param param
     * @return
     */
    ResponseData queryDetail(LsBtbPackOrderDetailParam param);

    /**
     * 申请发货
     * @param param
     * @return
     */
    ResponseData applyShipment(BtbPackApplyShipmentParam param);

    /**
     * 计算预估费用
     * @param param
     * @param rateListMap
     * @return
     */
    ResponseData calculatePredict(LsBtbLogisticsNo param, Map<String, List<FixedExchangeRate>> rateListMap);

    /**
     * 物流单号是否存在校验
     * @param param
     * @return
     */
    ResponseData hasLogisticsNo(BtbPackApplyShipmentParam param);

    /**
     * 编辑导入
     * @param id
     * @param file
     * @return
     */
    ResponseData importExcel(BigDecimal id, MultipartFile file);

    /**
     * 编辑保存
     * @param param
     * @return
     */
    ResponseData edit(LsBtbPackOrderResult param);

    /**
     * 导出
     * @param param
     * @return
     */
    LsBtbPackOrderResult export(LsBtbPackOrderParam param);

    /**
     * 根据物料编码获取K3物料信息
     * @param materialCodeSet
     * @return
     */
    List<LsBtbPackBoxDetailResult> getK3MaterialInfo(Set materialCodeSet);

    /**
     * 定时同步马帮BTB订单信息
     * @return
     */
    ResponseData syncBtbOrderInfo();

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
    List<LsLogisticAccountResult> lCountryPartitionSelect(LsLogisticAccountResult param);

    /**
     * 收货分区下拉
     * @param param
     * @return
     */
    List<LsLogisticAccountResult> lCountryPartitionAreaSelect(LsLogisticAccountResult param);

    /**
     * 货运方式1下拉
     * @return
     */
    ResponseData freightCompanySelect();

    /**
     * 运输方式下拉
     * @return
     */
    ResponseData transportTypeSelect();

    /**
     * 物流渠道下拉
     * @return
     */
    ResponseData logisticsChannelSelect();

    /**
     * 货物特性下拉
     * @return
     */
    ResponseData goodsPropertySelect();

    /**
     * 物流实际结算平台下拉
     * @return
     */
    List<BaseSelectResult> btbPlatformSelect();

    /**
     * 物流实际结算账号下拉
     * @return
     */
    List<BaseSelectResult> btbShopNameSelect();

    /**
     * 物流实际结算站点下拉
     * @return
     */
    List<BaseSelectResult> btbSiteSelect();

}
