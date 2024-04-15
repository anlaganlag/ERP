package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseAge;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseAgeParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseAgeResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  海外仓库龄报表Mapper接口
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
public interface OverseasWarehouseAgeMapper extends BaseMapper<OverseasWarehouseAge> {
    /**
     * 海外仓库龄报表分页查询列表
     * @param param
     * @return
     */
    Page<OverseasWarehouseAgeResult> queryPage(@Param("page") Page page, @Param("param") OverseasWarehouseAgeParam param);
}
