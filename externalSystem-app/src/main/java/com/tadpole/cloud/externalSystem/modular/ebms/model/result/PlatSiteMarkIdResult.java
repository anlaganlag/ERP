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
public class PlatSiteMarkIdResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private String platform;
    private String site;
    private String marketplaceId;
}
