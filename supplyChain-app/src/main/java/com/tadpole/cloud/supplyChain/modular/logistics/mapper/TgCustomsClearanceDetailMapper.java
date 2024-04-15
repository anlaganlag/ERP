package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearanceDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsClearanceDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 清关单明细 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface TgCustomsClearanceDetailMapper extends BaseMapper<TgCustomsClearanceDetail> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsClearanceDetailResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsClearanceDetailParam param);

    /**
     * 查询列表
     * @param param
     * @return
     */
    List<TgCustomsClearanceDetailResult> queryList(@Param("param") TgCustomsClearanceDetailParam param);

}
