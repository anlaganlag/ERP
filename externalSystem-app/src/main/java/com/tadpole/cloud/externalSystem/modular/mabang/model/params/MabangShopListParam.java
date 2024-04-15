package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("MABANG_SHOP_LIST")
public class MabangShopListParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 店铺编号，业务主键 */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 店铺名称 */
    @ApiModelProperty("NAME")
    private String name;

    /** 店铺名称-多选 */
    @ApiModelProperty("店铺名称-多选")
    private List<String> names;

    /** 平台编号 */
    @ApiModelProperty("PLATFORM_ID")
    private String platformId;

    /** 平台名称 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 平台名称-多选 */
    @ApiModelProperty("平台名称-多选")
    private List<String> platformNames;

    /** 店铺站点 */
    @ApiModelProperty("AMAZONSITE")
    private String amazonsite;

    /** 财务编码 */
    @ApiModelProperty("FINANCE_CODE")
    private String financeCode;


    @ApiModelProperty("财务编码")
    private String originalFinanceCode;

    /** 店铺状态:1启用2停用 */
    @ApiModelProperty("STATUS")
    private String status;

    /** 平台店铺账号 */
    @ApiModelProperty("ACCOUNT_USERNAME")
    private String accountUsername;

    /** 平台店铺标识 */
    @ApiModelProperty("ACCOUNT_STORE_NAME")
    private String accountStoreName;

    /** 店铺tokenid */
    @ApiModelProperty("DATACENTER_TOKENID")
    private String datacenterTokenid;

    /** 店长ID */
    @ApiModelProperty("SHOP_EMPLOYEE_ID")
    private String shopEmployeeId;

    /** 0 代表未删除,1 代表已删除 */
    @ApiModelProperty("IS_DELETE")
    private String isDelete;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间(本条记录值最后同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（0 ：同步失败,1：同步成功） */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步失败次数默认0次 */
    @ApiModelProperty("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 店铺负责人名称 */
    @ApiModelProperty("店铺负责人名称")
    private String employeeName;
}