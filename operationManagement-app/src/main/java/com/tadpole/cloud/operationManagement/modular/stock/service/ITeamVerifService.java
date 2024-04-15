package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TeamVerifyExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResult;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;

import java.util.List;

/**
 * <p>
 * Team审核 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
public interface ITeamVerifService extends IService<TeamVerif> {

    PageResult<TeamVerifResult> queryPage(OperApplyInfoReqVO reqVO);

    ResponseData commitBatch(List<OperApplyReqVO> reqParamList);

    ResponseData getByPurchaseNo(String purchaseNo);

    List<TeamVerifResult> exportExcel(OperApplyInfoReqVO param);

    ResponseData overTimeAction(StockApprovaltimeParameterResult parameterResult);

    List<TeamVerifyExcelExportParam> teamVerifyExport(String teamStockStatusWait);

    @DataSource(name = "stocking")
     boolean  saveTeamverif(TeamVerif teamVerif) ;

}
