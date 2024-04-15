package com.tadpole.cloud.operationManagement.modular.stock.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("PRODUCT_LINE2")
@ExcelIgnoreUnannotated
public class ProductLine2 implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 产品线Id */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /** 运营大类 */
    @ExcelProperty("运营大类")
    @Excel(name = "运营大类")
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 平台 */
    @ExcelProperty("平台")
    @Excel(name = "平台")
    private String platform;

    /** 区域 */
    @ExcelProperty("区域")
    @Excel(name = "区域")
    private String area;

    /** 事业部 */
    @ExcelProperty("事业部")
    @Excel(name = "事业部")
    private String department;

    /** Team */
    @ExcelProperty("Team")
    @Excel(name = "Team")
    private String team;

    /** 运营人员工号 */
    @ExcelProperty("运营人员工号")
    @Excel(name = "运营人员工号")
    private String operator;

    /** 运营人员名字 */
    @ExcelProperty("运营人员姓名")
    @Excel(name = "运营人员姓名")
    @TableField("OPERATOR_NAME")
    private String operatorName;

    /** Team主管工号 */
    @ExcelProperty("Team主管工号")
    @Excel(name = "Team主管工号")
    @TableField("TEAM_SUPERVISE")
    private String teamSupervise;

    /** Team主管名字 */
    @ExcelProperty("Team主管姓名")
    @Excel(name = "Team主管姓名")
    @TableField("TEAM_SUPERVISE_NAME")
    private String teamSuperviseName;

    /** 部门经理工号 */
    @ExcelProperty("事业部经理工号")
    @Excel(name = "事业部经理工号")
    @TableField("DEPT_MGR")
    private String deptMgr;

    /** 部门经理名字 */
    @ExcelProperty("事业部经理姓名")
    @Excel(name = "事业部经理姓名")
    @TableField("DEPT_MGR_NAME")
    private String  deptMgrName;

    /** 创建日期 */
    @TableField("CREATE_AT")
    private Date createAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 更新日期 */
    @TableField("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

}