package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
@ExcelIgnoreUnannotated
public class LogisticsDay implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 数据id
     */


    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    /**
     * 平台
     */
    @ExcelProperty("平台")
    @TableField("PLATFORM_NAME")
    private String platformName;

    /**
     * 区域
     */
    @ExcelProperty("区域")

    @TableField("AREA")
    private String area;

    /**
     * 国家
     */
    @TableField("COUNTRY")
    private String country;

    /**
     * 物流方式
     */
    @ExcelProperty("物流方式")

    @TableField("LOGISTICS_MODE")
    private String logisticsMode;

    /**
     * 运输天数
     */
    @ExcelProperty("运输天数")

    @TableField("LOGISTICS_DAYS")
    private String logisticsDays;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 创建人
     */
    @TableField("CREATE_BY")
    private String createBy;

    /**
     * 更新人
     */
    @TableField("UPDATE_BY")
    private String updateBy;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsDay that = (LogisticsDay) o;
        return getArea().equals(that.getArea()) && getLogisticsMode().equals(that.getLogisticsMode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArea(), getLogisticsMode());
    }
}