package com.tadpole.cloud.product.modular.product.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * <p>
 * 产品线管理 入参类
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
@TableName("RD_PL_MANAGE")
public class RdPlManageParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("")
    private BigDecimal id;

    /** 系统信息-产品线编号 */
    @ApiModelProperty("系统信息-产品线编号")
    private String sysPlCode;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-员工编号 */
    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;

    /** 系统信息-状态 值域{"正常","禁用"} */
    @ApiModelProperty("系统信息-状态 值域{正常,禁用}")
    private String sysTaLevel;

    /** 产品线设定-产品线名称 */
    @ApiModelProperty("产品线设定-产品线名称")
    private String sysPlName;

    /** 产品线设定-产品经理编码 */
    @ApiModelProperty("产品线设定-产品经理编码")
    private String sysPmPerCode;

    /** 产品线设定-产品经理名称 */
    @ApiModelProperty("产品线设定-产品经理名称")
    private String sysPmPerName;

    /** 产品线设定-部门审核人编号 */
    @ApiModelProperty("产品线设定-部门审核人编号")
    private String sysDeptExamPerCode;

    /** 产品线设定-部门审核人名称 */
    @ApiModelProperty("产品线设定-部门审核人名称")
    private String sysDeptExamPerName;

    /** 产品线设定-审批人编号 */
    @ApiModelProperty("产品线设定-审批人编号")
    private String sysApprPerCode;

    /** 产品线设定-审批人名称 */
    @ApiModelProperty("产品线设定-审批人名称")
    private String sysApprPerName;

    /** 系统信息-关闭时间 */
    @ApiModelProperty("系统信息-关闭时间")
    private Date sysPlCloseDate;

    private List<String> sysPlNameList;

    private List<String> sysPmPerCodeList;

    private List<String> sysDeptExamPerCodeList;

    private List<String> sysApprPerCodeList;

    private List<String>  sysPlCodeList;

    /** 系统信息-SPU */
    @ApiModelProperty("系统信息-SPU")
    private String sysSpu;

    /** 系统信息-状态 */
    @ApiModelProperty("系统信息-状态")
    private String sysStatus;

    /** 同款设定-产品名称 */
    @ApiModelProperty("同款设定-产品名称")
    private String sysProName;

    /** 同款设定-款式 */
    @ApiModelProperty("同款设定-款式")
    private String sysStyle;

    /** 同款设定-主材料 */
    @ApiModelProperty("同款设定-主材料")
    private String sysMainMaterial;

    /** 同款设定-适用品牌或对象 */
    @ApiModelProperty("同款设定-适用品牌或对象")
    private String sysBrand;

    /** 同款设定-型号 */
    @ApiModelProperty("同款设定-型号")
    private String sysModel;

    /** 系统信息-关闭时间 */
    @ApiModelProperty("系统信息-关闭日期")
    private Date sysCloseDate;

    private List<String> sysCDateList;

    private List<String> sysCLoseDateList;

    private List<String> sysProNameList;

    private List<String> sysSpuList;

    private List<String> sysStyleList;

    private List<String> sysMainMaterialList;

    private List<String> sysBrandList;

    private List<String> sysModelList;


}
