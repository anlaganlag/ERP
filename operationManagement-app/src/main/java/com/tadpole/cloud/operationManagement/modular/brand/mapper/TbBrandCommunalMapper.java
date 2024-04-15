package com.tadpole.cloud.operationManagement.modular.brand.mapper;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandCommunal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandCommunalParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandCommunalResult;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* <p>
* 品牌管理主表 Mapper接口
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
public interface TbBrandCommunalMapper extends BaseMapper<TbBrandCommunal> {

    Page<TbBrandCommunalResult> getPage(Page page, @Param("param") TbBrandCommunalParam param);
}
