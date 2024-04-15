package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsApplyDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 报关单明细 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface TgCustomsApplyDetailMapper extends BaseMapper<TgCustomsApplyDetail> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsApplyDetailResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsApplyDetailParam param);

    /**
     * 查询列表
     * @param param
     * @return
     */
    List<TgCustomsApplyDetailResult> queryList(@Param("param") TgCustomsApplyDetailParam param);

    /**
     * 获取报关单信息缺失明细数据
     * @param param
     * @return
     */
    List<TgCustomsApplyDetail> getIncompleteDetail(@Param("param") TgCustomsApplyDetail param);

}
