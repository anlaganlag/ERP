package com.tadpole.cloud.externalSystem.api.k3.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/20 <br>
 */
@Data
@TableName("View_Supplier")
public class ViewSupplier implements Serializable {
    private static final long serialVersionUID = 986772875698776276L;

    /**
     * 容器ID
     */
    private String fnumber;

    /**
     * 容器地址
     */
    private String fname;
}
