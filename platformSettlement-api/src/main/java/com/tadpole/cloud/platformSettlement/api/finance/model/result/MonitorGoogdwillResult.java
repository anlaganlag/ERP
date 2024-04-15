package com.tadpole.cloud.platformSettlement.api.finance.model.params;


import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
    * goodwill监控表	
    * </p>
*
* @author S20190161
* @since 2023-07-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("MONITOR_GOOGDWILL")
public class MonitorGoogdwillResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty("")
    private String accountName;


    @ApiModelProperty("")
    private String countryCode;


    @ApiModelProperty("")
    private String settlementId;


    @ApiModelProperty("")
    private String orderId;


    @ApiModelProperty("")
    private String sku;


    @ApiModelProperty("")
    private Date postedDate;


    @ApiModelProperty("")
    private String fiscalPeriod;


    @ApiModelProperty("")
    private BigDecimal amount;


    @ApiModelProperty("订单发货数量")
    private Long shipNum;


    @ApiModelProperty("退货入库数量")
    private Long storageNum;


    @ApiModelProperty("是否需要索赔	0否	1是	若订单发货数量大于退货入库数量，则为“是”，二者相等则为“否”，数量小于退货入库数量，则提示错误")
    private Integer isClaim;


    @ApiModelProperty("审核状态	0 未确认	1 已确认	汇总本表内“账号+站点+会计期”内所有【order-id】+【SKU】的[金额]，与AZ结算对账-收入记录表 中的同条件（账号+站点+会计期，求和多个结算ID）的金额【goodwill】对比，二者相等，则将本表内“账号+站点+会计期”内所有order-id+SKU的状态变更为“已确认”；否则为“未确认”		")
    private Integer status;


    @ApiModelProperty("")
    private String remark;


    @ApiModelProperty("")
    private Date createTime;


    @ApiModelProperty("")
    private Date updateTime;

}
