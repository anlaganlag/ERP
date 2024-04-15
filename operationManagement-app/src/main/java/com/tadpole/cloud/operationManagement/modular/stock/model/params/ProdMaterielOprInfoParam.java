package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
    * 物料运营信息
    * </p>
*
* @author gal
* @since 2022-10-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("PROD_MATERIEL_OPR_INFO")
public class ProdMaterielOprInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 物料编码 */
    @ApiModelProperty("MAT_CODE")
    private String matCode;

    /** 是否做FBIG推广 值域{"是","否"}；默认"否"； */
    @ApiModelProperty("MAT_IS_PROMOTE_FBIG")
    private String matIsPromoteFbig;

    /** 是否YouTube推广 值域{"是","否"}；默认"否"； */
    @ApiModelProperty("MAT_IS_PROMOTE_YOUTUBE")
    private String matIsPromoteYoutube;

    /** 图片等级 */
    @ApiModelProperty("MAT_PIC_LEVEL")
    private String matPicLevel;

    /** 文案等级 */
    @ApiModelProperty("MAT_COPY_LEVEL")
    private String matCopyLevel;

    /** 图片要求 */
    @ApiModelProperty("MAT_PIC_REQUIRE")
    private String matPicRequire;

    /** 文案要求 */
    @ApiModelProperty("MAT_COPY_REQUIRE")
    private String matCopyRequire;

    /** 开发卖点 */
    @ApiModelProperty("MAT_DEV_SELLPOINT")
    private String matDevSellpoint;

    /** 参考链接 */
    @ApiModelProperty("MAT_REFE_LINK")
    private String matRefeLink;

    /** 参考图片 */
    @ApiModelProperty("MAT_REFE_PIC")
    private String matRefePic;

}