package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;

/**
* <p>
*
* </p>
*
* @author cyt
* @since 2022-05-27
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ErpBankAccountResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 银行账户 */
    @ApiModelProperty("银行账户")
    private String bankAccount;

    /** 银行名称 */
    @ApiModelProperty("银行名称")
    private String bankName;

    /** 账号所属 */
    @ApiModelProperty("账号所属")
    private String bankAttribution;

    /** 银行账号 */
    @ApiModelProperty("银行账号")
    private String bankNumber;
}