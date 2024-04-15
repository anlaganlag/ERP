package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 上传记录表
 * </p>
 *
 * @author gal
 * @since 2021-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INV_REPORT_UPLOAD_RECORD")
public class InvReportUploadRecord implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 文件存放路径
   */
  @TableField("FILE_PATH")
  private String filePath;

  /**
   * 报告类型
   */
  @TableField("REPORT_TYPE")
  private String reportType;

  /**
   * 解析状态
   */
  @TableField("PARSE_STATUS")
  private String parseStatus;

  /**
   * 失败原因
   */
  @TableField("FAILURE_REASON")
  private String failureReason;

  /**
   * 创建时间
   */
  @TableField("CREATE_AT")
  private Date createAt;

  /**
   * 创建人
   */
  @TableField("CREATE_BY")
  private String createBy;

  /**
   * 账号
   */
  @TableField("SYS_SHOPS_NAME")
  private String shopName;

  /**
   * 站点
   */
  @TableField("SYS_SITE")
  private String site;

  /**
   * 上传标识
   */
  @TableField("UPLOAD_MARK")
  private String uploadMark;
}