package com.tadpole.cloud.operationManagement.modular.shipment.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentRecommendation;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecomD6Param;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecommendationParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SkuCheckParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationShopSkuResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationTemplateResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 发货推荐 服务类
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
public interface IShipmentRecommendationService extends IService<ShipmentRecommendation> {

    PageResult<ShipmentRecommendation> shipmentRecommendationList(ShipmentRecommendationParam param);


    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    PageResult<ShipmentRecommendation> shipmentRecommendationListDatalimit(ShipmentRecommendationParam param);

    ResponseData recommendationApplyShipmentSave(List<ShipmentApplyItem> applyItemList);

    ResponseData recommendationApplyShipmentSubmit(List<ShipmentApplyItem> param);


    ResponseData recommendationApplyShipmentSubmitDeprecated(List<ShipmentApplyItem> applyItemList);

    /**
     * 主键列表pKList 主键为平台+区域+事业部+Team+物料编码+ASIN 'Amazon'||PRE_AREA || department || team || material_code || asin
     * @param pKList
     * @return
     */
    List<ShipmentRecommendation> getListByPK(List<String> pKList);

    List<ShipmentRecommendationShopSkuResult> getShopSkuListByPK(String pk);

    List<ShipmentRecommendationShopSkuResult> getShopSkuListBySpec(ShipmentRecommendationParam param);

    public ShipmentRecommendation getByApplyItem(ShipmentRecomD6Param d6Param);


    List<ShipmentRecommendationTemplateResult> exportTemplateList(ShipmentRecommendationParam param);

    ResponseData exportTemplate(HttpServletResponse response, ShipmentRecommendationParam param) throws Exception ;

    ResponseData skuCheck(SkuCheckParam param);
    List<String> skuBatchCheck(List<SkuCheckParam> param);



    ResponseData exportTemplateSku(HttpServletResponse response, ShipmentRecommendationParam param) throws Exception ;


}
