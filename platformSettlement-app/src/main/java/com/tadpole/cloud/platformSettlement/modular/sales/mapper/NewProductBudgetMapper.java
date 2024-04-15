package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.tadpole.cloud.platformSettlement.modular.sales.entity.NewProductBudget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.NewProductBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.NewProductBudgetResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 新品预算 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
public interface NewProductBudgetMapper extends BaseMapper<NewProductBudget> {
  /**
   * 新品预算列表
   */
  List<NewProductBudgetResult> list(@Param("paramCondition") NewProductBudgetParam paramCondition);

  /**
   * 新品预算合计
   */
  NewProductBudgetResult listSum(@Param("paramCondition") NewProductBudgetParam paramCondition);

  /**
   * 获取特定年份最大版本号
   */
  @Select("SELECT TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) FROM  NEW_PRODUCT_BUDGET  WHERE YEAR = #{year}")
  String selectMaxVersionByYear(String year);

  /**
   * 获取一条指定年份及版本记录的确认状态(1已确认,0未确认,null无记录)
   */
  @Select("SELECT CONFIRM_STATUS FROM  NEW_PRODUCT_BUDGET  WHERE YEAR = #{year} AND VERSION=#{version} AND ROWNUM =1 ")
  String selectOneStatusByYearVersion(String year,String version);

  /**
   * 删除特定年份及版本的所有记录
   */
  @Delete("Delete  FROM  NEW_PRODUCT_BUDGET  WHERE YEAR = #{year} AND VERSION=#{version}")
  void deleteByByYearVersion(String year,String version);

}
