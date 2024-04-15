package com.tadpole.cloud.product.modular.product.service;

import com.tadpole.cloud.product.modular.product.entity.RdManageUpRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.modular.product.model.params.RdManageUpRecordParam;
import com.tadpole.cloud.product.modular.product.model.result.RdManageUpRecordResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author S20210221
 * @since 2023-12-08
 */
public interface IRdManageUpRecordService extends IService<RdManageUpRecord> {

    List<RdManageUpRecordResult> getList(RdManageUpRecordParam param);
}
