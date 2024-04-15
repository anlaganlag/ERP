package com.tadpole.cloud.externalSystem.api.ebms.model.resp;

import lombok.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sysPerCode;//": "S20190096",
    private String sysPerName;//: "李四",
    private String sysSupPerCode;//: "S20160051",
    private String sysSupPerName;//: "上级张三",
    private String sysComDeptCode;//: "1.5.3.6.223.224",
    private String sysComDeptFullName;//: "深圳市金畅进出口有限公司|深圳公司|综合管理本部|流程IT中心|项目研发部|后台开发组",
    private String sysPassWord;//: "e10adc3949ba59abbe56e057f20f883e",
    private String sysCreateDate;//: "2022-02-28T15:00:12.617",
    private String sysStatus;//: "正式，试用期",
    private String sysComPost;//: "职位",
    private String tbQxDept;//: null,
    private String tbQxEmployeeFuncs;//: null,
    private String tbQxEmployeeFuncDataFights;//: null
}
