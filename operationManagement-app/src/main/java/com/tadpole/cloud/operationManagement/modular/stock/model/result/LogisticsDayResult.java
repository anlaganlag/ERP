package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
* <p>
    * 物流天数维护
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LOGISTICS_DAY")
public class LogisticsDayResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 区域 */
    @ApiModelProperty("AREA")
    @ExcelProperty("区域")
    private String area;

    /** 国家 */
    @ApiModelProperty("COUNTRY")
    private String country;

    /** 物流方式 */
    @ApiModelProperty("LOGISTICS_MODE")
    @ExcelProperty("物流方式")
    private String logisticsMode;

    /** 运输天数 */
    @ApiModelProperty("LOGISTICS_DAYS")
    @ExcelProperty("运输天数")
    private String logisticsDays;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 更新人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

 @Override
 public boolean equals(Object o) {
  if (this == o) {

   return true;
  }
  if (o == null || getClass() != o.getClass()) {

   return false;
  }
  LogisticsDayResult that = (LogisticsDayResult) o;
  return getArea().equals(that.getArea()) && getLogisticsMode().equals(that.getLogisticsMode());
 }

 @Override
 public int hashCode() {
  return Objects.hash(getArea(), getLogisticsMode());
 }
}