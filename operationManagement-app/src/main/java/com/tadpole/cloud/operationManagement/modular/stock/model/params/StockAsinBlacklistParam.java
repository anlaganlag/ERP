package com.tadpole.cloud.operationManagement.modular.stock.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    * ASIN黑名单
    * </p>
*
* @author lsy
* @since 2022-12-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_ASIN_BLACKLIST")
public class StockAsinBlacklistParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("平台")
    private List<String> platformList;

    /** 区域 */
    @ApiModelProperty("区域")
    private List<String> areaList;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private List<String> productTypeList;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private List<String> departmentList;

    /** Team */
    @ApiModelProperty("Team")
    private List<String> teamList;

    /** 物料编码lIST */
    @ApiModelProperty("物料编码lIST")
    private List<String> materialCodeList;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private List<String> asinList;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** 站点 */
    @ApiModelProperty("站点")
    private List<String> countryCodeList;

    /** 状态;黑名单：'0' */
    @ApiModelProperty("状态;黑名单：'0'")
    private String status;

    /** 负责人*/
    @ApiModelProperty("负责人")
    private String operatorsName;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private Date createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private Date updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
