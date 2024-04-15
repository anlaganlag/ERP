package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyBankCollectTask;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyDet;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyTaxnTask;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

 /**
 * 资源-店铺申请;资源-店铺申请
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺申请",description = "资源-店铺申请")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopApplyResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 申请编号 */
    @ApiModelProperty(value = "申请编号")
    @ExcelProperty(value ="申请编号")
    private BigDecimal sysApplyId ;
    
    /** 申请人编号 */
    @ApiModelProperty(value = "申请人编号")
    @ExcelProperty(value ="申请人编号")
    private String sysApplyPerCode ;
    
    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名")
    @ExcelProperty(value ="申请人姓名")
    private String sysApplyPerName ;
    
    /** 申请时间 */
    @ApiModelProperty(value = "申请时间")
    @ExcelProperty(value ="申请时间")
    private Date sysApplyDate ;
    
    /** 申请状态;申请状态(1:平台账号申请、2:资料上传、3:店铺创建、4:财务任务刘表、5:完结) */
    @ApiModelProperty(value = "申请状态")
    @ExcelProperty(value ="申请状态")
    private Integer sysApplyState ;
    
    /** 店铺主体公司(中文) */
    @ApiModelProperty(value = "店铺主体公司(中文)")
    @ExcelProperty(value ="店铺主体公司(中文)")
    private String comNameCn ;
    
    /** 销售平台 */
    @ApiModelProperty(value = "销售平台")
    @ExcelProperty(value ="销售平台")
    private String elePlatformName ;
    
    /** 附件名称 */
    @ApiModelProperty(value = "附件名称")
    @ExcelProperty(value ="附件名称")
    private String attachName ;
    
    /** 申请说明 */
    @ApiModelProperty(value = "申请说明")
    @ExcelProperty(value ="申请说明")
    private String sysApplyDesc ;
    
    /** 账号简称 */
    @ApiModelProperty(value = "账号简称")
    @ExcelProperty(value ="账号简称")
    private String shopNameSimple ;
    
    /** 法人（英文） */
    @ApiModelProperty(value = "法人（英文）")
    @ExcelProperty(value ="法人（英文）")
    private String shopLegPersonEn ;
    
    /** 绑定电话 */
    @ApiModelProperty(value = "绑定电话")
    @ExcelProperty(value ="绑定电话")
    private String shopTelephone ;
    
    /** 店铺公司名称（英文） */
    @ApiModelProperty(value = "店铺公司名称（英文）")
    @ExcelProperty(value ="店铺公司名称（英文）")
    private String shopComNameEn ;
    
    /** 店铺公司地址1（英文） */
    @ApiModelProperty(value = "店铺公司地址1（英文）")
    @ExcelProperty(value ="店铺公司地址1（英文）")
    private String shopComAddrEn1 ;
    
    /** 店铺公司地址2（英文） */
    @ApiModelProperty(value = "店铺公司地址2（英文）")
    @ExcelProperty(value ="店铺公司地址2（英文）")
    private String shopComAddrEn2 ;
    
    /** 店铺发货地址1（英文） */
    @ApiModelProperty(value = "店铺发货地址1（英文）")
    @ExcelProperty(value ="店铺发货地址1（英文）")
    private String shopShipAddrEn1 ;
    
    /** 店铺发货地址2（英文） */
    @ApiModelProperty(value = "店铺发货地址2（英文）")
    @ExcelProperty(value ="店铺发货地址2（英文）")
    private String shopShipAddrEn2 ;
    
    /** 城市（英文） */
    @ApiModelProperty(value = "城市（英文）")
    @ExcelProperty(value ="城市（英文）")
    private String shopComCity ;
    
    /** 国家（英文） */
    @ApiModelProperty(value = "国家（英文）")
    @ExcelProperty(value ="国家（英文）")
    private String shopComCountry ;
    
    /** 省份（英文） */
    @ApiModelProperty(value = "省份（英文）")
    @ExcelProperty(value ="省份（英文）")
    private String shopComState ;
    
    /** 地区（英文） */
    @ApiModelProperty(value = "地区（英文）")
    @ExcelProperty(value ="地区（英文）")
    private String shopComDistrict ;
    
    /** 附件地址 */
    @ApiModelProperty(value = "附件地址")
    @ExcelProperty(value ="附件地址")
    private String attachAddrr ;
    
    /** 标识 */
    @ApiModelProperty(value = "标识")
    @ExcelProperty(value ="标识")
    private String identification ;
    
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    @ExcelProperty(value ="邮编")
    private String shopComPosCode ;

    /** 店铺申请详情表List */
    @ApiModelProperty(value = "店铺申请详情表List")
    private List<TbComShopApplyDet> applyDetList ;

    /** 店铺申请银行收款任务List */
    @ApiModelProperty(value = "店铺申请银行收款任务List")
    private List<TbComShopApplyBankCollectTask> bankCollectTaskList ;

    /** 店铺申请税务任务List */
    @ApiModelProperty(value = "店铺申请税务任务List")
    private List<TbComShopApplyTaxnTask> taxnTaskList ;



    @TableField("shop_type")
    @ApiModelProperty(value = "店铺类型")
    private String shopType ;

}