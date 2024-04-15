package com.tadpole.cloud.platformSettlement.modular.vat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseTaxList;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxListParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxListResult;
import java.util.List;

/**
 * <p>
 * 基础信息-税号列表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface IBaseTaxListService extends IService<BaseTaxList> {
    List<BaseTaxListResult> exportExcel(BaseTaxListParam param);

    Page<BaseTaxListResult> queryListPage(BaseTaxListParam param);

    List<BaseTaxList> queryEbmsData();

    Boolean RefreshData(List<BaseTaxList> ebmsData);
}
