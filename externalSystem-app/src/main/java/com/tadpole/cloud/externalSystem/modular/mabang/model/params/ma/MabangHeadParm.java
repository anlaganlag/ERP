package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.config.MabangConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MabangHeadParm {

    @JSONField(ordinal = 1)
    private String api;

    @JSONField(ordinal = 2)
    private String appkey;

    @JSONField(ordinal = 3)
    private Object data;

    @JSONField(ordinal = 4)
    private String timestamp;

    @JSONField(ordinal = 5)

    private String version;


    public MabangHeadParm(String api, Object data) {
        MabangConfig mabangConfig = SpringContext.getBean(MabangConfig.class);
        this.api = api;
        this.appkey = mabangConfig.getAppkey();
        this.data = data;
        this.timestamp =  new Long(System.currentTimeMillis() / 1000).toString();
        this.version = mabangConfig.getVersion();
    }
}
