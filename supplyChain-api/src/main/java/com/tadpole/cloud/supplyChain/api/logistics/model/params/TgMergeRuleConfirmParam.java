package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ty
 * @description: 规则合并预览确认入参
 * @date: 2023/6/1
 */
@Data
public class TgMergeRuleConfirmParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** detailIdList */
    @ApiModelProperty("detailId集合")
    private List<TgProductMergeIdsParam> detailList;

    /** 合并分组名称 */
    @ApiModelProperty("合并分组名称")
    private String groupMergeName;

}
