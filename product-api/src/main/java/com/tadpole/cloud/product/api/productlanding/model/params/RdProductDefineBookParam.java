package com.tadpole.cloud.product.api.productlanding.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 预提案-改良 入参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@TableName("RD_PRE_PROPOSAL_UP")
public class RdProductDefineBookParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品定义书-文档编号")
    private String sysDefBookCode;

    @ApiModelProperty("单据联系-产品线名称")
    private String sysPlName;

    @ApiModelProperty("产品定义书-物料编码")
    private String sysMatCode;

}
