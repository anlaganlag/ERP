package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Account;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FinancialClassification;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialClassificationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialClassificationResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 财务分类表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IFinancialClassificationService extends IService<FinancialClassification> {

    PageResult<FinancialClassificationResult> findPageBySpec(FinancialClassificationParam param);

    List<FinancialClassificationResult> exportFinancialClassificationList(FinancialClassificationParam param);

    void insertBatch(List<FinancialClassification> FinancialClassificationList) ;

    List<Account> queryAccount(Account param);

    List<Account> queryAccountList();

    void add(FinancialClassificationParam param);

    void addDataRange(FinancialClassificationParam param);

    void update(FinancialClassificationParam param);

    void changeStatus(FinancialClassificationParam param);

    void verifySettlement(FinancialClassificationParam param);

    ResponseData importExcel(MultipartFile file,List<Account> accountList);
}
