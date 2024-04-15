package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyBaseApply;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ErpBankAccountParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyBaseApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ErpBankAccountResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyt
 * @since 2022-05-25
 */
public interface IRemovalDestroyBaseApplyService extends IService<RemovalDestroyBaseApply> {

    /**
     * Erp银行账号信息明细接口
     */
    PageResult<ErpBankAccountResult> bankAccountList(ErpBankAccountParam param);

    ResponseData processCheck(RemovalDestroyBaseApplyParam param);
}
