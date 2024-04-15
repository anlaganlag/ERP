package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
@ExcelIgnoreUnannotated
@TableName("PRODUCT_LINE")
public class ProductLine2Result implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 产品线Id */
   @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** 区域 */
    @ApiModelProperty("AREA")
    private String area;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 运营人员工号 */
    @ApiModelProperty("OPERATOR")
    private String operator;

    /** 运营人员名字 */
    @ApiModelProperty("OPERATOR_NAME")
    private String operatorName;

    /** Team主管工号 */
    @ApiModelProperty("TEAM_SUPERVISE")
    private String teamSupervise;

    /** Team主管名字 */
    @ApiModelProperty("TEAM_SUPERVISE_NAME")
    private String teamSuperviseName;

    /** 部门经理工号 */
    @ApiModelProperty("DEPT_MGR")
    private String deptMgr;

    /** 部门经理名字 */
    @ApiModelProperty("DEPT_MGR_NAME")
    private String deptMgrName;

    /** 创建日期 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 更新日期 */
    @ApiModelProperty("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

}