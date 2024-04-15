package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomBoxInfoResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 报关自定义外箱表 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-08-18
 */
public interface TgCustomBoxInfoMapper extends BaseMapper<TgCustomBoxInfo> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomBoxInfoResult> queryPage(@Param("page") Page page, @Param("param") TgCustomBoxInfoParam param);

    /**
     * 根据区间查询体积重
     * @param param
     * @return
     */
    List<TgCustomBoxInfoResult> selectContainVal(@Param("param") TgCustomBoxInfoParam param);

    /**
     * 箱型下拉
     * @return
     */
    List<BaseSelectResult> boxTypeSelect();

}
