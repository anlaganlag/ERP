package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

 /**
 * 物流供应商;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_provider")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsProvider implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableId (value = "lp_code", type = IdType.ASSIGN_ID)
    @TableField("lp_code")
    private String lpCode ;
 
    /** 物流商名称 */
    @ApiModelProperty(value = "物流商名称")
    @TableField("lp_name")
    private String lpName ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    @TableField("lp_simple_name")
    private String lpSimpleName ;
 
    /** 通讯地址 */
    @ApiModelProperty(value = "通讯地址")
    @TableField("lp_address")
    private String lpAddress ;
 
    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    @TableField("lp_uni_soc_cre_code")
    private String lpUniSocCreCode ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** 启用禁用状态;A:启用，B:禁用 */
    @ApiModelProperty(value = "启用禁用状态")
    @TableField("forbid_status")
    private String forbidStatus ;

    /** 数据来源 */
    @ApiModelProperty(value = "数据来源")
    @TableField("data_source")
    private String dataSource ;

    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @TableField("sys_upd_user")
    private String sysUpdUser ;

    /** 物流单链接模板 */
    @ApiModelProperty(value = "物流单链接模板")
    @TableField("log_list_link_temp")
    private String logListLinkTemp ;

}