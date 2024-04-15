package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 马帮仓库列表
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
@TableName("MABANG_WAREHOUSE")
@ExcelIgnoreUnannotated
public class MabangWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 仓库编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 名称 */
    @TableField("NAME")
    private String name;

    /** 1启用2停用 */
    @TableField("STATUS")
    private String status;

    /** 1：自建仓库2：第三方仓库 */
    @TableField("TYPE")
    private String type;

    /** 联系人 */
    @TableField("CONTACT")
    private String contact;

    /** 国家 */
    @TableField("COUNTRY_CODE")
    private String countryCode;

    /** 省 */
    @TableField("PROVINCE")
    private String province;

    /** 市 */
    @TableField("CITY")
    private String city;

    /** 区 */
    @TableField("AREA")
    private String area;

    /** 地址 */
    @TableField("ADDRESS")
    private String address;

    /** 邮编 */
    @TableField("ZIPCODE")
    private String zipcode;

    /** 电话 */
    @TableField("TELEPHONE")
    private String telephone;

    /** 手机号码 */
    @TableField("PHONE")
    private String phone;

    /** 1是2否默认仓库 */
    @TableField("IS_DEFAULT")
    private String isDefault;

    /** 仓库编码 */
    @TableField("CODE")
    private String code;

    /** 财务编码 */
    @TableField("FINANCE_CODE")
    private String financeCode;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间(本条记录值最后同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（） ：同步失败,1：同步成功 */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步失败次数默认0次 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}