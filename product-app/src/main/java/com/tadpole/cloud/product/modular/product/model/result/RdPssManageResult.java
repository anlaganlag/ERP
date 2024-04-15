package com.tadpole.cloud.product.modular.product.model.result;

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
 * 产品同款管理 出参类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_PSS_MANAGE")
public class RdPssManageResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("")
    private BigDecimal id;


    @ApiModelProperty("系统信息-SPU")
    @ExcelProperty(value ="SPU")
    private String sysSpu;


    @ApiModelProperty("系统信息-产品线编号")
    @ExcelProperty(value ="产品线编号")
    private String sysPlCode;


    @ApiModelProperty("系统信息-创建时间")
    @ExcelProperty(value ="创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    @ExcelProperty(value ="最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-状态")
    @ExcelProperty(value ="状态")
    private String sysStatus;


    @ApiModelProperty("同款设定-产品名称")
    @ExcelProperty(value ="产品名称")
    private String sysProName;


    @ApiModelProperty("同款设定-款式")
    @ExcelProperty(value ="款式")
    private String sysStyle;


    @ApiModelProperty("同款设定-主材料")
    @ExcelProperty(value ="主材料")
    private String sysMainMaterial;


    @ApiModelProperty("同款设定-适用品牌或对象")
    @ExcelProperty(value ="适用品牌或对象")
    private String sysBrand;


    @ApiModelProperty("同款设定-型号")
    @ExcelProperty(value ="型号")
    private String sysModel;

    /** 系统信息-关闭时间 */
    @ApiModelProperty("系统信息-关闭日期")
    @ExcelProperty(value ="关闭日期")
    private Date sysCloseDate;

    /** 同款设定-当前应用版本 */
    @ApiModelProperty("同款设定-当前应用版本")
    @ExcelProperty(value ="当前应用版本")
    private String sysCurAppVersion;

    /** 同款设定-当前迭代版本 */
    @ApiModelProperty("同款设定-当前迭代版本")
    @ExcelProperty(value ="当前迭代版本")
    private String sysCurIteVersion;

}
