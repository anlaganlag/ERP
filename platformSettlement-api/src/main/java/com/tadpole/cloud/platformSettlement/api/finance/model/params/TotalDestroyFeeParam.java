package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
@TableName("TOTAL_DESTROY_FEE")
public class TotalDestroyFeeParam implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;

 @ApiModelProperty("id")
 private Integer id;

 private List<String> sysSites;

 private List<String> sysShopsNames;

 private String sysSite;

 private String sysShopsName;

 private String orderId;

 @ApiModelProperty("会计期间")
 private String startDur;

 @ApiModelProperty("会计期间")
 private String endDur;

 @ApiModelProperty("状态：1.未确认 2.手动确认 3.自动确认")
 private Integer status;

 private BigDecimal settlementId;

 @ApiModelProperty("sku")
 private String sku;

 @ApiModelProperty("币别")
 private String currency;

 @ApiModelProperty("销毁费")
 private BigDecimal disposalFee;

 @ApiModelProperty("移除费")
 private BigDecimal returnFee;


 @ApiModelProperty("部门")
 private String department;


 @ApiModelProperty("")
 private String team;


 @ApiModelProperty("物料")
 private String materialCode;


 @ApiModelProperty("分类")
 private String category;


 @ApiModelProperty("运营大类")
 private String productType;


 @ApiModelProperty("会计期间")
 private Date duration;


 @ApiModelProperty("来源：1.系统生成 2.手动插入")
 private Integer source;

 @ApiModelProperty("类型：1.结算报告汇总 2.移除报告明细数据")
 private Integer type;

 private String fnsku;

 @ApiModelProperty("是否正常：1.正常 0.异常")
 private Integer normal;

 private String settlePeriod;

 private String updateTime;

 private List<String> emailList;


}
