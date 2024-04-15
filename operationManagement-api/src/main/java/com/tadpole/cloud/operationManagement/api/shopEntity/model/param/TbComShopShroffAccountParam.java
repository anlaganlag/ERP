package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

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
 * 资源-信用卡账号管理;资源-税号管理
 * @author : LSY
 * @date : 2023-8-3
 */
@ApiModel(value = "资源-信用卡账号管理",description = "资源-税号管理")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComShopShroffAccountParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal sysId ;
    
    /** 店铺名称;平台_账号_站点 */
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 操作人员名 */
    @ApiModelProperty(value = "操作人员名")
    private String sysOperatePerName ;
    
    /** 操作人员编号 */
    @ApiModelProperty(value = "操作人员编号")
    private String sysOperatePerCode ;
    
    /** 操作日期 */
    @ApiModelProperty(value = "操作日期")
    private Date sysOperateDate ;
    
    /** 信用卡号 */
    @ApiModelProperty(value = "信用卡号")
    private String shopAccountNo ;
    
    /** 有效日期-开始 */
    @ApiModelProperty(value = "有效日期-开始")
    private Date shopEffeRangeStart ;
    
    /** 有效日期-截止 */
    @ApiModelProperty(value = "有效日期-截止")
    private Date shopEffeRangeEnd ;
    
    /** 信用卡开户行 */
    @ApiModelProperty(value = "信用卡开户行")
    private String shopBankName ;
    

}