package com.tadpole.cloud.supplyChain.api.logistics.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * EBMS物料信息
 * </p>
 *
 * @author ty
 * @since 2022-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("EBMS_MATERIAL")
public class EbmsMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "EBMS源数据ID")
    @TableField("PARENT_ID")
    private BigDecimal parentId;

    @ApiModelProperty(value = "物料编码")
    @TableField("MATERIAL_CODE")
    private String materialCode;

    @ApiModelProperty(value = "物料名称")
    @TableField("MATERIAL_NAME")
    private String materialName;

    @ApiModelProperty(value = "源运输重量")
    @TableField("MAT_TRANPORT_WEIGHT_ORG")
    private BigDecimal matTranportWeightOrg;

    @ApiModelProperty(value = "源包装体积")
    @TableField("MAT_BOX_VOLUME_ORG")
    private BigDecimal matBoxVolumeOrg;

    @ApiModelProperty(value = "物料禁用状态")
    @TableField("MATERIAL_STATUS")
    private String materialStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;
}
