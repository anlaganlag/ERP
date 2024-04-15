package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TeamVerifyExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResultV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
* <p>
    * Team审核 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
public interface TeamVerifMapper extends BaseMapper<TeamVerif> {

    IPage<TeamVerifResult> queryPage(Page pageContext, OperApplyInfoReqVO reqVO);
    List<TeamVerifyExcelExportParam> teamExport(String verifStatus , String operator);

    void commitBatch(List<OperApplyReqVO> reqParamList);

    void updateStockAreaList(Set<String> platformMaterialCodeTeamSet);

    TeamVerif selectCommit(String platformMaterialCodeTeam);
    List<TeamVerif> selectCommitBatch(List<String> platformMaterialCodeTeamSet);
    List<TeamVerif> selectMergeDetail(List<String> platformMaterialCodeTeamSet);

    void updateCommitStatus(String platformMaterialCodeTeam, String id);

    List<TeamVerifResult> queryList( @Param("reqVO") OperApplyInfoReqV3 reqVO);

    List<TeamVerifResultV3> applyQtyCountMat(String department, String materialCode, Date bizDate);

    void updateStockAreaListV3(List<String> platformMaterialCodeTeamList);

    void updateBatch(List<TeamVerif> teamVerifList);
}
