package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBaseProductDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBaseProductDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBaseProductDetailResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 通关产品详细信息 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgBaseProductDetailMapper extends BaseMapper<TgBaseProductDetail> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgBaseProductDetailResult> queryPage(@Param("page") Page page, @Param("param") TgBaseProductDetailParam param);

}
