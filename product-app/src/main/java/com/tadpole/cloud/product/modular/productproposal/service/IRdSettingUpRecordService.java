package com.tadpole.cloud.product.modular.productproposal.service;

import com.tadpole.cloud.product.api.productproposal.entity.RdSettingUpRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSettingUpRecordParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提案-设置-设置修改记录 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
public interface IRdSettingUpRecordService extends IService<RdSettingUpRecord> {
    void batchAdd(List<RdSettingUpRecordParam> params);

    List<Map<String,Object>> listRdTlSettingLog();

    List<Map<String,Object>> listRdSdSettingLog();

    List<Map<String,Object>> listRdFarSettingLog();
}
