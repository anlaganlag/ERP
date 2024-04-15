package com.tadpole.cloud.externalSystem.modular.mabang.mapper;


import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangReturnOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangReturnOrderResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 马帮退货单列表 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
public interface MabangReturnOrderMapper extends BaseMapper<MabangReturnOrder> {

    IPage<MabangReturnOrderResult> listBySpec (@Param("page") Page page, @Param("paramCondition") MabangReturnOrderParam paramCondition);
    List<MabangReturnOrderResult> exportList (@Param("paramCondition") MabangReturnOrderParam paramCondition);

    List<String> queryPlatformNames();
    List<String> queryShopName();
    List<String> getStatusSelect();



}
