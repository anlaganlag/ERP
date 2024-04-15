package com.tadpole.cloud.product.api.productproposal.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalUpResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提案-提案管理 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class RdProposalPublicInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("立项信息-产品经理编号")
    private String sysPmPerCode;


    @ApiModelProperty("立项信息-产品经理姓名")
    private String sysPmPerName;

    /** 产品线设定-产品线名称 */
    @ApiModelProperty("产品线名称")
    private String sysPlName;

    /** 预案公共-产品名称 */
    @ApiModelProperty("预案公共-产品名称")
    private String sysProName;

    /** 预案公共-款式 */
    @ApiModelProperty("预案公共-款式")
    private String sysStyle;

    /** 预案公共-适用品牌或对象 */
    @ApiModelProperty("预案公共-适用品牌或对象")
    private String sysBrand;

    /** 预案公共-主材料 */
    @ApiModelProperty("预案公共-主材料")
    private String sysMainMaterial;

    /** 预案公共-型号 */
    @ApiModelProperty("预案公共-型号")
    private String sysModel;

    @ApiModelProperty("立项信息-开发方式")
    private String sysDevMethond;

    @ApiModelProperty("立项信息-上架时间要求")
    private String sysTaLtrDate;

    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;


    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;

    @ApiModelProperty("拿样任务-样品信息")
    private List<RdSampleManageResult> rdSampleManageResults;
}
