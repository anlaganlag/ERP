package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatDetail;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatDetailResult;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatShopResult;
import java.util.List;

/**
 * <p>
 * 税金测算VAT明细 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface IVatDetailService extends IService<VatDetail> {

    /**
     * 税金测算VAT明细查询列表接口
     */
    ResponseData queryListPage(VatDetailParam param);

    /**
     * 税金测算VAT明细合计
     * @param param
     * @return
     */
    VatDetailResult getQuantity(VatDetailParam param);

    /**
     * 税金测算VAT明细导出接口
     */
    List<VatDetailResult> exportVatDetail(VatDetailParam param);

    /**
     * 税金测算VAT店铺维度导出接口
     */
    List<VatShopResult> exportVatShop(VatDetailParam param);

    /**
     *生成核对表
     * @param param
     * @return
     */
    ResponseData generateVatCheck(VatDetailParam param);

}
