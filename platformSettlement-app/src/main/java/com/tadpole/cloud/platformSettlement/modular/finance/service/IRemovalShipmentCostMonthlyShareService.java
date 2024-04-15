package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.RemovalShipmentCostMonthlyShare;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.RemovalShipmentCostMonthlyShareParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.RemovalShipmentCostMonthlyShareResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 销毁移除成本月分摊表 服务类
 * </p>
 *
 * @author ty
 * @since 2022-05-19
 */
public interface IRemovalShipmentCostMonthlyShareService extends IService<RemovalShipmentCostMonthlyShare> {

    /**
     * 销毁移除成本月分摊表
     */
    ResponseData queryPage(RemovalShipmentCostMonthlyShareParam param);

    /**
     * 销毁移除成本月分摊表列表导出
     * @param param
     * @return
     */
    List<RemovalShipmentCostMonthlyShareResult> export(RemovalShipmentCostMonthlyShareParam param);

    /**
     * 销毁移除成本月分摊汇总统计
     */
    ResponseData totalCost(RemovalShipmentCostMonthlyShareParam param);

    /**
     * 销毁移除成本月分摊表列表导入
     * @param file
     * @param departmentList
     * @param teamList
     * @return
     */
    ResponseData importExcel(MultipartFile file, List<String> departmentList, List<String> teamList);

    /**
     * 销毁移除批量确认
     * @param param
     * @return
     */
    ResponseData batchConfirm(List<Long> param);

    /**
     * 生成站外费用分摊汇总销毁成本
     * @param fiscalPeriod 会计期间
     * @return
     */
    ResponseData generateRemovalShipment(String fiscalPeriod);

    /**
     * 销毁移除编辑接口
     * @param param
     * @return
     */
    ResponseData updateRemovalShipment(RemovalShipmentCostMonthlyShare param);

    /**
     * 销毁移除批量作废
     */
    ResponseData destroyRemovalShipment(List<Long> param);

    /**
     * 生成销毁移除成本月分摊数据
     * @param removalShipmentCostMonthlyShareList
     */
    void generateRemovalShipmentMonShare(List<RemovalShipmentCostMonthlyShare> removalShipmentCostMonthlyShareList);

    /**
     * 根据维度判断销毁移除成本是否存在未确认数据
     * @param param
     * @return
     */
    Boolean hasNotConfirm(RemovalShipmentCostMonthlyShareParam param);
}
