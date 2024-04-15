package com.tadpole.cloud.product.api.productlanding.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 预提案 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
//@TableName("RD_PRE_PROPOSAL")
public class RdProductDefineBookExtentResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("产品定义书-文档编号")
    private String sysOldProDefineNum;

    @ApiModelProperty("产品定义书-文档版本")
    private String sysOldProDefineVersion;

    @ApiModelProperty("产品定义书-文档类型")
    private String sysOldProDefineType;

    @ApiModelProperty("产品定义书-定版日期")
    private Date sysFinalDate;

    @ApiModelProperty("预案指派-产品经理编号")
    private String sysPmPerCode;

    @ApiModelProperty("预案指派-产品经理姓名")
    private String sysPmPerName;

    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    @ApiModelProperty("单据联系-产品线名称")
    private String sysPlName;

    @ApiModelProperty("预案公共-SPU")
    private String sysSpu;

    @ApiModelProperty("预案公共-产品名称")
    private String sysProName;

    @ApiModelProperty("预案公共-款式")
    private String sysStyle;

    @ApiModelProperty("预案公共-适用品牌或对象")
    private String sysBrand;

    @ApiModelProperty("预案公共-主材料")
    private String sysMainMaterial;

    @ApiModelProperty("预案公共-型号")
    private String sysModel;

}
