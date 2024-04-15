package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.InitialBalance;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.InitialBalanceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InitialBalanceResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 设置期初余额 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IInitialBalanceService extends IService<InitialBalance> {

    PageResult<InitialBalanceResult> findPageBySpec(InitialBalanceParam param);


    List<InitialBalanceResult> exportInitialBalanceList(InitialBalanceParam param);


    void insertBatch(List<InitialBalance> BlackListList) ;


    void updateBalance(InitialBalanceParam param);


}
