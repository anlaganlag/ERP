package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class InvReportUploadRecordResult implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private BigDecimal id;

  /**
   * 文件存放路径
   */
  @ApiModelProperty("FILE_PATH")
  private String filePath;

  /**
   * 报告类型
   */
  @ApiModelProperty("REPORT_TYPE")
  private String reportType;

  /**
   * 解析状态
   */
  @ApiModelProperty("PARSE_STATUS")
  private String parseStatus;

  /**
   * 失败原因
   */
  @ApiModelProperty("FAILURE_REASON")
  private String failureReason;

  /**
   * 创建时间
   */
  @ApiModelProperty("CREATE_AT")
  private LocalDateTime createAt;

  /**
   * 创建人
   */
  @ApiModelProperty("CREATE_BY")
  private String createBy;
}
