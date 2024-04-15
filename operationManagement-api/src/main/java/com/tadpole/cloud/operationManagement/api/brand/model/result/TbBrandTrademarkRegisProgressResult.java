package com.tadpole.cloud.operationManagement.api.brand.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;


/**
* <p>
* 品牌商标注册进度表
* </p>
* @author S20190161
* @since 2023-10-24
*/
@Data
public class TbBrandTrademarkRegisProgressResult {
    private static final long serialVersionUID = 1L;

    private Long id;
    @ApiModelProperty("品牌商标注册表主键")
    private Long regisId;
    @ApiModelProperty("申请费用(含税/元)")
    private BigDecimal applyOutlayPlusTax;
    @ApiModelProperty("第一次付款日期")
    private Date paymentDateFirst;
    @ApiModelProperty("实付金额")
    private BigDecimal paymentAmount;
    @ApiModelProperty("发票号码")
    private String invoiceNumber;
    @ApiModelProperty("发票文件--支持的文件格{pdf;xls,txt,doc}")
    private String invoiceFiles;
    @ApiModelProperty("合同签订日期")
    private Date contractSigningDate;
    @ApiModelProperty("合同文件--支持的文件格{pdf;xls,txt,doc}")
    private String contractFiles;
    @ApiModelProperty("申请日期")
    private Date applyDate;
    @ApiModelProperty("申请号")
    private String applyNo;
    @ApiModelProperty("获证日期")
    private Date certificateDate;
    @ApiModelProperty("注册号")
    private String registerNumber;
    @ApiModelProperty("商标有效期开始")
    private Date trademarkValidityTermStart;
    @ApiModelProperty("商标有效期结束")
    private Date trademarkValidityTermEnd;
    @ApiModelProperty("商标状态：0.申请中;1.已受理;2.审查中;3.公告中;4.已下证;5.异常归档")
    private Integer trademarkStatus;
    private Date createTime;
    private String createName;
    private Date updateTime;
    private String  updateName;
    @ApiModelProperty("进度备注信息")
    private List<ProgressRemarkResult> remarks;
}
