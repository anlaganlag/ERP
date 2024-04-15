package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 提案-设置-设置修改记录 实体类
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
@ExcelIgnoreUnannotated
public class RdSettingUpRecord implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /** 最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 登陆用户员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 登陆用户员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 修改设置类型 {"提案等级设置","拿样任务时长设置","研发费用自动过审设置","拿样次数设置"} */
    @TableField("SYS_MODIFY_SETTING_TYPE")
    private String sysModifySettingType;

    /** 修改记录编号 */
    @TableField("SYS_MODIFY_RECORD_NUM")
    private String sysModifyRecordNum;

    /** 修改值项 */
    @TableField("SYS_MODIFY_RECORD_PARAM")
    private String sysModifyRecordParam;

    /** 修改前原值 */
    @TableField("SYS_MODIFY_RECORD_ORG_VALUE")
    private String sysModifyRecordOrgValue;

    /** 修改后现值 */
    @TableField("SYS_MODIFY_RECORD_NEW_VALUE")
    private String sysModifyRecordNewValue;

}