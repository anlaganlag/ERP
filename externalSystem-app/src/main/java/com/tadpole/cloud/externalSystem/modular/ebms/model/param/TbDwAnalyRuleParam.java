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
 * TbDWAnalyRule;TbDWAnalyRule
 * @author : LSY
 * @date : 2023-8-14
 */
@ApiModel(value = "TbDWAnalyRule",description = "TbDWAnalyRule")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbDwAnalyRuleParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal sysDwRuleId ;
    
    /** 数据对象编号 */
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 数据对象数据表 */
    @ApiModelProperty(value = "数据对象数据表")
    private String dwDataTableObj ;
    
    /** 报告列名 */
    @ApiModelProperty(value = "报告列名")
    private String dwReportProperty ;
    
    /** 源仓表字段名 */
    @ApiModelProperty(value = "源仓表字段名")
    private String dwUnityProperty ;
    
    /** 实体对象属性名 */
    @ApiModelProperty(value = "实体对象属性名")
    private String dwModelName ;
    
    /** 中文名称 */
    @ApiModelProperty(value = "中文名称")
    private String dwComments ;
    
    /** 标注 */
    @ApiModelProperty(value = "标注")
    private String dwRemark ;
    
    /** 状态 */
    @ApiModelProperty(value = "状态")
    private BigDecimal dwStatus ;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date dwCreateTime ;
    
    /** 最后更新时间 */
    @ApiModelProperty(value = "最后更新时间")
    private Date dwLastUpdateTime ;
    

}