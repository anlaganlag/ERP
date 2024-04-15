package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 产品线
 * </p>
 *
 * @author lsy
 * @since 2022-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("PRODUCT_LINE")
public class ProductLineReplaceUpdateParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("替换修改Ids")
    private List<String> idList;


    @ApiModelProperty("事业部经理")
    private String deptMgr;

    @ApiModelProperty("Team主管")
    private String teamSupv;

    @ApiModelProperty("运营人员")
    private String operator;


    @ApiModelProperty("事业部经理名称")
    private String deptMgrName;

    @ApiModelProperty("Team主管名称")
    private String teamSupvName;

    @ApiModelProperty("运营人员名称")
    private String operatorName;


}