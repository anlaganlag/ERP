package com.tadpole.cloud.externalSystem.modular.ebms.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class MarkIdEndPointResult  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String country;
    private String marketplaceId;
    private String countryCode;
    private String region;
    private String url;
    private String endpoint;
    private String aws_region;
    private String remark;
}
