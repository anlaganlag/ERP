package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.FbaCustomerReturns;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaCustomerReturnsParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 亚马逊物流买家退货 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface IFbaCustomerReturnsService extends IService<FbaCustomerReturns> {

  /**
   * FBA customer return列表
   * @param param
   * @return
   */
  PageResult<FbaCustomerReturns> findPageBySpec(FbaCustomerReturnsParam param);

  /**
   * 导出
   * @param param
   * @return
   */
  List<FbaCustomerReturns> export(FbaCustomerReturnsParam param);

  /**
   * 获取数量合计
   * @param param
   * @return
   */
  Integer getQuantity(FbaCustomerReturnsParam param);

  /**
   * 添加库存仓库组织
   * @return
   */
  ResponseData addInvWareOrg();

  /**
   * 审核
   * @param param
   */
  void verify(FbaCustomerReturnsParam param);

  /**
   * 批量审核
   * @param param
   * @return
   */
  Boolean verifyUpdateBatch(FbaCustomerReturnsParam param);

  /**
   * 作废
   * @param param
   */
  void reject(FbaCustomerReturnsParam param);

  /**
   * 批量作废
   * @param params
   */
  void rejectBatch(List<FbaCustomerReturnsParam> params);

  /**
   * 生成销售退货列表
   * @param param
   * @return
   */
  ResponseData toSalesReturnList(FbaCustomerReturnsParam param);

  /**
   * 根据账号、站点、sku获取物料编码
   * @param param
   * @return
   */
  String getMaterial(FbaCustomerReturnsParam param);

  /**
   * 导入订单修改销售组织
   * @param file
   * @return
   */
  ResponseData upload(MultipartFile file);

  /**
   * 根据组织编码和物料编码分配物料
   * @param param
   */
  void assignMaterial(FbaCustomerReturnsParam param);

  /**
   * 根据组织编码和物料编码批量分配物料
   * @param assignList
   * @return
   */
  ResponseData assignBatchMaterial(List<ZZDistributeMcms> assignList);

  /**
   * 修改站点
   * @param params
   */
  ResponseData editSites(List<FbaCustomerReturnsParam> params);

  /**
   * 修改平台
   * @param params
   */
  void editPlatform(List<FbaCustomerReturnsParam> params);

  /**
   * 修改账号
   * @param params
   * @return
   */
  ResponseData editShop(List<FbaCustomerReturnsParam> params);

  /**
   * 批量更新销售组织
   * @param updateList
   * @return
   */
  Boolean updateBatch(List<FbaCustomerReturns> updateList);

  /**
   * 批量获取组织编码和物料编码分配物料
   * @param detailIdList
   * @return
   */
  List<ZZDistributeMcms> assignMaterialList(List<String> detailIdList);

  /**
   * 修改销售组织
   * @param params
   */
  ResponseData editSalesOrg(List<FbaCustomerReturnsParam> params);

  /**
   * 刷组织编码
   * @return
   */
  ResponseData addNewOrg();

  /**
   * 获取没有刷上销售组织编码和库存组织编码的数据
   * @return
   */
  List<FbaCustomerReturns> remainList();

}
