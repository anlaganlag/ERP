package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* EBMS转仓清单（欧洲站点调拨)
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("AMAZON_INVENTORY_EU_TRANSFER")
public class AmazonInventoryEuTransferParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("")
    private String org;

    @ApiModelProperty("")
    private String warehouseName;

    @ApiModelProperty("")
    private String sku;

    @ApiModelProperty("")
    private Long qty;

    @ApiModelProperty("")
    private Date createTime;
}
