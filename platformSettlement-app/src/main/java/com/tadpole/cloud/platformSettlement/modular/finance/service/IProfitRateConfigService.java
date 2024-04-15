package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ProfitRateConfig;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ProfitRateConfigParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ProfitRateConfigResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 *  利润率参数管理服务类
 * </p>
 *
 * @author ty
 * @since 2022-05-27
 */
public interface IProfitRateConfigService extends IService<ProfitRateConfig> {

    /**
     * 利润率参数管理
     */
    ResponseData queryPage(ProfitRateConfigParam param);

    /**
     * 利润率参数管理导出
     * @param param
     * @return
     */
    List<ProfitRateConfigResult> export(ProfitRateConfigParam param);

    /**
     * 销毁移除成本月分摊表列表导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file, List<String> departmentList, List<String> teamList);

    /**
     * 历史利润率查询列表
     */
    ResponseData queryHistoryPage(ProfitRateConfigParam param);

    /**
     * 事业部下拉
     */
    ResponseData departmentSelect();

    /**
     * Team下拉
     */
    ResponseData teamSelect();

    /**
     * 运营大类下拉
     */
    ResponseData productTypeSelect();
}
