package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfoV3;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SysBizConfig;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3ComitDto;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoV3Result;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 特殊备货申请V3 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-08-31
 */
public interface ISpecialApplyInfoV3Service extends IService<SpecialApplyInfoV3> {

    ResponseData queryListPage(SpecialApplyInfoV3Param param);

    ResponseData queryList(SpecialApplyInfoV3Param param);

    ResponseData add(SpecialApplyInfoV3Param param, Material material, String controllerName);

    ResponseData upload(List<SpecialApplyInfoV3> entitys, String controllerName);

    ResponseData edit(SpecialApplyInfoV3Param param, String controllerName);

    ResponseData delete(List<String> ids, String controllerName);

    ResponseData batchSubmit(SpecialApplyInfoV3ComitDto param);

    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    void submit(String mergeField, List<SpecialApplyInfoV3> infoList) throws Exception;

    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    boolean updateBatchByIdList(List<SpecialApplyInfoV3> infoList);

    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    void saveTeamverif(TeamVerif teamVerif);



    @DataSource(name = "stocking")
        //    @Transactional(rollbackFor = Exception.class)
    ResponseData flashSpecialApplyData(SysBizConfig sysBizConfig);

    ResponseData flashApplyData(PurchaseOrders purchaseOrders);


    List<SpecialApplyInfoV3Result> uploadValidateData(List<String> mergeFields);

}
