package com.tadpole.cloud.operationManagement.modular.shipment.model.params;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
* <p>
    * BI发货推荐查询 D6维度查询
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRecomD6Param implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 平台
     */
    @NotEmpty(message = "平台不能为空")
    private String platform;

    /** 区域 */
    @NotEmpty(message = "区域")
    private String area;

    /** 部门 */
    @NotEmpty(message = "部门")
    private String department;

    /** Team */
    @NotEmpty(message = "Team")
    private String team;

    /** 物料编码 */
    @NotEmpty(message = "物料编码")
    private String materialCode;


    /** ASIN */
    @NotEmpty(message = "ASIN")
    private String asin;

    public String getPk() {
        return platform+area+department+team+materialCode+asin;
    }

}