package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3TransferItemParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * K3调拨单明细项 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-09
 */
public interface IK3TransferItemService extends IService<K3TransferItem> {

    List<K3TransferItem> list(@Param("paramCondition") K3TransferItemParam paramCondition);

    ResponseData add(List<K3TransferItem> itemList);


    Map<String, String> k3MabangWarehouse();

    ResponseData mabangWarehouse();

}
