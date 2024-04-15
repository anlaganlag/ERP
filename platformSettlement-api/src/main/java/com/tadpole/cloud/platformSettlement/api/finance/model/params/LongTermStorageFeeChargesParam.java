package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* 长期仓储费
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LONG_TERM_STORAGE_FEE_CHARGES")
public class LongTermStorageFeeChargesParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** Sku */
    @ApiModelProperty("SKU")
    private String sku;

    /** Fnsku */
    @ApiModelProperty("FNSKU")
    private String fnsku;

    @ApiModelProperty("状态")
    private Integer status;

    private List<String> sysSites;

    private List<String> sysShopsNames;

    private String sysSite;

    private String sysShopsName;

    @ApiModelProperty("会计期间")
    private String startDur;

    @ApiModelProperty("会计期间")
    private String endDur;

    private String materialCode;


    @ApiModelProperty("事业部")
    private String department;

    @ApiModelProperty("Team")
    private String team;

    @ApiModelProperty("快照期间")
    private String snapshotDate;


    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("邮件列表")
    private List<String> emailList;


}
