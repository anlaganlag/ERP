package com.tadpole.cloud.externalSystem.modular.ebms.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
* <p>
    * 
    * </p>
*
* @author gal
* @since 2022-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TbComMateriel")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;



     private String name;
     private Integer age;
     private Double salary;

}