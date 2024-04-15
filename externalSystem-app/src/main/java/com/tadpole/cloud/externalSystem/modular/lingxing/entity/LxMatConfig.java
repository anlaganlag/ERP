package com.tadpole.cloud.externalSystem.modular.lingxing.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

 /**
 * 领星物料配置表;
 * @author : LSY
 * @date : 2022-12-5
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "领星物料配置表",description = "")
@TableName("LX_MAT_CONFIG")
public class LxMatConfig implements Serializable,Cloneable{
    /** 主键 */
    @ApiModelProperty(name = "主键",notes = "")
    @TableId
    private String id ;
    /** 类目 */
    @ApiModelProperty(name = "类目",notes = "")
    private String category ;
    /** 运营大类 */
    @ApiModelProperty(name = "运营大类",notes = "")
    private String productType ;
    /** 领星接口传递方案;适用具体机型，非适用具体机型 */
    @ApiModelProperty(name = "领星接口传递方案",notes = "适用具体机型，非适用具体机型")
    private String lxInterfacePlan ;
    /** 领星产品名称-接口传递方案-名称 */
    @ApiModelProperty(name = "领星产品名称-接口传递方案-名称",notes = "")
    private String lxProductNameTitle ;
    /** 领星产品名称-接口传递方案-明细 */
    @ApiModelProperty(name = "领星产品名称-接口传递方案-明细",notes = "")
    private String lxProductNameValue ;
    /** 领星一级分类-接口传递方案-名称;默认领星一级分类就是EBMS的--运营大类 */
    @ApiModelProperty(name = "领星一级分类-接口传递方案-名称",notes = "默认领星一级分类就是EBMS的--运营大类")
    private String lxCategoryOneTitle ;
    /** 领星一级分类-接口传递方案-明细 */
    @ApiModelProperty(name = "领星一级分类-接口传递方案-明细",notes = "")
    private String lxCategoryOneValue ;
    /** 领星二级分类-接口传递方案-名称 */
    @ApiModelProperty(name = "领星二级分类-接口传递方案-名称",notes = "")
    private String lxCategoryTwoTitle ;
    /** 领星二级分类-接口传递方案-明细 */
    @ApiModelProperty(name = "领星二级分类-接口传递方案-明细",notes = "")
    private String lxCategoryTwoValue ;
    /** 领星三级分类-接口传递方案-名称 */
    @ApiModelProperty(name = "领星三级分类-接口传递方案-名称",notes = "")
    private String lxCategoryThreeTitle ;
    /** 领星三级分类-接口传递方案-明细 */
    @ApiModelProperty(name = "领星三级分类-接口传递方案-明细",notes = "")
    private String lxCategoryThreeValue ;
    /** 领星物料型号-接口传递方案-名称 */
    @ApiModelProperty(name = "领星物料型号-接口传递方案-名称",notes = "")
    private String lxModelTitle ;
    /** 领星物料型号-接口传递方案-明细 */
    @ApiModelProperty(name = "领星物料型号-接口传递方案-明细",notes = "")
    private String lxModelValue ;
    /** 方案版本号;版本迭代，只有一个版本投入使用 */
    @ApiModelProperty(name = "方案版本号",notes = "版本迭代，只有一个版本投入使用")
    private Integer version ;
    /** 是否删除--是否删除:正常：0，删除：1;正常：0，删除：1 */
    @ApiModelProperty(name = "是否删除--是否删除:正常：0，删除：1",notes = "正常：0，删除：1")
    private String isDelete ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    private Date createTime ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    private Date updatedTime ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private String createBy ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private String updatedBy ;

}
