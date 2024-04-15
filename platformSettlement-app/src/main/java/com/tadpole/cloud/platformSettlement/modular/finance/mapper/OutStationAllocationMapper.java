package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.OutStationAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OutStationAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OutStationAllocationResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 站外分摊汇总表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface OutStationAllocationMapper extends BaseMapper<OutStationAllocation> {

    /**
     * 站外分摊汇总列表、导出
     * @param page
     * @param param
     * @return
     */
    Page<OutStationAllocationResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") OutStationAllocationParam param);

    /**
     * 更新结算报告
     * @param ss
     */
    void updateToReport(@Param("paramCondition") OutStationAllocation ss);

    /**
     * 根据条件查询数据
     * @param param
     * @return
     */
    List<OutStationAllocationParam> queryConfirm(@Param("paramCondition") OutStationAllocationParam param);

    /**
     * 获取汇总数量
     * @param page
     * @param param
     * @return
     */
    OutStationAllocationResult getQuantity(@Param("page") Page page,@Param("paramCondition") OutStationAllocationParam param);

    /**
     * 批量确认
     * @param param
     */
    void updateToReportBatch(@Param("paramCondition") OutStationAllocationParam param);

    /**
     * 批量更新导入站外费用分摊表
     * @param dataList
     */
    void saveOrUpdateBatchOSA(@Param("dataList") List<OutStationAllocation> dataList);
}
