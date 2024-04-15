package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryTurnover;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryTurnoverParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryTurnoverResult;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库存周转 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-06-01
 */
public interface IInventoryTurnoverService extends IService<InventoryTurnover> {

    List<InventoryTurnoverResult> list(InventoryTurnoverParam param, List<String> removeList);

    ResponseData confirmedYearMonth ();

    ResponseData add(InventoryTurnoverParam param) throws ParseException;

    ResponseData upload(InventoryTurnoverParam param, MultipartFile file,List<String> platList,List<String> teamList,List<String> ptList,List<String> deptList,List<String> brandList);

    ResponseData delete(InventoryTurnoverParam param);

    ResponseData edit(InventoryTurnoverParam param);

    ResponseData monthConfirm(InventoryTurnoverParam param);

    Boolean batchUpdateNotConfirm(List<InventoryTurnover> params);

    Boolean insertBatch(List<InventoryTurnover> params) ;

    Boolean updateByConfirmMonth(InventoryTurnover params);

    void  addIdxByOne (Integer idx);

    /**
     * 库存周转下拉接口-平台
     */
    List<Map<String, Object>> getPlatformSelect();

    /**
     * 库存周转下拉接口-事业部
     */
    List<Map<String, Object>> getDepartmentSelect();

    /**
     * 库存周转下拉接口-Team
     */
    List<Map<String, Object>> getTeamSelect();

    /**
     * 库存周转下拉接口-运营大类
     */
    List<Map<String, Object>> getProductTypeSelect();

    /**
     * 库存周转下拉接口-销售品牌
     */
    List<Map<String, Object>> getCompanyBrandSelect();

    /**
     * 库存周转下拉接口-年份
     */
    List<Map<String, Object>> getYearSelect();
}
