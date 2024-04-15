package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatCheck;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatCheckParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatCheckResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * VAT核对表 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface VatCheckMapper extends BaseMapper<VatCheck> {

    /**
     * VAT核对表查询列表
     * @param param
     * @return
     */
    Page<VatCheckResult> queryListPage(@Param("page") Page page, @Param("param") VatCheckParam param);

    /**
     * VAT核对表查询明细合计
     * @param param
     * @return
     */
    VatCheckResult listSum(@Param("param") VatCheckParam param);

    /**
     *VAT导入批量更新
     * @param reqParamList
     */
    void commitUpdateBatch(@Param("reqParamList") List<VatCheck> reqParamList);
}
