package com.tadpole.cloud.product.modular.product.model.result;

import java.math.BigDecimal;

import cn.stylefeng.guns.cloud.model.objectLog.model.AttributeModel;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 *  出参类
 * </p>
 *
 * @author S20210221
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_MANAGE_UP_RECORD")
public class RdManageUpRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("产品线或SPU")
    private String sysType;


    @ApiModelProperty("修改日期")
    private Date sysLDate;


    @ApiModelProperty("修改人工号")
    private String sysPerCode;


    @ApiModelProperty("修改人姓名")
    private String sysPerName;


    @ApiModelProperty("修改记录编号")
    private String sysModifyRecordNum;


    @ApiModelProperty("更新内容")
    private String sysModifyContent;

    @ApiModelProperty("更新内容")
    private List<AttributeModel> attributeModels;

}
