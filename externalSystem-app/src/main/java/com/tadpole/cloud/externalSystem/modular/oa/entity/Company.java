package com.tadpole.cloud.externalSystem.modular.oa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 *  实体类
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
@TableName("company")
@ExcelIgnoreUnannotated
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer companyId;

    private String name;

    private Integer parentId;

}