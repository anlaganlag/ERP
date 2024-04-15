package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
    * K3客户物料对应表
    * </p>
*
* @author lsy
* @since 2022-06-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("K3_CUST_MAT_MAPPING")
public class K3CustMatMappingResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    @ExcelProperty("单据编号")
    private String summaryId;

    /** 名称 */
    @ApiModelProperty("名称")
    @ExcelProperty("名称")
    private String financeCode;


   /** 销售组织，默认值：采购中心 */
   @ApiModelProperty("销售组织")
   @ExcelProperty("销售组织")
   private String saleOrgId;

    /** 客户 */
    @ApiModelProperty("客户")
    @ExcelProperty("客户")
    private String name;



    /** 客户ID */
    @ApiModelProperty("客户ID")
    private String customerId;

    /** 单据状态，默认值：创建 */
    @ApiModelProperty("单据状态，默认值：创建")
    @ExcelProperty("单据状态")
    private String documentStatus;

    /** 是否多选客户，默认值：否 */
    @ApiModelProperty("是否多选客户，默认值：否")
    @ExcelProperty("是否多选客户")
    private String isMultiple;

    /** SKU */
    @ApiModelProperty("SKU")
    @ExcelProperty("SKU")
    private String custMatNo;

    /** 客户物料名称 */
    @ApiModelProperty("客户物料名称")
    @ExcelProperty("客户物料名称")
    private String custMatName;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @ExcelProperty("物料编码")
    private String materialId;

    /** 物料名称，由物料编码字段带出 */
    @ApiModelProperty("物料名称，由物料编码字段带出")
    @ExcelProperty("物料名称")
    private String materialName;

    /** 启用，默认值：是 */
    @ApiModelProperty("启用，默认值：是")
    @ExcelProperty("启用")
    private String effective;

    /** FNSKU，亚马逊平台专用字段，留空 */
    @ApiModelProperty("FNSKU，亚马逊平台专用字段，留空")
    private String fnsku;

    /** 默认携带，默认值：否 */
    @ApiModelProperty("默认携带，默认值：否")
    private String defCarry;

    /** 标签短描述，由物料编码字段带出 */
    @ApiModelProperty("标签短描述，由物料编码字段带出")
    @ExcelProperty("标签短描述")
    private String shortDesc;

    /** 站点，由客户字段中截取最后一小段带出 */
    @ApiModelProperty("站点，由客户字段中截取最后一小段带出")
    @ExcelProperty("站点")
    private String station;

    /** 颜色，由物料编码字段带出 */
    @ApiModelProperty("颜色，由物料编码字段带出")
    @ExcelProperty("颜色")
    private String bscColor;


   /** 推送K3状态(0 ：同步失败,1：同步成功) */
   @ApiModelProperty("推送K3状态(0 ：同步失败,1：同步成功)")
   @ExcelProperty("推送K3状态")
   private String syncStatus;


   /** 模板名称，留空 */
    @ApiModelProperty("模板名称，留空")
    private String bscTemplateName;

    /** 是否删除:正常：0，删除：1 */
    @ApiModelProperty("是否删除:正常：0，删除：1")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @ApiModelProperty("同步方式(0 ：系统同步,1：手动人工同步)")
    private String syncType;

    /** 推送K3时间 */
    @ApiModelProperty("推送K3时间")
    @ExcelProperty("推送K3时间")
    private Date syncTime;


    /** 同步成功次数(反审核通过情况次数增加) */
    @ApiModelProperty("同步成功次数(反审核通过情况次数增加)")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @ApiModelProperty("同步失败次数")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;


   private String billCode;

}