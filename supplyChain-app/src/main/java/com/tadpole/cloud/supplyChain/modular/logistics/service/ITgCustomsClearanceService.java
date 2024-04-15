package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.api.saihu.entity.SaihuShop;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearance;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceSaveParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsClearanceDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgLogisticsPackingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 清关单 服务类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface ITgCustomsClearanceService extends IService<TgCustomsClearance> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(TgCustomsClearanceParam param);

    /**
     * 删除
     * @param param
     * @return
     */
    ResponseData delete(TgCustomsClearanceParam param);

    /**
     * 查询EBMS出货清单信息
     * @param param
     * @return
     */
    ResponseData selectEbmsLogisticsPacking(TgLogisticsPackingResult param);

    /**
     * 关联新增保存
     * @param param
     * @return
     */
    ResponseData selectSave(TgCustomsClearanceSaveParam param);

    /**
     * 关联编辑保存
     * @param param
     * @return
     */
    ResponseData selectEdit(TgCustomsClearanceSaveParam param);

    /**
     * 获取最新的发票号
     * @return
     */
    String getNowQgdOrder();

    /**
     * 导入
     * @param file
     * @param arrivalCountryCode
     * @param arrivalCountryName
     * @return
     */
    ResponseData importExcel(MultipartFile file, String arrivalCountryCode, String arrivalCountryName);

    /**
     * 导入新增保存
     * @param param
     * @return
     */
    ResponseData importSave(TgCustomsClearanceSaveParam param);

    /**
     * 导入编辑保存
     * @param param
     * @return
     */
    ResponseData importEdit(TgCustomsClearanceSaveParam param);

    /**
     * 生成清关发票（通用）
     * @param param
     * @param response
     */
    void generateCustomsClearance(TgCustomsClearanceParam param, HttpServletResponse response) throws IOException;

    /**
     * 生成清关发票（快递）
     * @param param
     * @param response
     */
    void generateCustomsClearanceKD(TgCustomsClearanceParam param, HttpServletResponse response) throws IOException;

    /**
     * 销售价编辑
     * @param param
     * @return
     */
    ResponseData salePriceEdit(TgCustomsClearanceDetailParam param);

    /**
     * 清关合并预览
     * @param param
     * @return
     */
    ResponseData clearMergePreview(TgCustomsClearanceDetailParam param);

    /**
     * 清关合并拆分
     * @param param
     * @return
     */
    ResponseData clearMergeSplit(TgCustomsClearanceDetailParam param);

    /**
     * 清关合并
     * @param param
     * @return
     */
    ResponseData clearMerge(List<TgCustomsClearanceDetailResult> param);

    /**
     * 查看已合并清关产品
     * @param param
     * @return
     */
    ResponseData selectClearMerge(TgCustomsClearanceDetailParam param);

    /**
     * 特殊清关产品合并列表
     * @param param
     * @return
     */
    ResponseData specialClearMergeList(List<TgCustomsClearanceDetailResult> param);

    /**
     * 定时获取赛狐在线产品
     * @param param
     * @return
     */
    ResponseData getSaihuProduct(SaihuShop param);

    /**
     * 获取赛狐店铺
     * @return
     */
    List<SaihuShop> getSaihuShop();

}
