package com.tadpole.cloud.operationManagement.api.brand.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* 品牌商标表
* </p>
*
* @author S20190161
* @since 2023-10-19
*/
@Data
@TableName("TB_BRAND_TRADEMARK")
@ExcelIgnoreUnannotated
public class TbBrandTrademark implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统主键(sp+五位流水号) */
   @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 商标名称 */
    @TableField("TRADE_NAME")
    private String tradeName;

    /** 商标类型：0.文字商标;1.图形商标; */
    @TableField("type")
    private Long type;

    /** 是否注册 */
    @TableField("IS_REGISTER")
    private Integer isRegister;

    /** 备注说明 */
    @TableField("REMARK")
    private String remark;

    /** 提案人编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

 @ApiModelProperty("提案人姓名")
 private String createName;

 @ApiModelProperty("提案时间")
 private Date createTime;
 @ApiModelProperty("修改人姓名")
 private String updateName;
 @ApiModelProperty("修改时间")
 private Date updateTime;


}
