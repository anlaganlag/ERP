package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLpDepositPrepayment;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLpDepositPrepaymentResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物流商押金&预付 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
public interface LsLpDepositPrepaymentMapper extends BaseMapper<LsLpDepositPrepayment> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<LsLpDepositPrepaymentResult> queryPage(@Param("page") Page page, @Param("param") LsLpDepositPrepaymentParam param);

}
