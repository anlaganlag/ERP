package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApply;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplySaveParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgLogisticsPackingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 报关单 服务类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface ITgCustomsApplyService extends IService<TgCustomsApply> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(TgCustomsApplyParam param);

    /**
     * 删除
     * @param param
     * @return
     */
    ResponseData delete(TgCustomsApplyParam param);

    /**
     * 查询EBMS出货清单信息
     * @param param
     * @param tgBoxInfoList
     * @return
     */
    ResponseData selectEbmsLogisticsPacking(TgLogisticsPackingResult param, List<TgBoxInfo> tgBoxInfoList);

    /**
     * 根据出货清单号获取EBMS出货订单ShipmentID数据
     * @param packCodeList
     * @return
     */
    List<TgLogisticsPackingResult> selectLogisticsPackingShipment(List<String> packCodeList);

    /**
     * 出货清单号下拉
     */
    ResponseData packCodeSelect();

    /**
     * 关联新增保存
     * @param param
     * @return
     */
    ResponseData selectSave(TgCustomsApplySaveParam param);

    /**
     * 关联编辑保存
     * @param param
     * @return
     */
    ResponseData selectEdit(TgCustomsApplySaveParam param);

    /**
     * 获取最新的合同协议号
     * @return
     */
    String getNowBgdOrder();

    /**
     * 导入
     * @param files
     * @return
     */
    ResponseData importExcel(MultipartFile[] files);

    /**
     * 导入新增保存
     * @param param
     * @return
     */
    ResponseData importSave(TgCustomsApplySaveParam param);

    /**
     * 导入编辑保存
     * @param param
     * @return
     */
    ResponseData importEdit(TgCustomsApplySaveParam param);

    /**
     * 生成报关单
     * @param param
     * @param response
     * @return
     */
    void generateCustomsApply(TgCustomsApplyParam param, HttpServletResponse response) throws IOException;

}
