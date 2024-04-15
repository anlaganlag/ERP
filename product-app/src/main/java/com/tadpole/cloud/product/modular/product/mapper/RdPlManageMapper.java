package com.tadpole.cloud.product.modular.product.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.product.modular.product.entity.RdPlManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 产品线管理 Mapper接口
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
public interface RdPlManageMapper extends BaseMapper<RdPlManage> {

    IPage<RdPlManageResult> listPage(Page pageContext, @Param("paramCondition") RdPlManageParam param);

    List<RdPlManageResult> list(@Param("paramCondition") RdPlManageParam param);
}
