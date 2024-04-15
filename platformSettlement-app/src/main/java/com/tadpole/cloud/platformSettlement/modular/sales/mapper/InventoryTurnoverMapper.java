package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryTurnover;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryTurnoverParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryTurnoverResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 库存周转 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-06-01
 */
public interface InventoryTurnoverMapper extends BaseMapper<InventoryTurnover> {

    List<InventoryTurnoverResult> list(@Param("paramCondition") InventoryTurnoverParam paramCondition);

    @Update("UPDATE INVENTORY_TURNOVER SET IDX = IDX + 1 WHERE  IDX >= #{idx}")
    void addIdxByOne(Integer idx);

    @Select("SELECT YEAR,MONTH MONTH FROM INVENTORY_TURNOVER  WHERE  ROWNUM = 1")
    String  confirmedYearMonth();

    Integer  confirmedMonthEmpty(String year,Integer month);

    Boolean batchUpdateNotConfirm(@Param("verifyList") List<InventoryTurnover> verifyList);

    Boolean insertBatch(@Param("verifyList") List<InventoryTurnover> verifyList);

    Boolean updateByConfirmMonth(@Param("paramCondition") InventoryTurnover paramCondition);

    int insertOrUpdateBatch(@Param("entities") List<InventoryTurnover> entities);
}
