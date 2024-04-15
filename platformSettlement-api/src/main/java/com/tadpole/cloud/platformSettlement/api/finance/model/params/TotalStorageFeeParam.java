package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* <p>
* 仓储费合计 数据来源 定期生成
* </p>
*
* @author S20190161
* @since 2022-10-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TOTAL_STORAGE_FEE")
public class TotalStorageFeeParam extends BaseRequest implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;

 private List<String> sysSites;

 private List<String> sysShopsNames;

 private String sysSite;

 private String sysShopsName;

 @ApiModelProperty("会计期间")
 private String startDur;

 @ApiModelProperty("会计期间")
 private String endDur;

 @ApiModelProperty("是否差异：1是0否")
 private Integer difference;

}
