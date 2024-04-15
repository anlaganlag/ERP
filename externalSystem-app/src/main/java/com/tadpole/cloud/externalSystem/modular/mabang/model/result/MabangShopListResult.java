package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 马帮店铺列表
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("MABANG_SHOP_LIST")
public class MabangShopListResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


// @ExcelProperty("申请日期")
// @DateTimeFormat("yyyy-MM-dd")

    /** 店铺编号，业务主键 */
    @ExcelProperty("店铺编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 店铺名称 */
    @ExcelProperty("店铺名称")
    @ApiModelProperty("店铺名称")
    private String name;

    /** 平台编号 */
    @ExcelProperty("平台编号")
    @ApiModelProperty("平台编号")
    private String platformId;

    /** 平台名称 */
    @ExcelProperty("平台名称")
    @ApiModelProperty("平台名称")
    private String platformName;

    /** 店铺站点 */
    @ApiModelProperty("店铺站点")
    private String amazonsite;

    /** 财务编码 */
    @ApiModelProperty("财务编码")
    private String financeCode;


    @ExcelProperty("金蝶组织编码")
    @ApiModelProperty("财务编码")
    private String originalFinanceCode;

    /** 店铺状态:1启用2停用 */
    @ApiModelProperty("店铺状态:1启用2停用")
    private String status;

    @ExcelProperty("店铺状态")
    private String statusName;
    /** 平台店铺账号 */
    @ApiModelProperty("平台店铺账号")
    private String accountUsername;

    /** 平台店铺标识 */
    @ApiModelProperty("平台店铺标识")
    private String accountStoreName;

    /** 店铺tokenid */
    @ApiModelProperty("店铺tokenid")
    private String datacenterTokenid;

    /** 店长ID */
    @ApiModelProperty("店长ID")
    private String shopEmployeeId;

    /** 0 代表未删除,1 代表已删除 */
    @ApiModelProperty("0 代表未删除,1 代表已删除")
    private String isDelete;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("同步方式（0 ：系统同步,1：手动人工同步）")
    private String syncType;

    /** 同步时间(本条记录值最后同步时间 */
    @ExcelProperty("同步时间")
    @ApiModelProperty("同步时间(本条记录值最后同步时间")
    private Date syncTime;

    /** 同步状态（0 ：同步失败,1：同步成功） */
    @ApiModelProperty("同步状态（0 ：同步失败,1：同步成功）")
    private String syncStatus;

    /** 同步失败次数默认0次 */
    @ApiModelProperty("同步失败次数默认0次")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;


    /** 店铺负责人名称 */
    @ApiModelProperty("店铺负责人名称")
    private String employeeName;


}