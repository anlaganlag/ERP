package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ReceivableDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReceivableDetailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReceivableDetailResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 应收明细 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IReceivableDetailService extends IService<ReceivableDetail> {

    /**
     * 插入应收明细主表
     * @param param
     */
    void generateReceivable(ReceivableDetail param);

    List<ReceivableDetailResult> queryListPage(ReceivableDetail param);

    List<ReceivableDetail> verifyList(ReceivableDetailParam param);

    ResponseData verify(ReceivableDetail param);

    /**
     * 刷取应收明细金额
     * @param detailParam
     * @throws Exception
     */
    void refresh(ReceivableDetail detailParam) throws Exception;

    List<ReceivableDetailResult> list(ReceivableDetailParam param);

}
