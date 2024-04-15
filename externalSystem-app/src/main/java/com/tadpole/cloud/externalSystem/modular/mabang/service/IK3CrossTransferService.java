package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3CrossTransfer;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportK3CrossTransferResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferResult;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * K3跨组织直接调拨单主表 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-28
 */
public interface IK3CrossTransferService extends IService<K3CrossTransfer> {

    /**
     * 查询K3跨组织直接调拨单列表接口
     */
    PageResult<K3CrossTransferResult> findPageBySpec(K3CrossTransferParam param);

    /**
     * 查询K3跨组织直接调拨单列表V2接口
     */
    PageResult<ExportK3CrossTransferResult> findPageBySpecV2(K3CrossTransferParam param);

    /**
     * 查看详情
     */
    PageResult<K3CrossTransferItemResult> list(K3CrossTransferItemParam param);

    /**
     * k3跨组织调拨单列表导出接口
     */
    List<ExportK3CrossTransferResult> export(K3CrossTransferParam param);

    /**
     * 马帮订单同步跨组织调拨单接口  syncTransferToErp
     */
    ResponseData add(K3CrossTransferItemParam param) throws Exception;

    /**
     * 调入货主下拉
     * @return
     */
    List<Map<String, Object>> ownerIdHeadNameSelect();

    /**
     * 平台下拉
     * @return
     */
    List<Map<String, Object>> platformSelect();

    /**
     * 同步到K3跨组织调拨单
     */
    ResponseData syncTransferToErp() throws Exception;

    ResponseData checkReponseStatus() throws Exception;

    String getFinanceName(String fNumber);

    List<String> getFownerIdHead();

    List<ZZDistributeMcms> getFownerIdAndMat(int status);

    void updateOwnerIdName(String ownerId,String ownerName);

}
