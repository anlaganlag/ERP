package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-设置-设置修改记录 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_SETTING_UP_RECORD")
public class RdSettingUpRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty("最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("登陆用户员工编号")
    private String sysPerCode;


    @ApiModelProperty("登陆用户员工姓名")
    private String sysPerName;


    @ApiModelProperty("修改设置类型 {'提案等级设置','拿样任务时长设置','研发费用自动过审设置','拿样次数设置'}")
    private String sysModifySettingType;


    @ApiModelProperty("修改记录编号")
    private String sysModifyRecordNum;


    @ApiModelProperty("修改值项")
    private String sysModifyRecordParam;


    @ApiModelProperty("修改前原值")
    private String sysModifyRecordOrgValue;


    @ApiModelProperty("修改后现值")
    private String sysModifyRecordNewValue;

}
