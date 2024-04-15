package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PmcVerifInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPmcVerifK3;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockPmcVerifK3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockPmcVerifK3Result;

import java.util.List;

/**
 * <p>
 * PMC审核调用k3记录信息 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-09-07
 */
public interface IStockPmcVerifK3Service extends IService<StockPmcVerifK3> {

    StockPmcVerifK3 callK3(List<PmcVerifInfo> pmcVerifInfo, boolean needSave);
    public PageResult<StockPmcVerifK3Result> queryPage(StockPmcVerifK3Param reqVO);

    ResponseData edit(StockPmcVerifK3Param requestParm);

    List<StockPmcVerifK3Result> export(StockPmcVerifK3Param reqVO);

}
