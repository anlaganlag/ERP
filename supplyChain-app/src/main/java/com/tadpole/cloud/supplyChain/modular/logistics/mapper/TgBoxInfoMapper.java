package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBoxInfoResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  报关外箱Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-07-07
 */
public interface TgBoxInfoMapper extends BaseMapper<TgBoxInfo> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgBoxInfoResult> queryPage(@Param("page") Page page, @Param("param") TgBoxInfoParam param);

    /**
     * 箱型下拉
     * @return
     */
    List<BaseSelectResult> boxTypeSelect();

}
