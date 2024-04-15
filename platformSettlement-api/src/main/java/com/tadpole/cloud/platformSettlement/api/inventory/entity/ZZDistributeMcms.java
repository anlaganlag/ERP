package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.*;

/**
* <p>
*
* </p>
*
* @author gal
* @since 2021-12-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("Z_Z_DISTRIBUTE_MCMS")
public class ZZDistributeMcms implements Serializable {

    private static final long serialVersionUID = 1L;

    private String materialCode;

    private String shopCode;
}