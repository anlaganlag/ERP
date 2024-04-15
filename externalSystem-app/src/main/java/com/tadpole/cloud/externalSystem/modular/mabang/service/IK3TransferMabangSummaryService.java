package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferMabangSummary;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * K3调拨单同步概要 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-15
 */
public interface IK3TransferMabangSummaryService extends IService<K3TransferMabangSummary> {



    K3TransferMabangSummary doSync(Map<String, String> mabangWarehouse, List<K3TransferItem> itemList);


    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    K3TransferMabangSummary doSync2(Map<String, String> mabangWarehouse, List<K3TransferItem> itemList);

    Map<String, String> getK3MabangWarehouse();


    ResponseData anitAudit(String billNo);
}
