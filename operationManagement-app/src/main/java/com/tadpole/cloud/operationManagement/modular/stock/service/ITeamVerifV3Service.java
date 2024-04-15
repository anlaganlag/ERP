package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TeamVerifyExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResult;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Team审核 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
public interface ITeamVerifV3Service extends IService<TeamVerif> {



    @DataSource(name = "stocking")
    List<TeamVerifResult> queryList(OperApplyInfoReqV3 reqVO);

    ResponseData commitBatch(List<OperApplyReqVO> reqParamList);

    ResponseData getByPurchaseNo(String purchaseNo);

    List<TeamVerifResult> exportExcel(OperApplyInfoReqV3 param);

    ResponseData overTimeAction(StockApprovaltimeParameterResult parameterResult);

    List<TeamVerifyExcelExportParam> teamVerifyExport(String teamStockStatusWait);

    @DataSource(name = "stocking")
     boolean  saveTeamverif(TeamVerif teamVerif) ;

    ResponseData batchNoStocking(List<String> idArrayList);

    @DataSource(name = "stocking")
    List<BigDecimal> getDetailIdList(List<String> idArrayList);

    ResponseData mergeCommitBatchFast(List<String> idArrayList,List<BigDecimal> idList);

    ResponseData mergeDetail(List<BigDecimal> idList);

    ResponseData updateDetailBatch(List<TeamVerif> teamVerifList);

    ResponseData commitDetailBatch(List<TeamVerif> teamVerifList);

    ResponseData applyQtyCountMat(String department, String materialCode, Date bizDate);

    ResponseData queryAsinListByTeamNo(List<String> teamNoList);
}
