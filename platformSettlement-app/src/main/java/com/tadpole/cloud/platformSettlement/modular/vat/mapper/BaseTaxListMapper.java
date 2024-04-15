package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseTaxList;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxListParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxListResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础信息-税号列表 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface BaseTaxListMapper extends BaseMapper<BaseTaxList> {

    Page<BaseTaxListResult> queryListPage(@Param("page") Page page, @Param("paramCondition") BaseTaxListParam paramCondition);

    @Select("SELECT SHOP_NAME shopName,TAXN_OVERSEAS taxnOverseas,TAXN_STATE  taxnState FROM STOCKING.TB_COM_TAX_NUM")
    List<Map<String,String>> queryTaxNums();
}
