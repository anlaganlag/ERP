package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* ERP仓库组织编码
* </p>
*
* @author gal
* @since 2021-12-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ERP_WAREHOUSE_CODE")
public class ErpWarehouseCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 编码 */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /** 组织货仓库名称 */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /** 仓库还是组织机构 */
    @TableField("ORGANIZATION_TYPE")
    private String organizationType;

    /** 使用组织 */
    @TableField("USE_ORGANIZATION")
    private String useOrganization;
}