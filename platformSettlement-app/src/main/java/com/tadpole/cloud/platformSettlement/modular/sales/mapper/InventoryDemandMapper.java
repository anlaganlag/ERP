package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryDemand;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryDemandParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryDemandResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 存货需求 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
public interface InventoryDemandMapper extends BaseMapper<InventoryDemand> {
  /**
   * 存货需求列表
   */
  List<InventoryDemandResult> list(@Param("paramCondition") InventoryDemandParam paramCondition);

  List<StockMonitor> stockMonitorHead(@Param("dept") String dept);

  /**
   * 存货需求合计
   */
  InventoryDemandResult listSum(@Param("paramCondition") InventoryDemandParam paramCondition);

  @Select("SELECT DISTINCT department FROM  INVENTORY_DEMAND WHERE YEAR = #{year} and VERSION = #{version}")
  List<String> distinctDept(String year,String version);

  /**
   * 获取特定年份最大版本号
   */
  @Select("SELECT TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) FROM  INVENTORY_DEMAND  WHERE YEAR = #{year}")
  String selectMaxVersionByYear(String year);

  /**
   * 获取一条指定年份及版本记录的确认状态(1已确认,0未确认,null无记录)
   */
  @Select("SELECT CONFIRM_STATUS FROM  INVENTORY_DEMAND  WHERE YEAR = #{year} AND VERSION=#{version} AND ROWNUM =1 ")
  String selectOneStatusByYearVersion(String year,String version);

  /**
   * 删除特定年份及版本的所有记录
   */
  @Delete("Delete  FROM  INVENTORY_DEMAND  WHERE YEAR = #{year} AND VERSION=#{version}")
  void deleteByByYearVersion(String year,String version);

  StockMonitor stockMonitorHeadDept(@Param("dept") String dept);
}
