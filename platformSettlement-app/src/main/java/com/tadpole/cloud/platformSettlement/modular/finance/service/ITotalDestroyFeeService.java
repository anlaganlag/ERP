package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TotalDestroyFee;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalDestroyFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalDestroyFeeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalDestroyFeeTotal;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 销毁移除费用统计 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-10-18
 */
public interface ITotalDestroyFeeService extends IService<TotalDestroyFee> {

    PageTotalResult<TotalDestroyFeeResult, TotalDestroyFeeTotal> findPageBySpec(TotalDestroyFeeParam param);

    List<TotalDestroyFee> export(TotalDestroyFeeParam param);

    void afreshCount(String durDate);

    void confirm(TotalDestroyFeeParam param);

    int add(TotalDestroyFeeParam param);

    TotalDestroyFeeResult getFifferenceFee(TotalDestroyFeeParam param);

    TotalDestroyFeeResult getProductBySku(TotalDestroyFeeParam param);

    void freshDisposeReturnFee(TotalDestroyFeeParam param);

    List<TotalDestroyFeeResult> emailList();

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    void updateLatestDate(TotalDestroyFeeParam param) throws ParseException;

    void deleteById(TotalDestroyFeeParam param);

    void updateById(TotalDestroyFeeParam param);



    @Transactional
    @DataSource(name = "finance")
    ResponseData pushDestroyManualAllocSql(TotalDestroyFeeParam param) throws ParseException;

    ResponseData pushDestroyManualAlloc(TotalDestroyFeeParam param) throws ParseException;

}
