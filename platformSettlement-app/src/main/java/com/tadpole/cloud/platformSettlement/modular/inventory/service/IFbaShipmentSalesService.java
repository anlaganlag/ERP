package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ErpOrgCode;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.FbaShipmentSales;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaShipmentSalesParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 亚马逊物流买家货件销售 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-23
 */
public interface IFbaShipmentSalesService extends IService<FbaShipmentSales> {

  /**
   * 分页查询列表
   * @param param
   * @return
   */
  PageResult<FbaShipmentSales> findPageBySpec(FbaShipmentSalesParam param);

  /**
   * Quantity合计
   * @param param
   * @return
   */
  Integer getQuantity(FbaShipmentSalesParam param);

  /**
   * 导入订单修改销售组织
   * @param file
   * @return
   */
  ResponseData upload(MultipartFile file);

  /**
   * fbaShipmentSales导出
   * @param param
   * @return
   */
  List<FbaShipmentSales> export(FbaShipmentSalesParam param);

  /**
   * 根据账号、站点、sku获取物料编码
   * @param param
   * @return
   */
  String getMaterial(FbaShipmentSalesParam param);

  /**
   * 根据组织编码和物料编码分配物料
   * @param param
   */
  void assignMaterial(FbaShipmentSalesParam param);

  /**
   * 取出待分配的物料和销售组织编码
   * @param assignList
   * @return
   */
  ResponseData assignBatchMaterial(List<ZZDistributeMcms> assignList) ;

  /**
   * 审核
   * @param param
   */
  void verify(FbaShipmentSalesParam param);

  /**
   * 批量审核
   * @param param
   * @return
   */
  Boolean verifyUpdateBatch(FbaShipmentSalesParam param);

  /**
   * 获取没有刷上销售组织编码和库存组织编码的数据
   * @return
   */
  List<FbaShipmentSales> remainList();

  /**
   * 批量更新销售组织
   * @param updateList
   * @return
   */
  Boolean updateBatch(List<FbaShipmentSales> updateList);

  /**
   * 作废
   * @param param
   */
  void reject(FbaShipmentSalesParam param);

  /**
   * 批量作废
   * @param params
   */
  void rejectBatch(List<FbaShipmentSalesParam> params);

  /**
   * 生成销售出库列表
   * @param param
   * @return
   * @throws IOException
   */
  ResponseData toSalesStockOutList(FbaShipmentSalesParam param) throws IOException;

  /**
   * 同步ERP仓库组织编码
   * @param codes
   */
  void syncErpCode(List<ErpOrgCode> codes);

  /**
   * 同步ERP仓库组织编码
   * @return
   */
  List<ErpOrgCode> getErpCode();

  /**
   * 修改站点
   * @param params
   * @return
   */
  ResponseData editSites(List<FbaShipmentSalesParam> params);

  /**
   * 修改平台
   * @param params
   */
  void editPlatform(List<FbaShipmentSalesParam> params);

  /**
   * 修改账号
   * @param params
   * @return
   */
  ResponseData editShop(List<FbaShipmentSalesParam> params);

  /**
   * 获取销售出库列表组织编码及物料编码
   * @param detailIdList
   * @return
   */
  List<ZZDistributeMcms> assignMaterialList(List<String> detailIdList);

  /**
   * 修改销售组织
   * @param params
   */
  ResponseData editSalesOrg(List<FbaShipmentSalesParam> params);

  /**
   * 刷组织编码
   * @return
   */
  ResponseData addNewOrg();
}
