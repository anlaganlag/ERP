package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatCheck;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatCheckParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatCheckResult;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * VAT核对表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface IVatCheckService extends IService<VatCheck> {

    /**
     * VAT核对表查询列表接口
     */
    ResponseData queryListPage(VatCheckParam param);

    /**
     * VAT核对表查询明细合计接口
     * @param param
     * @return
     */
    VatCheckResult getQuantity(VatCheckParam param);

    /**
     * VAT核对表导出接口
     */
    List<VatCheckResult> export(VatCheckParam param);

    /**
     * VAT核对表明细编辑
     * @param param
     * @return
     */
    ResponseData edit(VatCheckParam param);

    /**
     * VAT核对表明细导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

}
