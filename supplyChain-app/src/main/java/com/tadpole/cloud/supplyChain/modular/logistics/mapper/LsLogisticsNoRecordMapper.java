package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoRecordResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物流单费用操作记录 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2024-03-19
 */
public interface LsLogisticsNoRecordMapper extends BaseMapper<LsLogisticsNoRecord> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<LsLogisticsNoRecordResult> queryPage(@Param("page") Page page, @Param("param") LsLogisticsNoRecordParam param);

}
