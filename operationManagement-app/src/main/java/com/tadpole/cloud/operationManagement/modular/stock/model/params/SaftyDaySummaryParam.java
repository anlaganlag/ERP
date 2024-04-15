package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    * 安全天数概要表
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
@TableName("STOCK_SAFTY_DAY_SUMMARY")
public class SaftyDaySummaryParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Integer id;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platformName;

    /** 区域 */
    @ApiModelProperty("区域")
    private String area;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private String productName;

    /** 款式 */
    @ApiModelProperty("款式")
    private String style;

    /** 型号 */
    @ApiModelProperty("型号")
    private String model;

    /** 优先级 */
    @ApiModelProperty("优先级")
    private int priority;

    /** 是否启用（0：禁用,1：启用）默认：1 */
    @ApiModelProperty("是否启用（0：禁用,1：启用）默认：1")
    private int isEnable;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @ApiModelProperty("是否启用（0：未删除,1：已删除）默认：0")
    private int isDelete;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("修改人")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

}