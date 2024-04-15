package com.tadpole.cloud.product.modular.product.mapper;

import com.tadpole.cloud.product.modular.product.entity.RdManageUpRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.modular.product.model.params.RdManageUpRecordParam;
import com.tadpole.cloud.product.modular.product.model.result.RdManageUpRecordResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper接口
 * </p>
 *
 * @author S20210221
 * @since 2023-12-08
 */
public interface RdManageUpRecordMapper extends BaseMapper<RdManageUpRecord> {

    List<RdManageUpRecordResult> getList(@Param("paramCondition") RdManageUpRecordParam param);
}
