package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 提案-设置-设置修改记录 入参类
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
@TableName("RD_SETTING_UP_RECORD")
public class RdSettingUpRecordParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "系统编号", type = IdType.ASSIGN_ID)
    private String id;

    /** 最后更新时间 */
    @ApiModelProperty("最后更新时间")
    private Date sysLDate;

    /** 登陆用户员工编号 */
    @ApiModelProperty("登陆用户员工编号")
    private String sysPerCode;

    /** 登陆用户员工姓名 */
    @ApiModelProperty("登陆用户员工姓名")
    private String sysPerName;

    /** 修改设置类型 {"提案等级设置","拿样任务时长设置","研发费用自动过审设置","拿样次数设置"} */
    @ApiModelProperty("修改设置类型 {'提案等级设置','拿样任务时长设置','研发费用自动过审设置','拿样次数设置'}")
    private String sysModifySettingType;

    /** 修改记录编号 */
    @ApiModelProperty("修改记录编号")
    private String sysModifyRecordNum;

    /** 修改值项 */
    @ApiModelProperty("修改值项")
    private String sysModifyRecordParam;

    /** 修改前原值 */
    @ApiModelProperty("修改前原值")
    private String sysModifyRecordOrgValue;

    /** 修改后现值 */
    @ApiModelProperty("修改后现值")
    private String sysModifyRecordNewValue;

}
