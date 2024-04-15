package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResultV3;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 备货申请信息 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
public interface IOperApplyInfoServiceV3 extends IService<OperApplyInfo> {

    PageResult<OperApplyInfoResult> queryPage(OperApplyInfoReqVO reqVO);


    ResponseData update(OperApplyInfo applyInfo);

    ResponseData commitBatch(List<OperApplyReqVO> reqParamList);

    ResponseData operImport(List<OperApplyInfoExcelParam> dataList);

    List<OperApplyInfo> operExport(Integer stockStatus);

    PageResult<OperApplyInfoResult> recordList(OperApplyInfoReqVO reqVO);

    List<OperApplyInfoExcelParam> operExportFast(int i, boolean note);
    List<OperApplyInfoExcelExportParam> operExportFast2(int i, boolean note);



    ResponseData overTimeAction(StockApprovaltimeParameterResult sysApprovaltimeParameterResult);

    @DataSource(name = "stocking")
    Boolean checkOverTime(StockApprovaltimeParameterResult parameter);

    ResponseData updateBatch(List<OperApplyInfo> applyInfos);

    ResponseData batchFillSalesDemand(OperApplyInfoReqVO reqVO);

    ResponseData fillSalesStockDays(OperApplyInfoReqVO reqVO);

    List<String> getAllComintByPlatformTeamMaterialCode(List<String> materialCodeTeamList);

    List<OperApplyInfoResultV3> queryList(OperApplyInfoReqV3 reqVO);

    ResponseData batchNoStocking(List<String> idArrayList);

    @DataSource(name = "stocking")
    List<BigDecimal> getDetailIdList(List<String> idArrayList);

    ResponseData batchUseAdvice(List<String> idArrayList);

    ResponseData mergeCommitBatch(List<String> idArrayList);

    ResponseData mergeCommitBatchFast(List<String> idArrayList,List<BigDecimal> idList);

    ResponseData mergeDetail(List<BigDecimal> idList);

    ResponseData updateDetailBatch(List<OperApplyInfo> applyInfoList);

    ResponseData commitDetailBatch(List<OperApplyInfo> applyInfoList);

    ResponseData applyQtyCountMat(String materialCode);
}
