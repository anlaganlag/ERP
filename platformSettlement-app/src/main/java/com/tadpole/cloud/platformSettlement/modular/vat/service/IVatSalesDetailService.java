package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatSalesDetail;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatSalesDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatSalesDetailResult;
import java.util.List;

/**
 * <p>
 * 税金测算Sales明细 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface IVatSalesDetailService extends IService<VatSalesDetail> {

    /**
     * 税金测算Sales明细查询列表接口
     */
    PageResult<VatSalesDetailResult> queryListPage(VatSalesDetailParam param);

    /**
     * 税金测算Sales查询明细合计
     * @param param
     * @return
     */
    VatSalesDetailResult getQuantity(VatSalesDetailParam param);

    /**
     * 税金测算Sales明细导出接口
     */
    List<VatSalesDetailResult> export(VatSalesDetailParam param);

    /**
     * 生成VAT明细
     * @param param
     */
    ResponseData generateVatDetail(VatSalesDetailParam param);

}
