package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 提案-提案管理-设计文档 入参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PROPOSAL_DOC")
public class RdProposalDocParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 提案编号 */
    @ApiModelProperty("提案编号")
    private String sysPerName;

    /** 上传日期 */
    @ApiModelProperty("上传日期")
    private Date sysTaCode;

    /** 上传人编号 */
    @ApiModelProperty("上传人编号")
    private String sysCDate;

    /** 上传人姓名 */
    @ApiModelProperty("上传人姓名")
    private String sysLDate;

    /** 上传方式 */
    @ApiModelProperty("上传方式")
    private String sysDeptCode;

    /** 设计文档文件名称 */
    @ApiModelProperty("设计文档文件名称")
    private String sysDeptName;

    /** 设计文档文件路径 */
    @ApiModelProperty("设计文档文件路径")
    private String sysPerCode;

}
