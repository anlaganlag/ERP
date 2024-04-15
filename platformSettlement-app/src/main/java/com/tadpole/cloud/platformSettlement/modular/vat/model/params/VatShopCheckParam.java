package com.tadpole.cloud.platformSettlement.modular.vat.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * VAT核对表
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("VAT_SHOP_CHECK")
public class VatShopCheckParam  extends BaseRequest implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;

 /** 汇率id--自增 */
 @TableId(value = "ID", type = IdType.AUTO)
 private BigDecimal id;

 /** 期间 */
 @ApiModelProperty("ACTIVITY_PERIOD")
 private String activityPeriod;

 /** ebms账号 */
 @ApiModelProperty("EBMS_SHOPS_NAME")
 private String ebmsShopsName;

 /** VAT报告账号 */
 @ApiModelProperty("SYS_SHOPS_NAME")
 private String sysShopsName;

 /** 站点 */
 @ApiModelProperty("SYS_SITE")
 private String sysSite;

 /** 异常类型 */
 @ApiModelProperty("ABNORMAL_TYPE")
 private String abnormalType;

 /** 备注 */
 @ApiModelProperty("REMARK")
 private String remark;

 /** 创建人 */
 @ApiModelProperty("CREATE_PERSON")
 private String createPerson;

 /** 创建时间 */
 @ApiModelProperty("CREATE_TIME")
 private Date createTime;

 /** 最后更新时间--默认值：getdate */
 @ApiModelProperty("UPDATE_TIME")
 private Date updateTime;

 /** 最后更新人名称--默认值：当前修改人名称 */
 @ApiModelProperty("UPDATE_PERSON")
 private String updatePerson;

}