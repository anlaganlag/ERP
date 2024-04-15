package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.TotalDestroyFee;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalDestroyFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalDestroyFeeResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 销毁移除费用统计 Mapper 接口
 * </p>
 *
 * @author S20190161
 * @since 2022-10-18
 */
public interface TotalDestroyFeeMapper extends BaseMapper<TotalDestroyFee> {
    IPage<TotalDestroyFeeResult> queryListPage(@Param("page") Page page, @Param("param") TotalDestroyFeeParam param);

    /**
    * 刷新表数据
    * @author AmteMa
    * @date 2022/10/18
    */
    void afreshCount(@Param("date") String date);
    TotalDestroyFeeResult getFifferenceFee(@Param("settlementId")BigDecimal settlementId,@Param("orderId")String orderId);
    TotalDestroyFeeResult getProductBySku(@Param("param")TotalDestroyFeeParam param);

    void freshDisposeReturnFee(@Param("param") TotalDestroyFeeParam param);

    List<TotalDestroyFeeResult> emailList();

}
