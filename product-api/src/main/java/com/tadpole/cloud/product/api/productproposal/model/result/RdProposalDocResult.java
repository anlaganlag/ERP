package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-提案管理-设计文档 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_PROPOSAL_DOC")
public class RdProposalDocResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty("提案编号")
    private String sysPerName;


    @ApiModelProperty("上传日期")
    private Date sysTaCode;


    @ApiModelProperty("上传人编号")
    private String sysCDate;


    @ApiModelProperty("上传人姓名")
    private String sysLDate;


    @ApiModelProperty("上传方式")
    private String sysDeptCode;


    @ApiModelProperty("设计文档文件名称")
    private String sysDeptName;


    @ApiModelProperty("设计文档文件路径")
    private String sysPerCode;

}
