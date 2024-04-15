package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* FBA存货存储超额费用报告
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
@TableName("INVENTORY_STORAGE_OVERAGE_FEES")
public class InventoryStorageOverageFeesParam implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;

 /**
  * 收费日期
  */
 @ApiModelProperty("CHARGED_DATE")
 private Date chargedDate;

 /**
  * 国家代码
  */
 @ApiModelProperty("COUNTRY_CODE")
 private String countryCode;

 /**
  * 1.未确认
  * 2.已确认
  */
 @ApiModelProperty("STATUS")
 private Integer status;

 private List<String> sysSites;

 private List<String> sysShopsNames;

 private String sysSite;

 private String sysShopsName;

 @ApiModelProperty("会计期间")
 private String startDur;

 @ApiModelProperty("会计期间")
 private String endDur;
}
