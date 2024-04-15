package com.tadpole.cloud.externalSystem.api.oa.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 *  入参类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("Company")
public class CompanyParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Integer companyId;

    private String name;

    private Integer parentId;

}
