package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户搜索关键词
 * </p>
 *
 * @author cyt
 * @since 2022-05-16
 */
@Data
@TableName("L_respose_log")
public class ResposeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.AUTO)
    private long id;

    /** 会计期间 */
    @TableField("SHOP")
    private String shop;

    /** 会计期间 */
    @TableField("SITE")
    private String site;


    /** 会计期间 */
    @TableField("sid")
    private int sid;

    /** 会计期间 */
    @TableField("offset")
    private int offset;

    /** 会计期间 */
    @TableField("length")
    private int length;

    /** 会计期间 */
    @TableField("start_date")
    private String startDate;

    /** 会计期间 */
    @TableField("end_date")
    private String endDate;

    @TableField("type")
    private int type;

    @TableField("create_date")
    private Date createDate;
}