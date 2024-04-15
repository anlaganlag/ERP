package com.tadpole.cloud.operationManagement.modular.shipment.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentRecommendation;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecommendationParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SkuCheckParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationShopSkuResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationTemplateResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.SkuCheckResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* <p>
    * 发货推荐 Mapper 接口
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
public interface ShipmentRecommendationMapper extends BaseMapper<ShipmentRecommendation> {


    List<ShipmentRecommendation> getListByPK(@RequestBody List<String> pKList);

    List<ShipmentRecommendationTemplateResult> exportTemplateList(@Param("paramCondition") ShipmentRecommendationParam param);
    List<SkuCheckResult> skuCheck(@Param("paramCondition") SkuCheckParam param);
    List<String> failSku(@Param("paramCondition") List<SkuCheckParam> params);

    int exportTemplateCount(@Param("paramCondition") ShipmentRecommendationParam param);


    List<ShipmentRecommendationShopSkuResult> getShopSkuListByPK(String pk);
    List<ShipmentRecommendationShopSkuResult> getShopSkuListBySpec(@Param("paramCondition") ShipmentRecommendationParam param);


    IPage<ShipmentRecommendation> selectPageDatalimit(@Param("page") Page page, @Param("reqVO") ShipmentRecommendationParam param);
}
