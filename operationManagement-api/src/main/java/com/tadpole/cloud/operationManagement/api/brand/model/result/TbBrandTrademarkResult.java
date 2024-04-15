package com.tadpole.cloud.operationManagement.api.brand.model.result;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import java.util.Date;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;


/**
* <p>
* 品牌商标表
* </p>
* @author S20190161
* @since 2023-10-19
*/
@Data
public class TbBrandTrademarkResult {

    private static final long serialVersionUID = 1L;

    private Long id;
    @ApiModelProperty("注册信息表ID")
    private Long regisId;
    @ApiModelProperty("注册进度表ID")
    private Long progressId;
    @ApiModelProperty("商标名称")
    private String tradeName;
    @ApiModelProperty("商标类型：0.文字商标;1.图形商标;")
    private Long type;
    @ApiModelProperty("是否注册")
    private Integer isRegister;
    @ApiModelProperty("备注说明")
    private String remark;

    @ApiModelProperty("注册国家")
    private String registerCountry;
    @ApiModelProperty("商标大类")
    private String trademarkCategory;
    @ApiModelProperty("商标小类")
    private String trademarkSubClass;

    @ApiModelProperty("商标状态")
    private Integer trademarkStatus;
    @ApiModelProperty("提案时间")
    private Date createTime;
    @ApiModelProperty("提案人姓名")
    private String createName;

    @ApiModelProperty("代理机构")
    private String agencyOrganization;
}
