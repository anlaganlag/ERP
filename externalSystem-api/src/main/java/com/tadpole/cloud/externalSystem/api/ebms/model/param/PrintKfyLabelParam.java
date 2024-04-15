package com.tadpole.cloud.externalSystem.api.ebms.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 提案-定制申请 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class PrintKfyLabelParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("开发样编码")
    private String KfyLabelNumber;

    @ApiModelProperty("")
    private String MatOperateCate;

    @ApiModelProperty("")
    private String MatProName;

}
