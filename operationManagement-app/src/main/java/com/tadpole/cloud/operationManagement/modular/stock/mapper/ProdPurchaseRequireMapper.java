package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdPurchaseRequire;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProdPurchaseRequireParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.ProdPurchaseRequireExtentsResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 新品采购申请 Mapper 接口
    * </p>
*
* @author gal
* @since 2022-10-20
*/
public interface ProdPurchaseRequireMapper extends BaseMapper<ProdPurchaseRequire> {



    List<ProdPurchaseRequireExtentsResult> queryPurchaseRequireExtentsOpr(@Param("paramCondition") ProdPurchaseRequireParam param);
    List<ProdPurchaseRequireExtentsResult> purchasePmcCheck(@Param("paramCondition") ProdPurchaseRequireParam param);

    IPage<ProdPurchaseRequireExtentsResult> applyListPage(@Param("page")Page pageContext, @Param("reqVO") ProdPurchaseRequireParam reqVO);


    void updateOprApplyQty3( List<String> idList);


}
