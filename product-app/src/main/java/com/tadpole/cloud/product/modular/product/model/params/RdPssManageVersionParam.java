package cn.stylefeng.guns.cloud.system.modular.shipment.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 产品同款版本 入参类
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PSS_MANAGE_VERSION")
public class RdPssManageVersionParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 系统信息-SPU */
    @ApiModelProperty("系统信息-SPU")
    private String sysSpu;

    /** 同款设定-当前应用版本 */
    @ApiModelProperty("同款设定-当前应用版本")
    private String sysCurAppVersion;

    /** 同款设定-当前迭代版本 */
    @ApiModelProperty("同款设定-当前迭代版本")
    private String sysCurIteVersion;

    /** 同款设定-状态 值域{"进行中","已落地"} */
    @ApiModelProperty("同款设定-状态")
    private String sysVersionStatus;

}
