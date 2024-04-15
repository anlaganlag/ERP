package com.tadpole.cloud.externalSystem.api.oa.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;

/**
 * <p>
 *  出参类
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
@ExcelIgnoreUnannotated
@TableName("Company")
public class CompanyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    private Integer companyId;


    private String name;


    private Integer parentId;

}
