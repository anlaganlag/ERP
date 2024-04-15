package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 产品线
 * </p>
 *
 * @author lsy
 * @since 2022-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("PRODUCT_LINE")
public class ProductLine2Param extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 产品线Id */
    @TableId(value = "产品线Id")
    private Integer id;

    @ApiModelProperty("运营大类")
    private String productType;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("Team")
    private String team;

    @ApiModelProperty("运营人员工号")
    private String operator;

    @ApiModelProperty("运营人员姓名")
    private String operatorName;

    @ApiModelProperty("Team主管工号")
    private  String teamSupervise;

    @ApiModelProperty("Team主管姓名")
    private  String teamSuperviseName;

    @ApiModelProperty("事业部经理工号")
    private  String  deptMgr;

    @ApiModelProperty("事业部经理姓名")
    private  String  deptMgrName;
}