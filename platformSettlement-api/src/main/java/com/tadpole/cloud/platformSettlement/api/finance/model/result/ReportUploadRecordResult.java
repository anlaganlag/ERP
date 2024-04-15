package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 设置期初余额
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class ReportUploadRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("id")
    private int id;

    /**
     * 源仓任务名称
     */
    @ApiModelProperty("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /** 获取路径 */
    @ApiModelProperty("FILE_PATH")
    private String filePath;

    /** 单据类型 */
    @ApiModelProperty("BILL_TYPE")
    private String billType;

    /** 结算id */
    @ApiModelProperty("SETTLEMENT_ID")
    private String settlementId;

    /** 开始时间 */
    @ApiModelProperty("START_TIME")
    private String startTime;

    /** 结束时间 */
    @ApiModelProperty("END_TIME")
    private String endTime;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 解析状态 */
    @ApiModelProperty("PARSE_STATUS")
    private String parseStatus;

    /** 失败原因 */
    @ApiModelProperty("FAILURE_REASON")
    private String failureReason;

    /** 上传方式 */
    @ApiModelProperty("UPLOAD_TYPE")
    private String uploadType;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private String updateAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

}