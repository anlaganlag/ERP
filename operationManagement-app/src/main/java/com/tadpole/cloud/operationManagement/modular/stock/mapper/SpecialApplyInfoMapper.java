package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyReqVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
    * 特殊备货申请信息 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-24
*/
@Repository
public interface SpecialApplyInfoMapper extends BaseMapper<SpecialApplyInfo> {

    IPage<SpecialApplyInfoResult> queryPage(@Param("page") Page page, @Param("reqVO") SpecialApplyInfoReqVO reqVO);
    List<SpecialApplyInfoResult> specialExport( @Param("reqVO") SpecialApplyInfoReqVO reqVO);




    void commitUpdateBatch(List<SpecialApplyReqVO> reqParamList);


    SpecialApplyInfo selectAreaCommit(String platformMaterialCodeTeamArea,List<SpecialApplyReqVO> reqParamList);
    List<PurchaseOrdersResult> selectMatCommit(String platformMaterialCodeTeam, List<TeamVerif>  reqParamList);


    void updateTeamVerifNo(String platformMaterialCodeTeamArea, String teamVerifNo,String userAccount,String userName, List<SpecialApplyReqVO> reqParamList);
    void updatePurchaseApplyNo(String platformMaterialCodeTeam, String purchaseApplyNo,String billType ,List<TeamVerif>  reqParamList);



    IPage<OperApplyInfoResult> recordSpecialList(@Param("page") Page page, @Param("reqVO") List<String> reqVO);








}
