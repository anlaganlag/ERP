package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    * 马帮物料同步记录表
    * </p>
*
* @author gal
* @since 2022-10-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class MabangMatSyncResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


 /** 数据id--数据id */
 @TableId(value = "ID", type = IdType.AUTO)
 private String id;

 /** Erp商品编号--Erp商品编号 */
 @ApiModelProperty("Erp商品编号")
 private String stockId;

 /** 物料编码--物料编码 */
 @ApiModelProperty("物料编码")
 private String matCode;

 /** 物料名称 */
 @ApiModelProperty("物料名称-EBMS")
 private String matName;

 /** 合并物料名称*/
 @ApiModelProperty("合并物料名称-马帮erp")
 private String mergeMatName;

 /** 库存SKU--库存SKU */
 @ApiModelProperty("库存SKU")
 private String stockSku;

 /** 马帮仓库名称 */
 @ApiModelProperty("马帮仓库名称")
 private String mabangWarehouseName;

 /** 马帮仓库ID--马帮仓库ID */
 @ApiModelProperty("马帮仓库ID")
 private String mabangWarehouseId;

 /** 1、默认仓库2非默认仓库 */
 @ApiModelProperty("默认仓库2非默认仓库")
 private String isDefault;

 /** 仓位，可以多个 */
 @ApiModelProperty("仓位")
 private String gridList;

 /** 备注 */
 @ApiModelProperty("备注")
 private String remark;

 /** 商品状态--1、自动创建；2、待开发；3、正常；4、清仓；5、停止销售--EBMS启用-->3正常（马帮）	禁用-->5停止销售（马帮） */
 @ApiModelProperty("商品状态--1、自动创建；2、待开发；3、正常；4、清仓；5、停止销售")
 private String status;

 /** 物料创建时间--物料创建时间 */
 @ApiModelProperty("物料创建时间")
 private Date matCreateTime;

 /** 物料最后一次更新时间--物料最后一次更新时间 */
 @ApiModelProperty("物料最后一次更新时间")
 private Date matUpdateTime;

 /** 同步业务类型--(新增，更新,删除) */
 @ApiModelProperty("同步业务类型--(新增，更新,删除)")
 private String bizType;

 /** 一级目录名称--一级目录名称 */
 @ApiModelProperty("一级目录名称")
 private String parentCategoryName;

 /** 二级目录--二级目录 */
 @ApiModelProperty("二级目录")
 private String categoryName;

 /** 是否删除--是否删除:正常：0，删除：1 */
 @ApiModelProperty("是否删除--是否删除:正常：0，删除：1")
 private String isDelete;

 /** 同步方式--同步方式(0 ：系统同步,1：手动人工同步) */
 @ApiModelProperty("同步方式--同步方式(0 ：系统同步,1：手动人工同步)")
 private String syncType;

 /** 同步时间--同步时间 */
 @ApiModelProperty("同步时间")
 private Date syncTime;

 /** 同步状态--同步状态(0 ：同步失败,1：同步成功) */
 @ApiModelProperty("同步状态--同步状态(0 ：同步失败,1：同步成功)")
 private String syncStatus;

 /** 同步请求参数--同步结果消息内容 */
 @ApiModelProperty("同步请求参数")
 private String syncRequstPar;

 /** 同步结果消息内容--同步结果消息内容 */
 @ApiModelProperty("同步结果消息内容")
 private String syncResultMsg;

 /** 创建时间--创建时间 */
 @ApiModelProperty("创建时间")
 private Date createTime;

 /** 更新时间--更新时间 */
 @ApiModelProperty("更新时间")
 private Date updateTime;


}