package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
public class InvReportUploadRecordParam extends BaseRequest implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private Long id;

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

  /** 账号 */
  @ApiModelProperty("SHOP_NAME")
  private String shopName;

  /** 站点 */
  @ApiModelProperty("SITE")
  private String site;

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
