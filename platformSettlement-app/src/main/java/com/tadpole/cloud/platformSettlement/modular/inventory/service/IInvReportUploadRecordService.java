package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InvReportUploadRecord;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InvReportUploadRecordParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 上传记录表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-26
 */
public interface IInvReportUploadRecordService extends IService<InvReportUploadRecord> {

  PageResult<InvReportUploadRecord> findPageBySpec(InvReportUploadRecordParam param);

  void insertFbaShipmentSalesRecord(InvReportUploadRecordParam param);

  void insertFbaCustomerReturnsRecord(InvReportUploadRecordParam param);

  void insertRemovalOrderDetailRecord(InvReportUploadRecordParam param);

  void insertRemoveMainRecord(InvReportUploadRecordParam param);

  void insertFbaInventoryAdjustmentsRecord(InvReportUploadRecordParam param);

  void insertMonthlyInventoryHistoryRecord(InvReportUploadRecordParam param);

  void insertRemovalShipmentDetailRecord(InvReportUploadRecordParam param);

  boolean checkFileName(String fileName, InvReportUploadRecordParam param);
}
