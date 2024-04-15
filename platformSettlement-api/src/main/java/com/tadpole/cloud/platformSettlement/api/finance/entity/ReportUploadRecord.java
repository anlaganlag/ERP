package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

/**
* <p>
* AZ报告上传记录
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_REPORT_UPLOAD_RECORD")
public class ReportUploadRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 源仓任务名称 */
    @TableField("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    /** 平台 */
    @TableField("PLATFORM_NAME")
    private String platformName;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 获取路径 */
    @TableField("FILE_PATH")
    private String filePath;

    /** 单据类型 */
    @TableField("BILL_TYPE")
    private String billType;

    /** 结算id */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** 开始时间 */
    @TableField("START_TIME")
    private Date startTime;

    /** 结束时间 */
    @TableField("END_TIME")
    private Date endTime;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 解析状态 */
    @TableField("PARSE_STATUS")
    private String parseStatus;

    /** 失败原因 */
    @TableField("FAILURE_REASON")
    private String failureReason;

    /** 上传方式 */
    @TableField("UPLOAD_TYPE")
    private String uploadType;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private Date createAt;

    /** 修改时间 */
    @TableField("UPDATE_AT")
    private Date updateAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

}