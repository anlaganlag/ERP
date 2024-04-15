package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 安全天数明细项
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_SAFTY_DAY_ITEM")
public class SaftyDayItemParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** STOCK_SAFTY_DAY_SUMMARY表对应数据id */
    @ApiModelProperty("SUMMARY_ID")
    private BigDecimal summaryId;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 区域 */
    @ApiModelProperty("AREA")
    private String area;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 款式 */
    @ApiModelProperty("STYLE")
    private String style;

    /** 型号 */
    @ApiModelProperty("MODEL")
    private String model;

    /** 优先级 */
    @ApiModelProperty("PRIORITY")
    private int priority;

    /** 是否启用（0：禁用,1：启用）默认：1 */
    @ApiModelProperty("IS_ENABLE")
    private int isEnable;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @ApiModelProperty("IS_DELETE")
    private int isDelete;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}