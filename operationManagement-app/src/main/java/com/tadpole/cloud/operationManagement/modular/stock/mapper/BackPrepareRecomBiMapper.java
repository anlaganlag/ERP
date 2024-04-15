package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.BackPrepareRecomBi;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.BackPrepareRecomBiParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.BackPrepareRecomBiExcel;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.BackPrepareRecomBiResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 每日备货推荐表-BI Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-08-19
*/
@Mapper
public interface BackPrepareRecomBiMapper extends BaseMapper<BackPrepareRecomBi> {

    IPage<BackPrepareRecomBiResult> queryPage(@Param("page")Page pageContext, @Param("reqVO") BackPrepareRecomBiParam reqVO);

    List<BackPrepareRecomBiExcel> exportExcel(@Param("reqVO")BackPrepareRecomBiParam reqVO);

    /**
     * groupFiled  == t.platform|| t.area ||t.department|| t.team|| t.material_code
     * @param groupFiled
     * @return
     */
    TeamVerif creatTeamVerif(String groupFiled);

    PurchaseOrders createPurchaseOrder(String platformDepartTeamMentlcodeBilltype, List<TeamVerif>  teamVerifList);

    void updatePurchaseApplyNo(String platformDepartTeamMentlcodeBilltype, String purchaseApplyNo,String billType ,List<TeamVerif>  reqParamList);

    List<BackPrepareRecomBiExcel> excelQueryPage( @Param("reqVO") BackPrepareRecomBiParam reqVO);
}
