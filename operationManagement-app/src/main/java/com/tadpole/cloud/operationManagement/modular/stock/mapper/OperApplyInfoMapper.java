package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelExportParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoExcelParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResultV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyInfoReqVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* <p>
    * 备货申请信息 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@Repository
public interface OperApplyInfoMapper extends BaseMapper<OperApplyInfo> {

    IPage<OperApplyInfoResult> queryPage(@Param("page") Page page, @Param("reqVO") OperApplyInfoReqVO reqVO);

    List<OperApplyInfo> queryByCondition(@Param("reqVO") OperApplyInfoReqVO reqVO);

    List<OperApplyInfo> queryByConditions( @Param("reqVO") OperApplyInfoReqVO reqVO);

    void commitBatch(List<OperApplyReqVO> reqParamList);

    void updateStockAreaList(Set<String> platformMaterialCodeTeamAreaSet);

    OperApplyInfo selectCommit(String platformMaterialCodeTeamArea);

    List<OperApplyInfo> batchSelectCommit(List<String> platformMaterialCodeTeamArea);

    void updateCommitStatus(String platformMaterialCodeTeamArea, String id);

    void batchUpdateCommitStatus(List<Map<String,String>> upMap, List<String> keyList);

    List<OperApplyInfo> operExport(@Param("operator") String operator,@Param("stockStatu") Integer stockStatu);

    List<SpecialApplyInfo> specialExport(@Param("paramCondition") SpecialApplyInfoReqVO paramCondition);

    IPage<OperApplyInfoResult> recordList(@Param("page") Page page, @Param("reqVO") OperApplyInfoReqVO reqVO);

    List<OperApplyInfoExcelParam> operExportFast(String operator, int stockStatu);
    List<OperApplyInfoExcelExportParam> operExportFast2(String operator, int stockStatu);
    void importCommitBatch(List<OperApplyInfoExcelParam> reqParamList);

    List<String> getAllComintByPlatformTeamMaterialCode(List<String> platformMaterialCodeTeamList);

    List<OperApplyInfoResult> queryList( @Param("reqVO") OperApplyInfoReqVO reqVO);
    List<OperApplyInfoResultV3> queryListV3(@Param("reqVO") OperApplyInfoReqV3 reqVO);
    void updateStockAreaListV3(List<String> platformMaterialCodeTeamAreaSet);

    List<OperApplyInfoResultV3> applyQtyCountMat( @Param("materialCode") String materialCode);

    void updateBatchOperApplyInfo(List<OperApplyInfo> updateOperInofList);

    void batchUseAdvice(List<BigDecimal> idList,String userName,String userNo);
}
