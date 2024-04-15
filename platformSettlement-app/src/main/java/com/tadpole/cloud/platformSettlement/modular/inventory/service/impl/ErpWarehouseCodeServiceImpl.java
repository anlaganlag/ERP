package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ErpWarehouseCode;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.ErpWarehouseCodeMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ErpWarehouseCodeParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ERP仓库组织编码 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-12-07
 */
@Service
public class ErpWarehouseCodeServiceImpl extends ServiceImpl<ErpWarehouseCodeMapper, ErpWarehouseCode> implements IErpWarehouseCodeService {

  @DataSource(name = "warehouse")
  @Override
  public String getOrganizationCode(String param) {
    return  this.baseMapper.getOrganizationCode(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData getOrganizationCodeByWarehouse(String param) {
    String organizationCode = this.baseMapper.getOrganizationCodeByWarehouse(param);
    if (ObjUtil.isNotEmpty(organizationCode)) {
      return ResponseData.success(organizationCode);
    }
    return ResponseData.error(param+"未找到组织编码");
  }

  @DataSource(name = "BIDM")
  @Override
  public String getSkuSalesOrganization(ErpWarehouseCodeParam param) {
    return this.baseMapper.getSkuSalesOrganization(param);
  }

  @DataSource(name = "finance")
  @Override
  public String getOtherSalesOrganization(String merchantOrderId ){
    return this.baseMapper.getOtherSalesOrganization(merchantOrderId);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<Map<String, String>> orgCodeMap(ErpWarehouseCodeParam param) {
    return this.baseMapper.orgCodeMap(param);
  }

  @DataSource(name = "BIDM")
  @Override
  public String getSalesOrganization(String orderId) {
    return this.baseMapper.getSalesOrganization(orderId);

  }

  @DataSource(name = "warehouse")
  @Override
  public List<Map<String, String>> getOrgCodeInfo() {
    return this.baseMapper.getOrgCodeInfo();
  }

  @DataSource(name = "warehouse")
  @Override
  public Map<String, String> getOrgCodeMap() {
    Map<String, String> map = new HashMap<>(2048);
    List<Map<String, String>> list = this.baseMapper.getOrgCodeMap();
    if (CollUtil.isNotEmpty(list)) {
      for (Map<String, String> mp : list) {
        String key = "";
        String value = "";
        for (Map.Entry<String, String> entry : mp.entrySet()) {
          if ("ORG".equals(entry.getKey())) {
            key =  entry.getValue();
          } else if ("CODE".equals(entry.getKey())) {
            value = entry.getValue();
          }
        }
        map.put(key, value);
      }
    }
    return map;
  }
  @DataSource(name = "warehouse")
  @Override
  public Map<String, String> getOrgMap() {
    Map<String, String> map = new HashMap<>(2048);
    List<Map<String, String>> list = this.baseMapper.getOrgMap();
    if (CollUtil.isNotEmpty(list)) {
      for (Map<String, String> mp : list) {
        String key = "";
        String value = "";
        for (Map.Entry<String, String> entry : mp.entrySet()) {
          if ("CODE".equals(entry.getKey())) {
            key =  entry.getValue();
          } else if ("ORG".equals(entry.getKey())) {
            value = entry.getValue();
          }
        }
        map.put(key, value);
      }
    }
    return map;
  }
}
