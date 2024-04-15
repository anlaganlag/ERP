package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* 销毁移除费用统计
* </p>
*
* @author S20190161
* @since 2022-10-18
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TOTAL_DESTROY_FEE")
public class TotalDestroyFeeResult implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;

 @TableId(value = "ID", type = IdType.AUTO)
 private Long id;

 @ExcelProperty("结算ID")
 private BigDecimal settlementId;

 @ExcelProperty("账号")
 private String sysShopsName;

 @ExcelProperty("站点")
 @ApiModelProperty("")
 private String sysSite;

 @ExcelProperty("orderId")
 @ApiModelProperty("")
 private String orderId;

 @ExcelProperty("SKU")
 @ApiModelProperty("")
 private String sku;

 @ExcelProperty("币别")
 @ApiModelProperty("")
 private String currency;

 @ExcelProperty("销毁费")
 @ApiModelProperty("销毁费")
 private BigDecimal disposalFee;

 @ExcelProperty("移除费")
 @ApiModelProperty("移除费")
 private BigDecimal returnFee;

 @ExcelProperty("部门")
 @ApiModelProperty("部门")
 private String department;

 @ExcelProperty("Team")
 @ApiModelProperty("Team")
 private String team;

 @ExcelProperty("物料编码")
 @ApiModelProperty("物料编码")
 private String materialCode;

 @ExcelProperty("分类")
 @ApiModelProperty("分类")
 private String category;

 @ExcelProperty("运营大类")
 @ApiModelProperty("运营大类")
 private String productType;

 @ExcelProperty("会计期间")
 @ApiModelProperty("会计期间")
 private Date duration;

 @ExcelProperty("状态：1.未确认 2.手动确认 3.自动确认")
 @ApiModelProperty("状态：1.未确认 2.手动确认 3.自动确认")
 private Integer status;

 @ExcelProperty("来源：1.系统生成 2.手动插入")
 @ApiModelProperty("来源：1.系统生成 2.手动插入")
 private Integer source;

 @ExcelProperty("类型：1.结算报告汇总 2.移除报告明细数据")
 @ApiModelProperty("类型：1.结算报告汇总 2.移除报告明细数据")
 private Integer type;

 @ApiModelProperty("")
 private Date createTime;

 private Integer lev;

 private String fnsku;

 private String normal;

 @ExcelProperty("结算期间")
 private String settlePeriod;
}
