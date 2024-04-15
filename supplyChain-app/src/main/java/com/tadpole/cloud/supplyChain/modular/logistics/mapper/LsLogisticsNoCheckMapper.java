package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoCheck;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoCheckDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoCheckParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport0Result;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport1Result;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 物流单对账 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
public interface LsLogisticsNoCheckMapper extends BaseMapper<LsLogisticsNoCheck> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<LsLogisticsNoCheckResult> queryPage(@Param("page") Page page, @Param("param") LsLogisticsNoCheckParam param);

    /**
     * 分页查询列表合计
     * @param param
     * @return
     */
    LsLogisticsNoCheckResult queryPageTotal(@Param("param") LsLogisticsNoCheckParam param);

    /**
     * 导出预估
     * @param param
     * @return
     */
    List<LsLogisticsNoCheckExport0Result> export(@Param("param") LsLogisticsNoCheckParam param);

    /**
     * 导出实际
     * @param param
     * @return
     */
    List<LsLogisticsNoCheckExport1Result> exportDetail(@Param("param") LsLogisticsNoCheckParam param);

    /**
     * 获取EBMS物流跟踪表的签收日期
     * @param param
     * @return
     */
    List<LsLogisticsNoCheck> getEbmsSignDate(@Param("param") List<LsLogisticsNoCheck> param);

    /**
     * 定时刷物流跟踪表的签收日期
     * @param param
     * @return
     */
    void refreshSignDate(@Param("param") List<LsLogisticsNoCheck> param);

    /**
     * 批量同步锁定/解锁EBMS
     * @param shipmentNumList
     * @param lockStatus
     * @return
     */
    void batchLockEbms(@Param("shipmentNumList") List<String> shipmentNumList, @Param("lockStatus") String lockStatus);

    /**
     * 物流对账单号校验
     * @param param
     * @return
     */
    List<LsLogisticsNoCheck> validateCheckOrder(@Param("param") LsLogisticsNoCheckDetailParam param);

}
