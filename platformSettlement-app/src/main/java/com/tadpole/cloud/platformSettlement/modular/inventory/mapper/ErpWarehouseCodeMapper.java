package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.ErpWarehouseCode;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ErpWarehouseCodeParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * ERP仓库组织编码 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-12-07
 */
public interface ErpWarehouseCodeMapper extends BaseMapper<ErpWarehouseCode> {


  String getOrganizationCode(@Param("name") String name);

  String getOrganizationCodeByWarehouse(@Param("name") String name);

  String getSkuSalesOrganization(@Param("paramCondition") ErpWarehouseCodeParam paramCondition);

  /**
   * 拿到OrderId去结算报告里匹配销售组织
   * @param merchantOrderId
   * @return
   */
  String getOtherSalesOrganization(String merchantOrderId);



  List<Map<String,String>> orgCodeMap(@Param("paramCondition") ErpWarehouseCodeParam paramCondition);

  String getSalesOrganization(@Param("amazonOrderId") String amazonOrderId);

  /**
   * 获取AmazonERP仓库组织信息
   * @return
   */
  List<Map<String,String>> getOrgCodeInfo();


  /**
   * 获取AmazonERP组织名称对应的组织编码
   * @return
   */
  List<Map<String, String>> getOrgCodeMap();
  List<Map<String, String>> getOrgMap();


}
