package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatSalesDetail;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatSalesDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatSalesDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 税金测算Sales明细 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface VatSalesDetailMapper extends BaseMapper<VatSalesDetail> {

    /**
     * 税金测算Sales明细查询列表
     * @param param
     * @return
     */
    Page<VatSalesDetailResult> queryListPage(@Param("page") Page page, @Param("param") VatSalesDetailParam param);

    /**
     * 税金测算Sales查询明细合计
     * @param param
     * @return
     */
    VatSalesDetailResult listSum(@Param("param") VatSalesDetailParam param);

    /**
     * 生成VAT明细
     * @param param
     * @return
     */
    void generateVatDetail(@Param("param") VatSalesDetailParam param);

    List<VatSalesDetailResult> notTaxRate(@Param("param") VatSalesDetailParam param);
}
