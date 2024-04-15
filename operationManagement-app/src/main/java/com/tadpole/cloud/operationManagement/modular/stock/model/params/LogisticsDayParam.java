package com.tadpole.cloud.operationManagement.modular.stock.model.params;

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
import java.util.List;

/**
* <p>
    * 物流天数维护
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LOGISTICS_DAY")
public class LogisticsDayParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 区域 */
    @ApiModelProperty("AREA")
    private String area;


    @ApiModelProperty("AREA")
    private List<String> areaList;

    /** 国家 */
    @ApiModelProperty("COUNTRY")
    private String country;

    /** 物流方式 */
    @ApiModelProperty("LOGISTICS_MODE")
    private String logisticsMode;

    /** 运输天数 */
    @ApiModelProperty("LOGISTICS_DAYS")
    private String logisticsDays;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 更新人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}