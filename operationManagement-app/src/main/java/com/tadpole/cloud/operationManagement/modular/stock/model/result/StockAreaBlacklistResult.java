package com.tadpole.cloud.operationManagement.modular.stock.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
    * 区域黑名单
    * </p>
*
* @author lsy
* @since 2022-12-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("STOCK_AREA_BLACKLIST")
public class StockAreaBlacklistResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;


    @ApiModelProperty("平台")
    private String platform;


    @ApiModelProperty("区域")
    private String area;


    @ApiModelProperty("运营大类")
    private String productTypeList;


    @ApiModelProperty("事业部")
    private String department;


    @ApiModelProperty("Team")
    private String team;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("状态:黑名单:0")
    private String status;


    @ApiModelProperty("创建人")
    private Date createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private Date updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
