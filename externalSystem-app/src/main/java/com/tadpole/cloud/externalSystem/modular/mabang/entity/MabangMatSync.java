package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("MABANG_MAT_SYNC")
@ExcelIgnoreUnannotated
public class MabangMatSync implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id--数据id */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** Erp商品编号--Erp商品编号 */
    @TableField("STOCK_ID")
    private String stockId;

    /** 物料编码--物料编码 */
    @TableField("MAT_CODE")
    private String matCode;

    /** 物料名称-EBMS */
    @TableField("MAT_NAME")
    private String matName;

    /** 合并物料名称-马帮erp*/
    @TableField("MERGE_MAT_NAME")
    private String mergeMatName;


    /** 库存SKU--库存SKU */
    @TableField("STOCK_SKU")
    private String stockSku;

    /** 马帮仓库名称 */
    @TableField("MABANG_WAREHOUSE_NAME")
    private String mabangWarehouseName;

    /** 马帮仓库ID--马帮仓库ID */
    @TableField("MABANG_WAREHOUSE_ID")
    private String mabangWarehouseId;

    /** 1、默认仓库2非默认仓库 */
    @TableField("IS_DEFAULT")
    private String isDefault;


    /** 仓位，可以多个 */
    @TableField("GRID_LIST")
    private String gridList;




    /** 商品状态--1、自动创建；2、待开发；3、正常；4、清仓；5、停止销售--EBMS启用-->3正常（马帮）	禁用-->5停止销售（马帮） */
    @TableField("STATUS")
    private String status;

    /** 物料创建时间--物料创建时间 */
    @TableField("MAT_CREATE_TIME")
    private Date matCreateTime;

    /** 物料最后一次更新时间--物料最后一次更新时间 */
    @TableField("MAT_UPDATE_TIME")
    private Date matUpdateTime;

    /** 同步业务类型--(新增，更新,删除) */
    @TableField("BIZ_TYPE")
    private String bizType;

    /** 一级目录名称--一级目录名称 */
    @TableField("PARENT_CATEGORY_NAME")
    private String parentCategoryName;

    /** 二级目录--二级目录 */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 是否删除--是否删除:正常：0，删除：1 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 同步方式--同步方式(0 ：系统同步,1：手动人工同步) */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间--同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态--同步状态(0 ：同步失败,1：同步成功) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步请求参数--同步结果消息内容 */
    @TableField("SYNC_REQUST_PAR")
    private String syncRequstPar;

    /** 同步结果消息内容--同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间--创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间--更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}