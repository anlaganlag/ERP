package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.*;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.BackPrepareRecomBiParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.BackPrepareRecomBiResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;


/**
 * <p>
 * 每日备货推荐表-BI 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-19
 */
public interface IBackPrepareRecomBiService extends IService<BackPrepareRecomBi> {

    PageResult<BackPrepareRecomBiResult> queryPage(BackPrepareRecomBiParam reqVO);


    List<BackPrepareRecomBiResult> export(BackPrepareRecomBiParam reqVO);
    void exportExcel(HttpServletResponse response,BackPrepareRecomBiParam param) throws Exception;

    ResponseData getDepartment(String accountCode);


    ResponseData flashSpecialApplyData();

    @DataSource(name = "stocking")
    TeamVerif creatTeamVerif(SpecialApplyInfoV3 specialApplyInfoV3);

    @DataSource(name = "stocking")
    void saveTeamverif(TeamVerif teamVerif);

    @DataSource(name = "stocking")
    TeamVerif initTeamVerif(SpecialApplyInfoV3 applyInfo);

    @DataSource(name = "basicdata")
    Material getMaterial(String materialCode);

    @DataSource(name = "stocking")
    PurchaseOrders createPurchaseOrder(List<TeamVerif> teamVerifList, List<SpecialApplyInfoV3> infoList);

    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    VerifInfoRecord creatVerifInfo(String reason, BigDecimal qty, String id, LoginUser currentUser, int verifBizType);

    @DataSource(name = "stocking")
    PurchaseOrders initPurchaseOrders(List<TeamVerif> teamVerifList);

    TeamVerif updateTeamVerif(SpecialApplyInfoV3 applyInfo,TeamVerif teamVerif);

    PurchaseOrders updatePurchaseOrder(PurchaseOrders order,List<TeamVerif> teamVerifList);

    @DataSource(name = "stocking")
    @Transactional
    ResponseData updateSysBizConfig(SysBizConfig bizConfig);
}
