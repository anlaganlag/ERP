package cn.stylefeng.guns.cloud.system.modular.shipment.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 产品同款版本 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_PSS_MANAGE_VERSION")
public class RdPssManageVersionResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    @ApiModelProperty("系统信息-SPU")
    private String sysSpu;


    @ApiModelProperty("同款设定-当前应用版本")
    private String sysCurAppVersion;


    @ApiModelProperty("同款设定-当前迭代版本")
    private String sysCurIteVersion;


    @ApiModelProperty("同款设定-状态 值域{'进行中','已落地'}")
    private String sysVersionStatus;

}
