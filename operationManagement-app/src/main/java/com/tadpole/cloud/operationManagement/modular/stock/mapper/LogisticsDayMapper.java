package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.LogisticsDay;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsDayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsDayResult;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 企业信息 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2019-10-10
 */
public interface LogisticsDayMapper extends BaseMapper<LogisticsDay> {

    /**
     * 获取分页实体列表
     *
     * @author gal
     * @Date 2021-6-02
     */
    Page<LogisticsDayResult> customPageList(@Param("page") Page page, @Param("paramCondition") LogisticsDayParam paramCondition);
    List<LogisticsDayResult> exportExcel(@Param("paramCondition") LogisticsDayParam paramCondition);



    void updateBatch(@Param("paramCondition") HashSet<LogisticsDay> paramCondition);



    List<String> repeatAreaLog(@Param("paramCondition") LogisticsDayParam paramCondition);


}