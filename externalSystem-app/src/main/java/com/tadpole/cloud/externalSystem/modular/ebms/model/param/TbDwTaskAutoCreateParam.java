package com.tadpole.cloud.externalSystem.modular.ebms.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * TbDwTaskAutoCreate;TbDWTaskAutoCreate
 * @author : LSY
 * @date : 2023-8-14
 */
@ApiModel(value = "TbDwTaskAutoCreate",description = "TbDWTaskAutoCreate")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbDwTaskAutoCreateParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 系统任务编号 */
    @ApiModelProperty(value = "系统任务编号")
    private BigDecimal sysDwId ;
    
    /** 数据对象编号 */
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    private String dwTaskName ;
    
    /** 数据对象英文名 */
    @ApiModelProperty(value = "数据对象英文名")
    private String dwDataObjEnName ;
    
    /** SellerID */
    @ApiModelProperty(value = "SellerID")
    private String dwSellerId ;
    
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String dwShopNameSimple ;
    
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String dwCountryCode ;
    
    /** 负责人编号 */
    @ApiModelProperty(value = "负责人编号")
    private String dwDlPerson ;
    
    /** 负责人姓名 */
    @ApiModelProperty(value = "负责人姓名")
    private String dwDlPersonName ;
    
    /** 使用部门 */
    @ApiModelProperty(value = "使用部门")
    private String dwUseDepart ;
    
    /** AWSAccessKeyID */
    @ApiModelProperty(value = "AWSAccessKeyID")
    private String dwAwsKey ;
    
    /** 商城端点 */
    @ApiModelProperty(value = "商城端点")
    private String dwEndPoint ;
    
    /** 商城站点编号 */
    @ApiModelProperty(value = "商城站点编号")
    private String dwMarkIdList ;
    
    /** 是否自动下载 */
    @ApiModelProperty(value = "是否自动下载")
    private BigDecimal dwIsAutoDownload ;
    
    /** 平台 */
    @ApiModelProperty(value = "平台")
    private String dwPlatName ;
    
    /** createTime */
    @ApiModelProperty(value = "createTime")
    private Date createTime ;
    

}