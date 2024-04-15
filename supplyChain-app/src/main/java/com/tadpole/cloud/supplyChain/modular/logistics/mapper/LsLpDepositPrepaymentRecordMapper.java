package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLpDepositPrepaymentRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLpDepositPrepaymentRecordResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物流商押金&预付操作记录 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-12-21
 */
public interface LsLpDepositPrepaymentRecordMapper extends BaseMapper<LsLpDepositPrepaymentRecord> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<LsLpDepositPrepaymentRecordResult> queryPage(@Param("page") Page page, @Param("param") LsLpDepositPrepaymentRecordParam param);

}
