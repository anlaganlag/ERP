package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 马帮店铺列表
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MABANG_SHOP_LIST")
@ExcelIgnoreUnannotated
public class MabangShopList implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 店铺编号，业务主键 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 店铺名称 */
    @TableField("NAME")
    private String name;

    /** 平台编号 */
    @TableField("PLATFORM_ID")
    private String platformId;

    /** 平台名称 */
    @TableField("PLATFORM_NAME")
    private String platformName;

    /** 店铺站点 */
    @TableField("AMAZONSITE")
    private String amazonsite;

    /** 财务编码 */
    @TableField("FINANCE_CODE")
    private String financeCode;

    /** 店铺状态:1启用2停用 */
    @TableField("STATUS")
    private String status;

    /** 平台店铺账号 */
    @TableField("ACCOUNT_USERNAME")
    private String accountUsername;

    /** 平台店铺标识 */
    @TableField("ACCOUNT_STORE_NAME")
    private String accountStoreName;

    /** 店铺tokenid */
    @TableField("DATACENTER_TOKENID")
    private String datacenterTokenid;

    /** 店长ID */
    @TableField("SHOP_EMPLOYEE_ID")
    private String shopEmployeeId;

    /** 0 代表未删除,1 代表已删除 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间(本条记录值最后同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（0 ：同步失败,1：同步成功） */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步失败次数默认0次 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;


    @TableField("ORIGINAL_FINANCE_CODE")
    private String originalFinanceCode;

}