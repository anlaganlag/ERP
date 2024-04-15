package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangShopList;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangShopListParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangShopListResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 马帮店铺列表 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
public interface MabangShopListMapper extends BaseMapper<MabangShopList> {


    Page<MabangShopListResult> list(@Param("page") Page page, @Param("paramCondition") MabangShopListParam paramCondition);

    List<String> queryNames();

    List<String> queryPlatformNames();
    List<String> queryFinanceCodeList();
}
