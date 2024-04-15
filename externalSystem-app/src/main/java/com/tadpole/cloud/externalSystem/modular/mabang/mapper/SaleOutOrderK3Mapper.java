package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderK3;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.SaleOutOrderK3QueryResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 根据K3仓库可用数量自动产生-销售出库单 Mapper 接口
    * </p>
*
* @author lsy
* @since 2023-04-07
*/
public interface SaleOutOrderK3Mapper extends BaseMapper<SaleOutOrderK3> {

    Page<SaleOutOrderK3QueryResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") SaleOutOrderK3QueryResult paramCondition);

}
