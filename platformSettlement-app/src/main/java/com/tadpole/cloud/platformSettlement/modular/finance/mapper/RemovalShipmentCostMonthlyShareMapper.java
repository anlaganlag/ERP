package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.RemovalShipmentCostMonthlyShare;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.RemovalShipmentCostMonthlyShareParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.RemovalShipmentCostMonthlyShareResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.RemovalShipmentTotalCost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 销毁移除成本月分摊表 Mapper 接口
* </p>
*
* @author ty
* @since 2022-05-19
*/
public interface RemovalShipmentCostMonthlyShareMapper extends BaseMapper<RemovalShipmentCostMonthlyShare> {

    /**
     * 销毁移除成本月分摊表列表
     * @param param
     * @return
     */
    Page<RemovalShipmentCostMonthlyShareResult> queryShipmentCostMonthlyShare(@Param("page") Page page, @Param("param") RemovalShipmentCostMonthlyShareParam param);

    /**
     * 销毁移除成本月分摊汇总统计
     * @param param
     * @return
     */
    RemovalShipmentTotalCost totalCost(@Param("param") RemovalShipmentCostMonthlyShareParam param);

    /**
     * 销毁移除批量确认
     * @param param
     * @param userName
     */
    void batchConfirm(@Param("param") List<Long> param, @Param("userName") String userName);

    /**
     * 销毁移除批量销毁
     * @param param
     * @param userName
     */
    void batchDestroy(@Param("param") List<Long> param, @Param("userName") String userName);

    /**
     * 生成站外费用分摊汇总销毁成本
     * @param fiscalPeriod
     * @param userName
     */
    void generateRemovalShipment(@Param("fiscalPeriod") String fiscalPeriod, @Param("userName") String userName);

    /**
     * 执行更新销毁移除成本月份摊
     * @param fiscalPeriod
     * @param userName
     */
    void batchUpdateRemovalShipment(@Param("fiscalPeriod") String fiscalPeriod, @Param("userName") String userName);

    /**
     * 销毁移除成本月分摊刷Listing
     * @param param
     * @return
     */
    List<RemovalShipmentCostMonthlyShare> refreshListing(@Param("param") RemovalShipmentCostMonthlyShareParam param);

    /**
     * 销毁移除成本月分摊刷单位成本
     * @param param
     * @return
     */
    List<RemovalShipmentCostMonthlyShare> refreshUnitPrice(@Param("param") RemovalShipmentCostMonthlyShareParam param);

    /**
     * 是否存在相同维度销毁移除月分摊未确认的数据
     * @param param
     * @return
     */
    List<String> hasNotConfirm(@Param("param") RemovalShipmentCostMonthlyShareParam param);
}
