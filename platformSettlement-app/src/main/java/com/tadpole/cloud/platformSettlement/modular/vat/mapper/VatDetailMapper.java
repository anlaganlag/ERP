package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatDetail;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatDetailResult;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatShopResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 税金测算VAT明细 Mapper 接口
* </p>
*
* @author cyt
* @since 2022-08-06
*/
public interface VatDetailMapper extends BaseMapper<VatDetail> {

    /**
     * 税金测算VAT明细查询列表
     * @param param
     * @return
     */
    Page<VatDetailResult> queryListPage(@Param("page") Page page, @Param("param") VatDetailParam param);

    /**
     * 税金测算VAT明细查询合计
     * @param param
     * @return
     */
    VatDetailResult listSum(@Param("param") VatDetailParam param);

    /**
     * 税金测算VAT明细导出接口
     * @param param
     * @return
     */
    List<VatDetailResult> vatDetailList(@Param("param") VatDetailParam param);

    /**
     * 税金测算VAT店铺维度导出接口
     * @param param
     * @return
     */
    List<VatShopResult> vatShopList(@Param("param") VatDetailParam param);

    /**
     * 生成核对表
     * @param param
     * @return
     */
    void generateVatCheck(@Param("param") VatDetailParam param);

}
